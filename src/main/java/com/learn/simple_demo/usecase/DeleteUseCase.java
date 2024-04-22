package com.learn.simple_demo.usecase;

import com.learn.simple_demo.annotation.UseCase;
import com.learn.simple_demo.mysql.mapper.EntityMapper;

import static com.learn.simple_demo.MysqlFactory.mapper;

@UseCase
public class DeleteUseCase {
    public void execute(String id) {
        EntityMapper mapper = mapper(EntityMapper.class);
        mapper.deleteByPrimaryKey(id);
    }

}
