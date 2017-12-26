package com.example.kevin.firebasetest4.FireBase;

/**
 * Created by kevin on 2017/12/1.
 */

public class HouseSub {
    String houseID;
    String houseSubName;
    Integer rent;
    Integer management;
    Integer water;
    Integer electricity;
    Boolean hasTenant;

    public HouseSub() {
    }

    public HouseSub(String houseSubName,String houseID, Integer rent, Integer management, Integer water, Integer electricity, Boolean hasTenant) {
        this.houseID = houseID;
        this.houseSubName = houseSubName;
        this.rent = rent;
        this.management = management;
        this.water = water;
        this.electricity = electricity;
        this.hasTenant = hasTenant;
    }

    public String getHouseID() {
        return houseID;
    }

    public String getHouseSubName() {
        return houseSubName;
    }

    public Integer getRent() {
        return rent;
    }

    public Integer getManagement() {
        return management;
    }

    public Integer getWater() {
        return water;
    }

    public Integer getElectricity() {
        return electricity;
    }

    public Boolean getHasTenant() {
        return hasTenant;
    }
}
