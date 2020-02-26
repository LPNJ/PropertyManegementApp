package com.example.z00s600149.propertymanegementapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;

import entity.PropertyInfo;
import task.GetNameTask;
import task.GetPropertyInfoTask;
import task.PropertyInfoTask;
import task.ResultListener;
import task.mock.GetNameTaskMock;
import task.mock.GetPropertyInfoTaskMock;
import task.mock.PropertyInfoTaskMock;
import task.serialize.PropertyInfoRequest;
import task.serialize.PropertyInfoResponse;
import task.serialize.PropertyRegistRequest;
import task.serialize.PropertyRegistResponse;

public class PropertyEditActivity extends AppCompatActivity {

    /** 名前保持用リスト */
    private ArrayList<String> mNames;
    /** 購入区分保持用リスト */
    private ArrayList<String> mPurchase_Category;
    /** 資産区分保持用リスト */
    private ArrayList<String> mProperty_Category;
    /** 資産情報登録用ボタン */
    private Button mPropertyRegist;
    /** 資産管理者用スピナー */
    private Spinner mManager;
    /** 資産利用者用スピナー */
    private Spinner mPropertyUser;
    /** 購入区分スピナー */
    private Spinner mPurchase_Category_Spinner;
    /** 資産区分スピナー */
    private Spinner mProperty_Category_Spinner;

    /** ログイン用ID */
    private EditText mLocation;
    /** ログイン用ID */
    private EditText mProductName;
    /** ログイン用ID */
    private EditText mRemarks;


    private TextView mControlNumber;

    /**
     *
     */
    private GetNameTask mGetNameTask;

    /**
     *
     */
    private GetPropertyInfoTask mGetPropertyInfoTask;

    /**
     *
     */
    private PropertyInfoTask mPropertyInfoTask;


    /**
     * デフォルトコンストラクタ
     */
    public PropertyEditActivity() {
        super();
        mGetNameTask = new GetNameTaskMock();
        mGetPropertyInfoTask = new GetPropertyInfoTaskMock();
        mPropertyInfoTask = new PropertyInfoTaskMock();
        //mErrorMessage.put(-1, R.string.cannot_connect);
        //mErrorMessage.put(11, R.string.cannot_register_error);
        Log.i("Regist", "register activity contstructor");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_property_edit);

        mControlNumber = (TextView) findViewById(R.id.edit_controlnumber);
        mControlNumber.setText(getIntent().getStringExtra(IntentKey.CONTROL_NUMBER));

        mManager = (Spinner) findViewById(R.id.property_info_spinner_7);
        mPropertyUser = (Spinner) findViewById(R.id.property_info_spinner_5);
        mPurchase_Category_Spinner = (Spinner) findViewById(R.id.property_info_spinner_8);
        mProperty_Category_Spinner = (Spinner) findViewById(R.id.property_info_spinner_6);

        mLocation = (EditText) findViewById(R.id.property_edit_editText_location);
        mProductName = (EditText) findViewById(R.id.property_edit_editText_productname);
        mRemarks = (EditText) findViewById(R.id.property_edit_editText_remarks);

        mNames = new ArrayList<>();
        mGetNameTask.execute(mNames,mResultListener);

    }

    class PropertyInfoActivityOnClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.property_button_regist: {
                    mPropertyInfoTask.execute(
                            new PropertyRegistRequest(
                                    "",
                                    new PropertyInfo((String) mManager.getSelectedItem(),
                                            (String)mPropertyUser.getSelectedItem(),
                                            mLocation.getText().toString(),
                                            "",
                                            mProductName.getText().toString(),
                                            (String)mPurchase_Category_Spinner.getSelectedItem(),
                                            (String)mProperty_Category_Spinner.getSelectedItem(),
                                            mRemarks.getText().toString())),
                            mResponseListener);
                }
                break;
            }
        }
    }

    private ResultListener<ArrayList<String>> mResultListener = new ResultListener<ArrayList<String>>() {
        @Override
        public void onResult(ArrayList<String> names) {
            ArrayAdapter<String> myAdapter_Manager = new ArrayAdapter<String>(PropertyEditActivity.this ,android.R.layout.simple_list_item_1, names);
            ArrayAdapter<String> myAdapter_User = new ArrayAdapter<String>(PropertyEditActivity.this ,android.R.layout.simple_list_item_1, names);
            ArrayAdapter<String> myAdapter_Purchase_Category = new ArrayAdapter<String>(PropertyEditActivity.this ,android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.Purchase_Category));
            ArrayAdapter<String> myAdapter_Property_Category = new ArrayAdapter<String>(PropertyEditActivity.this ,android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.Property_Category));

            myAdapter_Manager.setDropDownViewResource(android.R.layout.simple_list_item_1);
            myAdapter_User.setDropDownViewResource(android.R.layout.simple_list_item_1);
            myAdapter_Purchase_Category.setDropDownViewResource(android.R.layout.simple_list_item_1);
            myAdapter_Property_Category.setDropDownViewResource(android.R.layout.simple_list_item_1);

            mManager.setAdapter(myAdapter_Manager);
            mPropertyUser.setAdapter(myAdapter_User);
            mPurchase_Category_Spinner.setAdapter(myAdapter_Purchase_Category);
            mProperty_Category_Spinner.setAdapter(myAdapter_Property_Category);
        }
    };

    private ResultListener<PropertyRegistResponse> mResponseListener = new ResultListener<PropertyRegistResponse>() {

        @Override
        public void onResult(PropertyRegistResponse result) {
            if (result == null) {
                throw new IllegalArgumentException("result is null");
            }
            if (result.getError() == 0) {
                Intent intent = new Intent(PropertyEditActivity.this, ControlNumberIssuedActivity.class);
                intent.putExtra(IntentKey.CONTROL_NUMBER, result.getControlNumber());
                Log.i("CONTROLNUMBER",result.getControlNumber());
                startActivity(intent);
            }
        }
    };

}
