package com.iua.alanalberino.fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.iua.alanalberino.Constantes;
import com.iua.alanalberino.activities.MainActivity;
import com.iua.alanalberino.async.DownloadFilesTask;
import com.iua.alanalberino.model.Favorite;
import com.iua.alanalberino.persistence.FavoritesRepository;
import com.iua.alanalberino.model.Movie;
import com.iua.alanalberino.generators.MovieGenerator;
import com.iua.alanalberino.R;
import com.iua.alanalberino.persistence.UserRepository;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import static android.content.Context.MODE_PRIVATE;

public class MovieFragment extends Fragment {

    private SharedPreferences sharedPreferences;
    private RequestQueue requestQueue;
    private int id;
    Context context;

    public MovieFragment(int id, Context context) {
        this.id = id;
        this.context = context;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        try {
            sharedPreferences = view.getContext().getSharedPreferences(Constantes.PREFS_NAME, MODE_PRIVATE);
            requestQueue = Volley.newRequestQueue(getActivity().getApplicationContext());
            MovieGenerator movieGenerator = new MovieGenerator(getActivity().getApplication());
            final Movie movie = movieGenerator.getPeliculaByID(id);
            TextView titleTextView = (TextView) view.findViewById(R.id.textView18);
            ImageView imageView = (ImageView) view.findViewById(R.id.imageView15);
            TextView infoTextView = (TextView) view.findViewById(R.id.textView19);
            TextView descripcionTextView = (TextView) view.findViewById(R.id.textView20);
            titleTextView.setText(movie.getTitle());
            infoTextView.setText(movie.getFechaLanzamiento() + " - " + movie.getCategorias() + " - " + movie.getDuracion());
            descripcionTextView.setText(movie.getDescripcion());
            Glide.with(context)
                    .load(movie.getHorizontalImageURL())
                    .fitCenter()
                    .into(imageView);

            final UserRepository userRepository = new UserRepository(getActivity().getApplication());
            final FavoritesRepository favoritesRepository = new FavoritesRepository(getActivity().getApplication());
            final Favorite isFavorite = favoritesRepository.isMarkedAsFavorite(new Favorite((long) sharedPreferences.getInt(Constantes.USER_ID, 0), (long) movie.getId()));
            final ImageView star = (ImageView) view.findViewById(R.id.imageView16);
            //Si está marcado como favoritos, cambio la estrella apagada a la estrella encendida

                if(isFavorite!=null){
                    star.setImageDrawable(getResources().getDrawable(android.R.drawable.btn_star_big_on));
            }

            //Si toco la estrella, marcar o desmarcar como favorito y cambiar el tipo de estrella

            View.OnClickListener listener = new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(isFavorite!=null){
                        favoritesRepository.removeAsFavorite(new Favorite((long) sharedPreferences.getInt(Constantes.USER_ID, 0), (long) movie.getId()));
                        star.setImageDrawable(getResources().getDrawable(android.R.drawable.btn_star_big_off));
                        try {
                            quitarFavoritoAPI((long) movie.getId());
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    else{
                        favoritesRepository.markAsFavorite(new Favorite((long) sharedPreferences.getInt(Constantes.USER_ID, 0), (long) movie.getId()));
                        star.setImageDrawable(getResources().getDrawable(android.R.drawable.btn_star_big_on));
                        try {
                            agregarFavoritoAPI((long) movie.getId());
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                }
            };

            star.setOnClickListener(listener);

        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }




    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.movie_fragment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //mViewModel = ViewModelProviders.of(this).get(MovieViewModel.class);
        // TODO: Use the ViewModel
    }

    private void quitarFavoritoAPI(final long idPelicula) throws JSONException {

        int userId = sharedPreferences.getInt(Constantes.USER_ID, 0);
        String sessionId = sharedPreferences.getString(Constantes.SESSION_ID, "");

        String url = "https://api.themoviedb.org/3/account/"+userId+"/favorite?api_key=ef7cd5f6820e2df3368478f9ecb07597&session_id="+sessionId;
        final JSONObject jsonBody = new JSONObject("{\"media_type\":\"movie\",\"media_id\":"+idPelicula+",\"favorite\":false }");
        JsonObjectRequest req = new JsonObjectRequest(url, jsonBody,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            Log.i("MovieFragment", "Favorito quitado");
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        });
        requestQueue.add(req);
    }

    private void agregarFavoritoAPI(final long idPelicula) throws JSONException {

        int userId = sharedPreferences.getInt(Constantes.USER_ID, 0);
        String sessionId = sharedPreferences.getString(Constantes.SESSION_ID, "");

        String url = "https://api.themoviedb.org/3/account/"+userId+"/favorite?api_key=ef7cd5f6820e2df3368478f9ecb07597&session_id="+sessionId;
        final JSONObject jsonBody = new JSONObject("{\"media_type\":\"movie\",\"media_id\":"+idPelicula+",\"favorite\":true }");
        JsonObjectRequest req = new JsonObjectRequest(url, jsonBody,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            Log.i("MovieFragment", "Favorito quitado");
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        });
        requestQueue.add(req);
    }



}