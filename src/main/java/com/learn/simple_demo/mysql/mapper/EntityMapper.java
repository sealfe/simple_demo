package com.learn.simple_demo.mysql.mapper;

import com.learn.simple_demo.mysql.model.Entity;

public interface EntityMapper {
    int deleteByPrimaryKey(String id);

    int insert(Entity record);

    int insertSelective(Entity record);

    Entity selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(Entity record);

    int updateByPrimaryKey(Entity record);
}