package dialog;

import android.app.Activity;
import android.app.AlertDialog;

import com.example.z00s600149.propertymanegementapp.R;

public class ShowDialog {

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
