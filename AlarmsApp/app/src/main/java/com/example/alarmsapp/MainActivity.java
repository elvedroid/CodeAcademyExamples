package com.example.alarmsapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    Intent registeredIntent;
    private NotificationBroadcastReceiver receiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        receiver = new NotificationBroadcastReceiver();
        Button button = findViewById(R.id.btnScheduleAlarm);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


//                Intent intent = new Intent(MainActivity.this, NotificationBroadcastReceiver.class);


//                boolean alarmExists = PendingIntent.getBroadcast(
//                        MainActivity.this,
//                        0,
//                        registeredIntent,
//                        PendingIntent.FLAG_NO_CREATE) != null;

                PendingIntent pendingIntent = PendingIntent.getBroadcast(
                        MainActivity.this,
                        1,
                        registeredIntent,
                        PendingIntent.FLAG_UPDATE_CURRENT);

                alarmManager.setRepeating(
                        AlarmManager.ELAPSED_REALTIME_WAKEUP,
                        SystemClock.elapsedRealtime() + 3000,
                        100000,
                        pendingIntent
                );

            }
        });
    }
}
