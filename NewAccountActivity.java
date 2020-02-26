package com.example.z00s600149.propertymanegementapp;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.json.JSONException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import entity.EditableUserInfo;
import entity.UserInfo;
import task.AsyncTaskListener.NewAccountCallbackListener;
import task.LogoutTask;
import task.NewAccountTask;
import task.ResultListener;
import task.impl.NewAccountTaskImpl2;
import task.mock.NewAccountTaskMock;
import validator.NewAccountValidator;

public class NewAccountActivity extends AppCompatActivity implements ResultListener<Integer>,NewAccountCallbackListener {

    /** 登録用ボタン */
    private Button mRegist;

    /** ユーザー情報新規作成用ID　*/
    private EditText mId;
    /**　ユーザー情報新規作成用PASSWARD　*/
    private EditText mPass;
    /**　再入力用PASSWARD　*/
    private EditText mconfirmationPass;
    /**　ユーザーIDデータ保持用　*/
    private String mId_info;
    /**　ユーザーPASSWARDデータ保持用　*/
    private String mPass_info;
    /**　再入力用PASSWARD　*/
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
        mNewAccountTask = new NewAccountTaskMock();
//        mNewAccountTaskImpl = new NewAccountTaskImpl();
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
                    if(validationResult == 1){
                        show("入力されていない項目があります");
                    }
                    else if(validationResult == 2){
                        show("許可されていない文字が含まれています");
                    }
                    else{
                        new AlertDialog.Builder(NewAccountActivity.this)
                                .setMessage("登録してもよろしいですか？")
                                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        mNewAccountTask.execute(new UserInfo(mId_info, mPass_info), NewAccountActivity.this);
//                                        mNewAccountTaskImpl.execute(new UserInfo(mId_info, mPass_info));
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

    void show(String msg){
        new AlertDialog.Builder(NewAccountActivity.this)
                .setMessage(msg)
                .setPositiveButton("OK", null)
                .create()
                .show();
    }

    @Override
    public void onPostExecute(String result) {
        int resultCode = Integer.parseInt(result);
        if(resultCode != 0){

        }
        else{
            Intent intent = new Intent(NewAccountActivity.this , LogoutTask.class);
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


    class NewAccountTaskImpl extends AsyncTask<UserInfo, Void, String> {
        private NewAccountCallbackListener listener;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // doInBackground前処理
        }

        @Override
        protected String doInBackground(UserInfo... params) {
            Log.i("NewAccountTask", "doInBackground start.");
            org.json.JSONObject json = new org.json.JSONObject();
            try {
                json.put("userId",params[0].getUserId());
                json.put("password",params[0].getPassword());

            } catch (JSONException e) {
                e.printStackTrace();
            }

            String data = json.toString();
            HttpURLConnection con = null;
            URL url = null;

//        String urlSt = "http://133.139.115.159:8080/assets/api/user";

            String urlSt = "http://133.139.115.154:8080/assets/api/user";


//            String urlSt = "http://133.139.115.154:8080/event/api/user";

            try {
                // URLの作成
                url = new URL(urlSt);
                // 接続用HttpURLConnectionオブジェクト作成
                con = (HttpURLConnection)url.openConnection();
                // リクエストメソッドの設定
                con.setRequestMethod("POST");
                // リダイレクトを自動で許可しない設定
//            con.setInstanceFollowRedirects(false);
                // URL接続からデータを読み取る場合はtrue
                con.setDoInput(true);
                // URL接続にデータを書き込む場合はtrue
                con.setDoOutput(true);
                //
                con.setRequestProperty("Content-Type", "application/json");
                //
                OutputStream os = null;
                try {
                    os = con.getOutputStream();
                } catch (Throwable t) {
                    t.printStackTrace();
                }
                Log.i("NewAccountTask", "get output stream. con=" + os.toString());
                // 送信するデータの設定
                os.write(data.getBytes("UTF-8"));
                //
                os.flush();
                //
                os.close();

                Log.i("NewAccountTask", "send data set.");
                // 接続
                con.connect(); // ①

                Log.i("NewAccountTask", "connect.");
                InputStream in = con.getInputStream();
                String readSt = readInputStream(in);

                String returnCode = null;

                ObjectMapper mapper = new ObjectMapper();
                try {
                    Info info = mapper.readValue(readSt, Info.class);
                    returnCode = info.error;
                } catch (IOException e) {
                    e.printStackTrace();
                }

                return returnCode;

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            finally {
                if (con != null) {
                    con.disconnect();
                }
                Log.i("NewAccountTask", "doInBackground end.");
            }

            return null;

        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            // doInBackground後処理
            if (listener != null) {
                listener.onPostExecute(result);
            }

        }


        public String readInputStream(InputStream in) throws IOException {
            StringBuffer sb = new StringBuffer();
            String st = "";

            BufferedReader br = new BufferedReader(new InputStreamReader(in, "UTF-8"));
            while((st = br.readLine()) != null)
            {
                sb.append(st);
            }
            try
            {
                in.close();
            }
            catch(Exception e)
            {
                e.printStackTrace();
            }

            return sb.toString();
        }

        class Info {
            @JsonProperty("error")
            public String error;

            public String getError() {
                return error;
            }

        }

    }


}


