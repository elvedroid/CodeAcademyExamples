package com.example.fragments;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import static com.example.fragments.MainActivity.NAME_EXTRA;


/**
 * A simple {@link Fragment} subclass.
 */
public class SecondFragment extends Fragment {


    public SecondFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_second, container, false);
        TextView tvName = rootView.findViewById(R.id.tvName);
//        Проверуваме дали постојат аргументите
        if (getArguments() != null) {
//            Ова е идентично како и getStringExtra() кај интентинте.
//            Вториот аргумент од getString методот е опционален и е дефолтна вредност ако не постои
//            стринг со дадениот клуч.
            String name = getArguments().getString(NAME_EXTRA, "");
            tvName.setText(name);
        }
        return rootView;
    }

}
