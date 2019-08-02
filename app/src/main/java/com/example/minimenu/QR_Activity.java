package com.example.minimenu;

import android.app.Activity;
import android.content.Intent;
import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.journeyapps.barcodescanner.DecoratedBarcodeView;

public class QR_Activity extends AppCompatActivity {


    ImageView btnBack_QR;
//    DecoratedBarcodeView scanner_QR;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qr_);

        btnBack_QR = (ImageView)findViewById(R.id.btnBack_QR);
//        scanner_QR = (DecoratedBarcodeView) findViewById(R.id.scanner_QR);



    }

    public void onResume(){
        super.onResume();

        IntentIntegrator intentIntegrator = new IntentIntegrator(this);
        intentIntegrator.setCaptureActivity(EmptyActivity.class);
        intentIntegrator.initiateScan();

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        Log.d("onActivityResult", "onActivityResult: .");
        if (resultCode == Activity.RESULT_OK) {
            IntentResult scanResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, intent);
            String re = scanResult.getContents();
            String message = re;
            Log.d("onActivityResult", "onActivityResult: ." + re);
            Toast.makeText(this, re, Toast.LENGTH_LONG).show();
        }
        else{
            super.onActivityResult(requestCode, resultCode, intent);
            Intent intentFinal = new Intent(getApplicationContext(), FinalActivity.class);
            startActivity(intentFinal);
        }

    }

}
