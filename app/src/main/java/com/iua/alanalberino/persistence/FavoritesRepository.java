package com.iua.alanalberino.persistence;

import android.app.Application;
import android.os.AsyncTask;

import com.iua.alanalberino.model.Favorite;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class FavoritesRepository {
    private FavoritesDAO favoritesDAO;
    private Favorite favorite;

    public FavoritesRepository(Application application) {
        TheMovieDBDatabase database = TheMovieDBDatabase.getDatabase(application);
        favoritesDAO = database.favoritesDAO();
    }

    public void markAsFavorite(Favorite favorite){
        new markAsFavoriteAsyncTask(favoritesDAO).execute(favorite);
    }

    public void removeAsFavorite(Favorite favorite){
        new removeAsFavoriteAsyncTask(favoritesDAO).execute(favorite);
    }

    public List<Favorite> getUserFavorites(Long id) throws ExecutionException, InterruptedException {
        return new getUserFavoritesAsyncTask(favoritesDAO).execute(id).get();
    }

    public Favorite isMarkedAsFavorite(Favorite favorite) throws ExecutionException, InterruptedException {
        return new isMarkedAsFavoriteAsyncTask(favoritesDAO).execute(favorite).get();
    }


    private static class markAsFavoriteAsyncTask extends AsyncTask<Favorite, Void, Void> {
        private FavoritesDAO asyncTaskFavoritesDAO;
        markAsFavoriteAsyncTask(FavoritesDAO favoritesDAO) { asyncTaskFavoritesDAO = favoritesDAO; }

        @Override
        protected Void doInBackground(Favorite... favorites) {
            asyncTaskFavoritesDAO.markAsFavorite(favorites[0]);
            return null;
        }
    }

    private static class removeAsFavoriteAsyncTask extends AsyncTask<Favorite, Void, Void> {
        private FavoritesDAO asyncTaskFavoritesDAO;
        removeAsFavoriteAsyncTask(FavoritesDAO favoritesDAO) { asyncTaskFavoritesDAO = favoritesDAO; }

        @Override
        protected Void doInBackground(Favorite... favorites) {
            asyncTaskFavoritesDAO.removeAsFavorite(favorites[0].getUserID(), favorites[0].getMovieID());
            return null;
        }
    }

    private static class getUserFavoritesAsyncTask extends AsyncTask<Long, Void, List<Favorite>> {
        private FavoritesDAO asyncTaskFavoritesDAO;
        getUserFavoritesAsyncTask(FavoritesDAO favoritesDAO) { asyncTaskFavoritesDAO = favoritesDAO; }

        @Override
        protected List<Favorite> doInBackground(Long... ids) {
            return asyncTaskFavoritesDAO.getUserFavorites(ids[0]);
        }
    }

    private static class isMarkedAsFavoriteAsyncTask extends AsyncTask<Favorite, Void, Favorite> {
        private FavoritesDAO asyncTaskFavoritesDAO;
        isMarkedAsFavoriteAsyncTask(FavoritesDAO favoritesDAO) { asyncTaskFavoritesDAO = favoritesDAO; }

        @Override
        protected Favorite doInBackground(Favorite... favorites) {
            return asyncTaskFavoritesDAO.isMarkedAsFavorite(favorites[0].getUserID(), favorites[0].getMovieID());
        }
    }

}
