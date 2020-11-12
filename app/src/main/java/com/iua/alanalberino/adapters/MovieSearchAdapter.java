package com.iua.alanalberino.adapters;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.iua.alanalberino.R;
import com.iua.alanalberino.fragments.MovieFragment;
import com.iua.alanalberino.model.Movie;

import java.util.List;

public class MovieSearchAdapter extends RecyclerView.Adapter<MovieSearchAdapter.SearchViewHolder> {
    private List<Movie> data;
    private Context context;
    FragmentManager fragmentManager;
    int idFragment;

    public MovieSearchAdapter(@NonNull List<Movie> data, Context context, FragmentManager fragmentManager, int idFragment) {
        this.data = data;
        this.context = context;
        this.fragmentManager = fragmentManager;
        this.idFragment = idFragment;
    }

    @NonNull
    @Override
    public SearchViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View row = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.pelicula_busqueda, viewGroup, false);
        return new SearchViewHolder(row);
    }

    @Override
    public void onBindViewHolder(@NonNull SearchViewHolder searchViewHolder, int i) {
        Movie movie = data.get(i);
        String title = movie.getTitle();
        String description = movie.getDescripcion();

        searchViewHolder.getTitleTextView().setText(title);
        searchViewHolder.getTextViewDescriptionSearch().setText(description);
        Glide.with(context)
                .load(movie.getVerticalImageURL())
                .fitCenter()
                .into(searchViewHolder.getImageView());
        final int idPelicula = movie.getId();
        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MovieFragment mf = new MovieFragment(idPelicula, context);
                FragmentTransaction transaction = fragmentManager.beginTransaction();
                transaction.replace(idFragment, mf);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        };

        searchViewHolder.getImageView().setOnClickListener(listener);
        searchViewHolder.getTitleTextView().setOnClickListener(listener);
        searchViewHolder.getTextViewDescriptionSearch().setOnClickListener(listener);
    }


    @Override
    public int getItemCount() {
        return data.size();
    }

    public class SearchViewHolder extends RecyclerView.ViewHolder {
        private TextView titleTextView;
        private ImageView imageView;
        private TextView textViewDescriptionSearch;

        public SearchViewHolder(View itemView) {
            super(itemView);
            titleTextView = (TextView) itemView.findViewById(R.id.tituloTextView);
            imageView = (ImageView) itemView.findViewById(R.id.portadaInicio);
            textViewDescriptionSearch = (TextView) itemView.findViewById(R.id.textViewDescriptionSearch);

        }

        public TextView getTitleTextView() {
            return titleTextView;
        }

        public ImageView getImageView() {
            return imageView;
        }

        public TextView getTextViewDescriptionSearch() {
            return textViewDescriptionSearch;
        }
    }

    public void cambiarLista(List<Movie> data){
        this.data=data;
    }
}

