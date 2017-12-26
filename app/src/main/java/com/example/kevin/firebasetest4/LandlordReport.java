package com.example.kevin.firebasetest4;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by kevin on 2017/11/27.
 */

public class LandlordReport {
    ArrayList<Date> PayTenantDate;
    ArrayList<Integer>PayTenantMoney;
    ArrayList<Date> PayLandlordDate;
    ArrayList<Integer>PayLandlordMoney;
    ArrayList<Date> PayWaterDate;
    ArrayList<Integer>PayWaterMoney;
    ArrayList<Date>PayEleDate;
    ArrayList<Integer>PayEleMoney;
    ArrayList<Date>PayManagerDate;
    ArrayList<Integer>PayManagerMoney;

    public LandlordReport(){

    }

    public LandlordReport(ArrayList<Date> payTenantDate, ArrayList<Integer> payTenantMoney, ArrayList<Date> payWaterDate, ArrayList<Integer> payWaterMoney) {
        PayTenantDate = payTenantDate;
        PayTenantMoney = payTenantMoney;
        PayWaterDate = payWaterDate;
        PayWaterMoney = payWaterMoney;
    }

    public LandlordReport(ArrayList<Date> payTenantDate, ArrayList<Integer> payTenantMoney) {
        PayTenantDate = payTenantDate;
        PayTenantMoney = payTenantMoney;
    }

    public LandlordReport(ArrayList<Date> payTenantDate, ArrayList<Integer> payTenantMoney, ArrayList<Date> payLandlordDate, ArrayList<Integer> payLandlordMoney, ArrayList<Date> payWaterDate, ArrayList<Integer> payWaterMoney, ArrayList<Date> payEleDate, ArrayList<Integer> payEleMoney, ArrayList<Date> payManagerDate, ArrayList<Integer> payManagerMoney) {
        PayTenantDate = payTenantDate;
        PayTenantMoney = payTenantMoney;
        PayLandlordDate = payLandlordDate;
        PayLandlordMoney = payLandlordMoney;
        PayWaterDate = payWaterDate;
        PayWaterMoney = payWaterMoney;
        PayEleDate = payEleDate;
        PayEleMoney = payEleMoney;
        PayManagerDate = payManagerDate;
        PayManagerMoney = payManagerMoney;
    }


}
