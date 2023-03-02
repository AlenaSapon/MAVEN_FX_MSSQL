package com.company.model;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Date;

public class OrderDesc extends Entity <OrderDesc.CompoundKey>{

    private final CompoundKey compoundKey;
    private String supplyDesc;
    private BigDecimal price;
    private int qty;
    private BigDecimal amount;



    public class CompoundKey{
        private int syntCode;
        private  int innerCode;
        private  int vendCode;


        public int getVendCode() {
            return vendCode;
        }

        public void setVendCode(int vendCode) {
            this.vendCode = vendCode;
        }

        public int getSyntCode() {
            return syntCode;
        }

        public void setSyntCode(int syntCode) {
            this.syntCode = syntCode;
        }

        public int getInnerCode() {
            return innerCode;
        }

        public void setInnerCode(int innerCode) {
            this.innerCode = innerCode;
        }

    }


    public OrderDesc(){
        compoundKey=new CompoundKey();
    }

    public CompoundKey getCompoundKey() {
        return compoundKey;
    }

    public String getSupplyDesc() {
        return supplyDesc;
    }

    public void setSupplyDesc(String supplyDesc) {
        this.supplyDesc = supplyDesc;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }


    @Override
    public CompoundKey getKey() {
        return getCompoundKey();
    }

}
