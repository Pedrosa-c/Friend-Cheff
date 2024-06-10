package com.example.aplicacion_1.Recetas;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DirectAction;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.example.aplicacion_1.Clases.Comentario;
import com.example.aplicacion_1.Clases.Receta;
import com.example.aplicacion_1.Clases.Singleton;
import com.example.aplicacion_1.R;
import com.example.aplicacion_1.conexion.RecetaService;
import com.example.aplicacion_1.conexion.RetrofitClient;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class singleReceta extends AppCompatActivity {

    private List<Comentario> comentarios;
    private List<Comentario> comentariosAmostrar;
    private RecyclerView myRecycler;
    private RecetaService servicios;
    private Context miContexto;

    private int usuarioLogeado = Singleton.getInstance().getUserId();

    Bundle  contenidoActividadRecetas;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.layout_single_receta);
        contenidoActividadRecetas = getIntent().getExtras();

        servicios = RetrofitClient.getClient().create(RecetaService.class);
        myRecycler = findViewById(R.id.rvRecetas);
        myRecycler.addItemDecoration(new DividerItemDecoration(miContexto, DividerItemDecoration.VERTICAL));
        myRecycler.setLayoutManager(new LinearLayoutManager(miContexto));


        Log.d("ID Receta", "id de la receta viendose: " +  Singleton.getInstance().getRecetaId());

        creaSingle();
        listarComentarios();
    }

    private void listarComentarios() {
        Call<List<Comentario>> call = servicios.listarComentarios();
        call.enqueue(new Callback<List<Comentario>>() {
            @Override
            public void onResponse(Call<List<Comentario>> call, Response<List<Comentario>> response) {
                if (response.isSuccessful()) {
                    comentarios = response.body();
                    for (Comentario comentario : comentarios) {

                    }
//                    configurarAdaptador();
                } else {
                    Toast.makeText(miContexto, "Error al obtener las recetas", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Comentario>> call, Throwable t) {
                Toast.makeText(miContexto, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void creaSingle(){
        TextView textViewNombre = findViewById(R.id.nombre);
        TextView textViewOrigen = findViewById(R.id.origen);
        TextView textViewDescripcion = findViewById(R.id.descripcion);

        String nombreRecibido = contenidoActividadRecetas.getString("nombre");
        String origenRecibido = contenidoActividadRecetas.getString("origen");
        String descripcionRecibida = contenidoActividadRecetas.getString("descripcion");

        textViewNombre.setText(nombreRecibido);
        textViewOrigen.setText(origenRecibido);
        textViewDescripcion.setText(descripcionRecibida);


    }
}