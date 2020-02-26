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

import entity.EditableUserInfo;
import entity.UserInfo;
import task.AsyncTaskListener.CallbackListener_OneElement;
import task.NewAccountTask;
import task.ResultListener;
import task.impl.NewAccountTaskImpl;
import validator.NewAccountValidator;

public class NewAccountActivity extends AppCompatActivity implements ResultListener<Integer>,CallbackListener_OneElement {

    /**
     * 登録用ボタン
     */
    private Button mRegist;

    /**
     * ユーザー情報新規作成用ID
     */
    private EditText mId;
    /**
     * 　ユーザー情報新規作成用PASSWARD
     */
    private EditText mPass;
    /**
     * 　再入力用PASSWARD
     */
    private EditText mconfirmationPass;
    /**
     * 　ユーザーIDデータ保持用
     */
    private String mId_info;
    /**
     * 　ユーザーPASSWARDデータ保持用
     */
    private String mPass_info;
    /**
     * 　再入力用PASSWARD
     */
    private String mConfirmationPass_info;

    /**
     *
     */
    private NewAccountTask mNewAccountTask;

    /**
     *
     */
    private NewAccountTaskImpl mNewAccountTaskImpl;

    /**
     * デフォルトコンストラクタ
     */
    public NewAccountActivity() {
        super();
//        mNewAccountTask = new NewAccountTaskMock();
        mNewAccountTaskImpl = new NewAccountTaskImpl(this);
        //mErrorMessage.put(-1, R.string.cannot_connect);
        //mErrorMessage.put(11, R.string.cannot_register_error);
        Log.i("Regist", "register activity constructor");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_account);

        mRegist = (Button) findViewById(R.id.newaccount_button_regist);

        NewAccountOnClickListener listener = new NewAccountOnClickListener();
        mRegist.setOnClickListener(listener);

    }

    class NewAccountOnClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.newaccount_button_regist: {

                    mId_info = ((EditText) findViewById(R.id.newaccount_id)).getText().toString();
                    mPass_info = ((EditText) findViewById(R.id.newaccount_passward)).getText().toString();
                    mConfirmationPass_info = ((EditText) findViewById(R.id.newaccount_repassward)).getText().toString();

                    int validationResult = new NewAccountValidator().validate(new EditableUserInfo(mId_info, mPass_info, mConfirmationPass_info));
                    if (validationResult == 1) {
                        show("入力されていない項目があります");
                    } else if (validationResult == 2) {
                        show("許可されていない文字が含まれています");
                    } else {
                        new AlertDialog.Builder(NewAccountActivity.this)
                                .setMessage("登録してもよろしいですか？")
                                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
//                                        mNewAccountTask.execute(new UserInfo(mId_info, mPass_info), NewAccountActivity.this);
                                        mNewAccountTaskImpl.execute(new UserInfo(mId_info, mPass_info));
                                    }
                                })
                                .setNegativeButton("Cancel", null)
                                .show();
                    }
                }
                break;
            }
        }
    }

    void show(String msg) {
        new AlertDialog.Builder(NewAccountActivity.this)
                .setMessage(msg)
                .setPositiveButton("OK", null)
                .create()
                .show();
    }

    @Override
    public void onPostExecute(String result) {
        int resultCode = Integer.parseInt(result);
        if(resultCode == 1){
            show("RuntimeException");
        }
        else if(resultCode == 11){
            show("すでにユーザーが登録されています");
        }
        else {
            Intent intent = new Intent(NewAccountActivity.this, LoginActivity.class);
            startActivity(intent);
        }

    }

    @Override
    public void onResult(Integer result) {
        if (result != 0) {
            show("サーバーへの接続が失敗しました");
            return;
        }
        Intent intent = new Intent(NewAccountActivity.this, LoginActivity.class);
        startActivity(intent);
    }

}


