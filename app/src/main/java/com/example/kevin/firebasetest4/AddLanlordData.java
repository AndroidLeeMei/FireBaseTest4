package com.example.kevin.firebasetest4;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.kevin.firebasetest4.FireBase.LandlordData;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class AddLanlordData extends AppCompatActivity {

    EditText editName,editPassword,editEmail,editPhone,editBank,editBankNumber,edtAccunt,editPasswordCheck;
    //加入偏好設定
//    private static String pref_login_user,pref_login_password,pref_login_user_type,pref_login_ID;
    //Restore preferences,取得SharedPreferences的資料

    //取得 intent extra資料
    private String intent_login_user,intent_login_user_type,intent_login_password,intent_login_ID;
    private String  intent_check_Password,intent_user_Name,intent_user_Phone,intent_user_Bank,intent_user_Bank_Number,intent_user_Account;
    private void restoreIntent(){
//        SharedPreferences settings=getSharedPreferences(MainActivity.PREF,0);
//        pref_login_user=settings.getString(MainActivity.PREF_LOGIN_USER,"");
//        pref_login_password=settings.getString(MainActivity.PREF_LOGIN_PASSWORID,"");
//        pref_login_user_type=settings.getString(MainActivity.PREF_LOGIN_TYPE,"");
//        pref_login_ID=settings.getString(MainActivity.PREF_LOGIN_ID,"");

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        intent_login_user = bundle.getString("PREF_LOGIN_USER");
        intent_login_user_type = bundle.getString("PREF_LOGIN_USER_TYPE");
        intent_login_password = bundle.getString("PREF_LOGIN_PASSWORD");
        intent_login_ID = bundle.getString("FIREDB_LOGIN_ID");

        intent_user_Name = bundle.getString("FIREDB_USER_NAME");
        intent_user_Phone = bundle.getString("FIREDB_USER_PHONE");
        intent_user_Bank = bundle.getString("FIREDB_USER_BANK");
        intent_user_Bank_Number = bundle.getString("FIREDB_USER_BANK_NUMBER");
        intent_user_Account = bundle.getString("FIREDB_USER_ACCUNT");
        intent_check_Password = bundle.getString("FIREDB_USER_CHECK_PASSWORD");

    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lanlord_data_edit);
        findViews();
        restoreIntent();

        Log.d("FireBaseTraining,user=",intent_user_Name+"" );
        Log.d("FireBaseTraining,pas",intent_user_Phone+"" );
        Log.d("FireBaseTraining,type",intent_user_Bank+"" );
        Log.d("FireBaseTraining,id",intent_user_Bank_Number+"" );
        Log.d("FireBaseTraining,id",intent_user_Account +"");
        Log.d("FireBaseTraining,user=",intent_login_user+"" );
        Log.d("FireBaseTraining,user=",intent_login_user_type+"" );
        Log.d("FireBaseTraining,user=",intent_login_password +"");
        Log.d("FireBaseTraining,user=",intent_login_ID+"" );



        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("Landlord").child(intent_login_ID);
        mDatabase.addValueEventListener(valueEventListener );


//        String ,intent_login_user_type,,intent_login_ID;


//        editEmail.setText(intent_login_user);
//        editPassword.setText(intent_login_password);
//        editName.setText(intent_user_Name);
//        editPhone.setText(intent_user_Phone);
//        editBank.setText(intent_user_Bank);
//        editBankNumber.setText(intent_user_Bank_Number);
//        edtAccunt.setText(intent_user_Account);
//        editPasswordCheck.setText(intent_check_Password);


    }

//    ValueEventListener valueEventListener = new ValueEventListener() {
//        @Override
//        public void onDataChange(DataSnapshot dataSnapshot) {
//            for (DataSnapshot ds : dataSnapshot.getChildren()) {
//                Landlord landlord=new Landlord();
////                landlord=ds.getValue(new Landlord());
//                Log.d("FireBaseTraining", "ds.getKey()=" + ds.getKey());
//                Log.d("FireBaseTraining", "ds.getKey()=" + ds.getValue().toString());
////                Log.d("FireBaseTraining",ds.child("email").getValue().toString());
////        Map<String, Object> nameMap = new HashMap<String, Object>();
//////        nameMap.put("name", "name");
////                nameMap=ds.;
////                LandlordData landlord = ds.getValue(LandlordData.class);
//
//                    //取出UserID
////                    pref_login_ID = ds.getKey();
////                    Log.d("FireBaseTraining", "資料已經有存在,userid=" + pref_login_ID);
////                    addLanlordFlag=false;
////                    break;
//
//            }
//        }
//
//        @Override
//        public void onCancelled(DatabaseError databaseError) {
//
//        }
//    };

    private void findViews(){
        editName=(EditText)findViewById(R.id.edtName);
        editPassword=(EditText)findViewById(R.id.edtPassWord1);
        editEmail=(EditText)findViewById(R.id.edtEmail);

        editPhone=(EditText)findViewById(R.id.edtPhone);
        editBank=(EditText)findViewById(R.id.edtBank);
        editBankNumber=(EditText)findViewById(R.id.edtBankNumber);
        edtAccunt=(EditText)findViewById(R.id.edtAccount);
        editPasswordCheck=(EditText)findViewById(R.id.edtPassWord);
    }

    public void btnSubmit(View target){
        Log.d("addLondlard",editName+"");
        Log.d("addLondlard",editEmail+"");
//        Log.d("addLondlard",editName.getText().toString());
//        LandlordData landlordData =new LandlordData(
//                editName.getText().toString()    //name
//                ,pref_login_user_type            //type
//                ,pref_login_password
//                ,pref_login_user
//                ,editPhone.getText().toString()
//                ,editBank.getText().toString()
//                ,editBankNumber.getText().toString()
//                ,edtAccunt.getText().toString()
//                ,editPasswordCheck.getText().toString());
// , String phone, String bank, String bankNumber, String accunt, String passwordCheck) {

//        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("Landlord");
//        mDatabase.push().setValue(landlordData);

        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("Landlord");
//        mDatabase = mDatabase.child(pref_login_ID);
        Map<String, Object> nameMap = new HashMap<String, Object>();
        nameMap.put("name", editName.getText().toString() );
        nameMap.put("phone", editPhone.getText().toString() );
        nameMap.put("bank", editBank.getText().toString() );
        nameMap.put("bankNumber", editBankNumber.getText().toString() );
        nameMap.put("accunt", edtAccunt.getText().toString() );
        nameMap.put("passwordCheck", editPasswordCheck.getText().toString().trim() );
        mDatabase.child(intent_login_ID).updateChildren(nameMap);

        //房東的房屋驗証密碼在按返回到landlord.activity那一頁時,會自動更新house.landlordPassword
        LanlordActivity.intent_check_Password=editPasswordCheck.getText().toString().trim();
        LanlordActivity.myadapter.notifyDataSetChanged();
//        Log.d("intent_login_ID0",intent_login_ID);
//        Log.d("intent_login_ID1",editPasswordCheck.getText().toString());

        //更新House.landlord Password的資料
//        mDatabase = FirebaseDatabase.getInstance().getReference("House");
//        Log.d("AAA==",mDatabase.getDatabase()+"");

//        mDatabase.addValueEventListener(valueEventListener);
//        mDatabase = mDatabase.child(intent_house_id);
//        Map<String, Object> nameMap = new HashMap<String, Object>();
//        nameMap.put("tenantID", postID);
//        nameMap.put("tenantName", edtName.getText().toString().trim());
//        nameMap.put("tenantPhone", etPhone.getText().toString().trim());
//        mDatabase.updateChildren(nameMap);

        Toast.makeText(this,"資料已異動完成",Toast.LENGTH_SHORT).show();

    }

    public void btnCancel(View target){
        finish();
    }


    private ValueEventListener valueEventListener = new ValueEventListener() {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
            LandlordData landlordData=dataSnapshot.getValue(LandlordData.class);

            Log.d("intent_housegetAccunt=",landlordData.getAccunt());
            editEmail.setText(intent_login_user);
            editPassword.setText(intent_login_password);
            editName.setText(intent_user_Name);
            editPhone.setText(landlordData.getPhone());
            editBank.setText(landlordData.getBank());
            editBankNumber.setText(landlordData.getBankNumber());
            edtAccunt.setText(landlordData.getAccunt());
            editPasswordCheck.setText(landlordData.getPasswordCheck());
//            Log.d("intent_house_id=1",intent_house_landlord_id);
//
//            alrAddr=new ArrayList<>();
//
//
//            alrAddr.add(" " + intent_house_title);
//            alrAddr.add(" " + intent_house_addr);
//            alrAddr.add(" 房東姓名: " +landlordData.getName());
//            alrAddr.add(" 房東電話: " + landlordData.getPhone());
//            alrAddr.add(" 匯款銀行: " + landlordData.getBank()+"(" + landlordData.getBankNumber() + ")");
//            alrAddr.add(" 匯款銀行: " + landlordData.getAccunt());
//
////
////
////                if ((houseData == null) || houseData.getIdentity() == null)
////                    alrAddr.add(" 租客 : 無");
////                else
////                    alrAddr.add(" 租客 : " + houseData.getTenantName());
//            myadapter.notifyDataSetChanged();
//            }
        }
        @Override
        public void onCancelled(DatabaseError databaseError) {}
    };

}