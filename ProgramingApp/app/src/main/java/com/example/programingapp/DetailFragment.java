package com.example.programingapp;


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

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;


/**
 * A simple {@link Fragment} subclass.
 */
public class DetailFragment extends Fragment {


    public DetailFragment() {
        // Required empty public constructor
    }


    TextView tvAuthor, tvQuote, tvRating;

    DetailAsyncTask detailAsyncTask = new DetailAsyncTask();





    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);


        detailAsyncTask.execute();
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

    class DetailAsyncTask extends AsyncTask <Void, Void , Quote>{





        @Override
        protected Quote doInBackground(Void... voids) {

            Uri uri = Uri.parse(MainActivity.BASE_URL + "quotes/id/" + getArguments().getString("quoteId") );

            Quote quotes = null;

            try {
                URL url = new URL(uri.toString());
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("GET");
                httpURLConnection.connect();
                int responseCode = httpURLConnection.getResponseCode();
                if(responseCode == 200){
                    InputStream inputStream = httpURLConnection.getInputStream();
                    String results = ConvertUtil.convertIsToString(inputStream);

                    JSONObject jsonObject = new JSONObject(results);

                    String quotesEn;
                    String author;
                    double rating;
                    int numberOfVotes;
                    String id;

                    quotesEn = (String) jsonObject.get("en");

                    author = (String) jsonObject.get("author");

                    rating = 0.0;
                    if(jsonObject.has("rating")){

                        rating = (Double) jsonObject.getDouble("rating");
                    }

                    numberOfVotes = 0;
                    if(jsonObject.has("numberOfVotes")) {
                        numberOfVotes = (Integer) jsonObject.get("numberOfVotes");
                    }

                    id = (String) jsonObject.get("id");

                    quotes = new Quote(author, quotesEn, rating, id, numberOfVotes);

                }




            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }


            return quotes;
        }




        @Override
        protected void onPostExecute(Quote quotes) {
            super.onPostExecute(quotes);

            tvQuote.setText(quotes.getEn());
            tvAuthor.setText(quotes.getAuthor());
            tvRating.setText(quotes.getRating() + "(" + quotes.getNumberOfVotes() + ")");


        }
    }

}
