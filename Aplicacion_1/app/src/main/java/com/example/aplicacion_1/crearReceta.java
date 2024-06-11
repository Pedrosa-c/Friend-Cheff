package com.example.aplicacion_1;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.aplicacion_1.Adapters.IngredientesAdapter;
import com.example.aplicacion_1.Adapters.RecetaAdapter;
import com.example.aplicacion_1.Clases.Ingrediente;
import com.example.aplicacion_1.Clases.Receta;
import com.example.aplicacion_1.Clases.Singleton;
import com.example.aplicacion_1.Clases.Usuario;
import com.example.aplicacion_1.Login.Login;
import com.example.aplicacion_1.Recetas.Recetas;
import com.example.aplicacion_1.Recetas.singleReceta;
import com.example.aplicacion_1.conexion.RecetaService;
import com.example.aplicacion_1.conexion.RetrofitClient;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class crearReceta extends AppCompatActivity {
    private RecyclerView myRecycler;
    private RecetaService servicios;
    private List<Ingrediente> ingredientes;
    private List<Integer> ingredientesSeleccionados = new ArrayList<>();
    private List<Receta> recetas;
    int idUsuarioLogeado = Singleton.getInstance().getUserId();
    int ultimoId;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_crear_receta);

        // Inicializa el RecyclerView
        myRecycler = findViewById(R.id.rvIngredientes);
        if (myRecycler == null) {
            Log.e("crearReceta", "RecyclerView no encontrado");
            return;
        }
        myRecycler.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        myRecycler.setLayoutManager(new LinearLayoutManager(this));

        // Inicializa el servicio de recetas utilizando Retrofit
        servicios = RetrofitClient.getClient().create(RecetaService.class);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        listarIngredientes();

        findViewById(R.id.bottom).setOnClickListener(v -> obtenerUltimoId());
    }

    private void listarIngredientes() {
        Call<List<Ingrediente>> call = servicios.listarIngredientes();
        call.enqueue(new Callback<List<Ingrediente>>() {
            @Override
            public void onResponse(@NonNull Call<List<Ingrediente>> call, @NonNull Response<List<Ingrediente>> response) {
                Log.d("LISTA Ingredientes", "CONEXION EXITOSA");
                if (response.isSuccessful() && response.body() != null) {
                    Log.d("LISTA Ingredientes", "ME HAN RESPONDIDO");
                    ingredientes = response.body();
                    configurarAdaptador();
                } else {
                    Log.d("LISTA Ingredientes", "RESPUESTA FALLIDA: " + response.message());
                    Toast.makeText(crearReceta.this, "Error en la respuesta: " + response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<Ingrediente>> call, @NonNull Throwable t) {
                Log.d("LISTA INGREDIENTES", "CONEXION FALLIDA: " + t.getMessage());
                Toast.makeText(crearReceta.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void configurarAdaptador() {
        if (ingredientes == null || ingredientes.isEmpty()) {
            Log.d("CONFIGURAR ADAPTADOR", "La lista de ingredientes está vacía o es nula");
            return;
        }

        Log.d("CONFIGURAR ADAPTADOR", "Configurando el adaptador con los ingredientes");
        IngredientesAdapter adapter = new IngredientesAdapter(this, ingredientes);
        myRecycler.setAdapter(adapter);

        adapter.setOnItemClickListener(new IngredientesAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                IngredientesAdapter.IngredientesViewHolder viewHolder = (IngredientesAdapter.IngredientesViewHolder) myRecycler.findViewHolderForAdapterPosition(position);
                if (viewHolder != null) {
                    int currentColor = viewHolder.nombre.getCurrentTextColor();
                    int greenColor = getResources().getColor(android.R.color.holo_green_dark);
                    int blackColor = getResources().getColor(android.R.color.black);

                    if (currentColor == greenColor) {
                        ingredientesSeleccionados.remove(Integer.valueOf(ingredientes.get(position).getId()));
                        viewHolder.nombre.setTextColor(blackColor);
                        viewHolder.ingrediente.setTextColor(blackColor);
                    } else {
                        ingredientesSeleccionados.add(ingredientes.get(position).getId());
                        viewHolder.nombre.setTextColor(greenColor);
                        viewHolder.ingrediente.setTextColor(greenColor);
                    }
                    Log.d("ONCLICK", "Color del texto actualizado en la posición: " + position);
                }
            }
        });
    }

    public void obtenerUltimoId() {
        Call<List<Receta>> call = servicios.listarRecetas();
        call.enqueue(new Callback<List<Receta>>() {
            @Override
            public void onResponse(Call<List<Receta>> call, Response<List<Receta>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<Receta> recetas = response.body();
                    if (recetas != null && !recetas.isEmpty()) {
                        ultimoId = recetas.get(recetas.size() - 1).getId() + 1;
                        Log.d("Recetas", "Último ID: " + ultimoId);
                        crear_Receta(ultimoId);
                    } else {
                        Log.d("Recetas", "Lista de recetas vacía");
                    }
                } else {
                    Log.d("Recetas", "Respuesta fallida: " + response.message());
                }
            }

            @Override
            public void onFailure(Call<List<Receta>> call, Throwable t) {
                Log.d("Recetas", "Error: " + t.getMessage());
            }
        });
    }

    public void crear_Receta(int id) {
        Log.d("Receta", "Añadiendo Receta");

        TextView name = findViewById(R.id.nombre);
        TextView description = findViewById(R.id.descripcion);
        TextView origin = findViewById(R.id.origen);

        String nombre = name.getText().toString();
        String descripcion = description.getText().toString();
        String origen = origin.getText().toString();

        Call<Void> call = servicios.anadirReceta(id, nombre, descripcion, origen, ingredientesSeleccionados, null);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    Log.d("Receta", "Receta añadida correctamente");
                    modificarUsuario();
                } else {
                    Log.d("Receta", "Fallo al añadir la receta: " + response.message());
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Log.d("Receta", "Fallo de conexión al añadir la receta: " + t.getMessage());
            }
        });
    }

    public void modificarUsuario() {
        // Llama al método anadirUsuario para añadir el nuevo usuario
        /*
        @Field("id") int id,
            @Field("nombre") String nombre,
            @Field("telefono") String telefono,
            @Field("email") String email,
            @Field("contraseña") String contraseña,
            @Field("idAlergias") List<Integer> idAlergias,  // Usamos String para listas
            @Field("idRecetas") List<Integer> idRecetas,    // Usamos String para listas
            @Field("idComentarios") List<Integer> idComentarios,  // Usamos String para listas
            @Field("idAmigos") List<Integer> idAmigos       // Usamos String para listas
         */
        List<Integer> idRecetas = new ArrayList<>();
        idRecetas.add(ultimoId);
        idRecetas.add(idUsuarioLogeado);
        Call<Void> call = servicios.actualizarUsuario(idUsuarioLogeado, null, null, null, null, null, idRecetas, null, null);
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
}
