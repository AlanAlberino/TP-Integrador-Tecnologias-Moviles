package com.iua.alanalberino;

import android.annotation.SuppressLint;

import java.util.ArrayList;

public class MovieGenerator {

    ArrayList<Movie> movies;

    @SuppressLint("ResourceType")
    public ArrayList<Movie> initMovies1(MainActivity mainActivity) {

        movies = new ArrayList<Movie>();
        movies.add(new Movie("Dark", "https://image.tmdb.org/t/p/w220_and_h330_face/5LoHuHWA4H8jElFlZDvsmU2n63b.jpg"));
        movies.add(new Movie("The Joker", "https://image.tmdb.org/t/p/w220_and_h330_face/v0eQLbzT6sWelfApuYsEkYpzufl.jpg"));
        movies.add(new Movie("Rick & Morty", "https://image.tmdb.org/t/p/w220_and_h330_face/g1Tcm9RRlSs2twIwIa0UqWEOP7b.jpg"));
        movies.add(new Movie("Lucifer", "https://image.tmdb.org/t/p/w220_and_h330_face/1sBx2Ew4WFsa1YY32vlHt079O03.jpg"));
        movies.add(new Movie("Riverdale", "https://image.tmdb.org/t/p/w220_and_h330_face/6zBWSuYW3Ps1nTfeMS8siS4KUaA.jpg"));
        movies.add(new Movie("Frozen II", "https://image.tmdb.org/t/p/w220_and_h330_face/jnFCk7qGGWop2DgfnJXeKLZFuBq.jpg"));
        movies.add(new Movie("Avengers: Endgame", "https://image.tmdb.org/t/p/w220_and_h330_face/br6krBFpaYmCSglLBWRuhui7tPc.jpg"));
        movies.add(new Movie("La Casa De Papel", "https://image.tmdb.org/t/p/w220_and_h330_face/4LjPjtfaxEn2W61ORPeytr5Qq7j.jpg"));
        return movies;
    }

    @SuppressLint("ResourceType")
    public ArrayList<Movie> initMovies2(MainActivity mainActivity) {

        movies = new ArrayList<Movie>();
        movies.add(new Movie("Mulan", "https://image.tmdb.org/t/p/w220_and_h330_face/rd7ElSRYhN2CFqMcyH8rqrwLbd6.jpg"));
        movies.add(new Movie("The Babysitter: Killer Queen", "https://image.tmdb.org/t/p/w220_and_h330_face/bcse23Sim126hRjZJEY4Yx03H9r.jpg"));
        movies.add(new Movie("Pets United", "https://image.tmdb.org/t/p/w220_and_h330_face/gtwqIYSOCRwEndZTg9s6iWjEZc1.jpg"));
        movies.add(new Movie("The Boys", "https://image.tmdb.org/t/p/w220_and_h330_face/dzOxNbbz1liFzHU1IPvdgUR647b.jpg"));
        movies.add(new Movie("Raised By Wolves", "https://image.tmdb.org/t/p/w220_and_h330_face/mTvSVKMn2Npf6zvYNbGMJnYLtvp.jpg"));
        movies.add(new Movie("Tenet", "https://image.tmdb.org/t/p/w220_and_h330_face/oh8XmxWlySHgGLlx8QOBmq9k72j.jpg"));
        movies.add(new Movie("The Social Dillema", "https://image.tmdb.org/t/p/w220_and_h330_face/VAXS2hbKwCKd3DEz57Cr6ch61s.jpg"));
        movies.add(new Movie("PowerBook II: Ghost", "https://image.tmdb.org/t/p/w220_and_h330_face/jAjIzf0hQwWVoBo80rnGUdKccTO.jpg"));
        return movies;
    }

}


