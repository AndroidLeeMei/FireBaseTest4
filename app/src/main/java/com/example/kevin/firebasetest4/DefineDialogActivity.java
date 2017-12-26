package com.example.kevin.firebasetest4;

//import android.support.v7.app.AppCompatActivity;
//import android.os.Bundle;
//
//public class DefineDialogActivity extends AppCompatActivity {
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_define_dialog);
//    }
//}

import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class DefineDialogActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_define_dialog);

        textDefineDialog = (TextView) findViewById(R.id.textDefineDialog);

        findDialogViews();
        openOptionsDialog();

    }

    AlertDialog dialog; //讓自定Layout可有關閉功能
    View root;
    EditText et;
    Button confirm;
    TextView textDefineDialog;

    //找出自定ｌａｙｏｕｔ裏的元件,待對話框有顯示出來才可執行此方法
    void findDialogViews() {
        //setC
        root = getLayoutInflater().inflate(R.layout.dialog_layout, null);//找出根源樹,
        et = (EditText) root.findViewById(R.id.et_tel);  //若不使用root,則它會去找主畫面的layout的元件
        confirm = (Button) root.findViewById(R.id.btm_confirm);
        confirm.setOnClickListener(dialoglistener);
    }

    View.OnClickListener dialoglistener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
//            System.out.println("按下自訂對話的按鈕");
            textDefineDialog.setText("輸入的電話號碼:" + et.getText());
            textDefineDialog.setTextColor(Color.BLUE);
            textDefineDialog.setTextSize(20);
            dialog.dismiss();
        }
    };

    void openOptionsDialog() {

        //裏下為電話號碼顯示的畫面
        AlertDialog.Builder ab = new AlertDialog.Builder(this);
//////        ab.setTitle(R.string.dialog_title);
////        ab.setTitle(getString(R.string.dialog_title));
////        ab.setMessage("我的內容");//
//        ab.setTitle("我的標題").setMessage("我的內容").show();//
//        ab.setPositiveButton("確定", dialogListener);
////        ab.setNeutralButton("首頁",dialogListener);
////        ab.setView(R.layout.dialog_layout);  //這一段無法執行關閉
        ab.setView(root);
        dialog = ab.show();


    }

    DialogInterface.OnClickListener dialogListener = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {

        }
    };
}
