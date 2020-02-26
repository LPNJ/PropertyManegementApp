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

import entity.PropertyInfo;
import task.GetTargetNamePropertyInfoTask;
import task.ResultListener;
import task.mock.GetTargetNamePropertyInfoTaskMock;

public class PropertySelectManegerActivity extends AppCompatActivity {

    ArrayList<String> mProductName;
    ArrayList<String> mControlNumber;
    ArrayList<String> mProductNumber;

    final String NAME = "komiyama";

    /**
     *
     */
    private GetTargetNamePropertyInfoTask mPropertyInfosTask;

    /**
     * デフォルトコンストラクタ
     */
    public PropertySelectManegerActivity() {
        super();
        mPropertyInfosTask = new GetTargetNamePropertyInfoTaskMock();
        //mErrorMessage.put(-1, R.string.cannot_connect);
        //mErrorMessage.put(11, R.string.cannot_register_error);
        Log.i("Regist", "register activity contstructor");
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_property_select_maneger);

        mPropertyInfosTask.execute("",responseListener);
    }


    ResultListener<ArrayList<PropertyInfo>> responseListener = new ResultListener<ArrayList<PropertyInfo>>() {

        @Override
        public void onResult(ArrayList<PropertyInfo> propertyInfos) {

            mProductName = new ArrayList<>();
            mControlNumber = new ArrayList<>();
            mProductNumber = new ArrayList<>();

            for (int i = 0; i < propertyInfos.size(); i++) {
                if(NAME.equals(propertyInfos.get(i).getPropertyManager())) {
                    mProductName.add(propertyInfos.get(i).getProductName());
                    mControlNumber.add(propertyInfos.get(i).getControlNumber());
                    String ProductNumber = propertyInfos.get(i).getProductName() + propertyInfos.get(i).getControlNumber();
                    mProductNumber.add(ProductNumber);
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

                    i.putExtra(IntentKey.CONTROL_NUMBER,  mControlNumber.get(position));
                    startActivity(i);

                }

            });
        }
    };

}
