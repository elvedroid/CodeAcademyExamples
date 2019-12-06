package com.example.programingapp.quotes_list;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.programingapp.R;
import com.example.programingapp.model.Quote;
import com.example.programingapp.model.User;
import com.example.programingapp.quotes_details.QuoteDetailFragment;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item__quotes, parent, false);
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

        holder.ibFavorites.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Zema instanca od firbase databazata
                final FirebaseFirestore db = FirebaseFirestore.getInstance();
                // referenca do kolekcijata quotes
                final CollectionReference quotesRef = db.collection("quotes");
                // referenca do userot so path users/zo5rKdiMe9l2gxDgPmR6
                final DocumentReference userRef = db.collection("users")
                        .document("zo5rKdiMe9l2gxDgPmR6");
                // user koj bi go dobile so koristenje na firebase authentication, ova e objektot/dokumentot
                // koj e so referenca userRef
                final User user = new User("testUser", null);

                // query koe treba da go najde quotot so id = quote.getId() t.e. quotot koj treba da
                // se dodade vo favorites
                Query quoteQuery = quotesRef.whereEqualTo("id", quote.getId());
                quoteQuery.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful() && task.getResult() != null) {

                            // iako ocekuvame ova query da ni vrati 0 ili 1 quote, site queries vrakjaat
                            // lista od rezultati.
                            List<Quote> quotes = task.getResult().toObjects(Quote.class);
                            // proveruvame dali listata e prazna. Ako e prazna toa znaci deka quotot ne postoi
                            // vo quotes kolekcijata, odnosno prethodno nieden user go nema dodadeno vo favorites
                            if (quotes.isEmpty()) {
                                // dodadigo quotot vo quotes kolekcijata
                                quotesRef.add(quote).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                                    @Override
                                    public void onComplete(@NonNull Task<DocumentReference> task) {
                                        if (task.isSuccessful()) {
                                            // ako uspesno e dodaden quotot vo quotes kolekcijata,
                                            // referencata od noviot dodaden quote (task.getResult)
                                            // stavija vo userot

                                            // star kod od predavanje
//                                            Map<String, Object> userMap = new HashMap<>();
//                                            userMap.put("name", user.getName());
//                                            userMap.put("favoriteQuote", task.getResult());
//                                            userRef.set(userMap);
                                            // nov kod, malku podobar nacin
                                            // azuriraj go favoriteQuote poleto vo userot
                                            userRef.update("favoriteQuote", task.getResult());
                                        }
                                    }
                                });
                            } else {
                                // star kod od predavanje
//                                Map<String, Object> userMap = new HashMap<>();
//                                userMap.put("name", user.getName());
//                                userMap.put("favoriteQuote", task.getResult().getDocuments().get(0).getReference());
//                                userRef.set(userMap);
                                // nov kod, zemija referencata od postoeckiot quote
                                DocumentReference reference = task.getResult().getDocuments().get(0).getReference();
                                // azuriraj go favoriteQuote poleto vo userot
                                userRef.update("favoriteQuote", reference);
                            }

                        } else {

                        }
                    }
                });
            }
        });
    }

    @Override
    public int getItemCount() {
        return quotesList.size();

    }

    public static class QuotesViewHolder extends RecyclerView.ViewHolder {
        TextView tvQuote;
        TextView tvRating;
        TextView tvAuthor;
        ImageButton ibFavorites;

        public QuotesViewHolder(@NonNull View itemView) {
            super(itemView);
            tvQuote = itemView.findViewById(R.id.en_textview);
            tvAuthor = itemView.findViewById(R.id.author_textview);
            tvRating = itemView.findViewById(R.id.rating_textview);
            ibFavorites = itemView.findViewById(R.id.ibFavorite);
        }
    }
}
