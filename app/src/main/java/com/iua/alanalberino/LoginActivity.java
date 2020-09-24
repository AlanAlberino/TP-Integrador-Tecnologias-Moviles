package com.iua.alanalberino;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

    public void verificarDatos(View view){
        Intent listIntent = new Intent(this, MainActivity.class);
        startActivity(listIntent);
    }

    public void registrar(View view){
        Intent listIntent = new Intent(this, Registrar.class);
        startActivity(listIntent);
    }
}