// TODO 他と同様パッケージは小文字で始める
package Dialog;

import android.app.Activity;
import android.app.AlertDialog;

// TODO 削除する
import com.example.z00s600149.propertymanegementapp.NewAccountActivity;

public class ShowDialog {

    // TODO final
    private Activity mActivity;

    public ShowDialog(Activity activity){
        this.mActivity = activity;
    }

    /*エラーメッセージ表示*/
    public void show(int msg) {
        new AlertDialog.Builder(mActivity)
                .setMessage(msg)
                .setPositiveButton("OK", null)
                .create()
                .show();
    }
}
