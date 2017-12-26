package com.example.kevin.firebasetest4;

import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.support.annotation.ColorInt;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

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

public class LandlordListView extends AppCompatActivity {
    TextView txtlandlord,txtAlarm;
    String landordID = "";
    Myadapter myadapter;
    final String FINAL_LANDLORD_ID="J3455888889";
    ArrayList<String> alrAddr=new ArrayList<>();
    ArrayList<Boolean> alrAlarm=new ArrayList<>();
    ArrayList<String> alrTentNote=new ArrayList<>();
    ArrayList<Boolean> alrPayTent=new ArrayList<>();
    ArrayList<Boolean> alrPayLandlord=new ArrayList<>();
    ArrayList<String> alrHouseKey=new ArrayList<>();
    ArrayList<Date>alrPrePayDate=new ArrayList<Date>();
    ArrayList<Date>alrNextPayDate=new ArrayList<Date>();
//…    public HashMap<String, List<Boolean>> mTenantChkStatus = new HashMap<String, List<Boolean>>();//記錄房客是否付款


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landlord_list_view);

        ListView listView = (ListView) findViewById(R.id.list);


        txtlandlord = (TextView) findViewById(R.id.txtlandlord);
//        txtAlarm=(TextView)findViewById(R.id.txtAlarm);
        myadapter = new Myadapter(this);
        listView.setAdapter(myadapter);

        java.util.Date current=new java.util.Date();
        java.text.SimpleDateFormat sdf=new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String c=sdf.format(current);
//        Log.d("fire==date===" ,current);


        DatabaseReference reference_contacts = FirebaseDatabase.getInstance().getReference("J3455888889");
        reference_contacts.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
//                myadapter.clear();

                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
//                alrAlarm.clear();
                alrAlarm=new ArrayList<Boolean>();
                alrHouseKey.clear();
                alrTentNote.clear();
                alrPayTent.clear();
                alrPayLandlord.clear();
                alrAddr.clear();
                alrPrePayDate.clear();
                alrNextPayDate.clear();
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    Landlord landlord = ds.getValue(Landlord.class);
                    alrHouseKey.add(ds.getKey());
                    Log.d("fire==date==",ds.getKey());

                    if (landlord.getNextPayDate()!=null && new Date().after(landlord.getNextPayDate())) {
                        alrAlarm.add(true);
//                        txtAlarm.setText("房租已逾期");
//                        txtAlarm.setTextColor(ColorStateList.valueOf(3));
                        Log.d("fire==after","租客逾期未繳房租");
                    }
                    else {
                        alrAlarm.add(false);
                        Log.d("fire==after", "租客按時繳房租");
//                        txtAlarm.setText("房租未逾期");
                    }


                    alrPrePayDate.add(landlord.prePayDate);
                    alrNextPayDate.add(landlord.nextPayDate);
//                    myadapter.add(landlord.getHouseAddr());
                    alrAddr.add(landlord.getHouseAddr());
                    String str=(landlord.getTenantNote()==null)?"":landlord.getTenantNote();
                    str+="\n"+"房東留言:";
                    str+=(landlord.getLandNote()==null)?"":landlord.getLandNote();

                    str+="\n"+"上次收到房租日期:";
                    str+=(landlord.getPrePayDate()==null)?"":formatter.format(landlord.getPrePayDate());
//                    str+=(landlord.getPrePayDate()==null)?"":landlord.getPrePayDate();
                    str+="\n"+"下次收到房租日期:";
                    str+=(landlord.getNextPayDate()==null)?"":formatter.format(landlord.getNextPayDate());
//                    Log.d("date=",Date.from(Instant.now()).toString());
                    alrTentNote.add(str);
                    alrPayTent.add(landlord.getPayTent());
                    alrPayLandlord.add(landlord.getPayLandord());
                    if (txtlandlord.getText().length() == 0) {//將房東姓名顯示在上面
                        txtlandlord.setText(landlord.getLandName());
                        landordID=landlord.getLandordID();
                    }

                }
                myadapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void addHouse(View target){
        Intent intent=new Intent();
        intent.setClass(this,LandlordExpandable.class);
        Bundle bundle = new Bundle();
        bundle.putString("landordID",landordID);
        intent.putExtras(bundle);
        startActivity(intent);
    }




    class Myadapter extends BaseAdapter {
        private LayoutInflater myInflater;
        Context mContext;

        public Myadapter(Context context) {
            myInflater = LayoutInflater.from(context);
            this.mContext=context;
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
        EditText et_message;
        Button confirmMessage,cancelMessage;

        private DatabaseReference mDatabase;
        @Override
        public View getView(int i, View convertView, ViewGroup viewGroup) {
            final int position=i;
            mDatabase = FirebaseDatabase.getInstance().getReference(FINAL_LANDLORD_ID);

            Log.d("fire==i=",i+"");
            convertView = myInflater.inflate(R.layout.mysimple_list_item_1, null);
            TextView txTentNote=(TextView)convertView.findViewById(R.id.txTentNote);
            TextView txHouseAddr = (TextView) convertView.findViewById(R.id.txHouseAddr);
//            TextView txtAlarm=(TextView) convertView.findViewById(R.id.txtAlarm);
            CheckBox chkPayTent=(CheckBox) convertView.findViewById(R.id.chkTent);
            CheckBox chkPayLandlord=(CheckBox) convertView.findViewById(R.id.chkLand);
//            ImageButton imgBtnTalk=convertView.findViewById(R.id.imgbtnTalk) ;
            txHouseAddr.setText("房屋地址: " + alrAddr.get(i));
            txTentNote.setText("房客留言:" + alrTentNote.get(i));
            chkPayTent.setChecked(alrPayTent.get(i));
            chkPayLandlord.setChecked(alrPayLandlord.get(i));
            Log.d("fire==",""+i );
            if (alrAlarm.get(i)) {
                txtAlarm.setText("房租已逾期");
                txtAlarm.setTextColor(Color.parseColor("red"));
//                Log.d("fire==after", "租客逾期未繳房租");
            }else{
                txtAlarm.setText("房租未逾期");
            }

//            imgBtnTalk.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    LayoutInflater inflater = (LayoutInflater) mContext
//                            .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//                    root = inflater.inflate(R.layout.dialog_message_layout, null);//找出根源樹,
//                    et_message=root.findViewById(R.id.et_message);
//                    confirmMessage = root.findViewById(R.id.btm_confirmMessage);
//                    cancelMessage=root.findViewById(R.id.btn_cancelMessage);
//
//                    cancelMessage.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View view) {
//                            dialog.dismiss();
//                        }
//                    });
//                    confirmMessage.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View view) {
//
//                            mDatabase = mDatabase.child(alrHouseKey.get(position));
//                                Map<String, Object> nameMap = new HashMap<String, Object>();
//                                nameMap.put("landNote", et_message.getText().toString().trim());
//                                mDatabase.updateChildren(nameMap);
//                            dialog.dismiss();
//
//                        }
//                    });
//                    AlertDialog.Builder ab = new AlertDialog.Builder(mContext);
//                    ab.setView(root);
//                    dialog = ab.show();
//                }
//            });

            chkPayLandlord.setOnCheckedChangeListener(new CheckBox.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

//                        Log.d("fire==打勾==…", b + "," + position);
                        mDatabase = mDatabase.child(alrHouseKey.get(position));
                        Map<String, Object> nameMap = new HashMap<String, Object>();
                        nameMap.put("payLandord",b);
                        mDatabase.updateChildren(nameMap);
                    if (b) {
                        nameMap.put("prePayDate", new Date());
                        mDatabase.updateChildren(nameMap);
                    }



                }
            });


            return convertView;
        }
    }
}
