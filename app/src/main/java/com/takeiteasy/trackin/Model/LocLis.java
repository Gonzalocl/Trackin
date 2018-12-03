package com.takeiteasy.trackin.Model;

import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;

import com.takeiteasy.trackin.Storage.CSVWriter;
import com.takeiteasy.trackin.Storage.KMLWriter;
import com.takeiteasy.trackin.View.Notification;

public class LocLis implements LocationListener {

    private Terminal t;
    private CSVWriter cw;
    private KMLWriter kw;
    private Notification n;
    private int count;

    public LocLis(Terminal t, CSVWriter cw, KMLWriter kw, Notification n) {
        this.t = t;
        this.cw = cw;
        this.kw = kw;
        this.n = n;
        this.count = 0;
    }

    @Override
    public void onLocationChanged(Location location) {
        count++;
        t.println(location.getLongitude() + "/" + location.getLatitude());
        cw.newrec(location.getLatitude(),
                location.getLongitude(),
                location.getAltitude(),
                location.getAccuracy(),
                location.getTime()
        );
        kw.newrec(location.getLatitude(), location.getLongitude());
        n.notitify(count + " puntos guardados");
    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {
        t.println("Status changed --> " + s);
    }

    @Override
    public void onProviderEnabled(String s) {
        t.println("provider enabled --> " + s);
    }

    @Override
    public void onProviderDisabled(String s) {
        t.println("provider disabled --> " + s);
    }
}
