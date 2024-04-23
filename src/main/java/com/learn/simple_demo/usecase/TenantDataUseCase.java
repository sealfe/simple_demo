package com.learn.simple_demo.usecase;

import com.learn.simple_demo.mongodb.TenantData;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.learn.simple_demo.MongoTemplateFactory.mongoTemplate;

@Service
public class TenantDataUseCase {


    public String getTenantId(String id) {
        return mongoTemplate().findById(id, TenantData.class).getTenantId();
    }

    public TenantData getTenantData(String id) {
        return mongoTemplate().findById(id, TenantData.class);
    }

    public List<TenantData> getTenantDatas() {
        return mongoTemplate().findAll(TenantData.class);
    }
}
