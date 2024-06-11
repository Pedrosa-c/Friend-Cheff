package com.example.aplicacion_1.Clases;

public class Singleton {
    private static Singleton instance;
    private int userId;
    private Receta receta;
    private Usuario usuario;

    private Singleton() { }

    public static Singleton getInstance() {
        if (instance == null) {
            instance = new Singleton();
        }
        return instance;
    }

    public int getUserId() {
        return userId;
    }

    public Usuario getUser() {
        return usuario;
    }

    public void setReceta(Receta receta){this.receta = receta;}

    public void setUsuario(Usuario usuario){this.usuario = usuario;}

    public void setUserId(int id) {
        this.userId = id;
    }

    public int getRecetaId() {
        return receta.getId();
    }

    public void setRecetaId(int id) {
        this.receta.setId(id);
    }
}
