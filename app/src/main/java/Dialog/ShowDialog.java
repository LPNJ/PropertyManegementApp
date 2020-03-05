package Dialog;

import android.app.Activity;
import android.app.AlertDialog;

import com.example.z00s600149.propertymanegementapp.NewAccountActivity;

public class ShowDialog {

    private Activity mActivity;

    public ShowDialog(Activity activity){
        this.mActivity = activity;
    }

    /*エラーメッセージ表示*/
    // TODO ほかのところにも同じ処理があるので共通化する
    // ダイアログのutilsクラスみたいなんを作るとよい
    public void show(int msg) {
        new AlertDialog.Builder(mActivity)
                .setMessage(msg)
                .setPositiveButton("OK", null)
                .create()
                .show();
    }
}
