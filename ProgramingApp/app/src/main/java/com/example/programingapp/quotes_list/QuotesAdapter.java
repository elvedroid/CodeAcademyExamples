package com.example.programingapp.quotes_list;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.programingapp.model.Quote;
import com.example.programingapp.R;
import com.example.programingapp.quotes_details.QuoteDetailFragment;

import java.util.List;

public class QuotesAdapter extends RecyclerView.Adapter<QuotesAdapter.QuotesViewHolder> {
     private List<Quote> quotesList;
     private FragmentManager fragmentManager;

    public QuotesAdapter(List<Quote> quotesList, FragmentManager fragmentManager) {
        this.quotesList = quotesList;
        this.fragmentManager = fragmentManager;
    }

    @NonNull
    @Override
    public QuotesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item__quotes,parent,false);
        QuotesViewHolder quotesViewHolder = new QuotesViewHolder(view);
        return quotesViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull QuotesViewHolder holder, int position) {

        final Quote quote = quotesList.get(holder.getAdapterPosition());

        holder.tvQuote.setText(quote.getEn());
        holder.tvRating.setText(String.valueOf(quote.getRating()));
        holder.tvAuthor.setText(quote.getAuthor());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                QuoteDetailFragment fragment = new QuoteDetailFragment();

                Bundle bundle = new Bundle();
                bundle.putString("quoteId", quote.getId());

                fragment.setArguments(bundle);

                fragmentManager
                        .beginTransaction()
                        .replace(R.id.frameLayout, fragment)
                        .addToBackStack("null")
                        .commit();
            }
        });

    }

    @Override
    public int getItemCount() {
        return quotesList.size();

    }

    public static class QuotesViewHolder extends RecyclerView.ViewHolder{
        TextView tvQuote;
        TextView tvRating;
        TextView tvAuthor;

        public QuotesViewHolder(@NonNull View itemView) {
            super(itemView);
            tvQuote =itemView.findViewById(R.id.en_textview);
            tvAuthor =itemView.findViewById(R.id.author_textview);
            tvRating =itemView.findViewById(R.id.rating_textview);
        }
    }
}
