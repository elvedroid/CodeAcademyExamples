package com.example.mybookstore;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView rvBooks = findViewById(R.id.rvBooks);
        LinearLayoutManager llManager = new LinearLayoutManager(this);
        rvBooks.setLayoutManager(llManager);
        BooksAdapter adapter = new BooksAdapter(createBooks());
        rvBooks.setAdapter(adapter);
    }

    private List<Book> createBooks(){
        List<Book> books = new ArrayList<>();

        books.add(new Book("War and Peace", "Leo Tolstoy"));
        books.add(new Book("Stranger", "Albert Camino"));
        books.add(new Book("Song of Solomon", "Tony Morrison"));

        return books;
    }
}
