package com.learn.simple_demo.usecase;

import com.learn.simple_demo.annotation.UseCase;
import com.learn.simple_demo.mongodb.TenantData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;

import static com.learn.simple_demo.MongoTemplateFactory.mongoTemplate;

@UseCase
public class TenantSaveUseCase {

    @Autowired
    private MongoTemplate mongoTemplate;

    public void execute(TenantData tenantData) {
        mongoTemplate().save(tenantData);
        mongoTemplate.save(tenantData.toIndex());
    }




}
