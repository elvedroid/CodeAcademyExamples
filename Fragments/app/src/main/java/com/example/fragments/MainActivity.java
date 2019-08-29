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
        firstFragment.setListener(new OnStartSecondFragmentListener() {
            @Override
            public void startSecondFragment(String name) {
                SecondFragment secondFragment = new SecondFragment();
                Bundle args = new Bundle();
                args.putString(NAME_EXTRA, name);
                secondFragment.setArguments(args);
//                getFragmentManager() го враќа истиот фрагмент менаџер како и
//                getSupportFragmentManager() во MainActivity класата
                FragmentManager fragmentManager = getSupportFragmentManager();
//                Проверуваме дали постои фрагмент менаџерот. Во фрагментите е добро да се прави оваа
//                проверка затоа што има ситуации кога getFragmentManager() враќа null.
                if (fragmentManager != null) {
//                    Тука го заменуваме првиот фрагмент со вториот, и вториот фрагмент го додаваме
//                    во BackStack-от од фрагмент менаџерот(Најчесто корисно за правилно
//                    функционирање на back копчето од андроид телефонот).
                    fragmentManager
                            .beginTransaction()
                            .replace(R.id.frameLayout, secondFragment)
                            .addToBackStack("SecondFragment")
                            .commit();
                }
            }
        });
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
