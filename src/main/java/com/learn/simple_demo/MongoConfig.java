package com.learn.simple_demo;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.MongoId;

@Data
@AllArgsConstructor
public class MongoConfig {

    @MongoId
    private String tenantId;

    private String url;

    private String database;


    public String url() {
        return url + "/" + database;
    }
}
