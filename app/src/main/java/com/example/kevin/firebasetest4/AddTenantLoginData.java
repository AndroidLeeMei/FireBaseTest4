package com.example.kevin.firebasetest4;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class AddTenantLoginData extends AppCompatActivity {

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



//        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("Landlord").child(intent_login_ID);
//        mDatabase.addValueEventListener(valueEventListener );


//        String ,intent_login_user_type,,intent_login_ID;


        editEmail.setText(intent_login_user);
        editPassword.setText(intent_login_password);
        editName.setText(intent_user_Name);
        editPhone.setText(intent_user_Phone);
        editBank.setText(intent_user_Bank);
        editBankNumber.setText(intent_user_Bank_Number);
        edtAccunt.setText(intent_user_Account);
        editPasswordCheck.setText(intent_check_Password);


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
        nameMap.put("passwordCheck", editPasswordCheck.getText().toString() );
        mDatabase.child(intent_login_ID).updateChildren(nameMap);

        Toast.makeText(this,"資料已異動完成",Toast.LENGTH_SHORT).show();

    }

    public void btnCancel(View target){
        finish();
    }
}