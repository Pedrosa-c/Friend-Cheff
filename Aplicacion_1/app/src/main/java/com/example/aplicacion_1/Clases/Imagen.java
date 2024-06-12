package com.example.aplicacion_1.Clases;


/**
 * The Imagen interface represents an image.
 */
public class Imagen{
    private int id;
    private String ruta;
    private int usuarioId;
    
    public Imagen(int id, String ruta, int usuarioId) {
        this.id = id;
        this.ruta = ruta;
        this.usuarioId = usuarioId;
    }
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getRuta() {
        return ruta;
    }
    public void setRuta(String ruta) {
        this.ruta = ruta;
    }
    public int getUsuarioId() {
        return usuarioId;
    }
    public void setUsuarioId(int usuarioId) {
        this.usuarioId = usuarioId;
    }
    public String toString() {
        return "Imagen [id=" + id + ", ruta=" + ruta + "]";
    }
}
