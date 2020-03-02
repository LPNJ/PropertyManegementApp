package com.example.z00s600149.propertymanegementapp;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.ArrayList;

import entity.LoginNameSingleton;
import entity.PropertyInfo;
import entity.UserInfo;
import task.AsyncTaskListener.CallbackListener;
import task.GetNameTask;
import task.LoginTask;
import task.PropertyInfoTask;
import task.Request.RegisterPropertyRequest;
import task.ResultListener;
import task.impl.GetNameTaskImpl;
import task.impl.LoginTaskImpl;
import task.impl.RegisterPropertyInfoTaskImpl;
import task.mock.GetNameTaskMock;
import task.mock.LoginTaskMock;
import task.mock.PropertyInfoTaskMock;
import task.response.GetNameResponse;
import task.response.RegisterPropertyResponse;
import task.serialize.PropertyRegistRequest;
import task.serialize.PropertyRegistResponse;

public class PropertyInfoActivity extends AppCompatActivity implements View.OnClickListener{

    /*ログインユーザー情報保持用リスト*/
    ArrayAdapter<String> myAdapter_Manager;
    /** 資産情報登録用ボタン */
    private Button mPropertyRegist;
    /** 資産管理者用スピナー */
    private Spinner mManager;
    /** 資産利用者用スピナー */
    private Spinner mPropertyUser;
    /** 購入区分スピナー */
    private Spinner mPurchase_Category_Spinner;
    /** 資産区分スピナー */
    private Spinner mProperty_Category_Spinner;

    /** 管理場所情報保持用 */
    private EditText mLocation;
    /** 製品名情報保持用 */
    private EditText mProductName;
    /** 備考情報保持用 */
    private EditText mRemarks;

    /* MOCK */
    private GetNameTask mGetNameTask;

    /* MOCK */
    private PropertyInfoTask mPropertyInfoTask;

    /*サーバー接続用（ログインユーザー情報取得）*/
    private GetNameTaskImpl mGetNameTaskImpl;

    /*サーバー接続用（資産番号と、エラーコード）*/
    private RegisterPropertyInfoTaskImpl mRegisterPropertyInfoTaskImpl;

    /*デフォルトコンストラクタ*/
    public PropertyInfoActivity() {
        super();
        mGetNameTaskImpl = new GetNameTaskImpl(mCallBackListener);
        mRegisterPropertyInfoTaskImpl = new RegisterPropertyInfoTaskImpl(mCallBackListener_AssetId);
        Log.i("Property", "Property activity contstructor");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_property_info);

        //ID対応付け
        mPropertyRegist = (Button) findViewById(R.id.property_button_regist);
        mManager = (Spinner) findViewById(R.id.property_info_spinner_1);
        mPropertyUser = (Spinner) findViewById(R.id.property_info_spinner_2);
        mPurchase_Category_Spinner = (Spinner) findViewById(R.id.property_info_spinner_3);
        mProperty_Category_Spinner = (Spinner) findViewById(R.id.property_info_spinner_4);
        mLocation = (EditText) findViewById(R.id.property_info_editText_location);
        mProductName = (EditText) findViewById(R.id.property_info_editText_productname);
        mRemarks = (EditText) findViewById(R.id.property_info_editText_remarks);

        //ボタン押下の動作
        mPropertyRegist.setOnClickListener(this);

        //ログイン名一覧取得
        mGetNameTaskImpl.execute();

    }

    @Override
    //ボタン押下後の動作確認
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.property_button_regist: {
                mRegisterPropertyInfoTaskImpl.execute(
                        new RegisterPropertyRequest(
                                LoginNameSingleton.getInstanse().getName(),
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

    //MOCK用
    private ResultListener<ArrayList<String>> mResultListener = new ResultListener<ArrayList<String>>() {
        @Override
        public void onResult(ArrayList<String> names) {
            myAdapter_Manager = new ArrayAdapter<String>(PropertyInfoActivity.this ,android.R.layout.simple_list_item_1, names);
            ArrayAdapter<String> myAdapter_User = new ArrayAdapter<String>(PropertyInfoActivity.this ,android.R.layout.simple_list_item_1, names);
            ArrayAdapter<String> myAdapter_Purchase_Category = new ArrayAdapter<String>(PropertyInfoActivity.this ,android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.Purchase_Category));
            ArrayAdapter<String> myAdapter_Property_Category = new ArrayAdapter<String>(PropertyInfoActivity.this ,android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.Property_Category));

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

    //MOCK用
    private ResultListener<PropertyRegistResponse> mResponseListener = new ResultListener<PropertyRegistResponse>() {
        @Override
        public void onResult(PropertyRegistResponse result) {
            if (result == null) {
                throw new IllegalArgumentException("result is null");
            }
            if (result.getError() == 0) {
                Intent intent = new Intent(PropertyInfoActivity.this, ControlNumberIssuedActivity.class);
                intent.putExtra(IntentKey.CONTROL_NUMBER, result.getControlNumber());
                Log.i("CONTROLNUMBER",result.getControlNumber());
                startActivity(intent);
            }
        }
    };

    //GetNameTask実行後の結果の処理
    private CallbackListener<GetNameResponse> mCallBackListener = new CallbackListener<GetNameResponse>() {
        @Override
        public void onPostExecute(GetNameResponse response) {
            if(Integer.parseInt(response.getError()) == 1){
                show("不明なエラー");
            }
            else {
                myAdapter_Manager = new ArrayAdapter<String>(PropertyInfoActivity.this, android.R.layout.simple_list_item_1, response.getNames());
                ArrayAdapter<String> myAdapter_User = new ArrayAdapter<String>(PropertyInfoActivity.this, android.R.layout.simple_list_item_1, response.getNames());
                ArrayAdapter<String> myAdapter_Purchase_Category = new ArrayAdapter<String>(PropertyInfoActivity.this, android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.Purchase_Category));
                ArrayAdapter<String> myAdapter_Property_Category = new ArrayAdapter<String>(PropertyInfoActivity.this, android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.Property_Category));

                myAdapter_Manager.setDropDownViewResource(android.R.layout.simple_list_item_1);
                myAdapter_User.setDropDownViewResource(android.R.layout.simple_list_item_1);
                myAdapter_Purchase_Category.setDropDownViewResource(android.R.layout.simple_list_item_1);
                myAdapter_Property_Category.setDropDownViewResource(android.R.layout.simple_list_item_1);

                mManager.setAdapter(myAdapter_Manager);
                mPropertyUser.setAdapter(myAdapter_User);
                mPurchase_Category_Spinner.setAdapter(myAdapter_Purchase_Category);
                mProperty_Category_Spinner.setAdapter(myAdapter_Property_Category);
            }
        }
    };

    //RegisterPropertyTask実行時の結果
    private CallbackListener<RegisterPropertyResponse> mCallBackListener_AssetId = new CallbackListener<RegisterPropertyResponse>() {
        @Override
        public void onPostExecute(RegisterPropertyResponse response) {
            if (response == null) {
                throw new IllegalArgumentException("result is null");
            }
            if (Integer.parseInt(response.getmError()) == 0) {
                Intent intent = new Intent(PropertyInfoActivity.this, ControlNumberIssuedActivity.class);
                intent.putExtra(IntentKey.CONTROL_NUMBER, response.getmControlNumber());
                startActivity(intent);
            }
            else if(Integer.parseInt(response.getmError()) == 1){
                show("通信に異常がありました");
            }
            else if(Integer.parseInt(response.getmError()) == 2){
                show("不正なパラメーターが渡されました");
            }
            else if(Integer.parseInt(response.getmError()) == 12){
                show("登録対象のユーザーが見つかりません");
            }
            else if(Integer.parseInt(response.getmError()) == 31){
                show("イベントが見つかりません");
            }
            else if(Integer.parseInt(response.getmError()) == 32){
                show("資産登録上限に達しています");
            }
        }
    };

    //エラーメッセージの表示
    void show(String msg){
        new AlertDialog.Builder(PropertyInfoActivity.this)
                .setMessage(msg)
                .setPositiveButton("OK", null)
                .create()
                .show();
    }

    //Backキー押下後の処理
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // BackBtnアクション
        if(keyCode==KeyEvent.KEYCODE_BACK){
            new AlertDialog.Builder(PropertyInfoActivity.this)
                    .setMessage("メニュー画面に戻りますか？")
                    .setPositiveButton("OK",new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intent = new Intent(PropertyInfoActivity.this, MenuActivity.class);
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
