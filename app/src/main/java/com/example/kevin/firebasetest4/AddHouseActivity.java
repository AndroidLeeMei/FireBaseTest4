package com.example.kevin.firebasetest4;

import android.content.Context;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.kevin.firebasetest4.FireBase.HouseData;
import com.example.kevin.firebasetest4.FireBase.HouseSub;
import com.example.kevin.firebasetest4.FireBase.PriceData;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class AddHouseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_house);


        addHouse();


//          String FINAL_LANDLORD_ID="J3455888889";
//        public final static String FINAL_HOUSE_ID="-KzrIWelZvFNuOXWFqKN";
//         String FINAL_HOUSE_SUB_ID="…H002";
//        public final static String FINAL_TENANT_ID="tenantID01";

        //house subhouse
//        HouseData houseData=new HouseData(FINAL_LANDLORD_ID,"桃園市","中壢區","民族路20號","法國香謝");
//        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("House");
//        mDatabase.push().setValue(houseData);


//        HouseSub houseSub=new HouseSub("3","-L-H5myGL1PNUi-4jCOE",5000,300,456,123,true);
//        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("HouseSub");
//        mDatabase.push().setValue(houseSub);

    }
    //================================================================
    AlertDialog dialog; //讓自定Layout可有關閉功能

    private void addHouse(){
        View root;
        EditText etRent,etWater,etElectricity,etManagement;
        Button btnConfirm,cancel,confirmAdd,cancelDelete;
        TextView txRentDate,txManDate,txWaterDate,txEleDate;
        TextView txDescription,txDeleteDescription;
        ImageButton imgRentDate,imgWaterDate,imgEleDate,imgManDate;

//        String str=etHouseAddr.getText().toString().trim();
//        Toast.makeText(this,strPriceKey,Toast.LENGTH_SHORT).show();
        LayoutInflater inflater = (LayoutInflater) this
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        root = inflater.inflate(R.layout.dialog_add_house, null);//找出根源樹,
        txDeleteDescription=root.findViewById(R.id.txDeleteDescription);
//        txDeleteDescription.setText("請問是否要將房屋"+str+"資料刪除");

        cancelDelete=root.findViewById(R.id.btnCancel);
        btnConfirm =  root.findViewById(R.id.btnConfirm);

        btnConfirm.setOnClickListener(addPriceListener);
        cancelDelete.setOnClickListener(addPriceListener);

        AlertDialog.Builder abc = new AlertDialog.Builder(this);
        abc.setView(root);
        dialog = abc.show();

    }

    View.OnClickListener addPriceListener= new View.OnClickListener() {
        @Override
        public void onClick(View view) {
//            if (view == confirmAdd) {
//                Log.d("fire=toString=", view.toString());
//                if (etRent.getText().toString().trim().length() > 0) {
//                    int price = Integer.parseInt(etRent.getText().toString().trim());
//                    String input = txRentDate.getText().toString().trim();
//                    String type="Rent";
//                    fireBaseAdd(input,type,price);
//                }
//
//                if (etWater.getText().toString().trim().length() > 0) {
//                    int price = Integer.parseInt(etWater.getText().toString().trim());
//                    String input = txWaterDate.getText().toString().trim();
//                    String type="Rent";
//                    fireBaseAdd(input,type,price);
//                }
//                if (etElectricity.getText().toString().trim().length() > 0) {
//                    int price = Integer.parseInt(etElectricity.getText().toString().trim());
//                    String input = txEleDate.getText().toString().trim();
//                    String type="Electricity";
//                    fireBaseAdd(input,type,price);
//                }
//                if (etManagement.getText().toString().trim().length() > 0) {
//                    int price = Integer.parseInt(etManagement.getText().toString().trim());
//                    String input = txManDate.getText().toString().trim();
//                    String type="Management";
//                    fireBaseAdd(input,type,price);
//                }
//            }
            dialog.dismiss();

        }

    };

    private void fireBaseAdd(String input,String type,int price){
//        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
//        Date t = null;
//        try {
//            t = formatter.parse(input);
//        } catch (ParseException e) {
//        }
//        PriceData priceData = new PriceData(FINAL_LANDLORD_ID, FINAL_HOUSE_ID, FINAL_HOUSE_SUB_ID, FINAL_TENANT_ID, input, t, price);
//        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference(type);
//        mDatabase.push().setValue(priceData);

    }

    }

