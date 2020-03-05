package com.example.z00s600149.propertymanegementapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class PropertySelectActivity extends AppCompatActivity implements View.OnClickListener{

    // TODO コメントに意味がないからこのコメントであれば書く必要がない
    private Button mManagerButton;
    private Button mUserButton;
    private Button mAllPropertyButton;

    // TODO コメントに意味がないからこのコメントであれば書く必要がない
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