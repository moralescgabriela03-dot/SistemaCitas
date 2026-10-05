package com.consultorio.model;

public class Cita {
    private String id;
    private String fechaHora;
    private String motivo;
    private Doctor doctor;
    private Paciente paciente;

    public Cita(String id, String fechaHora, String motivo, Doctor doctor, Paciente paciente) {
        this.id = id;
        this.fechaHora = fechaHora;
        this.motivo = motivo;
        this.doctor = doctor;
        this.paciente = paciente;
    }

    public String getId() { return id; }
    public String getFechaHora() { return fechaHora; }
    public String getMotivo() { return motivo; }
    public Doctor getDoctor() { return doctor; }
    public Paciente getPaciente() { return paciente; }

    public String obtenerDetalle() {
        return "Cita #" + id + " | Fecha/Hora: " + fechaHora + " | Motivo: " + motivo +
                "\n  -> " + doctor.mostrarResumen() +
                "\n  -> " + paciente.mostrarResumen();
    }
}
