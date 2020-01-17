package com.example.firstunittestapp;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoRule;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class MovieRepoTest {

    @Mock
    public RetrofitApi retrofitApi;

    @Rule
    public MockitoRule mockitoRule;

    public MovieRepo movieRepo;

    @Before
    public void setUp() throws Exception {
        movieRepo = new MovieRepo(retrofitApi);
    }

    @Test
    public void testMoviesNonEmptyList() {
        List<String> movies = new ArrayList<>();
        movies.add("Movie 1");

        when(retrofitApi.getMovies()).thenReturn(movies);

        int size = movieRepo.getMoviesLength();
        assertEquals(size, 1);
    }

}