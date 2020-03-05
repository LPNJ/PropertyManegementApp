package com.example.z00s600149.propertymanegementapp;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.google.zxing.Result;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class QRcodeReaderActivity extends AppCompatActivity implements ZXingScannerView.ResultHandler{

    private static final int REQUEST_CAMERA = 1;
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

    // TODO 必要ないメソッドは削除。他も同様


    @Override
    public void handleResult(Result result) {
        final String scanResult = result.getText();
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Scan Result");
        builder.setPositiveButton("戻る", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int i) {
                scannerView.resumeCameraPreview(QRcodeReaderActivity.this);
            }
        });
        builder.setNeutralButton("資産情報表示", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int i) {
                Intent intent = new Intent(QRcodeReaderActivity.this , PropertyReferenceActivity.class);
                intent.putExtra(IntentKey.NUMBER, scanResult);
                startActivity(intent);
            }
        });
        builder.setMessage(scanResult);
        AlertDialog alert = builder.create();
        alert.show();
    }
}
