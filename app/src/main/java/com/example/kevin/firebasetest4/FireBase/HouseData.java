package com.example.kevin.firebasetest4.FireBase;

/**
 * Created by kevin on 2017/12/1.
 */

public class HouseData {
    String createUser;
    String landlordID;
    String tenantID;
    String identity;
    String city;
    String location;
    String addr;
    String title;
    String tenantName;
    String createName;
    String tenantPhone;
    String landlordPassword;

    public HouseData(String createUser, String landlordID, String tenantID, String identity, String city, String location, String addr, String title, String tenantName, String createName, String landlordPassword) {
        this.createUser = createUser;
        this.landlordID = landlordID;
        this.tenantID = tenantID;
        this.identity = identity;
        this.city = city;
        this.location = location;
        this.addr = addr;
        this.title = title;
        this.tenantName = tenantName;
        this.createName = createName;
        this.landlordPassword = landlordPassword;
    }

    public HouseData(String createUser, String landlordID, String tenantID, String identity, String city, String location, String addr, String title, String tenantName, String createName, String tenantPhone, String landlordPassword) {
        this.createUser = createUser;
        this.landlordID = landlordID;
        this.tenantID = tenantID;
        this.identity = identity;
        this.city = city;
        this.location = location;
        this.addr = addr;
        this.title = title;
        this.tenantName = tenantName;
        this.createName = createName;
        this.tenantPhone = tenantPhone;
        this.landlordPassword = landlordPassword;
    }

    public HouseData(String createUser, String landlordID, String tenantID, String identity, String city, String location, String addr, String title, String tenantName, String createName) {
        this.createUser = createUser;
        this.landlordID = landlordID;
        this.tenantID = tenantID;
        this.identity = identity;
        this.city = city;
        this.location = location;
        this.addr = addr;
        this.title = title;
        this.tenantName = tenantName;
        this.createName = createName;
    }

    public String getTenantPhone() {
        return tenantPhone;
    }

    public String getLandlordPassword() {
        return landlordPassword;
    }

    public String getCreateName() {
        return createName;
    }

    public HouseData(String createUser, String landlordID, String tenantID, String identity, String city, String location, String addr, String title, String tenantName) {
        this.createUser = createUser;

        this.landlordID = landlordID;
        this.tenantID = tenantID;
        this.identity = identity;
        this.city = city;
        this.location = location;
        this.addr = addr;
        this.title = title;
        this.tenantName = tenantName;
    }

    public HouseData(String createUser, String landlordID, String tenantID, String identity, String city, String location, String addr, String title) {
        this.createUser = createUser;
        this.landlordID = landlordID;
        this.tenantID = tenantID;
        this.identity = identity;
        this.city = city;
        this.location = location;
        this.addr = addr;
        this.title = title;
    }

    public HouseData(String createUser, String identity, String city, String location, String addr, String title) {
        this.createUser = createUser;
        this.identity = identity;
        this.city = city;
        this.location = location;
        this.addr = addr;
        this.title = title;
    }

    public HouseData() {
    }

    public HouseData(String createUser, String landlordID, String identity, String city, String location, String addr, String title) {
        this.createUser = createUser;
        this.landlordID = landlordID;
        this.identity = identity;
        this.city = city;
        this.location = location;
        this.addr = addr;
        this.title = title;
    }

    public String getTenantName() {
        return tenantName;
    }

    public String getCreateUser() {
        return createUser;
    }

    public String getLandlordID() {
        return landlordID;
    }

    public String getTenantID() {
        return tenantID;
    }

    public String getIdentity() {
        return identity;
    }

    public String getCity() {
        return city;
    }

    public String getLocation() {
        return location;
    }

    public String getAddr() {
        return addr;
    }

    public String getTitle() {
        return title;
    }
}
