package com.example.mealapp.ui.explore;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.mealapp.R;
import com.example.mealapp.model.Category;
import com.example.mealapp.ui.meal_list.MealListFragment;

import java.util.List;

public class CategoriesAdapter extends RecyclerView.Adapter<CategoriesAdapter.CategoriesViewHolder> {

    private FragmentManager fragmentManager;
    private List<Category> categories;

    public CategoriesAdapter(FragmentManager fragmentManager,
                             List<Category> categories) {
        this.fragmentManager = fragmentManager;
        this.categories = categories;
    }

    @NonNull
    @Override
    public CategoriesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_categories, parent, false);
        return new CategoriesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoriesViewHolder holder, int position) {
        final Category category = categories.get(holder.getAdapterPosition());

        Glide.with(holder.ivCategory.getContext())
                .load(category.getStrCategoryThumb())
                .into(holder.ivCategory);
        holder.tvCategory.setText(category.getStrCategory());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = new MealListFragment();
                Bundle bundle = new Bundle();
                bundle.putSerializable("CATEGORY", category);
                fragment.setArguments(bundle);
                fragmentManager.beginTransaction()
                        .replace(R.id.explore_container, fragment)
                        .addToBackStack(MealListFragment.TAG)
                        .commit();
            }
        });
    }

    @Override
    public int getItemCount() {
        return categories.size();
    }

    static class CategoriesViewHolder extends RecyclerView.ViewHolder {
        ImageView ivCategory;
        TextView tvCategory;

        CategoriesViewHolder(@NonNull View itemView) {
            super(itemView);
            ivCategory = itemView.findViewById(R.id.ivCategory);
            tvCategory = itemView.findViewById(R.id.tvCategory);
        }
    }
}
