package com.example.aplicacion_1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.aplicacion_1.Adapters.RecetaAdapter;
import com.example.aplicacion_1.Clases.Receta;
import com.example.aplicacion_1.Clases.Usuario;
import com.example.aplicacion_1.Clases.Singleton;
import com.example.aplicacion_1.MainMenu;
import com.example.aplicacion_1.R;
import com.example.aplicacion_1.conexion.RecetaService;
import com.example.aplicacion_1.conexion.RetrofitClient;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SingleUsuario extends AppCompatActivity {

    TextView nombre;
    Usuario usuario;

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

    //Listar las recetas del usuario
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
                    //Toast.makeText(Recetas.this, "Error en la respuesta: " + response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Receta>> call, Throwable t) {
                Log.d("LISTA RECETAS", "CONEXION FALLIDA: " + t.getMessage());
                //Toast.makeText(Recetas.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
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
    }

    //Edirtar el usuarioRegistrado y añadir en su idAmigos el id del usuario que se va a seguir
    public void seguir(View v){
        
        // Aquí defines los datos del nuevo usuario
        Log.d("Usuarios", "Añadiendo Usuario2");
        int id = usuario.getIdUsuario();
        String nombre = usuario.getNombre();
        String telefono = usuario.getTelefono();
        String email = usuario.getEmail();
        String password = usuario.getContraseña();
        List<Integer> idAlergias = usuario.getIdAlergias();
        List<Integer> idRecetas = usuario.getIdRecetas();
        List<Integer> idComentarios = usuario.getIdComentarios();
        List<Integer> idAmigos = usuario.getIdAmigos();


        // Llama al método anadirUsuario para añadir el nuevo usuario
        Call<Void> call = servicios.actualizarUsuario(id, nombre, telefono, email, password);
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