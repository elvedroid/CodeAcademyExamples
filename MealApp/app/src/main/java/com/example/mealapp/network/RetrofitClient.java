package com.example.mealapp.network;

import com.example.mealapp.BuildConfig;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {
    private static MealApi mealApi;

    public static MealApi getMealApi() {
        if (mealApi == null) {
            createMealApi();
        }
        return mealApi;
    }

    private static void createMealApi() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BuildConfig.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        mealApi = retrofit.create(MealApi.class);
    }
}
