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
public class ControlNumberIssuedActivityTest {

    private ControlNumberIssuedActivity mControlNumberIssuedActivity;
    /**
     * @Before がついたメソッドは、すべてのテストメソッド(@Testがついたメソッド)の
     * 実施前に呼び出される。
     */
    @Before
    public void setup() {
        // android.utils.Log を使ったログを出力する
        ShadowLog.stream = System.out;
        // Activityを生成
        mControlNumberIssuedActivity = TestUtils.createActivity(ControlNumberIssuedActivity.class, null);
    }

    /**
     * @After がついたメソッドは、すべてのテストメソッド(@Testがついたメソッド)の
     * 実施後に呼び出される。
     */
    @After
    public void teardown() {
    }

    /**
     * ハンディプリンタに印刷指示を送るためのActivityに移動するためのテスト
     */
    @Test
    public void goToThePrinterActivitySuccess() {
        // ●setup(準備)
        Button button = (Button) mControlNumberIssuedActivity.findViewById(R.id.control_button_tomenu);

        // ●execute(テストの実施)
        // PrinterActivityに遷移させるボタンを押下
        button.performClick();

        // ●verify(検証)
        String nextActivityName = TestUtils.getNextActivityName(mControlNumberIssuedActivity);
        assertThat(nextActivityName, is(PrinterActivity.class.getName()));
    }

}