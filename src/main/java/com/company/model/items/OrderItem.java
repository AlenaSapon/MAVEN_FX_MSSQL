package com.company.model;

import javafx.beans.property.*;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.Scanner;

public class OrderItem {
    private int syntCode;
    private final IntegerProperty orderNum;
    private final StringProperty supplier;
    private final ObjectProperty<Date> orderDate;
    private final IntegerProperty staffCode;
    private final ObjectProperty<BigDecimal> amount;

    public OrderItem(Order order){
        syntCode= order.getSyntCode();
        orderNum=new SimpleIntegerProperty(order.getOrderNum());
        supplier=new SimpleStringProperty(order.getSupplier());
        orderDate =new SimpleObjectProperty<>(order.getDate());
        staffCode=new SimpleIntegerProperty(order.getStaffCode());
        amount=new SimpleObjectProperty<>(order.getAmount());
    }

    public int getSyntCode() {
        return syntCode;
    }

    private void setSyntCode(int syntCode) {
        this.syntCode = syntCode;
    }

    public Integer getOrderNum() {
        return orderNum.get();
    }

    public void setOrderNum(String orderNum) throws IllegalArgumentException{
        Scanner scanner=new Scanner(orderNum);
        if (scanner.hasNextInt()) {
            this.orderNum.set(scanner.nextInt());
        }
        else {
            throw new IllegalArgumentException(orderNum);
        }
    }

    public String getSupplier() {
        return supplier.get();
    }

    public void setSupplier(String supplier) {
        this.supplier.set(supplier);
    }

    public Date getOrderDate() {
        return orderDate.get();
    }

    public void setOrderDate(String orderDate) throws IllegalArgumentException {
        this.orderDate.set(Date.valueOf(orderDate));
    }

    public Integer getStaffCode() {
        return staffCode.get();
    }

    public void setStaffCode(String staffCode) throws IllegalArgumentException {
        Scanner scanner=new Scanner(staffCode);
        if (scanner.hasNextInt()) {
            this.staffCode.set(scanner.nextInt());
        }
        else {
            throw new IllegalArgumentException(staffCode);
        }
    }

    public BigDecimal getAmount() {
        return amount.get();
    }

    public void setAmount(String amount) throws IllegalArgumentException {
        Scanner scanner=new Scanner(amount);
        if (scanner.hasNextBigDecimal()) {
            this.amount.set(scanner.nextBigDecimal());
        }
        else {
            throw new IllegalArgumentException(amount);
        }
    }

    public Entity<Integer> getEntity(){
        Order order =new Order();
        order.setSyntCode(syntCode);
        order.setOrderNum(orderNum.get());
        order.setSupplier(supplier.get());
        order.setDate(orderDate.get());
        order.setStaffCode(staffCode.get());
        order.setAmount(amount.get());
        return order;
    }

}
