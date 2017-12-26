package com.example.kevin.firebasetest4;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by kevin on 2017/11/23.
 */

public class LandlordAdd extends AppCompatActivity {
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

        writeNewLandlord("H3455888810",
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
                true,true);
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
            boolean payTent,boolean payLandord){
        Landlord landlord = new Landlord(
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
         payTent,payLandord
        );
        mDatabase = FirebaseDatabase.getInstance().getReference(landordID);

//        mDatabase.child(landordID).child(houseID).setValue(landlord);
        mDatabase.push().setValue(landlord);
//        mDatabase = mDatabase.child("users");
//        User user = new User("Addy", 20);
//        mDatabase.push().setValue(user);

    }


}
