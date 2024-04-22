package com.learn.simple_demo.mysql;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.MongoId;

/**
 * @author by fengww
 * @Classname MysqlConfig
 * @Description
 * @Date 2024/4/21 21:53
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MysqlConfig {
    @MongoId
    private String tenantId;

    private String url;


    private String username;

    private String password;

}
