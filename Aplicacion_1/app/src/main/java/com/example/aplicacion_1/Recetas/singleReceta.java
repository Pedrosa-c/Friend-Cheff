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
import android.widget.TextView;
import android.widget.Toast;

import com.example.aplicacion_1.Adapters.ComentarioAdapter;
import com.example.aplicacion_1.Clases.Comentario;
import com.example.aplicacion_1.Clases.Singleton;
import com.example.aplicacion_1.R;
import com.example.aplicacion_1.conexion.RecetaService;
import com.example.aplicacion_1.conexion.RetrofitClient;
import com.example.aplicacion_1.newComentario;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class singleReceta extends AppCompatActivity {

    private List<Comentario> comentarios;
    private List<Comentario> comentariosMuestra = new ArrayList<>();
    private RecyclerView myRecycler;
    private RecetaService servicios;
    private Context miContexto;
    private int idReceta = Singleton.getInstance().getRecetaId();

    private int usuarioLogeado = Singleton.getInstance().getUserId();

    Bundle contenidoActividadRecetas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_single_receta);

        miContexto = this;

        contenidoActividadRecetas = getIntent().getExtras();

        servicios = RetrofitClient.getClient().create(RecetaService.class);
        myRecycler = findViewById(R.id.rvComentarios);
        myRecycler.addItemDecoration(new DividerItemDecoration(miContexto, DividerItemDecoration.VERTICAL));
        myRecycler.setLayoutManager(new LinearLayoutManager(miContexto));

        Log.d("ID Receta", "id de la receta viendose: " +  Singleton.getInstance().getRecetaId());

        listarComentarios();
        creaSingle();
    }

    private void listarComentarios() {
        Call<List<Comentario>> call = servicios.listarComentarios();
        call.enqueue(new Callback<List<Comentario>>() {
            @Override
            public void onResponse(Call<List<Comentario>> call, Response<List<Comentario>> response) {
                if (response.isSuccessful()) {
                    comentarios = response.body();
                    if (comentarios != null) {
                        Log.d("COMENTARIOS", "Comentarios a mostrar: " + comentarios);
                        for (Comentario comentario : comentarios) {
                            Log.d("COMENTARIOS", "ID COMENTARIO: " + comentario.getRecetaId());
                            Log.d("COMENTARIOS", "ID RECETA: " + idReceta);
                            if (comentario.getRecetaId() == idReceta) {
                                comentariosMuestra.add(comentario);
                            }
                        }
                        Log.d("COMENTARIOS", "Comentarios a mostrar: " + comentariosMuestra);

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                configurarAdaptador();
                            }
                        });
                    } else {
                        Log.d("COMENTARIOS", "No hay comentarios");
                    }
                } else {
                    Toast.makeText(miContexto, "Error al obtener los comentarios", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Comentario>> call, Throwable t) {
                Toast.makeText(miContexto, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void creaSingle() {
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

    private void configurarAdaptador() {
        if (comentariosMuestra == null || comentariosMuestra.isEmpty()) {
            Log.d("CONFIGURAR ADAPTADOR", "La lista de comentarios está vacía o es nula");
            return;
        }
        Log.d("CONFIGURAR ADAPTADOR", "Configurando el adaptador con los comentarios");
        ComentarioAdapter adapter = new ComentarioAdapter(miContexto, comentariosMuestra);
        myRecycler.setAdapter(adapter);

        // Log adicional para verificar el estado del adaptador
        if (myRecycler.getAdapter() != null) {
            Log.d("RECYCLER VIEW", "Adaptador asignado correctamente");
        } else {
            Log.d("RECYCLER VIEW", "Adaptador no asignado");
        }
    }

    public void toCrearComentario(View vista){
        Intent anterior = new Intent(vista.getContext(), newComentario.class);
        startActivity(anterior);
    }
}
