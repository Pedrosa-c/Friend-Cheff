package com.example.aplicacion_1;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.aplicacion_1.Clases.Singleton;
import com.example.aplicacion_1.Recetas.Recetas;

public class MainMenu extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_main_menu);

        Log.d("ID USUARIO", "id del usuario logeado: " +  Singleton.getInstance().getUserId());
    }
    public void toSettings(View vista){
        Intent anterior = new Intent(vista.getContext(), Settings.class);
        startActivity(anterior);
    }
    public void toRecetas(View vista){
        Intent anterior = new Intent(vista.getContext(), Recetas.class);
        startActivity(anterior);
    }

    public void toCamera(View vista){
        Intent anterior = new Intent(vista.getContext(), Camera.class);
        startActivity(anterior);
    }
    public void openUploadPhotoActivity(View view) {
        Intent intent = new Intent(this, Subir_Photo.class);
        startActivity(intent);
    }

    public void toPersonalUser(View view) {
        Intent intent = new Intent(this, PersonalUser.class);
        startActivity(intent);
    }

    public void toCrearReceta(View view) {
        Intent intent = new Intent(view.getContext(), crearReceta.class);
        startActivity(intent);
    }

}
