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

        miContexto = this;

        servicios = RetrofitClient.getClient().create(RecetaService.class);
        myRecycler = findViewById(R.id.rvRecetas);
        myRecycler.addItemDecoration(new DividerItemDecoration(miContexto, DividerItemDecoration.VERTICAL));
        myRecycler.setLayoutManager(new LinearLayoutManager(miContexto));

        listarRecetas();
    }

    private void listarRecetas() {
        Call<List<Receta>> call = servicios.listarRecetas();
        call.enqueue(new Callback<List<Receta>>() {
            @Override
            public void onResponse(Call<List<Receta>> call, Response<List<Receta>> response) {
                if (response.isSuccessful()) {
                    recetas = response.body();
                    configurarAdaptador();
                } else {
                    Toast.makeText(miContexto, "Error al obtener las recetas", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Receta>> call, Throwable t) {
                Toast.makeText(miContexto, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void configurarAdaptador() {
        if (recetas != null && !recetas.isEmpty()) {
            RecetaAdapter adapter = new RecetaAdapter(miContexto, recetas);
            myRecycler.setAdapter(adapter);
            adapter.setOnItemClickListener(new RecetaAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(int position) {
                    //Para saber que receta estamos viendo en todo momento
                    Singleton.getInstance().setUserId(recetas.get(position).getId());

                    Intent intent = new Intent(miContexto, singleReceta.class);
                    intent.putExtra("nombre", recetas.get(position).getNombre());
                    intent.putExtra("descripcion", recetas.get(position).getDescripcion());
                    intent.putExtra("origen", recetas.get(position).getOrigen());
                    miContexto.startActivity(intent);
                }
            });
        } else {
            Log.d("Recetas", "La lista de recetas está vacía o es nula");
        }
    }

    public void toMenu(View view) {
        Intent anterior = new Intent(miContexto, MainMenu.class);
        startActivity(anterior);
    }
}
