package com.iua.alanalberino.persistence;

import android.app.Application;
import android.os.AsyncTask;
import com.iua.alanalberino.model.Movie;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class MoviesRepository {
    private MoviesDAO moviesDAO;
    private Movie movie;

    public MoviesRepository(Application application) {
        TheMovieDBDatabase database = TheMovieDBDatabase.getDatabase(application);
        moviesDAO = database.moviesDAO();
    }

    public void addMovie(Movie movie){
        new addMovieAsyncTask(moviesDAO).execute(movie);
    }

    public List<Movie> getMovies(Integer cat) throws ExecutionException, InterruptedException {
        return new getMoviesAsyncTask(moviesDAO).execute(cat).get();
    }

    private static class addMovieAsyncTask extends AsyncTask<Movie, Void, Void> {
        private MoviesDAO asyncTaskMoviesDAO;
        addMovieAsyncTask(MoviesDAO moviesDAO) { asyncTaskMoviesDAO = moviesDAO; }

        @Override
        protected Void doInBackground(Movie... movies) {
            asyncTaskMoviesDAO.addMovie(movies[0]);
            return null;
        }
    }

    private static class getMoviesAsyncTask extends AsyncTask<Integer, Void, List<Movie>> {
        private MoviesDAO asyncTaskMoviesDAO;
        getMoviesAsyncTask(MoviesDAO moviesDAO) { asyncTaskMoviesDAO = moviesDAO; }

        @Override
        protected List<Movie> doInBackground(Integer... ints) {
            return asyncTaskMoviesDAO.getMovies(ints[0]);
        }
    }

}
