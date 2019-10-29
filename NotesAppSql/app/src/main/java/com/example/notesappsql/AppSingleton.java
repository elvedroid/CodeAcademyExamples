package com.example.notesappsql;

import android.content.Context;

public class AppSingleton {
    private static AppSingleton INSTANCE;
    private DatabaseHelper dbHelper;

    public static AppSingleton getInstance(Context context) {
        if (INSTANCE == null) {
            INSTANCE = new AppSingleton();
            INSTANCE.setDbHelper(new DatabaseHelper(context));
        }
        return INSTANCE;
    }

    public void setDbHelper(DatabaseHelper dbHelper) {
        this.dbHelper = dbHelper;
    }

    public DatabaseHelper getDbHelper() {
        return dbHelper;
    }
}
