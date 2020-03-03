package com.example.z00s600149.propertymanegementapp;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 * TODO どういう役割のクラスか書く。何の画面かを端的に説明する
 * ほかのクラスも同様
 */
public class ControlNumberIssuedActivity extends AppCompatActivity implements View.OnClickListener{

    /** 印刷画面遷移用ボタン */
    private Button mToPrint;
    /** 資産番号発行用 */
    private TextView mControlNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_control_number_issued);

        mToPrint = (Button) findViewById(R.id.control_button_tomenu);
        mControlNumber = (TextView) findViewById(R.id.control_number);
        mControlNumber.setText(getIntent().getStringExtra(IntentKey.CONTROL_NUMBER));
        mToPrint.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.control_button_tomenu: {
                Intent intent = new Intent(ControlNumberIssuedActivity.this, PrinterActivity.class);
                intent.putExtra(IntentKey.CONTROL_NUMBER, getIntent().getStringExtra(IntentKey.CONTROL_NUMBER));
                startActivity(intent);
            }
            break;
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // BackBtnアクション
        if(keyCode==KeyEvent.KEYCODE_BACK){
            new AlertDialog.Builder(ControlNumberIssuedActivity.this)
                    // TODO メッセージと”OK”はres/values/strings.xmlに追加して、それを使う
                    // ここだけじゃなくほかのところも同様
                    .setMessage("印刷ボタンを押下してください")
                    .setNegativeButton("OK",null)
                    .create()
                    .show();
        }
        return true;
    }

}
