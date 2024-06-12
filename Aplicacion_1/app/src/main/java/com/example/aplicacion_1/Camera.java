package com.example.aplicacion_1;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import java.io.IOException;

public class Camera extends AppCompatActivity {

    private static final String TAG = "CameraActivity";

    private ImageView imageView;

    // ActivityResultLauncher for requesting permission
    private final ActivityResultLauncher<String> requestPermissionLauncher =
            registerForActivityResult(new ActivityResultContracts.RequestPermission(), isGranted -> {
                if (isGranted) {
                    Log.d(TAG, "Permission granted. Opening gallery.");
                    openGallery();
                } else {
                    Log.d(TAG, "Permission denied.");
                    showSettingsDialog();
                }
            });

    // ActivityResultLauncher for opening the gallery
    private final ActivityResultLauncher<Intent> openGalleryLauncher =
            registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
                if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                    Uri imageUri = result.getData().getData();
                    Log.d(TAG, "Image selected.");
                    try {
                        Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), imageUri);
                        imageView.setImageBitmap(bitmap);
                        Log.d(TAG, "Image set in ImageView.");
                    } catch (IOException e) {
                        e.printStackTrace();
                        Log.e(TAG, "Error loading image.", e);
                    }
                } else {
                    Log.d(TAG, "No image selected or other activity result.");
                }
            });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_camera);

        imageView = findViewById(R.id.imageView);
        Button button = findViewById(R.id.button_camera);

        button.setOnClickListener(view -> {
            Log.d(TAG, "Button clicked");
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                Log.d(TAG, "Permission not granted. Requesting permission.");
                requestStoragePermission();
            } else {
                Log.d(TAG, "Permission granted. Opening gallery.");
                openGallery();
            }
        });
    }

    private void requestStoragePermission() {
        if (shouldShowRequestPermissionRationale(Manifest.permission.READ_EXTERNAL_STORAGE)) {
            showPermissionExplanationDialog();
        } else {
            Log.d(TAG, "Requesting storage permission.");
            requestPermissionLauncher.launch(Manifest.permission.READ_EXTERNAL_STORAGE);
        }
    }

    private void showPermissionExplanationDialog() {
        new AlertDialog.Builder(this)
                .setTitle("Permiso Necesario")
                .setMessage("Este permiso es necesario para acceder a tus imágenes. Por favor, otorga el permiso.")
                .setPositiveButton("OK", (dialog, which) -> requestPermissionLauncher.launch(Manifest.permission.READ_EXTERNAL_STORAGE))
                .setNegativeButton("Cancelar", (dialog, which) -> {
                    dialog.dismiss();
                    Toast.makeText(Camera.this, "Permiso denegado. No se puede abrir la galería.", Toast.LENGTH_SHORT).show();
                })
                .create()
                .show();
    }

    private void showSettingsDialog() {
        new AlertDialog.Builder(this)
                .setTitle("Permiso Necesario")
                .setMessage("El permiso para acceder al almacenamiento es necesario. Por favor, permite el acceso desde la configuración de la aplicación.")
                .setPositiveButton("Ir a Configuración", (dialog, which) -> openAppSettings())
                .setNegativeButton("Cancelar", (dialog, which) -> {
                    dialog.dismiss();
                    Toast.makeText(Camera.this, "Permiso denegado. No se puede abrir la galería.", Toast.LENGTH_SHORT).show();
                })
                .create()
                .show();
    }

    private void openAppSettings() {
        Intent intent = new Intent(android.provider.Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        Uri uri = Uri.fromParts("package", getPackageName(), null);
        intent.setData(uri);
        startActivity(intent);
    }

    private void openGallery() {
        Log.d(TAG, "Opening gallery.");
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        openGalleryLauncher.launch(intent);
    }
}
