package com.example.z00s600149.propertymanegementapp;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

import jsonclass.PropertyInfoJson;
import entity.LoginUserNameHolder;
import task.AsyncTaskListener.CallbackListener;
import task.Request.DeletePropertyRequest;
import task.DeletePropertyTask;
import task.GetReferenceInfoTask;
import webApi.WebApi;
import webApi.WebApiImpl;
import task.mock.DeletePropertyInfoTaskMock;
import task.mock.GetReferenceInfoTaskMock;
import task.response.GetReferencePropertyResponse;

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

    private final WebApi mWebApi;

    /*デフォルトコンストラクタ*/
    public PropertyReferenceActivity() {
        super();
        mWebApi = new WebApiImpl();
        // TODO TAGを定義する
        Log.i("PropertyReference", "PropertyReference activity contstructor");
    }

    /*デフォルトコンストラクタ*/
    public PropertyReferenceActivity(WebApi WebApi) {
        super();
        mWebApi = WebApi;
        // TODO constructorが全部タイポ、ほかのクラスも確認する
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
        mWebApi.getReferenceProperty(mGetProperty , getIntent().getStringExtra(IntentKey.NUMBER));

        //ボタン押下
        mEditButton.setOnClickListener(this);
        mDeleteButton.setOnClickListener(this);
        mPrinterButton.setOnClickListener(this);

    }

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
                        // TODO stringsに追加、「.setMessage("」とかで検索して漏れないか確認してください
                        .setMessage("削除してもよろしいですか？")
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                mWebApi.deleteProperty(new DeletePropertyRequest(LoginUserNameHolder.getInstanse().getName(),Integer.parseInt(getIntent().getStringExtra(IntentKey.NUMBER))),mDeleteProperty);
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

    //サーバー通信結果
    private CallbackListener<GetReferencePropertyResponse> mGetProperty = new CallbackListener<GetReferencePropertyResponse>() {
        @Override
        public void onPostExecute(GetReferencePropertyResponse response) {

            // TODO この処理はGetReferenceInfoTaskのparseJsonで行わる処理
            ObjectMapper mapper = new ObjectMapper();
            try {
                PropertyInfoJson info = mapper.readValue(response.getInfo(), PropertyInfoJson.class);

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