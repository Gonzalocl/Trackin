package com.takeiteasy.trackin.Model;

import android.app.IntentService;
import android.app.Notification;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.widget.Toast;

import com.takeiteasy.trackin.R;

public class ListeninService extends IntentService {

    public ListeninService() {
        super("listeninService");
    }

    @Override
    public int onStartCommand(@Nullable Intent intent, int flags, int startId) {
        Toast.makeText(this, "Grabando", Toast.LENGTH_SHORT).show();
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {

        com.takeiteasy.trackin.View.Notification n = new com.takeiteasy.trackin.View.Notification(this);


        Notification notification = new NotificationCompat.Builder(this, "noti")
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setContentTitle("titulo")
                .setContentText("contenido")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setOnlyAlertOnce(true)
                .build();

        startForeground(999, notification);
        n.notitify("haha");

        try {
            Thread.sleep(1000*60*60*5);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        for (int i = 100 ; i < 120; i++) {
            try {
                Thread.sleep(1000*60*60*8);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            n.notitify("haha" + i);

        }


    }
}
