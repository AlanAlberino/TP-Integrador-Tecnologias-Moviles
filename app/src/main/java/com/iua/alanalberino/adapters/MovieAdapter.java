package com.iua.alanalberino.adapters;


import android.content.Context;
import android.util.Log;
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

public class MovieAdapter extends RecyclerView.Adapter<PalleteViewHolder> {
    private List<Movie> data;
    private Context context;
    private FragmentManager fragmentManager;
    private int idFragment;
    private Movie movie;

    public MovieAdapter(@NonNull List<Movie> data, Context context, FragmentManager fragmentManager, int idFragment) {
        this.data = data;

        this.context = context;
        this.fragmentManager = fragmentManager;
        this.idFragment = idFragment;
    }

    @NonNull
    @Override
    public PalleteViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View row = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.pelicula_simple, viewGroup, false);
        return new PalleteViewHolder(row);
    }

    @Override
    public void onBindViewHolder(@NonNull PalleteViewHolder palleteViewHolder, int i) {

        movie = data.get(i);
            String title = movie.getTitle();
            if(title.length()>13)
                title= title.substring(0, 13) + "...";

            palleteViewHolder.getTitleTextView().setText(title);
            Glide.with(context)
                    .load(movie.getVerticalImageURL())
                    .fitCenter()
                    .into(palleteViewHolder.getImageView());


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
            palleteViewHolder.getImageView().setOnClickListener(listener);
            palleteViewHolder.getTitleTextView().setOnClickListener(listener);

    }


    @Override
    public int getItemCount() {
        return data.size();
    }
}

class PalleteViewHolder extends RecyclerView.ViewHolder {
    private TextView titleTextView;
    private ImageView imageView;
    private TextView idTextView;

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