package com.iua.alanalberino.persistence;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.iua.alanalberino.model.Favorite;

import java.util.List;

@Dao
public interface FavoritesDAO {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void markAsFavorite(Favorite favorite);

    @Query("SELECT * FROM favorites WHERE userID == :userID")
    List<Favorite> getUserFavorites(Long userID);

    @Query("SELECT * from favorites WHERE userID == :userID AND movieID  == :movieID")
    Favorite isMarkedAsFavorite(Long userID, Long movieID);

    @Query("DELETE FROM favorites WHERE userID == :userID AND movieID  == :movieID")
    void removeAsFavorite(Long userID, Long movieID);

    @Query("DELETE FROM favorites")
    void deleteAll();


}
