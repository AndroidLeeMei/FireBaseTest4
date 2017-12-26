package com.example.kevin.firebasetest4.FireBase;

import java.util.Date;

/**
 * Created by kevin on 2017/11/30.
 */

public class PriceData {
    String landlordID;
    String houseID;
    String houseSubID;
    String tenantID;
    String dateString;
    Date date;
    Integer price;
    Boolean isCheck;



    public PriceData(){}

    public PriceData(String landlordID, String houseID, String houseSubID, String tenantID, String dateString, Date date, Integer price, Boolean isCheck) {
        this.landlordID = landlordID;
        this.houseID = houseID;
        this.houseSubID = houseSubID;
        this.tenantID = tenantID;
        this.dateString = dateString;
        this.date = date;
        this.price = price;
        this.isCheck = isCheck;
    }


    public Boolean getIsCheck() {
        return isCheck;
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

    public String getDateString() {
        return dateString;
    }

    public Date getDate() {
        return date;
    }

    public Integer getPrice() {
        return price;
    }
}


