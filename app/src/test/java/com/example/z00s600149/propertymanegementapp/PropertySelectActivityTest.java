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
public class PropertySelectActivityTest {

    private PropertySelectActivity mPropertySelectActivity;
    /**
     * @Before がついたメソッドは、すべてのテストメソッド(@Testがついたメソッド)の
     * 実施前に呼び出される。
     */
    @Before
    public void setup() {
        // android.utils.Log を使ったログを出力する
        ShadowLog.stream = System.out;
        // Activityを生成
        mPropertySelectActivity = TestUtils.createActivity(PropertySelectActivity.class, null);
    }

    /**
     * @After がついたメソッドは、すべてのテストメソッド(@Testがついたメソッド)の
     * 実施後に呼び出される。
     */
    @After
    public void teardown() {
    }

    /**
     * 検索画面（資産管理者）に遷移
     */
    @Test
    public void goToThePropertySelectManagerActivitySuccess() {
        // ●setup(準備)
        Button button = (Button) mPropertySelectActivity.findViewById(R.id.select_manager);

        // ●execute(テストの実施)
        // 検索画面に遷移させるためのボタンを押下
        button.performClick();

        // ●verify(検証)
        String nextActivityName = TestUtils.getNextActivityName(mPropertySelectActivity);
        assertThat(nextActivityName, is(PropertySelectManagerActivity.class.getName()));
    }

    /**
     * 検索画面（資産管理者）に遷移
     */
    @Test
    public void goToThePropertySelectUserActivitySuccess() {
        // ●setup(準備)
        Button button = (Button) mPropertySelectActivity.findViewById(R.id.select_user);

        // ●execute(テストの実施)
        // 検索画面に遷移させるためのボタンを押下
        button.performClick();

        // ●verify(検証)
        String nextActivityName = TestUtils.getNextActivityName(mPropertySelectActivity);
        assertThat(nextActivityName, is(PropertySelectUserActivity.class.getName()));
    }

    /**
     * 検索画面（資産管理者）に遷移
     */
    @Test
    public void goToThePropertySelectAllActivitySuccess() {
        // ●setup(準備)
        Button button = (Button) mPropertySelectActivity.findViewById(R.id.select_all_property);

        // ●execute(テストの実施)
        // 検索画面に遷移させるためのボタンを押下
        button.performClick();

        // ●verify(検証)
        String nextActivityName = TestUtils.getNextActivityName(mPropertySelectActivity);
        assertThat(nextActivityName, is(PropertySelectAllActivity.class.getName()));
    }

}