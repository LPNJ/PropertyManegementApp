package com.example.z00s600149.propertymanegementapp;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;

import service.HandyPrintService;
import service.PrintService;

public class PrinterActivity extends AppCompatActivity {
    private static final String TAG = "時間測定";

    private final PrintService mPrinterService;

    /*リクエストコード*/
    static final int REQUEST_ENABLE_BT = 1;

    /*Bluetooth使用可否*/
    Button mMenu;

    private long mStartTime;

    public PrinterActivity() {
        mPrinterService = HandyPrintService.getInstance();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_printer);

        mMenu = (Button) findViewById(R.id.printer_buttton_menu);
        PrinterActivityOnClickListener listener = new PrinterActivityOnClickListener();
        mMenu.setOnClickListener(listener);

        mPrinterService.print(getIntent().getStringExtra(IntentKey.CONTROL_NUMBER),this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    class PrinterActivityOnClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.printer_buttton_menu: {
                    mPrinterService.terminate();
                    Intent intent = new Intent(PrinterActivity.this, MenuActivity.class);
                    startActivity(intent);
                }
                break;
            }
        }
    }

    /**結果取得
     *@param requestCode リクエスト
     *@param resultCode  リザルト
     *@param data        データ
     **/

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.i(TAG, "onActivityResult requestCode = "+requestCode + " ResultCode = "+resultCode);
        if (requestCode == REQUEST_ENABLE_BT && resultCode == Activity.RESULT_OK) {
            Log.i(TAG, "onActivityResult bluetooth on.");
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // BackBtnアクション
        if(keyCode==KeyEvent.KEYCODE_BACK){
            new AlertDialog.Builder(PrinterActivity.this)
                    .setMessage("メニュー画面に戻りますか？")
                    .setPositiveButton("OK",new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intent = new Intent(PrinterActivity.this, MenuActivity.class);
                            startActivity(intent);
                        }
                    })
                    .setNegativeButton("Cancel",null)
                    .create()
                    .show();
        }
        return true;
    }
}
