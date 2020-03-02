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

public class PropertySelectActivity extends AppCompatActivity implements View.OnClickListener{

    /*ボタン*/
    private Button mManagerButton;
    /*ボタン*/
    private Button mUserButton;
    /*ボタン*/
    private Button mAllPropertyButton;

    /*デフォルトコンストラクタ*/
    public PropertySelectActivity() {
        super();
        Log.i("PropertySelect", "PropertySelect activity contstructor");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_property_select);

        mManagerButton = (Button) findViewById(R.id.select_manager);
        mAllPropertyButton = (Button) findViewById(R.id.select_all_property);
        mUserButton = (Button) findViewById(R.id.select_user);

        mManagerButton.setOnClickListener(this);
        mAllPropertyButton.setOnClickListener(this);
        mUserButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.select_manager: {
                Intent intent = new Intent(PropertySelectActivity.this, PropertySelectManegerActivity.class);
                startActivity(intent);
            }
            break;
            case R.id.select_user: {
                Intent intent = new Intent(PropertySelectActivity.this, PropertySelectUserActivity.class);
                startActivity(intent);
            }
            break;
            case R.id.select_all_property: {
                Intent intent = new Intent(PropertySelectActivity.this, PropertySelectAllActivity.class);
                startActivity(intent);
            }
            break;
        }
    }
}