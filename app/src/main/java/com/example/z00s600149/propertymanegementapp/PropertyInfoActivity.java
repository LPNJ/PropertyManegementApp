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
import Dialog.ShowDialog;
import entity.LoginUserNameHolder;
import entity.PropertyInfo;
import task.AsyncTaskListener.CallbackListener;
import task.Request.RegisterPropertyRequest;
import task.GetNameTask;
import task.RegisterPropertyInfoTask;
import webApi.WebApi;
import webApi.WebApiImpl;
import task.mock.GetNameTaskMock;
import task.mock.RegisterPropertyInfoTaskMock;
import task.response.GetNameResponse;
import task.response.RegisterPropertyResponse;

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

    private final WebApi mWebApi;

    /*デフォルトコンストラクタ*/
    public PropertyInfoActivity() {
        super();
        mWebApi = new WebApiImpl();
        Log.i("Property", "Property activity contstructor");
    }

    public PropertyInfoActivity(WebApi WebApi) {
        super();
        mWebApi = WebApi;
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
        mWebApi.getName(mCallBackListener);

    }

    @Override
    //ボタン押下後の動作確認
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.property_button_regist: {
                mWebApi.propertyRegister(new RegisterPropertyRequest(
                        LoginUserNameHolder.getInstanse().getName(),
                        new PropertyInfo((String) mManager.getSelectedItem(),
                                (String)mPropertyUser.getSelectedItem(),
                                mLocation.getText().toString(),
                                "",
                                mProductName.getText().toString(),
                                (String)mPurchase_Category_Spinner.getSelectedItem(),
                                (String)mProperty_Category_Spinner.getSelectedItem(),
                                mRemarks.getText().toString())),mCallBackListenerAssetId);

            }
            break;
        }
    }

    //GetNameTask実行後の結果の処理
    private CallbackListener<GetNameResponse> mCallBackListener = new CallbackListener<GetNameResponse>() {
        @Override
        public void onPostExecute(GetNameResponse response) {
            if(Integer.parseInt(response.getError()) == 1){
                new ShowDialog(PropertyInfoActivity.this).show(R.string.error);
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
    private CallbackListener<RegisterPropertyResponse> mCallBackListenerAssetId = new CallbackListener<RegisterPropertyResponse>() {
        @Override
        public void onPostExecute(RegisterPropertyResponse response) {
            if (response == null) {
                throw new IllegalArgumentException("result is null");
            }
            if (Integer.parseInt(response.getError()) == 0) {
                Intent intent = new Intent(PropertyInfoActivity.this, ControlNumberIssuedActivity.class);
                intent.putExtra(IntentKey.CONTROL_NUMBER, response.getControlNumber());
                startActivity(intent);
            }
            else if(Integer.parseInt(response.getError()) == 1){
                new ShowDialog(PropertyInfoActivity.this).show(R.string.cannot_connect);
            }
            else if(Integer.parseInt(response.getError()) == 2){
                new ShowDialog(PropertyInfoActivity.this).show(R.string.not_permit_character);
            }
            else if(Integer.parseInt(response.getError()) == 12){
                new ShowDialog(PropertyInfoActivity.this).show(R.string.cannot_find_user);
            }
            else if(Integer.parseInt(response.getError()) == 31){
                new ShowDialog(PropertyInfoActivity.this).show(R.string.cannot_find_property);
            }
            else if(Integer.parseInt(response.getError()) == 32){
                new ShowDialog(PropertyInfoActivity.this).show(R.string.limit);
            }
        }
    };

    //Backキー押下後の処理
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // BackBtnアクション
        if(keyCode==KeyEvent.KEYCODE_BACK){
            new AlertDialog.Builder(PropertyInfoActivity.this)
                    .setMessage(R.string.to_menu)
                    .setPositiveButton("OK",new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intent = new Intent(PropertyInfoActivity.this, MenuActivity.class);
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
