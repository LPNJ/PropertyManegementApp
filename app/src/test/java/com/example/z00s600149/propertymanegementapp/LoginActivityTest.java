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

import webApi.WebApiMock;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class, sdk = Build.VERSION_CODES.O, application = TestApplication.class)
public class LoginActivityTest {

    private LoginActivity mLoginActivity;
    /**
     * @Before がついたメソッドは、すべてのテストメソッド(@Testがついたメソッド)の
     * 実施前に呼び出される。
     */
    @Before
    public void setup() {
        // android.utils.Log を使ったログを出力する
        ShadowLog.stream = System.out;
        // Activityを生成
        mLoginActivity = TestUtils.createActivity(LoginActivity.class, null);
    }

    /**
     * @After がついたメソッドは、すべてのテストメソッド(@Testがついたメソッド)の
     * 実施後に呼び出される。
     */
    @After
    public void teardown() {
    }

    /**
     * ログイン成功でメニュー画面に遷移するかの確認
     */
    @Test
    public void loginSuccess() {
        // ●setup(準備)
        // IDとPassを設定
        enterIdAndPass("komiyama", "komiyama");
        // LoginTaskで必ず成功が返るように設定
        mLoginActivity.setApi(new WebApiMock());
        Button loginButton = (Button) mLoginActivity.findViewById(R.id.login_button_login);

        // ●execute(テストの実施)
        // ログインボタンを押下
        loginButton.performClick();

        // ●verify(検証)
        String nextActivityName = TestUtils.getNextActivityName(mLoginActivity);
        assertThat(nextActivityName, is(MenuActivity.class.getName()));
    }

    /**
     * サーバーに問い合わせてログイン失敗してダイアログを表示するかの確認
     */
    @Test
    public void loginFailedByDifferent() {
        // ●setup(準備)
        // IDとPassを設定
        enterIdAndPass("failure", "aaaaaaaa");
        // LoginTaskで必ず失敗が返るように設定
        Button loginButton = (Button) mLoginActivity.findViewById(R.id.login_button_login);

        // ●execute(テストの実施)
        // ログインボタンを押下
        loginButton.performClick();

        // ●verify(検証)
        String message = TestUtils.getLatestAlertDialogMessage();
        assertThat(message, is(RuntimeEnvironment.application.getString(R.string.cannot_connect)));
    }

    /**
     * パスワードが空でログイン失敗しでメニュー画面に遷移するかの確認
     */
//    @Test
//    public void loginFailedByEnptytPassword() {
//        // ●setup(準備)
//        // IDとPassを設定
//        enterIdAndPass("aaaaaaaaaaaaaaaaa", "");
//        // LoginTaskで必ず失敗が返るように設定
//        LoginTask task = createTask( 1);
//        mMainActivity.setTask(task);
//        Button loginButton = mMainActivity.findViewById(R.id.login);
//
//        // ●execute(テストの実施)
//        // ログインボタンを押下
//        loginButton.performClick();
//
//        // ●verify(検証)
//        String message = TestUtils.getLatestAlertDialogMessage();
//        assertThat(message, is(RuntimeEnvironment.application.getString(R.string.login_error)));
//    }

    /**
     * 引数に指定したId, Passを設定します。
     * @param id
     * @param pass
     */
    private void enterIdAndPass(String id, String pass) {
        EditText idText = (EditText) mLoginActivity.findViewById(R.id.login_id);
        EditText passText = (EditText) mLoginActivity.findViewById(R.id.login_passward);
        idText.setText(id);
        passText.setText(pass);
    }

}