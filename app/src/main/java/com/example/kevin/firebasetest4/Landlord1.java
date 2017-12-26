package com.example.kevin.firebasetest4;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by kevin on 2017/11/27.
 */

public class Landlord1 {
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
    LandlordReport landlordReport;

    public Landlord1(String landordID, String landName, String landAddr, String landNote, int price, int managerPrice, int water, int electricity, int period, String houseID, String houseAddr, String waterPay, String electricityPay, String managerPay, String tenant, String tenantID, Date fromDate, Date toDate, Date nextPayDate, Date prePayDate, String tenantTel, String tenantAddr, String tenantNote, String bankCount, boolean payTent, boolean payLandord, LandlordReport landlordReport) {
        this.landordID = landordID;
        this.landName = landName;
        this.landAddr = landAddr;
        this.landNote = landNote;
        this.price = price;
        this.managerPrice = managerPrice;
        this.water = water;
        this.electricity = electricity;
        this.period = period;
        this.houseID = houseID;
        this.houseAddr = houseAddr;
        this.waterPay = waterPay;
        this.electricityPay = electricityPay;
        this.managerPay = managerPay;
        this.tenant = tenant;
        this.tenantID = tenantID;
        this.fromDate = fromDate;
        this.toDate = toDate;
        this.nextPayDate = nextPayDate;
        this.prePayDate = prePayDate;
        this.tenantTel = tenantTel;
        this.tenantAddr = tenantAddr;
        this.tenantNote = tenantNote;
        this.bankCount = bankCount;
        this.payTent = payTent;
        this.payLandord = payLandord;
        this.landlordReport = landlordReport;
    }

    public String getLandordID() {
        return landordID;
    }

    public String getLandName() {
        return landName;
    }

    public String getLandAddr() {
        return landAddr;
    }

    public String getLandNote() {
        return landNote;
    }

    public int getPrice() {
        return price;
    }

    public int getManagerPrice() {
        return managerPrice;
    }

    public int getWater() {
        return water;
    }

    public int getElectricity() {
        return electricity;
    }

    public int getPeriod() {
        return period;
    }

    public String getHouseID() {
        return houseID;
    }

    public String getHouseAddr() {
        return houseAddr;
    }

    public String getWaterPay() {
        return waterPay;
    }

    public String getElectricityPay() {
        return electricityPay;
    }

    public String getManagerPay() {
        return managerPay;
    }

    public String getTenant() {
        return tenant;
    }

    public String getTenantID() {
        return tenantID;
    }

    public Date getFromDate() {
        return fromDate;
    }

    public Date getToDate() {
        return toDate;
    }

    public Date getNextPayDate() {
        return nextPayDate;
    }

    public Date getPrePayDate() {
        return prePayDate;
    }

    public String getTenantTel() {
        return tenantTel;
    }

    public String getTenantAddr() {
        return tenantAddr;
    }

    public String getTenantNote() {
        return tenantNote;
    }

    public String getBankCount() {
        return bankCount;
    }

    public boolean isPayTent() {
        return payTent;
    }

    public boolean isPayLandord() {
        return payLandord;
    }

    public LandlordReport getLandlordReport() {
        return landlordReport;
    }
}
