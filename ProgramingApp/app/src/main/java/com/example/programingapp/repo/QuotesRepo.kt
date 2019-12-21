package com.example.programingapp.repo

import android.content.Context
import com.example.programingapp.QuotesApi
import com.example.programingapp.RetrofitClient
import com.example.programingapp.db.QuoteDao
import com.example.programingapp.db.QuoteDatabase
import com.example.programingapp.model.Quote
import com.example.programingapp.quotes_details.GetQuoteDetailListener
import com.example.programingapp.quotes_list.GetQuotesListener
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class QuotesRepo constructor(val quoteDao: QuoteDao, val quotesApi: QuotesApi) {

    companion object {
        @Volatile
        private var INSTANCE: QuotesRepo? = null

        fun getInstance(context: Context): QuotesRepo {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: buildRepo(context).also { INSTANCE = it }
            }
        }

        private fun buildRepo(context: Context): QuotesRepo {
            val dao = QuoteDatabase.getQuoteDatabase(context).quoteDao()
            val api = RetrofitClient.getInstance().getQuotesApi()
            return QuotesRepo(dao, api)
        }
    }

    fun getQuotes(listener: GetQuotesListener) {
        quotesApi.allQuotes.enqueue(object : Callback<List<Quote>> {
            override fun onResponse(call: Call<List<Quote>>, response: Response<List<Quote>>) {
                Thread(Runnable { quoteDao.insertQuotes(response.body()) }).start()

                listener.onSuccess(response.body())
            }

            override fun onFailure(call: Call<List<Quote>>, t: Throwable) {
                val asyncTask = QuotesRepoJava.DatabaseQuotesAsyncTask(quoteDao, listener)
                asyncTask.execute()
            }
        })
    }

    fun getQuoteDetail(id: String, listener: GetQuoteDetailListener) {
        quotesApi.getQuoteDetail(id).enqueue(object : Callback<Quote> {
            override fun onResponse(call: Call<Quote>, response: Response<Quote>) {
                listener.onSuccess(response.body())
            }
            override fun onFailure(call: Call<Quote>, t: Throwable) {
                listener.onError(t as Exception)
            }
        })

    }
}