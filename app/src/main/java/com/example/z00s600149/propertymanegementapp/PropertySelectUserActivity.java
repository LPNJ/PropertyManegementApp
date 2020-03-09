package com.example.z00s600149.propertymanegementapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ListView;

import java.util.ArrayList;

import task.AsyncTaskListener.CallbackListener;
import task.response.GetPropertyResponse;
import webApi.WebApi;
import webApi.WebApiImpl;

/**
 * 資産利用者がログイン名と一致していた場合のみ結果を表示させるActivity
 */
public class PropertySelectUserActivity extends AppCompatActivity{

    private static final String TAG = "PropertySelectUserAct";

    private ArrayList<String> mProductNumber;
    private final WebApi mWebApi;
    ListView properties;

    /*デフォルトコンストラクタ*/
    public PropertySelectUserActivity() {
        super();
        mWebApi = new WebApiImpl();
        Log.i(TAG, "PropertySelectUser activity start");
    }

    /*デフォルトコンストラクタ*/
    public PropertySelectUserActivity(WebApi WebApi) {
        super();
        mWebApi = WebApi;
        Log.i(TAG, "PropertySelectUser activity start");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_property_select_user);

        properties = (ListView) findViewById(R.id.listview_user);

        mWebApi.getProperty(listener);
    }

    CallbackListener<GetPropertyResponse> listener = new CallbackListener<GetPropertyResponse>() {
        final String roleUser = "USER";
        @Override
        public void onPostExecute(GetPropertyResponse response) {
            new SelectProperties(PropertySelectUserActivity.this).getProperties(roleUser,response);
        }
    };
}