package com.example.z00s600149.propertymanegementapp;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import java.util.ArrayList;

import entity.PropertyInfo;
import task.GetTargetNamePropertyInfoTask;
import task.PropertyInfoTask;
import task.ResultListener;
import task.mock.GetNameTaskMock;
import task.mock.GetTargetNamePropertyInfoTaskMock;
import task.mock.PropertyInfoTaskMock;
import task.serialize.PropertyRegistResponse;

public class PropertySelectActivity extends AppCompatActivity {

    private String LoginName = "komiyama";

    /**
     * 資産管理者用スピナー
     */
    private Spinner mManagerSpinner3;

    private ArrayList<String> mManager;
    private ArrayList<String> mUser = new ArrayList<>();


    private ArrayList<String> mProductName;
    private ArrayList<String> mProductName2;
    private ArrayList<String> mProductName3;


    private ArrayList<String> mControlNumber;
    private ArrayList<String> mControlNumber2;
    private ArrayList<String> mControlNumber3;

    private ArrayList<String> mProductNumber;
    private ArrayList<String> mProductNumber2;
    private ArrayList<String> mProductNumber3;

    /**
     * 資産情報登録用ボタン
     */
    private Button mManagerButton;
    /**
     * 資産情報登録用ボタン
     */
    private Button mUserButton;
    /**
     * 資産情報登録用ボタン
     */
    private Button mAllPropertyButton;

    /**
     *
     */
    private GetTargetNamePropertyInfoTask mPropertyInfosTask;

    /**
     * デフォルトコンストラクタ
     */
    public PropertySelectActivity() {
        super();
        mPropertyInfosTask = new GetTargetNamePropertyInfoTaskMock();
        //mErrorMessage.put(-1, R.string.cannot_connect);
        //mErrorMessage.put(11, R.string.cannot_register_error);
        Log.i("Regist", "register activity contstructor");
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_property_select);

        mManagerButton = (Button) findViewById(R.id.select_manager);
        mAllPropertyButton = (Button) findViewById(R.id.select_all_property);
        mUserButton = (Button) findViewById(R.id.select_user);

        PropertySelectActivityOnClickListener listener = new PropertySelectActivityOnClickListener();
        mManagerButton.setOnClickListener(listener);
        mAllPropertyButton.setOnClickListener(listener);
        mUserButton.setOnClickListener(listener);
    }

    class PropertySelectActivityOnClickListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.select_manager: {

                    Intent intent = new Intent(PropertySelectActivity.this, PropertySelectManegerActivity.class);
                    startActivity(intent);

//                    mPropertyInfosTask.execute("", responseListener);

                }
                break;
                case R.id.select_user: {

                    Intent intent = new Intent(PropertySelectActivity.this, PropertySelectUserActivity.class);
                    startActivity(intent);

//                    mPropertyInfosTask.execute("", responseListener2);

                }
                break;
                case R.id.select_all_property: {

                    Intent intent = new Intent(PropertySelectActivity.this, PropertySelectAllActivity.class);
                    startActivity(intent);

//                    mPropertyInfosTask.execute("", responseListener3);
                }
                break;
            }
        }
    }

    ResultListener<ArrayList<PropertyInfo>> responseListener = new ResultListener<ArrayList<PropertyInfo>>() {
        @Override
        public void onResult(ArrayList<PropertyInfo> propertyInfos) {

            mManager = new ArrayList<>();
            mProductName = new ArrayList<>();
            mProductNumber = new ArrayList<>();

            for (int i = 0; i < propertyInfos.size(); i++) {
                if (LoginName.equals(propertyInfos.get(i).getPropertyManager())) {
                    mProductName.add(propertyInfos.get(i).getProductName());
                    mControlNumber.add(propertyInfos.get(i).getControlNumber());
                    String ProductNumber = propertyInfos.get(i).getProductName() + propertyInfos.get(i).getControlNumber();
                    mProductNumber.add(ProductNumber);
                }
            }

            ArrayAdapter<String> myAdapter_Manager = new ArrayAdapter<String>(PropertySelectActivity.this, android.R.layout.simple_list_item_1, mProductNumber);
            myAdapter_Manager.setDropDownViewResource(android.R.layout.simple_list_item_1);
            mManagerSpinner3.setAdapter(myAdapter_Manager);

        }
    };

    ResultListener<ArrayList<PropertyInfo>> responseListener2 = new ResultListener<ArrayList<PropertyInfo>>() {


        @Override
        public void onResult(ArrayList<PropertyInfo> propertyInfos) {

        }
    };

    ResultListener<ArrayList<PropertyInfo>> responseListener3 = new ResultListener<ArrayList<PropertyInfo>>() {

        @Override
        public void onResult(ArrayList<PropertyInfo> propertyInfos) {

            mProductName3 = new ArrayList<>();
            mControlNumber3 = new ArrayList<>();
            mProductNumber3 = new ArrayList<>();

            for (int i = 0; i < propertyInfos.size(); i++) {
                mProductName3.add(propertyInfos.get(i).getProductName());
                mControlNumber3.add(propertyInfos.get(i).getControlNumber());
                String ProductNumber = propertyInfos.get(i).getProductName() + propertyInfos.get(i).getControlNumber();
                mProductNumber3.add(ProductNumber);
            }

            ArrayAdapter<String> myAdapter_Manager = new ArrayAdapter<String>(PropertySelectActivity.this, android.R.layout.simple_list_item_1, mProductNumber3);
            myAdapter_Manager.setDropDownViewResource(android.R.layout.simple_list_item_1);
            mManagerSpinner3.setAdapter(myAdapter_Manager);

            mManagerSpinner3.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                }

            });
        }
    };
}