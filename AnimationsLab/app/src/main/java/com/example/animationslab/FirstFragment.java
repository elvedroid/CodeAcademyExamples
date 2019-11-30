package com.example.animationslab;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;


/**
 * A simple {@link Fragment} subclass.
 */
public class FirstFragment extends Fragment {


    public FirstFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_first, container, false);

        rootView.findViewById(R.id.btnStartFragment)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (getFragmentManager() == null) {
                            return;
                        }
                        getFragmentManager().beginTransaction()
                                // So ova se postavuvaat animacii koi kje se primenat pri tranzicija
                                // na fragmentot. Treba da se postavat najmalku 2 animacii, od koi
                                // ednata kje bide koga fragmentot kje se replacne/addne a drugata
                                // koga fragmentot kje se izbrise (na primer, pri klik na back kopceto)
                                .setCustomAnimations(R.animator.card_flip_right_in,
                                        R.animator.card_flip_right_out,
                                        R.animator.card_flip_left_in,
                                        R.animator.card_flip_left_out)
                                .replace(R.id.frameLayout, new SecondFragment())
                                .addToBackStack(null)
                                .commit();
                    }
                });
        return rootView;
    }

}
