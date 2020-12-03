package com.iua.alanalberino.fragments;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.iua.alanalberino.Constantes;
import com.iua.alanalberino.activities.LoginActivity;
import com.iua.alanalberino.R;
import com.iua.alanalberino.persistence.UserRepository;

import static android.content.Context.MODE_PRIVATE;


public class SettingsFragment extends Fragment {

    private SharedPreferences sharedPreferences;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_settings, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {

        sharedPreferences = view.getContext().getSharedPreferences(Constantes.PREFS_NAME, MODE_PRIVATE);

        View.OnClickListener logoutListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sharedPreferences.edit().remove(Constantes.USER_ID).commit();
                sharedPreferences.edit().remove(Constantes.SESSION_ID).commit();
                getActivity().finish();
                Intent listIntent = new Intent(getActivity(), LoginActivity.class);
                startActivity(listIntent);
            }
        };
        TextView logoutTextView = (TextView) getView().findViewById(R.id.textView17);
        logoutTextView.setOnClickListener(logoutListener);

        View.OnClickListener configurationListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ConfigurationFragment configurationFragment = new ConfigurationFragment();
                FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
                transaction.replace(getId(), configurationFragment);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        };

        TextView configurationTextView = (TextView) getView().findViewById(R.id.textView13);
        configurationTextView.setOnClickListener(configurationListener);

    }


}
