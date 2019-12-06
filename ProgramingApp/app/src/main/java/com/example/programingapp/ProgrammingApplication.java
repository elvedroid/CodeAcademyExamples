package com.example.programingapp;

import android.app.Application;
import android.content.Context;

import androidx.multidex.MultiDex;

public class ProgrammingApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }
}
