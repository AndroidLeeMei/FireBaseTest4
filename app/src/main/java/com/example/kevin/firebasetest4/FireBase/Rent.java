package com.example.kevin.firebasetest4.FireBase;

import java.util.Date;

/**
 * Created by kevin on 2017/11/29.
 */

public class Rent {
    String landlordID;
    String houseID;
    String houseSubID;
    String tenantID;
    Date date;
    Integer price;
    public Rent(){}
    public Rent(String landlordID, String houseID, String houseSubID, String tenantID, Date date, Integer price) {
        this.landlordID = landlordID;
        this.houseID = houseID;
        this.houseSubID = houseSubID;
        this.tenantID = tenantID;
        this.date = date;
        this.price = price;
    }

    public String getLandlordID() {
        return landlordID;
    }

    public String getHouseID() {
        return houseID;
    }

    public String getHouseSubID() {
        return houseSubID;
    }

    public String getTenantID() {
        return tenantID;
    }

    public Date getDate() {
        return date;
    }

    public Integer getPrice() {
        return price;
    }
}
