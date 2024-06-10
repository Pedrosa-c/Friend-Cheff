package com.example.aplicacion_1.conexion;


import com.example.aplicacion_1.Clases.Comentario;
import com.example.aplicacion_1.Clases.Ingrediente;
import com.example.aplicacion_1.Clases.Receta;
import com.example.aplicacion_1.Clases.Usuario;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface RecetaService {

    // Método para obtener todas las recetas disponibles
    @GET("friendchef/ListadoRecetas")
    Call<List<Receta>> listarRecetas();

    // Método para obtener todos los usuarios disponibles
    @GET("friendchef/ListadoUsuarios")
    Call<List<Usuario>> listarUsuarios();

    // Método para obtener todos los usuarios disponibles
    @GET("friendchef/ListadoComentarios")
    Call<List<Comentario>> listarComentarios();

    // Método para filtrar recetas según un término de búsqueda
    @GET("friendchef/filterUsersByName")
    Call<List<Usuario>> filtrarUsuariosNombre(@Query("nombre") String nombre);


    // Método para añadir un nuevo usuario
    @FormUrlEncoded
    @POST("friendchef/anadirUsuario")
    Call<Void> anadirUsuario(
            @Field("id") int id,
            @Field("nombre") String nombre,
            @Field("telefono") String telefono,
            @Field("email") String email,
            @Field("password") String password
    );

    @FormUrlEncoded
    @POST("friendchef/anadirComentario")
    Call<Void> anadirComentario(
            @Field("id") int id,
            @Field("texto") String texto,
            @Field("valoracion") String valoracion,
            @Field("fecha") String fecha,
            @Field("hora") String hora,
            @Field("recetaId") int recetaId,
            @Field("usuarioId") int usuarioId
    );

}