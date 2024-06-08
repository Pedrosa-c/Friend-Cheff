package com.example.aplicacion_1.Recetas;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.aplicacion_1.Adapters.RecetaAdapter;
import com.example.aplicacion_1.Clases.Receta;
import com.example.aplicacion_1.MainMenu;
import com.example.aplicacion_1.R;

import java.util.HashSet;
import java.util.Set;

public class Recetas extends AppCompatActivity {

    private int[] listaFotos;
    private Receta[] recetas;
    RecyclerView myRecycler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_recetas);

        listaFotos();
        recetasList();
        myRecycler = findViewById(R.id.rvRecetas);//Esta cogiendo el layout del recycler.
        myRecycler.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL));
        RecetaAdapter misRecetas = new RecetaAdapter(this, recetas, listaFotos);//Este es el adapter que monta cada ViewHolder.

        myRecycler.setLayoutManager(new LinearLayoutManager(this));//Se el pasa al recycler la layout donde mostrar
        myRecycler.setAdapter(misRecetas);//Se le pasa el adapter.

    }

    public void toSingleReceta(View vista){

        TextView textname = findViewById(R.id.nombre);
        String nombre = textname.getText().toString();

        Intent toSingleReceta = new Intent(this, singleReceta.class);
        toSingleReceta.putExtra("titulo",nombre);

        startActivity(toSingleReceta);
    }

    private void listaFotos(){
        listaFotos = new int[4];
        listaFotos[0] = R.drawable.foto1;
        listaFotos[1] = R.drawable.foto2;
        listaFotos[2] = R.drawable.foto3;
        listaFotos[3] = R.drawable.foto4;
    }

    private void recetasList(){
        /*
         * Esto podría ser una lista de usuarios, la única diferencia es que tendrías que cargar
         * la lista desde una BD o desde un archivo externo.
         */

        recetas = new Receta[4];

        Set<Integer> imagenes1 = new HashSet<>();
        imagenes1.add(101);
        imagenes1.add(102);

        Set<Integer> ingredientes1 = new HashSet<>();
        ingredientes1.add(201);
        ingredientes1.add(202);

        Set<Integer> comentarios1 = new HashSet<>();
        comentarios1.add(301);
        comentarios1.add(302);

        Receta receta1 = new Receta(1, "Paella", imagenes1, "España", "Deliciosa paella con mariscos", ingredientes1, comentarios1, 4.5f);

        Set<Integer> imagenes2 = new HashSet<>();
        imagenes2.add(103);
        imagenes2.add(104);

        Set<Integer> ingredientes2 = new HashSet<>();
        ingredientes2.add(203);
        ingredientes2.add(204);

        Set<Integer> comentarios2 = new HashSet<>();
        comentarios2.add(303);
        comentarios2.add(304);

        Receta receta2 = new Receta(2, "Tacos", imagenes2, "México", "Tacos auténticos con carne asada", ingredientes2, comentarios2, 4.7f);

        Set<Integer> imagenes3 = new HashSet<>();
        imagenes3.add(105);
        imagenes3.add(106);

        Set<Integer> ingredientes3 = new HashSet<>();
        ingredientes3.add(205);
        ingredientes3.add(206);

        Set<Integer> comentarios3 = new HashSet<>();
        comentarios3.add(305);
        comentarios3.add(306);

        Receta receta3 = new Receta(3, "Sushi", imagenes3, "Japón", "Sushi fresco y sabroso", ingredientes3, comentarios3, 4.8f);

        Set<Integer> imagenes4 = new HashSet<>();
        imagenes4.add(107);
        imagenes4.add(108);

        Set<Integer> ingredientes4 = new HashSet<>();
        ingredientes4.add(207);
        ingredientes4.add(208);

        Set<Integer> comentarios4 = new HashSet<>();
        comentarios4.add(307);
        comentarios4.add(308);

        Receta receta4 = new Receta(4, "Pizza", imagenes4, "Italia", "Pizza con queso y pepperoni", ingredientes4, comentarios4, 4.6f);

        recetas[0] = receta1;
        recetas[1] = receta2;
        recetas[2] = receta3;
        recetas[3] = receta4;
    }

    public void toMenu(View view) {
        Intent anterior = new Intent(this, MainMenu.class);
        startActivity(anterior);
    }
}