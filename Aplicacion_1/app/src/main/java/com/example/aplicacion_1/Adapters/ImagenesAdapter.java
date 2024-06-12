package com.example.aplicacion_1.Adapters;

import android.content.ContentResolver;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.aplicacion_1.Clases.Imagen;
import com.example.aplicacion_1.R;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

public class ImagenesAdapter extends RecyclerView.Adapter<ImagenesAdapter.ImagenesViewHolder> {
    private Context contexto;
    private List<Imagen> misImagenes;
    private OnItemClickListener listener;

    public ImagenesAdapter(Context contexto, List<Imagen> listaImagenes) {
        this.contexto = contexto;
        this.misImagenes = listaImagenes;
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    @Override
    public ImagenesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View miVistaImagenes = LayoutInflater.from(contexto).inflate(R.layout.imagen_compuesto, parent, false);
        return new ImagenesViewHolder(miVistaImagenes, listener);
    }

    @Override
    public void onBindViewHolder(ImagenesViewHolder holder, int position) {
        Imagen imagen_i = misImagenes.get(position);

        // Agregar logs para depuración
        Log.d("ImagenesAdapter", "Binding imagen: " + imagen_i.getId() + ", posición: " + position);

        // Usar AsyncTask para cargar la imagen
        new LoadImageTask(holder.fotoComida, contexto).execute(imagen_i.getRuta());
    }

    @Override
    public int getItemCount() {
        // Agregar log para verificar tamaño de la lista
        Log.d("ImagenesAdapter", "Item count: " + misImagenes.size());
        return misImagenes.size();
    }

    public static class ImagenesViewHolder extends RecyclerView.ViewHolder {
        ImageView fotoComida;
        public ImageView confirmar;

        public ImagenesViewHolder(View itemView, final OnItemClickListener listener) {
            super(itemView);
            fotoComida = itemView.findViewById(R.id.fotoComida);
            confirmar = itemView.findViewById(R.id.confirmar);

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

    private static class LoadImageTask extends AsyncTask<String, Void, Bitmap> {
        private ImageView imageView;
        private Context context;

        public LoadImageTask(ImageView imageView, Context context) {
            this.imageView = imageView;
            this.context = context;
        }

        @Override
        protected Bitmap doInBackground(String... urls) {
            String urlDisplay = urls[0];
            Bitmap bitmap = null;
            try {
                Uri imageUri = Uri.parse(urlDisplay);
                // Copiar el contenido a una ubicación accesible
                Uri localUri = copyContentToLocalUri(context, imageUri);
                if (localUri != null) {
                    ContentResolver resolver = context.getContentResolver();
                    InputStream inputStream = resolver.openInputStream(localUri);
                    if (inputStream != null) {
                        bitmap = BitmapFactory.decodeStream(inputStream);
                        inputStream.close();
                    }
                }
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return bitmap;
        }

        @Override
        protected void onPostExecute(Bitmap result) {
            if (result != null) {
                imageView.setImageBitmap(result);
            } else {
                // Set a placeholder or error image in case of failure
                imageView.setImageResource(R.drawable.foto1);
            }
        }

        private Uri copyContentToLocalUri(Context context, Uri contentUri) {
            ContentResolver resolver = context.getContentResolver();
            try (InputStream inputStream = resolver.openInputStream(contentUri)) {
                if (inputStream != null) {
                    File localFile = new File(context.getCacheDir(), "temp_image.jpg");
                    try (OutputStream outputStream = new FileOutputStream(localFile)) {
                        byte[] buffer = new byte[1024];
                        int length;
                        while ((length = inputStream.read(buffer)) > 0) {
                            outputStream.write(buffer, 0, length);
                        }
                        return Uri.fromFile(localFile);
                    }
                }
            } catch (Exception e) {
                Log.e("CopyContent", "Error copying content to local URI", e);
            }
            return null;
        }
    }
}
