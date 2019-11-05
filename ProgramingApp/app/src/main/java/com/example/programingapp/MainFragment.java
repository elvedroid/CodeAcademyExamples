package com.example.programingapp;


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

import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class MainFragment extends Fragment {


    public MainFragment() {
        // Required empty public constructor
    }


    ProgressBar progressBar;

    RecyclerView recyclerView;

    View rootView;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        MainActivity.QuotesAsyncTask quotesAsyncTask = new MainActivity.QuotesAsyncTask(
                getContext(),
                new GetQuotesListener(){

            @Override
            public void loadQuotes(List<Quote> quotes) {
                QuotesAdapter adapter = new QuotesAdapter(quotes, getFragmentManager());
                recyclerView.setAdapter(adapter);
                progressBar.setVisibility(View.GONE);
                recyclerView.setVisibility(View.VISIBLE);
            }
        });

        quotesAsyncTask.execute();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
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
