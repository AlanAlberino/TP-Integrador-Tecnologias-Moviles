package com.iua.alanalberino;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

public class MovieSearchAdapter extends RecyclerView.Adapter<MovieSearchAdapter.SearchViewHolder> {
    private List<Movie> data;
    private Context context;

    public MovieSearchAdapter(@NonNull List<Movie> data, Context context) {
        this.data = data;
        this.context = context;
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

        searchViewHolder.getTitleTextView().setText(title);
        Glide.with(context)
                .load(movie.getImageURL())
                .fitCenter()
                .into(searchViewHolder.getImageView());
    }


    @Override
    public int getItemCount() {
        return data.size();
    }

    public class SearchViewHolder extends RecyclerView.ViewHolder {
        private TextView titleTextView;
        private ImageView imageView;

        public SearchViewHolder(View itemView) {
            super(itemView);
            titleTextView = (TextView) itemView.findViewById(R.id.tituloTextView);
            imageView = (ImageView) itemView.findViewById(R.id.portadaInicio);
        }

        public TextView getTitleTextView() {
            return titleTextView;
        }

        public ImageView getImageView() {
            return imageView;
        }
    }

    public void cambiarLista(List<Movie> data){
        this.data=data;
    }
}

