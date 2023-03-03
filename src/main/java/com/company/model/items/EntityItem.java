package com.company.model.items;

import com.company.model.entity.Entity;

public abstract class EntityItem <T> extends Entity<T> {

    public abstract T getKey();

    public abstract void performOperation();

    @Override
    public void setEntityStatus(EntityStatus entityStatus) {
        if (super.entityStatus.equals(EntityStatus.INSERT)) return;
        super.setEntityStatus(entityStatus);
    }
    public String getStyle (){
        String style="";
        switch (entityStatus) {
            case DELETE -> style= "-fx-border-color: red ;";
            case INSERT -> style= "-fx-border-color: green ;";
            case UPDATE -> style= "-fx-border-color: blue ;";
            case SELECT -> style= "";
        }
        return style;
    }
}
