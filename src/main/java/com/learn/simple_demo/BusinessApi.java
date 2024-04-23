package com.learn.simple_demo;


import com.learn.simple_demo.mongodb.TenantData;
import com.learn.simple_demo.usecase.TenantDataUseCase;
import com.learn.simple_demo.usecase.TenantDeleteUseCase;
import com.learn.simple_demo.usecase.TenantSaveUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/business/api")
@RestController
public class BusinessApi {


    @Autowired
    private TenantSaveUseCase tenantSaveUseCase;

    @Autowired
    private TenantDeleteUseCase tenantDeleteUseCase;

    @Autowired
    private TenantDataUseCase tenantDataUseCase;

    @PostMapping("")
    public void save(@RequestBody TenantData tenantData) {
        tenantData.setTenantId(Context.getTenantId());
        tenantSaveUseCase.execute(tenantData);
    }

    @DeleteMapping("{id}")
    public void delete(@PathVariable String id) {
        Context.resetTenantId(tenantDataUseCase.getTenantId(id));
        tenantDeleteUseCase.execute(id);
    }


    @GetMapping("{id}")
    public TenantData select(@PathVariable String id) {
        Context.resetTenantId(tenantDataUseCase.getTenantId(id));
        return tenantDataUseCase.getTenantData(id);
    }


    @GetMapping
    public List<TenantData> select() {
        return tenantDataUseCase.getTenantDatas();
    }

}
