package com.example.aplicacion_1.Clases;

import java.util.Set;

/**
 * The Ingrediente interface represents an ingredient in a recipe.
 */
public class Ingrediente {

    private int id;
    private String nombre;
    private String descripcion;
    private Set<Integer> idAlergenos;

    public Ingrediente() {
        // Default constructor
    }

    public Ingrediente(int id, String nombre, String descripcion, Set<Integer> idAlergenos) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.idAlergenos = idAlergenos;
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

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Set<Integer> getIdAlergenos() {
        return idAlergenos;
    }

    public void setIdAlergenos(Set<Integer> alergenos) {
        this.idAlergenos = alergenos;
    }

    public String toString() {
        return "Ingrediente [id=" + id + ", nombre=" + nombre + ", descripcion=" + descripcion + ", idAlergenos=" + idAlergenos + "]";
    }
}

