package com.learn.simple_demo;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.MongoTransactionManager;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.learn.simple_demo.Context.getTenantId;

@Configuration
public class MongoTemplateFactory {

    private static MongoTemplate mongoTemplate;

    public MongoTemplateFactory(MongoTemplate mongoTemplate) {
        MongoTemplateFactory.mongoTemplate = mongoTemplate;
    }

    private static Map<String, MongoTemplate> mongoTemplateMap = new HashMap<>();

    private static Map<String, MongoClient> mongoClientMap = new HashMap<>();

    private static Map<String, MongoTransactionManager> mongoTransactionManagerMap = new HashMap<>();


    public static MongoTemplate mongoTemplate() {
        String tenantId = getTenantId();
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
        if (mongoClientMap.containsKey(tenantId)) {
            mongoTemplateMap.put(tenantId, new MongoTemplate(mongoClient, database));
            return mongoTemplateMap.get(tenantId);
        }
        mongoClientMap.put(tenantId, MongoClients.create(mongoConfig.getUrl()));
        mongoTemplateMap.put(tenantId, new MongoTemplate(mongoClient, database));
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


}
