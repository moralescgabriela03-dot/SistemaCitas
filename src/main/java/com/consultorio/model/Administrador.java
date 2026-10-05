package com.consultorio.model;

public class Administrador {
    private String id;
    private String contrasena;

    public Administrador(String id, String contrasena) {
        this.id = id;
        this.contrasena = contrasena;
    }

    public String getId() { return id; }

    public boolean verificarContrasena(String contrasenaIngresada) {
        return this.contrasena.equals(contrasenaIngresada);
    }
}
