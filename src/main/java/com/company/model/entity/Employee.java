package com.company.model.entity;

public class Employee extends Entity<Integer>{

    private int staffCode;
    private String fullName;
    private int depIndex;
    private String depName;
    private String city;
    private String region;


    public int getStaffCode() {
        return staffCode;
    }

    public void setStaffCode(int staffCode) {
        this.staffCode = staffCode;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public int getDepIndex() {
        return depIndex;
    }

    public void setDepIndex(int depIndex) {
        this.depIndex = depIndex;
    }

    public String getDepName() {
        return depName;
    }

    public void setDepName(String depName) {
        this.depName = depName;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    @Override
    public Integer getKey() {
        return staffCode;
    }
}
