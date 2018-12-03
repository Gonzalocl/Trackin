package com.takeiteasy.trackin.View;

import android.Manifest;
import android.app.Activity;
import android.app.IntentService;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityCompat.OnRequestPermissionsResultCallback;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.takeiteasy.trackin.Model.ListeninService;
import com.takeiteasy.trackin.R;



/**
 * Created by Elvis on 01/02/2018.
 */

public class Maine extends Activity implements OnRequestPermissionsResultCallback {

    public static final String KEY_RATE = "rate";
    public static final String KEY_HIST = "hist";
    public static final int CODE = 555;
    public static final int L_CODE = 666;
    public static final int W_CODE = 777;

    public boolean locationP = false;
    public boolean writeP = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.config);


        reqPer();


    }

    private void setButton() {
        Button b = findViewById(R.id.button_start);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(v.getContext(), Listenin.class);


                EditText et = findViewById(R.id.rate);
                if ( et.getText().toString().equals("") )
                    i.putExtra(KEY_RATE, getText(R.string.default_rate));
                else
                    i.putExtra(KEY_RATE, et.getText().toString());

                et = findViewById(R.id.hist);
                if ( et.getText().toString().equals(""))
                    i.putExtra(KEY_HIST, getText(R.string.default_hist));
                else
                    i.putExtra(KEY_HIST, et.getText().toString());

                startActivity(i);
                Intent noti = new Intent(v.getContext(), ListeninService.class);
                startForegroundService(noti);

            }
        });
    }


    public void reqPer() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.WRITE_EXTERNAL_STORAGE}, CODE);
        }
        else {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, L_CODE);
            } else {
                locationP = true;
            }

            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, W_CODE);
            } else {
                writeP = true;
            }
        }

        if ( locationP && writeP ) {
            setButton();
        } else {
            Button b = findViewById(R.id.button_start);
            b.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    reqPer();
                }
            });
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case CODE:
                if (grantResults.length > 0) {
                    if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                        locationP = true;
                    }
                    if (grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                        writeP = true;
                    }
                }
                break;
            case L_CODE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    locationP = true;
                }
                break;
            case W_CODE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    writeP = true;
                }
                break;
        }

        if ( locationP && writeP ) {
            setButton();
        }
    }

}