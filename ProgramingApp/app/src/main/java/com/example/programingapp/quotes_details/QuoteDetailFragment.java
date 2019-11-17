package com.example.programingapp.quotes_details;

import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.programingapp.repo.QuotesRepo;
import com.example.programingapp.utils.ConvertUtil;
import com.example.programingapp.model.Quote;
import com.example.programingapp.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import static com.example.programingapp.RetrofitClient.BASE_URL;

public class QuoteDetailFragment extends Fragment {

    public QuoteDetailFragment() {
        // Required empty public constructor
    }

    private TextView tvAuthor, tvQuote, tvRating;


    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        QuotesRepo.getInstance(context).getQuoteDetail(getArguments().getString("quoteId"),
                new GetQuoteDetailListener() {
                    @Override
                    public void onSuccess(Quote quote) {
                        tvQuote.setText(quote.getEn());
                        tvAuthor.setText(quote.getAuthor());
                        tvRating.setText(quote.getRating() + "(" + quote.getNumberOfVotes() + ")");
                    }

                    @Override
                    public void onError(Exception e) {

                    }
                });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_detail, container, false);

        tvQuote = rootView.findViewById(R.id.tvQuote);
        tvAuthor = rootView.findViewById(R.id.tvAuthor);
        tvRating = rootView.findViewById(R.id.tvRating);
        return rootView;
    }

}
