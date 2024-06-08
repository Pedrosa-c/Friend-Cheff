package com.example.aplicacion_1.Clases;

import com.example.aplicacion_1.Enumerados.TipoAlergeno;

/**
 * The Alergeno interface represents an allergen.
 */
public class Alergeno {

    private int id;
    private String nombre;
    private TipoAlergeno tipoAlergeno;

    public Alergeno() {
        // Default constructor
    }

    public Alergeno(int id, String nombre, TipoAlergeno tipoAlergeno) {
        this.id = id;
        this.nombre = nombre;
        this.tipoAlergeno = tipoAlergeno;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public TipoAlergeno getAlergeno() {
        return tipoAlergeno;
    }

    public void setAlergeno(TipoAlergeno tipoAlergeno) {
        this.tipoAlergeno = tipoAlergeno;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String toString() {
        return "AlergenoImpl{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", tipoAlergeno=" + tipoAlergeno +
                '}';
    }
}
