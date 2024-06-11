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
import android.widget.Toast;

import com.example.aplicacion_1.Clases.Singleton;

public class Subir_Photo extends AppCompatActivity {

    private static final int PICK_IMAGE = 1;
    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_upload_photo);

        Button btnSelectPhoto = findViewById(R.id.btnSelectPhoto);
        imageView = findViewById(R.id.imageView);

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
            Uri selectedImage = data.getData();
            Log.d("la foto", "lafoto " +  data.getData());
            if (selectedImage != null) {
                imageView.setImageURI(selectedImage);
            } else {
                Toast.makeText(this, "Error al seleccionar la imagen", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
