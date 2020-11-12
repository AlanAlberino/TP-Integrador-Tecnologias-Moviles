package com.iua.alanalberino.fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.iua.alanalberino.model.Favorite;
import com.iua.alanalberino.persistence.FavoritesRepository;
import com.iua.alanalberino.model.Movie;
import com.iua.alanalberino.generators.MovieGenerator;
import com.iua.alanalberino.R;
import com.iua.alanalberino.persistence.UserRepository;

import org.json.JSONException;

import java.util.concurrent.ExecutionException;

public class MovieFragment extends Fragment {

    private int id;
    Context context;

    public MovieFragment(int id, Context context) {
        this.id = id;
        this.context = context;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        try {
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
            final Favorite isFavorite = favoritesRepository.isMarkedAsFavorite(new Favorite(userRepository.getLoggedUser().getId(), (long) movie.getId()));
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
                        favoritesRepository.removeAsFavorite(new Favorite(userRepository.getLoggedUser().getId(), (long) movie.getId()));
                        star.setImageDrawable(getResources().getDrawable(android.R.drawable.btn_star_big_off));
                    }
                    else{
                        favoritesRepository.markAsFavorite(new Favorite(userRepository.getLoggedUser().getId(), (long) movie.getId()));
                        star.setImageDrawable(getResources().getDrawable(android.R.drawable.btn_star_big_on));
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


}