package com.consultorio.model;

public class Paciente extends Persona {
    public Paciente(String id, String nombreCompleto) {
        super(id, nombreCompleto);
    }

    @Override
    public String mostrarResumen() {
        return "Paciente: " + nombreCompleto + " - ID: " + id;
    }
}
