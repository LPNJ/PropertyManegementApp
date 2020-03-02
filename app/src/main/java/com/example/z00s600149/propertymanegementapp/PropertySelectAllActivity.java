package com.example.z00s600149.propertymanegementapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.ArrayList;

import JSONCLASS.PropertyInfoJSON;
import entity.PropertyInfo;
import task.AsyncTaskListener.CallbackListener;
import task.GetPropertyInfoTask;
import task.GetTargetNamePropertyInfoTask;
import task.ResultListener;
import task.impl.GetPropertyInfoTaskImpl;
import task.mock.GetTargetNamePropertyInfoTaskMock;
import task.response.GetPropertyEntity;
import task.response.GetPropertyResponse;

public class PropertySelectAllActivity extends AppCompatActivity implements CallbackListener<GetPropertyResponse> {

    //データ保持用
    ArrayList<String> mControlNumber;
    ArrayList<String> mProductNumber;

    //資産の情報を表示するリストビュー
    ListView properties;

    /* MOCK */
    private GetTargetNamePropertyInfoTask mPropertyInfosTask;

    /* サーバー接続（登録資産情報のデータを取得する） */
    private GetPropertyInfoTaskImpl mGetPropertyInfoTaskImpl;

    /*デフォルトコンストラクタ*/
    public PropertySelectAllActivity() {
        super();
        mGetPropertyInfoTaskImpl = new GetPropertyInfoTaskImpl(this);
        Log.i("Propert", "register activity contstructor");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_property_select_all);

        //IDと対応付け
        properties = (ListView) findViewById(R.id.listview_all);

        //サーバー通信開始（製品名取得）
        mGetPropertyInfoTaskImpl.execute();
    }

    //MOCK用
    ResultListener<ArrayList<PropertyInfo>> responseListener = new ResultListener<ArrayList<PropertyInfo>>() {

        @Override
        public void onResult(ArrayList<PropertyInfo> propertyInfos) {

            //資産番号と番号＋製品名
            mControlNumber = new ArrayList<>();
            mProductNumber = new ArrayList<>();

            for (int i = 0; i < propertyInfos.size(); i++) {
                mControlNumber.add(propertyInfos.get(i).getControlNumber());
                String ProductNumber = propertyInfos.get(i).getProductName() + propertyInfos.get(i).getControlNumber();
                mProductNumber.add(ProductNumber);
            }

            ListView properties = (ListView) findViewById(R.id.listview_all);

            ArrayAdapter<String> myAdapter_Manager = new ArrayAdapter<String>(PropertySelectAllActivity.this, android.R.layout.simple_list_item_1, mProductNumber);
            myAdapter_Manager.setDropDownViewResource(android.R.layout.simple_list_item_1);
            properties.setAdapter(myAdapter_Manager);

            properties.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Intent i = new Intent(PropertySelectAllActivity.this, PropertyReferenceActivity.class);
                    i.putExtra(IntentKey.CONTROL_NUMBER, mControlNumber.get(position));
                    startActivity(i);
                }
            });
        }
    };

    @Override
    public void onPostExecute(GetPropertyResponse response) {

        ArrayList<String> productName = new ArrayList<>();

        //JSON文字列にキーを指定して値を取得
        ObjectMapper mapper = new ObjectMapper();
        try {
            for (int i = 0; response.getInfos().size() > i; i++) {
                PropertyInfoJSON info = mapper.readValue(response.getInfos().get(i).getProperty(), PropertyInfoJSON.class);
            productName.add(info.mProductName);
        }

        //画面表示用の資産番号＋製品名の情報保持用
            mProductNumber = new ArrayList<>();

            for (int i = 0; i < response.getInfos().size(); i++) {
                mProductNumber.add(response.getInfos().get(i).getAssetId().toString() +" "+ productName.get(i));
            }

            //Spinnerに情報を登録
            ArrayAdapter<String> myAdapter_Manager = new ArrayAdapter<String>(PropertySelectAllActivity.this, android.R.layout.simple_list_item_1, mProductNumber);
            myAdapter_Manager.setDropDownViewResource(android.R.layout.simple_list_item_1);
            properties.setAdapter(myAdapter_Manager);
            properties.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                //リストから任意のものを選択した場合の動作保証
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Intent i = new Intent(PropertySelectAllActivity.this, PropertyReferenceActivity.class);
                    i.putExtra(IntentKey.NUMBER, response.getInfos().get(position).getAssetId().toString());
                    startActivity(i);
                }
            });

        } catch (JsonParseException e) {
            e.printStackTrace();
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
