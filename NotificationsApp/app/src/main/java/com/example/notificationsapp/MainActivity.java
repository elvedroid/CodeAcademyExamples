package com.example.notificationsapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    private static final String CHANNEL_ID = "CHANNEL_ID";
    public static final int NOTIFICATION_ID = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Kreirame notificationManager koj kje ni koristi za otvoranje/zatvoranje na notifikaciite
        final NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        findViewById(R.id.open_notification).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Proveruvame dali sme na >= od API 26, ako sme, treba da kreirame kanal
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    // Kreirame kanal - CHANNEL_ID go definirame sami, i kje go koristime koga kje
                    // kreirame notifikacija, za da ja grupirame notifikacijata vo ovoj kanal
                    NotificationChannel channel = new NotificationChannel(CHANNEL_ID,
                            "Channel 1",
                            NotificationManager.IMPORTANCE_DEFAULT);

                    // Proveruvame dali notificationManager postoi
                    if (notificationManager != null) {
                        // go kreirame kanalot - so ovaa linija kod se kreira kanalot i kje bide
                        // vidliv vo Settings na telefonot
                        notificationManager.createNotificationChannel(channel);
                    }
                }

                // Kreirame intent koj go startuva NotificationActivity
                Intent intent = new Intent(MainActivity.this, NotificationActivity.class);
                // Go wrap-uvame intentot vo PendingIntent
                PendingIntent pendingIntent = PendingIntent.getActivity(MainActivity.this,
                        1,
                        intent,
                        PendingIntent.FLAG_UPDATE_CURRENT);

                // Kreirame intent koj go startuva BroadcastClose resiverot. Ovoj resiver treba da go registrirame
                // (vo AndroidManifest fajlot)
                Intent intentClose = new Intent(MainActivity.this, BroadcastClose.class);
                // Go wrap-uvame intentot vo PendingIntent
                PendingIntent pendingIntentClose = PendingIntent.getBroadcast(MainActivity.this,
                        2,
                        intentClose,
                        PendingIntent.FLAG_UPDATE_CURRENT);

                // Kreirame action Close koj pri klik ke go trigerira BroadcastClose resiverot.
                // Bidejki ovoj action bara pendingIntent a nema onClickListener, morame so Intent
                // da go hendlame klikot na ovoj action. Bidejki pri klik na ovoj action sakame samo
                // da ja zatvorime notifikacijata (bez pritoa da prikazuvame nekoj ekran) zatoa go
                // kreiravme BroadcastClose resiverot, koj pri klik na actionot Close kje ja ranuva metodata
                // onReceive. Vo onReceive metodata ja zatvorame notifikacijata.
                NotificationCompat.Action action = new NotificationCompat.Action(android.R.drawable.ic_menu_close_clear_cancel,
                        "Close",
                        pendingIntentClose);

                // Ja kreirame notifikacijata
                Notification notification = new NotificationCompat.Builder(MainActivity.this, CHANNEL_ID)
                        .setContentTitle("Notification Title")
                        .setContentText("Notification Text")
                        .setSmallIcon(android.R.drawable.star_on)
                        .setContentIntent(pendingIntent)
                        .addAction(action)
                        .setAutoCancel(true)
                        .setStyle(new NotificationCompat.BigTextStyle()
                                .bigText("Some very long text!")
                                .setBigContentTitle("Big Title")
                                .setSummaryText("Summury Text"))
                        .build();


                if (notificationManager != null) {
                    // Otvori ja notifikacijata - NOTIFICATION_ID go definirame sami i istoto mozeme
                    // da go koristime za zatvoranje na notifikacijata
                    notificationManager.notify(NOTIFICATION_ID, notification);
                }
            }
        });


        findViewById(R.id.close_notification).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Kako i sekade, proveri dali menadzerot postoi
                if (notificationManager != null) {
                    // Zatvori ja notifikacijata so id-to so koe sme ja otvorile notifikacijata
                    notificationManager.cancel(NOTIFICATION_ID);
                }
            }
        });
    }

}
