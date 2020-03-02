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

import entity.EditableUserInfo;
import entity.UserInfo;
import task.AsyncTaskListener.CallbackListener;
import task.NewAccountTask;
import task.ResultListener;
import task.impl.NewAccountTaskImpl;
import validator.NewAccountValidator;

public class NewAccountActivity extends AppCompatActivity implements ResultListener<Integer> , View.OnClickListener{

    /*データ登録用ボタン*/
    private Button mRegist;
    /*ユーザーIDデータ保持用*/
    private String mId_info;
    /*ユーザーPASSWARDデータ保持用*/
    private String mPass_info;
    /*再入力用PASSWARD*/
    private String mConfirmationPass_info;
    /* MOCK 動作確認用 */
    private NewAccountTask mNewAccountTask;
    /*サーバー接続用*/
    private NewAccountTaskImpl mNewAccountTaskImpl;

    /*デフォルトコンストラクタ*/
    public NewAccountActivity() {
        super();
        mNewAccountTaskImpl = new NewAccountTaskImpl(listener);
        Log.i("NEWACCOUNT", "NEWACCOUNT Activity constructor");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_account);

        //IDと対応付け
        mRegist = (Button) findViewById(R.id.newaccount_button_regist);

        //ボタン押下の動作
        mRegist.setOnClickListener(this);

    }

    /*キー入力動作*/
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.newaccount_button_regist: {

                //IDと対応付け
                mId_info = ((EditText) findViewById(R.id.newaccount_id)).getText().toString();
                mPass_info = ((EditText) findViewById(R.id.newaccount_passward)).getText().toString();
                mConfirmationPass_info = ((EditText) findViewById(R.id.newaccount_repassward)).getText().toString();

                /*入力チェック*/
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

    /*エラーメッセージ表示*/
    void show(String msg) {
        new AlertDialog.Builder(NewAccountActivity.this)
                .setMessage(msg)
                .setPositiveButton("OK", null)
                .create()
                .show();
    }

    CallbackListener<String> listener = new CallbackListener<String>() {
        /*サーバー通信結果を受けるメソッド*/
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
    };

    @Override
    /* MOCK用*/
    public void onResult(Integer result) {
        if (result != 0) {
            show("サーバーへの接続が失敗しました");
            return;
        }
        Intent intent = new Intent(NewAccountActivity.this, LoginActivity.class);
        startActivity(intent);
    }

    @Override
    /*BACKキー押下後の動作確認*/
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // BackBtnアクション
        if(keyCode==KeyEvent.KEYCODE_BACK){
            new AlertDialog.Builder(NewAccountActivity.this)
                    .setMessage("ログイン画面に戻りますか？")
                    .setPositiveButton("OK",new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            Intent intent = new Intent(NewAccountActivity.this, LoginActivity.class);
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


