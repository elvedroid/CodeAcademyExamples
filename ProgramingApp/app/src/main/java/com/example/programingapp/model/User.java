package com.example.programingapp.model;

public class User {
    private String name;
    private Quote favoriteQuote;

    public User() {
    }

    public User(String name, Quote favoriteQuote) {
        this.name = name;
        this.favoriteQuote = favoriteQuote;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Quote getFavoriteQuote() {
        return favoriteQuote;
    }

    public void setFavoriteQuote(Quote favoriteQuote) {
        this.favoriteQuote = favoriteQuote;
    }
}
