package com.iua.alanalberino.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.iua.alanalberino.R;
import com.iua.alanalberino.persistence.UserRepository;
import com.iua.alanalberino.model.User;

public class LoginActivity extends AppCompatActivity {

    private UserRepository repository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        repository = new UserRepository(getApplication());

        User loggedUser = repository.getLoggedUser();
        if(loggedUser!=null){
            Toast toast = Toast.makeText(getApplicationContext(), "Usted está logueado como "+loggedUser.getUserName(), Toast.LENGTH_LONG);
            toast.show();
            Intent listIntent = new Intent(this, MainActivity.class);
            listIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            listIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(listIntent);
        }
        else{
            setContentView(R.layout.activity_login);
        }
    }

    public void verificarDatos(View view){

        TextView userNameTextView = (TextView) findViewById(R.id.editTextPersonName);
        String userName = userNameTextView.getText().toString();

        TextView passwordTextView = (TextView) findViewById(R.id.editTextNumberPassword);
        String password = passwordTextView.getText().toString();

        UserRepository repository = new UserRepository(getApplication());
        User usuario = repository.getUser(userName);

        if(usuario==null){
            Toast toast = Toast.makeText(getApplicationContext(), "El usuario ingresado no existe.", Toast.LENGTH_LONG);
            toast.show();
        }
        else{
            if(usuario.getPassword().compareTo(password)==0){
                repository.setAsLoggedIn(usuario);
                Intent listIntent = new Intent(this, MainActivity.class);
                listIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                listIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(listIntent);
            }
            else{
                Toast toast = Toast.makeText(getApplicationContext(), "La contraseña ingresada es incorrecta.", Toast.LENGTH_LONG);
                toast.show();
            }
        }


    }

    public void registrar(View view){
        Intent listIntent = new Intent(this, RegisterActivity.class);
        startActivity(listIntent);
    }
}