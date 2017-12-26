package com.example.kevin.firebasetest4;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.kevin.firebasetest4.FireBase.PriceData;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class AddRentActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rent_add);


        String input = "2017-08-06";
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date t = null;
        try{
            t = formatter.parse(input);
        }catch(ParseException e){
        }

//        public final static String FINAL_LANDLORD_ID="J3455888889";
//        public final static String FINAL_HOUSE_ID="-KzrIWelZvFNuOXWFqKN";
//        public final static String FINAL_HOUSE_SUB_ID="H001";
//        public final static String FINAL_TENANT_ID="tenantID01";
        PriceData priceData=new PriceData("J3455888889","-KzrIWelZvFNuOXWFqKN","H001","tenantID01",input,t,500,false);

        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("Management");
        mDatabase.push().setValue(priceData);
    }

}
