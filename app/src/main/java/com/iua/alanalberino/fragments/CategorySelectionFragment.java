package com.iua.alanalberino.fragments;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import com.iua.alanalberino.model.Category;
import com.iua.alanalberino.persistence.CategoriesRepository;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutionException;

import static android.content.Context.MODE_PRIVATE;

public class CategorySelectionFragment extends DialogFragment {

    private static final String PREFS_NAME ="com.nicolasfanin.IUASampleApp.prefs";
    SharedPreferences sharedPreferences;

    public Dialog onCreateDialog(Bundle savedInstanceState) {

        sharedPreferences  = getActivity().getApplicationContext().getSharedPreferences(PREFS_NAME,MODE_PRIVATE);

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        String[] categoriesArray = new String[]{};
        CategoriesRepository categoriesRepository = new CategoriesRepository(getActivity().getApplication());
        try {
            List<Category> categories = categoriesRepository.getCategories();
            categoriesArray = new String[categories.size()];
            for(int i=0;i<categories.size();i++){
                categoriesArray[i]=categories.get(i).getNombreCategoria();
            }
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        final boolean[] checkedCategoriesArray = new boolean[categoriesArray.length];
        String desiredCats = sharedPreferences.getString("DesiredCats", "");

        for(int i=0; i<categoriesArray.length;i++){
            if(desiredCats.compareTo("")==0 || desiredCats.indexOf(categoriesArray[i])>=0)
                checkedCategoriesArray[i]=true;
            else checkedCategoriesArray[i]=false;
        }
        final List<String> categoriesList = Arrays.asList(categoriesArray);

        builder.setTitle("Seleccionar categorias");

        builder.setMultiChoiceItems(categoriesArray, checkedCategoriesArray, new DialogInterface.OnMultiChoiceClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                checkedCategoriesArray[which] = isChecked;
                String currentItem = categoriesList.get(which);
                Toast.makeText(getContext(), currentItem + " " + isChecked, Toast.LENGTH_SHORT).show();
            }
        });
        // Set the positive/yes button click listener
        final String[] finalCategoriesArray = categoriesArray;
        builder.setPositiveButton("Guardar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String cats = "";
                for(int i=0;i<checkedCategoriesArray.length;i++){
                    if(checkedCategoriesArray[i]){
                        if(cats.compareTo("")!=0)
                            cats+=",";
                        cats+= finalCategoriesArray[i];
                    }
                }
                sharedPreferences.edit().putString("DesiredCats", cats).apply();
            }
        });
        // Set the neutral/cancel button click listener
        builder.setNeutralButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        return builder.create();
    }
}
