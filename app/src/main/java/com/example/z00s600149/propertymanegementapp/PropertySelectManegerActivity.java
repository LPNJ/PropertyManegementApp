package com.example.z00s600149.propertymanegementapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

import jsonResolution.JsonResolution;
import entity.LoginUserNameHolder;
import task.AsyncTaskListener.CallbackListener;
import task.GetPropertyInfoTask;
import webApi.WebApi;
import webApi.WebApiImpl;
import task.mock.GetPropertyInfoTaskMock;
import task.response.GetPropertyResponse;

public class PropertySelectManegerActivity extends AppCompatActivity implements CallbackListener<GetPropertyResponse> {

    ArrayList<String> mProductName;
    ArrayList<String> mControlNumber;
    ArrayList<String> mProductNumber;

    private final WebApi mWebApi;

    /*デフォルトコンストラクタ*/
    public PropertySelectManegerActivity() {
        super();
        mWebApi = new WebApiImpl();
        // TODO 命名規則に合わせる最後にactivity付ける
        Log.i("PropertySelectManeger", "PropertySelectManeger activity contstructor");
    }

    /*デフォルトコンストラクタ*/
    public PropertySelectManegerActivity(WebApi WebApi) {
        super();
        mWebApi = WebApi;
        Log.i("PropertySelectManeger", "PropertySelectManeger activity contstructor");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_property_select_maneger);
        mWebApi.getProperty(this);
    }

    @Override
    public void onPostExecute(GetPropertyResponse response) {

        ArrayList<String> pManager = new ArrayList<>();
        ArrayList<String> pName = new ArrayList<>();

        ArrayList<String> SendNumber = new ArrayList<>();

        //JSON文字列にキーを指定して値を取得
        new JsonResolution().toListManager(response , pManager,pName);

        // TODO これ以下のインデント必要ない
            mProductName = new ArrayList<>();
            mControlNumber = new ArrayList<>();
            mProductNumber = new ArrayList<>();

            for (int i = 0; i < response.getInfos().size(); i++) {
                if(pManager.get(i).equals(LoginUserNameHolder.getInstanse().getName())) {
                    mProductName.add(response.getInfos().get(i).getProperty());
                    mControlNumber.add(response.getInfos().get(i).getProperty());
                    mProductNumber.add(response.getInfos().get(i).getAssetId().toString() + " " + pName.get(i));
                    SendNumber.add(response.getInfos().get(i).getAssetId().toString());
                }
            }

            ListView properties = (ListView) findViewById(R.id.listview_maneger);

            ArrayAdapter<String> myAdapter_Manager = new ArrayAdapter<String>(PropertySelectManegerActivity.this, android.R.layout.simple_list_item_1, mProductNumber);
            myAdapter_Manager.setDropDownViewResource(android.R.layout.simple_list_item_1);
            properties.setAdapter(myAdapter_Manager);

            properties.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Intent i = new Intent(PropertySelectManegerActivity.this, PropertyReferenceActivity.class);
                    i.putExtra(IntentKey.NUMBER, SendNumber.get(position));
                    startActivity(i);
                }

            });
    }
}