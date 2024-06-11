package com.example.aplicacion_1.Login;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.aplicacion_1.Clases.Singleton;
import com.example.aplicacion_1.Clases.Usuario;
import com.example.aplicacion_1.MainMenu;
import com.example.aplicacion_1.R;
import com.example.aplicacion_1.conexion.RecetaService;
import com.example.aplicacion_1.conexion.RetrofitClient;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Login extends AppCompatActivity {

    EditText name, password;
    private RecetaService servicios;
    private Context miContexto;
    private View rootView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_login);

        name = findViewById(R.id.editTextName);
        password = findViewById(R.id.editTextPassword);

        // Inicializa rootView después de setContentView
        rootView = findViewById(android.R.id.content);
        miContexto = rootView.getContext(); // para evitar el error del contexto

        // Inicializa el servicio de recetas utilizando Retrofit
        servicios = RetrofitClient.getClient().create(RecetaService.class);
    }

    public void toRegister(View vista){
        Intent register = new Intent(this, Register.class);
        startActivity(register);
    }

    public void verificaDatos(View vista){

        String myName = name.getText().toString();
        String myPassword = password.getText().toString();

        Log.d("Valor name: ", myName);

        // Realiza una llamada asíncrona al servicio de recetas para obtener el usuario dado el nombre
        Call<List<Usuario>> call = servicios.filtrarUsuariosNombre(myName);
        call.enqueue(new Callback<List<Usuario>>() {
            @Override
            public void onResponse(Call<List<Usuario>> call, Response<List<Usuario>> response) {
                Log.d("FILTRADO USUARIOS", "CONEXION EXITOSA");
                if (response.isSuccessful()) {
                    Log.d("FILTRADO USUARIOS", "ME HAN RESPONDIDO");
                    // Si la respuesta es exitosa, obtiene la lista de usuarios
                    List<Usuario> usuarios = response.body();

                    Log.d("FILTRADO USUARIOS", "Usuarios: " + usuarios);
                    if (usuarios != null && !usuarios.isEmpty()) {
                        Usuario usuario = usuarios.get(0);

                        if (usuario.getContraseña().equals(calculateSHA256(myPassword))) {

                            //Para saber que usuario está logeado en todo momento
                            Singleton.getInstance().setUserId(usuario.getIdUsuario());

                            Intent acceso = new Intent(miContexto, MainMenu.class);
                            miContexto.startActivity(acceso);
                        }
                        else {
                            Intent errorAcceso = new Intent(miContexto, FailedLogin.class);
                            miContexto.startActivity(errorAcceso);
                        }
                    } else {
                        Intent errorAcceso = new Intent(miContexto, FailedLogin.class);
                        miContexto.startActivity(errorAcceso);
                    }
                } else {
                    Intent errorAcceso = new Intent(miContexto, FailedLogin.class);
                    miContexto.startActivity(errorAcceso);
                }
            }

            @Override
            public void onFailure(Call<List<Usuario>> call, Throwable t) {
                Log.d("FILTRADO USUARIOS", "CONEXION FALLIDA");
                // Si la llamada falla, muestra un mensaje de error mediante un Toast
                Toast.makeText(miContexto, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public static String calculateSHA256(String input) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(input.getBytes());
            byte[] digest = md.digest();
            StringBuilder sb = new StringBuilder();
            for (byte b : digest) {
                sb.append(String.format("%02x", b & 0xff));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }

}
