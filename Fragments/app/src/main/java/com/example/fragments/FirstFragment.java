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

    private OnStartSecondFragmentListener listener;

    public void setListener(OnStartSecondFragmentListener listener) {
        this.listener = listener;
    }

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

        final EditText etName = rootView.findViewById(R.id.etName);

        Button button = rootView.findViewById(R.id.btnOpenSecondActivity);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = etName.getText().toString();

                if (listener != null) {
                    listener.startSecondFragment(name);
                }
            }
        });

        return rootView;
    }
}
