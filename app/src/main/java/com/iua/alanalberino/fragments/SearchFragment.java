package com.iua.alanalberino.fragments;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.iua.alanalberino.model.Movie;
import com.iua.alanalberino.generators.MovieGenerator;
import com.iua.alanalberino.adapters.MovieSearchAdapter;
import com.iua.alanalberino.R;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class SearchFragment extends Fragment {

    private RecyclerView recyclerView;
    MovieSearchAdapter movieSearchAdapter;
    private ArrayList<Movie> movies;
    LinearLayoutManager manager;

    public SearchFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {

        final MovieGenerator movieGenerator = new MovieGenerator(getActivity().getApplication());
        movies = new ArrayList<Movie>();

        movieSearchAdapter = new MovieSearchAdapter(movies, getActivity().getApplicationContext(), getFragmentManager(), getId());
        manager = new LinearLayoutManager(getActivity().getApplicationContext());

        recyclerView = getView().findViewById(R.id.recyclerViewSearch);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(movieSearchAdapter);

        EditText searchEditText = (EditText) getActivity().findViewById(R.id.editTextSearch);

        searchEditText.addTextChangedListener(new TextWatcher() {
        List<Movie> listaFiltrada = new ArrayList<Movie>();
            public void afterTextChanged(Editable s) {
                try {
                    listaFiltrada = movieGenerator.searchMovies(s.toString().toLowerCase());
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                movieSearchAdapter.cambiarLista(listaFiltrada);
                recyclerView.setAdapter(movieSearchAdapter);
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            public void onTextChanged(CharSequence s, int start, int before, int count) {}
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_search, container, false);
    }
}