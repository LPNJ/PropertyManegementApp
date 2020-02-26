package com.example.z00s600149.propertymanegementapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import entity.PropertyInfo;
import task.serialize.PropertyRegistRequest;

public class AnotherQRcodeReaderActivity extends AppCompatActivity {

    private Button mQRread;

    private String mControlNumber = "10001";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_another_qrcode_reader);

        mQRread = (Button) findViewById(R.id.qr_button);
        AnotherQRcodeReaderOnClickListener listener = new AnotherQRcodeReaderOnClickListener();
        mQRread.setOnClickListener(listener);


    }

    class AnotherQRcodeReaderOnClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.qr_button: {
                    Intent intent = new Intent(AnotherQRcodeReaderActivity.this, PropertyReferenceActivity.class);
                    intent.putExtra(IntentKey.CONTROL_NUMBER, mControlNumber);
                    startActivity(intent);
                }
                break;
            }
        }
    }

}
