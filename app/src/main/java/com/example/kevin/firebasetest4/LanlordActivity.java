package com.example.kevin.firebasetest4;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.example.kevin.firebasetest4.FireBase.HouseData;
import com.example.kevin.firebasetest4.FireBase.LandlordData;
import com.example.kevin.firebasetest4.FireBase.Tenant;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class LanlordActivity extends AppCompatActivity implements ListView.OnItemClickListener {

    TextView txtlandlord, txtAlarm;
    String landordID = "";
    static Myadapter myadapter;
//    static String USER_ID = "J3455800020";
//    static String USER_TYPE = "lanlord";
    ArrayList<String> alrAddr = new ArrayList<>();
    ArrayList<Boolean> alrDelKey = new ArrayList<>();//記錄刪除鈕是否可被看見
    ArrayList<Boolean> alrMessflag = new ArrayList<>();//記錄聊天室是否可被看見
    ArrayList<String> alrTentNote = new ArrayList<>();
    ArrayList<String> alrUserType = new ArrayList<>();
    ArrayList<String> alrTenantID=new ArrayList<>();
    ArrayList<String> alrHouseCreateUser=new ArrayList<>();
    ArrayList<String> alrTenantName=new ArrayList<>();
    ArrayList<String> alrHouseLanlordID=new ArrayList<>();

    ArrayList<String> alrHouseKey = new ArrayList<>();
//    private DatabaseReference mDatabase;

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("AAA","onResume");

        String userTypte=(intent_login_user_type.equals("landlord"))?"房東":"房客";
        txtlandlord.setText(userTypte + ":"+intent_user_Name +"歡迎使用本系統!");

        myadapter = new Myadapter(this);
        listView.setAdapter(myadapter);//-L0-9i5bWu6BcwiKwV2K

        //1.找出由pref_login_ID(自己)建立的房屋資料,createUser=pref_login_ID
        //2.由房東建立的資料,但房客有通過驗證,匯入該筆房屋資料 tenantID=pref_login_ID
        reference_contacts = FirebaseDatabase.getInstance().getReference("House");
        reference_contacts.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                alrHouseKey = new ArrayList<>();
                alrTentNote=new ArrayList<String>();
                alrAddr=new ArrayList<String>();
                alrDelKey=new ArrayList<Boolean>();
                alrUserType=new ArrayList<String>();
                alrTenantID=new ArrayList<String>();
                alrTenantName=new ArrayList<>();
                alrHouseLanlordID=new ArrayList<String>();
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    HouseData house = ds.getValue(HouseData.class);

                    Log.d("restorePrefsTT=","=" +tenant_login_check_Password);
                    Log.d("restorePrefsTT=","=" +intent_user_Phone);
                    if (house.getCreateUser().equals(intent_user_ID)//case 1. 由pref_login_ID(自己)建立的房屋資料
                            || ((!(house.getLandlordPassword()==null))
                            &&(!(house.getTenantPhone()==null))
                            &&house.getTenantPhone().equals(intent_user_Phone)
                            &&house.getLandlordPassword().equals(tenant_login_check_Password)
                    )){  //2.由房東建立的資料,但房客有通過驗證,匯入該筆房屋資料
                        if (house.getCreateUser().equals(intent_user_ID)){
//                            Log.d("restorePrefsTT","~~~~~"+ds.getKey()+"~~~~~");
//                            Log.d("restorePrefsTT=",intent_user_ID+"");
//                            Log.d("restorePrefsTT=","house.getTenantID()="+house.getTenantID());
//                            Log.d("restorePrefsTT=","createequal="+house.getCreateUser().equals(intent_user_ID));

                            Map<String, Object> nameMap = new HashMap<>();
                            nameMap.put("landlordPassword", intent_check_Password);
//                            nameMap.put("tenantName", int);
//                            nameMap.put("tenantPhone", etPhone.getText().toString().trim());
                            reference_contacts.child(ds.getKey()).updateChildren(nameMap);
                        }

                        //2.由房東建立的資料,但房客有通過驗證,匯入該筆房屋資料
//                        if ((!(house.getLandlordPassword()==null)) &&house.getTenantPhone().equals(intent_user_Phone) )
//                        {
////                        tenant_login_check_Password
//                        }

                        if (house.getCreateUser().equals(intent_user_ID)) {
                            alrDelKey.add(true);  //只有自已才能刪除由自已建立的房屋資料
                            alrUserType.add("create");
                        }else {
                            alrDelKey.add(false);
                            alrUserType.add("tenant");
                        }

                        if (house.getCreateUser().equals(intent_user_ID)
                                &&house.getIdentity()=="landlord"&&!(house.getTenantID()==null)){
                            alrMessflag.add(true);  //該使用者是房東而且目前該房屋有房客
                        }
                        else if ((!(house.getTenantID()==null))
                                &&house.getTenantID().equals(intent_user_ID)
                                &&(!(house.getCreateUser().equals(intent_user_ID)))) //房客可看到聊天室
                            alrMessflag.add(true);//該使用者是房客,但該筆資料的建立者不是房客
                        else
                            alrMessflag.add(false);

//                        Log.d("tenantName","tenid="+house.getTenantName());
                        alrTenantName.add((house.getTenantName()==null)?"":house.getTenantName());
                        alrTenantID.add(house.getTenantID());
                        alrAddr.add("房屋代號: "+house.getTitle() );
                        alrTentNote.add("房屋地址: " + house.getCity()+house.getLocation()+"\t"+house.getAddr());
                        alrHouseKey.add(ds.getKey());
                        alrHouseCreateUser.add(house.getCreateUser());
                        alrHouseLanlordID.add(house.getLandlordID());
                    }
                }
                myadapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    //Restore preferences,取得SharedPreferences的資料
    private void restorePrefs(){
//        SharedPreferences settings=getSharedPreferences(MainActivity.PREF,0);
//        pref_login_user=settings.getString(MainActivity.PREF_LOGIN_USER,"");
//        pref_login_password=settings.getString(MainActivity.PREF_LOGIN_PASSWORID,"");
//        pref_login_user_type=settings.getString(MainActivity.PREF_LOGIN_TYPE,"");
//        pref_login_ID=settings.getString(MainActivity.PREF_LOGIN_ID,"");

    }

    //取得 intent extra資料
    public static String intent_login_user,intent_login_user_type,intent_login_password,intent_user_ID;
    public static String  intent_user_Name,intent_user_Phone,intent_user_Bank,intent_user_Bank_Number,intent_user_Account;
    public static String intent_check_Password,tenant_login_check_Password;
    public void restoreIntent(){
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();

        intent_login_user = bundle.getString("PREF_LOGIN_USER");
        intent_login_user_type = bundle.getString("PREF_LOGIN_USER_TYPE");
        intent_login_password = bundle.getString("PREF_LOGIN_PASSWORD");
        intent_user_ID = bundle.getString("FIREDB_LOGIN_ID");
        Log.d("AAABB=get",bundle.getString("PREF_LOGIN_USER_TYPE"));
        Log.d("AAABB=get",intent_login_user_type);
        intent_user_Phone = bundle.getString("FIREDB_USER_PHONE");
        intent_user_Bank = bundle.getString("FIREDB_USER_BANK");
        intent_user_Bank_Number = bundle.getString("FIREDB_USER_BANK_NUMBER");
        intent_user_Account = bundle.getString("FIREDB_USER_ACCUNT");
        intent_check_Password = bundle.getString("FIREDB_USER_CHECK_PASSWORD");

        tenant_login_check_Password= bundle.getString("PREF_TENANT_CHECK_PASSWORD");
        intent_user_Name = bundle.getString("FIREDB_LOGIN_NAME");

//        Log.d("pref_login_user-2=a=",intent_user_Name+"");
//        Log.d("pref_login_user-2=a=",intent_user_Name+"");
    }
    ListView listView;
    DatabaseReference reference_contacts;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lanlord);

        listView = (ListView) findViewById(R.id.list);
        listView.setOnItemClickListener(this);
//        restorePrefs();
        restoreIntent();


        txtlandlord = (TextView) findViewById(R.id.txtlandlord);

//        String userTypte=(intent_login_user_type.equals("landlord"))?"房東":"房客";
//        txtlandlord.setText(userTypte + ":"+intent_user_Name +"歡迎使用本系統!");
//
//        myadapter = new Myadapter(this);
//        listView.setAdapter(myadapter);//-L0-9i5bWu6BcwiKwV2K
//
//        //1.找出由pref_login_ID(自己)建立的房屋資料,createUser=pref_login_ID
//        //2.由房東建立的資料,但房客有通過驗證,匯入該筆房屋資料 tenantID=pref_login_ID
//        reference_contacts = FirebaseDatabase.getInstance().getReference("House");
//        reference_contacts.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                alrHouseKey = new ArrayList<>();
//                alrTentNote=new ArrayList<String>();
//                alrAddr=new ArrayList<String>();
//                alrDelKey=new ArrayList<Boolean>();
//                alrUserType=new ArrayList<String>();
//                alrTenantID=new ArrayList<String>();
//                alrTenantName=new ArrayList<>();
//                alrHouseLanlordID=new ArrayList<String>();
//                for (DataSnapshot ds : dataSnapshot.getChildren()) {
//                    HouseData house = ds.getValue(HouseData.class);
//
//                    Log.d("restorePrefsTT=","=" +tenant_login_check_Password);
//                    Log.d("restorePrefsTT=","=" +intent_user_Phone);
//                    if (house.getCreateUser().equals(intent_user_ID)//case 1. 由pref_login_ID(自己)建立的房屋資料
//                            || ((!(house.getLandlordPassword()==null))
//                                 &&(!(house.getTenantPhone()==null))
//                                 &&house.getTenantPhone().equals(intent_user_Phone)
//                                 &&house.getLandlordPassword().equals(tenant_login_check_Password)
//                       )){  //2.由房東建立的資料,但房客有通過驗證,匯入該筆房屋資料
//                        if (house.getCreateUser().equals(intent_user_ID)){
////                            Log.d("restorePrefsTT","~~~~~"+ds.getKey()+"~~~~~");
////                            Log.d("restorePrefsTT=",intent_user_ID+"");
////                            Log.d("restorePrefsTT=","house.getTenantID()="+house.getTenantID());
////                            Log.d("restorePrefsTT=","createequal="+house.getCreateUser().equals(intent_user_ID));
//
//                            Map<String, Object> nameMap = new HashMap<>();
//                            nameMap.put("landlordPassword", intent_check_Password);
////                            nameMap.put("tenantName", int);
////                            nameMap.put("tenantPhone", etPhone.getText().toString().trim());
//                            reference_contacts.child(ds.getKey()).updateChildren(nameMap);
//                        }
//
//                        //2.由房東建立的資料,但房客有通過驗證,匯入該筆房屋資料
////                        if ((!(house.getLandlordPassword()==null)) &&house.getTenantPhone().equals(intent_user_Phone) )
////                        {
//////                        tenant_login_check_Password
////                        }
//
//                        if (house.getCreateUser().equals(intent_user_ID)) {
//                            alrDelKey.add(true);  //只有自已才能刪除由自已建立的房屋資料
//                            alrUserType.add("create");
//                        }else {
//                            alrDelKey.add(false);
//                            alrUserType.add("tenant");
//                        }
//
//                        if (house.getCreateUser().equals(intent_user_ID)
//                                &&house.getIdentity()=="landlord"&&!(house.getTenantID()==null)){
//                            alrMessflag.add(true);  //該使用者是房東而且目前該房屋有房客
//                        }
//                        else if ((!(house.getTenantID()==null))
//                                &&house.getTenantID().equals(intent_user_ID)
//                                &&(!(house.getCreateUser().equals(intent_user_ID)))) //房客可看到聊天室
//                            alrMessflag.add(true);//該使用者是房客,但該筆資料的建立者不是房客
//                        else
//                            alrMessflag.add(false);
//
////                        Log.d("tenantName","tenid="+house.getTenantName());
//                        alrTenantName.add((house.getTenantName()==null)?"":house.getTenantName());
//                        alrTenantID.add(house.getTenantID());
//                        alrAddr.add("房屋代號: "+house.getTitle() );
//                        alrTentNote.add("房屋地址: " + house.getCity()+house.getLocation()+"\t"+house.getAddr());
//                        alrHouseKey.add(ds.getKey());
//                        alrHouseCreateUser.add(house.getCreateUser());
//                        alrHouseLanlordID.add(house.getLandlordID());
//                    }
//                }
//                myadapter.notifyDataSetChanged();
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//
//            }
//        });
    }

    public void addHouse(View target) {
        Intent intent = new Intent();
        intent.setClass(this, AddHouseDataActivity.class);
        Bundle bundle = new Bundle();

        bundle.putString("PREF_LOGIN_USER", intent_login_user);
        bundle.putString("PREF_LOGIN_USER_TYPE", intent_login_user_type);
        bundle.putString("PREF_LOGIN_PASSWORD", intent_login_password);
        bundle.putString("PREF_LOGIN_ID", intent_user_ID);
        bundle.putString("PREF_LOGIN_NAME", intent_user_Name);
        Log.d("pref_login_user-1=",intent_user_Name+"");
        intent.putExtras(bundle);
        startActivity(intent);
    }

    //implements  ListView.OnItemClickListener===================================
    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//        System.out.println("rowId = " + i);
//        Toast.makeText(this, "第" + i + "項", Toast.LENGTH_SHORT).show();

//        Log.d("fire==on=HouseKey=",alrHouseKey.get(i));
//        Log.d("fire==on=UserID=",FINAL_USER_ID);
        Intent intent = new Intent();
        intent.setClass(this, DetailActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("FIREDB_HOUSE_ID", alrHouseKey.get(i));
        bundle.putString("FIREDB_HOUSE_TITLE", alrAddr.get(i));
        bundle.putString("FIREDB_HOUSE_ADDR", alrTentNote.get(i));
        bundle.putString("FIREDB_TENANT_ID", alrTenantID.get(i));
        bundle.putString("FIREDB_TENANT_NAME", alrTenantName.get(i));

        bundle.putString("FIREDB_HOUSE_CRATE_USER", alrHouseCreateUser.get(i));
        bundle.putString("FIREDB_HOUSE_LANDLORD_ID", alrHouseLanlordID.get(i));

        bundle.putString("PREF_LOGIN_USER", intent_login_user);
        bundle.putString("PREF_LOGIN_USER_TYPE", intent_login_user_type);
        bundle.putString("PREF_LOGIN_PASSWORD", intent_login_password);
        Log.d("AAABBput",intent_login_user_type);

        bundle.putString("FIREDB_LOGIN_ID", intent_user_ID);
        bundle.putString("FIREDB_USER_NAME", intent_user_Name);
        bundle.putString("FIREDB_USER_PHONE", intent_user_Phone);
        bundle.putString("FIREDB_USER_BANK", intent_user_Bank);
        bundle.putString("FIREDB_USER_BANK_NUMBER", intent_user_Bank_Number);
        bundle.putString("FIREDB_USER_ACCUNT", intent_user_Account);
        bundle.putString("FIREDB_USER_CHECK_PASSWORD", intent_check_Password);

        intent.putExtras(bundle);
        startActivity(intent);
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
//implements  ListView.OnItemClickListener===================================//implements  ListView.OnItemClickListener===================================


    //==================OPTION MENU======================
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add("設定個人資料");
        return super.onCreateOptionsMenu(menu);
    }

    Class cs;
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (intent_login_user_type.equals("landlord"))   //登入者身份是房東,才需要設定房東的資料
            cs=AddLanlordData.class;
        else
            cs=AddTenantLoginData.class;

            Intent intent = new Intent();
            intent.setClass(this, cs);
            Bundle bundle = new Bundle();
            bundle.putString("PREF_LOGIN_USER", intent_login_user);
            bundle.putString("PREF_LOGIN_USER_TYPE", intent_login_user_type);
            bundle.putString("PREF_LOGIN_PASSWORD", intent_login_password);
            bundle.putString("FIREDB_LOGIN_ID", intent_user_ID);
            bundle.putString("FIREDB_USER_NAME", intent_user_Name);
            bundle.putString("FIREDB_USER_PHONE", intent_user_Phone);
            bundle.putString("FIREDB_USER_BANK", intent_user_Bank);
            bundle.putString("FIREDB_USER_BANK_NUMBER", intent_user_Bank_Number);
            bundle.putString("FIREDB_USER_ACCUNT", intent_user_Account);
            bundle.putString("FIREDB_USER_CHECK_PASSWORD", intent_check_Password);

            intent.putExtras(bundle);
            startActivity(intent);
//        }

        return super.onOptionsItemSelected(item);
    }


    //=================ADAPTER======================================
    class Myadapter extends BaseAdapter {
        private LayoutInflater myInflater;
        Context mContext;

        public Myadapter(Context context) {
            myInflater = LayoutInflater.from(context);
            this.mContext = context;
        }

        @Override
        public int getCount() {
            return alrAddr.size();
        }

        @Override
        public Object getItem(int i) {
            return alrAddr.size();
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        AlertDialog dialog; //讓自定Layout可有關閉功能
        View root;
        DatabaseReference mDatabase;
        @Override
        public View getView(int i, View convertView, ViewGroup viewGroup) {

            final int position = i;
            convertView = myInflater.inflate(R.layout.my_simple_item, null);
            TextView txTentNote = (TextView) convertView.findViewById(R.id.txTentNote);
            TextView txHouseAddr = (TextView) convertView.findViewById(R.id.txHouseAddr);
            ImageButton btnDel = convertView.findViewById(R.id.btnDel);
            ImageButton btnMessage = convertView.findViewById(R.id.btnMessage);
//            Button addTenant=convertView.findViewById(R.id.btnAddTenant) ;
//            Button delhouse=convertView.findViewById(R.id.btnDelhouse) ;
//            txHouseAddr.setText(alrAddr.get(i) + "\nhousekey=" +alrHouseKey.get(i)+ "/刪:"+alrDelKey.get(i)+"/角色:" + alrUserType.get(i));
            txHouseAddr.setText(alrAddr.get(i) + "\n租客:" +alrTenantName.get(i));
            txTentNote.setText(alrTentNote.get(i));

//            Log.d("fire==", "" + i);

            if (alrMessflag.get(i)){btnMessage.setVisibility(View.VISIBLE);}
//            若使用者是該房屋的建立者,才允許有刪除的按鈕及方法
            if (alrDelKey.get(i)) {
                btnDel.setVisibility(View.VISIBLE);
                btnDel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Log.d("fire===position", position + "");
                        Log.d("fire===alrHouseKey", alrHouseKey.get(position));
                        LayoutInflater inflater = (LayoutInflater) mContext
                                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                        root = inflater.inflate(R.layout.dialog_delete_layout, null);//找出根源樹,
                        TextView txDeleteDescription = root.findViewById(R.id.txDeleteDescription);
                        Button confirmDelete = root.findViewById(R.id.btn_Deleteconfirm);
                        Button cancelDelete = root.findViewById(R.id.btn_Deletecancel);
                        txDeleteDescription.setText("請問是否要將資料刪除");
                        cancelDelete.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                dialog.dismiss();
                            }
                        });
                        confirmDelete.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {

                                mDatabase = FirebaseDatabase.getInstance().getReference("House");
                                mDatabase = mDatabase.child(alrHouseKey.get(position));
                                mDatabase.removeValue();
                                final String strhouseID=alrHouseKey.get(position);
                                //刪除房屋資料同時要刪除租客資料
                                mDatabase = FirebaseDatabase.getInstance().getReference("Tenant");
                                mDatabase.addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(DataSnapshot dataSnapshot) {
                                        for (DataSnapshot ds : dataSnapshot.getChildren()) {
                                            Tenant tenant = ds.getValue(Tenant.class);
                                            Log.d("fire==-0==",strhouseID+"");
                                            Log.d("fire==-1==",tenant.getHouseID()+"");
                                            if ((!(tenant.getHouseID()==null))&&tenant.getHouseID().equals(strhouseID)){
                                                mDatabase = mDatabase.child(ds.getKey());
                                                mDatabase.removeValue();
                                            }
                                        }
                                    }

                                    @Override
                                    public void onCancelled(DatabaseError databaseError) {

                                    }
                                });

                                //同步刪除該房屋的所有房客資料


//                            myadapter.notifyDataSetChanged();
                                dialog.dismiss();
                            }
                        });
                        AlertDialog.Builder abc = new AlertDialog.Builder(mContext);
                        abc.setView(root);
                        dialog = abc.show();
                    }
                });
            }
            return convertView;
        }


    }




}
