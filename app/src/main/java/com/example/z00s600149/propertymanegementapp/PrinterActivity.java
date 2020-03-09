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

/**
 * 印刷指示を送るためのActivity
 */
public class PrinterActivity extends AppCompatActivity implements View.OnClickListener{
    private static final String TAG = "PrinterAct";

    private final PrintService mPrinterService;

    /*リクエストコード*/
    static final int REQUEST_ENABLE_BT = 1;

    // TODO private
    /*Bluetooth使用可否*/
    private Button mMenu;

    public PrinterActivity() {
        mPrinterService = HandyPrintService.getInstance();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_printer);

        mMenu = (Button) findViewById(R.id.printer_buttton_menu);
        mMenu.setOnClickListener(this);

        mPrinterService.print(getIntent().getStringExtra(IntentKey.CONTROL_NUMBER),this);
    }

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
                    // TODO stringsについか
                    .setMessage(R.string.to_menu)
                    // TODO stringsについか
                    .setPositiveButton(R.string.ok,new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intent = new Intent(PrinterActivity.this, MenuActivity.class);
                            startActivity(intent);
                        }
                    })
                    // TODO stringsについか
                    .setNegativeButton(R.string.cancel,null)
                    .create()
                    .show();
        }
        return true;
    }
}
