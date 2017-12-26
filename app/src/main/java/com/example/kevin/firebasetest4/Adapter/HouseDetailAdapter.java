package com.example.kevin.firebasetest4.Adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.kevin.firebasetest4.AddTenantActivity;
import com.example.kevin.firebasetest4.DetailActivity;
import com.example.kevin.firebasetest4.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by kevin on 2017/12/3.
 */

public class HouseDetailAdapter extends BaseAdapter {
    private LayoutInflater myInflater;
    Context mContext;

    public HouseDetailAdapter(Context context) {
        myInflater = LayoutInflater.from(context);
        this.mContext=context;
    }

    @Override
    public int getCount() {
        return DetailActivity.alrAddr.size();
    }

    @Override
    public Object getItem(int i) {
        return DetailActivity.alrAddr.size();
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
//        mDatabase = FirebaseDatabase.getInstance().getReference(DetailActivity.FINAL_USER_ID);
        mDatabase = FirebaseDatabase.getInstance().getReference("House");

        final int position=i;

        Log.d("fire==i=",i+"");
        convertView = myInflater.inflate(R.layout.house_detail_adapter_layout, null);
        TextView txTentNote=(TextView)convertView.findViewById(R.id.txTentNote);
        TextView txHouseAddr = (TextView) convertView.findViewById(R.id.txHouseAddr);
//        TextView txtAlarm=(TextView) convertView.findViewById(R.id.txtAlarm);
        CheckBox chkPayTent=(CheckBox) convertView.findViewById(R.id.chkTent);
        CheckBox chkPayLandlord=(CheckBox) convertView.findViewById(R.id.chkLand);
//        Button addTenant=convertView.findViewById(R.id.btnAddTenant) ;
//        Button addMessage=convertView.findViewById(R.id.btnAddTenant) ;
//        Button addTenant=convertView.findViewById(R.id.btnAddTenant) ;
        txHouseAddr.setText(DetailActivity.alrAddr.get(i));
//        txTentNote.setText( DetailActivity.alrTentNote.get(i));
//        chkPayTent.setChecked(DetailActivity.alrPayTent.get(i));
//        chkPayLandlord.setChecked(DetailActivity.alrPayLandlord.get(i));
        Log.d("fire==",""+i );
//            if (alrAlarm.get(i)) {
//                txtAlarm.setText("房租已逾期");
//                txtAlarm.setTextColor(Color.parseColor("red"));
////                Log.d("fire==after", "租客逾期未繳房租");
//            }else{
//                txtAlarm.setText("房租未逾期");
//            }

//        addTenant.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent=new Intent();
//                intent.setClass(DetailActivity.this,AddTenantActivity.class);
//                Bundle bundle = new Bundle();
//                bundle.putString("landordID",landordID);
//                intent.putExtras(bundle);
//                startActivity(intent);

//            }
//        });



        return convertView;
    }
}
