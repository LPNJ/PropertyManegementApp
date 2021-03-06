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
 * 資産管理者がログイン名と一致していた場合のみ結果を表示させるActivity
 */
public class PropertySelectManagerActivity extends AppCompatActivity{

    private static final String TAG = "PropertySelectMAct";

    ArrayList<String> mProductNumber;

    ListView properties;

    private final WebApi mWebApi;

    /*デフォルトコンストラクタ*/
    public PropertySelectManagerActivity() {
        super();
        mWebApi = new WebApiImpl();
        Log.i(TAG, "PropertySelectManager activity start");
    }

    /*デフォルトコンストラクタ*/
    public PropertySelectManagerActivity(WebApi WebApi) {
        super();
        mWebApi = WebApi;
        Log.i(TAG, "PropertySelectManager activity start");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_property_select_maneger);
        mWebApi.getProperty(listener);

        properties = (ListView) findViewById(R.id.listview_maneger);
    }

    CallbackListener<GetPropertyResponse> listener = new CallbackListener<GetPropertyResponse>() {
        final String roleManager = "MANAGER";
        @Override
        public void onPostExecute(GetPropertyResponse response) {
            new SelectProperties(PropertySelectManagerActivity.this).getProperties(roleManager,response);
        }
    };
}