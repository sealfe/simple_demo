package com.learn.simple_demo.mysql.model;

import java.time.Instant;

public class Entity {
    private String id;

    private String name;

    private String creatorId;

    private String updaterId;

    private Instant createdAt;

    private String createdAtZone;

    private Instant updatedAt;

    private String updatedAtZone;

    private Boolean isDeleted;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(String creatorId) {
        this.creatorId = creatorId;
    }

    public String getUpdaterId() {
        return updaterId;
    }

    public void setUpdaterId(String updaterId) {
        this.updaterId = updaterId;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public String getCreatedAtZone() {
        return createdAtZone;
    }

    public void setCreatedAtZone(String createdAtZone) {
        this.createdAtZone = createdAtZone;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Instant updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getUpdatedAtZone() {
        return updatedAtZone;
    }

    public void setUpdatedAtZone(String updatedAtZone) {
        this.updatedAtZone = updatedAtZone;
    }

    public Boolean getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(Boolean isDeleted) {
        this.isDeleted = isDeleted;
    }
}