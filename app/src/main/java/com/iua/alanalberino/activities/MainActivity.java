package com.iua.alanalberino.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;


import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.iua.alanalberino.fragments.FavoriteFragment;
import com.iua.alanalberino.R;
import com.iua.alanalberino.fragments.SettingsFragment;
import com.iua.alanalberino.fragments.HomeFragment;
import com.iua.alanalberino.fragments.SearchFragment;

public class MainActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigation;
    FavoriteFragment favoriteFragment;
    HomeFragment homeFragment;
    SearchFragment searchFragment;
    SettingsFragment settingsFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bottomNavigation = findViewById(R.id.bottom_navigation);
        bottomNavigation.setOnNavigationItemSelectedListener(navigationItemSelectedListener);

        openFragment(new HomeFragment());

    }

    public void openFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    BottomNavigationView.OnNavigationItemSelectedListener navigationItemSelectedListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    switch (item.getItemId()) {
                        case R.id.navigation_home:
                            if(homeFragment==null){
                                homeFragment = new HomeFragment();
                            }
                            openFragment(homeFragment);
                            return true;
                        case R.id.navigation_search:
                            if(searchFragment==null)
                                searchFragment = new SearchFragment();
                            openFragment(searchFragment);
                            return true;
                        case R.id.navigation_notifications:
                            if(settingsFragment==null)
                                settingsFragment = new SettingsFragment();
                            openFragment(settingsFragment);
                            return true;
                        case R.id.navigation_favorites:
                            if(favoriteFragment==null)
                                favoriteFragment = new FavoriteFragment();
                            openFragment(favoriteFragment);
                            return true;
                    }
                    return false;
                }
            };

     public void enviarEmail(View view){
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_EMAIL, new String[]{"soporte@themoviedb.org"});
        intent.putExtra(Intent.EXTRA_SUBJECT, "Problema con la aplicación para Android");
        intent.putExtra(Intent.EXTRA_TEXT, "Estoy experimentando un problema con la aplicación de The Movie DB para Android.");
        Intent mailer = Intent.createChooser(intent, "Enviar mail utilizando:");
        startActivity(intent);
    }

}


