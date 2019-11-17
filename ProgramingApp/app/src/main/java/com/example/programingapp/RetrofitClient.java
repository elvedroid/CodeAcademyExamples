package com.example.programingapp;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {

    public static final String BASE_URL = "https://programming-quotes-api.herokuapp.com/";

    private QuotesApi quotesApi;
    private static RetrofitClient INSTANCE;

    public static RetrofitClient getInstance() {

        if (INSTANCE == null) {
                INSTANCE = new RetrofitClient();
            Retrofit retrofit = new Retrofit.Builder()
                    .addConverterFactory(GsonConverterFactory.create())
                    .baseUrl(BASE_URL)
                    .build();
            QuotesApi quotesApi = retrofit.create(QuotesApi.class);
            INSTANCE.setQuotesApi(quotesApi);
        }
        return INSTANCE;
    }

    public void setQuotesApi(QuotesApi quotesApi) {
        this.quotesApi = quotesApi;
    }

    public QuotesApi getQuotesApi() {
        return quotesApi;
    }
}
