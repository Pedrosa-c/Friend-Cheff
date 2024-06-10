package com.example.aplicacion_1.conexion;


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

    // Método para filtrar recetas según un término de búsqueda
    @GET("friendchef/filterUsersByName")
    Call<List<Usuario>> filtrarUsuariosNombre(@Query("nombre") String nombre);

    // Realiza una solicitud GET a /recetas con parámetros de consulta para filtrar recetas

    // Método para crear una nueva receta
    @POST("recetas")
    Call<Void> crearReceta(@Body Receta receta);

    // Realiza una solicitud POST a /recetas para crear una nueva receta con el
    // objeto Receta en el cuerpo de la solicitud

    // Método para borrar una receta existente
    @POST("recetas")
    Call<Void> borrarReceta(@Query("accion") String accion, @Query("nombre") String nombre);
    // Realiza una solicitud POST a /recetas con parámetros de consulta
    // para borrar una receta existente

    // Método para actualizar una receta existente
    @POST("recetas")
    Call<Void> actualizarReceta(@Query("accion") String accion, @Query("nombreViejo") String nombreViejo, @Body Receta receta);
    // Realiza una solicitud POST a /recetas con parámetros de consulta y un objeto Receta en el cuerpo
    // de la solicitud para actualizar una receta existente

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

}