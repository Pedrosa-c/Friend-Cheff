
package com.example.aplicacion_1.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.aplicacion_1.Clases.Comentario;
import com.example.aplicacion_1.R;

import java.util.List;

public class ComentarioAdapter extends RecyclerView.Adapter<ComentarioAdapter.ComentariosViewHolder> {
    private Context contexto;
    private List<Comentario> misComentarios;
    private OnItemClickListener listener;

    public ComentarioAdapter(Context contexto, List<Comentario> listaComentarios) {
        this.contexto = contexto;
        this.misComentarios = listaComentarios;
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    @Override
    public ComentariosViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View miVistaComentarios = LayoutInflater.from(contexto).inflate(R.layout.comentario_compuesto, parent, false);
        return new ComentariosViewHolder(miVistaComentarios, listener);
    }

    @Override
    public void onBindViewHolder(ComentariosViewHolder holder, int position) {
        Comentario comentario_i = misComentarios.get(position);

        holder.texto.setText(comentario_i.getTexto());
        holder.valoracion.setText(comentario_i.getValoracion());
    }

    @Override
    public int getItemCount() {
        return misComentarios.size();
    }

    public static class ComentariosViewHolder extends RecyclerView.ViewHolder {
        TextView texto, valoracion;

        public ComentariosViewHolder(View itemView, final OnItemClickListener listener) {
            super(itemView);
            texto = itemView.findViewById(R.id.texto);
            valoracion = itemView.findViewById(R.id.valoracion);

        }
    }
}
