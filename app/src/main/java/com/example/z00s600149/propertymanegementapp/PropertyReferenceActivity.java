package com.example.z00s600149.propertymanegementapp;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

import JSONCLASS.PropertyInfoJSON;
import entity.LoginNameSingleton;
import entity.UserInfo;
import task.AsyncTaskListener.CallbackListener;
import task.DeletePropertyInfoTask;
import task.GetPropertyInfoTask;
import task.Request.DeletePropertyRequest;
import task.ResultListener;
import task.impl.DeletePropertyTaskImpl;
import task.impl.GetReferenceInfoTaskImpl;
import task.mock.DeletePropertyInfoTaskMock;
import task.mock.GetNameTaskMock;
import task.mock.GetPropertyInfoTaskMock;
import task.response.GetPropertyResponse;
import task.response.GetReferencePropertyResponse;
import task.serialize.PropertyDeleteRequest;
import task.serialize.PropertyInfoRequest;
import task.serialize.PropertyInfoResponse;

public class PropertyReferenceActivity extends AppCompatActivity implements View.OnClickListener{

    /*資産管理者用ID*/
    private TextView mManager;
    /*資産利用用ID*/
    private TextView mUser;
    /*資産保管場所用ID*/
    private TextView mPlace;
    /*資産番号ID*/
    private TextView mControlNumber;
    /*製品名用ID*/
    private TextView mProduct;
    /*購入区分用ID*/
    private TextView mPurchase;
    /*資産区分用ID*/
    private TextView mAssets;
    /*補足用ID*/
    private TextView mRemark;

    /*編集画面遷移用ボタン*/
    private Button mEditButton;

    /*削除用ボタン*/
    private Button mDeleteButton;
    /*印刷画面遷移用ボタン*/
    private Button mPrinterButton;

    /* MOCK */
    private GetPropertyInfoTask mGetPropertyInfoTask;

    /* BOCK*/
    private DeletePropertyInfoTask mDeletePropertyInfoTask;

    /* サーバー通信（ユーザー名取得）*/
    private GetReferenceInfoTaskImpl mGetReferenceTaskImpl;

    /*サーバー通信（データ削除）*/
    private DeletePropertyTaskImpl mDeleteTaskImpl;

    /*デフォルトコンストラクタ*/
    public PropertyReferenceActivity() {
        super();
        mGetReferenceTaskImpl = new GetReferenceInfoTaskImpl(mGetProperty);
        mDeleteTaskImpl = new DeletePropertyTaskImpl(mDeleteProperty);
        Log.i("PropertyReference", "PropertyReference activity contstructor");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_property_reference);

        //ID対応付け
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

        //サーバー接続（名前取得）
        mGetReferenceTaskImpl.execute(getIntent().getStringExtra(IntentKey.NUMBER));

        //ボタン押下
        mEditButton.setOnClickListener(this);
        mDeleteButton.setOnClickListener(this);
        mPrinterButton.setOnClickListener(this);

    }

    //MOCK
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
                                mDeleteTaskImpl.execute(new DeletePropertyRequest(LoginNameSingleton.getInstanse().getName(),Integer.parseInt(getIntent().getStringExtra(IntentKey.NUMBER))));
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

    //MOCK
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
            } else if (result == 0) {
                Intent intent = new Intent(PropertyReferenceActivity.this, MenuActivity.class);
                startActivity(intent);
            }

        }
    };

    //サーバー通信結果
    private CallbackListener<GetReferencePropertyResponse> mGetProperty = new CallbackListener<GetReferencePropertyResponse>() {
        @Override
        public void onPostExecute(GetReferencePropertyResponse response) {

            ObjectMapper mapper = new ObjectMapper();
            try {
                PropertyInfoJSON info = mapper.readValue(response.getInfos(), PropertyInfoJSON.class);

                mManager.setText(info.mPropertyManager);
                mUser.setText(info.mPropertyUser);
                mPlace.setText(info.mLocation);
                mControlNumber.setText(getIntent().getStringExtra(IntentKey.NUMBER));
                mProduct.setText(info.mProductName);
                mPurchase.setText(info.mPurchaseCategory);
                mAssets.setText(info.mPropertyCategory);
                mRemark.setText(info.mComplement);

            } catch (JsonParseException e) {
                e.printStackTrace();
            } catch (JsonMappingException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    };


    //サーバー通信結果
    private CallbackListener<String> mDeleteProperty = new CallbackListener<String>() {

        @Override
        public void onPostExecute(String response) {

            new AlertDialog.Builder(PropertyReferenceActivity.this)
                    .setMessage("削除が完了しました")
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intent = new Intent(PropertyReferenceActivity.this, MenuActivity.class);
                            startActivity(intent);
                        }
                    })
                    .show();
        }
    };

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // BackBtnアクション
        if(keyCode==KeyEvent.KEYCODE_BACK){
            new AlertDialog.Builder(PropertyReferenceActivity.this)
                    .setMessage("メニュー画面に戻りますか？")
                    .setPositiveButton("OK",new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intent = new Intent(PropertyReferenceActivity.this, MenuActivity.class);
                            startActivity(intent);
                        }
                    })
                    .setNegativeButton("Cancel",null)
                    .create()
                    .show();
        }
        return true;
    }

}