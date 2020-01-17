package com.example.firstunittestapp;

import java.util.List;

public class MovieRepo {

    RetrofitApi retrofitApi;

    public MovieRepo(RetrofitApi retrofitApi) {
        this.retrofitApi = retrofitApi;
    }

    public int getMoviesLength() {
        List<String> movies = retrofitApi.getMovies();
        return movies.size();
    }
}
