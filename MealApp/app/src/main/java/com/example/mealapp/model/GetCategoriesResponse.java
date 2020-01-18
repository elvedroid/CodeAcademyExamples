package com.example.mealapp.model;

import java.util.List;

public class GetCategoriesResponse {
    private List<Category> categories;

    public GetCategoriesResponse() {
    }

    public GetCategoriesResponse(List<Category> categories) {
        this.categories = categories;
    }

    public List<Category> getCategories() {
        return categories;
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }
}
