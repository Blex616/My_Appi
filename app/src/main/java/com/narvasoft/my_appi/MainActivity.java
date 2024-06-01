package com.narvasoft.my_appi;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    EditText txtUser, txtTitle, txtBody;
    Button btnEnviar;
    Button btnNuevo;
    Button btnActualizar;
    Button btnEliminar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        txtUser = findViewById(R.id.txtUser);
        txtTitle = findViewById(R.id.txtTitle);
        txtBody = findViewById(R.id.txtBody);
        btnEnviar = findViewById(R.id.btnEnviar);
        btnNuevo = findViewById(R.id.btnNuevo);
        btnActualizar = findViewById(R.id.btnActualizar);
        btnEliminar = findViewById(R.id.btnEliminar);

        btnEnviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getById(txtUser.getText().toString());
            }
        });

        btnNuevo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                enviarWs(txtTitle.getText().toString(), txtBody.getText().toString(), txtUser.getText().toString());
            }
        });

        btnActualizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                actualizarWs(txtTitle.getText().toString(), txtBody.getText().toString(), txtUser.getText().toString());

            }
        });

        btnEliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                eliminarWs(txtUser.getText().toString());

            }
        });
    }

    private void getById(final String idUser) {
        System.out.println("Hola, mundo!");
        String url = "https://jsonplaceholder.typicode.com/posts/";

        if(!idUser.isEmpty()) {
            url += idUser;
        } else {
            url += "2";
        }

        StringRequest postResquest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    txtUser.setText(jsonObject.getString("userId"));
                    txtTitle.setText(jsonObject.getString("title"));
                    txtBody.setText(jsonObject.getString("body"));
                    Toast.makeText(MainActivity.this, "Respuesta del Servidor = " + response, Toast.LENGTH_LONG).show();

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Error", error.getMessage());
            }
        });
        Volley.newRequestQueue(this).add(postResquest);
    }

    private void enviarWs(final String title, final String body, final String userId) {

        if(userId.isEmpty() || title.isEmpty()) {
            Toast.makeText(MainActivity.this, "UserId y Title deben estar llenos", Toast.LENGTH_LONG).show();
            return;
        }

        String url = "https://jsonplaceholder.typicode.com/posts";

        StringRequest postResquest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(MainActivity.this, "Nuevo, Resp del Servidor = " + response, Toast.LENGTH_LONG).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Error", error.getMessage());
            }
        }) {
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("title", title);
                params.put("body", body);
                params.put("userId", userId);

                return params;
            }
        };
        Volley.newRequestQueue(this).add(postResquest);
    }

    private void actualizarWs(final String title, final String body, final String Id) {

        if(Id.isEmpty() || title.isEmpty()) {
            Toast.makeText(MainActivity.this, "UserId y Title deben estar llenos", Toast.LENGTH_LONG).show();
            return;
        }

        String url = "https://jsonplaceholder.typicode.com/posts/" + Id;

        StringRequest postResquest = new StringRequest(Request.Method.PUT, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(MainActivity.this, "Actualizar resp del Servidor = " + response, Toast.LENGTH_LONG).show();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Error", error.getMessage());
            }
        }) {
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("id", "1");
                params.put("title", title);
                params.put("body", body);
                params.put("userId", Id);

                return params;
            }
        };
        Volley.newRequestQueue(this).add(postResquest);
    }

    private void eliminarWs(final String userId) {

        if(userId.isEmpty()) {
            Toast.makeText(MainActivity.this, "UserId debe estar diligenciado", Toast.LENGTH_LONG).show();
            return;
        }

        String url = "https://jsonplaceholder.typicode.com/posts/" + userId;

        StringRequest postResquest = new StringRequest(Request.Method.DELETE, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Toast.makeText(MainActivity.this, "Eliminar, resp del Servidor = " + response, Toast.LENGTH_LONG).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Error", error.getMessage());
            }
        });
        Volley.newRequestQueue(this).add(postResquest);
    }
}