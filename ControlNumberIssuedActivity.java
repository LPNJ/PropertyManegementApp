package com.example.z00s600149.propertymanegementapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ControlNumberIssuedActivity extends AppCompatActivity {

    /** メニュー画面遷移用ボタン */
    private Button mToMenu;
    /** メニュー画面遷移用ボタン */
    private TextView mControlNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_control_number_issued);

        mToMenu = (Button) findViewById(R.id.control_button_tomenu);
        mControlNumber = (TextView) findViewById(R.id.control_number);
        mControlNumber.setText(getIntent().getStringExtra(IntentKey.CONTROL_NUMBER));
        ControlNumberIssuedActivityOnClickListener listener = new ControlNumberIssuedActivityOnClickListener();
        mToMenu.setOnClickListener(listener);
    }

    class ControlNumberIssuedActivityOnClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.control_button_tomenu: {
                    Intent intent = new Intent(ControlNumberIssuedActivity.this, PrinterActivity.class);
                    startActivity(intent);
                }
                break;
            }
        }
    }
}
