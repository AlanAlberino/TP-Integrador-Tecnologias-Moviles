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

public class MovieAdapter extends RecyclerView.Adapter<PalleteViewHolder> {
    private List<Movie> data;
    private Context context;

    public MovieAdapter(@NonNull List<Movie> data, Context context) {
        this.data = data;
        this.context = context;
    }

    @NonNull
    @Override
    public PalleteViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View row = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.pelicula_simple, viewGroup, false);
        return new PalleteViewHolder(row);
    }

    @Override
    public void onBindViewHolder(@NonNull PalleteViewHolder palleteViewHolder, int i) {
        Movie movie = data.get(i);
        String title = movie.getTitle();
        if(title.length()>13)
            title= title.substring(0, 13) + "...";

        palleteViewHolder.getTitleTextView().setText(title);
        Glide.with(context)
                .load(movie.getImageURL())
                .fitCenter()
                .into(palleteViewHolder.getImageView());
    }


    @Override
    public int getItemCount() {
        return data.size();
    }
}

class PalleteViewHolder extends RecyclerView.ViewHolder {
    private TextView titleTextView;
    private ImageView imageView;

    public PalleteViewHolder(View itemView) {
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