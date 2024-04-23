package com.learn.simple_demo.usecase;

import com.learn.simple_demo.annotation.UseCase;
import com.learn.simple_demo.mongodb.TenantData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;

import static com.learn.simple_demo.MongoTemplateFactory.mongoTemplate;

@UseCase
public class TenantDataUseCase {



    public String getTenantId(String id) {
        return mongoTemplate().findById(id, TenantData.class).getTenantId();
    }

    public TenantData getTenantData(String id) {
        return mongoTemplate().findById(id, TenantData.class);
    }

    public TenantData getTenantDatas() {
        return mongoTemplate().findAll(TenantData.class).get(0);
    }
}
