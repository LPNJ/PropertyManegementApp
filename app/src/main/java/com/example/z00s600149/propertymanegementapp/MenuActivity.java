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

import java.util.ArrayList;

import task.LogoutTask;
import task.ResultListener;
import task.mock.LogoutTaskMock;
import task.mock.NewAccountTaskMock;

public class MenuActivity extends AppCompatActivity {

    /** QRカメラ用ボタン */
    private Button mPropertyRegist;
    /** QRカメラ用ボタン */
    private Button mPropertyInfo;
    /** QRカメラ用ボタン */
    private Button mQRcodeReader;
    /** QRカメラ用ボタン */
    private Button mLogout;

    private LogoutTask mLogoutTask;

    private String mUserId;

    /**
     * デフォルトコンストラクタ
     */
    public MenuActivity() {
        super();
        mLogoutTask = new LogoutTaskMock();
        //mErrorMessage.put(-1, R.string.cannot_connect);
        //mErrorMessage.put(11, R.string.cannot_register_error);
        Log.i("Regist", "register activity contstructor");
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        mUserId = getIntent().getStringExtra(IntentKey.LOGIN_NAME);

        mPropertyRegist = (Button) findViewById(R.id.menu_button_propertyregist);
        mPropertyInfo = (Button) findViewById(R.id.menu_button_propertyinfo);
        mQRcodeReader = (Button) findViewById(R.id.menu_button_qrcodereader);
        mLogout = (Button) findViewById(R.id.menu_button_logout);
        MenuActivityOnClickListener listener = new MenuActivityOnClickListener();
        mPropertyRegist.setOnClickListener(listener);
        mPropertyInfo.setOnClickListener(listener);
        mQRcodeReader.setOnClickListener(listener);
        mLogout.setOnClickListener(listener);

    }

        class MenuActivityOnClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.menu_button_propertyregist: {
                    Intent intent = new Intent(MenuActivity.this, PropertyInfoActivity.class);
                    intent.putExtra(IntentKey.LOGIN_NAME,mUserId);
                    startActivity(intent);
                }
                break;
                case R.id.menu_button_propertyinfo: {
                    Intent intent = new Intent(MenuActivity.this, PropertySelectActivity.class);
                    startActivity(intent);
                }
                break;
                case R.id.menu_button_qrcodereader: {
//                    Intent intent = new Intent(MenuActivity.this, AnotherQRcodeReaderActivity.class);
                    Intent intent = new Intent(MenuActivity.this, AnotherQRcodeReaderActivity.class);
                    startActivity(intent);
                }
                break;

                case R.id.menu_button_logout: {
                    new AlertDialog.Builder(MenuActivity.this)
                            .setMessage("ログアウトしますか？")
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    mLogoutTask.execute("",mResultListener);
                                }
                            })
                            .setNegativeButton("Cancel", null)
                            .create()
                            .show();

                }
                break;

            }
        }
    }


    private ResultListener<Integer> mResultListener = new ResultListener<Integer>() {

        @Override
        public void onResult(Integer result) {

            if (result != 0) {
                new AlertDialog.Builder(MenuActivity.this)
                        .setMessage("ログアウトできませんでした")
                        .setPositiveButton("OK", null)
                        .create()
                        .show();
                return;
            }
            else if(result == 0){
                Intent intent = new Intent(MenuActivity.this, LoginActivity.class);
                startActivity(intent);
            }

        }

    };

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // BackBtnアクション
        if(keyCode==KeyEvent.KEYCODE_BACK){
            new AlertDialog.Builder(MenuActivity.this)
                    .setMessage("ログアウトしますか？")
                    .setPositiveButton("OK",new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            mLogoutTask.execute("",mResultListener);
                        }
                    })
                    .setNegativeButton("Cancel",null)
                    .create()
                    .show();
        }
        return true;
    }

}


