package com.learn.simple_demo.changelogs;


import com.github.cloudyrock.mongock.ChangeLog;
import com.github.cloudyrock.mongock.ChangeSet;
import com.github.cloudyrock.mongock.driver.mongodb.springdata.v2.decorator.impl.MongockTemplate;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Indexes;
import org.bson.Document;

@ChangeLog
public class V202404071751AddTasksIndex {

    @ChangeSet(order = "202404071751", id = "202404071751", author = "test")
    public void execute(MongockTemplate mongockTemplate) {
        mongockTemplate.save(new MongocRecord("test", "testName"));
    }

}



