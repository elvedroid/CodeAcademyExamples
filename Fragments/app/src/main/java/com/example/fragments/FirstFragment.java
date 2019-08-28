package com.example.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import static com.example.fragments.MainActivity.NAME_EXTRA;

public class FirstFragment extends Fragment {

    public FirstFragment() {
        // Required empty public constructor
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
//        Се претвара xml фајлот R.layout.fragment_first во View, кое мора да го вратиме каку
//        резултат од оваа метода за да може фрагментот да се исцрта.
        View rootView = inflater.inflate(R.layout.fragment_first, container, false);

//        Тука следуваат сите поставувања на фрагментот и сите негови View-а.

//        Моментално не се користи
        final EditText etName = rootView.findViewById(R.id.etName);

        Button button = rootView.findViewById(R.id.btnOpenSecondActivity);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SecondFragment secondFragment = new SecondFragment();
                Bundle args = new Bundle();
                args.putString(NAME_EXTRA, etName.getText().toString());
                secondFragment.setArguments(args);
//                getFragmentManager() го враќа истиот фрагмент менаџер како и
//                getSupportFragmentManager() во MainActivity класата
                FragmentManager fragmentManager = getFragmentManager();
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

        return rootView;
    }
}
