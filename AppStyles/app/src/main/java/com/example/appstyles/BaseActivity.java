package com.example.appstyles;

import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.util.DisplayMetrics;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceManager;

import java.util.Locale;

public abstract class BaseActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setUpStyle();
        setUpLanguage();
    }

    private void setUpStyle() {
        boolean theme = PreferenceManager.getDefaultSharedPreferences(this).getBoolean("key_theme", false);
        setTheme(theme ? R.style.DarkTheme : R.style.AppTheme);
    }

    private void setUpLanguage() {
        Resources res = getBaseContext().getResources();
// Change locale settings in the app.
        DisplayMetrics dm = res.getDisplayMetrics();
        Configuration conf = res.getConfiguration();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            boolean lang_boolean= PreferenceManager.getDefaultSharedPreferences(this).getBoolean("key_lang", false);
            String lang=(lang_boolean)?"en":"mk";
            conf.setLocale(new Locale(lang.toLowerCase())); // API 17+ only.
        }
// Use conf.locale = new Locale(...) if targeting lower versions
        res.updateConfiguration(conf, dm);
    }
}
