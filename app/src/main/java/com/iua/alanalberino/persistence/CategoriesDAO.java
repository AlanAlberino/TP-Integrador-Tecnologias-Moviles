package com.iua.alanalberino.persistence;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.iua.alanalberino.model.Category;

import java.util.List;

@Dao
public interface CategoriesDAO {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void addCategory(Category category);

    @Query("SELECT * FROM categories ORDER BY nombreCategoria")
    List<Category> getCategories();

}
