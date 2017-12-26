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
import android.widget.ExpandableListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class LandlordExpandable extends AppCompatActivity {

    ExpListViewAdapterWithCheckbox listAdapter;
    ExpandableListView expListView;
    public static ArrayList<String> listHouseKey = new ArrayList<>();
    public static ArrayList<String> listDataHeader = new ArrayList<String>();  //第一層類別
    public static HashMap<String, List<String>> listDataChild = new HashMap<String, List<String>>();//第二層子項
    List<String> ListTenant = new ArrayList<String>();
    static ArrayList<String> alrHouseID = new ArrayList();

    TextView textView;
    EditText etHouseAddr;
    StringBuilder sbBuffer = new StringBuilder();
    String strHouseKey;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landlord_expandable);

        // get the listview
        expListView = (ExpandableListView) findViewById(R.id.lvExp);
        etHouseAddr = (EditText) findViewById(R.id.etHouserAddr);
        // preparing list data
        prepareListData();

        listAdapter = new ExpListViewAdapterWithCheckbox(this, listDataHeader, listDataChild);

        // setting list adapter
        expListView.setAdapter(listAdapter);
        Log.d("fire==0=", listDataChild.size() + "");
        // Listview Group click listener
        expListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {

            @Override
            public boolean onGroupClick(ExpandableListView parent, View v,
                                        int groupPosition, long id) {
//                 Toast.makeText(getApplicationContext(),
//                 "Group Clicked " + listDataHeader.get(groupPosition),
//                 Toast.LENGTH_SHORT).show();

                etHouseAddr.setText(listDataHeader.get(groupPosition));
                strHouseKey = listHouseKey.get(groupPosition);
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
//                Toast.makeText(
//                        getApplicationContext(),
//                        listDataHeader.get(groupPosition)
//                                + " : "
//                                + listDataChild.get(
//                                listDataHeader.get(groupPosition)).get(
//                                childPosition), Toast.LENGTH_SHORT)
//                        .show();
                return false;
            }
        });

        //底下的按鈕
        Button button = (Button) findViewById(R.id.button);
        textView = (TextView) findViewById(R.id.textView);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                int count = 0;
                for (int mGroupPosition = 0; mGroupPosition < listAdapter.getGroupCount(); mGroupPosition++) {
                    count = count + listAdapter.getNumberOfCheckedItemsInGroup(mGroupPosition);
                }
                textView.setText(ExpListViewAdapterWithCheckbox.SelTtem + "\n總金額:" + ExpListViewAdapterWithCheckbox.totalMoney + "元");

//                strBffer + "\n總共"+ TolCnt  +"個\n總金額"+totalMoney+"元",
            }
        });

    }

    final static String FINAL_LANDLORD_ID = "J3455888889";

    public void addHouseAddr(View target) {
        sbBuffer.append(etHouseAddr.getText().toString().trim());
        if (sbBuffer.length() == 0)
            Toast.makeText(this, "請先輸入房屋地址", Toast.LENGTH_SHORT).show();
        else {
            DatabaseReference mDatabase;
            mDatabase = FirebaseDatabase.getInstance().getReference(FINAL_LANDLORD_ID);
            Landlord landlord = new Landlord(sbBuffer.toString(), "1qaz2wsx");
            mDatabase.push().setValue(landlord);
        }
    }


    AlertDialog dialog; //讓自定Layout可有關閉功能
    View root;
    EditText et;
    Button confirm, cancel, confirmDelete, cancelDelete;
    TextView textDefineDialog, textDeleteDefineDialog;
    TextView txDescription, txDeleteDescription;


    public void delHouseAddr(View target) {

        String str = etHouseAddr.getText().toString().trim();
        Toast.makeText(this, strHouseKey, Toast.LENGTH_SHORT).show();
        LayoutInflater inflater = (LayoutInflater) this
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        root = inflater.inflate(R.layout.dialog_delete_layout, null);//找出根源樹,
        txDeleteDescription = root.findViewById(R.id.txDeleteDescription);
        confirmDelete = root.findViewById(R.id.btn_Deleteconfirm);
        cancelDelete = root.findViewById(R.id.btn_Deletecancel);
        txDeleteDescription.setText("請問是否要將房屋" + str + "資料刪除");
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
                mDatabase = FirebaseDatabase.getInstance().getReference(LandlordExpandable.FINAL_LANDLORD_ID);
                mDatabase = mDatabase.child(strHouseKey);
                mDatabase.removeValue();
                dialog.dismiss();
                etHouseAddr.setText(null);

            }
        });
        AlertDialog.Builder abc = new AlertDialog.Builder(this);
        abc.setView(root);
        dialog = abc.show();

//        if (sbBuffer.length()==0)
//            Toast.makeText(this,"請先輸入房屋地址",Toast.LENGTH_SHORT).show();
//        else {
//            DatabaseReference mDatabase;
//            mDatabase = FirebaseDatabase.getInstance().getReference(FINAL_LANDLORD_ID);
//            Landlord landlord = new Landlord(sbBuffer.toString(),"1qaz2wsx");
//            mDatabase.push().setValue(landlord);
//        }
    }


    /*
     * Preparing the list data
     */
    public static HashMap<String, ArrayList<String>> mChildColumnName;//第二層在db的欄位名稱
    ArrayList<String> alrCol;

    private void prepareListData() {
        mChildColumnName = new HashMap<String, ArrayList<String>>();//每次重新設定欄位名稱

        DatabaseReference reference_contacts = FirebaseDatabase.getInstance().getReference("J3455888889");
        reference_contacts.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                listDataHeader.clear();
                listDataChild.clear();
                listHouseKey.clear();

                int i = 0;
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    alrCol = new ArrayList<String>();
                    Landlord landlord = ds.getValue(Landlord.class);
//                        Log.d("fire===",ds.getKey());
                    listHouseKey.add(ds.getKey());
                    alrHouseID.add(landlord.getHouseID());
                    listDataHeader.add(landlord.getHouseAddr());
                    ListTenant = new ArrayList<String>();
//                        Log.d("fire0=11==",landlord.getHouseID().toString());
                    if (landlord.getHouseID().toString().equals("1qaz2wsx")) {
                        ListTenant.add("尚未有租客資料");
                        alrCol.add("No_tenant");
                    } else {//取出資料的同時,要記錄到欄位名稱,便於日後的修改,刪除
                        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
//                            formatter.format(landlord.getFromDate()))
                        ListTenant.add("房客身份證號碼 : " + ((landlord.getTenantID() == null) ? "" : landlord.getTenantID()));
                        alrCol.add("tenantID");
                        ListTenant.add("房客姓名 : " + ((landlord.getTenant() == null) ? "" : landlord.getTenant()));
                        alrCol.add("tenant");
                        ListTenant.add("租期開始日: " + ((landlord.getFromDate() == null) ? "" : formatter.format(landlord.getFromDate())));
                        alrCol.add("fromDate");
                        ListTenant.add("租期終止日 :" + ((landlord.getToDate() == null) ? "" : formatter.format(landlord.getToDate())));
                        alrCol.add("toDate");
                        ListTenant.add("房客留言 : " + ((landlord.getTenantNote() == null) ? "" : landlord.getTenantNote()));
                        alrCol.add("tenantNote");
                        ListTenant.add("房東留言 : " + ((landlord.getLandNote() == null) ? "" : landlord.getLandNote()));
                        alrCol.add("landNote");
                        ListTenant.add("上次收租日期 : " + ((landlord.getPrePayDate() == null) ? "" : formatter.format(landlord.getPrePayDate())));
                        alrCol.add("prePayDate");
                        ListTenant.add("下次收租日期 : " + ((landlord.getNextPayDate() == null) ? "" : formatter.format(landlord.getNextPayDate())));
                        alrCol.add("nextPayDate");
                    }
                    mChildColumnName.put(ds.getKey(), alrCol);
                    listDataChild.put(listDataHeader.get(i), ListTenant); // Header, Child data
                    i++;

//                    }
//                    flagFirst=false;
                }
                listAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        // Adding 第一層類別內容
//        listDataHeader.add("便當,快餐類");
//        listDataHeader.add("麵食類");
//        listDataHeader.add("快炒類");
//        listDataHeader.add("燒烤類");

//        // Adding child data //設定第一類的子項
//        List<String> top250 = new ArrayList<String>();
//        top250.add("紅燒/蔥爆牛肉飯 120元");
//        top250.add("泰式椒麻雞腿飯  120元");
//        top250.add("懷舊便當(控肉/排骨) 110元");
//        top250.add("炸大雞腿飯 110元");
//        top250.add("油雞飯 100元");
//        top250.add("雞排飯 100元");
//        top250.add("滷雞腿飯 100元");
        //設定第二類的子項
//        List<String> nowShowing = new ArrayList<String>();
//        nowShowing.add("牛肉麵 120元");
//        nowShowing.add("牛肉炒麵 100元");
//        nowShowing.add("牛肉湯餃 90元");
//        nowShowing.add("肉絲炒麵 80元");
//        nowShowing.add("家常麵(湯) 80元");
//        nowShowing.add("牛肉湯麵 70元");
//        //設定第三類的子項
//        List<String> comingSoon = new ArrayList<String>();
//        comingSoon.add("胡椒蝦 380元");
//        comingSoon.add("檸檬麵 380元");
//        comingSoon.add("宮保雞丁 200元");
//        comingSoon.add("乾煎鱈魚 200元");
//        comingSoon.add("薑絲大腸 200元");
//        //設定第四類的子項
//        List<String> burn = new ArrayList<String>();
//        burn.add("烤蝦 380元");
//        burn.add("牛小排 200元");

        // listDataChild HashMap<String, List<String>>
//        listDataChild.put(listDataHeader.get(0), top250); // Header, Child data
//        listDataChild.put(listDataHeader.get(1), nowShowing);
//        listDataChild.put(listDataHeader.get(2), comingSoon);
//        listDataChild.put(listDataHeader.get(3), burn);
    }
}
