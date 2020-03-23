package com.example.z00s600149.propertymanegementapp;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import dialog.ShowDialog;
import entity.LoginUserNameHolder;
import entity.PropertyInfo;
import entity.PropertyInfoForValidator;
import task.AsyncTaskListener.CallbackListener;
import request.EditPropertyRequest;
import validator.PropertyInfoValidator;
import webApi.WebApi;
import webApi.WebApiImpl;
import response.GetNameResponse;

/**
 * 資産情報を変更して、再度登録させるActivity
 */
public class PropertyEditActivity extends AppCompatActivity implements View.OnClickListener{

    private static final String TAG = "PropertyEditAct";

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

    private WebApi mWebApi;

    /*デフォルトコンストラクタ*/
    public PropertyEditActivity() {
        super();
        mWebApi = new WebApiImpl();
        Log.i(TAG, "PropertyEdit activity start");
    }

    public PropertyEditActivity(WebApi webApi) {
        super();
        mWebApi = webApi;
        Log.i(TAG, "PropertyEdit activity start");
    }

    void setApi(WebApi webApi) {
        mWebApi = webApi;
        Log.i(TAG, "MOCK start");
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
        mWebApi.getName(mCallBackListener);

        //ボタン押下の動作
        mPropertyEdit.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.edit_button_edit: {
                int validationResult = new PropertyInfoValidator().validate(new PropertyInfoForValidator(mLocation.getText().toString(),mProductName.getText().toString()));
                if (validationResult == 1) {
                    new ShowDialog(PropertyEditActivity.this).show(R.string.not_input);
                } else {
                    mWebApi.editProperty(new EditPropertyRequest(
                            LoginUserNameHolder.getInstance().getName(),
                            Integer.parseInt(mControlNumber.getText().toString()),
                            new PropertyInfo((String) mManager.getSelectedItem(),
                                    (String) mPropertyUser.getSelectedItem(),
                                    mLocation.getText().toString(),
                                    "",
                                    mProductName.getText().toString(),
                                    (String) mPurchase_Category_Spinner.getSelectedItem(),
                                    (String) mProperty_Category_Spinner.getSelectedItem(),
                                    mRemarks.getText().toString())), mCallBackListenerEdit);
                }
            }
            break;
        }
    }

    // サーバー接続結果  ログイン名取得用
    private CallbackListener<GetNameResponse> mCallBackListener = new CallbackListener<GetNameResponse>() {

        @Override
        public void onPostExecute(GetNameResponse response) {
            final String role = "EDIT";
            new GetNames(PropertyEditActivity.this).getLoginName(role,response);
        }
    };

    //サーバ接続結果　編集できるかどうか
    private CallbackListener<String> mCallBackListenerEdit = new CallbackListener<String>() {
        @Override
        public void onPostExecute(String response) {
            if (Integer.parseInt(response) == 0) {
                new AlertDialog.Builder(PropertyEditActivity.this)
                        .setMessage(R.string.edit_success)
                        .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent intent = new Intent(PropertyEditActivity.this, MenuActivity.class);
                                startActivity(intent);
                            }
                        })
                        .show();
            }
            else if(Integer.parseInt(response) == 1){
                new ShowDialog(PropertyEditActivity.this).show(R.string.cannot_connect);
            }
            else if(Integer.parseInt(response) == 2){
                new ShowDialog(PropertyEditActivity.this).show(R.string.not_permit_character);
            }
            else if(Integer.parseInt(response) == 12){
                new ShowDialog(PropertyEditActivity.this).show(R.string.cannot_find_user);
            }
            else if(Integer.parseInt(response) == 31){
                new ShowDialog(PropertyEditActivity.this).show(R.string.cannot_find_property);
            }
        }
    };
}
