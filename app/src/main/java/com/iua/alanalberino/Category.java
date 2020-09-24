package com.iua.alanalberino;

import java.util.ArrayList;

public class Category {

    public String nombreCategoria;
    public ArrayList<Movie> movies;

    public Category(String nombreCategoria, ArrayList<Movie> movies) {
        this.nombreCategoria = nombreCategoria;
        this.movies = movies;
    }

    public String getNombreCategoria() {
        return nombreCategoria;
    }

    public void setNombreCategoria(String nombreCategoria) {
        this.nombreCategoria = nombreCategoria;
    }

    public ArrayList<Movie> getMovies() {
        return movies;
    }

    public void setMovies(ArrayList<Movie> movies) {
        this.movies = movies;
    }
}
