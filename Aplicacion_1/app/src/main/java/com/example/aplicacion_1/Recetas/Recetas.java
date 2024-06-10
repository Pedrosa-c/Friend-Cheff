package com.example.aplicacion_1.Recetas;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.aplicacion_1.Adapters.RecetaAdapter;
import com.example.aplicacion_1.Clases.Receta;
import com.example.aplicacion_1.Clases.Singleton;
import com.example.aplicacion_1.MainMenu;
import com.example.aplicacion_1.R;
import com.example.aplicacion_1.conexion.RecetaService;
import com.example.aplicacion_1.conexion.RetrofitClient;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Recetas extends AppCompatActivity {

    private List<Receta> recetas;
    private RecyclerView myRecycler;
    private RecetaService servicios;
    private Context miContexto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_recetas);

        // Inicializa el RecyclerView
        myRecycler = findViewById(R.id.rvRecetas);
        myRecycler.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        myRecycler.setLayoutManager(new LinearLayoutManager(this));

        // Inicializa el servicio de recetas utilizando Retrofit
        servicios = RetrofitClient.getClient().create(RecetaService.class);

        // Lista las recetas
        listarRecetas();
    }

    // Método para listar todas las recetas disponibles
    private void listarRecetas() {
        Call<List<Receta>> call = servicios.listarRecetas();
        call.enqueue(new Callback<List<Receta>>() {
            @Override
            public void onResponse(Call<List<Receta>> call, Response<List<Receta>> response) {
                Log.d("LISTA RECETAS", "CONEXION EXITOSA");
                if (response.isSuccessful()) {
                    Log.d("LISTA RECETAS", "ME HAN RESPONDIDO");
                    recetas = response.body();

                    // Verificar si recetas no es nulo y tiene elementos
                    if (recetas != null && !recetas.isEmpty()) {
                        Log.d("LISTA RECETAS", "Recetas recibidas: " + recetas.size());
                        Log.d("LISTA RECETAS", "Nombre de la primera receta: " + recetas.get(0).getNombre());

                        // Configura el adaptador con los datos recibidos
                        configurarAdaptador();
                    } else {
                        Log.d("LISTA RECETAS", "No se recibieron recetas");
                    }
                } else {
                    Log.d("LISTA RECETAS", "RESPUESTA FALLIDA: " + response.message());
                    Toast.makeText(Recetas.this, "Error en la respuesta: " + response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Receta>> call, Throwable t) {
                Log.d("LISTA RECETAS", "CONEXION FALLIDA: " + t.getMessage());
                Toast.makeText(Recetas.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void configurarAdaptador() {
        if (recetas == null || recetas.isEmpty()) {
            Log.d("CONFIGURAR ADAPTADOR", "La lista de recetas está vacía o es nula");
            return;
        }

        Log.d("CONFIGURAR ADAPTADOR", "Configurando el adaptador con las recetas");
        RecetaAdapter adapter = new RecetaAdapter(this, recetas);
        myRecycler.setAdapter(adapter);
        adapter.setOnItemClickListener(new RecetaAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Log.d("ONCLICK", "DENTRO DEL ONCLICK, posición: " + position + " nombre: " + recetas.get(position).getNombre());
                //Para saber que usuario está logeado en todo momento
                Singleton.getInstance().setRecetaId(recetas.get(position).getId());

                Intent intent = new Intent(Recetas.this, singleReceta.class);

                intent.putExtra("nombre", recetas.get(position).getNombre());
                intent.putExtra("origen", recetas.get(position).getOrigen());
                intent.putExtra("descripcion", recetas.get(position).getDescripcion());
                startActivity(intent);
            }
        });
    }

    public void toMenu(View view) {
        Intent anterior = new Intent(this, MainMenu.class);
        startActivity(anterior);
    }
}
