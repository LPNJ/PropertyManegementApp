package com.example.z00s600149.propertymanegementapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

import dialog.ShowDialog;
import json.JsonResolution;
import task.AsyncTaskListener.CallbackListener;
import response.GetPropertyResponse;
import webApi.WebApi;
import webApi.WebApiImpl;

/**
 * サーバーに保存されている資産情報をすべて表示させる
 */
public class PropertySelectAllActivity extends AppCompatActivity{

    private static final String TAG = "PropertySelectAllAct";

     //資産の情報を表示するリストビュー
    private ListView mProperties;

    private WebApi mWebApi;

    public PropertySelectAllActivity() {
        super();
        mWebApi = new WebApiImpl();
        Log.i(TAG, "propertySelectAll activity start");
    }

    public PropertySelectAllActivity(WebApi webApi) {
        super();
        mWebApi = webApi;
        Log.i(TAG, "propertySelectAll activity start");
    }

    void setApi(WebApi webApi) {
        mWebApi = webApi;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_property_select_all);

        //IDと対応付け
        mProperties = (ListView) findViewById(R.id.listview_all);

        //サーバー通信開始（製品名取得）
        mWebApi.getProperty(mListener);
    }

    CallbackListener<GetPropertyResponse> mListener = new CallbackListener<GetPropertyResponse>() {
        @Override
        public void onPostExecute(GetPropertyResponse response) {

            if (response.getError().equals("1")){
                new ShowDialog(PropertySelectAllActivity.this).show(R.string.error);
            }

            ArrayList<String> productNumber;
            ArrayList<String> productName = new ArrayList<>();

            //JSON文字列にキーを指定して値を取得
            new JsonResolution().toListAll(response,productName);

            //画面表示用の資産番号＋製品名の情報保持用
            productNumber = new ArrayList<>();

            for (int i = 0; i < response.getInfos().size(); i++) {
                productNumber.add(String.valueOf( response.getInfos().get(i).getAssetId()) +" "+ productName.get(i));
            }

            if(productNumber.size()==0){
                new ShowDialog(PropertySelectAllActivity.this).show(R.string.not_found_property);
            }

            //Spinnerに情報を登録
            ArrayAdapter<String> myAdapterManager = new ArrayAdapter<String>(PropertySelectAllActivity.this, android.R.layout.simple_list_item_1, productNumber);
            myAdapterManager.setDropDownViewResource(android.R.layout.simple_list_item_1);
            mProperties.setAdapter(myAdapterManager);
            mProperties.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                //リストから任意のものを選択した場合の動作保証
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Intent i = new Intent(PropertySelectAllActivity.this, PropertyReferenceActivity.class);
                    i.putExtra(IntentKey.NUMBER, String.valueOf(response.getInfos().get(position).getAssetId()));
                    startActivity(i);
                }
            });
        }
    };
}
