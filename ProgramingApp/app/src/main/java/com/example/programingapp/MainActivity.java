package com.example.programingapp;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import com.example.programingapp.notification.RandomQuoteAlarm;
import com.example.programingapp.quotes_list.QuotesListFragment;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RandomQuoteAlarm randomQuoteAlarm = new RandomQuoteAlarm(this);
        randomQuoteAlarm.setUpAlarm();

        QuotesListFragment fragment = new QuotesListFragment();
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager
                .beginTransaction()
                .replace(R.id.frameLayout, fragment)
                .commit();
    }
}