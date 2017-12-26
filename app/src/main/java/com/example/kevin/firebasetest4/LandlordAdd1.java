package com.example.kevin.firebasetest4;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by kevin on 2017/11/27.
 */

public class LandlordAdd1  extends AppCompatActivity {
    private DatabaseReference mDatabase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landlord_first);


        String input = "2017-11-11";
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date t = null;
        try{
            t = formatter.parse(input);

        }catch(ParseException e){

        }


        //  準備輸出的格式，如：星期四 2009/01/01
//              SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//        Calendar calendar = Calendar.getInstance();
//                calendar.set(2018, Calendar.JANUARY, 1);
//                 System.out.println(sdf.format(calendar.getTime()));
        ArrayList<Date> payTenantDate=new ArrayList();
        payTenantDate.add(new Date());
        payTenantDate.add(new Date());

        ArrayList<Integer> payTenantMoney=new ArrayList<>();
        payTenantMoney.add(5000);
        payTenantMoney.add(6000);

        ArrayList<Date> payWaterDate=new ArrayList();
        payWaterDate.add(new Date());
        payWaterDate.add(new Date());

        ArrayList<Integer> payWaterMoney=new ArrayList<>();
        payWaterMoney.add(5000);
        payWaterMoney.add(6000);

        LandlordReport landlordReport=new LandlordReport(payTenantDate, payTenantMoney,payWaterDate,payWaterMoney);


        writeNewLandlord("J3455888892",
                "邱小美",
                "",
                "已收到房租",
                9000,
                500,
                677,
                766,
                3,
                "台中中正路33號",
                "J3455888889h003",//房屋編號
                "landord",
                "landord",
                "tenant",
                "陳妹",
                "Y445729457",
                t,
                new Date(),
                t,
                t,
                "0977465299",
                "彰化民雄路54號",
                "我繳完了",
                "654789200938",
                true,true,landlordReport);
    }
    public void writeNewLandlord(
            String landordID,
            String landName,
            String landAddr,
            String landNote,
            int price,
            int managerPrice,
            int water,
            int electricity,
            int period,
            String houseAddr,
            String houseID,
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
            boolean payTent,boolean payLandord,
            LandlordReport landlordReport){
        Landlord1 landlord1 = new Landlord1(
                landordID,
                landName,
                landAddr,
                landNote,
                price,
                managerPrice,
                water,
                electricity,
                period,
                houseID,
                houseAddr,
                waterPay,
                electricityPay,
                managerPay,
                tenant,
                tenantID,
                fromDate,
                toDate,
                nextPayDate,
                prePayDate,
                tenantTel,
                tenantAddr,
                tenantNote,
                bankCount,
                payTent,payLandord,landlordReport
        );
        mDatabase = FirebaseDatabase.getInstance().getReference(landordID);

//        mDatabase.child(landordID).child(houseID).setValue(landlord);
        mDatabase.push().setValue(landlord1);
//        mDatabase = mDatabase.child("users");
//        User user = new User("Addy", 20);
//        mDatabase.push().setValue(user);

    }
}
