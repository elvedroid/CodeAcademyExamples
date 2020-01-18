package com.example.mealapp.ui.explore;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.mealapp.R;
import com.example.mealapp.ui.base.BaseFragment;

/**
 * A simple {@link Fragment} subclass.
 */
public class ExploreFragment extends BaseFragment {


    public ExploreFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        setTitle(R.string.explore);
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_explore, container, false);
    }

}
