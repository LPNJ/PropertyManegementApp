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
import webApi.WebApi;
import webApi.WebApiImpl;
import task.response.GetPropertyResponse;

public class PropertySelectUserActivity extends AppCompatActivity implements CallbackListener<GetPropertyResponse> {

    private ArrayList<String> mControlNumber;
    private ArrayList<String> mProductNumber;

    private final WebApi mWebApi;

    /*デフォルトコンストラクタ*/
    public PropertySelectUserActivity() {
        super();
        mWebApi = new WebApiImpl();
        Log.i("PropertySelectUser", "PropertySelectUser activity contstructor");
    }

    /*デフォルトコンストラクタ*/
    public PropertySelectUserActivity(WebApi WebApi) {
        super();
        mWebApi = WebApi;
        Log.i("PropertySelectUser", "PropertySelectUser activity contstructor");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_property_select_user);

        mWebApi.getProperty(this);

    }

    // TODO GetPropertyResponseのJson解析をPropertySelectManagerActivityとPropertySelectUserActivityと
    // PropertySelectAllActivityでやっとるから共通化する。
    // Jsonの解析は画面ごとじゃなくてタスクごとやと思うからそっちに移動させるとよいかも。
    @Override
    public void onPostExecute(GetPropertyResponse response) {

        ArrayList<String> pUser = new ArrayList<>();
        ArrayList<String> pName = new ArrayList<>();
        ArrayList<String> SendNumber = new ArrayList<>();

        //JSON文字列にキーを指定して値を取得
        new JsonResolution().toListUser(response , pUser,pName);

            mControlNumber = new ArrayList<>();
            mProductNumber = new ArrayList<>();

            for (int i = 0; i < response.getInfos().size(); i++) {
                if(pUser.get(i).equals(LoginUserNameHolder.getInstanse().getName())) {
                    mControlNumber.add(response.getInfos().get(i).getProperty());
                    mProductNumber.add(response.getInfos().get(i).getAssetId().toString() + " " + pName.get(i));
                    SendNumber.add(response.getInfos().get(i).getAssetId().toString());
                }
            }

            ListView properties = (ListView) findViewById(R.id.listview_user);

            ArrayAdapter<String> myAdapter_Manager = new ArrayAdapter<String>(PropertySelectUserActivity.this, android.R.layout.simple_list_item_1, mProductNumber);
            myAdapter_Manager.setDropDownViewResource(android.R.layout.simple_list_item_1);
            properties.setAdapter(myAdapter_Manager);

            properties.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                    Intent intent = new Intent(PropertySelectUserActivity.this, PropertyReferenceActivity.class);
                    intent.putExtra(IntentKey.NUMBER, SendNumber.get(position));
                    startActivity(intent);
                }
            });
    }

}