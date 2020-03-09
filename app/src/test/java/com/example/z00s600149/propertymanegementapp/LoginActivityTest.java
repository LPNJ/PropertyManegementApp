package com.example.z00s600149.propertymanegementapp;

import android.widget.Button;
import android.widget.EditText;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.robolectric.shadows.ShadowLog;

import entity.UserInfo;
import webApi.WebApi;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

public class LoginActivityTest {

    private LoginActivity mLoginActivity;
    private WebApi mWebApi;
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
        // LoginTaskをモックする
        mWebApi = Mockito.mock(WebApi.class);
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
        enterIdAndPass("aaaaaaaaaaaaaaaaa", "bbbbbbbbbbbbb");
        // LoginTaskで必ず成功が返るように設定
        WebApi task = createTask( 0);
        mMainActivity.setTask(task);
        Button loginButton = mMainActivity.findViewById(R.id.login);

        // ●execute(テストの実施)
        // ログインボタンを押下
        loginButton.performClick();

        // ●verify(検証)
        String nextActivityName = TestUtils.getNextActivityName(mMainActivity);
        assertThat(nextActivityName, is(MenuActivity.class.getName()));
    }

    /**
     * サーバーに問い合わせてログイン失敗してダイアログを表示するかの確認
     */
    @Test
    public void loginFailedByDifferent() {
        // ●setup(準備)
        // IDとPassを設定
        enterIdAndPass("aaaaaaaaaaaaaaaaa", "bbbbbbbbbbbbb");
        // LoginTaskで必ず失敗が返るように設定
        LoginTask task = createTask( 1);
        mMainActivity.setTask(task);
        Button loginButton = mMainActivity.findViewById(R.id.login);

        // ●execute(テストの実施)
        // ログインボタンを押下
        loginButton.performClick();

        // ●verify(検証)
        String message = TestUtils.getLatestAlertDialogMessage();
        assertThat(message, is(RuntimeEnvironment.application.getString(R.string.cannot_login)));
    }

    /**
     * パスワードが空でログイン失敗しでメニュー画面に遷移するかの確認
     */
    @Test
    public void loginFailedByEnptytPassword() {
        // ●setup(準備)
        // IDとPassを設定
        enterIdAndPass("aaaaaaaaaaaaaaaaa", "");
        // LoginTaskで必ず失敗が返るように設定
        LoginTask task = createTask( 1);
        mMainActivity.setTask(task);
        Button loginButton = mMainActivity.findViewById(R.id.login);

        // ●execute(テストの実施)
        // ログインボタンを押下
        loginButton.performClick();

        // ●verify(検証)
        String message = TestUtils.getLatestAlertDialogMessage();
        assertThat(message, is(RuntimeEnvironment.application.getString(R.string.login_error)));
    }

    /**
     * ログインタスクの結果を設定します。
     * @param result
     * @return
     */
    private LoginTask createTask(final int result) {
        return new LoginTask() {
            @Override
            public void execute(UserInfo userInfo, ResultListener listener) {
                listener.onResult(result);
            }
        };
    }

    /**
     * 引数に指定したId, Passを設定します。
     * @param id
     * @param pass
     */
    private void enterIdAndPass(String id, String pass) {
        EditText idText = mMainActivity.findViewById(R.id.editText_ID);
        EditText passText = mMainActivity.findViewById(R.id.editText_PASS);
        idText.setText(id);
        passText.setText(pass);
    }

}