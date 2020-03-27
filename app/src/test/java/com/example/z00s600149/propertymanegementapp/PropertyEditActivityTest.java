package com.example.z00s600149.propertymanegementapp;

import android.os.Build;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

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
public class PropertyEditActivityTest {


    private PropertyEditActivity mPropertyEditActivity;
    /**
     * @Before がついたメソッドは、すべてのテストメソッド(@Testがついたメソッド)の
     * 実施前に呼び出される。
     */
    @Before
    public void setup() {
        // android.utils.Log を使ったログを出力する
        ShadowLog.stream = System.out;
        // Activityを生成
        mPropertyEditActivity = TestUtils.createActivity(PropertyEditActivity.class, null);
    }

    /**
     * @After がついたメソッドは、すべてのテストメソッド(@Testがついたメソッド)の
     * 実施後に呼び出される。
     */
    @After
    public void teardown() {
    }

    /**
     * サーバーの通信に失敗し、失敗したダイアログを出すテスト
     */
    @Test
    public void editPropertyInfoFailedByConnectError() {
        // ●setup(準備)
        registerPropertyInfo("10001","新横浜", "ハンディプリンタ");
        // PropertyEditTaskで必ず成功が返るように設定
        mPropertyEditActivity.setApi(new WebApiMock());
        Button button = (Button) mPropertyEditActivity.findViewById(R.id.edit_button_edit);

        //ユーザー名を固定
        LoginUserNameHolder name = LoginUserNameHolder.getInstance();
        name.setName("failure");

        // ●execute(テストの実施)
        // 編集ボタンを押下
        button.performClick();
        // ダイアログのOKボタンを押下
        TestUtils.clickOKOnAlertDialog();

        // ●verify(検証)
        String message = TestUtils.getLatestAlertDialogMessage();
        assertThat(message, is(RuntimeEnvironment.application.getString(R.string.cannot_connect)));
    }

    /**
     * 不正な文字が含まれているため、その旨を示すダイアログを表示する
     */
    @Test
    public void editPropertyInfoFailedByNotPermitCharacter() {
        // ●setup(準備)
        registerPropertyInfo("10001","新横浜", "ハンディプリンタ");
        // PropertyEditTaskで必ず成功が返るように設定
        mPropertyEditActivity.setApi(new WebApiMock());
        Button button = (Button) mPropertyEditActivity.findViewById(R.id.edit_button_edit);

        //ユーザー名を固定
        LoginUserNameHolder name = LoginUserNameHolder.getInstance();
        name.setName("nakayama");

        // ●execute(テストの実施)
        // 編集ボタンを押下
        button.performClick();
        // ダイアログのOKボタンを押下
        TestUtils.clickOKOnAlertDialog();

        // ●verify(検証)
        String message = TestUtils.getLatestAlertDialogMessage();
        assertThat(message, is(RuntimeEnvironment.application.getString(R.string.not_permit_character)));
    }

    /**
     * 指定したユーザーが見つからない、という旨のダイアログを表示
     */
    @Test
    public void editPropertyInfoFailedForNotFoundUser() {
        // ●setup(準備)
        registerPropertyInfo("10001","新横浜", "ハンディプリンタ");
        // PropertyEditTaskで必ず成功が返るように設定
        mPropertyEditActivity.setApi(new WebApiMock());
        Button button = (Button) mPropertyEditActivity.findViewById(R.id.edit_button_edit);

        //ユーザー名を固定
        LoginUserNameHolder name = LoginUserNameHolder.getInstance();
        name.setName("katayama");

        // ●execute(テストの実施)
        // 編集ボタンを押下
        button.performClick();
        // ダイアログのOKボタンを押下
        TestUtils.clickOKOnAlertDialog();

        // ●verify(検証)
        String message = TestUtils.getLatestAlertDialogMessage();
        assertThat(message, is(RuntimeEnvironment.application.getString(R.string.cannot_find_user)));
    }

    /**
     * 指定した資産が見つからない場合、その旨を示すダイアログを表示
     */
    @Test
    public void editPropertyInfoFailedForNotFoundProperty() {
        // ●setup(準備)
        // IDとPassを設定
        registerPropertyInfo("10001","新横浜", "ハンディプリンタ");
        // PropertyEditTaskで必ず成功が返るように設定
        mPropertyEditActivity.setApi(new WebApiMock());
        Button button = (Button) mPropertyEditActivity.findViewById(R.id.edit_button_edit);

        //ユーザー名を固定
        LoginUserNameHolder name = LoginUserNameHolder.getInstance();
        name.setName("takayama");

        // ●execute(テストの実施)
        // 編集ボタンを押下
        button.performClick();
        // ダイアログのOKボタンを押下
        TestUtils.clickOKOnAlertDialog();

        // ●verify(検証)
        String message = TestUtils.getLatestAlertDialogMessage();
        assertThat(message, is(RuntimeEnvironment.application.getString(R.string.cannot_find_property)));
    }

    /**
     * 空欄がある場合、それを示すダイアログを表示
     */
    @Test
    public void editPropertyInfoFailedForNotInput() {
        // ●setup(準備)
        // IDとPassを設定
        // LoginTaskで必ず成功が返るように設定
        mPropertyEditActivity.setApi(new WebApiMock());
        Button button = (Button) mPropertyEditActivity.findViewById(R.id.edit_button_edit);

        // ●execute(テストの実施)
        // ログインボタンを押下
        button.performClick();
        // ダイアログのOKボタンを押下
        TestUtils.clickOKOnAlertDialog();

        // ●verify(検証)
        String message = TestUtils.getLatestAlertDialogMessage();
        assertThat(message, is(RuntimeEnvironment.application.getString(R.string.not_input)));
    }

    /**
     * 引数に指定したパラメーターを設定します。
     * @param assetId
     * @param location
     * @param productName
     */
    private void registerPropertyInfo(String assetId, String location ,String productName) {

        TextView controlNumber = (TextView) mPropertyEditActivity.findViewById(R.id.edit_controlnumber);
        EditText propertyLocation = (EditText) mPropertyEditActivity.findViewById(R.id.property_edit_editText_location);
        EditText propertyProductName = (EditText) mPropertyEditActivity.findViewById(R.id.property_edit_editText_productname);

        controlNumber.setText(assetId);
        propertyLocation.setText(location);
        propertyProductName.setText(productName);

    }




}