package com.takeiteasy.trackin.Storage;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

public class CSVWriter {

    private PrintWriter pw;

    public CSVWriter(File f) {
        try {
            pw = new PrintWriter(f);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        pw.println("latitude,longitude,altitude,accuracy,timestamp");
    }

    synchronized public void newrec(double lat, double lon, double alt, float acc, long time) {
        pw.println(lat + "," +
                lon + "," +
                alt + "," +
                acc + "," +
                time
        );
    }

    public void fin() {
        pw.close();
    }


}
