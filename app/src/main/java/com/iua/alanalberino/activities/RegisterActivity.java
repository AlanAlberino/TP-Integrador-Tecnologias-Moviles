package com.iua.alanalberino.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.iua.alanalberino.R;
import com.iua.alanalberino.activities.MainActivity;
import com.iua.alanalberino.model.User;
import com.iua.alanalberino.persistence.UserRepository;

import java.util.concurrent.ExecutionException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegisterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar);
    }

    public void verificarDatosRegistro(View view) throws ExecutionException, InterruptedException {

        //Obtengo todos los EditText

        EditText editTextUsuario = (EditText) findViewById(R.id.editTextUsuario);
        EditText editTextEmail = (EditText) findViewById(R.id.editTextEmail);
        EditText editTextContrasena = (EditText) findViewById(R.id.editTextContrasena);
        EditText editTextConfirmarContrasena = (EditText) findViewById(R.id.editTextConfirmarContrasena);
        EditText editTextTelefono = (EditText) findViewById(R.id.editTextTelefono);

        //Guardo en contenido de los EditText en String

        String usuario = editTextUsuario.getText().toString();
        String email = editTextEmail.getText().toString();
        String contrasena = editTextContrasena.getText().toString();
        String contrasena2 = editTextConfirmarContrasena.getText().toString();
        String telefono = editTextTelefono.getText().toString();

        //Verifico la validez de los campos

        boolean valido = true;

        if(usuario.length()<8){
            valido = false;
            Toast toast = Toast.makeText(getApplicationContext(), "Su usuario debe contener al menos  8 caracteres.", Toast.LENGTH_LONG);
            toast.show();
        }

        if(contrasena.compareTo(contrasena2)!=0){
            valido = false;
            Toast toast = Toast.makeText(getApplicationContext(), "Las contraseñas ingresadas son diferentes.", Toast.LENGTH_LONG);
            toast.show();
        }

        if(contrasena.length()<8){
            valido = false;
            Toast toast = Toast.makeText(getApplicationContext(), "Su contraeña debe contener al menos  8 caracteres.", Toast.LENGTH_LONG);
            toast.show();
        }

        if(validateEmail(email)==false){
            valido = false;
            Toast toast = Toast.makeText(getApplicationContext(), "El email ingresado es incorrecto.", Toast.LENGTH_LONG);
            toast.show();
        }
        String telefonoLimpio = telefono;
        telefonoLimpio = telefonoLimpio.replace("+549", "");
        telefonoLimpio = telefonoLimpio.replace("+54", "");
        if(telefono.length()<10){
            valido = false;
            Toast toast = Toast.makeText(getApplicationContext(), "El teléfono ingresado es incorrecto.", Toast.LENGTH_LONG);
            toast.show();
        }

        //Si los datos están bien, guardo en la BD, establezco como logueado, y abro la MainActivity

        if(valido){
            UserRepository repository = new UserRepository(getApplication());
            repository.insert(new User(usuario, email, contrasena, telefono));

            Intent listIntent = new Intent(this, MainActivity.class);
            startActivity(listIntent);
        }
    }

    public static boolean validateEmail(String emailStr) {
        Pattern VALID_EMAIL_ADDRESS_REGEX = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
        Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(emailStr);
        return matcher.find();
    }
}