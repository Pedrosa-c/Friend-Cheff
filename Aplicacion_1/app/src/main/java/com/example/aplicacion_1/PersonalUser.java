package com.example.aplicacion_1;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.aplicacion_1.Adapters.RecetaAdapter;
import com.example.aplicacion_1.Adapters.RecetaPersonalAdapter;
import com.example.aplicacion_1.Clases.Receta;
import com.example.aplicacion_1.Clases.Singleton;
import com.example.aplicacion_1.Clases.Usuario;
import com.example.aplicacion_1.conexion.RecetaService;
import com.example.aplicacion_1.conexion.RetrofitClient;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PersonalUser extends AppCompatActivity {
    TextView nombre;
    Usuario usuario, usuarioLogeado;
    private List<Receta> recetas;
    private RecyclerView myRecycler;
    private RecetaService servicios;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_single_usuario);

        usuario = Singleton.getInstance().getUser();
        nombre = findViewById(R.id.nombre);
        nombre.setText(usuario.getNombre());

        // Inicializa el RecyclerView
        myRecycler = findViewById(R.id.rvRecetas);
        myRecycler.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        myRecycler.setLayoutManager(new LinearLayoutManager(this));

        // Inicializa el servicio de recetas utilizando Retrofit
        servicios = RetrofitClient.getClient().create(RecetaService.class);

        // Lista las recetas
        listarRecetas();
    }

    // Listar las recetas del usuario
    private void listarRecetas() {
        Call<List<Receta>> call = servicios.listarRecetas();
        call.enqueue(new Callback<List<Receta>>() {
            @Override
            public void onResponse(Call<List<Receta>> call, Response<List<Receta>> response) {
                Log.d("LISTA RECETAS", "CONEXION EXITOSA");
                if (response.isSuccessful()) {
                    Log.d("LISTA RECETAS", "ME HAN RESPONDIDO");
                    List<Receta> todasLasRecetas = response.body();

                    // Verificar si todasLasRecetas no es nulo y tiene elementos
                    if (todasLasRecetas != null && !todasLasRecetas.isEmpty()) {
                        Log.d("LISTA RECETAS", "Recetas recibidas: " + todasLasRecetas.size());

                        // Filtrar las recetas para incluir solo las del usuario actual
                        recetas = filtrarRecetasUsuario(todasLasRecetas, usuario.getIdRecetas());

                        if (recetas != null && !recetas.isEmpty()) {
                            Log.d("LISTA RECETAS", "Recetas del usuario: " + recetas.size());
                            Log.d("LISTA RECETAS", "Nombre de la primera receta: " + recetas.get(0).getNombre());

                            // Configura el adaptador con los datos filtrados
                            configurarAdaptador();
                        } else {
                            Log.d("LISTA RECETAS", "El usuario no tiene recetas");
                        }
                    } else {
                        Log.d("LISTA RECETAS", "No se recibieron recetas");
                    }
                } else {
                    Log.d("LISTA RECETAS", "RESPUESTA FALLIDA: " + response.message());
                }
            }

            @Override
            public void onFailure(Call<List<Receta>> call, Throwable t) {
                Log.d("LISTA RECETAS", "CONEXION FALLIDA: " + t.getMessage());
            }
        });
    }

    // Filtrar las recetas para incluir solo las del usuario
    private List<Receta> filtrarRecetasUsuario(List<Receta> todasLasRecetas, List<Integer> idRecetasUsuario) {
        List<Receta> recetasFiltradas = new ArrayList<>();
        for (Receta receta : todasLasRecetas) {
            if (idRecetasUsuario.contains(receta.getId())) {
                recetasFiltradas.add(receta);
            }
        }
        return recetasFiltradas;
    }

    private void configurarAdaptador() {
        if (recetas == null || recetas.isEmpty()) {
            Log.d("CONFIGURAR ADAPTADOR", "La lista de recetas está vacía o es nula");
            return;
        }

        Log.d("CONFIGURAR ADAPTADOR", "Configurando el adaptador con las recetas");
        RecetaPersonalAdapter adapter = new RecetaPersonalAdapter(this, recetas);
        myRecycler.setAdapter(adapter);
    }
}