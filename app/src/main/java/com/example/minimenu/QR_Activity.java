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

    Button btnToReceipt;

    private CaptureManager capture;
    private DecoratedBarcodeView barcodeScannerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qr_);

        btnBack_QR = (ImageView)findViewById(R.id.btnBack_QR);
        btnToReceipt = (Button)findViewById(R.id.btnReceipt);

        btnBack_QR.setOnClickListener(Click);
        btnToReceipt.setOnClickListener(Click);

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
                    Intent intent = new Intent(getApplicationContext(), MenuActivity.class);
                    startActivity(intent);
                    break;
                case R.id.btnReceipt:
                    Intent intent1 = new Intent(getApplicationContext(), FinalActivity.class);
                    startActivity(intent1);
            }
        }
    } ;

    public void onResume(){
        super.onResume();

        capture.onResume();

//        IntentIntegrator intentIntegrator = new IntentIntegrator(this);
//        intentIntegrator.setCaptureActivity(QR_Activity.class);
//        intentIntegrator.initiateScan();

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, intent);
        if(result != null) {
            Log.d("in","in");
            if(result.getContents() == null) {
                Log.d("null", "null");
                Toast.makeText(this, "Cancelled", Toast.LENGTH_LONG).show();

            } else {
                Log.d("scanning", "scanning");
                Toast.makeText(this, "Scanned: " + result.getContents(), Toast.LENGTH_LONG).show();

            }
        } else {
            Log.d("else", "else");
            super.onActivityResult(requestCode, resultCode, intent);
        }

//        Log.d("onActivityResult", "onActivityResult: .");
//        if (resultCode == Activity.RESULT_OK) {
//            IntentResult scanResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, intent);
//            String re = scanResult.getContents();
//            String message = re;
//            Log.d("onActivityResult", "onActivityResult: ." + re);
//            Toast.makeText(this, re, Toast.LENGTH_LONG).show();
//        }
//        else{
//            super.onActivityResult(requestCode, resultCode, intent);
//        }
    }

}
