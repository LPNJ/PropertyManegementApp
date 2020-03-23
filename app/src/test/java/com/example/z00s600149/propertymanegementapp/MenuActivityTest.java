package com.example.z00s600149.propertymanegementapp;

import android.os.Build;
import android.widget.Button;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;
import org.robolectric.shadows.ShadowLog;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class, sdk = Build.VERSION_CODES.O, application = TestApplication.class)
public class MenuActivityTest {

    private MenuActivity mMenuActivity;
    /**
     * @Before がついたメソッドは、すべてのテストメソッド(@Testがついたメソッド)の
     * 実施前に呼び出される。
     */
    @Before
    public void setup() {
        // android.utils.Log を使ったログを出力する
        ShadowLog.stream = System.out;
        // Activityを生成
        mMenuActivity = TestUtils.createActivity(MenuActivity.class, null);
    }

    /**
     * @After がついたメソッドは、すべてのテストメソッド(@Testがついたメソッド)の
     * 実施後に呼び出される。
     */
    @After
    public void teardown() {
    }

    /**
     * 資産情報登録画面に遷移することが成功する場合のテスト
     */
    @Test
    public void goToTheRegistrationActivitySuccess() {
        // ●setup(準備)
        Button button = (Button) mMenuActivity.findViewById(R.id.menu_button_propertyregist);

        // ●execute(テストの実施)
        // 資産情報登録画面に遷移させるボタンを押下
        button.performClick();

        // ●verify(検証)
        String nextActivityName = TestUtils.getNextActivityName(mMenuActivity);
        assertThat(nextActivityName, is(PropertyInfoActivity.class.getName()));
    }

    /**
     * 資産情報選択画面に遷移することが成功する場合のテスト
     */
    @Test
    public void goToThePropertySelectActivity() {
        // ●setup(準備)
        Button button = (Button) mMenuActivity.findViewById(R.id.menu_button_propertyinfo);

        // ●execute(テストの実施)
        // 資産情報選択画面に遷移させるボタンを押下
        button.performClick();

        // ●verify(検証)
        String nextActivityName = TestUtils.getNextActivityName(mMenuActivity);
        assertThat(nextActivityName, is(PropertySelectActivity.class.getName()));
    }

    /**
     * QRコード読み込み画面に遷移することが成功する場合のテスト
     */
    @Test
    public void goToTheQRCodeReaderActivity() {
        // ●setup(準備)
        Button button = (Button) mMenuActivity.findViewById(R.id.menu_button_qrcodereader);

        // ●execute(テストの実施)
        // QRコード読み込み画面に遷移させるボタンを押下
        button.performClick();

        // ●verify(検証)
        String nextActivityName = TestUtils.getNextActivityName(mMenuActivity);
        assertThat(nextActivityName, is(QRCodeReaderActivity.class.getName()));
    }

    /**
     * ログアウトが成功し、ログイン画面に遷移するかのテスト
     */
    @Test
    public void logoutSuccess() {
        // ●setup(準備)
        Button button = (Button) mMenuActivity.findViewById(R.id.menu_button_logout);

        // ●execute(テストの実施)
        // ログアウトボタンを押下
        button.performClick();
        // 表示されるダイアログのOKボタンを押下
        TestUtils.clickOKOnAlertDialog();

        // ●verify(検証)
        String nextActivityName = TestUtils.getNextActivityName(mMenuActivity);
        assertThat(nextActivityName, is(LoginActivity.class.getName()));
    }

}