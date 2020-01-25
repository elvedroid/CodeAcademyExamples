package com.example.mealapp.network;

import com.example.mealapp.BuildConfig;
import com.example.mealapp.model.GetCategoriesResponse;
import com.example.mealapp.model.GetMealsByCategoryResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface MealApi {

    @GET(BuildConfig.AUTH_KEY + "/categories.php")
    Call<GetCategoriesResponse> getCategories();

    @GET(BuildConfig.AUTH_KEY + "/filter.php")
    Call<GetMealsByCategoryResponse> getMealsByCategory(@Query("c") String category);
}
