package com.company.model.entity;

public class Supply extends Entity<Integer>{

    private int innerCode;
    private String supplyName;
    private String supplyDesc;
    private double consumptionRate;

    public record InnerCodeDesc(int innerCode, String supplyName) {

        @Override
        public String toString() {
            return String.valueOf(innerCode).concat(" (").concat(supplyName).concat(")");
        }

    }

    public int getInnerCode() {
        return innerCode;
    }

    public void setInnerCode(int innerCode) {
        this.innerCode = innerCode;
    }

    public String getSupplyName() {
        return supplyName;
    }

    public void setSupplyName(String supplyName) {
        this.supplyName = supplyName;
    }

    public String getSupplyDesc() {
        return supplyDesc;
    }

    public void setSupplyDesc(String supplyDesc) {
        this.supplyDesc = supplyDesc;
    }

    public double getConsumptionRate() {
        return consumptionRate;
    }

    public void setConsumptionRate(double consumptionRate) {
        this.consumptionRate = consumptionRate;
    }

    @Override
    public Integer getKey() {
        return innerCode;
    }
}
