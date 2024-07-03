package com.example.bagrutproject;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;

public class NotificationReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Toast.makeText(context, "Welcome back", Toast.LENGTH_LONG).show();
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        Intent repeatingIntent = new Intent(context, MainActivity.class);
        repeatingIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, repeatingIntent, PendingIntent.FLAG_IMMUTABLE);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "oneMinuteNotificationChannel")
                .setContentIntent(pendingIntent)
                .setSmallIcon(android.R.drawable.ic_dialog_info)
                .setContentTitle("Motivation")
                .setContentText("This is your reminder to never quit gambling!")
                .setAutoCancel(true);

        notificationManager.notify(100, builder.build());
    }
}
