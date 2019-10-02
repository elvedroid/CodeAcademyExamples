package com.example.notificationsapp;

import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import static com.example.notificationsapp.MainActivity.NOTIFICATION_ID;

public class BroadcastClose extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        // Kreirame notificationManager, no ovoj pat go koristime context-ot od OnReceive metodata
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        // Ja zatvorame notifikacijata
        if (notificationManager != null) {
            notificationManager.cancel(NOTIFICATION_ID);
        }
    }
}
