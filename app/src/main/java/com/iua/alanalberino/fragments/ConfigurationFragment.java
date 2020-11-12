package com.iua.alanalberino.fragments;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import com.iua.alanalberino.R;

import static android.content.Context.MODE_PRIVATE;


public class ConfigurationFragment extends Fragment {

    private static final String PREFS_NAME ="com.nicolasfanin.IUASampleApp.prefs";
    SharedPreferences sharedPreferences;
    CategorySelectionFragment categorySelectionFragment;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        categorySelectionFragment = new CategorySelectionFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_configuration, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState){

        sharedPreferences  = getActivity().getApplicationContext().getSharedPreferences(PREFS_NAME,MODE_PRIVATE);

        //Configuro el check listener para el switch que permite activar o desactivar las notificaciones diarias

        Switch switch1 = (Switch) getView().findViewById(R.id.switch1);
        switch1.setOnCheckedChangeListener(new  CompoundButton.OnCheckedChangeListener(){
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) sharedPreferences.edit().putBoolean("SendDailyNotification", true).apply();
                else sharedPreferences.edit().putBoolean("SendDailyNotification", false).apply();
            }
        });

        View.OnClickListener categorySelectionListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                categorySelectionFragment.show(getParentFragmentManager(), "CategorySelectionFragment");
            }
        };

        TextView selectCategoriesTextView = (TextView) getView().findViewById(R.id.textView23);
        selectCategoriesTextView.setOnClickListener(categorySelectionListener);

    }
}