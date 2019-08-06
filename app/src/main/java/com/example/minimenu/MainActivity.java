package com.example.minimenu;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.zxing.integration.android.IntentIntegrator;
import com.journeyapps.barcodescanner.CaptureManager;
import com.journeyapps.barcodescanner.DecoratedBarcodeView;

public class MainActivity extends AppCompatActivity {

    Button btnMain;

    // URL 설정.
    String url="http://hycurium.cafe24.com/minimenuProto/menuList.jsp";

    EditText edt;

    //content value
    ContentValues contentValues;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnMain = (Button)findViewById(R.id.btnMain);

        edt = (EditText)findViewById(R.id.edt);

        contentValues = new ContentValues();

//        String input = edt.getText().toString();   밖에서 이렇게 하면 안됨

        btnMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MenuActivity.class);
                startActivity(intent);

            }
        });

        findViewById(R.id.btnClick).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String input = edt.getText().toString();
                contentValues.put("key", input);

                HttpAsyncTask httpAsyncTask = new HttpAsyncTask(url,contentValues, getApplicationContext());
                httpAsyncTask.execute();

            }
        });




    }

}
