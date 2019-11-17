package com.example.programingapp.repo;

import android.content.Context;
import android.os.AsyncTask;

import com.example.programingapp.QuotesApi;
import com.example.programingapp.RetrofitClient;
import com.example.programingapp.db.QuoteDao;
import com.example.programingapp.db.QuoteDatabase;
import com.example.programingapp.model.Quote;
import com.example.programingapp.quotes_details.GetQuoteDetailListener;
import com.example.programingapp.quotes_list.GetQuotesListener;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class QuotesRepo {

    private static QuotesRepo INSTANCE;
    private QuoteDao quoteDao;
    private QuotesApi quotesApi;

    private QuotesRepo() {
    }

    public static QuotesRepo getInstance(Context context) {
        if (INSTANCE == null) {
            INSTANCE = new QuotesRepo();
            QuoteDao quoteDao = QuoteDatabase.getQuoteDatabase(context).quoteDao();
            INSTANCE.setQuoteDao(quoteDao);
            QuotesApi quotesApi = RetrofitClient.getInstance().getQuotesApi();
            INSTANCE.setQuoteApi(quotesApi);
        }
        return INSTANCE;
    }

    private void setQuoteApi(QuotesApi quotesApi) {
        this.quotesApi = quotesApi;
    }

    public void getQuoteDetail(String id, final GetQuoteDetailListener listener){
        quotesApi.getQuoteDetail(id).enqueue(new Callback<Quote>() {
            @Override
            public void onResponse(Call<Quote> call, Response<Quote> response) {
                listener.onSuccess(response.body());
            }

            @Override
            public void onFailure(Call<Quote> call, Throwable t) {
                listener.onError((Exception) t);
            }
        });

    }

    public void getQuotes(final GetQuotesListener listener) {

        quotesApi.getAllQuotes().enqueue(new Callback<List<Quote>>() {
            @Override
            public void onResponse(Call<List<Quote>> call, final Response<List<Quote>> response) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        quoteDao.insertQuotes(response.body());
                    }
                }).start();

                listener.onSuccess(response.body());
            }

            @Override
            public void onFailure(Call<List<Quote>> call, Throwable t) {
                DatabaseQuotesAsyncTask asyncTask = new DatabaseQuotesAsyncTask(quoteDao, listener);
                asyncTask.execute();
            }
        });

    }

    public QuoteDao getQuoteDao() {
        return quoteDao;
    }

    public void setQuoteDao(QuoteDao quoteDao) {
        this.quoteDao = quoteDao;
    }

    static class DatabaseQuotesAsyncTask extends AsyncTask<Void, Void, List<Quote>> {

        private QuoteDao quoteDao;
        private GetQuotesListener listener;

        public DatabaseQuotesAsyncTask(QuoteDao quoteDao, GetQuotesListener listener) {
            this.quoteDao = quoteDao;
            this.listener = listener;
        }

        @Override
        protected List<Quote> doInBackground(Void... voids) {
            List<Quote> allQuotes = quoteDao.getAllQuotes();
            return allQuotes;
        }

        @Override
        protected void onPostExecute(List<Quote> quotes) {
            listener.onSuccess(quotes);
        }
    }
}
