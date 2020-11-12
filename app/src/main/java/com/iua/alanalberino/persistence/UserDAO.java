package com.iua.alanalberino.persistence;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.iua.alanalberino.model.User;

@Dao
public interface UserDAO {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(User user);

    @Query("SELECT * FROM users WHERE userName == :userName")
    User getUserByUserName(String userName);

    @Query("DELETE FROM users")
    void deleteAll();

    @Query("UPDATE users SET 'isLogged'=1 WHERE userName == :userName")
    void setUserAsLoggedIn(String userName);

    @Query("UPDATE users SET 'isLogged'=0 WHERE userName == :userName")
    void setUserAsLoggedOut(String userName);

    @Query("SELECT * FROM users WHERE isLogged=1")
    User getLoggedUser();

}
