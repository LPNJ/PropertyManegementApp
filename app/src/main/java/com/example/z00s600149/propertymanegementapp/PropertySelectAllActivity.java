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
import json.JsonResolution;
import task.AsyncTaskListener.CallbackListener;
import webApi.WebApi;
import webApi.WebApiImpl;
import task.response.GetPropertyResponse;

/**
 * サーバーに保存されている資産情報をすべて表示させる
 */
public class PropertySelectAllActivity extends AppCompatActivity{

    private static final String TAG = "PropertySelectAllAct";

    // TODO メンバ変数は基本privateにする、ほかも同様
    // TODO 使わん奴は消す
    // TODO onPostExecuteでしか使ってないからローカル変数にする

    //資産の情報を表示するリストビュー
    private ListView mProperties;

    private final WebApi mWebApi;

    // TODO 意味ないコメント
    public PropertySelectAllActivity() {
        super();
        mWebApi = new WebApiImpl();
        // TODO TAGを定義する、あとほかのactivityクラスのタグはxxxActivityだったのでそれに命名規則に合わせる
        Log.i(TAG, "register activity start");
    }

    // TODO 意味ない、デフォルトじゃない
    public PropertySelectAllActivity(WebApi WebApi) {
        super();
        mWebApi = WebApi;
        Log.i(TAG, "register activity start");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_property_select_all);

        //IDと対応付け
        mProperties = (ListView) findViewById(R.id.listview_all);

        //サーバー通信開始（製品名取得）
        mWebApi.getProperty(listener);
    }

    // TODO メンバ変数定義して実装しとるのになんでここだけimplements？
    // TODO 統一したほうが良いので他に合わせる

    CallbackListener<GetPropertyResponse> listener = new CallbackListener<GetPropertyResponse>() {
        @Override
        public void onPostExecute(GetPropertyResponse response) {
            ArrayList<String> mProductNumber;
            ArrayList<String> productName = new ArrayList<>();

            //JSON文字列にキーを指定して値を取得
            new JsonResolution().toListAll(response,productName);

            //画面表示用の資産番号＋製品名の情報保持用
            mProductNumber = new ArrayList<>();

            for (int i = 0; i < response.getInfos().size(); i++) {
                mProductNumber.add(response.getInfos().get(i).getAssetId().toString() +" "+ productName.get(i));
            }

            //Spinnerに情報を登録
            // TODO キャメルケースにする
            ArrayAdapter<String> myAdapterManager = new ArrayAdapter<String>(PropertySelectAllActivity.this, android.R.layout.simple_list_item_1, mProductNumber);
            myAdapterManager.setDropDownViewResource(android.R.layout.simple_list_item_1);
            mProperties.setAdapter(myAdapterManager);
            mProperties.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                //リストから任意のものを選択した場合の動作保証
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Intent i = new Intent(PropertySelectAllActivity.this, PropertyReferenceActivity.class);
                    i.putExtra(IntentKey.NUMBER, response.getInfos().get(position).getAssetId().toString());
                    startActivity(i);
                }
            });
        }
    };
}
