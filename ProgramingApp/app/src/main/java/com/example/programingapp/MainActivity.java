package com.example.programingapp;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import com.example.programingapp.favorites.FavoritesFragment;
import com.example.programingapp.notification.RandomQuoteAlarm;
import com.example.programingapp.quotes_list.QuotesListFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RandomQuoteAlarm randomQuoteAlarm = new RandomQuoteAlarm(this);
        randomQuoteAlarm.setUpAlarm();

        BottomNavigationView bnvMain = findViewById(R.id.bnvMain);
        bnvMain.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.item_home:
                        QuotesListFragment fragment = new QuotesListFragment();
                        getSupportFragmentManager()
                                .beginTransaction()
                                .replace(R.id.frameLayout, fragment)
                                .commit();
                        return true;
                    case R.id.item_favorites:
                        FavoritesFragment favorites = new FavoritesFragment();
                        getSupportFragmentManager()
                                .beginTransaction()
                                .replace(R.id.frameLayout, favorites)
                                .commit();
                        return true;
                    default:
                        return false;
                }
            }
        });

        bnvMain.setSelectedItemId(R.id.item_home);
    }
}