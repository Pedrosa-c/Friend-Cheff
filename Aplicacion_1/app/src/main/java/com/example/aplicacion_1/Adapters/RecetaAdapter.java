package com.example.aplicacion_1.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.aplicacion_1.Clases.Receta;
import com.example.aplicacion_1.R;

public class RecetaAdapter extends RecyclerView.Adapter<RecetaAdapter.RecetasViewHolder> {
    Context contexto;
    Receta[] misRecetas;

    public RecetaAdapter(Context contexto, Receta[] listaRecetas, int[] fotos){
        this.contexto = contexto;
        misRecetas = listaRecetas;
    }

    @NonNull
    @Override
    public RecetasViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(contexto);
        View miVistaRecetas = inflater.inflate(R.layout.receta_compuesto,parent,false);
        RecetasViewHolder viewMisRecetas = new RecetasViewHolder(miVistaRecetas);

        return viewMisRecetas;
    }

    @Override
    public void onBindViewHolder(@NonNull RecetasViewHolder holder, int position) {
        Receta receta_i = misRecetas[position];
        holder.nombre.setText(receta_i.getNombre());
        holder.descripcion.setText(receta_i.getDescripcion());
    }

    @Override
    public int getItemCount() { return misRecetas.length;   }



    public class RecetasViewHolder extends RecyclerView.ViewHolder {

        TextView nombre, descripcion;

        public RecetasViewHolder(@NonNull View itemView) {
            super(itemView);
            nombre = itemView.findViewById(R.id.nombre);
            descripcion = itemView.findViewById(R.id.descripcion);
        }
    }
}