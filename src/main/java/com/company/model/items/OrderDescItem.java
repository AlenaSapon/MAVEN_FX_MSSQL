package com.company.model;

import javafx.beans.property.*;

import java.math.BigDecimal;

public class OrderDescItem {

    private int syntCode;

    private final IntegerProperty innerCode;
    private final IntegerProperty vendCode;
    private final StringProperty supplyDesc;
    private final ObjectProperty<BigDecimal> price;
    private final IntegerProperty qty;
    private final ObjectProperty<BigDecimal> amount;

    public OrderDescItem(OrderDesc orderDesc) {
        this.syntCode=orderDesc.getCompoundKey().getSyntCode();
        this.innerCode=new SimpleIntegerProperty(orderDesc.getCompoundKey().getInnerCode());
        this.vendCode=new SimpleIntegerProperty(orderDesc.getCompoundKey().getVendCode());
        this.supplyDesc=new SimpleStringProperty(orderDesc.getSupplyDesc());
        this.price=new SimpleObjectProperty<>(orderDesc.getPrice());
        this.qty=new SimpleIntegerProperty(orderDesc.getQty());
        this.amount=new SimpleObjectProperty<>(orderDesc.getAmount());
    }

    public int getSyntCode() {
        return syntCode;
    }

    public void setSyntCode(int syntCode) {
        this.syntCode = syntCode;
    }

    public int getInnerCode() {
        return innerCode.get();
    }

    public void setInnerCode(int innerCode) {
        this.innerCode.set(innerCode);
    }

    public int getVendCode() {
        return vendCode.get();
    }

    public void setVendCode(int vendCode) {
        this.vendCode.set(vendCode);
    }

    public String getSupplyDesc() {
        return supplyDesc.get();
    }

    public void setSupplyDesc(String supplyDesc) {
        this.supplyDesc.set(supplyDesc);
    }

    public BigDecimal getPrice() {
        return price.get();
    }

    public void setPrice(BigDecimal price) {
        this.price.set(price);
    }

    public int getQty() {
        return qty.get();
    }

    public void setQty(int qty) {
        this.qty.set(qty);
    }

    public BigDecimal getAmount() {
        return amount.get();
    }

    public void setAmount(BigDecimal amount) {
        this.amount.set(amount);
    }

    public Entity<OrderDesc.CompoundKey> getEntity(){
        OrderDesc orderDesc =new OrderDesc();
        orderDesc.getCompoundKey().setSyntCode(syntCode);
        orderDesc.getCompoundKey().setInnerCode(innerCode.get());
        orderDesc.getCompoundKey().setVendCode(vendCode.get());
        orderDesc.setSupplyDesc(supplyDesc.get());
        orderDesc.setPrice(price.get());
        orderDesc.setQty(qty.get());
        orderDesc.setAmount(amount.get());
        return orderDesc;
    }

}
