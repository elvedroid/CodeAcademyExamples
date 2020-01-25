package com.example.mealapp.ui.meal_list;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mealapp.R;
import com.example.mealapp.model.Category;
import com.example.mealapp.model.GetMealsByCategoryResponse;
import com.example.mealapp.model.Meal;
import com.example.mealapp.network.RetrofitClient;
import com.example.mealapp.ui.base.BaseFragment;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MealListFragment extends BaseFragment {

    public static final String TAG = MealListFragment.class.getSimpleName();

    private RecyclerView rvMeals;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_meal_list, container, false);
        if (getArguments() == null) return rootView;

        Category category = (Category) getArguments().getSerializable("CATEGORY");
        if (category == null) return rootView;

        setTitle(category.getStrCategory());

        rvMeals = rootView.findViewById(R.id.rvMeals);
        rvMeals.setLayoutManager(new LinearLayoutManager(getContext()));

        RetrofitClient.getMealApi().getMealsByCategory(category.getStrCategory()).enqueue(new Callback<GetMealsByCategoryResponse>() {
            @Override
            public void onResponse(Call<GetMealsByCategoryResponse> call, Response<GetMealsByCategoryResponse> response) {
                List<Meal> meals = response.body().getMeals();
                MealsAdapter adapter = new MealsAdapter(meals);
                rvMeals.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<GetMealsByCategoryResponse> call, Throwable t) {

            }
        });


        return rootView;
    }
}
