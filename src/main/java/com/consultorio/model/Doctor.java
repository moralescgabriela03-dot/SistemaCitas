package com.consultorio.model;

public class Doctor extends Persona {
    private String especialidad;

    public Doctor(String id, String nombreCompleto, String especialidad) {
        super(id, nombreCompleto);
        this.especialidad = especialidad;
    }

    public String getEspecialidad() { return especialidad; }

    @Override
    public String mostrarResumen() {
        return "Dr. " + nombreCompleto + " (" + especialidad + ") - ID: " + id;
    }
}