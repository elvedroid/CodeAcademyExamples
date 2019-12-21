package com.example.programingapp.quotes_list;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.programingapp.model.Quote;
import com.example.programingapp.repo.QuotesRepo;
import com.example.programingapp.repo.QuotesRepoJava;

import java.util.List;

public class QuotesListViewModel extends AndroidViewModel {

    MutableLiveData<List<Quote>> quotes = new MutableLiveData<>();
    MutableLiveData<Boolean> progressBar = new MutableLiveData<>();
    MutableLiveData<String> error = new MutableLiveData<>();

    public QuotesListViewModel(@NonNull Application application) {
        super(application);
    }

    public void loadQuotes() {
        progressBar.setValue(true);
        QuotesRepo quotesRepo = QuotesRepo.Companion.getInstance(getApplication());
        quotesRepo.getQuotes(new GetQuotesListener() {
            @Override
            public void onSuccess(List<Quote> quoteList) {
                progressBar.setValue(false);
                quotes.setValue(quoteList);
            }

            @Override
            public void onError(Exception e) {
                progressBar.setValue(false);
                error.setValue(e.getMessage());
            }
        });
    }


}
