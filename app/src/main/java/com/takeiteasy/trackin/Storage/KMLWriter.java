package com.takeiteasy.trackin.Storage;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

public class KMLWriter {
    private PrintWriter pw;

    public KMLWriter(File f) {
        try {
            pw = new PrintWriter(f);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        pw.println("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                "<kml xmlns=\"http://www.opengis.net/kml/2.2\" xmlns:gx=\"http://www.google.com/kml/ext/2.2\" xmlns:kml=\"http://www.opengis.net/kml/2.2\" xmlns:atom=\"http://www.w3.org/2005/Atom\">\n" +
                "<Document>\n" +
                "\t<name>Tracks</name>\n" +
                "\t\n" +
                "\t<Style id=\"sty\">\n" +
                "\t\t<LineStyle>\n" +
                "\t\t\t<color>ffffff55</color>\n" +
                "\t\t\t<width>2</width>\n" +
                "\t\t</LineStyle>\n" +
                "\t</Style>\n" +
                "\n" +
                "\t<Placemark>\n" +
                "\t\t<name>probasao</name>\n" +
                "\t\t<description>\n" +
                "\t\t<![CDATA[<html>\n" +
                "\t\t<body>\n" +
                "\t\t\t<big><big><big>\n" +
                "\t\t\t\tdescription que puede tener html y js\n" +
                "\t\t\t</big></big></big>\n" +
                "\t\t</body>\n" +
                "\t\t</html>]]>\n" +
                "\t\t</description>\n" +
                "\t\t<LookAt>\n" +
                "\t\t\t<longitude>-0.9914174753828609</longitude>\n" +
                "\t\t\t<latitude>37.60638181339133</latitude>\n" +
                "\t\t\t<altitude>0</altitude>\n" +
                "\t\t\t<heading>0</heading>\n" +
                "\t\t\t<tilt>0</tilt>\n" +
                "\t\t\t<range>500</range>\n" +
                "\t\t\t<altitudeMode>clampToGround</altitudeMode>\n" +
                "\t\t</LookAt>\n" +
                "\t\t<styleUrl>#sty</styleUrl>\n" +
                "\t\t<LineString>\n" +
                "\t\t\t<coordinates>");
    }

    synchronized public void newrec(double lat, double lon) {
        pw.println(lon + "," + lat + ",0");
    }

    public void fin() {
        pw.println("\t\t\t</coordinates>\n" +
                "\t\t</LineString>\n" +
                "\t</Placemark>\n" +
                "</Document>\n" +
                "</kml>");
        pw.close();
    }
}
