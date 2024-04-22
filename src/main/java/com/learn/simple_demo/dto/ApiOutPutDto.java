package com.learn.simple_demo.dto;

import com.learn.simple_demo.mongodb.MongoRecord;
import com.learn.simple_demo.mysql.model.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class ApiOutPutDto {

    private Entity entities;

    private List<MongoRecord> mongoRecords;


}
