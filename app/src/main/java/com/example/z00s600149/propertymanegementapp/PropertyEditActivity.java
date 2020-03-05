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

import Dialog.ShowDialog;
import entity.LoginUserNameHolder;
import entity.PropertyInfo;
import task.AsyncTaskListener.CallbackListener;
import task.Request.EditPropertyRequest;
import task.EditPropertyTask;
import task.GetNameTask;
import webApi.WebApi;
import webApi.WebApiImpl;
import webApi.WebApiMock;
import task.mock.GetNameTaskMock;
import task.mock.GetReferenceInfoTaskMock;
import task.response.GetNameResponse;

public class PropertyEditActivity extends AppCompatActivity implements View.OnClickListener{

    /*ログインユーザー情報保持用リスト*/
    // TODO onPostExecuteでしか使ってないからローカル変数にする
    // TODO キャメルケース
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

    private final WebApi mWebApi;

    /*デフォルトコンストラクタ*/
    public PropertyEditActivity() {
        super();
        // TODO 使わないやつ消す
//        mEditPropertyTaskImpl = new EditPropertyTask(mCallBackListener_Edit);
//        mGetNameTaskImpl = new GetNameTask(mCallBackListener);
        mWebApi = new WebApiImpl();
        Log.i("PropertyEdit", "PropertyEdit activity contstructor");
    }


    // TODO ローカル変数の最初は小文字
    public PropertyEditActivity(WebApi WebApi) {
        super();
        // TODO 使わないやつ消す
//        mEditPropertyTaskImpl = new EditPropertyTask(mCallBackListener_Edit);
//        mGetNameTaskImpl = new GetNameTask(mCallBackListener);・
        // TODO 引数を設定する
        mWebApi = new WebApiMock();
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
//        mGetNameTaskImpl.execute();
        mWebApi.getName(mCallBackListener);

        //ボタン押下の動作
        mPropertyEdit.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.edit_button_edit: {

                mWebApi.editProperty(new EditPropertyRequest(
                        LoginUserNameHolder.getInstanse().getName(),
                        Integer.parseInt(getIntent().getStringExtra(IntentKey.CONTROL_NUMBER)),
                        new PropertyInfo((String) mManager.getSelectedItem(),
                                (String)mPropertyUser.getSelectedItem(),
                                mLocation.getText().toString(),
                                "",
                                mProductName.getText().toString(),
                                (String)mPurchase_Category_Spinner.getSelectedItem(),
                                (String)mProperty_Category_Spinner.getSelectedItem(),
                                mRemarks.getText().toString())),mCallBackListenerEdit);
            }
            break;
        }
    }

    // サーバー接続結果  ログイン名取得用
    private CallbackListener<GetNameResponse> mCallBackListener = new CallbackListener<GetNameResponse>() {
        @Override
        public void onPostExecute(GetNameResponse response) {
            if(Integer.parseInt(response.getError()) == 1){
                new ShowDialog(PropertyEditActivity.this).show(R.string.error);
            }
            else {
                myAdapter_Manager = new ArrayAdapter<String>(PropertyEditActivity.this, android.R.layout.simple_list_item_1, response.getNames());
                // TODO 変数キャメルケースにする、elseの中が大体おかしい
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
    private CallbackListener<String> mCallBackListenerEdit = new CallbackListener<String>() {
        @Override
        public void onPostExecute(String response) {
            if (Integer.parseInt(response) == 0) {
                new AlertDialog.Builder(PropertyEditActivity.this)
                        .setMessage(R.string.edit_success)
                        // TODO stringsに追加
                        // TODO 小見山君が自分で"OK"でプロジェクト全体検索して漏れがないか確認する
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
