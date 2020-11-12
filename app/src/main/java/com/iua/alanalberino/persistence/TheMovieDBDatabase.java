package com.iua.alanalberino.persistence;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.iua.alanalberino.model.Category;
import com.iua.alanalberino.model.Favorite;
import com.iua.alanalberino.model.Movie;
import com.iua.alanalberino.model.User;

@Database(entities = {User.class, Favorite.class, Movie.class, Category.class}, version = 3)
public abstract class TheMovieDBDatabase extends RoomDatabase {
    public abstract UserDAO userDAO();
    public abstract FavoritesDAO favoritesDAO();
    public abstract MoviesDAO moviesDAO();
    public abstract CategoriesDAO categoriesDAO();

    private static volatile TheMovieDBDatabase INSTANCE;

    static TheMovieDBDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (TheMovieDBDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            TheMovieDBDatabase.class, "TheMovieDBDatabase").addMigrations(MIGRATION_1_2, MIGRATION_2_3).build();
                }
            }
        }
        return INSTANCE;
    }

    static final Migration MIGRATION_1_2 = new Migration(1, 2) {
        @Override
        public void migrate(SupportSQLiteDatabase database) {
            database.execSQL("CREATE TABLE `favorites` (" +
                    "`id` INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, "+
                    "`userID` INTEGER, " +
                    "`movieID` INTEGER)");
        }
    };


    static final Migration MIGRATION_2_3 = new Migration(2, 3) {
        @Override
        public void migrate(SupportSQLiteDatabase database) {
            database.execSQL("CREATE TABLE `movies` " +
                    "(`id` INTEGER NOT NULL, " +
                    "`categoriaID` INTEGER NOT NULL, " +
                    "`title` TEXT, " +
                    "`verticalImageURL` TEXT, " +
                    "`horizontalImageURL` TEXT," +
                    "`fechaLanzamiento` TEXT, " +
                    "`categorias` TEXT," +
                    "`duracion` TEXT," +
                    "`descripcion` TEXT," +
                    "PRIMARY KEY(`id`, `categoriaID`))");

            database.execSQL("CREATE TABLE `categories` " +
                    "(`id` INTEGER NOT NULL PRIMARY KEY, " +
                    "`nombreCategoria` TEXT)");
        }
    };
}
