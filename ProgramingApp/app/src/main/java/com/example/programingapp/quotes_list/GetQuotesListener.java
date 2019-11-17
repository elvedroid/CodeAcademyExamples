package com.example.programingapp.quotes_list;

import com.example.programingapp.model.Quote;

import java.util.List;

public interface GetQuotesListener {

    void onSuccess(List<Quote> quotes);

    void onError(Exception e);
}
