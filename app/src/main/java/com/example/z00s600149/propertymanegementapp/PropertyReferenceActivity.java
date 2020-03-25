package com.example.z00s600149.propertymanegementapp;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import dialog.ShowDialog;
import entity.LoginUserNameHolder;
import request.DeletePropertyRequest;
import response.GetReferencePropertyResponse;
import task.AsyncTaskListener.CallbackListener;
import webApi.WebApi;
import webApi.WebApiImpl;

/**
 * 選択した資産情報を表示させるActivity
 * 削除の操作もできる
 */
public class PropertyReferenceActivity extends AppCompatActivity implements View.OnClickListener{

    private static final String TAG = "PropertyReferenceAct";

    /*資産管理者用ID*/
    private TextView mManager;
    /*資産利用用ID*/
    private TextView mUser;
    /*資産保管場所用ID*/
    private TextView mPlace;
    /*資産番号ID*/
    private TextView mControlNumber;
    /*製品名用ID*/
    private TextView mProduct;
    /*購入区分用ID*/
    private TextView mPurchase;
    /*資産区分用ID*/
    private TextView mAssets;
    /*補足用ID*/
    private TextView mRemark;
    /*編集画面遷移用ボタン*/
    private Button mEditButton;
    /*削除用ボタン*/
    private Button mDeleteButton;
    /*印刷画面遷移用ボタン*/
    private Button mPrinterButton;

    private WebApi mWebApi;

    /*デフォルトコンストラクタ*/
    public PropertyReferenceActivity() {
        super();
        mWebApi = new WebApiImpl();
        Log.i(TAG, "PropertyReference activity start");
    }

    /*デフォルトコンストラクタ*/
    public PropertyReferenceActivity(WebApi webApi) {
        super();
        mWebApi = webApi;
        Log.i(TAG, "PropertyReference activity start");
    }

    void setApi(WebApi webApi) {
        mWebApi = webApi;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_property_reference);

        //ID対応付け
        mManager = (TextView) findViewById(R.id.reference_manager);
        mUser = (TextView) findViewById(R.id.reference_user);
        mPlace = (TextView) findViewById(R.id.reference_place);
        mControlNumber = (TextView) findViewById(R.id.reference_controlnumber);
        mProduct = (TextView) findViewById(R.id.reference_product);
        mPurchase = (TextView) findViewById(R.id.reference_purchase);
        mAssets = (TextView) findViewById(R.id.reference_assets);
        mRemark = (TextView) findViewById(R.id.reference_remark);
        mEditButton = (Button) findViewById(R.id.reference_edit);
        mDeleteButton = (Button) findViewById(R.id.reference_delete);
        mPrinterButton = (Button) findViewById(R.id.reference_print);

        //サーバー接続（名前取得）

        mWebApi.getReferenceProperty(mGetProperty , getIntent().getStringExtra(IntentKey.NUMBER));

        //ボタン押下
        mEditButton.setOnClickListener(this);
        mDeleteButton.setOnClickListener(this);
        mPrinterButton.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.reference_edit: {
                Intent intent = new Intent(PropertyReferenceActivity.this, PropertyEditActivity.class);
                intent.putExtra(IntentKey.CONTROL_NUMBER, mControlNumber.getText().toString());
                startActivity(intent);
            }
            break;
            case R.id.reference_delete: {
                new AlertDialog.Builder(PropertyReferenceActivity.this)
                        .setMessage(R.string.delete)
                        .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                if(mControlNumber.getText().toString().equals("")){
                                    mControlNumber.setText("1");
                                }
                                mWebApi.deleteProperty(new DeletePropertyRequest(LoginUserNameHolder.getInstance().getName(),Integer.parseInt(mControlNumber.getText().toString())),mDeleteProperty);
                            }
                        })
                        .setNegativeButton(R.string.cancel, null)
                        .show();
            }
            break;
            case R.id.reference_print: {
                Intent intent = new Intent(PropertyReferenceActivity.this, PrinterActivity.class);
                intent.putExtra(IntentKey.CONTROL_NUMBER, mControlNumber.getText().toString());
                startActivity(intent);
            }
            break;
        }
    }

    //サーバー通信結果
    private CallbackListener<GetReferencePropertyResponse> mGetProperty = new CallbackListener<GetReferencePropertyResponse>() {
        @Override
        public void onPostExecute(GetReferencePropertyResponse response) {

            if(response.getError().equals("1")){
                new ShowDialog(PropertyReferenceActivity.this).show(R.string.cannot_connect);
            }else if(response.getError().equals("2")) {
                new ShowDialog(PropertyReferenceActivity.this).show(R.string.not_permit_character);
            }else if(response.getError().equals("3")){
                new AlertDialog.Builder(PropertyReferenceActivity.this)
                        .setMessage(R.string.cannot_find_property)
                        .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent intent = new Intent(PropertyReferenceActivity.this, MenuActivity.class);
                                startActivity(intent);
                            }
                        })
                        .show();
            }else {

                mManager.setText(response.getInfo().mPropertyManager);
                mUser.setText(response.getInfo().mPropertyUser);
                mPlace.setText(response.getInfo().mLocation);
                mControlNumber.setText(getIntent().getStringExtra(IntentKey.NUMBER));
                mProduct.setText(response.getInfo().mProductName);
                mPurchase.setText(response.getInfo().mPurchaseCategory);
                mAssets.setText(response.getInfo().mPropertyCategory);
                mRemark.setText(response.getInfo().mComplement);

            }

        }
    };


    //サーバー通信結果
    private CallbackListener<String> mDeleteProperty = new CallbackListener<String>() {

        @Override
        public void onPostExecute(String response) {

            if (response.equals("1")) {
                new ShowDialog(PropertyReferenceActivity.this).show(R.string.cannot_connect);
            }
            if (response.equals("2")) {
                new ShowDialog(PropertyReferenceActivity.this).show(R.string.not_permit_character);
            }
            if (response.equals("12")) {
                new ShowDialog(PropertyReferenceActivity.this).show(R.string.cannot_find_user);
            }
            if (response.equals("13")) {
                new ShowDialog(PropertyReferenceActivity.this).show(R.string.not_management);
            }
            if (response.equals("31")) {
                new ShowDialog(PropertyReferenceActivity.this).show(R.string.cannot_find_property);
            }
            if (response.equals("0")) {
                new AlertDialog.Builder(PropertyReferenceActivity.this)
                        .setMessage(R.string.delete_complete)
                        .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent intent = new Intent(PropertyReferenceActivity.this, MenuActivity.class);
                                startActivity(intent);
                            }
                        })
                        .show();
            }
        }
    };

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // BackBtnアクション
        if(keyCode==KeyEvent.KEYCODE_BACK){
            new AlertDialog.Builder(PropertyReferenceActivity.this)
                    .setMessage(R.string.to_menu)
                    .setPositiveButton(R.string.ok,new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intent = new Intent(PropertyReferenceActivity.this, MenuActivity.class);
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