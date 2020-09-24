package com.iua.alanalberino;

import android.annotation.SuppressLint;

import java.util.ArrayList;

public class CategoryGenerator {

    ArrayList<Category> categories;

    @SuppressLint("ResourceType")
    public ArrayList<Category> initCategory(MainActivity mainActivity) {

        categories = new ArrayList<Category>();

        MovieGenerator movieGenerator = new MovieGenerator();
        ArrayList<Movie> movies = movieGenerator.initMovies1(mainActivity);
        ArrayList<Movie> movies2 = movieGenerator.initMovies2(mainActivity);

        categories.add(new Category("Recomendadas:", movies));
        categories.add(new Category("Estrenos:", movies2));
        categories.add(new Category("Recomendadas:", movies));
        categories.add(new Category("Estrenos:", movies2));
        return categories;

    }
}


