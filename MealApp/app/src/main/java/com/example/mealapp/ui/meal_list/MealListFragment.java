package com.example.mealapp.ui.meal_list;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.mealapp.R;
import com.example.mealapp.model.Category;
import com.example.mealapp.ui.base.BaseFragment;

public class MealListFragment extends BaseFragment {

    public static final String TAG = MealListFragment.class.getSimpleName();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_meal_list, container, false);
        if (getArguments() == null) return rootView;

        Category category = (Category) getArguments().getSerializable("CATEGORY");
        if (category == null) return rootView;

        setTitle(category.getStrCategory());



        return rootView;
    }
}
