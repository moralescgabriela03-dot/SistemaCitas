package com.consultorio.model;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class DataService {
    private static final String DB_DIR = "db/";
    private static final String FILE_ADMINS = DB_DIR + "administradores.csv";
    private static final String FILE_DOCTORS = DB_DIR + "doctores.csv";
    private static final String FILE_PATIENTS = DB_DIR + "pacientes.csv";
    private static final String FILE_CITAS = DB_DIR + "citas.csv";

    public DataService() {
        inicializarBaseDeDatos();
    }

    private void inicializarBaseDeDatos() {
        try {
            File directorio = new File(DB_DIR);
            if (!directorio.exists()) {
                directorio.mkdirs();
            }
            validarYCrearArchivo(new File(FILE_ADMINS), "admin01,ClaveDemo2026\nadmin02,Password2026");
            validarYCrearArchivo(new File(FILE_DOCTORS), "");
            validarYCrearArchivo(new File(FILE_PATIENTS), "");
            validarYCrearArchivo(new File(FILE_CITAS), "");
        } catch (Exception e) {
            System.out.println("Error critico al inicializar archivos: " + e.getMessage());
        }
    }

    private void validarYCrearArchivo(File archivo, String contenidoInicial) throws IOException {
        if (!archivo.exists()) {
            archivo.createNewFile();
            if (!contenidoInicial.isEmpty()) {
                try (BufferedWriter bw = new BufferedWriter(new FileWriter(archivo))) {
                    bw.write(contenidoInicial);
                }
            }
        }
    }

    public List<Administrador> cargarAdministradores() {
        List<Administrador> lista = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(FILE_ADMINS))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                String[] datos = linea.split(",");
                if (datos.length >= 2) {
                    lista.add(new Administrador(datos[0].trim(), datos[1].trim()));
                }
            }
        } catch (IOException e) {
            System.out.println("Error leyendo administradores: " + e.getMessage());
        }
        return lista;
    }

    public List<Doctor> cargarDoctores() {
        List<Doctor> lista = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(FILE_DOCTORS))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                String[] datos = linea.split(",");
                if (datos.length >= 3) {
                    lista.add(new Doctor(datos[0].trim(), datos[1].trim(), datos[2].trim()));
                }
            }
        } catch (IOException e) {
            System.out.println("Error leyendo doctores: " + e.getMessage());
        }
        return lista;
    }

    public List<Paciente> cargarPacientes() {
        List<Paciente> lista = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(FILE_PATIENTS))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                String[] datos = linea.split(",");
                if (datos.length >= 2) {
                    lista.add(new Paciente(datos[0].trim(), datos[1].trim()));
                }
            }
        } catch (IOException e) {
            System.out.println("Error leyendo pacientes: " + e.getMessage());
        }
        return lista;
    }

    public List<Cita> cargarCitas(List<Doctor> deDoctores, List<Paciente> dePacientes) {
        List<Cita> lista = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(FILE_CITAS))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                String[] datos = linea.split(",");
                if (datos.length >= 5) {
                    String idCita = datos[0].trim();
                    String fechaHora = datos[1].trim();
                    String motivo = datos[2].trim();
                    String idDoc = datos[3].trim();
                    String idPac = datos[4].trim();

                    Doctor doc = deDoctores.stream().filter(d -> d.getId().equals(idDoc)).findFirst().orElse(null);
                    Paciente pac = dePacientes.stream().filter(p -> p.getId().equals(idPac)).findFirst().orElse(null);

                    if (doc != null && pac != null) {
                        lista.add(new Cita(idCita, fechaHora, motivo, doc, pac));
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("Error leyendo citas: " + e.getMessage());
        }
        return lista;
    }

    public void guardarDoctor(Doctor doc) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(FILE_DOCTORS, true))) {
            bw.write(doc.getId() + "," + doc.getNombreCompleto() + "," + doc.getEspecialidad() + "\n");
        } catch (IOException e) {
            System.out.println("Error: No se pudo guardar el doctor en el archivo: " + e.getMessage());
        }
    }

    public void guardarPaciente(Paciente pac) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(FILE_PATIENTS, true))) {
            bw.write(pac.getId() + "," + pac.getNombreCompleto() + "\n");
        } catch (IOException e) {
            System.out.println("Error: No se pudo guardar el paciente en el archivo: " + e.getMessage());
        }
    }

    public void guardarCita(Cita cita) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(FILE_CITAS, true))) {
            bw.write(cita.getId() + "," + cita.getFechaHora() + "," + cita.getMotivo() + "," +
                    cita.getDoctor().getId() + "," + cita.getPaciente().getId() + "\n");
        } catch (IOException e) {
            System.out.println("Error: No se pudo guardar la cita en el archivo: " + e.getMessage());
        }
    }
}