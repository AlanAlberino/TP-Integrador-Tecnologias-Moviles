package com.iua.alanalberino.model;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.iua.alanalberino.model.Movie;

import java.util.ArrayList;


@Entity(tableName = "categories")
public class Category {

    @PrimaryKey
    private int id;
    private String nombreCategoria;
    @Ignore
    private ArrayList<Movie> movies;

    public Category(int id, String nombreCategoria) {
        this.id = id;
        this.nombreCategoria = nombreCategoria;
    }

    public Category(int id, String nombreCategoria, ArrayList<Movie> movies) {
        this.id = id;
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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
