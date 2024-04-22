package com.learn.simple_demo.usecase;

import com.learn.simple_demo.annotation.UseCase;
import com.learn.simple_demo.mongodb.MongoRecord;
import com.learn.simple_demo.mysql.mapper.EntityMapper;
import com.learn.simple_demo.mysql.model.Entity;

import static com.learn.simple_demo.MongoTemplateFactory.mongoTemplate;
import static com.learn.simple_demo.MysqlFactory.mapper;

@UseCase
public class SaveUseCase {


    public void execute(Entity entity, MongoRecord mongoRecord) {
        mongoTemplate().save(mongoRecord);
        EntityMapper mapper = mapper(EntityMapper.class);
        mapper.insert(entity);
    }


}
