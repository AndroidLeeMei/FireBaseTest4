package com.example.kevin.firebasetest4;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.kevin.firebasetest4.FireBase.Water;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class AddWaterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_water_add);

        String input = "2017-07-04";
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date t = null;
        try{
            t = formatter.parse(input);

        }catch(ParseException e){

        }

        Water water=new Water("J3455888889","-KzrIWelZvFNuOXWFqKN","H001","tenantID01",t,564);



        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("Water");
        mDatabase.push().setValue(water);
    }
}
