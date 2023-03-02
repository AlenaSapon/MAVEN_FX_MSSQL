package com.company.model;


import java.math.BigDecimal;
import java.sql.Date;

public class Order extends Entity <Integer> {
    private int syntCode;
    private  int orderNum;
    private String supplier;
    private Date date;
    private int staffCode;
    private BigDecimal amount;



    public int getSyntCode() {
        return syntCode;
    }

    public void setSyntCode(int syntCode) {
        this.syntCode = syntCode;
    }

    public int getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(int orderNum) {
        this.orderNum = orderNum;
    }

    public String getSupplier() {
        return supplier;
    }

    public void setSupplier(String supplier) {
        this.supplier = supplier;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getStaffCode() {
        return staffCode;
    }

    public void setStaffCode(int staffCode) {
        this.staffCode = staffCode;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    @Override
    public String toString() {
        return syntCode+" " +orderNum+supplier+date+staffCode+amount;
    }


    @Override
    public Integer getKey() {
        return getSyntCode();
    }
}
