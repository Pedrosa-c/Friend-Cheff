package com.example.aplicacion_1.Clases;
import com.example.aplicacion_1.Enumerados.Ranking;
import com.example.aplicacion_1.Enumerados.TipoAlergeno;


/**
 * The Comentario interface represents a comment in a system.
 * It provides methods to get and set the comment's id, text, rating, date, and time.
 */
public class Comentario {
    private int id;
    private String texto;
    private String valoracion;
    private String fecha;
    private String hora;
    private int recetaId;
    private int usuarioId;

    public Comentario(int id, String texto, String valoracion, String fecha, String hora, int recetaId, int usuarioId) {
        this.id = id;
        this.texto = texto;
        this.valoracion = valoracion;
        this.fecha = fecha;
        this.hora = hora;
        this.recetaId = recetaId;
        this.usuarioId = usuarioId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getRecetaId() {
        return recetaId;
    }

    public void setRecetaId(int recetaid) {
        this.recetaId = recetaid;
    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    public String getValoracion() {
        return valoracion.toString();
    }

    public void setValoracion(String valoracion) {
        this.valoracion = valoracion;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    public String toString() {
        return "Comentario{" +
                "id=" + id +
                ", texto='" + texto + '\'' +
                ", valoracion=" + valoracion +
                ", fecha='" + fecha + '\'' +
                ", hora='" + hora + '\'' +
                ", recetaId='" + recetaId + '\'' +
                ", usuarioId='" + usuarioId + '\'' +
                '}';
    }
}