package com.example.kevin.firebasetest4;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kevin.firebasetest4.Adapter.HouseDetailAdapter;
import com.example.kevin.firebasetest4.Adapter.PriceDataAdapter;
import com.example.kevin.firebasetest4.FireBase.HouseData;
import com.example.kevin.firebasetest4.FireBase.LandlordData;
import com.example.kevin.firebasetest4.FireBase.PriceData;
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

public class DetailActivity extends AppCompatActivity implements ListView.OnItemClickListener{
    static HouseDetailAdapter myadapter;
    PriceDataAdapter myPriceadapter;

    public static String houseKey;
    public static  ArrayList<String> alrDelKey=new ArrayList<>();
    public static  ArrayList<String> alrAddr=new ArrayList<>();
    public  static ArrayList<Boolean> alrAlarm=new ArrayList<>();
    public static ArrayList<String> alrTentNote=new ArrayList<>();
    public static ArrayList<Boolean> alrPayTent=new ArrayList<>();
    public static ArrayList<Boolean> alrPayLandlord=new ArrayList<>();
    public static ArrayList<Boolean> alrChcekPrice=new ArrayList<>();

    public static ArrayList<String> alrHouseKey=new ArrayList<>();
    ArrayList<Date>alrPrePayDate=new ArrayList<Date>();
    ArrayList<Date>alrNextPayDate=new ArrayList<Date>();
    ListView listView;
    Button btnTenant;
    DatabaseReference reference_contacts;

    //記錄在進行新增各項費用之前是點選了那個畫面
    private static String viewType;
    Button txBtn;
    //該錄房客對於各項費用權限,若是房東一律為true
    boolean boolWater,boolGas,boolEle,boolMan,chkPricePermission,delPricePermission;

    //取得 intent extra資料
    public static String intent_login_user,intent_login_user_type,intent_login_password,intent_user_ID;
    public static String  intent_user_Name,intent_user_Phone,intent_user_Bank,intent_user_Bank_Number,intent_user_Account;
    public static String intent_check_Password,intent_tenant_name;
    private static String intent_house_id,intent_house_title,intent_house_addr;
    public static String intent_tenant_id,intent_house_create_user,intent_house_landlord_id;
    //記錄該使用者對於該房屋的角色
    String permissionTtype;
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
        intent_tenant_name=bundle.getString("FIREDB_TENANT_NAME");
        intent_house_create_user=bundle.getString("FIREDB_HOUSE_CRATE_USER");
        intent_house_landlord_id=bundle.getString("FIREDB_HOUSE_LANDLORD_ID");
        Log.d("AAABBdetail",intent_login_user_type);
//        Log.d("intent_tenant_name","house_create="+intent_house_create_user);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        restoreIntent();
        //========================================
        listView = (ListView) findViewById(R.id.list);
        listView.setOnItemClickListener(this);

        btnTenant=(Button)findViewById(R.id.button19);//編輯房客
        Button btnDelLanTen=(Button)findViewById(R.id.button21);
        Button btnLanTen=(Button)findViewById(R.id.button8);

        //角色1,房東登入 ,角色2,房客登入,自已新增房屋自行管理,  角色3,房客登入,匯入房東的房屋物件
        if (intent_login_user_type.equals("landlord")
                && intent_house_create_user.equals(intent_user_ID)) {
            permissionTtype = "landlordLogin";
            chkPricePermission = true;
            delPricePermission = true;
            btnTenant.setText("編輯房客");
            btnDelLanTen.setText("刪除房客");
            btnLanTen.setText("房客");
            btnTenant.setOnClickListener(listenerAddTenant);
            btnDelLanTen.setOnClickListener(listenerDelTennet);

        }else if (intent_login_user_type.equals("tenant")
                && intent_house_create_user.equals(intent_user_ID)) {
            permissionTtype = "tenantLoginCreateHouse";
            chkPricePermission = false;
            delPricePermission = true;
            btnTenant.setText("編輯房東");
            btnDelLanTen.setText("刪除房東");
            btnLanTen.setText("房東");
            btnTenant.setOnClickListener(listenerAddTenant);
            btnDelLanTen.setOnClickListener(listenerDelTennet);
        }else {
            permissionTtype = "tenantImportHouse";
            btnTenant.setText("");//無法刪除
            btnDelLanTen.setText("");//無法編輯房東
            btnLanTen.setText("房客");
        }



//        intent_login_user_type="tenant";
        Log.d("PricePermission=",intent_house_create_user);
        Log.d("PricePermission=",intent_user_ID+"");
//        if (intent_house_create_user.equals(intent_user_ID)) {
//            chkPricePermission = true;
//            delPricePermission = true;
//        }
//        else {
//            chkPricePermission = false;
////            delPricePermission = true;
//        }

        Log.d("permission==",intent_login_user_type);

//        if (intent_login_user_type.equals("landlord")) {
//            btnTenant.setText("編輯房客");
//            btnDelLanTen.setText("刪除房客");
//           btnLanTen.setText("房客");
//        }else {
//            if (intent_user_ID.equals(intent_house_create_user)) {
//                btnTenant.setText("編輯房東");
//                btnDelLanTen.setText("刪除房東");
//
//            }else{//此情況為房客匯入房東的資料
//                btnTenant.setText("");
//                btnDelLanTen.setText("");
//            }
//            btnLanTen.setText("房東");
//
//
//        }

        defaultHouse();
    }


    //implements  ListView.OnItemClickListener===================================
    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        System.out.println("rowId = " + i);
        Toast.makeText(this, "第" + i + "項", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
    //=========================================================
    public void clickRent(View target){
        //==============================================

        if (!(txBtn==null)) txBtn.setBackgroundColor(Color.parseColor("#ffff8800"));
        ((Button)target).setBackgroundColor(Color.BLUE);
        txBtn=(Button)target;
        viewType="Rent";
        //================================================
        alrDelKey=new ArrayList<>();
        alrAddr=new ArrayList<>();
        alrChcekPrice=new ArrayList<>();
        reference_contacts = FirebaseDatabase.getInstance().getReference("Rent");
        reference_contacts.orderByChild("dateString").addValueEventListener(valueEventListenerPrice);

        myPriceadapter = new PriceDataAdapter(this,"Rent",true,chkPricePermission);
        listView.setAdapter(myPriceadapter);
        myPriceadapter.notifyDataSetChanged();
    }
    //=========================================================
    public void clickWater(View target){
        //=======================================================
        if (!(txBtn==null)) txBtn.setBackgroundColor(Color.parseColor("#ffff8800"));
        ((Button)target).setBackgroundColor(Color.BLUE);
        txBtn=(Button)target;
        viewType="Water";
        //====================================
        alrDelKey=new ArrayList<>();
        alrAddr=new ArrayList<>();
        alrChcekPrice=new ArrayList<>();
        reference_contacts = FirebaseDatabase.getInstance().getReference("Water");
        reference_contacts.orderByChild("dateString").addValueEventListener(valueEventListenerPrice);

        if(intent_house_create_user.equals(intent_user_ID)) boolWater=true;
            myPriceadapter = new PriceDataAdapter(this,"Water",boolWater,chkPricePermission);

        listView.setAdapter(myPriceadapter);
        myPriceadapter.notifyDataSetChanged();
    }
    //=========================================================
    public void clickElectricity(View target){
        //=======================================================
        if (!(txBtn==null)) txBtn.setBackgroundColor(Color.parseColor("#ffff8800"));
        ((Button)target).setBackgroundColor(Color.BLUE);
        txBtn=(Button)target;
        viewType="Electricity";
        alrDelKey=new ArrayList<>();
        alrAddr=new ArrayList<>();
        alrChcekPrice=new ArrayList<>();
        reference_contacts = FirebaseDatabase.getInstance().getReference("Electricity");
        reference_contacts.orderByChild("dateString").addValueEventListener(valueEventListenerPrice);

        if(intent_house_create_user.equals(intent_user_ID))boolEle=true;
        myPriceadapter = new PriceDataAdapter(this,"Electricity",boolEle,chkPricePermission);
        listView.setAdapter(myPriceadapter);
        myPriceadapter.notifyDataSetChanged();
    }
    //=========================================================
    public void clickGas(View target){
        //=======================================================
//        if (!(txBtn==null)) txBtn.setBackgroundColor(Color.LTGRAY);
//        ((Button)target).setBackgroundColor(Color.BLUE);
//        txBtn=(Button)target;
        viewType="Gas";
        //=======================================================
        alrDelKey=new ArrayList<>();
        alrAddr=new ArrayList<>();
        alrChcekPrice=new ArrayList<>();
        reference_contacts = FirebaseDatabase.getInstance().getReference("Gas");
        reference_contacts.orderByChild("dateString").addValueEventListener(valueEventListenerPrice);

        if(intent_house_create_user.equals(intent_user_ID))boolGas=true;
        myPriceadapter = new PriceDataAdapter(this,"Gas",boolGas,chkPricePermission);
        listView.setAdapter(myPriceadapter);
        myPriceadapter.notifyDataSetChanged();
    }
    public void clickManagement(View target){
        //=======================================================
        if (!(txBtn==null)) txBtn.setBackgroundColor(Color.LTGRAY);
        ((Button)target).setBackgroundColor(Color.BLUE);
        txBtn=(Button)target;
        viewType="Management";
        alrDelKey=new ArrayList<>();
        alrAddr=new ArrayList<>();
        alrChcekPrice=new ArrayList<>();
        reference_contacts = FirebaseDatabase.getInstance().getReference("Management");
        reference_contacts.orderByChild("dateString").addValueEventListener(valueEventListenerPrice);

        if(intent_house_create_user.equals(intent_user_ID))boolMan=true;
        myPriceadapter = new PriceDataAdapter(this,"Management",boolMan,chkPricePermission);
        listView.setAdapter(myPriceadapter);
        myPriceadapter.notifyDataSetChanged();
    }

    //============================================================
    public void clicktenant(View target){

        myadapter = new HouseDetailAdapter(this);
        listView.setAdapter(myadapter);

        reference_contacts = FirebaseDatabase.getInstance().getReference("Tenant");
        reference_contacts.addValueEventListener(valueEventListenerTenant);

    }
    //================================================
    public   void defaultHouse(){
        myadapter = new HouseDetailAdapter(this);
        listView.setAdapter(myadapter);
        Log.d("AAABB=",permissionTtype+"");
        if (permissionTtype .equals("tenantImportHouse")) {
                    Log.d("intent_house_id==","tenantImportHouse");

            reference_contacts = FirebaseDatabase.getInstance().getReference("Landlord");
            reference_contacts.addValueEventListener(valueEventListenerImport);
        }
        else{
            reference_contacts = FirebaseDatabase.getInstance().getReference("House");
            reference_contacts.addValueEventListener(valueEventListener);
        }
    }
    public void clickHouse(View target){
        defaultHouse();
    }

//==============================================================
private ValueEventListener valueEventListenerPrice = new ValueEventListener() {
    @Override
    public void onDataChange(DataSnapshot dataSnapshot) {
//        Log.d("fire==alrWaterKeCha1=",alrDelKey.size()+"");
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        alrDelKey=new ArrayList<>();
        alrAddr=new ArrayList<>();
        alrChcekPrice=new ArrayList<>();
        for (DataSnapshot ds : dataSnapshot.getChildren()) {
            Log.d("Price=ds.getkey==" ,ds.getKey()+"");

            PriceData priceData = ds.getValue(PriceData.class);
            if (priceData.getHouseID().equals(intent_house_id)) {
                Log.d("Price=alrChcekPrice==", priceData.getIsCheck() + "");
                alrChcekPrice.add(priceData.getIsCheck());
//            alrAddr.add(" "+formatter.format(priceData.getDate()) + "   " + priceData.getPrice() + "\n" +ds.getKey());
                alrAddr.add(" " + formatter.format(priceData.getDate()) + "   " + priceData.getPrice());
                alrDelKey.add(ds.getKey());
            }
        }
        myPriceadapter.notifyDataSetChanged();
//        Log.d("fire==alrWaterKeCha2=",alrDelKey.size()+"");

    }

    //======================================================================

    @Override
    public void onCancelled(DatabaseError databaseError) {

    }
};
//====================================================================
//for (DataSnapshot ds : dataSnapshot.getChildren()) {
//        LandlordData landlord = ds.getValue(LandlordData.class);
//        if (landlord.getEmail().equals(pref_login_user)
//                && landlord.getType().equals(pref_login_user_type)) //資料已經有存在
//        {
//            //取出UserID
//            pref_login_ID = ds.getKey();
//            pref_login_name=landlord.getName();
//            login_user_Phone=landlord.getPhone();
//            login_user_Bank=landlord.getBank();
//            login_user_Bank_Number=landlord.getBankNumber();
//            login_user_Account=landlord.getBankNumber();
//            login_check_Password=landlord.getPasswordCheck();
//
////                        Log.d("login_user_Name=",pref_login_name);
////                        Log.d("login_user_Phone=",login_user_Phone);
////                        Log.d("login_user_Bank=",login_user_Bank);
////                        Log.d("login_user_Bank_Number=",login_user_Bank_Number);
////                        Log.d("login_user_Account=",login_user_Account);
//
//            Log.d("FireBaseTraining", "資料已經有存在,userid=" + pref_login_ID);
//            addLanlordFlag=false;
//            break;
//        }
//    }
//========================================================
    //房東詳細資料,適用於import情況
    private ValueEventListener valueEventListenerImport = new ValueEventListener() {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
            Log.d("intent_house_id=",intent_house_id);
            Log.d("intent_house_id=1",intent_house_landlord_id);

            alrAddr=new ArrayList<>();

            LandlordData landlordData=dataSnapshot.child(intent_house_landlord_id).getValue(LandlordData.class);

            alrAddr.add(" " + intent_house_title);
            alrAddr.add(" " + intent_house_addr);
                alrAddr.add(" 房東姓名: " +landlordData.getName());
                alrAddr.add(" 房東電話: " + landlordData.getPhone());
                alrAddr.add(" 匯款銀行: " + landlordData.getBank()+"(" + landlordData.getBankNumber() + ")");
                alrAddr.add(" 匯款銀行: " + landlordData.getAccunt());

//
//
//                if ((houseData == null) || houseData.getIdentity() == null)
//                    alrAddr.add(" 租客 : 無");
//                else
//                    alrAddr.add(" 租客 : " + houseData.getTenantName());
                myadapter.notifyDataSetChanged();
//            }
        }
        @Override
        public void onCancelled(DatabaseError databaseError) {}
    };

    //房屋詳細資料
    private ValueEventListener valueEventListener = new ValueEventListener() {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
            Log.d("intent_house_id=",intent_house_id);
            alrAddr.clear();
            HouseData houseData =dataSnapshot.child(intent_house_id).getValue(HouseData.class);
//            alrAddr.add(" 房東姓名: " +houseData.getCreateUser() );
            alrAddr.add(" 房東Email: " +houseData.getCreateName());
            alrAddr.add(" " +intent_house_title );
            alrAddr.add(" " +intent_house_addr);


            if ((houseData==null)||houseData.getIdentity()==null)
                alrAddr.add(" 租客 : 無");
            else
            alrAddr.add(" 租客 : " + houseData.getTenantName());
            myadapter.notifyDataSetChanged();
        }
        @Override
        public void onCancelled(DatabaseError databaseError) {}
    };

    //================================================================
    private ValueEventListener valueEventListenerTenant = new ValueEventListener() {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
//                alrAlarm.clear();
            alrAlarm=new ArrayList<Boolean>();
            alrHouseKey.clear();
            alrTentNote.clear();
            alrPayTent.clear();
            alrPayLandlord.clear();
            alrAddr=new ArrayList<>();
            alrPrePayDate.clear();
            alrNextPayDate.clear();
            Log.d("tennid===",intent_tenant_id+"");

            if (!(intent_tenant_id==null)) {//情況1,一開始沒有房客資料的情況下點選房客
                Tenant tenant = dataSnapshot.child(intent_tenant_id).getValue(Tenant.class);

                if (!(tenant == null)) {//情況2,一開始有房客資料的情況下,刪除房客資料後,點選房客
                    alrAddr.add("房客姓名: " + tenant.getName());
                    alrAddr.add("戶籍地址: " + tenant.getCity()+tenant.getLocation()+tenant.getAddr());
                    alrAddr.add("手機: " + tenant.getPhone()+" 電話: " + tenant.getTel());
//                    alrAddr.add("電話: " + tenant.getTel());
                    alrAddr.add("簽約日期: " + ((tenant.getSignDate() == null) ? "" : tenant.getSignDate()));

                    alrAddr.add("租期開始日: " + ((tenant.getStartDate() == null) ? "" : tenant.getStartDate()));
                    alrAddr.add("租期終止日: " + ((tenant.getEndDate() == null) ? "" : tenant.getEndDate()));

                alrAddr.add("每期租金: " + tenant.getRent() + "元");
                alrAddr.add("付款日期: 每" + tenant.getPayDay() + "日 付款週期: 每" + tenant.getPeriod() + "月");
//            alrAddr.add(" 付款週期: " +tenant.getPeriod());
                    StringBuilder sb = new StringBuilder();
                    if (tenant.getCheckWater()) sb.append(" 水費");
                    if (tenant.getCheckEle()) sb.append(" 電費");
                    if (tenant.getCheckGas()) sb.append(" 瓦斯費");
                    if (tenant.getCheckMan()) sb.append(" 管理費");

                    alrAddr.add("房客另需支付費用:" + sb);
                }
            }
//            }
            myadapter.notifyDataSetChanged();
        }

        @Override
        public void onCancelled(DatabaseError databaseError) {

        }
    };
//================================================================================
    View.OnClickListener listenerDelTennet= new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        LayoutInflater inflater = (LayoutInflater) DetailActivity.this
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
                DatabaseReference mDatabase;
                mDatabase = FirebaseDatabase.getInstance().getReference("Tenant");
                mDatabase=mDatabase.child(intent_tenant_id);
                mDatabase.removeValue();

                //同步清空House.tenentID的資料
                mDatabase = FirebaseDatabase.getInstance().getReference("House");
                mDatabase = mDatabase.child(intent_house_id);
                Map<String, Object> nameMap = new HashMap<String, Object>();
                nameMap.put("tenantID", null);
                nameMap.put("tenantName", null);
                mDatabase.updateChildren(nameMap);
                dialog.dismiss();
            }
        });
        AlertDialog.Builder abc = new AlertDialog.Builder(DetailActivity.this);
        abc.setView(root);
        dialog = abc.show();
    }
};

//    public void delTennet(View target){
////        Log.d("fire===",position+"");
////        Log.d("fire===", "imgbtnDelete");
////        Log.d("fire===" ,DetailActivity.alrDelKey.get(position));
//        LayoutInflater inflater = (LayoutInflater) this
//                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//        root = inflater.inflate(R.layout.dialog_delete_layout, null);//找出根源樹,
//        TextView txDeleteDescription = root.findViewById(R.id.txDeleteDescription);
//        Button confirmDelete = root.findViewById(R.id.btn_Deleteconfirm);
//        Button cancelDelete = root.findViewById(R.id.btn_Deletecancel);
//        txDeleteDescription.setText("請問是否要將資料刪除");
//        cancelDelete.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                dialog.dismiss();
//            }
//        });
//        confirmDelete.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                DatabaseReference mDatabase;
//                mDatabase = FirebaseDatabase.getInstance().getReference("Tenant");
//                mDatabase=mDatabase.child(intent_tenant_id);
//                mDatabase.removeValue();
//
//                //同步清空House.tenentID的資料
//                mDatabase = FirebaseDatabase.getInstance().getReference("House");
//                mDatabase = mDatabase.child(intent_house_id);
//                Map<String, Object> nameMap = new HashMap<String, Object>();
//                nameMap.put("tenantID", null);
//                nameMap.put("tenantName", null);
//                mDatabase.updateChildren(nameMap);
//                dialog.dismiss();
//            }
//        });
//        AlertDialog.Builder abc = new AlertDialog.Builder(this);
//        abc.setView(root);
//        dialog = abc.show();
//
//    }

    View.OnClickListener listenerAddTenant= new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent=new Intent();
            intent.setClass(DetailActivity.this,AddTenantActivity.class);
            Bundle bundle = new Bundle();
            Log.d("tennAdd","1"+intent_house_id+"1");
            bundle.putString("FIREDB_HOUSE_ID", intent_house_id);
            bundle.putString("FIREDB_HOUSE_TITLE", intent_house_title);
            bundle.putString("FIREDB_HOUSE_ADDR", intent_house_addr);
            bundle.putString("FIREDB_TENANT_ID", intent_tenant_id);
            bundle.putString("FIREDB_TENANT_NAME", intent_tenant_name);
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
        }
    };
//    public void addTennet(View target){
////        intent_tenant_id=bundle.getString("FIREDB_TENANT_ID");
//        Log.d("tenantgetenid=",intent_tenant_id+"add");
//
//        Intent intent=new Intent();
//        intent.setClass(this,AddTenantActivity.class);
//        Bundle bundle = new Bundle();
//        Log.d("tennAdd","1"+intent_house_id+"1");
//        bundle.putString("FIREDB_HOUSE_ID", intent_house_id);
//        bundle.putString("FIREDB_HOUSE_TITLE", intent_house_title);
//        bundle.putString("FIREDB_HOUSE_ADDR", intent_house_addr);
//        bundle.putString("FIREDB_TENANT_ID", intent_tenant_id);
//        bundle.putString("FIREDB_TENANT_NAME", intent_tenant_name);
//        bundle.putString("PREF_LOGIN_USER", intent_login_user);
//        bundle.putString("PREF_LOGIN_USER_TYPE", intent_login_user_type);
//        bundle.putString("PREF_LOGIN_PASSWORD", intent_login_password);
//
//        bundle.putString("FIREDB_LOGIN_ID", intent_user_ID);
//        bundle.putString("FIREDB_USER_NAME", intent_user_Name);
//        bundle.putString("FIREDB_USER_PHONE", intent_user_Phone);
//        bundle.putString("FIREDB_USER_BANK", intent_user_Bank);
//        bundle.putString("FIREDB_USER_BANK_NUMBER", intent_user_Bank_Number);
//        bundle.putString("FIREDB_USER_ACCUNT", intent_user_Account);
//        bundle.putString("FIREDB_USER_CHECK_PASSWORD", intent_check_Password);
//
//        intent.putExtras(bundle);
//        startActivity(intent);
//
//    }

    //===============================================================
    EditText etGas,etRent,etWater,etElectricity,etManagement;
    Button confirm,cancel,confirmAdd,cancelDelete,txGasDate,txRentDate,txManDate,txWaterDate,txEleDate;
    AlertDialog dialog; //讓自定Layout可有關閉功能
    View root;


    public void addPrice(View target){
        myadapter = new HouseDetailAdapter(this);
        listView.setAdapter(myadapter);

        StringBuilder sbBuffer=new StringBuilder();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        View root;


            LayoutInflater inflater = (LayoutInflater) this
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            root = inflater.inflate(R.layout.dialog_tenant_addprice, null);//找出根源樹,

            etRent=root.findViewById(R.id.etRent);
            etWater=root.findViewById(R.id.etWater);
            etElectricity=root.findViewById(R.id.etElectricity);
            etManagement=root.findViewById(R.id.etManagement);
            etGas=root.findViewById(R.id.etGas);

            confirmAdd =  root.findViewById(R.id.btnConfirm);
            cancelDelete=root.findViewById(R.id.btnCancel);
            //=======================================================
            txRentDate=root.findViewById(R.id.txRentDate);
            txRentDate.setText(formatter.format(new Date()));
            txManDate=root.findViewById(R.id.txManDate);
            txManDate.setText(formatter.format(new Date()));
            txWaterDate = root.findViewById(R.id.txWaterDate);
            txWaterDate.setText(formatter.format(new Date()));

            txEleDate=root.findViewById(R.id.txEleDate);
            txEleDate.setText(formatter.format(new Date()));
            txGasDate=root.findViewById(R.id.txGasDate);
            txGasDate.setText(formatter.format(new Date()));

            //若是房客身份,則先檢查各項費用的權限
            if (intent_house_create_user.equals(intent_user_ID)){//角色A,B
                txGasDate.setOnClickListener(dateClickListener);
                txWaterDate.setOnClickListener(dateClickListener);
                txManDate.setOnClickListener(dateClickListener);
                txEleDate.setOnClickListener(dateClickListener);
                boolWater = boolGas = boolEle = boolMan = true;
            }
            else {

                reference_contacts = FirebaseDatabase.getInstance().getReference("Tenant");
                reference_contacts.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if (!(intent_tenant_id == null)) {
                            Tenant tenant = dataSnapshot.child(intent_tenant_id).getValue(Tenant.class);
//                        Log.d("boolean==tenant=", tenant + "");
//                        Log.d("boolean==tenant=", (tenant == null) + "");
                            if (!(tenant == null)) {
                                boolWater = tenant.getCheckWater();
                                boolEle = tenant.getCheckEle();
                                boolMan = tenant.getCheckMan();
                                boolGas = tenant.getCheckGas();

                                if (boolGas)
                                    txGasDate.setOnClickListener(dateClickListener);
                                else {
                                    etGas.setFocusable(false);
                                    txGasDate.setBackgroundColor(Color.GRAY);

                                }
                                if (boolWater)
                                    txWaterDate.setOnClickListener(dateClickListener);
                                else {
                                    etWater.setFocusable(false);
                                    txWaterDate.setBackgroundColor(Color.GRAY);
                                }


                                if (boolEle) {
                                    txEleDate.setOnClickListener(dateClickListener);
                                } else {
                                    etElectricity.setFocusable(false);
                                    txEleDate.setBackgroundColor(Color.GRAY);
                                }

                                if (boolMan)
                                    txManDate.setOnClickListener(dateClickListener);
                                else {
                                    etManagement.setFocusable(false);
                                    txManDate.setBackgroundColor(Color.GRAY);
                                }
                            }
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                    }
                });
            }
//===================================================================
            txRentDate.setOnClickListener(dateClickListener);
//===========================================================
            confirmAdd.setOnClickListener(addPriceListener);
            cancelDelete.setOnClickListener(addPriceListener);
            AlertDialog.Builder abc = new AlertDialog.Builder(this);
            abc.setView(root);
            dialog = abc.show();

    }

    private void fireBaseAdd(String input,String type,int price){
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date t = null;
        try {
            t = formatter.parse(input);
        } catch (ParseException e) {
        }
//        if (intent_login_user_type.equals("landlord")){
//
//        }
        PriceData priceData = new PriceData(intent_user_ID, intent_house_id, "", intent_tenant_id, input, t, price,false);
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference(type);
        mDatabase.push().setValue(priceData);



    }

    View.OnClickListener addPriceListener= new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if (view == confirmAdd) {
//                Log.d("fire=toString=", view.toString());
                if (etGas.getText().toString().trim().length() > 0) {
                    int price = Integer.parseInt(etGas.getText().toString().trim());
                    String input = txGasDate.getText().toString().trim();
                    String type="Gas";
                    fireBaseAdd(input,type,price);
                }
                if (etRent.getText().toString().trim().length() > 0) {
                    int price = Integer.parseInt(etRent.getText().toString().trim());
                    String input = txRentDate.getText().toString().trim();
                    String type="Rent";
                    fireBaseAdd(input,type,price);
                }

                if (etWater.getText().toString().trim().length() > 0) {
                    int price = Integer.parseInt(etWater.getText().toString().trim());
                    String input = txWaterDate.getText().toString().trim();
                    String type="Water";
                    fireBaseAdd(input,type,price);
                }
                if (etElectricity.getText().toString().trim().length() > 0) {
                    int price = Integer.parseInt(etElectricity.getText().toString().trim());
                    String input = txEleDate.getText().toString().trim();
                    String type="Electricity";
                    fireBaseAdd(input,type,price);
                }
                if (etManagement.getText().toString().trim().length() > 0) {
                    int price = Integer.parseInt(etManagement.getText().toString().trim());
                    String input = txManDate.getText().toString().trim();
                    String type="Management";
                    fireBaseAdd(input,type,price);

                }
            }
            if (!(myPriceadapter==null))
            myPriceadapter.notifyDataSetChanged();
            dialog.dismiss();

            if (!(viewType==null)) {
                //dialog消失後,程式自動更新資料
                switch (viewType) {
                    case "Rent":
                        clickRent(view);
                        break;
                    case "Water":
                        clickWater(view);
                        break;
                    case "Electricity":
                        clickElectricity(view);
                        break;
                    case "Gas":
                        clickGas(view);
                        break;
                    case "Management":
                        clickManagement(view);
                        break;

                }
            }


        }
    };


//==================日期按下時,開啟日曆,點選日期得更改textView內容======================================================================
    View.OnClickListener dateClickListener= new View.OnClickListener() {
        @Override
        public void onClick(final View view) {
            Date now = new Date();

            DatePickerDialog dpd=new DatePickerDialog(DetailActivity.this, new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                    String input = i +"-"+(i1+1)+"-"+("0"+i2).substring(("0"+i2).length()-2);

                    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                    Date t = null;
                    try{
                        t = formatter.parse(input);
                    }catch(ParseException e){
                    }
                    if (t.after(new Date()))
                        Toast.makeText(DetailActivity.this,"日期不可以大於今日",Toast.LENGTH_SHORT).show();
                    else
                        ((Button)view).setText(input);
                }

            },now.getYear()+1900,now.getMonth(),now.getDay()); //畫面出來預設值  2017,12,05
            dpd.show();

        }
    };

}