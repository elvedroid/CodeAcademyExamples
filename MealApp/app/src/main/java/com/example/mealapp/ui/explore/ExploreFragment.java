package com.example.mealapp.ui.explore;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mealapp.R;
import com.example.mealapp.model.Category;
import com.example.mealapp.model.GetCategoriesResponse;
import com.example.mealapp.network.RetrofitClient;
import com.example.mealapp.ui.base.BaseFragment;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class ExploreFragment extends BaseFragment {

    private ProgressBar progressBar;
    private RecyclerView rvCategories;
    private TextView tvError;

    public ExploreFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_explore, container, false);
        setTitle(R.string.explore);

        progressBar = rootView.findViewById(R.id.progress_bar);
        tvError = rootView.findViewById(R.id.tvError);

        rvCategories = rootView.findViewById(R.id.rvCategories);
        rvCategories.setLayoutManager(new GridLayoutManager(getContext(), 3));

        RetrofitClient.getMealApi().getCategories().enqueue(new Callback<GetCategoriesResponse>() {
            @Override
            public void onResponse(Call<GetCategoriesResponse> call, Response<GetCategoriesResponse> response) {
                progressBar.setVisibility(View.GONE);
                tvError.setVisibility(View.GONE);
                List<Category> categories = response.body().getCategories();
                CategoriesAdapter adapter = new CategoriesAdapter(getFragmentManager(), categories);
                rvCategories.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<GetCategoriesResponse> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
                tvError.setVisibility(View.VISIBLE);
            }
        });


        return rootView;
    }

}
