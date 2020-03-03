package service;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.graphics.Bitmap;
import android.util.Log;
import android.widget.Toast;

import com.example.z00s600149.propertymanegementapp.IntentKey;
import com.example.z00s600149.propertymanegementapp.R;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.concurrent.ScheduledFuture;

import jp.co.ricoh.hmp.sdk.HmpService;
import jp.co.ricoh.hmp.sdk.image.HmpImage;
import jp.co.ricoh.hmp.sdk.image.HmpImageFactory;
import jp.co.ricoh.hmp.sdk.image.generator.Qrcode;
import jp.co.ricoh.hmp.sdk.printer.HmpAdapter;
import jp.co.ricoh.hmp.sdk.printer.HmpCommand;
import jp.co.ricoh.hmp.sdk.printer.HmpPrinter;
import jp.co.ricoh.hmp.sdk.printer.HmpSettings;

import static jp.co.ricoh.hmp.sdk.image.generator.Qrcode.Level.M;
import static jp.co.ricoh.hmp.sdk.image.generator.Qrcode.Ratio.NORMAL;

public class HandyPrintService implements PrintService {
    ArrayList<HmpImage> mImages = new ArrayList<>();

    // TODO タグ名おかしい
    private static final String TAG = "時間測定";

    private long mStartTime;

    private static final HandyPrintService INSTANCE = new HandyPrintService();

    public static HandyPrintService getInstance() {
        return INSTANCE;
    }

    /**
     * タグ
     */
    //private static final String TAG = PrinterManager.class.getSimpleName();

    /**
     * リクエストコード
     */
    static final int REQUEST_ENABLE_BT = 1;



    /**
     * プリンタアダプタ
     */
    HmpAdapter mAdapter;

    /**
     * 接続タスク
     */
    ScheduledFuture mConnectFuture = null;

    /**
     * 選択したプリンタデバイス
     */
    HmpPrinter mPrinter = null;

    /**
     * エラー状態
     */
    HmpCommand.DeviceStatus mError = HmpCommand.DeviceStatus.DISCONNECTED;

    String number;
    Activity activityHandyPrinter;

    @Override
    public void print(String propertyNumber , Activity activity) {

        number = propertyNumber;
        activityHandyPrinter = activity;

        HmpService.initialize(activity.getApplicationContext());
        if (mAdapter == null) {
            mAdapter = HmpAdapter.getDefaultAdapter();
            mAdapter.setListener(mAdapterListener);
        }

        enable(activity);
        startScan();
    }

    /***
     *有効化
     **/
    public void enable(Activity activity) {
        //Logger.i(TAG, "onResume()- info HMPSDK : APP->SDK:  HmpAdapter is enabled by isEnabled().");
        if (!mAdapter.isEnabled()) {
            Log.i(TAG, "Start Activity for Result");
            activity.startActivityForResult(new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE), REQUEST_ENABLE_BT);

        }
    }

    /**スキャンモード開始
     *
     */
    public synchronized void startScan() {

        /* デバイスをスキャン */
        //Logger.i(TAG,"startScan() - info : HMPSDK : APP->SDK: start discovery by startDiscovery().");
        Log.i(TAG,"startScan() - info : HMPSDK : APP->SDK: start discovery by startDiscovery().");
        mAdapter.startDiscovery();

    }
    /**
     *プリンタデバイスリスナ
     */
    final HmpPrinter.Listener mPrinterListener = (printer, event ) -> {
        //Logger.i(TAG, "mPrinterListener - info HMPSDK : SDK->APP:  event=" + event);
        Log.i(TAG, "mPrinterListener - info HMPSDK : SDK->APP:  event=" + event);
        switch (event) {
            case CONNECTION_CONNECTED:
                Event.post(HmpPrinter.Event.CONNECTION_CONNECTED);

                generateQRCode(number,activityHandyPrinter);
                Log.i(TAG,"after generateQRCode() time = " + (System.currentTimeMillis() - mStartTime));
                HmpSettings settings = new HmpSettings(HmpSettings.Position.CENTER, HmpSettings.Direction.RIGHT, HmpSettings.Pass.SINGLE, HmpSettings.Theta.ENABLE);
                mPrinter.print(mImages.get(0),settings,1);

                break;
            case CONNECTION_FAILED:
                Event.post(HmpPrinter.Event.CONNECTION_FAILED);
                break;
            case CONNECTION_DISCONNECTED:
                Event.post(HmpPrinter.Event.CONNECTION_DISCONNECTED);
                break;
            case STATUS_CHANGED:
                Event.post(HmpPrinter.Event.STATUS_CHANGED);
                break;
            case INFORMATION_CHANGED:
                Event.post(HmpPrinter.Event.INFORMATION_CHANGED);
                break;
            case JOB_STARTED:
                Event.post(HmpPrinter.Event.JOB_STARTED);
                break;
            case PRINTED_PASS:
                Event.post(HmpPrinter.Event.PRINTED_PASS);
                break;
            case PRINTED_PAGE:
                Event.post(HmpPrinter.Event.PRINTED_PAGE);
                break;
            case JOB_ENDED:
                Event.post(HmpPrinter.Event.JOB_ENDED);
                break;
            case JOB_CANCELED:
                Event.post(HmpPrinter.Event.JOB_CANCELED);
                break;
            case JOB_ABORTED:
                Event.post(HmpPrinter.Event.JOB_ABORTED);
                break;
            case UPDATE_STATUS:
                onUpdateStatus();
                Event.post(HmpPrinter.Event.UPDATE_STATUS);
                break;
            default:
                break;
        }
    };

    /**
     *プリンタアダプタリスナ
     */
    final HmpAdapter.Listener mAdapterListener = (hmpAdapter,  event) -> {
        Log.i(TAG,"onReceive time = " + (System.currentTimeMillis() - mStartTime));
        Log.i(TAG, "mAdapterListener - info HMPSDK : SDK->APP:  event=" + event);
        switch (event) {
            case FOUND:
                mPrinter = mAdapter.getDiscoveredPrinters().get(0);
                mPrinter.setListener(mPrinterListener);
                mPrinter.open();
                break;
            default:
                break;
        }
    };

    @Override
    public void terminate() {
        if (mPrinter != null) {
            /* デバイスをクローズ */
            //Logger.i(TAG,"startScan() - info : HMPSDK : APP->SDK: Close device by close().");
            Log.i(TAG, "close()- info HMPSDK : APP->SDK:  Clear Listener by clearListener().");
            mPrinter.clearListener();
            Log.i(TAG,"startScan() - info : HMPSDK : APP->SDK: Close device by close().");
//            mPrinter.close();
            Log.i(TAG,"BBBBB");
        }
        // TODO if分の中に入れる、ここでcloseしたら上でnullチェックしている意味がない
        mPrinter.close();
        Log.i(TAG,"onDestroy");
        mAdapter.cancelDiscovery();
    }



    /////////////////////////////

    /**
     * イベント
     */
    public enum Event {

        /**
         * デバイス発見
         */
        DEVICE_FOUND,

        /**
         * 検索終了
         */
        DISCOVERY_FINISH,

        /**
         * 接続
         */
        CONNECTION_CONNECTED,

        /**
         * 切断
         */
        CONNECTION_DISCONNECTED,

        /**
         * 接続失敗
         */
        CONNECTION_FAILED,

        /**
         * ページ印刷
         */
        PRINTED_PAGE,

        /**
         * パス印刷
         */
        PRINTED_PASS,

        /**
         * ジョブ開始
         */
        JOB_STARTED,

        /**
         * ジョブ終了
         */
        JOB_ENDED,

        /**
         * ジョブキャンセル
         */
        JOB_CANCELED,

        /**
         * ジョブ中断
         */
        JOB_ABORTED,

        /**
         * プリンタ情報更新
         */
        INFORMATION_CHANGED,

        /**
         * プリンタ状態更新
         */
        STATUS_CHANGED,

        /**
         * 印字状態通知
         */
        UPDATE_STATUS;

        /**
         * イベント送信
         *
         * @param event イベント
         */
        static void post(Event event) {
            EventBus.getDefault().post(event);
        }

        public static void post(HmpPrinter.Event connectionFailed) {
        }
    }

    /////////////////////////////


    // TODO *がおかしい正しいjavadocの書き方は以下の通りなので修正する
    // そもそもこのメソッド使ってないので消す
    /**
     * メソッドの説明
     * @param printer 引数の説明
     */
    /** * 接続 * *
     @param printer プリンタデバイス
     */
    public synchronized void open(HmpPrinter printer) {
        if (printer == null) {
            Event.post(HmpPrinter.Event.CONNECTION_FAILED);
            return;
        }

        if (mPrinter != null) {
            /* リスナをクリア */
            //Logger.i(TAG, "open()- info HMPSDK : APP->SDK:  Clear listener by clearListener().");
            Log.i(TAG, "open()- info HMPSDK : APP->SDK:  Clear listener by clearListener().");
            mPrinter.clearListener();
            /* リスナをクリア */
            //Logger.i(TAG, "open()- info HMPSDK : APP->SDK:  Close device by close().");
            Log.i(TAG, "open()- info HMPSDK : APP->SDK:  Close device by close().");
            mPrinter.close();
        }
        mPrinter = printer;
        /* リスナをレジスト */
        //Logger.i(TAG, "open()- info HMPSDK : APP->SDK:  Register Listener to listen to printer by setListener().");
        Log.i(TAG, "open()- info HMPSDK : APP->SDK:  Register Listener to listen to printer by setListener().");
        mPrinter.setListener(mPrinterListener);
        /* プリントを接続 */
        //Logger.i(TAG, "open()- info HMPSDK : APP->SDK:  Connect Printer by open().");
        Log.i(TAG, "open()- info HMPSDK : APP->SDK:  Connect Printer by open().");
        mPrinter.open();
    }

    /////////////////////////////

    /**
     * プリント状態通知
     */
    void onUpdateStatus() {
        if (mPrinter != null) {
            //Logger.i(TAG, "onUpdateStatus - info HMPSDK : APP->SDK:  Get device status by getDeviceStatus().");
            Log.i(TAG, "onUpdateStatus - info HMPSDK : APP->SDK:  Get device status by getDeviceStatus().");
            mError = mPrinter.getDeviceStatus();
            switch (mError) {
                case FRONT_COVER:
                case CARTRIDGE_OFF:
                case BATTERY_END:
                case BLACK_END:
                case CARTRIDGE_FAILURE1:
                case CARTRIDGE_FAILURE2:
                case ERR100:
                case ERR101:
                case ERR200:
                case ERR201:
                case ERR202:
                case ERR203:
                    /* 印刷取消 */
                    Log.i(TAG, "cancel()- info HMPSDK : APP->SDK: Cancel print by cancel().");
                    mPrinter.cancel();
                default:
                    break;
            }
        } else {
            mError = HmpCommand.DeviceStatus.DISCONNECTED;
        }
    }

    ////////////////////////////

    /**
     印刷 *
     @param images 印刷イメージ
     @param settings 設定
     @param copies  部数
     @return 実行可否
     */
    public synchronized boolean print(ArrayList<HmpImage> images, HmpSettings settings, int copies) {
        if (mPrinter == null || images == null || settings == null || copies <= 0 || copies > 999) {
            //Logger.e(TAG, "print() - error : parameter is invalid");
            Log.e(TAG, "print() - error : parameter is invalid");
            return false;
        }
        /* 印刷 */
        //Logger.i(TAG, "print()- info HMPSDK : APP->SDK: Print data by print().");
        Log.i(TAG, "print()- info HMPSDK : APP->SDK: Print data by print().");
        return mPrinter.print(images, settings, copies);
    }


    //////////////////////////////////////////////////
    //////////////////////////////////////////////////

    /**
     * タグ
     */
    //private static final String TAG2 = QRCodeFragment.class.getSimpleName();

    /**
     * プリンタデバイス管理
     */
    //final PrinterManager mPrinterManager = PrinterManager.getInstance();

    /**
     * 誤り訂正レベル
     */
    Qrcode.Level mLevel = M;

    /**
     * セルサイズ
     */
    Qrcode.Ratio mRatio = NORMAL;

    /**
     * 部数
     */
    int mCopies = 1;

    Bitmap mBitmap = null;



    /**
     QRコードを生成
     */
    void generateQRCode(String propertyNumber,Activity activity) {
        Log.i(TAG,"generateQRCode() time = " + (System.currentTimeMillis() - mStartTime));
        mImages.clear();
        mBitmap = null;
//        if (mErrorCorrection.getText().toString().equals("") || Integer.valueOf(mErrorCorrection.getText().toString()) > 3 || Integer.valueOf(mErrorCorrection.getText().toString()) < 0) {
//            Toast.makeText(getApplicationContext(),getResources().getString(R.string.message_value_invalid),Toast.LENGTH_SHORT).show();
//            return;
//        }
//
//        if (mCellSize.getText().toString().equals("") || Integer.valueOf(mCellSize.getText().toString()) > 2 || Integer.valueOf(mCellSize.getText().toString()) < 0) {
//            Toast.makeText(getApplicationContext(),getResources().getString(R.string.message_value_invalid),Toast.LENGTH_SHORT).show();
//            return;
//        }
//
//        if (mBodyEdit.getText().toString().equals("")) {
//            Toast.makeText(getApplicationContext(),getResources().getString(R.string.qrcode_body_empty),Toast.LENGTH_SHORT).show();
//            return;
//        }

        /* qrコード生成 */
        Log.i(TAG, "generateQRCode()- info HMPSDK : APP->SDK: Generate qrcode by generateQrcode().");

        HmpImage image = HmpImageFactory.generateQrcode(propertyNumber, mLevel, mRatio, null);
        if ((image.getHeight() == 0) || (image.getWidth() == 0))
        {
            //Toast.makeText(getApplicationContext(),getResources().getString(R.string.message_unvalid),Toast.LENGTH_SHORT).show();
            Toast.makeText(activity.getApplicationContext(),activity.getResources().getString(R.string.app_name),Toast.LENGTH_SHORT).show();
            return;
        }

        mImages.add(image);
        //Logger.i(TAG, "generateQRCode()- info HMPSDK : APP->SDK: Get qrcode bitmap by getBitmap().");
        Log.i(TAG, "generateQRCode()- info HMPSDK : APP->SDK: Get qrcode bitmap by getBitmap().");
        mBitmap = image.getBitmap();
        //mBodyView.setImageBitmap(mBitmap);
    }
}
