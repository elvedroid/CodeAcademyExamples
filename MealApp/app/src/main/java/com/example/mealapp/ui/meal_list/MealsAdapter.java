package com.example.mealapp.ui.meal_list;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.mealapp.R;
import com.example.mealapp.model.Meal;

import java.util.List;

public class MealsAdapter extends RecyclerView.Adapter<MealsAdapter.MealViewHolder> {

    private List<Meal> meals;

    public MealsAdapter(List<Meal> meals) {
        this.meals = meals;
    }

    @NonNull
    @Override
    public MealViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_meal, parent, false);
        return new MealViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MealViewHolder holder, int position) {
        Meal meal = meals.get(holder.getAdapterPosition());

        holder.tvMeal.setText(meal.getStrMeal());
        Glide.with(holder.ivMeal.getContext())
                .load(meal.getStrMealThumb())
                .into(holder.ivMeal);
    }

    @Override
    public int getItemCount() {
        return meals.size();
    }

    class MealViewHolder extends RecyclerView.ViewHolder {

        TextView tvMeal;
        ImageView ivMeal;

        MealViewHolder(@NonNull View itemView) {
            super(itemView);
            tvMeal = itemView.findViewById(R.id.tvMeal);
            ivMeal = itemView.findViewById(R.id.ivMeal);
        }
    }
}
