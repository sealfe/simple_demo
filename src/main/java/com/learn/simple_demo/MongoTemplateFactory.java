package com.learn.simple_demo;

import com.github.cloudyrock.mongock.driver.mongodb.springdata.v2.SpringDataMongoV2Driver;
import com.github.cloudyrock.spring.v5.MongockSpring5;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.MongoTransactionManager;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.learn.simple_demo.Context.*;

@Configuration
public class MongoTemplateFactory {

    private static ApplicationContext applicationContext;
    private static MongoTemplate mongoTemplate;

    public MongoTemplateFactory(MongoTemplate mongoTemplate, ApplicationContext applicationContext) {
        MongoTemplateFactory.mongoTemplate = mongoTemplate;
        MongoTemplateFactory.applicationContext = applicationContext;
    }

    private static Map<String, MongoTemplate> mongoTemplateMap = new HashMap<>();

    private static Map<String, MongoClient> mongoClientMap = new HashMap<>();

    private static Map<String, MongoTransactionManager> mongoTransactionManagerMap = new HashMap<>();


    public static MongoTemplate mongoTemplate() {
        if (proxyTenantId.get() != null) {
            return getMongoTemplate(proxyTenantId.get());
        }
        if ("SD".equals(bizName.get())) {
            return mongoTemplate;
        }
        return getMongoTemplate(getTenantId());
    }

    private static MongoTemplate getMongoTemplate(String tenantId) {
        if (mongoTemplateMap.containsKey(tenantId)) {
            return mongoTemplateMap.get(tenantId);
        }
        List<MongoConfig> mongoConfigs = mongoTemplate.find(Query.query(Criteria.where("tenantId").is(tenantId)), MongoConfig.class);
        if (mongoConfigs.isEmpty()) {
            throw new RuntimeException("No mongo config found for tenantId: " + tenantId);
        }
        MongoConfig mongoConfig = mongoConfigs.get(0);
        String database = mongoConfig.getDatabase();
        MongoClient mongoClient = mongoClientMap.get(tenantId);
        if (mongoClient == null) {
            mongoClientMap.put(tenantId, MongoClients.create(mongoConfig.url()));
            mongoClient = mongoClientMap.get(tenantId);
        }
        mongoTemplateMap.put(tenantId, new MongoTemplate(mongoClient, database));
        mongock(mongoTemplateMap.get(tenantId));
        return mongoTemplateMap.get(tenantId);
    }


    public static MongoTransactionManager mongoTransactionManager() {
        MongoTransactionManager mongoTransactionManager = mongoTransactionManagerMap.get(getTenantId());
        if (mongoTransactionManager != null) {
            return mongoTransactionManager;
        }
        MongoDbFactory mongoDbFactory = mongoTemplate().getMongoDbFactory();
        MongoTransactionManager mongoTransactionManager1 = new MongoTransactionManager(mongoDbFactory);
        mongoTransactionManagerMap.put(Context.getTenantId(), mongoTransactionManager1);
        return mongoTransactionManager1;
    }


    public static void mongock(MongoTemplate mongoTemplate1) {
        MongockSpring5.builder()
                .setDriver(SpringDataMongoV2Driver.withDefaultLock(mongoTemplate1))
                .addChangeLogsScanPackage("com.learn.simple_demo.changelogs") // Your changelogs location
                .setSpringContext(applicationContext).buildApplicationRunner().run(null);
    }


    public static void clean() {
        mongoTemplateMap.clear();
        mongoClientMap.clear();
        mongoTransactionManagerMap.clear();
    }

}
