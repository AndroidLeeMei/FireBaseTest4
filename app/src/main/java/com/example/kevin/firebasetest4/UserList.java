package com.example.kevin.firebasetest4;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class UserList extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_list);

        ListView listView = (ListView) findViewById(R.id.list);
        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1,
                android.R.id.text1);
        listView.setAdapter(adapter);
        DatabaseReference reference_contacts = FirebaseDatabase.getInstance().getReference("users");

        reference_contacts.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                adapter.clear();
                for (DataSnapshot ds : dataSnapshot.getChildren() ){
                    User user=ds.getValue(User.class);
                    adapter.add(user.getName());

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
