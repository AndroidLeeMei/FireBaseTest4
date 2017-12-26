package com.example.kevin.firebasetest4;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kevin.firebasetest4.FireBase.HouseData;
import com.example.kevin.firebasetest4.FireBase.PriceData;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

    public class AddHouseDataActivity extends AppCompatActivity {

        private DatabaseReference mDatabaseCity,mDatabaseRoad,mDatabaseAdd;

        private Spinner sp;//第一個下拉選單
        private Spinner sp2;//第二個下拉選單
        private Context context;

        ArrayAdapter<String> adapter ;
        ArrayAdapter<String> adapter2;

        private ArrayList<String> alrRoadDown=new ArrayList<>();
        private ArrayList<String> alrCity=new ArrayList<>();
        private ArrayList<String> alrRoad=new ArrayList<>();

        RadioButton raLandlord,raTenant;
        EditText editAddrNum,edtTel,edtTitle;
        TextView edtAddr;


        //加入偏好設定
        public static String pref_login_Name,pref_login_user,pref_login_password,pref_login_user_type,pref_login_ID;
        //Restore preferences,取得SharedPreferences的資料
        private void restorePrefs(){
            SharedPreferences settings=getSharedPreferences(MainActivity.PREF,0);
            pref_login_user=settings.getString(MainActivity.PREF_LOGIN_USER,"");
            pref_login_password=settings.getString(MainActivity.PREF_LOGIN_PASSWORID,"");
            pref_login_user_type=settings.getString(MainActivity.PREF_LOGIN_TYPE,"");
            pref_login_ID=settings.getString(LanlordActivity.intent_user_ID,"");
//            pref_login_Name=settings.getString(LanlordActivity.intent_user_Name,"");
//            Log.d("pref_login_user2",pref_login_Name);
        }

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_add_house_data);
//            restorePrefs();
//
//            Log.d("restorePrefs,EMAIL=",pref_login_user+"888");
//            Log.d("restorePrefs login_ID=",pref_login_ID+"888");

            context = this;
            findViews();

            mDatabaseCity = FirebaseDatabase.getInstance().getReference("TWzipCode5");
            mDatabaseCity.addValueEventListener(valueEventListener);

            mDatabaseRoad = FirebaseDatabase.getInstance().getReference("TWzipCode5").child("桃園市中壢區");
            mDatabaseRoad.addValueEventListener(valueEventListenerRoad);

            //程式剛啟始時載入第一個下拉選單
            adapter = new ArrayAdapter<String>(this,R.layout.spinner_style, alrCity);
            adapter.setDropDownViewResource(R.layout.spinner_style);
            sp = (Spinner) findViewById(R.id.spinnerCity);
    //        adapter.setDropDownViewResource(R.layout.spinner_style);
            sp.setAdapter(adapter);


            //程式剛啟始時載入第一個下拉選單
            adapter2 = new ArrayAdapter<String>(this,R.layout.spinner_style, alrRoad);
            adapter2.setDropDownViewResource(R.layout.spinner_style);
    //        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            sp2 = (Spinner) findViewById(R.id.spinnerRoad);
            sp2.setAdapter(adapter2);

            setlisenter();
        }

    //  下拉類別的監看式
        private AdapterView.OnItemSelectedListener selectListener = new AdapterView.OnItemSelectedListener(){
            public void onItemSelected(AdapterView<?> parent, View v, int position, long id){
    //            Log.d("fire==",position+"");
    //            if (position>=0) {
                    int pos = sp.getSelectedItemPosition();
                    switch (parent.getId()) {
                        case R.id.spinnerCity:
                            Log.d("col==", "spinnerCity");
                            //讀取第一個下拉選單是選擇第幾個

                            alrRoadDown = new ArrayList<>();
                            Log.d("fire=",alrCity.get(pos));

                            mDatabaseRoad = FirebaseDatabase.getInstance().getReference("TWzipCode5").child(alrCity.get(pos));
                            mDatabaseRoad.addValueEventListener(valueEventListenerRoad2);

                            //重新產生新的Adapter，用的是二維陣列type2[pos]
                            adapter2 = new ArrayAdapter<String>(context,R.layout.spinner_style, alrRoadDown);
                            adapter2.setDropDownViewResource(R.layout.spinner_style);
                            //載入第二個下拉選單Spinner
                            sp2.setAdapter(adapter2);
                            break;
                        case R.id.spinnerRoad:
                            break;
                    }
                    edtAddr.setText("" + sp.getSelectedItem() + sp2.getSelectedItem());
    //            }

            }

            public void onNothingSelected(AdapterView<?> arg0){

            }

        };


        private void findViews(){
            raLandlord=(RadioButton)findViewById(R.id.rnLandlord);
            raTenant=(RadioButton)findViewById(R.id.rnTenant);

            edtTitle=(EditText)findViewById(R.id.edtTitle);
            edtAddr=(TextView)findViewById(R.id.edtAddr);
            editAddrNum=(EditText)findViewById(R.id.editAddrNum);
            edtTel=(EditText)findViewById(R.id.edtTitle);
        }

        private void setlisenter() {
            sp.setOnItemSelectedListener(selectListener);
            sp2.setOnItemSelectedListener(selectListener);
        }

        ValueEventListener valueEventListenerRoad2 = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                        alrRoadDown.add(ds.getKey());
                }

                //重新產生新的Adapter，用的是二維陣列type2[pos]
                adapter2 = new ArrayAdapter<String>(context,R.layout.spinner_style,alrRoadDown);
                adapter2.setDropDownViewResource(R.layout.spinner_style);

                //載入第二個下拉選單Spinner
                sp2.setAdapter(adapter2);

    //            adapter2.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting Post failed, log a message
                Log.w("realdb", "loadPost:onCancelled", databaseError.toException());
                // ...
            }
        };


        ValueEventListener valueEventListenerRoad = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
    //            alrRoad=new ArrayList<>();
//                Log.d("FireBaseTraining", " snapshot.getValue() = " + dataSnapshot.getValue());
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    alrRoad.add(ds.getKey());
//                    Log.d("FireBaseTraining", " ds.getKey()=" + ds.getKey());

                }
                adapter2.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting Post failed, log a message
                Log.w("realdb", "loadPost:onCancelled", databaseError.toException());
                // ...
            }
        };


        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
    //            alrCity=new ArrayList<>();
//                Log.d("FireBaseTraining", " snapshot.getValue() = " + dataSnapshot.getValue());
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    alrCity.add(ds.getKey());
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting Post failed, log a message
                Log.w("realdb", "loadPost:onCancelled", databaseError.toException());
                // ...
            }
        };

    //===============================================================================
        public void addHouseData(View target){
            Log.d("pref_login_user2","22");
            Intent intent = getIntent();
            Bundle bundle = intent.getExtras();
            String intent_login_user = bundle.getString("PREF_LOGIN_USER");
            String intent_login_user_type = bundle.getString("PREF_LOGIN_USER_TYPE");
            String intent_login_password = bundle.getString("PREF_LOGIN_PASSWORD");
            String intent_login_ID = bundle.getString("PREF_LOGIN_ID");
            String intent_login_name = bundle.getString("PREF_LOGIN_NAME");
            String intent_check_Password = bundle.getString("FIREDB_USER_CHECK_PASSWORD");
            Log.d("pref_login_user3",intent_check_Password+"");

            Log.d("type=",intent_login_user_type);
            String strTenant,strLandord,strTenantName;
            if(intent_login_user_type.equals("tenant")){//如果該使用者是房客則新增房屋時順便填入房客資訊
                     strTenant=intent_login_ID;
                    strTenantName=intent_login_name;
                strLandord=null;
            }else {
                strTenant = null;
                strTenantName=null;
                strLandord=intent_login_ID;
            }

                HouseData houseData = new HouseData(
                        intent_login_ID//新增該筆資料的人,擁有可刪除此資料的權限
                        , strLandord  //landlordID
                        , strTenant  //tentandID
                        , intent_login_user_type  //新增該筆資料者的身份
                        , sp.getSelectedItem().toString()  //地址所在城市
                        , sp2.getSelectedItem().toString()  //地址所在路名
                        , editAddrNum.getText().toString()   //地址後面的詳細資料
                        , edtTitle.getText().toString()
                        ,strTenantName
                        ,intent_login_name
                        ,LanlordActivity.intent_check_Password
                );    //房屋代號
                DatabaseReference mDatabaseAdd = FirebaseDatabase.getInstance().getReference("House");
                mDatabaseAdd.push().setValue(houseData);
                finish();
//            }else
//                Toast.makeText(AddHouseDataActivity.this,"請選擇身分類別",Toast.LENGTH_SHORT).show();
        }

        //====================================================================
        public void addHouseCancel(View target){
            finish();
        }

        public void addSubHouse(View target){


        }
    }
