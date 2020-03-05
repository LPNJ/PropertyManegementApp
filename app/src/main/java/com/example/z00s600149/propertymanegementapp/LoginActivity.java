package com.example.z00s600149.propertymanegementapp;

// TODO 必要ないimportは消す。他も同様
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import Dialog.ShowDialog;
import entity.LoginUserNameHolder;
import entity.UserInfo;
import task.AsyncTaskListener.CallbackListener;
import webApi.WebApi;
import webApi.WebApiImpl;
import validator.UserLoginValidator;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "LoginAct";

    /** ログイン用ボタン */
    private Button mLogin;
    /** 新規ユーザー登録ボタン */
    private Button mNewAccount;
    /** ログイン用ID */
    private EditText mId;
    /** ログイン用パスワード */
    private EditText mPass;
    /** ログインIDデータ保持用 */
    String mIdInfo;
    /** ログイン用パスワードデータ保持用 */
    String mPassInfo;

    private final WebApi mWebApi;

    /*デフォルトコンストラクタ*/
    public LoginActivity() {
        super();
        mWebApi = new WebApiImpl();
        Log.i(TAG, "LOGIN Activity start");
    }

    public LoginActivity(WebApi webApi) {
        super();
        mWebApi = webApi;
        Log.i(TAG, "LOGIN Activity start");
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

        //ボタン押下の動作
        mLogin.setOnClickListener(this);
        mNewAccount.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.login_button_login: {
                mIdInfo = mId.getText().toString();
                mPassInfo = mPass.getText().toString();
                    int validationResult = new UserLoginValidator().validate(new UserInfo(mIdInfo,mPassInfo));
                    if(validationResult == 1){
                        new ShowDialog(LoginActivity.this).show(R.string.not_input);
                    }
                    else {
                        LoginUserNameHolder name = LoginUserNameHolder.getInstanse();
                        name.setName(mIdInfo);
                        mWebApi.login(new UserInfo(mIdInfo,mPassInfo), listener);
                    }
                }
            break;
            case R.id.login_button_newaccount: {
                Intent intent = new Intent(LoginActivity.this, NewAccountActivity.class);
                startActivity(intent);
            }
            break;
        }
    }

    /**
     * TODO onResultと処理が変わるのはおかしい。
     */
    CallbackListener<String> listener = new CallbackListener<String>() {
        @Override
        /*サーバー通信結果を受けるメソッド*/
        public void onPostExecute(String result) {
            int resultCode = Integer.parseInt(result);
            if(resultCode == 1){
                // TODO このメッセージに意味はない。resultCodeが1で返ってくるときの理由を書く。
                // 例えばサーバーエラーであるならそれがわかるメッセージにする
                new ShowDialog(LoginActivity.this).show(R.string.cannot_connect);
            }
            else if(resultCode == 21){
                new ShowDialog(LoginActivity.this).show(R.string.already_register);
            }
            else {
                Intent intent = new Intent(LoginActivity.this, MenuActivity.class);
                startActivity(intent);
            }
        }
    };

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // BackBtnアクション
        if(keyCode==KeyEvent.KEYCODE_BACK){
            //バックキー押下で何も動作させない
        }
        return true;
    }
}