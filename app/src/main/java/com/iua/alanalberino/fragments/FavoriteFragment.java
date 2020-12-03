package com.iua.alanalberino.fragments;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.iua.alanalberino.Constantes;
import com.iua.alanalberino.persistence.FavoritesRepository;
import com.iua.alanalberino.generators.MovieGenerator;
import com.iua.alanalberino.R;
import com.iua.alanalberino.persistence.UserRepository;
import com.iua.alanalberino.adapters.MovieSearchAdapter;
import com.iua.alanalberino.model.Favorite;
import com.iua.alanalberino.model.Movie;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import static android.content.Context.MODE_PRIVATE;

public class FavoriteFragment extends Fragment {

    private RecyclerView recyclerView;
    private MovieSearchAdapter movieSearchAdapter;
    private ArrayList<Movie> movies;
    private LinearLayoutManager manager;
    private SharedPreferences sharedPreferences;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {

        FavoritesRepository favoritesRepository = new FavoritesRepository(getActivity().getApplication());
        UserRepository userRepository = new UserRepository(getActivity().getApplication());
        MovieGenerator movieGenerator = new MovieGenerator(getActivity().getApplication());
        sharedPreferences = view.getContext().getSharedPreferences(Constantes.PREFS_NAME, MODE_PRIVATE);
        int userId = sharedPreferences.getInt(Constantes.USER_ID, 0);

        //Traigo los favoritos desde la API a traves del moviegenerator, y los guardo en la BD para mas simplicidad
        //Primero los borro para evitar que se repitan
        favoritesRepository.deleteAll();
        try {
            List<Movie> peliculasFavoritas = movieGenerator.getFavorites();
            for(Movie m: peliculasFavoritas){
                Log.i("FavoriteFragment", m.getTitle());
                favoritesRepository.markAsFavorite(new Favorite(new Long(userId), new Long(m.getId())));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


        try {
            List<Favorite> favoritos = favoritesRepository.getUserFavorites(new Long(userId));
            movies = new ArrayList<Movie>();

            for(Favorite favorite: favoritos){
                movies.add(movieGenerator.getPeliculaByID(Integer.parseInt(favorite.getMovieID().toString())));
            }

            movieSearchAdapter = new MovieSearchAdapter(movies, getActivity().getApplicationContext(), getFragmentManager(), getId());
            manager = new LinearLayoutManager(getActivity().getApplicationContext());

            recyclerView = getView().findViewById(R.id.recyclerViewSearch);
            recyclerView.setLayoutManager(manager);
            recyclerView.setAdapter(movieSearchAdapter);

        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_favorites, container, false);
    }
}