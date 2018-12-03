package com.takeiteasy.trackin.View;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.takeiteasy.trackin.Model.LocLis;
import com.takeiteasy.trackin.Model.Terminal;
import com.takeiteasy.trackin.R;
import com.takeiteasy.trackin.Storage.CSVWriter;
import com.takeiteasy.trackin.Storage.KMLWriter;


import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Listenin extends Activity {

    int rate;
    int hist;

    @SuppressLint("MissingPermission")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent i = getIntent();
        setContentView(R.layout.terminal);
        rate = Integer.parseInt(i.getStringExtra(Maine.KEY_RATE)) * 1000;
        hist = Integer.parseInt(i.getStringExtra(Maine.KEY_HIST));
        TextView ter = findViewById(R.id.terminal);
        Terminal t = new Terminal(hist, ter);




        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            t.println("Almacenamiento disponible");
            t.println("Funcionando en modo activo");
        } else {
            t.println("ERROR: Almacenamiento NO disponible");
            return;
        }

        File f = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS), "Tracks");
        if(f.mkdirs()) {
            t.println("Se creo la carpeta correctamente");
        } /*else {
            t.println("ERROR: Fallo al crear la carpeta");
            return;
        }*/
        Date d = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss");
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd_HH-mm");
        t.println(sdf.format(d));
        String fileName = sdf.format(d);

        File truck = new File(f, fileName + ".csv");
        File ktruck = new File(f, fileName + ".kml");
        if (truck.exists() || ktruck.exists() || truck.isDirectory() || ktruck.isDirectory()) {
            int c = 0;
            while (truck.exists() || ktruck.exists() || truck.isDirectory() || ktruck.isDirectory()) {
                c++;
                truck = new File(f, fileName + "." + c + ".csv");
                ktruck = new File(f, fileName + "." + c + ".kml");
            }

            fileName = fileName + "." + c;

        }

        try {
            if (truck.createNewFile()) {
                t.println("Se creo el archivo " + fileName + ".csv correctamente");
            } else {
                t.println("ERROR: Fallo al crear la archivo csv");
                return;
            }
        } catch (IOException e) {
            t.println("ERROR: Excepcion al crear el archivo csv");
            e.printStackTrace();
        }

        try {
            if (ktruck.createNewFile()) {
                t.println("Se creo el archivo " + fileName + ".kml correctamente");
            } else {
                t.println("ERROR: Fallo al crear la archivo kml");
                return;
            }
        } catch (IOException e) {
            t.println("ERROR: Excepcion al crear el archivo kml");
            e.printStackTrace();
        }


        final CSVWriter cw = new CSVWriter(truck);
        final KMLWriter kw = new KMLWriter(ktruck);

        final LocationManager locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        Notification n = new Notification(this);
        final LocLis ll = new LocLis(t, cw, kw, n);

        locationManager.requestLocationUpdates(locationManager.GPS_PROVIDER, rate, 0, ll);

        Button b = findViewById(R.id.button_stop);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                locationManager.removeUpdates(ll);
                Intent i = new Intent(v.getContext(), Maine.class);
                cw.fin();
                kw.fin();
                startActivity(i);
            }
        });


    }


}
