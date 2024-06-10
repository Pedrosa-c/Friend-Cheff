package com.example.aplicacion_1.Login;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.aplicacion_1.Clases.Usuario;
import com.example.aplicacion_1.R;
import com.example.aplicacion_1.conexion.RecetaService;
import com.example.aplicacion_1.conexion.RetrofitClient;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Register extends AppCompatActivity {

    private RecetaService servicios;
    private Register miContexto;

    EditText name, contra, correo, tel;
    TextView error;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_register);

        name = findViewById(R.id.nombre);
        tel = findViewById(R.id.telefono);
        correo = findViewById(R.id.email);
        contra = findViewById(R.id.contraseña);

        error = findViewById(R.id.ErrorView);

        // Inicializa el servicio de recetas utilizando Retrofit
        servicios = RetrofitClient.getClient().create(RecetaService.class);
        miContexto = this;
    }

    public void registroUsuario(View vista) {
        // Llama al método listarUsuarios para obtener la lista de usuarios
        Call<List<Usuario>> call = servicios.listarUsuarios();
        call.enqueue(new Callback<List<Usuario>>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(Call<List<Usuario>> call, Response<List<Usuario>> response) {
                boolean fallo = false;
                if (response.isSuccessful()) {
                    List<Usuario> usuarios = response.body();
                    if (usuarios != null) {
                        // Procesa la lista de usuarios (por ejemplo, imprime sus nombres)
                        for (Usuario usuario : usuarios) {
                            Log.d("Usuario", "Nombre: " + usuario.getNombre());
                            if (name.getText().toString().equals(usuario.getNombre())){
                                Log.d("ERROR", "ERROR NOMBRES IGUALES");
                                fallo = true;
                                error.setText("Este Nombre de usuario ya esta en uso");
                            }
                            else if(correo.getText().toString().equals(usuario.getEmail())){
                                fallo = true;
                                error.setText("Este Correo ya esta en uso");
                            }
                        }
                    }
                    else {
                        Log.d("Usuarios", "Lista de usuarios vacía");
                    }

                    if(!fallo){
                        // Aquí puedes añadir la lógica para registrar un nuevo usuario si lo deseas
                        anadirNuevoUsuario(usuarios.get(usuarios.size()-1).getIdUsuario() + 1);
                    }
                }
            }

            @Override
            public void onFailure(Call<List<Usuario>> call, Throwable t) {
                Log.d("Usuarios", "Error: " + t.getMessage());
            }
        });
    }

    private void anadirNuevoUsuario(int id) {
        // Aquí defines los datos del nuevo usuario
        Log.d("Usuarios", "Añadiendo Usuario2");
        String nombre = name.getText().toString();
        String telefono = tel.getText().toString();
        String email = correo.getText().toString();
        String password = contra.getText().toString();


        // Llama al método anadirUsuario para añadir el nuevo usuario
        Call<Void> call = servicios.anadirUsuario(id, nombre, telefono, email, password);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    Log.d("Usuarios", "Usuario añadido correctamente");
                } else {
                    Log.d("Usuarios", "Fallo al añadir el usuario");
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Log.d("Usuarios", "Fallo de conexion al añadir el usuario");
            }
        });
        Intent anterior = new Intent(this, Login.class);
        startActivity(anterior);
    }
}
