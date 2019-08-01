package com.example.minimenu;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.google.zxing.integration.android.IntentIntegrator;
import com.journeyapps.barcodescanner.CaptureManager;
import com.journeyapps.barcodescanner.DecoratedBarcodeView;

public class MainActivity extends AppCompatActivity {

    private IntentIntegrator qrScan;

//    private CaptureManager manager;
//
//    private DecoratedBarcodeView barcodeView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        barcodeView = findViewById(R.id.db_qr);
//
//        manager = new CaptureManager(this,barcodeView);
//        manager.initializeFromIntent(getIntent(),savedInstanceState);
//        manager.decode();





//        new IntentIntegrator(this).initiateScan();

        qrScan = new IntentIntegrator(this);
        qrScan.setOrientationLocked(false); // default가 세로모드인데 휴대폰 방향에 따라 가로, 세로로 자동 변경됩니다.
        qrScan.setPrompt("Sample Text!");


        qrScan.initiateScan();

//        IntentIntegrator intentIntegrator = new IntentIntegrator(this);
//        intentIntegrator.setBeepEnabled(false);//바코드 인식시 소리
//        intentIntegrator.setCaptureActivity(MainActivity.class);
//        intentIntegrator.initiateScan();



    }



    
}
