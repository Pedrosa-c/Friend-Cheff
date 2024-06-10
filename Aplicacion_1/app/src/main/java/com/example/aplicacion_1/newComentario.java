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

    private RecetaService servicios;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_new_comentario);

        text_ = findViewById(R.id.text);
        valoracion_ = findViewById(R.id.valoracion);

        // Inicializa el servicio de recetas utilizando Retrofit
        servicios = RetrofitClient.getClient().create(RecetaService.class);
    }

    public void añadirComentario(View view) {
        Log.d("Usuarios", "Añadiendo Comentario");

        String text = text_.getText().toString();
        String valoracion = valoracion_.getText().toString();

        // Obtener el último ID y luego añadir el comentario
        obtenerUltimoIdComentario(new IdCallback() {
            @Override
            public void onIdObtained(int id) {
                // Llama al método anadirComentario para añadir el nuevo comentario
                Call<Void> call = servicios.anadirComentario(id, text, valoracion, obtenerFechaActual(),
                        obtenerHoraActual(), Singleton.getInstance().getRecetaId(),
                        Singleton.getInstance().getUserId());

                call.enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        if (response.isSuccessful()) {
                            Log.d("Usuarios", "Comentario añadido correctamente");
                            // Mover el Intent aquí
                            Intent anterior = new Intent(newComentario.this, MainMenu.class);
                            startActivity(anterior);
                        } else {
                            Log.d("Usuarios", "Fallo al añadir el comentario");
                        }
                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {
                        Log.d("Usuarios", "Fallo de conexion al añadir el comentario");
                    }
                });
            }
        });
    }

    private void obtenerUltimoIdComentario(IdCallback callback) {
        Call<List<Comentario>> call = servicios.listarComentarios();
        call.enqueue(new Callback<List<Comentario>>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(Call<List<Comentario>> call, Response<List<Comentario>> response) {
                if (response.isSuccessful()) {
                    List<Comentario> comentarios = response.body();
                    if (comentarios != null && !comentarios.isEmpty()) {
                        int id = comentarios.get(comentarios.size() - 1).getId() + 1;
                        callback.onIdObtained(id);
                    } else {
                        callback.onIdObtained(1); // Si no hay comentarios, empezar con ID 1
                    }
                } else {
                    Log.d("Usuarios", "Fallo al obtener el ID");
                    callback.onIdObtained(1); // Manejar el fallo adecuadamente
                }
            }

            @Override
            public void onFailure(Call<List<Comentario>> call, Throwable t) {
                Log.d("Usuarios", "Error: " + t.getMessage());
                callback.onIdObtained(1); // Manejar el fallo adecuadamente
            }
        });
    }

    public interface IdCallback {
        void onIdObtained(int id);
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

    public void toMenu(View vista){
        Intent anterior = new Intent(vista.getContext(), MainMenu.class);
        startActivity(anterior);
    }
}
