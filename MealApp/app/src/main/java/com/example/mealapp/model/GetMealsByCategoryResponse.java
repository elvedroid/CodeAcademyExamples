package com.example.mealapp.model;

import java.util.List;

public class GetMealsByCategoryResponse {
    private List<Meal> meals;

    public GetMealsByCategoryResponse() {
    }

    public GetMealsByCategoryResponse(List<Meal> meals) {
        this.meals = meals;
    }

    public List<Meal> getMeals() {
        return meals;
    }

    public void setMeals(List<Meal> meals) {
        this.meals = meals;
    }
}
