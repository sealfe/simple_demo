package com.learn.simple_demo;

import com.learn.simple_demo.mysql.MysqlConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
public class MongoTenantRecordTest {

    @Autowired
    private MongoTemplate mongoTemplate;


    @Test
    public void test() {
        mongoTemplate.save(new MongoConfig("tenant1", "mongodb://localhost:27017", "tenant1"));
        mongoTemplate.save(new MysqlConfig("tenant1", "jdbc:mysql://localhost:3306/tenant1?useUnicode=true&characterEncoding=utf-8&useSSL=false&serverTimezone=UTC", "root", "root1234"));
    }

    @Test
    public void test2() {
        mongoTemplate.save(new MongoConfig("tenant2", "mongodb://localhost:27017", "tenant2"));
        mongoTemplate.save(new MysqlConfig("tenant2", "jdbc:mysql://localhost:3306/tenant2?useUnicode=true&characterEncoding=utf-8&useSSL=false&serverTimezone=UTC", "root", "root1234"));
    }

    @Test
    public void test3() {
        mongoTemplate.save(new MongoConfig("tenant3", "mongodb://localhost:27017", "tenant3"));
        mongoTemplate.save(new MysqlConfig("tenant3", "jdbc:mysql://localhost:3306/tenant3?useUnicode=true&characterEncoding=utf-8&useSSL=false" +
                "&serverTimezone=UTC", "root", "root1234"));
    }


}
