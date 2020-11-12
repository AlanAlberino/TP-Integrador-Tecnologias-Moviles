package com.iua.alanalberino.persistence;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.iua.alanalberino.model.Movie;

import java.util.List;

@Dao
public interface MoviesDAO {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void addMovie(Movie movie);

    @Query("SELECT * FROM movies where categoriaID = :categoriaID")
    List<Movie> getMovies(int categoriaID);

}
