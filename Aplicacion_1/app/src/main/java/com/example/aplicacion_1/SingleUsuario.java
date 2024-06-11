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

import com.example.aplicacion_1.Adapters.RecetaAdapter;
import com.example.aplicacion_1.Clases.Receta;
import com.example.aplicacion_1.Clases.Usuario;
import com.example.aplicacion_1.Clases.Singleton;
import com.example.aplicacion_1.conexion.RecetaService;
import com.example.aplicacion_1.conexion.RetrofitClient;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SingleUsuario extends AppCompatActivity {

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
                }
            }

            @Override
            public void onFailure(Call<List<Receta>> call, Throwable t) {
                Log.d("LISTA RECETAS", "CONEXION FALLIDA: " + t.getMessage());
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

    // Editar el usuario registrado y añadir en su idAmigos el id del usuario que se va a seguir
    public void seguir(View v) {
        Call<Usuario> call = servicios.obtenerUsuario(Singleton.getInstance().getUserId());

        call.enqueue(new Callback<Usuario>() {
            @Override
            public void onResponse(Call<Usuario> call, Response<Usuario> response) {
                if (response.isSuccessful() && response.body() != null) {
                    usuarioLogeado = response.body();

                    TextView error = findViewById(R.id.error);

                    // Actualizar la lista de amigos
                    int id = usuarioLogeado.getIdUsuario();
                    String nombre = usuarioLogeado.getNombre();
                    String telefono = usuarioLogeado.getTelefono();
                    String email = usuarioLogeado.getEmail();
                    String password = usuarioLogeado.getContraseña();
                    List<Integer> idAlergias = usuarioLogeado.getIdAlergias();
                    List<Integer> idRecetas = usuarioLogeado.getIdRecetas();
                    List<Integer> idComentarios = usuarioLogeado.getIdComentarios();
                    List<Integer> idAmigos = usuarioLogeado.getIdAmigos();

                    if (!idAmigos.contains(usuario.getIdUsuario())) {
                        idAmigos.add(usuario.getIdUsuario());
                    }

                    for(int i : idAmigos){
                        if(i == usuario.getIdUsuario() || i == usuarioLogeado.getIdUsuario()){
                            error.setText("Ya estas siguiendo a este usuario");
                        }
                    }

                    // Llama al método actualizarUsuario para añadir el nuevo usuario
                    Call<Void> updateCall = servicios.actualizarUsuario(id, nombre, telefono, email, password, idAlergias, idRecetas, idComentarios, idAmigos);
                    updateCall.enqueue(new Callback<Void>() {
                        @Override
                        public void onResponse(Call<Void> call, Response<Void> response) {
                            if (response.isSuccessful()) {
                                // Ir a la actividad anterior después de actualizar el usuario
                                Intent anterior = new Intent(SingleUsuario.this, MainMenu.class);
                                startActivity(anterior);
                            }
                        }

                        @Override
                        public void onFailure(Call<Void> call, Throwable t) {
                            Log.d("Usuarios", "Fallo de conexion al añadir el usuario: " + t.getMessage());
                        }
                    });
                } else {
                    Log.d("Usuarios", "Fallo al obtener el usuario: " + response.message());
                }
            }

            @Override
            public void onFailure(Call<Usuario> call, Throwable t) {
                Log.d("Usuarios", "Fallo de conexion al obtener el usuario: " + t.getMessage());
            }
        });
    }
}
