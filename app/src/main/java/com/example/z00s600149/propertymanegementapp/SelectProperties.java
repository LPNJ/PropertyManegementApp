package com.example.z00s600149.propertymanegementapp;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

import entity.LoginUserNameHolder;
import json.JsonResolution;
import task.response.GetPropertyResponse;

public class SelectProperties {

    private ArrayList<String> mProductNumber;

    private Activity mActivity;

    ListView mProperties;

    public SelectProperties(Activity activity){
        mActivity = activity;
    }

    public void getProperties(String rule, GetPropertyResponse response){

        final String roleUser = "USER";
        final String roleManager = "MANAGER";

        ArrayList<String> pManager = new ArrayList<>();
        ArrayList<String> pUser = new ArrayList<>();
        ArrayList<String> pName = new ArrayList<>();
        ArrayList<String> SendNumber = new ArrayList<>();

        //JSON文字列にキーを指定して値を取得
        if(rule.equals(roleUser)) {
            new JsonResolution().toListUser(response, pUser, pName);
            mProperties = mActivity.findViewById(R.id.listview_user);
            mProductNumber = new ArrayList<>();
            for (int i = 0; i < response.getInfos().size(); i++) {
                if(pUser.get(i).equals(LoginUserNameHolder.getInstance().getName())) {
                    mProductNumber.add(response.getInfos().get(i).getAssetId().toString() + " " + pName.get(i));
                    SendNumber.add(response.getInfos().get(i).getAssetId().toString());
                }
            }
        }
        else if(rule.equals(roleManager)){
            new JsonResolution().toListManager(response , pManager,pName);
            mProperties = mActivity.findViewById(R.id.listview_maneger);
            mProductNumber = new ArrayList<>();
            for (int i = 0; i < response.getInfos().size(); i++) {
                if(pManager.get(i).equals(LoginUserNameHolder.getInstance().getName())) {
                    mProductNumber.add(response.getInfos().get(i).getAssetId().toString() + " " + pName.get(i));
                    SendNumber.add(response.getInfos().get(i).getAssetId().toString());
                }
            }
        }
        ArrayAdapter<String> myAdapter_Manager = new ArrayAdapter<String>(mActivity, android.R.layout.simple_list_item_1, mProductNumber);
        myAdapter_Manager.setDropDownViewResource(android.R.layout.simple_list_item_1);
        mProperties.setAdapter(myAdapter_Manager);
        mProperties.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(mActivity, PropertyReferenceActivity.class);
                intent.putExtra(IntentKey.NUMBER, SendNumber.get(position));
                mActivity.startActivity(intent);
            }
        });
    }

}
