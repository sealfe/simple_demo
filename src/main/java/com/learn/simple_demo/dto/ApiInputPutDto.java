package com.learn.simple_demo.dto;

import com.learn.simple_demo.mongodb.MongoRecord;
import com.learn.simple_demo.mysql.model.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApiInputPutDto {

    private Entity entity;

    private MongoRecord mongoRecord;


}
