package com.learn.simple_demo.usecase;

import com.learn.simple_demo.annotation.UseCase;
import com.learn.simple_demo.dto.ApiOutPutDto;
import com.learn.simple_demo.mongodb.MongoRecord;
import com.learn.simple_demo.mysql.mapper.EntityMapper;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import static com.learn.simple_demo.MongoTemplateFactory.mongoTemplate;
import static com.learn.simple_demo.MysqlFactory.mapper;

@Service
public class SelectUseCase {


    public ApiOutPutDto select(String id) {
        EntityMapper mapper = mapper(EntityMapper.class);
        return new ApiOutPutDto(mapper.selectByPrimaryKey(id), mongoTemplate().find(new Query(), MongoRecord.class));
    }

}
