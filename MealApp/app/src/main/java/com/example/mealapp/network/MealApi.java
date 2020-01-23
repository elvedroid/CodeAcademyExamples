package com.example.mealapp.network;

import com.example.mealapp.BuildConfig;
import com.example.mealapp.model.GetCategoriesResponse;

import retrofit2.Call;
import retrofit2.http.GET;

public interface MealApi {
    @GET(BuildConfig.AUTH_KEY + "/categories.php")
    Call<GetCategoriesResponse> getCategories();
}
