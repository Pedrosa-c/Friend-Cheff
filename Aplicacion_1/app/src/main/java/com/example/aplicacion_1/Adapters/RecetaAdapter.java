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

public class RecetaAdapter extends RecyclerView.Adapter<RecetaAdapter.RecetasViewHolder> {
    Context contexto;
    List<Receta> misRecetas;
    private OnItemClickListener listener;

    public RecetaAdapter(Context contexto, List<Receta> listaRecetas, int[] fotos) {
        this.contexto = contexto;
        misRecetas = listaRecetas;
    }

    public interface OnItemClickListener {
        void onItemClick(int receta);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    @Override
    public RecetasViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(contexto);
        View miVistaRecetas = inflater.inflate(R.layout.receta_compuesto, parent, false);
        return new RecetasViewHolder(miVistaRecetas);
    }

    @Override
    public void onBindViewHolder(RecetasViewHolder holder, int position) {
        Receta receta_i = misRecetas.get(position);

        Log.d("nombre: ", receta_i.getNombre());

        holder.nombre.setText(receta_i.getNombre());
        holder.descripcion.setText(receta_i.getDescripcion());
        //holder.itemView.setTag(receta_i); // Set the tag for the item view
    }

    @Override
    public int getItemCount() {
        return misRecetas.size();
    }

    public class RecetasViewHolder extends RecyclerView.ViewHolder {
        TextView nombre, descripcion;

        public RecetasViewHolder(View itemView) {
            super(itemView);
            nombre = itemView.findViewById(R.id.nombre);
            descripcion = itemView.findViewById(R.id.descripcion);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (listener != null && position != RecyclerView.NO_POSITION) {
                        Receta receta = (Receta) v.getTag();
                        listener.onItemClick(position);
                        Log.d("LISTENER", "Listener triggering on click");
                    }
                }
            });
        }
    }
}
