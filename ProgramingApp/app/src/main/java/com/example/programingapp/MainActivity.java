package com.example.programingapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.FrameLayout;

import com.example.programingapp.db.QuoteDatabase;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.WeakReference;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    public static final String BASE_URL = "https://programming-quotes-api.herokuapp.com/";
    private static final String QUOTES_PATH = "quotes";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setUpAlarm();

        FrameLayout frameLayout = findViewById(R.id.frameLayout);

        MainFragment fragment = new MainFragment();

        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager
                .beginTransaction()
                .replace(R.id.frameLayout, fragment)
                .commit();
    }

    private void setUpAlarm() {
        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);

        Intent intent = new Intent(this, QuoteNotificationReciever.class);

        PendingIntent pendingIntent = PendingIntent.getBroadcast(
                this,
                1,
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT);

        if (alarmManager != null) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(System.currentTimeMillis());
            calendar.set(Calendar.HOUR, 10);
            calendar.set(Calendar.MINUTE, 00);
            alarmManager.setRepeating(
                    AlarmManager.RTC_WAKEUP,
                    calendar.getTimeInMillis(),
                    60000 * 60 * 24,
                    pendingIntent);
        }
    }

    static class QuotesAsyncTask extends AsyncTask<Void, Void, ArrayList<Quote>> {

        private WeakReference<Context> context;
        GetQuotesListener listener;

        public QuotesAsyncTask(Context context, GetQuotesListener listener) {
            this.context = new WeakReference<>(context);
            this.listener = listener;
        }

        @Override
        protected ArrayList<Quote> doInBackground(Void... voids) {
            Uri uri = Uri.parse(BASE_URL + QUOTES_PATH);

            ArrayList<Quote> quotesResult = new ArrayList<>();

            try {
                URL url = new URL(uri.toString());
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("GET");
                httpURLConnection.connect();
                int responseCode = httpURLConnection.getResponseCode();
                if (responseCode == 200) {
                    InputStream inputStream = httpURLConnection.getInputStream();
                    String results = ConvertUtil.convertIsToString(inputStream);
                    JSONArray jsonArray = new JSONArray(results);
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = (JSONObject) jsonArray.get(i);
                        String quoteEn = (String) jsonObject.get("en");
                        double rating = 0.0;
                        if (jsonObject.has("rating")) {
                            rating = (Double) jsonObject.getDouble("rating");
                        }
                        String author = (String) jsonObject.get("author");
                        String id = (String) jsonObject.get("id");

                        quotesResult.add(new Quote(author, quoteEn, rating, id, 0));
                    }

                    // Ja polnime bazata so quotes (Dobro bi bilo tuka prvo da ja ivcistime basata,
                    // so clear metodata na quoteDao())
                    QuoteDatabase.getQuoteDatabase(context.get())
                            .quoteDao().insertQuotes(quotesResult);
                } else {
                    // Vo slucaj na nekoja greska od network povikot, zemi gi podatocite od bazata
                    quotesResult = (ArrayList<Quote>) QuoteDatabase.getQuoteDatabase(context.get())
                            .quoteDao().getAllQuotes();
                }

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
                // ovoj exception se slucuva koga nema internet, vo toj zemi gi podatocite od baza
                quotesResult = (ArrayList<Quote>) QuoteDatabase.getQuoteDatabase(context.get())
                        .quoteDao().getAllQuotes();
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return quotesResult;
        }

        @Override
        protected void onPostExecute(ArrayList<Quote> quotes) {
            listener.loadQuotes(quotes);
        }
    }
}
