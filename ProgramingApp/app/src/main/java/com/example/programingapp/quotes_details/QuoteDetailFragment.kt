package com.example.programingapp.quotes_details

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.programingapp.R
import com.example.programingapp.extentions.*
import com.example.programingapp.model.Quote
import com.example.programingapp.repo.QuotesRepo
import com.example.programingapp.repo.QuotesRepoJava
import kotlinx.android.synthetic.main.fragment_detail.*
import java.util.*

class QuoteDetailFragment : Fragment() {

    override fun onAttach(context: Context) {
        super.onAttach(context)

        // povikuvanje kon extention function
        val number: Int = 5
        val result = number.add(7)

        // primer za filter i map funkciite
        val list = listOf<String>("SIOnfros", "ASdfosfnm", "Sfmoas", "fdiodsnfs", "Afiosndfn")
        var filteredList = list.filter { it.toLowerCase(Locale.getDefault()).contains("fn") }
        var mappedList = list.map { it.length }
                .filter { it > 6 }


        QuotesRepo.getInstance(context).getQuoteDetail(arguments?.getString("quoteId") ?: "1000",
                object : GetQuoteDetailListener {
                    override fun onSuccess(quote: Quote) {
                        tvQuote.text = quote.en
                        tvAuthor.text = quote.author
                        tvRating.text = quote.rating.toString() + "(" + quote.numberOfVotes + ")"
                    }

                    override fun onError(e: Exception) {

                    }
                })
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_detail, container, false)
    }

    // companion object e isto sto i static vo java
    companion object {
        val TAG = "QuoteDetailFragment"
    }

}// Required empty public constructor
