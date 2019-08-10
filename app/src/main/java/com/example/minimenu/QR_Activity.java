package com.example.minimenu;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.journeyapps.barcodescanner.CaptureManager;
import com.journeyapps.barcodescanner.DecoratedBarcodeView;

public class QR_Activity extends AppCompatActivity {

    ImageView btnBack_QR;

    private CaptureManager capture;
    private DecoratedBarcodeView barcodeScannerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qr_);

        btnBack_QR = (ImageView)findViewById(R.id.btnBack_QR);

        btnBack_QR.setOnClickListener(Click);

        barcodeScannerView = (DecoratedBarcodeView)findViewById(R.id.scanner_QR);

        capture = new CaptureManager(this, barcodeScannerView);
        capture.initializeFromIntent(getIntent(), savedInstanceState);
        capture.decode();

    }

    View.OnClickListener Click = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()){
                case R.id.btnBack_QR:
                    onBackPressed();
                    break;
            }
        }
    };

    public void onResume(){
        super.onResume();
        capture.onResume();

    }

//    @Override
//    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
//        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, intent);
//        if(result != null) {
//            Log.d("in","in");
//            if(result.getContents() == null) {
//                Log.d("null", "null");
//                Toast.makeText(this, "Cancelled", Toast.LENGTH_LONG).show();
//
//            } else {
//                Log.d("scanning", "scanning");
//                Toast.makeText(this, "Scanned: " + result.getContents(), Toast.LENGTH_LONG).show();
//
//            }
//        } else {
//            Log.d("else", "else");
//            super.onActivityResult(requestCode, resultCode, intent);
//        }
//
//
//    }

}
