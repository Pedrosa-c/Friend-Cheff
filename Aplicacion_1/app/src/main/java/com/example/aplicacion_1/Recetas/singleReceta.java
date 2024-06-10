package com.example.aplicacion_1.Recetas;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DirectAction;
import android.os.Bundle;
import android.widget.TextView;

import com.example.aplicacion_1.R;

public class singleReceta extends AppCompatActivity {

    Bundle  contenidoActividadRecetas;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.layout_single_receta);
        contenidoActividadRecetas = getIntent().getExtras();
        creaSingle();
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