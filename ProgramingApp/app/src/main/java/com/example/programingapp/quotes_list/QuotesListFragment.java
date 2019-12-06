package com.example.programingapp.quotes_list;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.programingapp.R;
import com.example.programingapp.model.Quote;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

public class QuotesListFragment extends Fragment {

    public QuotesListFragment() {
        // Required empty public constructor
    }

    private View rootView;
    private RecyclerView recyclerView;
    private ProgressBar progressBar;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (rootView == null) {
            rootView = inflater.inflate(R.layout.fragment_main, container, false);
        }

        progressBar = rootView.findViewById(R.id.progresBar);
        recyclerView = rootView.findViewById(R.id.rvRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        QuotesListViewModel viewModel = ViewModelProviders.of(this).get(QuotesListViewModel.class);
        viewModel.quotes.observe(this, new Observer<List<Quote>>() {
            @Override
            public void onChanged(List<Quote> quoteList) {
                QuotesAdapter adapter = new QuotesAdapter(quoteList, getFragmentManager());
                recyclerView.setAdapter(adapter);
                recyclerView.setVisibility(VISIBLE);
            }
        });

        viewModel.progressBar.observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                progressBar.setVisibility(aBoolean ? VISIBLE : GONE);
            }
        });

        viewModel.error.observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                Toast.makeText(getContext(), s, Toast.LENGTH_LONG).show();
            }
        });
        viewModel.loadQuotes();
        // Inflate the layout for this fragment

        return rootView;
    }

    private void addData() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        Map<String, Object> quote = new HashMap<>();
        quote.put("id", "12345");
        quote.put("author", "Elvedin");
        quote.put("quote", "Nekoj quote");
        quote.put("rating", 4.0);
        quote.put("numberOfVotes", 1);

        db.collection("Quotes")
                .add(quote)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.d("QuotesList", "Success");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d("QuotesList", e.getMessage());
                    }
                });


        Map<String, Object> user = new HashMap<>();
        user.put("first", "Ada");
        user.put("last", "Lovelace");
        user.put("born", 1815);
    }
}
