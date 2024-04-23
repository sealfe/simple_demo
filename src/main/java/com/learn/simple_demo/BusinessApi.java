package com.learn.simple_demo;


import com.learn.simple_demo.mongodb.TenantData;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/business/api")
public class BusinessApi {


    @PostMapping("")
    public void save(@RequestBody TenantData tenantData){


    }





}
