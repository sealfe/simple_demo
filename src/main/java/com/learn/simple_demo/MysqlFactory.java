package com.learn.simple_demo;

import com.baomidou.mybatisplus.core.MybatisConfiguration;
import com.baomidou.mybatisplus.extension.spring.MybatisSqlSessionFactoryBean;
import com.learn.simple_demo.mysql.MysqlConfig;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import lombok.SneakyThrows;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.flywaydb.core.Flyway;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.transaction.ChainedTransactionManager;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

import static com.learn.simple_demo.MongoTemplateFactory.mongoTransactionManager;

@Configuration
public class MysqlFactory {

    private static MongoTemplate mongoTemplate;

    private static DataSourceTransactionManager dataSourceTransactionManager;


    private static Map<String, DataSourceTransactionManager> dataSourceTransactionManagerMap = new HashMap<>();

    private static Map<String, SqlSessionFactory> sqlSessionFactoryMap = new HashMap<>();

    private static Map<String, PlatformTransactionManager> platformTransactionManagerMap = new HashMap<>();

    private static Map<String, DataSource> dataSourceMap = new HashMap<>();


    public MysqlFactory(DataSourceTransactionManager dataSourceTransactionManager, MongoTemplate mongoTemplate) {
        MysqlFactory.dataSourceTransactionManager = dataSourceTransactionManager;
        MysqlFactory.mongoTemplate = mongoTemplate;
    }


    public static <T> T mapper(Class<T> tClass) {
        org.apache.ibatis.session.Configuration configuration = getSessionFactory()
                .getConfiguration();
        SqlSession sqlSession = getSessionFactory().openSession(true);
        return configuration
                .getMapper(tClass, sqlSession);
    }

    @SneakyThrows
    private static SqlSessionFactory getSessionFactory() {
        SqlSessionFactory sqlSessionFactory = sqlSessionFactoryMap.get(Context.getTenantId());
        if (sqlSessionFactory == null) {
            MybatisSqlSessionFactoryBean sqlSessionFactoryBean = new MybatisSqlSessionFactoryBean();
            MybatisConfiguration ibatisConfiguration = new MybatisConfiguration();
            ibatisConfiguration.setMapUnderscoreToCamelCase(true);
            ibatisConfiguration.addMappers("com.learn.simple_demo.mysql.mapper");
            sqlSessionFactoryBean.setConfiguration(ibatisConfiguration);
            DataSource hikariDataSource = getDataSource();
            sqlSessionFactoryBean.setDataSource(hikariDataSource);

            SqlSessionFactory object = sqlSessionFactoryBean.getObject();
            sqlSessionFactoryMap.put(Context.getTenantId(), object);
            sqlSessionFactory = sqlSessionFactoryMap.get(Context.getTenantId());
            migrate();
        }
        return sqlSessionFactory;
    }

    private static DataSource getDataSource() {
        DataSource dataSource = dataSourceMap.get(Context.getTenantId());
        if (dataSource == null) {
            MysqlConfig mysqlConfig = mongoTemplate.findOne(Query.query(Criteria.where("tenantId").is(Context.getTenantId())), MysqlConfig.class);
            HikariConfig config = new HikariConfig();
            config.setJdbcUrl(mysqlConfig.getUrl());
            config.setUsername(mysqlConfig.getUsername());
            config.setPassword(mysqlConfig.getPassword());
            config.setDriverClassName("com.mysql.cj.jdbc.Driver");
            config.setAutoCommit(false);  // 设置 autoCommit 为 false
            HikariDataSource hikariDataSource = new HikariDataSource(config);
            dataSourceMap.put(Context.getTenantId(), hikariDataSource);
            dataSource = dataSourceMap.get(Context.getTenantId());
        }
        return dataSource;
    }


    public static PlatformTransactionManager chainedTransactionManager() {
        PlatformTransactionManager platformTransactionManager = platformTransactionManagerMap.get(Context.getTenantId());
        if (platformTransactionManager == null) {
            platformTransactionManager = new ChainedTransactionManager(getDataSourceTransactionManager(),
                    dataSourceTransactionManager);
            platformTransactionManagerMap.put(Context.getTenantId(), platformTransactionManager);
            platformTransactionManager = platformTransactionManagerMap.get(Context.getTenantId());
        }
        return platformTransactionManager;
    }

    private static DataSourceTransactionManager getDataSourceTransactionManager() {
        DataSourceTransactionManager dataSourceTransactionManager1 = dataSourceTransactionManagerMap.get(Context.getTenantId());
        if (dataSourceTransactionManager1 == null) {
            DataSource build = getDataSource();
            dataSourceTransactionManager1 = new DataSourceTransactionManager(build);
            dataSourceTransactionManagerMap.put(Context.getTenantId(), dataSourceTransactionManager1);
            dataSourceTransactionManager1 = dataSourceTransactionManagerMap.get(Context.getTenantId());
        }
        return dataSourceTransactionManager1;
    }




    public static void migrate() {
        Flyway flyway = Flyway.configure()
                .dataSource(getDataSource())
                .locations("classpath:db/migration")
                .ignoreMissingMigrations(true)
                .outOfOrder(true).load();
        flyway.baseline();
        flyway.migrate();
    }

}
