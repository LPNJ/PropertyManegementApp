package com.example.z00s600149.propertymanegementapp;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import jp.co.ricoh.hmp.sdk.printer.HmpSettings;

import static android.content.Context.MODE_PRIVATE;

// TODO 必要ないクラスは削除する
public class PreferenceManager {

    /**
     * タグ
     */
    private static final String TAG = PreferenceManager.class.getSimpleName();

    /**
     * インスタンス
     */
    private static PreferenceManager sInstance = null;

    /**
     * プリファレンス
     */
    private final SharedPreferences mSharedPreferences;

    /**
     * プリファレンス名
     */
    private static final String SHARED_PREFERENCES_NAME = "preference";

    /**
     * 初期化
     *
     * @param context コンテキスト
     */
    public static synchronized void initialize(Context context) {
        if (sInstance == null) {
            sInstance = new PreferenceManager(context);
        }
    }

    /**
     * 終了
     */
    public static synchronized void terminate() {
        if (sInstance != null) {
            sInstance.onTerminate();
            sInstance = null;
        }
    }

    /**
     * インスタンス取得
     *
     * @return インスタンス
     */
    public static synchronized PreferenceManager getInstance() {
        return sInstance;
    }

    /**
     * コンストラクタ
     *
     * @param context コンテキスト
     */
    private PreferenceManager(Context context) {
        mSharedPreferences = context.getSharedPreferences(SHARED_PREFERENCES_NAME, MODE_PRIVATE);
    }

    /**
     * デストラクタ
     */
    private void onTerminate() {
    }

    /**
     * 印刷開始位置取得
     *
     * @return 印刷開始位置
     */
    public HmpSettings.Position getPosition() {
        if (mSharedPreferences.getInt(Key.PRINT_START_POSITION, 0) == 0) {
            return HmpSettings.Position.RIGHT;
        } else {
            return HmpSettings.Position.CENTER;
        }
    }

    /**
     * 印刷開始位置セット
     *
     * @param position 印刷開始位置
     */
    public void setPosition(HmpSettings.Position position) {
        if (!mSharedPreferences.edit().putInt(Key.PRINT_START_POSITION, position.ordinal()).commit()) {
            //Logger.e(TAG,"setPosition() - error : failed commit shared preferences.");
            Log.e(TAG,"setPosition() - error : failed commit shared preferences.");
        }
    }

    /**
     * 最後に切断したデバイスID
     *
     * @return 最後に切断したデバイスID
     */
    public String getLatestDeviceId() {
        return mSharedPreferences.getString(Key.LATEST_DEVICE, "");
    }

    /**
     * 最後に切断したデバイスID
     *
     * @param id 最後に切断したデバイスID
     */
    public void setLatestDeviceId(String id) {
        if (!mSharedPreferences.edit().putString(Key.LATEST_DEVICE, id).commit()) {
            //Logger.e(TAG, "setLatestDeviceId() - error : failed commit shared preferences.");
            Log.e(TAG, "setLatestDeviceId() - error : failed commit shared preferences.");
        }
    }

    /**
     * キー
     */
    static class Key {

        /**
         * 印刷開始位置
         */
        static final String PRINT_START_POSITION = "print_start_position";

        /**
         * 最後のデバイス
         */
        static final String LATEST_DEVICE = "latest_device";
    }

}
