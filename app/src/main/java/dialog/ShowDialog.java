// TODO 他と同様パッケージは小文字で始める
package dialog;

import android.app.Activity;
import android.app.AlertDialog;

import com.example.z00s600149.propertymanegementapp.R;

// TODO 削除する
public class ShowDialog {

    // TODO final
    private final Activity mActivity;

    public ShowDialog(Activity activity){
        this.mActivity = activity;
    }

    /*エラーメッセージ表示*/
    public void show(int msg) {
        new AlertDialog.Builder(mActivity)
                .setMessage(msg)
                .setPositiveButton(R.string.ok, null)
                .create()
                .show();
    }
}
