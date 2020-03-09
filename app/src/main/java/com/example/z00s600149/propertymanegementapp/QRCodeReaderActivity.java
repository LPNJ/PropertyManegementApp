package com.example.z00s600149.propertymanegementapp;

// TODO import全部見直して、または必要ないimportは自動削除できるようにできるからそれを設定して
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import com.google.zxing.Result;
import me.dm7.barcodescanner.zxing.ZXingScannerView;

/**
 * アカウントを新規作成するためのActivity
 */
public class QRCodeReaderActivity extends AppCompatActivity implements ZXingScannerView.ResultHandler{

    // TODO 使ってないから消す
    private ZXingScannerView scannerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qrcode_reader);
    }

    @Override
    protected void onResume() {
        super.onResume();

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
        {
            if(scannerView == null)
            {
                scannerView = new ZXingScannerView(this);
                setContentView(scannerView);
            }
            scannerView.setResultHandler(this);
            scannerView.startCamera();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        scannerView.stopCamera();
    }

    @Override
    public void handleResult(Result result) {
        final String scanResult = result.getText();
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        // TODO stringsに追加
        builder.setTitle(R.string.scan_result);
        // TODO stringsに追加
        builder.setPositiveButton(R.string.back, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int i) {
                scannerView.resumeCameraPreview(QRCodeReaderActivity.this);
            }
        });
        // TODO stringsに追追加
        builder.setNeutralButton(R.string.view_property_info, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int i) {
                Intent intent = new Intent(QRCodeReaderActivity.this , PropertyReferenceActivity.class);
                intent.putExtra(IntentKey.NUMBER, scanResult);
                startActivity(intent);
            }
        });
        builder.setMessage(scanResult);
        AlertDialog alert = builder.create();
        alert.show();
    }
}
