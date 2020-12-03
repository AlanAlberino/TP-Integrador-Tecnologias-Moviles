package com.iua.alanalberino.generators;

import android.app.Application;
import android.content.SharedPreferences;
import android.util.Log;

import com.iua.alanalberino.Constantes;
import com.iua.alanalberino.async.DownloadFilesTask;
import com.iua.alanalberino.model.Movie;
import com.iua.alanalberino.persistence.MoviesRepository;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import static android.content.Context.MODE_PRIVATE;

public class MovieGenerator {

    ArrayList<Movie> movies;
    Application app;
    private SharedPreferences sharedPreferences;

    public MovieGenerator(Application app) {
        this.app = app;
        sharedPreferences = app.getApplicationContext().getSharedPreferences(Constantes.PREFS_NAME, MODE_PRIVATE);
    }

    public ArrayList<Movie> generateMovies(int cat) throws ExecutionException, InterruptedException, JSONException {
        movies = new ArrayList<Movie>();
        MoviesRepository moviesRepository = new MoviesRepository(app);
        String myUrl = "https://api.themoviedb.org/3/discover/movie?api_key=ef7cd5f6820e2df3368478f9ecb07597&with_genres="+cat+"&language=es-MX";

        DownloadFilesTask getRequest = new DownloadFilesTask();
        String result = getRequest.execute(myUrl).get();

        JSONArray peliculas = new JSONObject(result).getJSONArray("results");
            for(int i=0; i<peliculas.length();i++){

                try{
                    JSONObject pelicula = peliculas.getJSONObject(i);
                    Movie movie = generarPelicula(pelicula, cat);
                    movies.add(movie);
                    moviesRepository.addMovie(movie);
                }
                catch (JSONException e){
                    throw e;
                }

            }

        return movies;
    }

    public ArrayList<Movie> searchMovies(String textToSearch) throws ExecutionException, InterruptedException, JSONException {
        movies = new ArrayList<Movie>();

        String myUrl = "https://api.themoviedb.org/3/search/movie?api_key=ef7cd5f6820e2df3368478f9ecb07597&language=es-MX&query="+textToSearch+"&page=1&include_adult=false";

        DownloadFilesTask getRequest = new DownloadFilesTask();
        String result = getRequest.execute(myUrl).get();

        JSONArray peliculas = new JSONObject(result).getJSONArray("results");
        for(int i=0; i<peliculas.length();i++){
            try{
                movies.add(generarPelicula(peliculas.getJSONObject(i), 0));
            }catch (JSONException e){
                e.printStackTrace();
            }
        }

        return movies;
    }

    private Movie generarPelicula(JSONObject pelicula, int cat) throws JSONException {

        String cats = "";

        try{
            JSONArray categorias = pelicula.getJSONArray("genres");
            for(int j=0; j<categorias.length();j++){
                JSONObject cate = categorias.getJSONObject(j);
                if(cats.compareTo("") !=0 ){
                    cats += ", ";
                }
                cats += cate.getString("name");
            }
        }catch (JSONException e){
        }

        String fecha = pelicula.getString("release_date");

        String duracion = "Duracion";

        try{
            duracion = pelicula.getString("runtime");
            // Conversión de minutos (Ej: 98 minutos) a horas y minutos (1h 38m)
            duracion = (int) Math.floor(Integer.parseInt(duracion)/60) +"h "+Integer.parseInt(duracion)%60+"m";
            // Conversion de YYYY-MM-DD a DD/MM/YYYY
            fecha = fecha.split("-")[2]+"/"+fecha.split("-")[1]+"/"+fecha.split("-")[0];
        }
        catch(Exception e){
        }

        return new Movie(pelicula.getInt("id"), pelicula.getString("title"), "https://image.tmdb.org/t/p/w220_and_h330_face" + pelicula.getString("poster_path"),"https://image.tmdb.org/t/p/w533_and_h300_bestv2" + pelicula.getString("backdrop_path"), fecha, cats, duracion, pelicula.getString("overview"),cat);

    }

    public Movie getPeliculaByID(int id) throws ExecutionException, InterruptedException, JSONException {
        String myUrl = "https://api.themoviedb.org/3/movie/"+id+"?api_key=ef7cd5f6820e2df3368478f9ecb07597&language=es-MX";
        DownloadFilesTask getRequest = new DownloadFilesTask();
        String result = getRequest.execute(myUrl).get();
        return generarPelicula(new JSONObject(result), 0);
    }

    public ArrayList<Movie> getNowPlaying(String countryCode) throws JSONException, ExecutionException, InterruptedException{
        movies = new ArrayList<Movie>();

        String myUrl = "https://api.themoviedb.org/3/movie/now_playing?api_key=ef7cd5f6820e2df3368478f9ecb07597&language=en-US&page=1&region="+countryCode;

        DownloadFilesTask getRequest = new DownloadFilesTask();
        String result = getRequest.execute(myUrl).get();

        JSONArray peliculas = new JSONObject(result).getJSONArray("results");
        for(int i=0; i<peliculas.length();i++){
            try{
                movies.add(generarPelicula(peliculas.getJSONObject(i), 0));
            }catch (JSONException e){
                e.printStackTrace();
            }
        }

        return movies;
    }

    public ArrayList<Movie> getPopular(String countryCode) throws JSONException, ExecutionException, InterruptedException {
        movies = new ArrayList<Movie>();

        String myUrl = "https://api.themoviedb.org/3/movie/popular?api_key=ef7cd5f6820e2df3368478f9ecb07597&language=en-US&page=1&region="+countryCode;

        DownloadFilesTask getRequest = new DownloadFilesTask();
        String result = getRequest.execute(myUrl).get();

        JSONArray peliculas = new JSONObject(result).getJSONArray("results");
        for(int i=0; i<peliculas.length();i++){
            try{
                movies.add(generarPelicula(peliculas.getJSONObject(i), 0));
            }catch (JSONException e){
                e.printStackTrace();
            }
        }

        return movies;
    }

    public ArrayList<Movie> getFavorites() throws JSONException, ExecutionException, InterruptedException {
        movies = new ArrayList<Movie>();
        String sessionId = sharedPreferences.getString(Constantes.SESSION_ID, "");
        int userId = sharedPreferences.getInt(Constantes.USER_ID, 0);

        String myUrl = "https://api.themoviedb.org/3/account/"+userId+"/favorite/movies?api_key=ef7cd5f6820e2df3368478f9ecb07597&session_id="+sessionId+"&language=es-MX&sort_by=created_at.asc&page=1";
        Log.i("MovieGenerator", "URL: "+"https://api.themoviedb.org/3/account/"+userId+"/favorite/movies?api_key=ef7cd5f6820e2df3368478f9ecb07597&session_id="+sessionId+"&language=es-MX&sort_by=created_at.asc&page=1");
        DownloadFilesTask getRequest = new DownloadFilesTask();
        String result = getRequest.execute(myUrl).get();

        JSONArray peliculas = new JSONObject(result).getJSONArray("results");
        for(int i=0; i<peliculas.length();i++){
            try{
                movies.add(generarPelicula(peliculas.getJSONObject(i), 0));
            }catch (JSONException e){
                e.printStackTrace();
            }
        }


        return movies;
    }

}


