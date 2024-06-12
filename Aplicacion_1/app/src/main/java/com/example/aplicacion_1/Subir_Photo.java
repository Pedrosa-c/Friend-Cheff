package com.example.aplicacion_1;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.aplicacion_1.Clases.Imagen;
import com.example.aplicacion_1.MainMenu;
import com.example.aplicacion_1.Clases.Singleton;
import com.example.aplicacion_1.Clases.Usuario;
import com.example.aplicacion_1.conexion.RecetaService;
import com.example.aplicacion_1.conexion.RetrofitClient;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Subir_Photo extends AppCompatActivity {

    private static final int PICK_IMAGE = 1;
    private ImageView imageView;
    private Usuario usuario;
    public Uri selectedImage;
    private RecetaService servicios;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_upload_photo);

        usuario = Singleton.getInstance().getUserLogeado();

        Button btnSelectPhoto = findViewById(R.id.btnSelectPhoto);
        imageView = findViewById(R.id.imageView);

        // Inicializa el servicio de recetas utilizando Retrofit
        servicios = RetrofitClient.getClient().create(RecetaService.class);

        btnSelectPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGallery();
            }
        });
    }

    private void openGallery() {
        Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(galleryIntent, PICK_IMAGE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE && resultCode == RESULT_OK && data != null) {
            selectedImage = data.getData();
            Log.d("la foto", "lafoto " +  data.getData());
            if (selectedImage != null) {
                imageView.setImageURI(selectedImage);
            } else {
                Toast.makeText(this, "Error al seleccionar la imagen", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void subirImagen(View view) {
        Log.d("Imagen", "Añadiendo Imagen");

        lastIdImagen(new LastIdCallback() {
            @Override
            public void onLastIdObtained(int lastId) {
                int id = usuario.getIdUsuario();

                Call<Void> call = servicios.anadirImagen(lastId, selectedImage.toString(), id);
                call.enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        if (response.isSuccessful()) {
                            Log.d("Imagen", "Imagen añadida correctamente");
                        } else {
                            Log.d("Imagen", "Fallo al añadir la imagen: " + response.message());
                        }
                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {
                        Log.d("Imagen", "Fallo de conexión al añadir la imagen: " + t.getMessage());
                    }
                });
            }
        });

        Intent intent = new Intent(this, MainMenu.class);
        startActivity(intent);
    }


    public void lastIdImagen(LastIdCallback callback) {
        Call<List<Imagen>> call = servicios.listarImagenes();
        call.enqueue(new Callback<List<Imagen>>() {
            @Override
            public void onResponse(Call<List<Imagen>> call, Response<List<Imagen>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<Imagen> imagenes = response.body();
                    int lastId = 0;
                    if (!imagenes.isEmpty()) {
                        // Obtener el último ID de las imágenes
                        lastId = imagenes.get(imagenes.size() - 1).getId() + 1;
                    }
                    callback.onLastIdObtained(lastId);
                } else {
                    Log.d("Imagen", "Error al listar imágenes: " + response.message());
                    callback.onLastIdObtained(0); // En caso de error, devuelve 0 o un valor de error adecuado
                }
            }

            @Override
            public void onFailure(Call<List<Imagen>> call, Throwable t) {
                Log.d("Imagen", "Fallo de conexión al listar imágenes: " + t.getMessage());
                callback.onLastIdObtained(0); // En caso de fallo, devuelve 0 o un valor de error adecuado
            }
        });
    }


    public interface LastIdCallback {
        void onLastIdObtained(int lastId);
    }

}
