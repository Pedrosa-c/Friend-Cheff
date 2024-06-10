package com.example.aplicacion_1.Clases;

public class Singleton {
    private static Singleton instance;
    private int userId;
    private int recetaId;

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

    public void setUserId(int id) {
        this.userId = id;
    }

    public int getRecetaId() {
        return recetaId;
    }

    public void setRecetaId(int id) {
        this.recetaId = id;
    }
}
