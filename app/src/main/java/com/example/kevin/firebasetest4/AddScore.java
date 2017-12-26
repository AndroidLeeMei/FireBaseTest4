package com.example.kevin.firebasetest4;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.example.kevin.firebasetest4.FireBase.PriceData;
import com.example.kevin.firebasetest4.FireBase.Rent;
import com.example.kevin.firebasetest4.FireBase.Score;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class AddScore extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_score);


        //ADD DATA
//        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
//
//        Score sore=new Score("CCC","8");
//
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("Rent");
        mDatabase.orderByChild("dateString").limitToLast(10).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
//                    Log.d("FireBaseTraining1",ds.getKey());
                    Log.d("FireBaseTraining1",ds.getValue().toString());

                PriceData priceData = ds.getValue(PriceData.class);
                Log.d("FireBaseTraining", "name = " + priceData.getDateString() + " , Age = " + priceData.getPrice());
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


//        mDatabase.push().setValue(sore);
//        mDatabase.child("Score").orderByChild("score").limitToLast(10);
//        mDatabase.addValueEventListener(valueEventListener);


    }

    ValueEventListener valueEventListener = new ValueEventListener() {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
            Log.d("FireBaseTraining0", " snapshot.getValue() = " + dataSnapshot.getValue());
            for (DataSnapshot ds : dataSnapshot.getChildren()) {
                Log.d("FireBaseTraining1",ds.getKey());
                Log.d("FireBaseTraining1",ds.getValue().toString());

//                Score score = ds.getValue(Score.class);
//                Log.d("FireBaseTraining", "name = " + score.getName() + " , Age = " + score.getScore());
            }
        }

        @Override
        public void onCancelled(DatabaseError databaseError) {
            // Getting Post failed, log a message
            Log.w("realdb", "loadPost:onCancelled", databaseError.toException());
            // ...
        }
    };
}
