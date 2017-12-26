package com.example.kevin.firebasetest4.FireBase;

import android.util.Log;

import java.util.Date;

/**
 * Created by kevin on 2017/12/2.
 */

public class Tenant {

        String createID;  //放建立者的id
        String houseID;
        String name;
        String id;
        String addr;
        String tel;
        String phone;
        String signDate;
    String startDate;
    String endDate;
        Integer rent;
        Integer period;
        Integer payDay;
        Boolean checkWater;
        Boolean checkEle;
        Boolean checkGas;
        Boolean checkMan;

    String city;
    String location;


    public Tenant() {
    }

    public Tenant(String createID, String houseID, String name, String id, String addr, String tel, String phone, String signDate, String startDate, String endDate, Integer rent, Integer period, Integer payDay, Boolean checkWater, Boolean checkEle, Boolean checkGas, Boolean checkMan, String city, String location) {
        this.createID = createID;
        this.houseID = houseID;
        this.name = name;
        this.id = id;
        this.addr = addr;
        this.tel = tel;
        this.phone = phone;
        this.signDate = signDate;
        this.startDate = startDate;
        this.endDate = endDate;
        this.rent = rent;
        this.period = period;
        this.payDay = payDay;
        this.checkWater = checkWater;
        this.checkEle = checkEle;
        this.checkGas = checkGas;
        this.checkMan = checkMan;
        this.city = city;
        this.location = location;
    }

    public Tenant(String houseID, String createID, String houseID1, String name, String id, String addr, String tel, String phone, String signDate, String startDate, String endDate, Integer rent, Integer period, Integer payDay, Boolean checkWater, Boolean checkEle, Boolean checkGas, Boolean checkMan) {
        this.houseID = houseID;
        this.createID = createID;
        this.houseID = houseID1;
        this.name = name;
        this.id = id;
        this.addr = addr;
        this.tel = tel;
        this.phone = phone;
        this.signDate = signDate;
        this.startDate = startDate;
        this.endDate = endDate;
        this.rent = rent;
        this.period = period;
        this.payDay = payDay;
        this.checkWater = checkWater;
        this.checkEle = checkEle;
        this.checkGas = checkGas;
        this.checkMan = checkMan;
    }

    public Tenant(String createID, String houseID, String name, String id, String addr, String tel, String phone, String signDate, String startDate, String endDate, Integer rent, Integer period, Integer payDay, Boolean checkWater, Boolean checkEle, Boolean checkGas, Boolean checkMan) {
        this.createID = createID;
        this.houseID = houseID;
        this.name = name;
        this.id = id;
        this.addr = addr;
        this.tel = tel;
        this.phone = phone;
        this.signDate = signDate;
        this.startDate = startDate;
        this.endDate = endDate;
        this.rent = rent;
        this.period = period;
        this.payDay = payDay;
        this.checkWater = checkWater;
        this.checkEle = checkEle;
        this.checkGas = checkGas;
        this.checkMan = checkMan;
    }

    public Tenant(String createID, String houseID, String name, String id, String addr, String tel, String phone, String signDate, String startDate, String endDate, Integer rent, Integer period, Integer payDay) {
        this.createID = createID;
        this.houseID = houseID;
        this.name = name;
        this.id = id;
        this.addr = addr;
        this.tel = tel;
        this.phone = phone;
        this.signDate = signDate;
        this.startDate = startDate;
        this.endDate = endDate;
        this.rent = rent;
        this.period = period;
        this.payDay = payDay;
    }

    public String getCity() {
        return city;
    }

    public String getLocation() {
        return location;
    }

    public Boolean getCheckWater() {
        return checkWater;
    }

    public Boolean getCheckEle() {
        return checkEle;
    }

    public Boolean getCheckGas() {
        return checkGas;
    }

    public Boolean getCheckMan() {
        return checkMan;
    }

    public String getCreateID() {
        return createID;
    }

    public String getHouseID() {
        return houseID;
    }

    public String getName() {
        return name;
    }

    public String getId() {
        return id;
    }

    public String getAddr() {
        return addr;
    }

    public String getTel() {
        return tel;
    }

    public String getPhone() {
        return phone;
    }

    public String getSignDate() {
        return signDate;
    }

    public String getStartDate() {
        return startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public Integer getRent() {
        return rent;
    }

    public Integer getPeriod() {
        return period;
    }

    public Integer getPayDay() {
        return payDay;
    }


}
