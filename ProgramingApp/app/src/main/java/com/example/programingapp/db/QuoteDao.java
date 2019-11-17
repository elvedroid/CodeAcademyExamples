package com.example.programingapp.db;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.programingapp.model.Quote;

import java.util.List;

@Dao
public interface QuoteDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertQuotes(List<Quote> quotes);

    @Query("SELECT * FROM Quote")
    List<Quote> getAllQuotes();

    @Query("SELECT * FROM Quote WHERE author LIKE :author")
    List<Quote> getQuotesByAuthor(String author);

    @Query("SELECT * FROM Quote WHERE author LIKE :author AND numberOfVotes > :numberOfVotes")
    List<Quote> getQuotesByAuthorAndNumOfVotes(String author, int numberOfVotes);

    @Query("SELECT * FROM Quote WHERE id LIKE :id LIMIT 1")
    Quote getQuoteById(String id);

    @Query("DELETE FROM Quote")
    void clearQuotes();

}
