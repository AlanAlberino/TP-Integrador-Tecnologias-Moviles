package com.iua.alanalberino.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.iua.alanalberino.R;
import com.iua.alanalberino.model.Category;

import java.util.ArrayList;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ViewHolder> {

    public ArrayList<Category> categories;
    private Context context;
    private LayoutInflater layoutInflater;
    private View view;
    private FragmentManager fragmentManager;
    private int idFragment;

    public CategoryAdapter(ArrayList<Category> categories, Context context, FragmentManager fragmentManager, int idFragment) {
        this.categories = categories;
        this.context = context;
        this.layoutInflater = LayoutInflater.from(context);
        this.fragmentManager = fragmentManager;
        this.idFragment = idFragment;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.categoria, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.recyclerView.setAdapter(new MovieAdapter(categories.get(position).getMovies(), context, fragmentManager, idFragment));
        holder.recyclerView.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));
        holder.recyclerView.setHasFixedSize(true);
        holder.textView.setText(categories.get(position).getNombreCategoria());
    }

    @Override
    public int getItemCount() {
        return categories.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        RecyclerView recyclerView;
        TextView textView;

        public ViewHolder(View itemView) {
            super(itemView);
            recyclerView = (RecyclerView) itemView.findViewById(R.id.RecyclerViewCategoria);
            textView = (TextView) itemView.findViewById(R.id.textViewCategoria);
        }
    }
}