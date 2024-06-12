package com.example.aplicacion_1.conexion;


import com.example.aplicacion_1.Clases.Comentario;
import com.example.aplicacion_1.Clases.Imagen;
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

    // Método para obtener todas las imagens disponibles
    @GET("friendchef/ListadoImagenes")
    Call<List<Imagen>> listarImagenes();

    // Método para obtener todos los ingredientes disponibles
    @GET("friendchef/ListadoIngredientes")
    Call<List<Ingrediente>> listarIngredientes();

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

    // Método para obtener un usuario por ID
    @FormUrlEncoded
    @POST("friendchef/ObtenerUsuario")
    Call<Usuario> obtenerUsuario(
            @Field("usuarioId") int usuarioId
    );

    @FormUrlEncoded
    @POST("friendchef/actualizarUsuario")
    Call<Void> actualizarUsuario(
            @Field("id") int id,
            @Field("nombre") String nombre,
            @Field("telefono") String telefono,
            @Field("email") String email,
            @Field("contraseña") String contraseña,
            @Field("idAlergias") List<Integer> idAlergias,  // Usamos String para listas
            @Field("idRecetas") List<Integer> idRecetas,    // Usamos String para listas
            @Field("idComentarios") List<Integer> idComentarios,  // Usamos String para listas
            @Field("idAmigos") List<Integer> idAmigos       // Usamos String para listas
    );
    @FormUrlEncoded
    @POST("friendchef/anadirReceta")
    Call<Void> anadirReceta(
            @Field("id") int id,
            @Field("nombre") String nombre,
            @Field("descripcion") String descripcion,
            @Field("origen") String origen,
            @Field("ingredientes") List<Integer> ingredientes,
            @Field("imagenes") List<Integer> imagenes  // Usamos String para listas
    );

    @FormUrlEncoded
    @POST("friendchef/anadirImagen")
    Call<Void> anadirImagen(
        @Field("id") int id,
        @Field("ruta") String nombre,
        @Field("usuarioId") int usuarioId
    );

    @FormUrlEncoded
    @POST("friendchef/actualizarReceta")
    Call<Void> actualizarReceta(
            @Field("id") int id,
            @Field("nombre") String nombre,
            @Field("descripcion") String descripcion,
            @Field("origen") String origen,
            @Field("ingredientes") List<Integer> ingredientes,  // Usamos String para listas
            @Field("imagenes") List<Integer> imagenes    // Usamos String para listas
    );

    @FormUrlEncoded
    @POST("friendchef/filtrarRecetas")
    Call<List<Receta>> filtrarRecetas(
            @Field("nombre") String nombre,
            @Field("ingrediente") String ingrediente,
            @Field("origen") String origen
    );
}