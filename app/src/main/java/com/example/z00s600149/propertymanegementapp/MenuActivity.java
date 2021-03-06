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

/**
 * 資産情報登録、資産情報選択、QRカメラ起動画面に遷移させるためのActivity
 */
public class MenuActivity extends AppCompatActivity implements View.OnClickListener{

    private static final String TAG = "MenuAct";

    /*資産情報画面遷移用ボタン*/
    private Button mPropertyRegister;
    /*資産情報一覧確認画面遷移用ボタン*/
    private Button mPropertyInfo;
    /*資産情報一覧確認遷移用ボタン*/
    private Button mQRCodeReader;
    /** QRカメラ画面用遷移ボタン */
    private Button mLogout;

    /*デフォルトコンストラクタ*/
    public MenuActivity() {
        super();
        Log.i(TAG, "MENU Activity start");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        //IDと対応付け
        mPropertyRegister = (Button) findViewById(R.id.menu_button_propertyregist);
        mPropertyInfo = (Button) findViewById(R.id.menu_button_propertyinfo);
        mQRCodeReader = (Button) findViewById(R.id.menu_button_qrcodereader);
        mLogout = (Button) findViewById(R.id.menu_button_logout);

        //ボタン押下の動作
        mPropertyRegister.setOnClickListener(this);
        mPropertyInfo.setOnClickListener(this);
        mQRCodeReader.setOnClickListener(this);
        mLogout.setOnClickListener(this);

    }

    @Override
    //ボタン押下後の画面遷移など
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.menu_button_propertyregist: {
                Intent intent = new Intent(MenuActivity.this, PropertyInfoActivity.class);
                startActivity(intent);
            }
            break;
            case R.id.menu_button_propertyinfo: {
                Intent intent = new Intent(MenuActivity.this, PropertySelectActivity.class);
                startActivity(intent);
            }
            break;
            case R.id.menu_button_qrcodereader: {
                Intent intent = new Intent(MenuActivity.this, QRCodeReaderActivity.class);
                startActivity(intent);
            }
            break;
            case R.id.menu_button_logout: {
                new AlertDialog.Builder(MenuActivity.this)
                        .setMessage(R.string.logout)
                        .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent intent = new Intent(MenuActivity.this, LoginActivity.class);
                                startActivity(intent);
                            }
                        })
                        .setNegativeButton(R.string.cancel, null)
                        .create()
                        .show();
            }
            break;
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // BackBtnアクション
        if(keyCode==KeyEvent.KEYCODE_BACK){
            new AlertDialog.Builder(MenuActivity.this)
                    .setMessage(R.string.logout)
                    .setPositiveButton(R.string.ok,new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intent = new Intent(MenuActivity.this, LoginActivity.class);
                            startActivity(intent);
                        }
                    })
                    .setNegativeButton(R.string.cancel,null)
                    .create()
                    .show();
        }
        return true;
    }
}


