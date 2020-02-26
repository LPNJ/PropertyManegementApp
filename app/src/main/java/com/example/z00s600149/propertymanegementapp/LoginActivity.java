package com.example.z00s600149.propertymanegementapp;

import android.app.AlertDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import entity.UserIdInfo;
import entity.UserInfo;
import task.AsyncTaskListener.CallbackListener_OneElement;
import task.LoginTask;
import task.NewAccountTask;
import task.ResultListener;
import task.impl.LoginTaskImpl;
import task.mock.LoginTaskMock;
import task.mock.NewAccountTaskMock;
import validator.UserLoginValidator;

public class LoginActivity extends AppCompatActivity implements ResultListener<Integer> , CallbackListener_OneElement {

    /** ログイン用ボタン */
    private Button mLogin;
    /** 新規ユーザー登録ボタン */
    private Button mNewAccount;
    /** ログイン用ID */
    private EditText mId;
    /** ログイン用PASSWARD */
    private EditText mPass;
    /** ログインIDデータ保持用 */
    String mId_info;
    /** ログイン用PASSWARDデータ保持用 */
    String mPass_info;

    /**
     *
     */
    private LoginTask mLoginTask;

    /**
     *
     */
    private LoginTaskImpl mLoginTaskImpl;

    /**
     * デフォルトコンストラクタ
     */
    public LoginActivity() {
        super();
//        mLoginTask = new LoginTaskMock();
        mLoginTaskImpl = new LoginTaskImpl(this);
        //mErrorMessage.put(-1, R.string.cannot_connect);
        //mErrorMessage.put(11, R.string.cannot_register_error);
        Log.i("Regist", "register activity contstructor");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //IDと対応付け
        mLogin = (Button) findViewById(R.id.login_button_login);
        mNewAccount = (Button) findViewById(R.id.login_button_newaccount);
        mId = (EditText) findViewById(R.id.login_id);
        mPass = (EditText) findViewById(R.id.login_passward);

        //ボタン押下機能の動作保証
        LoginActivityOnClickListener listener = new LoginActivityOnClickListener();
        mLogin.setOnClickListener(listener);
        mNewAccount.setOnClickListener(listener);
    }

    @Override
    public void onPostExecute(String result) {
        int resultCode = Integer.parseInt(result);
        if(resultCode == 1){
            show("RuntimeException");
        }
        else if(resultCode == 21){
            show("すでにユーザーが登録されています");
        }
        else {
            Intent intent = new Intent(LoginActivity.this, MenuActivity.class);
            startActivity(intent);
        }
    }

    class LoginActivityOnClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.login_button_login: {
                    mId_info = mId.getText().toString();
                    mPass_info = mPass.getText().toString();
//                    int validationResult = new UserLoginValidator().validate(new UserInfo(mId_info,mPass_info));
//                    if(validationResult == 1){
//                        new AlertDialog.Builder(LoginActivity.this);
//                                show("入力されていない項目があります");
//                    }
//                    else {
                            mLoginTaskImpl.execute(new UserInfo(mId_info,mPass_info));
//                    }
                }
                break;
                case R.id.login_button_newaccount: {
                    Intent intent = new Intent(LoginActivity.this, NewAccountActivity.class);
                    startActivity(intent);
                }
                break;
            }
        }
    }

    @Override
    public void onResult(Integer result) {
        if (result == 1) {
            new AlertDialog.Builder(LoginActivity.this);
                    show("サーバーへの接続が失敗しました");
            return;
        }
        else if(result == 0){
            Intent intent = new Intent(LoginActivity.this, MenuActivity.class);
            intent.putExtra(IntentKey.LOGIN_NAME, mId_info);
            startActivity(intent);
        }
    }

    void show(String msg){
        new AlertDialog.Builder(LoginActivity.this)
                .setMessage(msg)
                .setPositiveButton("OK", null)
                .create()
                .show();
    }

}