package com.example.z00s600149.propertymanegementapp;

import android.os.Build;
import android.widget.ListView;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;
import org.robolectric.shadows.ShadowLog;
import java.util.ArrayList;
import entity.LoginUserNameHolder;
import json.GetPropertyJson;
import task.AsyncTaskListener.CallbackListener;
import response.GetPropertyResponse;
import webApi.WebApiMock;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class, sdk = Build.VERSION_CODES.O, application = TestApplication.class)
public class PropertySelectManagerActivityTest {

    private PropertySelectManagerActivity mPropertySelectManagerActivity;
    /**
     * @Before がついたメソッドは、すべてのテストメソッド(@Testがついたメソッド)の
     * 実施前に呼び出される。
     */
    @Before
    public void setup() {
        // android.utils.Log を使ったログを出力する
        ShadowLog.stream = System.out;
        // Activityを生成
        mPropertySelectManagerActivity = TestUtils.createActivityWithoutOnCreate(PropertySelectManagerActivity.class);
    }

    /**
     * @After がついたメソッドは、すべてのテストメソッド(@Testがついたメソッド)の
     * 実施後に呼び出される。
     */
    @After
    public void teardown() {
    }

    /**
     * 資産情報の一覧を取得し、選択した後資産情報参照画面に遷移する
     */
    @Test
    public void goToThePropertySelectAllActivitySuccess() {
        // ●setup(準備)
        //ユーザー名を固定
        LoginUserNameHolder name = LoginUserNameHolder.getInstance();
        name.setName("komiyama");
        // GetPropertyTaskで必ず成功が返るように設定
        mPropertySelectManagerActivity.setApi(new WebApiMock() {
            @Override
            public void getProperty(CallbackListener<GetPropertyResponse> listener) {
                listener.onPostExecute(createPropertyJson("0"));
            }
        });
        mPropertySelectManagerActivity.onCreate(null);
        ListView view = (ListView) mPropertySelectManagerActivity.findViewById(R.id.listview_maneger);

        // ●execute(テストの実施)
        //取得した資産情報リストから一つ選択
        view.performItemClick(view, 0, 0);

        // ●verify(検証)
        String nextActivityName = TestUtils.getNextActivityName(mPropertySelectManagerActivity);
        assertThat(nextActivityName, is(PropertyReferenceActivity.class.getName()));
    }

    /**
     * 資産情報の一覧を取得に失敗し、ダイアログを表示
     */
    @Test
    public void goToThePropertySelectAllActivityFailed() {
        // GetPropertyTaskで必ず失敗が返るように設定
        mPropertySelectManagerActivity.setApi(new WebApiMock(){
            @Override
            public void getProperty(CallbackListener<GetPropertyResponse> listener) {
                listener.onPostExecute(createPropertyJson("1"));
            }
        });
        mPropertySelectManagerActivity.onCreate(null);
        // ●verify(検証)

        String message = TestUtils.getLatestAlertDialogMessage();
        assertThat(message, is(RuntimeEnvironment.application.getString(R.string.error)));
    }

    /**
     * ログインIDと資産管理者の名前が一致せず、資産情報が見つからない
     */
    @Test
    public void goToThePropertySelectAllActivityFailedByEmptyProperty() {
        // GetPropertyTaskで必ず成功が返るように設定
        mPropertySelectManagerActivity.setApi(new WebApiMock(){
            @Override
            public void getProperty(CallbackListener<GetPropertyResponse> listener) {
                listener.onPostExecute(createPropertyJsonNotMatch("0"));
            }
        });
        mPropertySelectManagerActivity.onCreate(null);
        // ●verify(検証)

        String message = TestUtils.getLatestAlertDialogMessage();
        assertThat(message, is(RuntimeEnvironment.application.getString(R.string.not_found_property)));

    }

    /**
     * エラーコードを指定し、資産情報を登録
     */
    private GetPropertyResponse createPropertyJson(String error) {
        String productManager = "komiyama";
        String productUser = "komiyama";
        String location = "新横浜";
        String controlNumber = "10001";
        String productNmae = "ハンディプリンタ";
        String purchaseCategory = "レンタル";
        String propertyCategory = "機器";
        String complete = "";

        GetPropertyJson json = new GetPropertyJson("komiyama", 10001, "{" +
                "\"mPropertyManager\":" + "\""+ productManager+ "\"" +
                ","+"\"mPropertyUser\":" + "\""+ productUser + "\"" +
                ","+"\"mLocation\":" + "\""+location + "\"" +
                ","+"\"mControlNumber\":" + "\""+controlNumber + "\"" +
                ","+"\"mProductName\":" + "\""+productNmae + "\"" +
                ","+"\"mPurchaseCategory\":" +"\""+ purchaseCategory + "\"" +
                ","+"\"mPropertyCategory\":" + "\""+propertyCategory + "\"" +
                ","+"\"mComplement\":" + "\""+ complete + "\"" +
                '}');

        ArrayList<GetPropertyJson> list = new ArrayList<>();
        list.add(json);
        return new GetPropertyResponse(error, list);
    }

    /**
     * エラーコードを指定し、資産情報を登録。ただし、資産管理者の名前は異なる
     */
    private GetPropertyResponse createPropertyJsonNotMatch(String error) {
        String productManager = "nakayama";
        String productUser = "komiyama";
        String location = "新横浜";
        String controlNumber = "10001";
        String productNmae = "ハンディプリンタ";
        String purchaseCategory = "レンタル";
        String propertyCategory = "機器";
        String complete = "";

        GetPropertyJson json = new GetPropertyJson("komiyama", 10001, "{" +
                "\"mPropertyManager\":" + "\""+ productManager+ "\"" +
                ","+"\"mPropertyUser\":" + "\""+ productUser + "\"" +
                ","+"\"mLocation\":" + "\""+location + "\"" +
                ","+"\"mControlNumber\":" + "\""+controlNumber + "\"" +
                ","+"\"mProductName\":" + "\""+productNmae + "\"" +
                ","+"\"mPurchaseCategory\":" +"\""+ purchaseCategory + "\"" +
                ","+"\"mPropertyCategory\":" + "\""+propertyCategory + "\"" +
                ","+"\"mComplement\":" + "\""+ complete + "\"" +
                '}');

        ArrayList<GetPropertyJson> list = new ArrayList<>();
        list.add(json);
        return new GetPropertyResponse(error, list);
    }

}