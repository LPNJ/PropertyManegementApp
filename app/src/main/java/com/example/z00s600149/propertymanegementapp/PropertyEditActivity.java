package com.example.z00s600149.propertymanegementapp;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;

import entity.LoginNameSingleton;
import entity.PropertyInfo;
import task.AsyncTaskListener.CallbackListener;
import task.GetNameTask;
import task.GetPropertyInfoTask;
import task.PropertyInfoTask;
import task.Request.EditPropertyRequest;
import task.ResultListener;
import task.impl.EditPropertyTaskImpl;
import task.impl.GetNameTaskImpl;
import task.mock.GetNameTaskMock;
import task.mock.GetPropertyInfoTaskMock;
import task.mock.PropertyInfoTaskMock;
import task.response.GetNameResponse;
import task.serialize.PropertyInfoRequest;
import task.serialize.PropertyInfoResponse;
import task.serialize.PropertyRegistRequest;
import task.serialize.PropertyRegistResponse;

public class PropertyEditActivity extends AppCompatActivity implements View.OnClickListener{

    /*ログインユーザー情報保持用リスト*/
    ArrayAdapter<String> myAdapter_Manager;
    /** 資産情報登録用ボタン */
    private Button mPropertyEdit;
    /** 資産管理者用スピナー */
    private Spinner mManager;
    /** 資産利用者用スピナー */
    private Spinner mPropertyUser;
    /** 購入区分スピナー */
    private Spinner mPurchase_Category_Spinner;
    /** 資産区分スピナー */
    private Spinner mProperty_Category_Spinner;

    /** ログイン用ID */
    private EditText mLocation;
    /** ログイン用ID */
    private EditText mProductName;
    /** ログイン用ID */
    private EditText mRemarks;

    private TextView mControlNumber;

    /* MOCK */
    private GetNameTask mGetNameTask;

    /* MOCK */
    private GetPropertyInfoTask mGetPropertyInfoTask;

    /* MOCK */
    private PropertyInfoTask mPropertyInfoTask;

    /* サーバー接続用（資産番号と、エラーコード） */
    private EditPropertyTaskImpl mEditPropertyTaskImpl;

    /* サーバー接続用（ログインユーザー情報取得) */
    private GetNameTaskImpl mGetNameTaskImpl;

    /*デフォルトコンストラクタ*/
    public PropertyEditActivity() {
        super();
        mEditPropertyTaskImpl = new EditPropertyTaskImpl(mCallBackListener_Edit);
        mGetNameTaskImpl = new GetNameTaskImpl(mCallBackListener);
        Log.i("PropertyEdit", "PropertyEdit activity contstructor");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_property_edit);

        //ID対応付け
        mControlNumber = (TextView) findViewById(R.id.edit_controlnumber);
        mControlNumber.setText(getIntent().getStringExtra(IntentKey.CONTROL_NUMBER));
        mManager = (Spinner) findViewById(R.id.property_info_spinner_7);
        mPropertyUser = (Spinner) findViewById(R.id.property_info_spinner_5);
        mPurchase_Category_Spinner = (Spinner) findViewById(R.id.property_info_spinner_8);
        mProperty_Category_Spinner = (Spinner) findViewById(R.id.property_info_spinner_6);
        mLocation = (EditText) findViewById(R.id.property_edit_editText_location);
        mProductName = (EditText) findViewById(R.id.property_edit_editText_productname);
        mRemarks = (EditText) findViewById(R.id.property_edit_editText_remarks);
        mPropertyEdit = (Button) findViewById(R.id.edit_button_edit);

        //ログイン名一覧取得
        mGetNameTaskImpl.execute();

        //ボタン押下の動作
        mPropertyEdit.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.edit_button_edit: {
                mEditPropertyTaskImpl.execute(
                        new EditPropertyRequest(
                                LoginNameSingleton.getInstanse().getName(),
                                Integer.parseInt(getIntent().getStringExtra(IntentKey.CONTROL_NUMBER)),
                                new PropertyInfo((String) mManager.getSelectedItem(),
                                        (String)mPropertyUser.getSelectedItem(),
                                        mLocation.getText().toString(),
                                        "",
                                        mProductName.getText().toString(),
                                        (String)mPurchase_Category_Spinner.getSelectedItem(),
                                        (String)mProperty_Category_Spinner.getSelectedItem(),
                                        mRemarks.getText().toString()))
                );
            }
            break;
        }
    }

    //MOCK
    private ResultListener<PropertyRegistResponse> mResponseListener = new ResultListener<PropertyRegistResponse>() {

        @Override
        public void onResult(PropertyRegistResponse result) {
            if (result == null) {
                throw new IllegalArgumentException("result is null");
            }
            if (result.getError() == 0) {
                Intent intent = new Intent(PropertyEditActivity.this, ControlNumberIssuedActivity.class);
                intent.putExtra(IntentKey.CONTROL_NUMBER, result.getControlNumber());
                Log.i("CONTROLNUMBER",result.getControlNumber());
                startActivity(intent);
            }
        }
    };

    //MOCK
    private ResultListener<ArrayList<String>> mResultListener = new ResultListener<ArrayList<String>>() {
        @Override
        public void onResult(ArrayList<String> names) {
            ArrayAdapter<String> myAdapter_Manager = new ArrayAdapter<String>(PropertyEditActivity.this ,android.R.layout.simple_list_item_1, names);
            ArrayAdapter<String> myAdapter_User = new ArrayAdapter<String>(PropertyEditActivity.this ,android.R.layout.simple_list_item_1, names);
            ArrayAdapter<String> myAdapter_Purchase_Category = new ArrayAdapter<String>(PropertyEditActivity.this ,android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.Purchase_Category));
            ArrayAdapter<String> myAdapter_Property_Category = new ArrayAdapter<String>(PropertyEditActivity.this ,android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.Property_Category));

            myAdapter_Manager.setDropDownViewResource(android.R.layout.simple_list_item_1);
            myAdapter_User.setDropDownViewResource(android.R.layout.simple_list_item_1);
            myAdapter_Purchase_Category.setDropDownViewResource(android.R.layout.simple_list_item_1);
            myAdapter_Property_Category.setDropDownViewResource(android.R.layout.simple_list_item_1);

            mManager.setAdapter(myAdapter_Manager);
            mPropertyUser.setAdapter(myAdapter_User);
            mPurchase_Category_Spinner.setAdapter(myAdapter_Purchase_Category);
            mProperty_Category_Spinner.setAdapter(myAdapter_Property_Category);
        }
    };

    // サーバー接続結果  ログイン名取得用
    private CallbackListener<GetNameResponse> mCallBackListener = new CallbackListener<GetNameResponse>() {
        @Override
        public void onPostExecute(GetNameResponse response) {
            if(Integer.parseInt(response.getError()) == 1){
                show("不明なエラー");
            }
            else {
                myAdapter_Manager = new ArrayAdapter<String>(PropertyEditActivity.this, android.R.layout.simple_list_item_1, response.getNames());
                ArrayAdapter<String> myAdapter_User = new ArrayAdapter<String>(PropertyEditActivity.this, android.R.layout.simple_list_item_1, response.getNames());
                ArrayAdapter<String> myAdapter_Purchase_Category = new ArrayAdapter<String>(PropertyEditActivity.this, android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.Purchase_Category));
                ArrayAdapter<String> myAdapter_Property_Category = new ArrayAdapter<String>(PropertyEditActivity.this, android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.Property_Category));

                myAdapter_Manager.setDropDownViewResource(android.R.layout.simple_list_item_1);
                myAdapter_User.setDropDownViewResource(android.R.layout.simple_list_item_1);
                myAdapter_Purchase_Category.setDropDownViewResource(android.R.layout.simple_list_item_1);
                myAdapter_Property_Category.setDropDownViewResource(android.R.layout.simple_list_item_1);

                myAdapter_Manager.getContext().toString();

                mManager.setAdapter(myAdapter_Manager);
                mPropertyUser.setAdapter(myAdapter_User);
                mPurchase_Category_Spinner.setAdapter(myAdapter_Purchase_Category);
                mProperty_Category_Spinner.setAdapter(myAdapter_Property_Category);
            }
        }
    };

    //サーバ接続結果　編集できるかどうか
    private CallbackListener<String> mCallBackListener_Edit = new CallbackListener<String>() {
        @Override
        public void onPostExecute(String response) {
            if (Integer.parseInt(response) == 0) {
                new AlertDialog.Builder(PropertyEditActivity.this)
                        .setMessage("編集が完了しました")
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent intent = new Intent(PropertyEditActivity.this, MenuActivity.class);
                                startActivity(intent);
                            }
                        })
                        .show();
            }
            else if(Integer.parseInt(response) == 1){
                show("RuntimeException");
            }
            else if(Integer.parseInt(response) == 2){
                show("不正なパラメータ");
            }
            else if(Integer.parseInt(response) == 12){
                show("指定ユーザーが見つかりません");
            }
            else if(Integer.parseInt(response) == 31){
                show("指定資産が見つかりません");
            }
        }
    };

    void show(String msg){
        new AlertDialog.Builder(PropertyEditActivity.this)
                .setMessage(msg)
                .setPositiveButton("OK", null)
                .create()
                .show();
    }

}
