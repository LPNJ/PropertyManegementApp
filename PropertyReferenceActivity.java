package com.example.z00s600149.propertymanegementapp;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import entity.UserInfo;
import task.DeletePropertyInfoTask;
import task.GetPropertyInfoTask;
import task.ResultListener;
import task.mock.DeletePropertyInfoTaskMock;
import task.mock.GetNameTaskMock;
import task.mock.GetPropertyInfoTaskMock;
import task.serialize.PropertyDeleteRequest;
import task.serialize.PropertyInfoRequest;
import task.serialize.PropertyInfoResponse;

public class PropertyReferenceActivity extends AppCompatActivity {

    /**
     * 資産管理者用ID
     */
    private TextView mManager;
    /**
     * 資産管理者用ID
     */
    private TextView mUser;
    /**
     * 資産管理者用ID
     */
    private TextView mPlace;
    /**
     * 資産管理者用ID
     */
    private TextView mControlNumber;
    /**
     * 資産管理者用ID
     */
    private TextView mProduct;
    /**
     * 資産管理者用ID
     */
    private TextView mPurchase;
    /**
     * 資産管理者用ID
     */
    private TextView mAssets;
    /**
     * 資産管理者用ID
     */
    private TextView mRemark;

    /**
     * 変更画面遷移用ボタン
     */
    private Button mEditButton;

    /**
     * 削除用ボタン
     */
    private Button mDeleteButton;
    /**
     * 印刷画面遷移用ボタン
     */
    private Button mPrinterButton;

    /**
     *
     */
    private GetPropertyInfoTask mGetPropertyInfoTask;

    /**
     *
     */
    private DeletePropertyInfoTask mDeletePropertyInfoTask;

    /**
     * デフォルトコンストラクタ
     */
    public PropertyReferenceActivity() {
        super();
        mGetPropertyInfoTask = new GetPropertyInfoTaskMock();
        mDeletePropertyInfoTask = new DeletePropertyInfoTaskMock();
        //mErrorMessage.put(-1, R.string.cannot_connect);
        //mErrorMessage.put(11, R.string.cannot_register_error);
        Log.i("Regist", "register activity contstructor");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_property_reference);

        mManager = (TextView) findViewById(R.id.reference_manager);
        mUser = (TextView) findViewById(R.id.reference_user);
        mPlace = (TextView) findViewById(R.id.reference_place);
        mControlNumber = (TextView) findViewById(R.id.reference_controlnumber);
        mProduct = (TextView) findViewById(R.id.reference_product);
        mPurchase = (TextView) findViewById(R.id.reference_purchase);
        mAssets = (TextView) findViewById(R.id.reference_assets);
        mRemark = (TextView) findViewById(R.id.reference_remark);

        mEditButton = (Button) findViewById(R.id.reference_edit);
        mDeleteButton = (Button) findViewById(R.id.reference_delete);
        mPrinterButton = (Button) findViewById(R.id.reference_print);



        mGetPropertyInfoTask.execute(getIntent().getStringExtra(IntentKey.CONTROL_NUMBER),mResultListener);

        PropertyReferenceOnClickListener listener = new PropertyReferenceOnClickListener();
        mEditButton.setOnClickListener(listener);
        mDeleteButton.setOnClickListener(listener);
        mPrinterButton.setOnClickListener(listener);


    }

    private ResultListener<PropertyInfoResponse> mResultListener = new ResultListener<PropertyInfoResponse>() {

        @Override
        public void onResult(PropertyInfoResponse propertyInfoResponse) {
            mManager.setText(propertyInfoResponse.getPropertyInfo().getPropertyManager());
            mUser.setText(propertyInfoResponse.getPropertyInfo().getPropertyUser());
            mPlace.setText(propertyInfoResponse.getPropertyInfo().getLocation());
            mControlNumber.setText(propertyInfoResponse.getPropertyInfo().getControlNumber());
            mProduct.setText(propertyInfoResponse.getPropertyInfo().getProductName());
            mPurchase.setText(propertyInfoResponse.getPropertyInfo().getPurchaseCategory());
            mAssets.setText(propertyInfoResponse.getPropertyInfo().getPropertyCategory());
            mRemark.setText(propertyInfoResponse.getPropertyInfo().getComplement());
        }
    };

    class PropertyReferenceOnClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.reference_edit: {
                    Intent intent = new Intent(PropertyReferenceActivity.this, PropertyEditActivity.class);
                    intent.putExtra(IntentKey.CONTROL_NUMBER, mControlNumber.getText().toString());
                    startActivity(intent);
                }
                break;
                case R.id.reference_delete: {
                    new AlertDialog.Builder(PropertyReferenceActivity.this)
                            .setMessage("削除してもよろしいですか？")
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    mDeletePropertyInfoTask.execute(new PropertyDeleteRequest("",""),mResultListenerDelete);
                                }
                            })
                            .setNegativeButton("Cancel", null)
                            .show();
                }
                break;
                case R.id.reference_print: {
                    Intent intent = new Intent(PropertyReferenceActivity.this, PrinterActivity.class);
                    intent.putExtra(IntentKey.CONTROL_NUMBER, mControlNumber.getText().toString());
                    startActivity(intent);
                }
                break;
            }
        }
    }

    private ResultListener<Integer> mResultListenerDelete = new ResultListener<Integer>() {

        @Override
        public void onResult(Integer result) {

            if (result != 0) {
                new AlertDialog.Builder(PropertyReferenceActivity.this)
                        .setMessage("削除できませんでした")
                        .setPositiveButton("OK", null)
                        .create()
                        .show();
                return;
            }
            else if(result == 0){
                Intent intent = new Intent(PropertyReferenceActivity.this, MenuActivity.class);
                startActivity(intent);
            }

        }
    };

}
