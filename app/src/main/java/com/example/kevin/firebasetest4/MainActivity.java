package com.example.kevin.firebasetest4;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.kevin.firebasetest4.FireBase.LandlordData;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    //加入偏好設定
    public static final String PREF="HOUSE_SYSTEM_PREF";
    public static final String PREF_LOGIN_USER="Login_User";
    public static final String PREF_LOGIN_TYPE="Login_User_Type";
    public static final String PREF_LOGIN_PASSWORID="Login_Password";
    public static final String PREF_LOGIN_NAME="Login_Name";

    //使用者選擇匯入房東資料

    private DatabaseReference mDatabase;

    String pref_login_user,pref_login_name,pref_login_password,pref_login_user_type,pref_login_ID;
    String login_user_Phone,login_user_Bank,login_user_Bank_Number,login_user_Account;
    String login_check_Password;

    //房東設定的房客import密碼
    String tenant_login_check_Password;


//    @Override//儲存SharedPreferences的資料的資料
//    protected void onPause() {
//        super.onPause();
//        Log.d("SharedPreferences","Bmi.onPause");
//        //Save user preferences. use Editor object to make changes.
//        SharedPreferences settings=getSharedPreferences(PREF,0);
//        settings.edit().putString(PREF_LOGIN_USER,"abc@abc.com.tw").commit();
//        settings.edit().putString(PREF_LOGIN_TYPE,"landlord").commit();
//        settings.edit().putString(PREF_LOGIN_PASSWORID,"123").commit();
//        settings.edit().putString(PREF_LOGIN_ID,pref_login_ID).commit();
//
//        settings.edit().putString(PREF_USER_NAME,login_user_Name).commit();
//        settings.edit().putString(PREF_USER_PHONE,login_user_Phone).commit();
//        settings.edit().putString(PREF_USER_BANK,login_user_Bank).commit();
//        settings.edit().putString(PREF_USER_BANK_NUMBER,login_user_Bank_Number).commit();
//        settings.edit().putString(PREF_USER_ACCUNT,login_user_Account).commit();
//    }


    @Override
    protected void onResume() {
        super.onResume();
//        restorePrefs();
//        Log.d("restorePrefs,user=",pref_login_user);
//        Log.d("restorePrefs,type=",pref_login_user_type);
//        Log.d("restorePrefs,passworid=",pref_login_password);
    }
    //Restore preferences,取得SharedPreferences的資料
    private void restorePrefs(){
        SharedPreferences settings=getSharedPreferences(PREF,0);
        pref_login_user=settings.getString(PREF_LOGIN_USER,"");
        pref_login_user_type=settings.getString(PREF_LOGIN_TYPE,"");
        pref_login_password=settings.getString(PREF_LOGIN_PASSWORID,"");
//        房客資料
        pref_login_user="kevin@abc.com";
//        pref_login_user_type="tenant";
        pref_login_password="1qaz2wsx";
        pref_login_name="Kevin";
        //房客要import資料的的驗證密碼
        tenant_login_check_Password="22221";
        login_user_Phone="0988";

        Log.d("restorePrefsTT=","phone="+login_user_Phone);

//        //房東資料
//        pref_login_user="LeeMei@abc.com";
//        pref_login_user_type="landlord";
//        pref_login_password="1qaz2wsx";
//        pref_login_name="LeeMei";


//        pref_login_ID=settings.getString(PREF_LOGIN_ID,"");
//        login_user_Name=settings.getString(PREF_LOGIN_ID,"");
//        login_user_Phone=settings.getString(PREF_LOGIN_ID,"");
//        login_user_Bank=settings.getString(PREF_LOGIN_ID,"");
//        login_user_Bank_Number=settings.getString(PREF_LOGIN_ID,"");
//        login_user_Account=settings.getString(PREF_LOGIN_ID,"");
    }

    public void login(String userType){
        Log.d("restorePrefs==","name="+pref_login_name);
        Log.d("restorePrefsTT=","Phone="+login_user_Phone);

        Intent intent = new Intent();
        intent.setClass(this, LanlordActivity.class);
        Bundle bundle = new Bundle();

        bundle.putString("PREF_LOGIN_USER", pref_login_user);
//        bundle.putString("PREF_LOGIN_USER_TYPE", pref_login_user_type);
        bundle.putString("PREF_LOGIN_USER_TYPE", userType);
        bundle.putString("PREF_LOGIN_PASSWORD", pref_login_password);

        bundle.putString("FIREDB_LOGIN_NAME", pref_login_name);

        bundle.putString("FIREDB_LOGIN_ID", pref_login_ID);
        bundle.putString("FIREDB_USER_PHONE", login_user_Phone);
        bundle.putString("FIREDB_USER_BANK", login_user_Bank);
        bundle.putString("FIREDB_USER_BANK_NUMBER", login_user_Bank_Number);
        bundle.putString("FIREDB_USER_ACCUNT", login_user_Account);
        bundle.putString("FIREDB_USER_CHECK_PASSWORD", login_check_Password);
        bundle.putString("PREF_TENANT_CHECK_PASSWORD", tenant_login_check_Password);

        Log.d("restorePrefs==","name="+pref_login_name);
        Log.d("restorePrefsTT=","Phone="+login_user_Phone);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    Button btnLoginLandlord,btnLogintenant;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        restorePrefs();

        btnLoginLandlord=(Button)findViewById(R.id.btnLoginLandlord) ;
        btnLogintenant=(Button)findViewById(R.id.bunLoginTenant) ;
        btnLoginLandlord.setOnClickListener(listenerLogin);
        btnLogintenant.setOnClickListener(listenerLogin);

        //        //將login成功的使用者資料新增至firebase並取得該使用者的ID,=========================
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("Landlord");
        mDatabase.addValueEventListener(valueEventListener );


//=======================================================================

//        mDatabase = FirebaseDatabase.getInstance().getReference("Management");
////                        mDatabase = mDatabase.child(TenantActivity.listPriceKey.get(mGroupPosition));
//        mDatabase = mDatabase.child("-L-AoJRx0PRgDNbPPedX");
//        mDatabase.removeValue();

//        mDatabase.addValueEventListener(valueEventListener);
//        mDatabase.addChildEventListener(childEventListener);
        //ListenerForSingleValueEvent( originalListener) 解除監聽

        //add方法1
//        mDatabase = mDatabase.child("0");
//        User user = new User("3Amy", 3224);
//        mDatabase.setValue(user);

        //add方法1
//        mDatabase = mDatabase.child("users").child("0");
//        User user = new User("2Amy", 224);
//        mDatabase.setValue(user);
        //add方法2,用 push( ) 新增一個擁有唯一ID的節點資料
//        mDatabase = mDatabase.child("users");
//        User user = new User("Addy", 20);
//        mDatabase.push().setValue(user);

        //update,只更新姓名,  setValue:沒有指定資料的節點也會被覆蓋掉
//        mDatabase = FirebaseDatabase.getInstance().getReference("Landlord");
//        mDatabase = mDatabase.child("-L-fl1E7Km7eWnuVm_be");
//        Map<String, Object> nameMap = new HashMap<String, Object>();
//        nameMap.put("email", "aaa@abc");
//        mDatabase.updateChildren(nameMap);

        //setValue:沒有指定資料的節點也會被覆蓋掉
//        mDatabase = FirebaseDatabase.getInstance().getReference("Landlord");
//        mDatabase = mDatabase.child("-L-fl1E7Km7eWnuVm_be");
//        Map<String, Object> nameMap = new HashMap<String, Object>();
//        nameMap.put("name", "name");
//        mDatabase.setValue(nameMap);


        //用 removeValue( ) 刪除資料,或是setValue(null);updateChildren(null);
//        mDatabase = mDatabase.child("users").child("-KzcwTXekOYB1ltZbLY4");
//        mDatabase.removeValue();
    }

    View.OnClickListener listenerLogin= new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Log.d("login==",((Button)view).getText()+"");
            Log.d("login==",((Button)view).getId()+"");
            if (((Button)view).getText().equals("房東"))
                login("landlord");
            else if (((Button)view).getText().equals("房客"))
                login("tenant");
        }
    };

//  ====  一次回傳所有子節點資料…=================================
    ValueEventListener valueEventListener = new ValueEventListener() {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
            Log.d("FireBaseTraining", " snapshot.getValue() = " + dataSnapshot.getValue());
//            pref_login_user_type="tenant";
            Boolean addLanlordFlag=true;

            if (dataSnapshot.getValue()==null){//若firebase沒有Lanlord節點時
                LandlordData landlordData = new LandlordData(pref_login_user,pref_login_user_type,pref_login_password,pref_login_user, login_user_Phone, "", "",  "", "");

                mDatabase = FirebaseDatabase.getInstance().getReference("Landlord");
//                String postID = mDatabase.push().getKey();;
//                mDatabase.child(postID).setValue(landlordData);
                mDatabase.push().setValue(landlordData);
//                Log.d("FireBaseTraining", "第一次新增Lanlord,新增資料,key=");
            }else {//若firebase有Lanlord節點時,則檢查loginUser,loginPossword是否有存在
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    LandlordData landlord = ds.getValue(LandlordData.class);
                    if (landlord.getEmail().equals(pref_login_user)
                            && landlord.getType().equals(pref_login_user_type)) //資料已經有存在
                    {
                        //取出UserID
                        pref_login_ID = ds.getKey();
                        pref_login_name=landlord.getName();
                        login_user_Phone=landlord.getPhone();
                        login_user_Bank=landlord.getBank();
                        login_user_Bank_Number=landlord.getBankNumber();
                        login_user_Account=landlord.getBankNumber();
                        login_check_Password=landlord.getPasswordCheck();

                        Log.d("FireBaseTraining", "資料已經有存在,userid=" + pref_login_ID);
                        addLanlordFlag=false;
                        break;
                    }
                }
                if (addLanlordFlag){//有Landlord節點,但該使用者沒有資料時,新增該使用者
                    Log.d("pref_login_user1==",pref_login_user);
                    LandlordData landlordData = new LandlordData(pref_login_name,pref_login_user_type,pref_login_password,pref_login_user,login_user_Phone,  "", "", "", "");

                    Log.d("FireBaseTraining", "有Landlord節點,新增資料");
                    DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("Landlord");
                    mDatabase.push().setValue(landlordData);
//                    String postID = mDatabase.push().getKey();;
//                    mDatabase.child(postID).setValue(landlordData);
                }


            }

            }


        @Override
        public void onCancelled(DatabaseError databaseError) {
            // Getting Post failed, log a message
            Log.w("realdb", "loadPost:onCancelled", databaseError.toException());
            // ...
        }
    };
//    =================================================================================?…
    //此方法只會回傳有發生資料變更的單一子節點資料，而非
    ChildEventListener childEventListener=new ChildEventListener(){

        @Override
        public void onChildAdded(DataSnapshot dataSnapshot, String s) {
            User user = dataSnapshot.getValue(User.class);
//            Log.d("FireBaseTraining", "Added : name = " + user.getName() + " , Age = " + user.getAge());
//            Log.d("FireBaseTraining", "Added : name = ");
        }

        @Override
        public void onChildChanged(DataSnapshot dataSnapshot, String s) {
//            User user = dataSnapshot.getValue(User.class);
            Log.d("FireBaseTraining", "Changed : name = ");
        }

        @Override
        public void onChildRemoved(DataSnapshot dataSnapshot) {
//            User user = dataSnapshot.getValue(User.class);
//            Log.d("FireBaseTraining", "Removed : name = " + user.getName() + " , Age = " + user.getAge());
            Log.d("FireBaseTraining", "Removed : name = ");
        }

        @Override
        public void onChildMoved(DataSnapshot dataSnapshot, String s) {
//            User user = dataSnapshot.getValue(User.class);
//            Log.d("FireBaseTraining", "Moved : name = " + user.getName() + " , Age = " + user.getAge());
            Log.d("FireBaseTraining", "Moved : name = ");
        }

        @Override
        public void onCancelled(DatabaseError databaseError) {

        }
    };



}
