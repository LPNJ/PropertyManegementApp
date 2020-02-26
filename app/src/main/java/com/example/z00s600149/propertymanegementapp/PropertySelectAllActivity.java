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

import entity.PropertyInfo;
import task.AsyncTaskListener.CallbackListener_getAssetId;
import task.AsyncTaskListener.CallbackListener_getProperties;
import task.GetPropertyInfoTask;
import task.GetTargetNamePropertyInfoTask;
import task.ResultListener;
import task.impl.GetPropertyInfoTaskImpl;
import task.mock.GetTargetNamePropertyInfoTaskMock;
import task.response.GetPropertyEntity;
import task.response.GetPropertyResponse;

public class PropertySelectAllActivity extends AppCompatActivity implements CallbackListener_getProperties {

    ArrayList<String> mProductName;
    ArrayList<String> mControlNumber;
    ArrayList<String> mProductNumber;

    /**
     *
     */
    private GetTargetNamePropertyInfoTask mPropertyInfosTask;

    /**
     *
     */
    private GetPropertyInfoTaskImpl mGetPropertyInfoTaskImpl;

    /**
     * デフォルトコンストラクタ
     */
    public PropertySelectAllActivity() {
        super();
//        mPropertyInfosTask = new GetTargetNamePropertyInfoTaskMock();
        mGetPropertyInfoTaskImpl = new GetPropertyInfoTaskImpl(this);
        //mErrorMessage.put(-1, R.string.cannot_connect);
        //mErrorMessage.put(11, R.string.cannot_register_error);
        Log.i("Regist", "register activity contstructor");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_property_select_all);

        mGetPropertyInfoTaskImpl.execute("");
    }


    ResultListener<ArrayList<PropertyInfo>> responseListener = new ResultListener<ArrayList<PropertyInfo>>() {

        @Override
        public void onResult(ArrayList<PropertyInfo> propertyInfos) {

            mProductName = new ArrayList<>();
            mControlNumber = new ArrayList<>();
            mProductNumber = new ArrayList<>();

            for (int i = 0; i < propertyInfos.size(); i++) {
                mProductName.add(propertyInfos.get(i).getProductName());
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

        ArrayList<String> pName = new ArrayList<>();

//        Log.i("CallBackできてる？", response.getInfos().get(0).getProperty());
//        Log.i("CallBackできてる？", response.getInfos().get(1).getProperty());
//        Log.i("CallBackできてる？", String.valueOf(response.getInfos().size()));

        ObjectMapper mapper = new ObjectMapper();
        try {
            for (int i = 0; response.getInfos().size() > i; i++) {
            Info_GetProperties_JSON info = mapper.readValue(response.getInfos().get(i).getProperty(), Info_GetProperties_JSON.class);
                Log.i("管理者"+i, info.mPropertyManager);
                Log.i("利用者"+i, info.mPropertyUser);
                Log.i("設置場所"+i, info.mLocation);
                Log.i("製品名"+i, info.mProductName);
            pName.add(info.mProductName);
//                mapper = new ObjectMapper();
        }





            mProductName = new ArrayList<>();
            mControlNumber = new ArrayList<>();
            mProductNumber = new ArrayList<>();

            Log.i("CallBackできてる？", "できてるよ");

            for (int i = 0; i < response.getInfos().size(); i++) {
//            mProductName.add(response.getInfos().get(i).getProperty().getProductName());
                mProductName.add(response.getInfos().get(i).getProperty());
//            mControlNumber.add(response.getInfos().get(i).getProperty().getControlNumber());
                mControlNumber.add(response.getInfos().get(i).getProperty());
//            String ProductNumber = response.getInfos().get(i).getProperty().getProductName() + response.getInfos().get(i).getProperty().getControlNumber();
//            String ProductNumber = response.getInfos().get(i).getProperty() + response.getInfos().get(i).getProperty();
//            mProductNumber.add(ProductNumber);
                mProductNumber.add(response.getInfos().get(i).getAssetId().toString() +" "+ pName.get(i));
            }

            ListView properties = (ListView) findViewById(R.id.listview_all);

            ArrayAdapter<String> myAdapter_Manager = new ArrayAdapter<String>(PropertySelectAllActivity.this, android.R.layout.simple_list_item_1, mProductNumber);
            myAdapter_Manager.setDropDownViewResource(android.R.layout.simple_list_item_1);
            properties.setAdapter(myAdapter_Manager);

            properties.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                    Intent i = new Intent(PropertySelectAllActivity.this, PropertyReferenceActivity.class);

                    i.putExtra(IntentKey.NUMBER, response.getInfos().get(position).getAssetId().toString());
                    Log.i("資産番号は？", response.getInfos().get(position).getAssetId().toString());
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

class Info_GetProperties_JSON {
    @JsonProperty("mPropertyManager")
    public String mPropertyManager;
    @JsonProperty("mPropertyUser")
    public String mPropertyUser;
    @JsonProperty("mLocation")
    public String mLocation;
    @JsonProperty("mControlNumber")
    public String mControlNumber;
    @JsonProperty("mProductName")
    public String mProductName;
    @JsonProperty("mPurchaseCategory")
    public String mPurchaseCategory;
    @JsonProperty("mPropertyCategory")
    public String mPropertyCategory;
    @JsonProperty("mComplement")
    public String mComplement;

    public String getProductName() {
        return mProductName;
    }

}
