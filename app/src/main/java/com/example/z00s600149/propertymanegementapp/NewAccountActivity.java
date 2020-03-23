package com.example.z00s600149.propertymanegementapp;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import dialog.ShowDialog;
import entity.EditableUserInfo;
import entity.UserInfo;
import task.AsyncTaskListener.CallbackListener;
import webApi.WebApi;
import webApi.WebApiImpl;
import validator.NewAccountValidator;

/**
 * アカウントを新規作成するためのActivity
 */
public class NewAccountActivity extends AppCompatActivity implements View.OnClickListener{

    private static final String TAG = "NewAccountAct";

    /*データ登録用ボタン*/
    private Button mRegister;
    /*ユーザーIDデータ保持用*/
    private String mIdInfo;
    /*ユーザーパスワードデータ保持用*/
    private String mPassInfo;
    /*再入力用パスワード*/
    private String mConfirmationPassInfo;

    private WebApi mWebApi;

    /*デフォルトコンストラクタ*/

    public NewAccountActivity() {
        super();
        mWebApi = new WebApiImpl();
        Log.i(TAG, "NEW ACCOUNT Activity start");
    }

    public NewAccountActivity(WebApi WebApi) {
        super();
        mWebApi = WebApi;
        Log.i(TAG, "NEW ACCOUNT Activity start");
    }

    void setApi(WebApi webApi) {
        mWebApi = webApi;
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_account);

        //IDと対応付け
        mRegister = (Button) findViewById(R.id.newaccount_button_regist);

        //ボタン押下の動作
        mRegister.setOnClickListener(this);

    }

    /*キー入力動作*/
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.newaccount_button_regist: {

                //IDと対応付け
                mIdInfo = ((EditText) findViewById(R.id.newaccount_id)).getText().toString();
                mPassInfo = ((EditText) findViewById(R.id.newaccount_passward)).getText().toString();
                mConfirmationPassInfo = ((EditText) findViewById(R.id.newaccount_repassward)).getText().toString();

                /*入力チェック*/
                int validationResult = new NewAccountValidator().validate(new EditableUserInfo(mIdInfo, mPassInfo, mConfirmationPassInfo));
                if (validationResult == 1) {
                    new ShowDialog(NewAccountActivity.this).show(R.string.not_input);
                } else if (validationResult == 2) {
                    new ShowDialog(NewAccountActivity.this).show(R.string.not_permit_character);
                } else if (validationResult == 3) {
                    new ShowDialog(NewAccountActivity.this).show(R.string.input_rule_id);
                } else if (validationResult == 4) {
                    new ShowDialog(NewAccountActivity.this).show(R.string.input_rule_pass);
                } else if (validationResult == 5) {
                    new ShowDialog(NewAccountActivity.this).show(R.string.not_match_pass);
                } else {
                    new AlertDialog.Builder(NewAccountActivity.this)
                            .setMessage(R.string.register_permit)
                            .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    mWebApi.newAccount(new UserInfo(mIdInfo, mPassInfo),listener);
                                }
                            })
                            .setNegativeButton(R.string.cancel, null)
                            .show();
                }
            }
            break;
        }
    }

    CallbackListener<String> listener = new CallbackListener<String>() {
        /*サーバー通信結果を受けるメソッド*/
        @Override
        public void onPostExecute(String result) {
            int resultCode = Integer.parseInt(result);
            if(resultCode == 1){
                new ShowDialog(NewAccountActivity.this).show(R.string.cannot_connect);
            }
            else if(resultCode == 11){
                new ShowDialog(NewAccountActivity.this).show(R.string.already_register);
            }
            else {
                Intent intent = new Intent(NewAccountActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        }
    };

    @Override
    /*BACKキー押下後の動作確認*/
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // BackBtnアクション
        if(keyCode==KeyEvent.KEYCODE_BACK){
            new AlertDialog.Builder(NewAccountActivity.this)
                    .setMessage(R.string.to_login)
                    .setPositiveButton(R.string.ok,new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intent = new Intent(NewAccountActivity.this, LoginActivity.class);
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


