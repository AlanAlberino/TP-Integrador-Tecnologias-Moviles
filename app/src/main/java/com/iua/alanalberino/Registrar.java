package com.iua.alanalberino;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class Registrar extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar);
    }

    public void verificarDatosRegistro(View view){
        Intent listIntent = new Intent(this, MainActivity.class);
        startActivity(listIntent);
    }
}