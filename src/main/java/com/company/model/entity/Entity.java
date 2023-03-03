package com.company.model.entity;

public abstract class Entity <T> {

    protected EntityStatus entityStatus=EntityStatus.INSERT;

    public EntityStatus getEntityStatus() {
        return entityStatus;
    }

    public void setEntityStatus(EntityStatus entityStatus) {
        this.entityStatus = entityStatus;
    }

    public abstract  T  getKey();

    public enum EntityStatus{INSERT, UPDATE, DELETE, SELECT}




}
