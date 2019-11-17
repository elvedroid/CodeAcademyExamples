package com.example.programingapp.quotes_list;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.example.programingapp.MainActivity;
import com.example.programingapp.model.Quote;
import com.example.programingapp.R;
import com.example.programingapp.repo.QuotesRepo;

import java.util.List;

public class QuotesListFragment extends Fragment {

    public QuotesListFragment() {
        // Required empty public constructor
    }

    private View rootView;
    private RecyclerView recyclerView;
    private ProgressBar progressBar;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        QuotesRepo quotesRepo = QuotesRepo.getInstance(getContext());
        quotesRepo.getQuotes(new GetQuotesListener() {
            @Override
            public void onSuccess(List<Quote> quotes) {
                QuotesAdapter adapter = new QuotesAdapter(quotes, getFragmentManager());
                recyclerView.setAdapter(adapter);
                progressBar.setVisibility(View.GONE);
                recyclerView.setVisibility(View.VISIBLE);
            }

            @Override
            public void onError(Exception e) {
                progressBar.setVisibility(View.GONE);
            }
        });
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if(rootView == null) {
            rootView = inflater.inflate(R.layout.fragment_main, container, false);
        }

        progressBar = rootView.findViewById(R.id.progresBar);
        recyclerView = rootView.findViewById(R.id.rvRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        // Inflate the layout for this fragment
        return rootView;
    }
}
