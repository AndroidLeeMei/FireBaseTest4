package com.example.kevin.firebasetest4;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by kevin on 2017/11/23.
 */

public class Landlord {
    String landordID;
    String landName;
    String landAddr;
    String landNote;
    int price;
    int managerPrice;
    int water;
    int electricity;
    int period;
    String houseID;
    String houseAddr;
    String waterPay;
    String electricityPay;
    String managerPay;
    String tenant;
    String tenantID;
    Date fromDate;
    Date toDate;
    Date nextPayDate;
    Date prePayDate;
    String tenantTel;
    String tenantAddr;
    String tenantNote;
    String bankCount;
    boolean payTent,payLandord;

    public Landlord(String houseAddr,String houseID){
        this.houseAddr=houseAddr;
        this.houseID=houseID;
    }
    public Landlord(){    }
    public Landlord(
            String landorID,
            String landName,
            String landAddr,
            String landNote,
            int price,
            int managerPrice,
            int water,
            int electricity,
            int period,
            String houseID,
            String houseAddr,
            String waterPay,
            String electricityPay,
            String managerPay,
            String tenant,
            String tenantID,
            Date fromDate,
            Date toDate,
            Date nextPayDate,
            Date prePayDate,
            String tenantTel,
            String tenantAddr,
            String tenantNote,
            String bankCount,
            boolean payTent,boolean payLandord){
        this.landordID=landorID;
        this.landName=landName;
        this.landAddr=landAddr;
        this.landNote=landNote;
        this.price=price;
        this.managerPrice=managerPrice;
        this.water=water;
        this.electricity=electricity;
        this.period=period;
        this.houseID=houseID;
        this.houseAddr=houseAddr;
        this.waterPay=waterPay;
        this.electricityPay=electricityPay;
        this.managerPay=managerPay;
        this.tenant=tenant;
        this.tenantID=tenantID;
        this.fromDate=fromDate;
        this.toDate=toDate;
        this.nextPayDate=nextPayDate;
        this.prePayDate=prePayDate;
        this.tenantTel=tenantTel;
        this.tenantAddr=tenantAddr;
        this.tenantNote=tenantNote;
        this.bankCount=bankCount;
        this.payLandord=payLandord;
        this.payTent=payTent;
    }
   public String getLandordID(){
       return this.landordID;
   }
    public String getLandName(){
        return this.landName;
    }
    public String getLandAddr(){
        return this.landAddr;
    }
    public String getLandNote(){
        return this.landNote;
    }
    public int getPrice(){
        return this.price;
    }
    public int getManagerPrice(){
        return this.price;
    }
    public int getWater(){
        return this.water;
    }
    public int getElectricity(){
        return this.electricity;
    }
    public int getPeriod(){
        return this.period;
    }
    public String getHouseAddr(){return this.houseAddr;}
    public String getHouseID(){return this.houseID;}
    public String getWaterPay(){
        return this.waterPay;
    }
    public String getElectricityPay(){
        return this.electricityPay;
    }
    public String getManagerPay(){
        return this.managerPay;
    }
    public String getTenant(){
        return this.tenant;
    }
    public String getTenantID(){
        return this.tenantID;
    }
    public Date getFromDate(){
        return this.fromDate;
    }
    public Date getToDate(){
        return this.toDate;
    }
    public Date getNextPayDate(){
        return this.nextPayDate;
    }
    public Date getPrePayDate(){
        return this.prePayDate;
    }
    public String getTenantTel(){
        return this.tenantTel;
    }
    public String getTenantAddr(){
        return this.tenantAddr;
    }
    public String getTenantNote(){
        return this.tenantNote;
    }
    public String getBankCount(){
        return this.bankCount=bankCount;
    }
    public boolean getPayLandord(){
        return this.payLandord;
    }
    public boolean getPayTent(){
        return this.payTent;
    }


}

