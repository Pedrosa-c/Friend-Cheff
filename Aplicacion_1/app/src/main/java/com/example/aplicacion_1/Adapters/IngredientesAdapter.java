package com.example.aplicacion_1.Adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.aplicacion_1.Clases.Ingrediente;
import com.example.aplicacion_1.R;

import java.util.List;

public class IngredientesAdapter extends RecyclerView.Adapter<IngredientesAdapter.IngredientesViewHolder> {
    private Context contexto;
    private List<Ingrediente> misIngredientes;
    private OnItemClickListener listener;

    public IngredientesAdapter(Context contexto, List<Ingrediente> listaIngredientes) {
        this.contexto = contexto;
        this.misIngredientes = listaIngredientes;
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public IngredientesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View miVistaIngredientes = LayoutInflater.from(contexto).inflate(R.layout.ingredientes_compuesto, parent, false);
        return new IngredientesViewHolder(miVistaIngredientes, listener);
    }

    @Override
    public void onBindViewHolder(@NonNull IngredientesViewHolder holder, int position) {
        Ingrediente ingrediente_i = misIngredientes.get(position);

        // Agregar logs para depuración
        Log.d("IngredienteAdapter", "Binding ingrediente: " + ingrediente_i.getNombre() + ", posición: " + position);

        holder.nombre.setText(ingrediente_i.getNombre());
        holder.ingrediente.setText(ingrediente_i.getDescripcion());
    }

    @Override
    public int getItemCount() {
        // Agregar log para verificar tamaño de la lista
        Log.d("IngredienteAdapter", "Item count: " + misIngredientes.size());
        return misIngredientes.size();
    }

    public static class IngredientesViewHolder extends RecyclerView.ViewHolder {
        public TextView nombre;
        public TextView ingrediente;

        public IngredientesViewHolder(View itemView, final OnItemClickListener listener) {
            super(itemView);
            nombre = itemView.findViewById(R.id.nombre);
            ingrediente = itemView.findViewById(R.id.ingrediente);

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
