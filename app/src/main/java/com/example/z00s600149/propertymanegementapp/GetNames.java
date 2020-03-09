package com.example.z00s600149.propertymanegementapp;

import android.app.Activity;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import dialog.ShowDialog;
import task.response.GetNameResponse;

public class GetNames {

    /*ログインユーザー情報保持用リスト*/
    ArrayAdapter<String> myAdapter_Manager;
    /** 資産管理者用スピナー */
    private Spinner mManager;
    /** 資産利用者用スピナー */
    private Spinner mPropertyUser;
    /** 購入区分スピナー */
    private Spinner mPurchase_Category_Spinner;
    /** 資産区分スピナー */
    private Spinner mProperty_Category_Spinner;
    private Activity mActivity;

    public GetNames(Activity activity){
        mActivity = activity;
    }

    public void getLoginName(String role ,GetNameResponse response) {

        final String roleName = "INFO";

        if (Integer.parseInt(response.getError()) == 1) {
            new ShowDialog(mActivity).show(R.string.error);
        } else {

            if(role.equals(roleName)){
                mManager = mActivity.findViewById(R.id.property_info_spinner_1);
                mPropertyUser = mActivity.findViewById(R.id.property_info_spinner_2);
                mPurchase_Category_Spinner = mActivity.findViewById(R.id.property_info_spinner_3);
                mProperty_Category_Spinner = mActivity. findViewById(R.id.property_info_spinner_4);
            }
            else{
                mManager = mActivity.findViewById(R.id.property_info_spinner_7);
                mPropertyUser = mActivity.findViewById(R.id.property_info_spinner_5);
                mPurchase_Category_Spinner = mActivity.findViewById(R.id.property_info_spinner_8);
                mProperty_Category_Spinner = mActivity.findViewById(R.id.property_info_spinner_6);
            }

            myAdapter_Manager = new ArrayAdapter<String>(mActivity, android.R.layout.simple_list_item_1, response.getNames());
            ArrayAdapter<String> myAdapter_User = new ArrayAdapter<String>(mActivity, android.R.layout.simple_list_item_1, response.getNames());
            ArrayAdapter<String> myAdapter_Purchase_Category = new ArrayAdapter<String>(mActivity, android.R.layout.simple_list_item_1, mActivity.getResources().getStringArray(R.array.Purchase_Category));
            ArrayAdapter<String> myAdapter_Property_Category = new ArrayAdapter<String>(mActivity, android.R.layout.simple_list_item_1, mActivity.getResources().getStringArray(R.array.Property_Category));

            myAdapter_Manager.setDropDownViewResource(android.R.layout.simple_list_item_1);
            myAdapter_User.setDropDownViewResource(android.R.layout.simple_list_item_1);
            myAdapter_Purchase_Category.setDropDownViewResource(android.R.layout.simple_list_item_1);
            myAdapter_Property_Category.setDropDownViewResource(android.R.layout.simple_list_item_1);

            mManager.setAdapter(myAdapter_Manager);
            mPropertyUser.setAdapter(myAdapter_User);
            mPurchase_Category_Spinner.setAdapter(myAdapter_Purchase_Category);
            mProperty_Category_Spinner.setAdapter(myAdapter_Property_Category);
        }

    }

}
