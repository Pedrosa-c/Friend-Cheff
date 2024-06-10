package com.example.aplicacion_1;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.example.aplicacion_1.Clases.Comentario;
import com.example.aplicacion_1.Clases.Singleton;
import com.example.aplicacion_1.Login.Register;
import com.example.aplicacion_1.conexion.RecetaService;
import com.example.aplicacion_1.conexion.RetrofitClient;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class newComentario extends AppCompatActivity {

    EditText text_;
    EditText valoracion_;

    int id;

    private RecetaService servicios;
    private Register miContexto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_new_comentario);

        text_ = findViewById(R.id.text);
        valoracion_ = findViewById(R.id.valoracion);

        // Inicializa el servicio de recetas utilizando Retrofit
        servicios = RetrofitClient.getClient().create(RecetaService.class);

        añadirComentario();
    }

    private void añadirComentario() {
        // Aquí defines los datos del nuevo usuario
        Log.d("Usuarios", "Añadiendo Comentario");

        String text = text_.getText().toString();
        String valoracion = valoracion_.getText().toString();


        // Llama al método anadirUsuario para añadir el nuevo usuario
        Call<Void> call = servicios.anadirComentario(id, text, valoracion, obtenerFechaActual(),
                obtenerHoraActual(), Singleton.getInstance().getRecetaId(),
                Singleton.getInstance().getUserId());

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

        Intent anterior = new Intent(this, MainMenu.class);
        startActivity(anterior);
    }



    public int lastIdComentario(View vista) {
        // Llama al método listarUsuarios para obtener la lista de usuarios
        Call<List<Comentario>> call = servicios.listarComentarios();
        call.enqueue(new Callback<List<Comentario>>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(Call<List<Comentario>> call, Response<List<Comentario>> response) {
                boolean fallo = false;
                if (response.isSuccessful()) {
                    List<Comentario> comentarios = response.body();
                    id = (comentarios.get(comentarios.size() - 1).getId() + 1);
                }
            }

            @Override
            public void onFailure(Call<List<Comentario>> call, Throwable t) {
                Log.d("Usuarios", "Error: " + t.getMessage());
            }
        });
        return id;
    }

    public String obtenerFechaActual() {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return now.format(formatter);
    }

    public String obtenerHoraActual() {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
        return now.format(formatter);
    }
}