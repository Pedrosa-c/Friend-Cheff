package com.example.aplicacion_1.Clases;


/**
 * The Imagen interface represents an image.
 */
public class Imagen {
    private int id;
    private String ruta;
    private String formato;
    private int alto;
    private int ancho;

    public Imagen(int id, String ruta, String formato, int alto, int ancho) {
        this.id = id;
        this.ruta = ruta;
        this.formato = formato;
        this.alto = alto;
        this.ancho = ancho;
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

    public String getFormato() {
        return formato;
    }

    public void setFormato(String formato) {
        this.formato = formato;
    }

    public int getAlto() {
        return alto;
    }

    public void setAlto(int alto) {
        this.alto = alto;
    }

    public int getAncho() {
        return ancho;
    }

    public void setAncho(int ancho) {
        this.ancho = ancho;
    }

    public String toString() {
        return "Imagen [id=" + id + ", ruta=" + ruta + ", formato=" + formato + ", alto=" + alto + ", ancho=" + ancho + "]";
    }
}
