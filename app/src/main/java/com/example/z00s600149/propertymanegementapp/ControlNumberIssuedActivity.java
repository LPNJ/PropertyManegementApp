package com.example.z00s600149.propertymanegementapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import dialog.ShowDialog;

/**
 * 発行された資産番号を表示するクラス
 * また、ハンディプリンタに印刷指示を送るActivityに遷移させる
 */
public class ControlNumberIssuedActivity extends AppCompatActivity implements View.OnClickListener {

    /**
     * 印刷画面遷移用ボタン
     */
    private Button mToPrint;
    /**
     * 資産番号発行用
     */
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
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            new ShowDialog(this).show(R.string.push);
        }
        return true;
    }
}
