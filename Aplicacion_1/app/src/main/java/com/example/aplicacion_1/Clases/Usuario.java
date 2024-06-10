package com.example.aplicacion_1.Clases;

import java.util.ArrayList;
import java.util.List;

/**
 * The Usuario interface represents a user in the system.
 * It defines methods to get and set user information such as id, name, phone number, email, and password.
 * It also provides methods to manage user's allergies, recipes, comments, and friends.
 */
public class Usuario {

    private int id;
    private String nombre;
    private String telefono;
    private String email;
    private String contraseña;
    private List<Integer> idAlergias;
    private List<Integer> idRecetas;
    private List<Integer> idComentarios;
    private List<Integer> idAmigos;

    public Usuario(int idUsuario, String nombre, String telefono, String email, String contraseña) {
        this.id = idUsuario;
        this.nombre = nombre;
        this.telefono = telefono;
        this.email = email;
        this.contraseña = contraseña;
        this.idAlergias = new ArrayList<>();
        this.idRecetas = new ArrayList<>();
        this.idComentarios = new ArrayList<>();
        this.idAmigos = new ArrayList<>();
    }

    public int getIdUsuario() {
        return id;
    }

    public void setIdUsuario(int usuario) {
        this.id = usuario;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getContraseña() {
        return contraseña;
    }

    public void setContraseña(String contraseña) {
        this.contraseña = contraseña;
    }

    public List<Integer> getIdAlergias() {
        return idAlergias;
    }

    public void setAlergias(List<Integer> alergias) {
        this.idAlergias = alergias;
    }

    public void addAlergia(int alergia) {
        if (idAlergias == null) {
            idAlergias = new ArrayList<>();
        }
        idAlergias.add(alergia);
    }

    public List<Integer> getIdRecetas() {
        return idRecetas;
    }

    public void setRecetas(List<Integer> idRecetas) {
        this.idRecetas = idRecetas;
    }

    public void addReceta(int receta) {
        if (idRecetas == null) {
            idRecetas = new ArrayList<>();
        }
        idRecetas.add(receta);
    }

    public List<Integer> getIdComentarios() {
        return idComentarios;
    }

    public void setComentarios(List<Integer> idComentarios) {
        this.idComentarios = idComentarios;
    }

    public void addComentario(int comentario) {
        if (idComentarios == null) {
            idComentarios = new ArrayList<>();
        }
        idComentarios.add(comentario);
    }

    public List<Integer> getIdAmigos() {
        return idAmigos;
    }

    public void setAmigos(List<Integer> idAmigos) {
        this.idAmigos = idAmigos;
    }

    public void addAmigo(int amigo) {
        if (idAmigos == null) {
            idAmigos = new ArrayList<>();
        }
        idAmigos.add(amigo);
    }

    public String toString() {
        return "UsuarioImpl{" +
                "idUsuario=" + id +
                ", nombre='" + nombre + '\'' +
                ", telefono='" + telefono + '\'' +
                ", email='" + email + '\'' +
                ", contraseña='" + contraseña + '\'' +
                ", idAlergias=" + idAlergias +
                ", idRecetas=" + idRecetas +
                ", idComentarios=" + idComentarios +
                ", idAmigos=" + idAmigos +
                '}';
    }
}
