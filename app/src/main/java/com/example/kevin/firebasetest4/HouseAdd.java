package com.example.kevin.firebasetest4;

import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

public class HouseAdd extends AppCompatActivity {
    TextView tv[]=new TextView[6];
    Button bt[]=new Button[6];
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_house_add);

        LinearLayout linearLayout=new LinearLayout(HouseAdd.this);
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        setContentView(linearLayout);

        for (int i=0;i<tv.length;i++){
            tv[i]=new TextView(this);

            bt[i]=new Button(this);
            bt[i].setText("No=" + i);

        }

        //button設定
        bt[0].setTextColor(Color.RED);
        bt[1].setTextColor(Color.BLUE);
        bt[2].setTextColor(Color.YELLOW);

        //TextView設定
        tv[0].setTextColor(Color.BLACK);
        tv[0].setBackgroundColor(Color.RED);
        tv[0].setTextSize(20);

//        tv[1].setTextColor(Color.BLUE);
        tv[1].setBackgroundColor(Color.GRAY);
        tv[1].setTextSize(20);
        tv[2].setTextColor(Color.CYAN);
        tv[2].setBackgroundColor(Color.YELLOW);
        tv[2].setTextSize(30);
        tv[0].setText("房東姓名");
        tv[2].setText("房東地址");
        tv[3].setText("房東電話");
//        tv[3].setTextColor(Color.DKGRAY);
//        tv[4].setTextColor(Color.BLACK);
//        tv[5].setTextColor(Color.BLACK);

        tv[0].setTypeface(Typeface.create(Typeface.MONOSPACE,Typeface.BOLD));//1.字型Typeface.SERIF2.style
        tv[1].setTypeface(Typeface.create(Typeface.SERIF,Typeface.ITALIC));//1.字型Typeface.SERIF2.style
        tv[2].setTypeface(Typeface.create(Typeface.SERIF,Typeface.NORMAL));//1.字型Typeface.SERIF2.style



        linearLayout.addView(tv[0]);
        linearLayout.addView(tv[1]);
        linearLayout.addView(tv[2]);

        linearLayout.addView(bt[0]);
        linearLayout.addView(bt[1]);
    }
}
