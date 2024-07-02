package com.example.bagrutproject;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import androidx.core.app.NotificationCompat;

public class Alarm extends BroadcastReceiver {


    @Override
    public void onReceive(Context context, Intent intent) {
        Notification notification = new NotificationCompat.Builder(context)
                .setContentTitle("Alarm Triggered")
                .setContentText("This is your reminder notification")
                .setSmallIcon(R.drawable.glayer)
                .build();

        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(1, notification);

    }
}
