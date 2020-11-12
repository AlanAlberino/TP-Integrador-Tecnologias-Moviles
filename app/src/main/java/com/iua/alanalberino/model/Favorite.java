package com.iua.alanalberino.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "favorites")
public class Favorite {

    @PrimaryKey(autoGenerate = true)
    private int id;
    private Long userID;
    private Long movieID;

    public Favorite(Long userID, Long movieID) {
        this.userID = userID;
        this.movieID = movieID;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Long getUserID() {
        return userID;
    }

    public void setUserID(Long userID) {
        this.userID = userID;
    }

    public Long getMovieID() {
        return movieID;
    }

    public void setMovieID(Long movieID) {
        this.movieID = movieID;
    }
}
