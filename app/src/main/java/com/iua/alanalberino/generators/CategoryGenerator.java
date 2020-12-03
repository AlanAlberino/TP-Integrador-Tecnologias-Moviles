package com.iua.alanalberino.generators;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.maps.model.LatLng;
import com.iua.alanalberino.Constantes;
import com.iua.alanalberino.async.DownloadFilesTask;
import com.iua.alanalberino.model.Category;

import com.iua.alanalberino.persistence.CategoriesRepository;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.ExecutionException;

import static android.content.Context.LOCATION_SERVICE;
import static android.content.Context.MODE_PRIVATE;
import static androidx.core.content.ContextCompat.getSystemService;

public class CategoryGenerator{

    ArrayList<Category> categories;
    Application app;
    Activity activity;
    SharedPreferences sharedPreferences;


    public CategoryGenerator(Application app, Activity activity) {
        this.app = app;
        this.activity = activity;
    }

    @SuppressLint("ResourceType")
    public ArrayList<Category> initCategory() throws ExecutionException, InterruptedException, JSONException, IOException {

        categories = new ArrayList<Category>();
        CategoriesRepository categoriesRepository = new CategoriesRepository(app);

        String myUrl = "https://api.themoviedb.org/3/genre/movie/list?api_key=ef7cd5f6820e2df3368478f9ecb07597&language=es-MX";
        DownloadFilesTask getRequest = new DownloadFilesTask();
        String result = getRequest.execute(myUrl).get();


        //Cargo las categorías que requieren ubicacion (Cartelera y tendencias)

        if (ContextCompat.checkSelfPermission(app.getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION) !=
                PackageManager.PERMISSION_GRANTED) {
            // Permission is not granted
            ActivityCompat.requestPermissions(activity,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    PackageManager.GET_PERMISSIONS);

            if (ContextCompat.checkSelfPermission(app.getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION) ==
                    PackageManager.PERMISSION_GRANTED) {
                cargarPorUbicacion();
            }
        } else cargarPorUbicacion();


        //Agarro el resto de las categorías convencionales que no requieren ubicacion

        JSONArray categorias = new JSONObject(result).getJSONArray("genres");

        sharedPreferences = app.getApplicationContext().getSharedPreferences(Constantes.PREFS_NAME, MODE_PRIVATE);
        String desiredCats = sharedPreferences.getString("DesiredCats", "");

        for (int i = 0; i < categorias.length(); i++) {

            JSONObject categoria = categorias.getJSONObject(i);
            int id = categoria.getInt("id");
            try {
                Category cat = new Category(id, categoria.getString("name"), new MovieGenerator(app).generateMovies(id));
                if (desiredCats.compareTo("") == 0 || desiredCats.indexOf(cat.getNombreCategoria()) >= 0) {
                    categories.add(cat);
                    categoriesRepository.addCategory(cat);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
        return categories;

    }

    private void cargarPorUbicacion() throws IOException {
        LatLng latLng = getLocation();
        Log.i("Longitud:",latLng.longitude+"");
        Log.i("Latitud", latLng.latitude+"");

        String countryCode = "No se pudo encontrar el codigo";
        String countryName = "";
        try {
            Geocoder geocoder = new Geocoder(app.getApplicationContext(), app.getResources().getConfiguration().locale);
            List<Address> addresses = geocoder.getFromLocation(latLng.latitude, latLng.longitude, 2);
            if (addresses.size() > 1) {
                Address address = addresses.get(1);
                countryCode = address.getCountryCode();
                countryName = address.getCountryName();
                categories.add(new Category(100000, "Populares en "+countryName, new MovieGenerator(app).getPopular(countryCode)));
                categories.add(new Category(100001, "En cartelera en "+countryName, new MovieGenerator(app).getNowPlaying(countryCode)));
            }
            else Log.i("CategoryGenerator", "No se encontraron resultados en la ubicación");
        } catch (IOException e) {
            Log.e("tag", e.getMessage());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @SuppressLint("MissingPermission")
    public LatLng getLocation(){
        LocationManager locationManager = (LocationManager) activity.getSystemService(Context.LOCATION_SERVICE);

        locationManager.requestLocationUpdates(
                LocationManager.GPS_PROVIDER,
                1000,
                1,
                new LocationListener() {
                    public void onLocationChanged(Location location) {
                        String message = String.format(
                                "New Location \n Longitude: %1$s \n Latitude: %2$s",
                                location.getLongitude(), location.getLatitude()
                        );

                    }

                    public void onStatusChanged(String s, int i, Bundle b) {
                    }

                    public void onProviderDisabled(String s) {
                    }

                    public void onProviderEnabled(String s) {

                    }
                }
        );

        Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

        if (location != null) {
            String message = String.format(
                    "Current Location \n Longitude: %1$s \n Latitude: %2$s",
                    location.getLongitude(), location.getLatitude()
            );
        }

        return new LatLng(location.getLatitude(), location.getLongitude());

    }
}


