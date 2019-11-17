package com.example.programingapp.quotes_details;

import com.example.programingapp.model.Quote;

public interface GetQuoteDetailListener {

    void onSuccess(Quote quote);

    void onError(Exception e);
}
