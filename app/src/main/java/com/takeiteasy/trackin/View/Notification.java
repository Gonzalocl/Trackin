package com.takeiteasy.trackin.View;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.v4.app.NotificationCompat;

import com.takeiteasy.trackin.R;

public class Notification {


    private static final int NOT_ID = 123;
    private static final String CHANNEL_ID = "noti";
    private static final String CHANNEL_NAME = "notis";
    private static final String CHANNEL_DESC = "description";
    private NotificationCompat.Builder builder;
    private Context context;
    NotificationManager notificationManager;



    public Notification(Context context) {

        Intent intent = new Intent(context, Listenin.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);

        this.context = context;
        builder = new NotificationCompat.Builder(context, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setContentTitle("Grabando ruta")
                .setContentText("1010")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setOnlyAlertOnce(true)
//                .setContentIntent(pendingIntent)
                .setAutoCancel(true);


        createNotificationChannel();
    }



    public void notitify(String text) {
        builder.setContentText(text);
        notificationManager.notify(NOT_ID, builder.build());
    }




    private void createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = CHANNEL_NAME;
            String description = CHANNEL_DESC;
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            notificationManager = context.getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

}
