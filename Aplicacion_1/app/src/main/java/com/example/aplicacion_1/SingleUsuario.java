package com.example.aplicacion_1;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.aplicacion_1.Clases.Singleton;
import com.example.aplicacion_1.Clases.Usuario;

public class SingleUsuario extends AppCompatActivity {

    TextView nombre;
    Usuario usuario;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_single_usuario);

        usuario = Singleton.getInstance().getUser();
        nombre = findViewById(R.id.nombre);
        nombre.setText(usuario.getNombre());

    }

    //Edirtar el usuarioRegistrado y añadir en su idAmigos el id del usuario que se va a seguir
    public void seguir(View v){

    }

    //Listar las recetas del usuario
    public void listaRecetas(View v){

    }
}