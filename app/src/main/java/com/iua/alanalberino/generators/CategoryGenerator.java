package com.iua.alanalberino.generators;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.SharedPreferences;
import com.iua.alanalberino.async.DownloadFilesTask;
import com.iua.alanalberino.model.Category;

import com.iua.alanalberino.persistence.CategoriesRepository;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import static android.content.Context.MODE_PRIVATE;

public class CategoryGenerator {

    ArrayList<Category> categories;
    Application app;
    private static final String PREFS_NAME ="com.nicolasfanin.IUASampleApp.prefs";
    SharedPreferences sharedPreferences;

    public CategoryGenerator(Application app) {
        this.app = app;
    }

    @SuppressLint("ResourceType")
    public ArrayList<Category> initCategory() throws ExecutionException, InterruptedException, JSONException {

        categories = new ArrayList<Category>();
        CategoriesRepository categoriesRepository = new CategoriesRepository(app);

        String myUrl = "https://api.themoviedb.org/3/genre/movie/list?api_key=ef7cd5f6820e2df3368478f9ecb07597&language=es-MX";
        DownloadFilesTask getRequest = new DownloadFilesTask();
        String result = getRequest.execute(myUrl).get();

        JSONArray categorias = new JSONObject(result).getJSONArray("genres");

        sharedPreferences  = app.getApplicationContext().getSharedPreferences(PREFS_NAME,MODE_PRIVATE);
        String desiredCats = sharedPreferences.getString("DesiredCats", "");

        for(int i=0; i<categorias.length();i++){

            JSONObject categoria = categorias.getJSONObject(i);
            int id = categoria.getInt("id");
            try{
                Category cat = new Category(id, categoria.getString("name"), new MovieGenerator(app).generateMovies(id));
                if(desiredCats.compareTo("")==0 || desiredCats.indexOf(cat.getNombreCategoria())>=0){
                    categories.add(cat);
                    categoriesRepository.addCategory(cat);
                }
            }catch (Exception e){
                e.printStackTrace();
            }

        }
        return categories;

    }
}


