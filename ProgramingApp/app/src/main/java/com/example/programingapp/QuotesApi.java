package com.example.programingapp;

import com.example.programingapp.model.Quote;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface QuotesApi {



    @GET("/quotes")
    Call<List<Quote>> getAllQuotes();

    @GET("/quotes/id/{id}")
    Call<Quote> getQuoteDetail(@Path("id")String id);
}
