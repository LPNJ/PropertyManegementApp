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
import task.AsyncTaskListener.CallbackListener;
import task.GetPropertyInfoTask;
import webApi.WebApi;
import webApi.WebApiImpl;
import task.mock.GetPropertyInfoTaskMock;
import task.response.GetPropertyResponse;

public class PropertySelectAllActivity extends AppCompatActivity implements CallbackListener<GetPropertyResponse> {

    // TODO メンバ変数は基本privateにする、ほかも同様
    //データ保持用
    private ArrayList<String> mControlNumber;
    private ArrayList<String> mProductNumber;

    //資産の情報を表示するリストビュー
    private ListView mProperties;

    private final WebApi mWebApi;

    /*デフォルトコンストラクタ*/
    public PropertySelectAllActivity() {
        super();
        mWebApi = new WebApiImpl();
        Log.i("Propert", "register activity start");
    }

    /*デフォルトコンストラクタ*/
    public PropertySelectAllActivity(WebApi WebApi) {
        super();
        mWebApi = WebApi;
        Log.i("Propert", "register activity start");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_property_select_all);

        //IDと対応付け
        mProperties = (ListView) findViewById(R.id.listview_all);

        //サーバー通信開始（製品名取得）
        mWebApi.getProperty(this);
    }

    @Override
    public void onPostExecute(GetPropertyResponse response) {

        ArrayList<String> productName = new ArrayList<>();

        //JSON文字列にキーを指定して値を取得
        new JsonResolution().toListAll(response , productName);

        //画面表示用の資産番号＋製品名の情報保持用
            mProductNumber = new ArrayList<>();

            for (int i = 0; i < response.getInfos().size(); i++) {
                mProductNumber.add(response.getInfos().get(i).getAssetId().toString() +" "+ productName.get(i));
            }

            //Spinnerに情報を登録
            ArrayAdapter<String> myAdapter_Manager = new ArrayAdapter<String>(PropertySelectAllActivity.this, android.R.layout.simple_list_item_1, mProductNumber);
            myAdapter_Manager.setDropDownViewResource(android.R.layout.simple_list_item_1);
            mProperties.setAdapter(myAdapter_Manager);
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
}
