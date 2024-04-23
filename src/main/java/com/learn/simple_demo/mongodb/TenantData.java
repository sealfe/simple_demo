package com.learn.simple_demo.mongodb;

import lombok.Data;

@Data
public class TenantData {

    private String id;

    private String tenantId;

    private String name;

    private String idCard;

    public TenantData toIndex() {
        TenantData tenantData = new TenantData();
        tenantData.setId(id);
        tenantData.setTenantId(tenantId);
        tenantData.setName(name);
        return tenantData;
    }
}
