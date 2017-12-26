package com.example.kevin.firebasetest4;

/**
 * Created by dbhat on 15-03-2016.
 */

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Context;
import android.graphics.Color;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// Eclipse wanted me to use a sparse array instead of my hashmaps, I just suppressed that suggestion
@SuppressLint("UseSparseArrays")
public class ExpListViewAdapterWithCheckbox extends BaseExpandableListAdapter {


    public static String SelTtem;
    public String strBffer = "";
    public static int totalMoney;
    public static int TolCnt;
    public static int idSpinner;
//    ArrayList<String> alrHouseID=new ArrayList();
//    String[] aryColumn={"tenantID","tenant"};
    ArrayList<Integer> alrCount=new ArrayList();
    ArrayList<Integer> alrMoney=new ArrayList();

    // Define activity context
    private Context mContext;

    /*
     * Here we have a Hashmap containing a String key
     * (can be Integer or other type but I was testing
     * with contacts so I used contact name as the key)
    */
    private HashMap<String, List<String>> mListDataChild;

    // ArrayList that is what each key in the above
    // hashmap points to
    private ArrayList<String> mListDataGroup;

    // Hashmap for keeping track of our checkbox check states
    private HashMap<Integer, boolean[]> mChildCheckStates;
    private HashMap<Integer, int[]> mChildNumberStates;

    // Our getChildView & getGroupView use the viewholder patter
    // Here are the viewholders defined, the inner classes are
    // at the bottom
    private ChildViewHolder childViewHolder;
    private GroupViewHolder groupViewHolder;


    /*
          *  For the purpose of this document, I'm only using a single
     *	textview in the group (parent) and child, but you're limited only
     *	by your XML view for each group item :)
    */
    private String groupText;
    private String childText;

    /*  Here's the constructor we'll use to pass in our calling
     *  activity's context, group items, and child items
    */
    public ExpListViewAdapterWithCheckbox(Context context, ArrayList<String> listDataGroup, HashMap<String, List<String>> listDataChild) {

        mContext = context;
        mListDataGroup = listDataGroup;
        mListDataChild = listDataChild;

        // Initialize our hashmap containing our check states here
        mChildCheckStates = new HashMap<Integer, boolean[]>();
        mChildNumberStates = new HashMap<Integer, int[]>();
    }

    public int getNumberOfCheckedItemsInGroup(int mGroupPosition) {  //取得一共有幾個checkbox被選擇
        boolean getChecked[] = mChildCheckStates.get(mGroupPosition);
        int count = 0;
        if (getChecked != null) {
            for (int j = 0; j < getChecked.length; ++j) {
                if (getChecked[j] == true) count++;
            }
        }
        return count;
    }

    public int getNumberOfNumberItemsInGroup(int mGroupPosition) {  //取得一共有幾個spinner被選擇
        int getChecked[] = mChildNumberStates.get(mGroupPosition);
        int count = 0;
        if (getChecked != null) {
            for (int j = 0; j < getChecked.length; ++j) {
                if (getChecked[j] >0) count++;
            }
        }
        return count;
    }

    @Override
    public int getGroupCount() {
        return mListDataGroup.size();
    }

    /*
     * This defaults to "public object getGroup" if you auto import the methods
     * I've always make a point to change it from "object" to whatever item
     * I passed through the constructor
    */
    @Override
    public String getGroup(int groupPosition) {
        return mListDataGroup.get(groupPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }


    @Override
    public View getGroupView(int groupPosition, boolean isExpanded,
                             View convertView, ViewGroup parent) {
        final  int fgroupPosition=groupPosition;
        //  I passed a text string into an activity holding a getter/setter
        //  which I passed in through "ExpListGroupItems".
        //  Here is where I call the getter to get that text
        groupText = getGroup(groupPosition);

        if (convertView == null) {

            LayoutInflater inflater = (LayoutInflater) mContext
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.list_group, null);

            // Initialize the GroupViewHolder defined at the bottom of this document
            groupViewHolder = new GroupViewHolder();
            groupViewHolder.mGroupText = (TextView) convertView.findViewById(R.id.lblListHeader);
//            groupViewHolder.mGroupbtnAdd=(Button)convertView.findViewById(R.id.btnAddTenant) ;
//            groupViewHolder.mGroupbtnDel=(Button)convertView.findViewById(R.id.btnDelHouse) ;
            convertView.setTag(groupViewHolder);

//            textViewHolder=new TextViewHolder();
//            textViewHolder.mTextView=(TextView) convertView.findViewById(R.id.textView);
        } else {

            groupViewHolder = (GroupViewHolder) convertView.getTag();
        }

        groupViewHolder.mGroupText.setText(groupText);
        return convertView;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return mListDataChild.get(mListDataGroup.get(groupPosition)).size();
    }

    /*
     * This defaults to "public object getChild" if you auto import the methods
     * I've always make a point to change it from "object" to whatever item
     * I passed through the constructor
    */
    @Override
    public String getChild(int groupPosition, int childPosition) {
        return mListDataChild.get(mListDataGroup.get(groupPosition)).get(childPosition);
    }



    @Override
    public long getChildId(int groupPosition, int childPosition) {

        return childPosition;
    }

    AlertDialog dialog; //讓自定Layout可有關閉功能
    View root;
    EditText et;
    Button confirm,cancel,confirmDelete,cancelDelete;
    TextView textDefineDialog,textDeleteDefineDialog;
    TextView txDescription,txDeleteDescription;

    private DatabaseReference mDatabase;

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
//        Log.d("getChildView start" ,groupPosition + ", "+childPosition);
        final int mGroupPosition = groupPosition;
        final int mChildPosition = childPosition;

        childText = getChild(mGroupPosition, mChildPosition);


        if (convertView == null) {

            LayoutInflater inflater = (LayoutInflater) this.mContext
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.list_item, null);

            childViewHolder = new ChildViewHolder();

            childViewHolder.mChildText = (TextView) convertView
                    .findViewById(R.id.lblListItem);

            childViewHolder.mbtnDelete=(ImageButton) convertView.findViewById(R.id.imgbtnDelete);
//            childViewHolder.mbtnUpdate=(ImageButton)convertView.findViewById(R.id.imgbtnUpdate);

            convertView.setTag(R.layout.list_item, childViewHolder);

        } else {

            childViewHolder = (ChildViewHolder) convertView
                    .getTag(R.layout.list_item);
        }
        textDefineDialog = (TextView)convertView.findViewById(R.id.textDefineDialog);
        childViewHolder.mChildText.setText(childText);

        //child的刪除事件監聽
        childViewHolder.mbtnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String strKey=LandlordExpandable.listHouseKey.get(mGroupPosition);
                final String strColName=LandlordExpandable.mChildColumnName.get(strKey).get(mChildPosition);
                LayoutInflater inflater = (LayoutInflater) mContext
                        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                root = inflater.inflate(R.layout.dialog_delete_layout, null);//找出根源樹,
                txDeleteDescription=root.findViewById(R.id.txDeleteDescription);
                confirmDelete =  root.findViewById(R.id.btn_Deleteconfirm);
                cancelDelete=root.findViewById(R.id.btn_Deletecancel);
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
                        mDatabase = FirebaseDatabase.getInstance().getReference(LandlordExpandable.FINAL_LANDLORD_ID);
                            mDatabase = mDatabase.child(LandlordExpandable.listHouseKey.get(mGroupPosition));
                            Map<String, Object> nameMap = new HashMap<String, Object>();
                            nameMap.put(strColName, null);
                            mDatabase.updateChildren(nameMap);

                        dialog.dismiss();

                    }
                });
                AlertDialog.Builder abc = new AlertDialog.Builder(mContext);
                abc.setView(root);
                dialog = abc.show();
            }
        });

        //child的修改事件監聽
        childViewHolder.mbtnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String strKey = LandlordExpandable.listHouseKey.get(mGroupPosition);
                final String strColName = LandlordExpandable.mChildColumnName.get(strKey).get(mChildPosition);
                mDatabase = FirebaseDatabase.getInstance().getReference(LandlordExpandable.FINAL_LANDLORD_ID);

                if (strColName.equals("fromDate")||strColName.equals("toDate")||strColName.equals("nextPayDate")||strColName.equals("prePayDate")) {
                    Date now = new Date();
                    DatePickerDialog dpd=new DatePickerDialog(mContext, new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                            String input = i +"-"+(i1+1)+"-"+i2;

                            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                            Date t = null;
                            try{
                                t = formatter.parse(input);
                            }catch(ParseException e){
                            }

                            mDatabase = mDatabase.child(strKey);
                            Map<String, Object> nameMap = new HashMap<>();
                            nameMap.put(strColName, t);
                            mDatabase.updateChildren(nameMap);
                            Log.d("fire=date=",input);

                        }
                    },now.getYear()+1900,now.getMonth(),now.getDay()); //畫面出來預設值  2017,12,05
                    dpd.show();
                }else
                    openDialog(strKey, strColName, R.layout.dialog_layout);
            }
        });
        return convertView;
    }

    public void openDialog(final String strKey,final String strColName,int intLayout){
        LayoutInflater inflater = (LayoutInflater) mContext
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        root = inflater.inflate(intLayout, null);//找出根源樹,
        txDescription=(TextView)root.findViewById(R.id.txDescription);
        et = (EditText) root.findViewById(R.id.et_tel);  //若不使用root,則它會去找主畫面的layout的元件
        confirm = (Button) root.findViewById(R.id.btm_confirm);
        cancel=(Button)root.findViewById(R.id.btn_cancel);

        if (strColName.equals("No_tenant"))
            txDescription.setText("請輸入租客姓名");
        else
            txDescription.setText("請輸入修改內容");

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (strColName.equals("No_tenant")) { //當沒有租客資料時
                    mDatabase = mDatabase.child(strKey);
                    Map<String, Object> nameMap = new HashMap<String, Object>();
                    nameMap.put("tenant", et.getText().toString().trim());
                    mDatabase.updateChildren(nameMap);
                    nameMap.put("houseID", strKey);
                    mDatabase.updateChildren(nameMap);
                }else{
                    mDatabase = mDatabase.child(strKey);
                    Map<String, Object> nameMap = new HashMap<String, Object>();
                    nameMap.put(strColName, et.getText().toString().trim());
                    mDatabase.updateChildren(nameMap);
                }
                dialog.dismiss();

            }
        });
        AlertDialog.Builder ab = new AlertDialog.Builder(mContext);
        ab.setView(root);
        dialog = ab.show();
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    public final class GroupViewHolder {
        TextView mGroupText;
    }



    public final class ChildViewHolder {
        TextView mChildText;
        ImageButton mbtnUpdate;
        ImageButton mbtnDelete;
    }



//    public Object getChildObject(int groupPosition, int childPosititon) {
//        return this.mListDataChild.get(this.mListDataGroup.get(groupPosition))
//                .get(childPosititon);
//    }
//
//    @Override
//    public long getChildId(int groupPosition, int childPosition) {
//
//        return childPosition;
//    }




}


