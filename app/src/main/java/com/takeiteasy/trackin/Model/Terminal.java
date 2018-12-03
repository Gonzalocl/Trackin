package com.takeiteasy.trackin.Model;

import android.util.Log;
import android.widget.TextView;

public class Terminal {

    private int lines;
    private String[] content;
    private int head;
    private TextView tv;
    private static final int DEFAULT_LINES = 5;
    private static final String DEBUG_TAG = "DEBUGO";
    private int size;

    public Terminal(int lines, TextView tv) {
        this.lines = lines;
        this.content = new String[lines];
        this.head = 0;
        this.tv = tv;
        this.size = 0;
    }

    public Terminal(TextView tv) {
        this(DEFAULT_LINES, tv);
    }

    public void println (String l) {
        String text = "";
        content[head] = l + "\n";
        if (size < lines) size++;
        for (int i = 0; i < size; i++) {
            text += content[(head+i) % lines];
        }
        head = mod(head-1, lines);
        tv.setText(text);
    }

    public static void e(String s) {
        Log.e(DEBUG_TAG, s);
    }

    public static int mod(int a, int b) {
        return (((a % b) + b) % b);
    }

}
