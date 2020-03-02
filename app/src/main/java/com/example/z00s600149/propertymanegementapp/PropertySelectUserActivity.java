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
import entity.LoginNameSingleton;
import entity.PropertyInfo;
import task.AsyncTaskListener.CallbackListener;
import task.GetTargetNamePropertyInfoTask;
import task.ResultListener;
import task.impl.GetPropertyInfoTaskImpl;
import task.mock.GetTargetNamePropertyInfoTaskMock;
import task.response.GetPropertyResponse;

public class PropertySelectUserActivity extends AppCompatActivity implements CallbackListener<GetPropertyResponse> {

    ArrayList<String> mControlNumber;
    ArrayList<String> mProductNumber;

    /**/
    private GetTargetNamePropertyInfoTask mPropertyInfosTask;

    /**/
    private GetPropertyInfoTaskImpl mGetPropertyInfoTaskImpl;


    /*デフォルトコンストラクタ*/
    public PropertySelectUserActivity() {
        super();
        mGetPropertyInfoTaskImpl = new GetPropertyInfoTaskImpl(this);
        Log.i("PropertySelectUser", "PropertySelectUser activity contstructor");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_property_select_user);

        mGetPropertyInfoTaskImpl.execute();

    }


    ResultListener<ArrayList<PropertyInfo>> responseListener = new ResultListener<ArrayList<PropertyInfo>>() {

        @Override
        public void onResult(ArrayList<PropertyInfo> propertyInfos) {

            mControlNumber = new ArrayList<>();
            mProductNumber = new ArrayList<>();

            for (int i = 0; i < propertyInfos.size(); i++) {
                if(LoginNameSingleton.getInstanse().getName().equals(propertyInfos.get(i).getPropertyUser())) {
                    mControlNumber.add(propertyInfos.get(i).getControlNumber());
                    String ProductNumber = propertyInfos.get(i).getProductName() + propertyInfos.get(i).getControlNumber();
                    mProductNumber.add(ProductNumber);
                }
            }

            ListView properties = (ListView) findViewById(R.id.listview_user);

            ArrayAdapter<String> myAdapter_Manager = new ArrayAdapter<String>(PropertySelectUserActivity.this, android.R.layout.simple_list_item_1, mProductNumber);
            myAdapter_Manager.setDropDownViewResource(android.R.layout.simple_list_item_1);
            properties.setAdapter(myAdapter_Manager);

            properties.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                    Intent i = new Intent(PropertySelectUserActivity.this, PropertyReferenceActivity.class);

                    i.putExtra(IntentKey.CONTROL_NUMBER,  mControlNumber.get(position));
                    startActivity(i);

                }

            });
        }
    };

    @Override
    public void onPostExecute(GetPropertyResponse response) {

        ArrayList<String> pUser = new ArrayList<>();
        ArrayList<String> pName = new ArrayList<>();

        ArrayList<String> SendNumber = new ArrayList<>();

        ObjectMapper mapper = new ObjectMapper();
        try {
            for (int i = 0; response.getInfos().size() > i; i++) {
                PropertyInfoJSON info = mapper.readValue(response.getInfos().get(i).getProperty(), PropertyInfoJSON.class);
                pUser.add(info.mPropertyUser);
                pName.add(info.mProductName);
            }

            mControlNumber = new ArrayList<>();
            mProductNumber = new ArrayList<>();

            for (int i = 0; i < response.getInfos().size(); i++) {
                if(pUser.get(i).equals(LoginNameSingleton.getInstanse().getName())) {
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

        } catch (JsonParseException e) {
            e.printStackTrace();
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}