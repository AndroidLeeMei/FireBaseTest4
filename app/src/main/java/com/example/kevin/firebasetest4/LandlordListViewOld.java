package com.example.kevin.firebasetest4;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * Created by kevin on 2017/11/24.
 */

public class LandlordListViewOld extends AppCompatActivity {
    TextView txtlandlord;
    String landordName = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landlord_list_view);

        ListView listView = (ListView) findViewById(R.id.list);
        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                R.layout.mysimple_list_item_1,
                R.id.txHouseAddr);
        listView.setAdapter(adapter);

        txtlandlord = (TextView) findViewById(R.id.txtlandlord);


        DatabaseReference reference_contacts = FirebaseDatabase.getInstance().getReference("J3455888889");
        reference_contacts.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                adapter.clear();
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    Landlord landlord = ds.getValue(Landlord.class);
                    adapter.add(landlord.getHouseAddr());
//                    if (landordName=="")
                    if (txtlandlord.getText().length() == 0)
                        txtlandlord.setText(landlord.getLandName());
//                        landordName=landlord.getName();

//                    String str=ds.child("addr").getValue().toString();
//                    String []aryAddr=str.substring(1,str.length()-1).split(",");
//                    for (String s:aryAddr){
//                        str=aryAddr[0]+"\n"+aryAddr[1]+"\n"+aryAddr[2]+"\n"+aryAddr[3];
//                    }
//                    adapter.add("地址:" + ds.child("H345588888H001").child("addr").getValue().toString()+"\n");
//                    for …(DataSnapshot dss : ds.getChildren() ){
//                        adapter.add(dss.child("name")+"");
//                    }

//                    Log.d("firebase==",ds.child("addr").getKey()+"");

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
