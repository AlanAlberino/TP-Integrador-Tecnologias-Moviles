package com.iua.alanalberino.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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

public class FavoriteFragment extends Fragment {

    private RecyclerView recyclerView;
    MovieSearchAdapter movieSearchAdapter;
    private ArrayList<Movie> movies;
    LinearLayoutManager manager;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {

        FavoritesRepository favoritesRepository = new FavoritesRepository(getActivity().getApplication());
        UserRepository userRepository = new UserRepository(getActivity().getApplication());
        MovieGenerator movieGenerator = new MovieGenerator(getActivity().getApplication());
        try {
            List<Favorite> favoritos = favoritesRepository.getUserFavorites(userRepository.getLoggedUser().getId());
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