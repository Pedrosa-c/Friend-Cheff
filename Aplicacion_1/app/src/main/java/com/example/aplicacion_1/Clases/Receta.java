package com.example.aplicacion_1.Clases;

import com.example.aplicacion_1.Enumerados.TipoAlergeno;

import java.util.Set;



// Esta interfaz representa una receta
/**
 * This interface represents a recipe.
 */
public class Receta {

    private int id;
    private String nombre;
    private Set<Integer> idImagenes;
    private String origen;
    private String descripcion;
    private Set<Integer> idIngredientes;
    private Set<Integer> idComentarios;
    private Float mediaValoracion;

    public Receta(int id, String nombre, Set<Integer> idImagenes, String origen, String descripcion, Set<Integer> idIngredientes, Set<Integer> idComentarios, Float mediaValoracion) {
        this.id = id;
        this.nombre = nombre;
        this.idImagenes = idImagenes;
        this.origen = origen;
        this.descripcion = descripcion;
        this.idIngredientes = idIngredientes;
        this.idComentarios = idComentarios;
        this.mediaValoracion = mediaValoracion;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Set<Integer> getIdImagenes() {
        return idImagenes;
    }

    public void setIdImagenes(Set<Integer> imagenes) {
        this.idImagenes = imagenes;
    }

    public String getOrigen() {
        return origen;
    }

    public void setOringen(String ubicacion) {
        this.origen = ubicacion;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Set<Integer> getIdIngredientes() {
        return idIngredientes;
    }

    public void setIdIngredientes(Set<Integer> ingredientes) {
        this.idIngredientes = ingredientes;
    }

    public void addIngrediente(Ingrediente ingrediente) {
        // Add implementation here
    }

    public void removeIngrediente(Ingrediente ingrediente) {
        // Add implementation here
    }

    public Set<Integer> getIdComentarios() {
        return idComentarios;
    }

    public void setIdComentarios(Set<Integer> comentarios) {
        this.idComentarios = comentarios;
    }

    public void addComentario(Comentario comentario) {
        // Add implementation here
    }

    public void removeComentario(Comentario comentario) {
        // Add implementation here
    }

    public Float getMediaValoracion() {
        return mediaValoracion;
    }

    public void setMediaValoracion(Float mediaValoracion) {
        this.mediaValoracion = mediaValoracion;
    }

    public void calculoMediaValoracion() {
        // Add implementation here
    }

    public boolean detectarAlergia(int idUsuario) {
        // Add implementation here
        return false;
    }

    public String toString() {
        return "Receta{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", idImagenes=" + idImagenes +
                ", origen='" + origen + '\'' +
                ", descripcion='" + descripcion + '\'' +
                ", idIngredientes=" + idIngredientes +
                ", idComentarios=" + idComentarios +
                ", mediaValoracion=" + mediaValoracion +
                '}';
    }
}
