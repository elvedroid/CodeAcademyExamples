package com.example.fragments;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    public static final String NAME_EXTRA = "NAME_EXTRA";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        Креираме нов фрагмент
        FirstFragment firstFragment = new FirstFragment();
//        Го земаме фрагмент менаџерот, започнуваме трансакција, го заменуваме FrameLayout-от
//        со firstFragment, и на крај мора да ја комитаме трансакцијата. Со ова го додадовме
//        првиот фрагмент во активитито.
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager
                .beginTransaction()
                .replace(R.id.frameLayout, firstFragment)
                .commit();

    }
}
