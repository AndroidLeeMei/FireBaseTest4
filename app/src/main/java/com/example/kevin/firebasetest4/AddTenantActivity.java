package com.example.kevin.firebasetest4;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kevin.firebasetest4.FireBase.HouseData;
import com.example.kevin.firebasetest4.FireBase.Tenant;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static com.example.kevin.firebasetest4.R.id.edtPhone;
import static com.example.kevin.firebasetest4.R.id.etName;
import static com.example.kevin.firebasetest4.R.id.imgRentDate;

public class AddTenantActivity extends AppCompatActivity {
//    private String houseId="aaa001";

    private DatabaseReference mDatabaseCity, mDatabaseRoad, mDatabaseTenant;
    private Context context;
    ArrayAdapter<String> adapter, adapter2;
    private ArrayList<String> alrRoadDown = new ArrayList<>();
    private ArrayList<String> alrCity = new ArrayList<>();
    private ArrayList<String> alrRoad = new ArrayList<>();
    CheckBox checkWater,checkEle,checkGas,checkMan;
    EditText etTel, edtName, etID, etPhone, etRent,edtPayDay,edtPayPeriod;
    TextView edtAddr, etAddrNumber, txtLandlordInfo;
    Spinner sp, sp2;//下拉選單
    int spCount;
    String strLocation,strCity;
    Boolean spFlag=true,sp2Flag=true;
    Button txSignDate, txStartDate, txEndDate;

    Button btnSubmit;


    //取得 intent extra資料
    public static String intent_login_user,intent_login_user_type,intent_login_password,intent_user_ID;
    public static String  intent_user_Name,intent_user_Phone,intent_user_Bank,intent_user_Bank_Number,intent_user_Account;
    public static String intent_check_Password;
    private static String intent_house_id,intent_house_title,intent_house_addr,intent_tenant_id;
    public void restoreIntent(){
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        intent_login_user = bundle.getString("PREF_LOGIN_USER");
        intent_login_user_type = bundle.getString("PREF_LOGIN_USER_TYPE");
        intent_login_password = bundle.getString("PREF_LOGIN_PASSWORD");
        intent_user_ID = bundle.getString("FIREDB_LOGIN_ID");
        intent_user_Name = bundle.getString("FIREDB_USER_NAME");
        intent_user_Phone = bundle.getString("FIREDB_USER_PHONE");
        intent_user_Bank = bundle.getString("FIREDB_USER_BANK");
        intent_user_Bank_Number = bundle.getString("FIREDB_USER_BANK_NUMBER");
        intent_user_Account = bundle.getString("FIREDB_USER_ACCUNT");
        intent_check_Password = bundle.getString("FIREDB_USER_CHECK_PASSWORD");
        //==================================================================
        intent_house_id = bundle.getString("FIREDB_HOUSE_ID");
        intent_house_title=bundle.getString("FIREDB_HOUSE_TITLE");
        intent_house_addr=bundle.getString("FIREDB_HOUSE_ADDR");
        intent_tenant_id=bundle.getString("FIREDB_TENANT_ID");
        Log.d("tenantgetenid=",intent_tenant_id+"aaa");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_tenant);
        restoreIntent();

        context = this;
        findViews();

        String strSelfTitle,strOtherTitle;
        if (intent_login_user_type.equals("landlord")){
            strSelfTitle="房東";
            strOtherTitle="房客";
        }else{
            strSelfTitle="房客";
            strOtherTitle="房東";
        }
        TextView txTitle=(TextView)findViewById(R.id.txTitle) ;
        txtLandlordInfo.setText(strSelfTitle+":"+intent_user_Name +"\n" +intent_house_addr);
        txTitle.setText("*"+strOtherTitle+"姓名:");




        mDatabaseCity = FirebaseDatabase.getInstance().getReference("TWzipCode5");
        mDatabaseCity.addValueEventListener(valueEventListener);

        mDatabaseRoad = FirebaseDatabase.getInstance().getReference("TWzipCode5").child("桃園市中壢區");
        mDatabaseRoad.addValueEventListener(valueEventListenerRoad);


        //程式剛啟始時載入第一個下拉選單
        adapter = new ArrayAdapter(this, R.layout.spinner_style, alrCity);
        adapter.setDropDownViewResource(R.layout.spinner_style);
        sp.setAdapter(adapter);

        //程式剛啟始時載入第一個下拉選單
        adapter2 = new ArrayAdapter<String>(this, R.layout.spinner_style, alrRoad);
        adapter2.setDropDownViewResource(R.layout.spinner_style);
        sp2.setAdapter(adapter2);



        setlisenter();

        if (intent_tenant_id==null)//新增
        {
            mDatabaseTenant = FirebaseDatabase.getInstance().getReference("Tenant");
        }else//修改
            mDatabaseTenant = FirebaseDatabase.getInstance().getReference("Tenant").child(intent_tenant_id);

        mDatabaseTenant.addValueEventListener(valueEventListenerTenant);


    }

    //  下拉類別的監看式
    private AdapterView.OnItemSelectedListener selectListener = new AdapterView.OnItemSelectedListener() {
        public void onItemSelected(AdapterView<?> parent, View v, int position, long id) {
//            Log.d("fire==",position+"");
//            if (position>=0) {
            int pos = sp.getSelectedItemPosition();
            switch (parent.getId()) {
                case R.id.spinnerCity:
                    Log.d("spCount","~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");

                    spCount++;
                    Log.d("col==", "spinnerCity");
                    //讀取第一個下拉選單是選擇第幾個

                    alrRoadDown = new ArrayList<>();
                    Log.d("fire=", alrCity.get(pos));

                    mDatabaseRoad = FirebaseDatabase.getInstance().getReference("TWzipCode5").child(alrCity.get(pos));
                    mDatabaseRoad.addValueEventListener(valueEventListenerRoad2);

                    //重新產生新的Adapter，用的是二維陣列type2[pos]
                    adapter2 = new ArrayAdapter<String>(context, R.layout.spinner_style, alrRoadDown);
                    adapter2.setDropDownViewResource(R.layout.spinner_style);
                    //載入第二個下拉選單Spinner
                    sp2.setAdapter(adapter2);

                    Log.d("spCount","casesp");
//                    edtAddr.setText(sp.getSelectedItem().toString());
                    break;
                case R.id.spinnerRoad:
//                    spCount++;
                    Log.d("spCount","casesp2");


                    Log.d("spCount",sp.getSelectedItem()+"A");
                    Log.d("spCountsp2",sp2.getSelectedItem()+"b");
                    Log.d("spCountsp2Flag",sp2Flag+"");
                    Log.d("spCountspFlag",spFlag+"");
                    Log.d("spCount","===================");
                    edtAddr.setText(sp.getSelectedItem().toString() + sp2.getSelectedItem().toString());
//                    strLocation
                    if (spFlag){
                        sp.setSelection(alrCity.indexOf(strCity));
                        spFlag=false;
                    }
                    if ((alrRoadDown.indexOf(strLocation)>=0)&&sp2Flag) {
                        sp2.setSelection(alrRoadDown.indexOf(strLocation));
                        sp2Flag=false;
                    }
                    break;
            }
        }

        public void onNothingSelected(AdapterView<?> arg0) {
        }
    };


    private void findViews() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        txtLandlordInfo = (TextView) findViewById(R.id.txtLandlordInfo);
        txSignDate = (Button) findViewById(R.id.txSignDate);
        txStartDate = (Button) findViewById(R.id.txStartDate);
        txEndDate = (Button) findViewById(R.id.txEndDate);
        edtName = (EditText) findViewById(R.id.etName);
        etID = (EditText) findViewById(R.id.etID);
        edtAddr = (TextView) findViewById(R.id.etAddr);
        etAddrNumber = (EditText) findViewById(R.id.etAddrNumber);
        etTel = (EditText) findViewById(R.id.etTel);
        etPhone = (EditText) findViewById(R.id.etPhone);
        etRent = (EditText) findViewById(R.id.etRent);

        edtPayPeriod = (EditText) findViewById(R.id.edtPayPeriod);
        edtPayDay = (EditText) findViewById(R.id.edtPayDay);
        sp2 = (Spinner) findViewById(R.id.spinnerRoad);
        sp = (Spinner) findViewById(R.id.spinnerCity);

        //=============================================================
        checkWater=(CheckBox)findViewById(R.id.checkWater);
        checkEle=(CheckBox)findViewById(R.id.checkEle);
        checkGas=(CheckBox)findViewById(R.id.checkGas);
        checkMan=(CheckBox)findViewById(R.id.checkMan);
        //=====================================================
        btnSubmit=(Button)findViewById(R.id.btnSubmit);
    }


    ValueEventListener valueEventListenerTenant = new ValueEventListener() {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {

            Log.d("editTenant ","onDataChange");
            for (DataSnapshot ds : dataSnapshot.getChildren()) {
                if (ds.getKey().equals("location"))strLocation = ds.getValue().toString();
                if (ds.getKey().equals("city")) strCity=ds.getValue().toString();
                if (ds.getKey().equals("rent"))etRent.setText(ds.getValue().toString());
                if (ds.getKey().equals("addr"))etAddrNumber.setText(ds.getValue().toString());
                if (ds.getKey().equals("checkWater"))checkWater.setChecked((boolean)ds.getValue());
                if (ds.getKey().equals("checkEle"))checkEle.setChecked((boolean)ds.getValue());
                if (ds.getKey().equals("checkGas"))checkGas.setChecked((boolean)ds.getValue());
                if (ds.getKey().equals("checkMan"))checkMan.setChecked((boolean)ds.getValue());
                if (ds.getKey().equals("endDate"))txEndDate.setText(ds.getValue().toString());;
                if (ds.getKey().equals("signDate"))txSignDate.setText(ds.getValue().toString());;
                if (ds.getKey().equals("startDate"))txStartDate.setText(ds.getValue().toString());;
                if (ds.getKey().equals("id"))etID.setText(ds.getValue().toString());;
                if (ds.getKey().equals("name"))edtName.setText(ds.getValue().toString());;
                if (ds.getKey().equals("payDay"))edtPayDay.setText(ds.getValue().toString());;
                if (ds.getKey().equals("period"))edtPayPeriod.setText(ds.getValue().toString());;
                if (ds.getKey().equals("phone"))etPhone.setText(ds.getValue().toString());;
                if (ds.getKey().equals("tel"))etTel.setText(ds.getValue().toString());;
            }
//        mDatabaseTenant.removeEventListener(valueEventListenerTenant);
        }

        @Override
        public void onCancelled(DatabaseError databaseError) {
            // Getting Post failed, log a message
            Log.w("realdb", "loadPost:onCancelled", databaseError.toException());
            // ...
        }
    };




    ValueEventListener valueEventListenerRoad2 = new ValueEventListener() {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
            for (DataSnapshot ds : dataSnapshot.getChildren()) {
                alrRoadDown.add(ds.getKey());
            }

            //重新產生新的Adapter，用的是二維陣列type2[pos]
            adapter2 = new ArrayAdapter<String>(context, R.layout.spinner_style, alrRoadDown);
            adapter2.setDropDownViewResource(R.layout.spinner_style);

            //載入第二個下拉選單Spinner
            sp2.setAdapter(adapter2);
//            if ((!(intent_tenant_id==null))&&spFlag)//若是修改時,要帶入資料
//            {
//                sp2.setSelection(alrRoadDown.indexOf(strlocation));
//                spFlag = false;
//            }
            Log.d("editTenant","adapter2Listener");
            adapter2.notifyDataSetChanged();
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
            Log.d("FireBaseTraining", " snapshot.getValue() = " + dataSnapshot.getValue());
            for (DataSnapshot ds : dataSnapshot.getChildren()) {
                alrRoad.add(ds.getKey());

            }
//            Log.d("editTenant", " valueEventListenerRoad");
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
            Log.d("FireBaseTraining", " snapshot.getValue() = " + dataSnapshot.getValue());
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
    //================================================
    public void addTenantCancel(View target){
        finish();
    }

    //===============================================================================
    private void editTenant(){
        //更新tenentID的資料
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("Tenant");
        mDatabase = mDatabase.child(DetailActivity.intent_tenant_id);
        Map<String, Object> nameMap = new HashMap<String, Object>();
        nameMap.put("addr", etAddrNumber.getText().toString());
        nameMap.put("checkEle", checkEle.isChecked());
        nameMap.put("checkGas", checkGas.isChecked());
        nameMap.put("checkMan", checkMan.isChecked());
        nameMap.put("checkWater", checkWater.isChecked());
        nameMap.put("city", sp.getSelectedItem().toString());

        nameMap.put("id", etID.getText().toString());
        nameMap.put("location", sp2.getSelectedItem().toString());
        nameMap.put("name", edtName.getText().toString());
        nameMap.put("payDay", Integer.parseInt(edtPayDay.getText().toString()));
        nameMap.put("period", Integer.parseInt(edtPayPeriod.getText().toString()));
        nameMap.put("phone", etPhone.getText().toString());
        if (!etRent.getText().toString().trim().equals(""))
            nameMap.put("rent", Integer.parseInt(etRent.getText().toString()));
        if (!(txEndDate.getText().toString().equals("請選擇日期")))
            nameMap.put("endDate", txEndDate.getText().toString());
        if (!(txSignDate.getText().toString().equals("請選擇日期")))
            nameMap.put("signDate", txSignDate.getText().toString());
        if (!(txStartDate.getText().toString().equals("請選擇日期")))
            nameMap.put("startDate", txStartDate.getText().toString());
        nameMap.put("tel", etTel.getText().toString());
        mDatabase.updateChildren(nameMap);
//                    , ((…

        //同步更新House.tenentID的資料
        mDatabase = FirebaseDatabase.getInstance().getReference("House");
        mDatabase = mDatabase.child(intent_house_id);
        nameMap = new HashMap<String, Object>();
        nameMap.put("tenantName", edtName.getText().toString().trim());
        nameMap.put("tenantPhone", etPhone.getText().toString().trim());
        mDatabase.updateChildren(nameMap);

        DetailActivity.intent_user_Name=edtName.getText().toString();
        finish();
    }

    private void addTenant() {
//            Log.d("anddtenant=",txSignDate.getText().toString().equals("請選擇日期")+"");
//        Log.d("anddtenant=","a"+txSignDate.getText().toString()+"a");
        if (etPhone.getText().toString().trim().equals("")
                ||edtName.getText().toString().trim().equals("")
                ||etID.getText().toString().trim().equals(""))
            Toast.makeText(this,"有*的欄位不可空白",Toast.LENGTH_SHORT).show();
        else {
            String tempRent = etRent.getText().toString().trim();
            String tempPayPeriod = edtPayPeriod.getText().toString().trim();
            String tempPayDay = edtPayDay.getText().toString().trim();
//            if (tempRent.equals("")) tempRent = "0";
//            if (tempPayPeriod.equals("")) tempPayPeriod = "0";
//            if (tempPayDay.equals("")) tempPayDay = "0";
            Log.d("edtPayPeriod=","1"+edtPayPeriod.getText().toString());
            Log.d("edtPayDay=","2"+edtPayDay.getText().toString());
            Log.d("etRent=","3"+etRent.getText().toString()+"3");
            Log.d("etRent=","3"+etRent.getText().toString().equals(""));
            Log.d("etRent=","3"+(etRent.getText().toString()==null));
            Tenant tenant = new Tenant(
                    intent_user_ID//landlordid
                    , intent_house_id
                    , edtName.getText().toString().trim()
                    , etID.getText().toString().trim()//房客身份證號
                    , etAddrNumber.getText().toString().trim()
                    , etTel.getText().toString().trim()
                    , etPhone.getText().toString().trim()
                    , ((txSignDate.getText().toString().equals("請選擇日期")))?null:txSignDate.getText().toString().trim()
                    , ((txStartDate.getText().toString().equals("請選擇日期")))?null:txStartDate.getText().toString().trim()
                    , ((txEndDate.getText().toString().equals("請選擇日期")))?null:txEndDate.getText().toString().trim()

                    ,(tempRent.equals(""))?null:Integer.parseInt(tempRent)
                    ,(tempPayPeriod.equals(""))?null:Integer.parseInt(tempPayPeriod)
                    ,(tempPayDay.equals(""))?null:Integer.parseInt(tempPayDay)

                    , checkWater.isChecked()
                    , checkEle.isChecked()
                    , checkGas.isChecked()
                    , checkMan.isChecked()
                    ,sp.getSelectedItem().toString()
                    ,sp2.getSelectedItem().toString()
            );

            DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("Tenant");
//            mDatabase.push().setValue(tenant);
            //先取得產生tenentID
            String postID = mDatabase.push().getKey();;
            mDatabase.child(postID).setValue(tenant);
            Log.d("tenaidtenid=",postID);
            Log.d("tenaidtenidhouse=",etPhone.getText().toString().trim());

            //更新House.tenentID的資料
            mDatabase = FirebaseDatabase.getInstance().getReference("House");
            mDatabase = mDatabase.child(intent_house_id);
            Map<String, Object> nameMap = new HashMap<String, Object>();
            nameMap.put("tenantID", postID);
            nameMap.put("tenantName", edtName.getText().toString().trim());
            nameMap.put("tenantPhone", etPhone.getText().toString().trim());
            mDatabase.updateChildren(nameMap);
            //=====================================
            DetailActivity.intent_tenant_id=postID;
            finish();

        }
    }

    //====================================================================
    public void addHouseCancel(View target) {
        finish();
    }
//=================================================================
private void setlisenter() {
    sp.setOnItemSelectedListener(selectListener);
    sp2.setOnItemSelectedListener(selectListener);
    txSignDate.setOnClickListener(dateBtnListener);
    txStartDate.setOnClickListener(dateBtnListener);
    txEndDate.setOnClickListener(dateBtnListener);
    btnSubmit.setOnClickListener(submitListener);

}
    View.OnClickListener submitListener= new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if (DetailActivity.intent_tenant_id==null)
            addTenant();
            else
                editTenant();

        }
    };
    //===========================================================
    View.OnClickListener dateBtnListener = new View.OnClickListener() {
        @Override
        public void onClick(final View view) {
            Date now = new Date();
            DatePickerDialog dpd=new DatePickerDialog(AddTenantActivity.this, new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                    String input = i +"-"+(i1+1)+"-"+("0"+i2).substring(("0"+i2).length()-2);

                    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                    Date t = null;
                    try{
                        t = formatter.parse(input);
                    }catch(ParseException e){
                    }
                    ((Button)view).setText(input);
                }
            },now.getYear()+1900,now.getMonth(),now.getDay()); //畫面出來預設值  2017,12,05
            dpd.show();
        }
    };

//    View.OnClickListener dateListener = new View.OnClickListener() {
//        @Override
//        public void onClick(View view) {
//            if (view==btnSignDate) tx=txSignDate;
//            if (view==imgStartDate) tx=txStartDate;
//            if (view==imgEndDate) tx=txEndDate;
//            Date now = new Date();
//            DatePickerDialog dpd=new DatePickerDialog(AddTenantActivity.this, new DatePickerDialog.OnDateSetListener() {
//                @Override
//                public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
//                    String input = i +"-"+(i1+1)+"-"+("0"+i2).substring(("0"+i2).length()-2);
//
//                    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
//                    Date t = null;
//                    try{
//                        t = formatter.parse(input);
//                    }catch(ParseException e){
//                    }
//                    tx.setText(input);
//                }
//            },now.getYear()+1900,now.getMonth(),now.getDay()); //畫面出來預設值  2017,12,05
//            dpd.show();
//        }
//    };

}

