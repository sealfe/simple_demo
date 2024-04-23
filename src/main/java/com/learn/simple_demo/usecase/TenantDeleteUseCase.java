package com.learn.simple_demo.usecase;

import com.learn.simple_demo.Context;
import com.learn.simple_demo.annotation.UseCase;
import com.learn.simple_demo.mongodb.TenantData;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import static com.learn.simple_demo.MongoTemplateFactory.mongoTemplate;

@UseCase
public class TenantDeleteUseCase {

    @Autowired
    private MongoTemplate mongoTemplate;


    public void execute(String id) {
        mongoTemplate().remove(Query.query(Criteria.where("id").is(id)), TenantData.class);
        mongoTemplate.remove(Query.query(Criteria.where("id").is(id).and("tenantId").is(Context.getTenantId())), TenantData.class);
    }


}
