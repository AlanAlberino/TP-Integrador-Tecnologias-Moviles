package com.iua.alanalberino.fragments;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.iua.alanalberino.model.Category;
import com.iua.alanalberino.adapters.CategoryAdapter;
import com.iua.alanalberino.generators.CategoryGenerator;
import com.iua.alanalberino.R;
import com.iua.alanalberino.model.Movie;
import com.iua.alanalberino.persistence.CategoriesRepository;
import com.iua.alanalberino.persistence.MoviesRepository;

import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class HomeFragment extends Fragment {

    private RecyclerView recyclerView;
    private CategoryAdapter categoryAdapter;
    private ArrayList<Category> categories;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {


        if(!isNetworkConnected()){
            CategoriesRepository categoriesRepository = new CategoriesRepository(getActivity().getApplication());
            try {

                categories = (ArrayList<Category>) categoriesRepository.getCategories();
                MoviesRepository moviesRepository = new MoviesRepository(getActivity().getApplication());
                for(Category category: categories){
                    category.setMovies((ArrayList<Movie>) moviesRepository.getMovies(category.getId()));
                }
            } catch (ExecutionException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            NoInternetDialogFragment noInternetDialogFragment = new NoInternetDialogFragment();
            noInternetDialogFragment.show(getParentFragmentManager(), "noInternetDialogFragment");
        }
        else{
            CategoryGenerator categoryGenerator = new CategoryGenerator(getActivity().getApplication(), getActivity());
            try {
                categories = categoryGenerator.initCategory();
            } catch (ExecutionException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        if(categories==null){
            NoUpdateDialogFragment noUpdateDialogFragment = new NoUpdateDialogFragment();
            noUpdateDialogFragment.show(getParentFragmentManager(), "noUpdateDialogFragment");
        }else {
            categoryAdapter = new CategoryAdapter(categories, getActivity().getApplicationContext(), getFragmentManager(), this.getId());
            LinearLayoutManager manager = new LinearLayoutManager(getActivity().getApplicationContext());

            recyclerView = getView().findViewById(R.id.recyclerView);
            recyclerView.setLayoutManager(manager);
            recyclerView.setAdapter(categoryAdapter);
        }

        View.OnClickListener sonicListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MovieFragment mf = new MovieFragment(454626, getContext());
                FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
                transaction.replace(getId(), mf);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        };
        getView().findViewById(R.id.button2).setOnClickListener(sonicListener);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    private boolean isNetworkConnected() {
        ConnectivityManager connectivityManager = (ConnectivityManager)
                getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isConnected();
    }
}