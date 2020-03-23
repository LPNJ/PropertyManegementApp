package com.example.z00s600149.propertymanegementapp;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import org.robolectric.Robolectric;
import org.robolectric.Shadows;
import org.robolectric.shadows.ShadowActivity;
import org.robolectric.shadows.ShadowAlertDialog;
import org.robolectric.shadows.ShadowIntent;

public class TestUtils {

    /**
     * Activityを生成します。
     * Activity生成にIntentが必要な場合はIntentを指定してください。
     * 必要ない場合はnullを設定してください。
     * @param clazz
     * @param intent
     * @param <T>
     * @return
     */
    static <T extends Activity> T createActivity(Class<T> clazz, Intent intent) {
        if (intent == null) {
            return Robolectric.setupActivity(clazz);
        }
        return Robolectric.buildActivity(clazz, intent).setup().get();
    }

    /**
     * 引数のActivityで画面遷移を実施した後に、このメソッドを実行すると、
     * 遷移後の画面のクラス名を取得できます。
     * @param t
     * @param <T>
     * @return
     */
    static <T extends Activity> String getNextActivityName(T t) {
        ShadowActivity shadowActivity = Shadows.shadowOf(t);
        Intent intent = shadowActivity.peekNextStartedActivity();
        ShadowIntent shadowIntent = Shadows.shadowOf(intent);
        return shadowIntent.getIntentClass().getName();
    }

    /**
     * 最後に表示したダイアログのメッセージを取得します。
     * @return
     */
    static String getLatestAlertDialogMessage() {
        AlertDialog dialog = ShadowAlertDialog.getLatestAlertDialog();
        ShadowAlertDialog shadowDialog = Shadows.shadowOf(dialog);
        return shadowDialog.getMessage().toString();
    }

}
