package com.iua.alanalberino.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request.Method;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.iua.alanalberino.Constantes;
import com.iua.alanalberino.R;
import com.iua.alanalberino.async.DownloadFilesTask;
import com.iua.alanalberino.persistence.UserRepository;
import com.iua.alanalberino.model.User;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;

public class LoginActivity extends AppCompatActivity {

    private UserRepository repository;
    private RequestQueue requestQueue;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        repository = new UserRepository(getApplication());
        requestQueue = Volley.newRequestQueue(getApplication().getApplicationContext());
        sharedPreferences = getApplication().getApplicationContext().getSharedPreferences(Constantes.PREFS_NAME, MODE_PRIVATE);
        //Verificamos si hay alguna sesión iniciada
        if(verificarSesion()){
            Intent listIntent = new Intent(this, MainActivity.class);
            listIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            listIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(listIntent);
        }
        else{
            setContentView(R.layout.activity_login);
        }
    }

    public void verificarDatos(View view) throws ExecutionException, InterruptedException, JSONException {

        //Obtengo el usuario y contraseña ingresados

        TextView userNameTextView = (TextView) findViewById(R.id.editTextPersonName);
        String userName = userNameTextView.getText().toString();

        TextView passwordTextView = (TextView) findViewById(R.id.editTextNumberPassword);
        String password = passwordTextView.getText().toString();

        if(userName.length()>=4 && password.length()>=4){
            iniciarSesion(userName, password);
        }
        else{
            Toast toast = Toast.makeText(getApplicationContext(), "Los datos ingresados son incorrectos.", Toast.LENGTH_LONG);
            toast.show();
        }

    }

    public void registrar(View view){
        String url = "https://www.themoviedb.org/signup";
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setData(Uri.parse(url));
        startActivity(i);
    }

    private void iniciarSesion(final String userName, final String password) throws InterruptedException, ExecutionException, JSONException {
        final String token = obtenerToken();
        Log.i("LoginActivity", "Token = " + token);
        String url = "https://api.themoviedb.org/3/authentication/token/validate_with_login?api_key=ef7cd5f6820e2df3368478f9ecb07597";
        StringRequest strReq = new StringRequest(Method.POST,
                url, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                try {
                    JSONObject responseJSON = new JSONObject(response);
                    boolean success = responseJSON.getBoolean("success");
                    //Verificamos si el ingreso fue exitoso
                    if (success) {
                        obtenerIdSesion(token);
                    } else {
                        Toast toast = Toast.makeText(getApplicationContext(), "Los datos ingresados son incorrectos.", Toast.LENGTH_LONG);
                        toast.show();
                    }
                } catch (JSONException e) {
                    // JSON error
                    e.printStackTrace();
                    Toast toast = Toast.makeText(getApplicationContext(), "Sucedió un error, intente nuevamente.", Toast.LENGTH_LONG);
                    toast.show();
                }

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
               //Tiro un toast que diga que hubo un error
            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                // Posting parameters to login url
                Map<String, String> params = new HashMap<String, String>();
                params.put("username", userName);
                params.put("password", password);
                params.put("request_token", token);

                return params;
            }

        };
        requestQueue.add(strReq);
    }

    private String obtenerToken() throws JSONException, ExecutionException, InterruptedException {
        String myUrl = "https://api.themoviedb.org/3/authentication/token/new?api_key=ef7cd5f6820e2df3368478f9ecb07597";
        DownloadFilesTask getRequest = new DownloadFilesTask();
        String result = getRequest.execute(myUrl).get();
        return new JSONObject(result).getString("request_token");
    }

    private void obtenerIdSesion(final String token){

        String url = "https://api.themoviedb.org/3/authentication/session/new?api_key=ef7cd5f6820e2df3368478f9ecb07597";
        StringRequest strReq = new StringRequest(Method.POST,
                url, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                try {
                    JSONObject responseJSON = new JSONObject(response);
                    boolean success = responseJSON.getBoolean("success");
                    //Verificamos si el ingreso fue exitoso
                    if (success) {
                        String sessionId = responseJSON.getString("session_id");
                        Log.i("LoginActivity", "ID Sesion = " + sessionId);

                        //Guardo el ID de sesion en una shared preference ya que será necesario utilizarlo más adelante

                        sharedPreferences.edit().putString(Constantes.SESSION_ID, sessionId).apply();

                        //Tambien obtenemos y guardamos el ID de usuario, ya que es necesario para obtener los favoritos.

                        String myUrl = "https://api.themoviedb.org/3/account?api_key=ef7cd5f6820e2df3368478f9ecb07597&session_id=" + sessionId;
                        DownloadFilesTask getRequest = new DownloadFilesTask();
                        String result = getRequest.execute(myUrl).get();
                        int id = new JSONObject(result).getInt("id");
                        Log.i("LoginActivity", "ID Usuario = " + id);
                        sharedPreferences.edit().putInt(Constantes.USER_ID, id).apply();

                        //Una vez logueado el usuario y guardado su ID de sesión, paso a abrir la MainActivity
                        Intent listIntent = new Intent(getApplicationContext(), MainActivity.class);
                        listIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        listIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(listIntent);

                    } else {
                        //Tiro un toast que diga que hubo un error
                    }
                } catch (JSONException e) {
                    // JSON error
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                //Tiro un toast que diga que hubo un error
            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                // Posting parameters to login url
                Map<String, String> params = new HashMap<String, String>();
                params.put("request_token", token);
                return params;
            }

        };
        requestQueue.add(strReq);
    }

    private boolean verificarSesion(){
        String sessionId = sharedPreferences.getString(Constantes.SESSION_ID, "");

        //Si el Session ID es distinto al valor por defecto, que es un string vacío, quiere decir que hay una sesión iniciada

        if(sessionId.compareTo("")!=0){
            //Verificamos que el Session ID siga siendo valido
            try{

                //El siguiente GET obtiene la información del usuario. Si la devuelve, es porque el Session ID sigue siendo valido.
                String myUrl = "https://api.themoviedb.org/3/account?api_key=ef7cd5f6820e2df3368478f9ecb07597&session_id=" + sessionId;
                DownloadFilesTask getRequest = new DownloadFilesTask();
                String result = getRequest.execute(myUrl).get();
                String name = new JSONObject(result).getString("username");
                if(name!=null && name.compareTo("")!=0){
                    return true;
                }

            }catch (Exception e){

            }
        }
        return false;
    }
}