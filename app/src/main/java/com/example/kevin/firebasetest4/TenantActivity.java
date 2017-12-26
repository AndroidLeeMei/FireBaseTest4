package com.example.kevin.firebasetest4;

import android.app.DatePickerDialog;
import android.content.Context;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kevin.firebasetest4.Adapter.TenantExpandableAdapter;
import com.example.kevin.firebasetest4.FireBase.PriceData;
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
import java.util.List;


public class TenantActivity extends AppCompatActivity {
    public final static String FINAL_LANDLORD_ID="J3455888889";
    public final static String FINAL_HOUSE_ID="-KzrIWelZvFNuOXWFqKN";
    public final static String FINAL_HOUSE_SUB_ID="H001";
    public final static String FINAL_TENANT_ID="tenantID01";


    TenantExpandableAdapter listAdapter;
    ExpandableListView expListView;
    public static ArrayList<String> listPriceKey=new ArrayList<>();
    public static ArrayList<String> listDataHeader = new ArrayList<String>();  //第一層類別
    public static HashMap<String, List<String>> listDataChild = new HashMap<String, List<String>>();//第二層子項
//    List<String> ListTenant = new ArrayList<String>();
    static ArrayList<String> alrHouseID=new ArrayList();

    TextView textView,textViewInfo;
    EditText etHouseAddr;
    StringBuilder sbBuffer=new StringBuilder();
    String strPriceKey;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tenant);

        // get the listview
        expListView = (ExpandableListView) findViewById(R.id.lvExp);
        etHouseAddr=(EditText)findViewById(R.id.etHouserAddr);
        textViewInfo=(TextView)findViewById(R.id.textView2) ;
        textViewInfo.setText("房屋地址:" +"\n房東姓名:" + "\n租期:  ~  \n每月租金:");
        // preparing list data
        prepareListData();
//        Log.d("fire=LislistDataChild0=",listDataChild.size()+"");

        listAdapter = new TenantExpandableAdapter(this, listDataHeader, listDataChild);

        // setting list adapter
        expListView.setAdapter(listAdapter);
        Log.d("fire=LislistDataChild1=",listDataChild.size()+"");



        // Listview Group click listener
        expListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {

            @Override
            public boolean onGroupClick(ExpandableListView parent, View v,
                                        int groupPosition, long id) {
//                 Toast.makeText(getApplicationContext(),
//                 "Group Clicked " + listDataHeader.get(groupPosition)+"  "+alrType.get(groupPosition),
//                 Toast.LENGTH_SHORT).show();

//                etHouseAddr.setText(listDataHeader.get(groupPosition) );
//                strHouseKey=listHouseKey.get(groupPosition);
                return false;//若回傳true,則不支援展開功能
            }
        });

        // Listview Group expanded listener
        expListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {

            @Override
            public void onGroupExpand(int groupPosition) {
//                Toast.makeText(getApplicationContext(),
//                        listDataHeader.get(groupPosition) + " Expanded",
//                        Toast.LENGTH_SHORT).show();
            }
        });

        // Listview Group collasped listener
        expListView.setOnGroupCollapseListener(new ExpandableListView.OnGroupCollapseListener() {

            @Override
            public void onGroupCollapse(int groupPosition) {
//                Toast.makeText(getApplicationContext(),
//                        listDataHeader.get(groupPosition) + " Collapsed",
//                        Toast.LENGTH_SHORT).show();

            }
        });

        // Listview on child click listener
        expListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v,
                                        int groupPosition, int childPosition, long id) {
                // TODO Auto-generated method stub
                Toast.makeText( getApplicationContext(),

                        mChildColumnName.get(groupPosition)+""
//                                + " : "
//                                + listDataChild.get(
//                                listDataHeader.get(groupPosition)).get(
//                                childPosition)
                        , Toast.LENGTH_SHORT)
                        .show();
                return false;
            }
        });

        //底下的按鈕====================================================
        Button button = (Button) findViewById(R.id.button);
        textView = (TextView) findViewById(R.id.textView);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

            }
        });

    }

    //============================================================
    AlertDialog dialog; //讓自定Layout可有關閉功能
    View root;
    EditText etRent,etWater,etElectricity,etManagement;
    Button confirm,cancel,confirmAdd,cancelDelete;
    TextView txRentDate,txManDate,txWaterDate,txEleDate;
    TextView txDescription,txDeleteDescription;
    ImageButton imgRentDate,imgWaterDate,imgEleDate,imgManDate;

    public void addTenantPrice(View target){
//        sbBuffer.append(etHouseAddr.getText().toString().trim());
//        sbBuffer="d";
        if (sbBuffer.length()!=0) {
//            Toast.makeText(this,"請先輸入房屋地址",Toast.LENGTH_SHORT).show();
        }
        else {
            LayoutInflater inflater = (LayoutInflater) this
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            root = inflater.inflate(R.layout.dialog_tenant_addprice_old, null);//找出根源樹,

            etRent=root.findViewById(R.id.etRent);
            etWater=root.findViewById(R.id.etWater);
            etElectricity=root.findViewById(R.id.etElectricity);
            etManagement=root.findViewById(R.id.etManagement);

            confirmAdd =  root.findViewById(R.id.btnConfirm);
            cancelDelete=root.findViewById(R.id.btnCancel);
            txRentDate=root.findViewById(R.id.txRentDate);
            txRentDate.setText(formatter.format(new Date()));
            txManDate=root.findViewById(R.id.txManDate);
            txManDate.setText(formatter.format(new Date()));
            txWaterDate=root.findViewById(R.id.txWaterDate);
            txWaterDate.setText(formatter.format(new Date()));
            txEleDate=root.findViewById(R.id.txEleDate);
            txEleDate.setText(formatter.format(new Date()));
//===================================================================
            imgWaterDate=root.findViewById(R.id.imgWaterDate);
            imgWaterDate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Date now = new Date();
                    DatePickerDialog dpd=new DatePickerDialog(TenantActivity.this, new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                            String input = i +"-"+(i1+1)+"-"+("0"+i2).substring(("0"+i2).length()-2);

                            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                            Date t = null;
                            try{
                                t = formatter.parse(input);
                            }catch(ParseException e){
                            }
                            txWaterDate.setText(input);
                        }
                    },now.getYear()+1900,now.getMonth(),now.getDay()); //畫面出來預設值  2017,12,05
                    dpd.show();
                }
            });
// =============================================================================================
            imgEleDate=root.findViewById(R.id.imgEleDate);
            imgEleDate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Date now = new Date();
                    DatePickerDialog dpd=new DatePickerDialog(TenantActivity.this, new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                            String input = i +"-"+(i1+1)+"-"+("0"+i2).substring(("0"+i2).length()-2);

                            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                            Date t = null;
                            try{
                                t = formatter.parse(input);
                            }catch(ParseException e){
                            }
                            txEleDate.setText(input);
                        }
                    },now.getYear()+1900,now.getMonth(),now.getDay()); //畫面出來預設值  2017,12,05
                    dpd.show();
                }
            });
//================================================================================
            imgManDate=root.findViewById(R.id.imgManagement);
            imgManDate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Date now = new Date();
                    DatePickerDialog dpd=new DatePickerDialog(TenantActivity.this, new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                            String input = i +"-"+(i1+1)+"-"+("0"+i2).substring(("0"+i2).length()-2);

                            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                            Date t = null;
                            try{
                                t = formatter.parse(input);
                            }catch(ParseException e){
                            }
                            txManDate.setText(input);
                        }
                    },now.getYear()+1900,now.getMonth(),now.getDay()); //畫面出來預設值  2017,12,05
                    dpd.show();
                }
            });
//================================================================
            imgRentDate=root.findViewById(R.id.imgRentDate);
            imgRentDate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                      Date now = new Date();
                    DatePickerDialog dpd=new DatePickerDialog(TenantActivity.this, new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                            String input = i +"-"+(i1+1)+"-"+("0"+i2).substring(("0"+i2).length()-2);

                            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                            Date t = null;
                            try{
                                t = formatter.parse(input);
                            }catch(ParseException e){
                            }
                            txRentDate.setText(input);
                        }
                    },now.getYear()+1900,now.getMonth(),now.getDay()); //畫面出來預設值  2017,12,05
                    dpd.show();
                }
            });
//===========================================================

            confirmAdd.setOnClickListener(addPriceListener);
            cancelDelete.setOnClickListener(addPriceListener);

            AlertDialog.Builder abc = new AlertDialog.Builder(this);
            abc.setView(root);
            dialog = abc.show();
        }
    }

//=========================================================




    /*
     * Preparing the list data
     */
    public static HashMap<String, ArrayList<String>> mChildColumnName;//第二層在db的欄位名稱
    public static ArrayList<String> alrType=new ArrayList<>();
    private ArrayList<String> aryDate=new ArrayList<>();
    private ArrayList<String> aryPrice=new ArrayList<>();
    private List ListTenant;
    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
    DatabaseReference reference_contacts;


    private void prepareListData() {
        alrType.clear();
        alrType.add("Rent");
        alrType.add("Water");
        alrType.add("Electricity");
        alrType.add("Management");
        mChildColumnName= new HashMap<String, ArrayList<String>>();//每次重新設定欄位名稱
        // Adding 第一層類別內容
        listDataHeader.clear();
        listDataHeader.add("租金");
        listDataHeader.add("水費");
        listDataHeader.add("電費");
        listDataHeader.add("管理費");

        listDataChild.clear();
        mChildColumnName.clear();
        for (int i=0;i<alrType.size();i++){
            selectFireBaseData(i);
        }
    }



    private void selectFireBaseData(final int i){
        reference_contacts = FirebaseDatabase.getInstance().getReference(alrType.get(i));
//        reference_contacts.orderByChild()
        reference_contacts.orderByChild("dateString").addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.d("fire=rent.type=",alrType.get(i)+"");
                ListTenant=new ArrayList<>();
                listPriceKey=new ArrayList<>();
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    PriceData priceData = ds.getValue(PriceData.class);
                    if (priceData.getLandlordID()!=null
                        && priceData.getHouseID()!=null
                        && priceData.getHouseSubID()!=null
                        && priceData.getTenantID()!=null
                            && priceData.getLandlordID().equals(FINAL_LANDLORD_ID)
                            && priceData.getHouseID().equals( FINAL_HOUSE_ID)
                            && priceData.getHouseSubID().equals( FINAL_HOUSE_SUB_ID)
                            && priceData.getTenantID().equals(FINAL_TENANT_ID)) {

                        ListTenant.add(formatter.format(priceData.getDate()) + "   " + priceData.getPrice());
                        listPriceKey.add(ds.getKey());

                    }
                }
                listDataChild.put(listDataHeader.get(i), ListTenant); // Header, Child data
                mChildColumnName.put(alrType.get(i),listPriceKey);
                listAdapter.notifyDataSetChanged();
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(TenantActivity.this,"目前網路連線異常",Toast.LENGTH_SHORT).show();
            }
        });
//


    }

//==================================================================================
    View.OnClickListener addPriceListener= new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (view == confirmAdd) {
                    Log.d("fire=toString=", view.toString());
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
                dialog.dismiss();

            }

    };

    private void fireBaseAdd(String input,String type,int price){
                       SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                        Date t = null;
                        try {
                            t = formatter.parse(input);
                        } catch (ParseException e) {
                        }
                        PriceData priceData = new PriceData(FINAL_LANDLORD_ID, FINAL_HOUSE_ID, FINAL_HOUSE_SUB_ID, FINAL_TENANT_ID, input, t, price,false);
                        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference(type);
                        mDatabase.push().setValue(priceData);

    }
}

