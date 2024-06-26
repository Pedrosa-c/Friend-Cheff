package com.example.aplicacion_1.Adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.aplicacion_1.Clases.Receta;
import com.example.aplicacion_1.R;

import java.util.List;

public class RecetaPersonalAdapter extends RecyclerView.Adapter<RecetaPersonalAdapter.RecetasViewHolder> {
    private Context contexto;
    private List<Receta> misRecetas;
    private OnItemClickListener listener;

    public RecetaPersonalAdapter(Context contexto, List<Receta> listaRecetas) {
        this.contexto = contexto;
        this.misRecetas = listaRecetas;
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    @Override
    public RecetasViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View miVistaRecetas = LayoutInflater.from(contexto).inflate(R.layout.receta_personal_compuesto, parent, false);
        return new RecetasViewHolder(miVistaRecetas, listener);
    }

    @Override
    public void onBindViewHolder(RecetasViewHolder holder, int position) {
        Receta receta_i = misRecetas.get(position);

        // Agregar logs para depuración
        Log.d("RecetaAdapter", "Binding receta: " + receta_i.getNombre() + ", posición: " + position);

        holder.nombre.setText(receta_i.getNombre());
        holder.descripcion.setText(receta_i.getDescripcion());
    }

    @Override
    public int getItemCount() {
        // Agregar log para verificar tamaño de la lista
        Log.d("RecetaAdapter", "Item count: " + misRecetas.size());
        return misRecetas.size();
    }

    public static class RecetasViewHolder extends RecyclerView.ViewHolder {
        TextView nombre, descripcion;

        public RecetasViewHolder(View itemView, final OnItemClickListener listener) {
            super(itemView);
            nombre = itemView.findViewById(R.id.nombre);
            descripcion = itemView.findViewById(R.id.descripcion);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (listener != null && position != RecyclerView.NO_POSITION) {
                        listener.onItemClick(position);
                    }
                }
            });
        }
    }
}
