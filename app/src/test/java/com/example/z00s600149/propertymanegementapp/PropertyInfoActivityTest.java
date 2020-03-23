package com.example.z00s600149.propertymanegementapp;

import android.os.Build;
import android.widget.Button;
import android.widget.EditText;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;
import org.robolectric.shadows.ShadowLog;
import entity.LoginUserNameHolder;
import webApi.WebApiMock;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class, sdk = Build.VERSION_CODES.O, application = TestApplication.class)
public class PropertyInfoActivityTest {

    private PropertyInfoActivity mPropertyInfoActivity;
    /**
     * @Before がついたメソッドは、すべてのテストメソッド(@Testがついたメソッド)の
     * 実施前に呼び出される。
     */
    @Before
    public void setup() {
        // android.utils.Log を使ったログを出力する
        ShadowLog.stream = System.out;
        // Activityを生成
        mPropertyInfoActivity = TestUtils.createActivity(PropertyInfoActivity.class, null);
    }

    /**
     * @After がついたメソッドは、すべてのテストメソッド(@Testがついたメソッド)の
     * 実施後に呼び出される。
     */
    @After
    public void teardown() {
    }

    /**
     * 資産登録が成功し、資産番号発行画面に遷移するテスト
     */
    @Test
    public void registerPropertyInfoSuccess() {
        // ●setup(準備)
        registerPropertyInfo("新横浜", "ハンディプリンタ");
        // RegisterPropertyInfoTaskで必ず成功が返るように設定
        mPropertyInfoActivity.setApi(new WebApiMock());
        Button button = (Button) mPropertyInfoActivity.findViewById(R.id.property_button_regist);

        //ユーザー名を固定
        LoginUserNameHolder name = LoginUserNameHolder.getInstance();
        name.setName("komiyama");

        // ●execute(テストの実施)
        // 登録ボタンを押下
        button.performClick();

        // ●verify(検証)
        String nextActivityName = TestUtils.getNextActivityName(mPropertyInfoActivity);
        assertThat(nextActivityName, is(ControlNumberIssuedActivity.class.getName()));
    }

    /**
     * 空欄があることを示すダイアログを表示
     */
    @Test
    public void registerPropertyInfoFailedByEmptyLocationInfo() {
        // ●setup(準備)
        registerPropertyInfo("", "ハンディプリンタ");
        // RegisterPropertyInfoTaskで必ず失敗が返るように設定
        mPropertyInfoActivity.setApi(new WebApiMock());
        Button button = (Button) mPropertyInfoActivity.findViewById(R.id.property_button_regist);

        // ●execute(テストの実施)
        // 登録ボタンを押下
        button.performClick();

        // ●verify(検証)
        String message = TestUtils.getLatestAlertDialogMessage();
        assertThat(message, is(RuntimeEnvironment.application.getString(R.string.not_input)));
    }

    /**
     * 空欄があることを示すダイアログを表示
     */
    @Test
    public void registerPropertyInfoFailedByEmptyProductNumberInfo() {
        // ●setup(準備)
        // IDとPassを設定
        registerPropertyInfo("新横浜", "");
        // RegisterPropertyInfoTaskで必ず失敗が返るように設定
        mPropertyInfoActivity.setApi(new WebApiMock());
        Button button = (Button) mPropertyInfoActivity.findViewById(R.id.property_button_regist);

        // ●execute(テストの実施)
        // 登録ボタンを押下
        button.performClick();

        // ●verify(検証)
        String message = TestUtils.getLatestAlertDialogMessage();
        assertThat(message, is(RuntimeEnvironment.application.getString(R.string.not_input)));
    }

    /**
     * サーバーの通信に異常があることを示すダイアログを表示
     */
    @Test
    public void registerPropertyInfoFailedByConnectError() {
        // ●setup(準備)
        registerPropertyInfo("新横浜", "ハンディプリンタ");
        // RegisterPropertyInfoTaskで必ず成功が返るように設定
        mPropertyInfoActivity.setApi(new WebApiMock());
        Button button = (Button) mPropertyInfoActivity.findViewById(R.id.property_button_regist);

        //ユーザー名を固定
        LoginUserNameHolder name = LoginUserNameHolder.getInstance();
        name.setName("failure");

        // ●execute(テストの実施)
        // 登録ボタンを押下
        button.performClick();

        // ●verify(検証)
        String message = TestUtils.getLatestAlertDialogMessage();
        assertThat(message, is(RuntimeEnvironment.application.getString(R.string.cannot_connect)));
    }

    /**
     * 許可されていない文字が含まれており、それを示すダイアログを表示
     */
    @Test
    public void registerPropertyInfoFailedByIllegalInput() {
        // ●setup(準備)
        registerPropertyInfo("failure", "ハンディプリンタ");
        // RegisterPropertyInfoTaskで必ず成功が返るように設定
        mPropertyInfoActivity.setApi(new WebApiMock());
        Button button = (Button) mPropertyInfoActivity.findViewById(R.id.property_button_regist);

        //ユーザー名を固定
        LoginUserNameHolder name = LoginUserNameHolder.getInstance();
        name.setName("komiyama");

        // ●execute(テストの実施)
        // 登録ボタンを押下
        button.performClick();

        // ●verify(検証)
        String message = TestUtils.getLatestAlertDialogMessage();
        assertThat(message, is(RuntimeEnvironment.application.getString(R.string.not_permit_character)));
    }

    /**
     * 指定したユーザーが見つからず、それを示すダイアログを表示
     */
    @Test
    public void registerPropertyInfoFailedByNotFoundAccount() {
        // ●setup(準備)
        registerPropertyInfo("新横浜", "ハンディプリンタ");
        // RegisterPropertyInfoTaskで必ず成功が返るように設定
        mPropertyInfoActivity.setApi(new WebApiMock());
        Button button = (Button) mPropertyInfoActivity.findViewById(R.id.property_button_regist);

        //ユーザー名を固定
        LoginUserNameHolder name = LoginUserNameHolder.getInstance();
        name.setName("");

        // ●execute(テストの実施)
        // 登録ボタンを押下
        button.performClick();

        // ●verify(検証)
        String message = TestUtils.getLatestAlertDialogMessage();
        assertThat(message, is(RuntimeEnvironment.application.getString(R.string.cannot_find_user)));
    }


    /**
     * サーバーに保存できる資産が上限に達し、その状態で資産を新規に登録しようとしたときにダイアログを表示する
     */
    @Test
    public void registerPropertyInfoFailedByFullProperties() {
        // ●setup(準備)
        // IDとPassを設定
        registerPropertyInfo("MAX", "ハンディプリンタ");
        // RegisterPropertyInfoTaskで必ず成功が返るように設定
        mPropertyInfoActivity.setApi(new WebApiMock());
        Button button = (Button) mPropertyInfoActivity.findViewById(R.id.property_button_regist);

        //ユーザー名を固定
        LoginUserNameHolder name = LoginUserNameHolder.getInstance();
        name.setName("komiyama");

        // ●execute(テストの実施)
        // 登録ボタンを押下
        button.performClick();

        // ●verify(検証)
        String message = TestUtils.getLatestAlertDialogMessage();
        assertThat(message, is(RuntimeEnvironment.application.getString(R.string.limit)));
    }


    /**
     * 引数に指定したパラメータを設定します。
     * @param location
     * @param productName
     */
    private void registerPropertyInfo(String location ,String productName) {

        EditText propertyLocation = (EditText) mPropertyInfoActivity.findViewById(R.id.property_info_editText_location);
        EditText propertyProductName = (EditText) mPropertyInfoActivity.findViewById(R.id.property_info_editText_productname);

        propertyLocation.setText(location);
        propertyProductName.setText(productName);

    }



}