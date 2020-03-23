package com.example.z00s600149.propertymanegementapp;

import android.os.Build;
import android.widget.Button;

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
public class PropertyReferenceActivityTest {

    private PropertyReferenceActivity mPropertyReferenceActivity;
    /**
     * @Before がついたメソッドは、すべてのテストメソッド(@Testがついたメソッド)の
     * 実施前に呼び出される。
     */
    @Before
    public void setup() {
        // android.utils.Log を使ったログを出力する
        ShadowLog.stream = System.out;
        // Activityを生成
        mPropertyReferenceActivity = TestUtils.createActivityWithoutOnCreate(PropertyReferenceActivity.class);
    }

    /**
     * @After がついたメソッドは、すべてのテストメソッド(@Testがついたメソッド)の
     * 実施後に呼び出される。
     */
    @After
    public void teardown() {
    }

    /**
     * 資産情報削除が成功し、Menu画面に遷移するかの確認
     */
    @Test
    public void deletePropertySuccess() {
        // ●setup(準備)
        // DeletePropertyTaskで必ず成功が返るように設定

        //ユーザー名を固定
        LoginUserNameHolder name = LoginUserNameHolder.getInstance();
        name.setName("komiyama");

        mPropertyReferenceActivity.setApi(new WebApiMock());

        mPropertyReferenceActivity.onCreate(null);

        Button button = (Button) mPropertyReferenceActivity.findViewById(R.id.reference_delete);

        // ●execute(テストの実施)
        //削除ボタンを押下
        button.performClick();
        //ダイアログのOKボタンを押下
        TestUtils.clickOKOnAlertDialog();

        // ●verify(検証)
        String message = TestUtils.getLatestAlertDialogMessage();
        assertThat(message, is(RuntimeEnvironment.application.getString(R.string.delete_complete)));
    }

    /**
     * サーバーの通信に失敗し、ダイアログを表示
     */
    @Test
    public void deletePropertyFailedByError() {
        // ●setup(準備)
        // DeletePropertyTaskで必ず成功が返るように設定
        //ユーザー名を固定
        LoginUserNameHolder name = LoginUserNameHolder.getInstance();
        name.setName("");

        mPropertyReferenceActivity.setApi(new WebApiMock());

        mPropertyReferenceActivity.onCreate(null);

        Button button = (Button) mPropertyReferenceActivity.findViewById(R.id.reference_delete);

        // ●execute(テストの実施)
        //削除ボタンを押下
        button.performClick();
        //ダイアログのOKボタンを押下
        TestUtils.clickOKOnAlertDialog();

        // ●verify(検証)
        String message = TestUtils.getLatestAlertDialogMessage();
        assertThat(message, is(RuntimeEnvironment.application.getString(R.string.cannot_connect)));
    }

    /**
     * 不正なパラメーターがあったとき、ダイアログを表示
     */
    @Test
    public void deletePropertyFailedByNotPermitCharacter() {
        // ●setup(準備)
        // DeletePropertyTaskで必ず成功が返るように設定
        LoginUserNameHolder name = LoginUserNameHolder.getInstance();
        name.setName("failure");

        mPropertyReferenceActivity.setApi(new WebApiMock());

        mPropertyReferenceActivity.onCreate(null);

        Button button = (Button) mPropertyReferenceActivity.findViewById(R.id.reference_delete);

        // ●execute(テストの実施)
        //削除ボタンを押下
        button.performClick();
        //ダイアログのOKボタンを押下
        TestUtils.clickOKOnAlertDialog();

        // ●verify(検証)
        String message = TestUtils.getLatestAlertDialogMessage();
        assertThat(message, is(RuntimeEnvironment.application.getString(R.string.not_permit_character)));
    }

    /**
     * 指定したユーザーが見つからなかったとき、ダイアログを表示
     */
    @Test
    public void deletePropertyFailedByNotFoundUser() {
        // ●setup(準備)
        // DeletePropertyTaskで必ず成功が返るように設定
        LoginUserNameHolder name = LoginUserNameHolder.getInstance();
        name.setName("nakayama");

        mPropertyReferenceActivity.setApi(new WebApiMock());

        mPropertyReferenceActivity.onCreate(null);

        Button button = (Button) mPropertyReferenceActivity.findViewById(R.id.reference_delete);

        // ●execute(テストの実施)
        //削除ボタンを押下
        button.performClick();
        //ダイアログのOKボタンを押下
        TestUtils.clickOKOnAlertDialog();

        // ●verify(検証)
        String message = TestUtils.getLatestAlertDialogMessage();
        assertThat(message, is(RuntimeEnvironment.application.getString(R.string.cannot_find_user)));
    }

    /**
     * 資産管理者ではないユーザーが資産情報を削除したとき、ダイアログを表示
     */
    @Test
    public void deletePropertyFailedForNotManager() {
        // ●setup(準備)
        // DeletePropertyTaskで必ず成功が返るように設定
        LoginUserNameHolder name = LoginUserNameHolder.getInstance();
        name.setName("katayama");

        mPropertyReferenceActivity.setApi(new WebApiMock());

        mPropertyReferenceActivity.onCreate(null);

        Button button = (Button) mPropertyReferenceActivity.findViewById(R.id.reference_delete);

        // ●execute(テストの実施)
        //削除ボタンを押下
        button.performClick();
        //ダイアログのOKボタンを押下
        TestUtils.clickOKOnAlertDialog();

        // ●verify(検証)
        String message = TestUtils.getLatestAlertDialogMessage();
        assertThat(message, is(RuntimeEnvironment.application.getString(R.string.not_management)));
    }


    /**
     * 指定した資産が見つからないとき、ダイアログを表示
     */
    @Test
    public void deletePropertyFailedForNotFoundProperty() {
        // ●setup(準備)
        // IDとPassを設定
        // DeletePropertyTaskで必ず成功が返るように設定
        //ユーザー名を固定
        LoginUserNameHolder name = LoginUserNameHolder.getInstance();
        name.setName("notFound");

        mPropertyReferenceActivity.setApi(new WebApiMock());

        mPropertyReferenceActivity.onCreate(null);

        Button button = (Button) mPropertyReferenceActivity.findViewById(R.id.reference_delete);

        // ●execute(テストの実施)
        //削除ボタンを押下
        button.performClick();
        //ダイアログのOKボタンを押下
        TestUtils.clickOKOnAlertDialog();

        // ●verify(検証)
        String message = TestUtils.getLatestAlertDialogMessage();
        assertThat(message, is(RuntimeEnvironment.application.getString(R.string.cannot_find_property)));
    }

    /**
     * 資産情報編集画面に遷移する
     */
    @Test
    public void goToThePropertyEditActivity() {
        // ●setup(準備)
        // IDとPassを設定

        mPropertyReferenceActivity.setApi(new WebApiMock());

        mPropertyReferenceActivity.onCreate(null);

        Button button = (Button) mPropertyReferenceActivity.findViewById(R.id.reference_edit);

        // ●execute(テストの実施)
        //資産情報編集画面に遷移させるためのボタンを押下
        button.performClick();

        // ●verify(検証)
        String nextActivityName = TestUtils.getNextActivityName(mPropertyReferenceActivity);
        assertThat(nextActivityName, is(PropertyEditActivity.class.getName()));
    }

    /**
     * PrinterActivityに遷移させる
     */
    @Test
    public void goToThePrinterActivity() {
        // ●setup(準備)

        mPropertyReferenceActivity.setApi(new WebApiMock());

        mPropertyReferenceActivity.onCreate(null);

        Button button = (Button) mPropertyReferenceActivity.findViewById(R.id.reference_print);

        // ●execute(テストの実施)
        //PrinterActivityに遷移させるためのボタンを押下
        button.performClick();

        // ●verify(検証)
        String nextActivityName = TestUtils.getNextActivityName(mPropertyReferenceActivity);
        assertThat(nextActivityName, is(PrinterActivity.class.getName()));
    }


}