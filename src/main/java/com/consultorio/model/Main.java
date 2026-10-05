package com.consultorio.model;

import java.util.List;
import java.util.Scanner;

public class Main {
    private static DataService dataService;
    private static List<Administrador> administradores;
    private static List<Doctor> doctores;
    private static List<Paciente> pacientes;
    private static List<Cita> citas;
    private static Scanner teclado = new Scanner(System.in);

    public static void main(String[] args) {
        System.out.println("==============================================");
        System.out.println("SISTEMA CLINICO DE CITAS MEDICAS v1.0");
        System.out.println("==============================================");

        dataService = new DataService();
        administradores = dataService.cargarAdministradores();
        doctores = dataService.cargarDoctores();
        pacientes = dataService.cargarPacientes();
        citas = dataService.cargarCitas(doctores, pacientes);

        if (iniciarSesion()) {
            mostrarMenu();
        } else {
            System.out.println("\nError: Demasiados intentos fallidos. Programa terminado.");
        }
    }

    private static boolean iniciarSesion() {
        int intentos = 3;
        while (intentos > 0) {
            System.out.print("\nIngrese su identificador de Administrador: ");
            String user = teclado.nextLine().trim();
            System.out.print("Ingrese su contrasena: ");
            String pass = teclado.nextLine().trim();

            Administrador admin = administradores.stream()
                    .filter(a -> a.getId().equals(user))
                    .findFirst()
                    .orElse(null);

            if (admin != null && admin.verificarContrasena(pass)) {
                System.out.println("\nAcceso autorizado. Bienvenido.");
                return true;
            } else {
                intentos--;
                System.out.println("Error: Credenciales invalidas. Intentos restantes: " + intentos);
            }
        }
        return false;
    }

    private static void mostrarMenu() {
        int opcion = 0;
        do {
            try {
                System.out.println("\n--- MENU DE ACCIONES ---");
                System.out.println("1. Dar de alta a un Doctor");
                System.out.println("2. Dar de alta a un Paciente");
                System.out.println("3. Crear y agendar Cita Medica");
                System.out.println("4. Mostrar todas las Citas Agendadas");
                System.out.println("5. Salir del sistema");
                System.out.print("Seleccione una opcion: ");

                opcion = Integer.parseInt(teclado.nextLine());

                switch (opcion) {
                    case 1: registrarDoctor(); break;
                    case 2: registrarPaciente(); break;
                    case 3: crearCita(); break;
                    case 4: listarCitas(); break;
                    case 5: System.out.println("\nCerrando sesion. Cambios guardados de forma segura."); break;
                    default: System.out.println("Aviso: Opción invalida. Intente de nuevo.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Error: Ingrese un numero valido para el menú.");
            } catch (Exception e) {
                System.out.println("Ocurrio una anomalia inesperada: " + e.getMessage());
            }
        } while (opcion != 5);
    }

    private static void registrarDoctor() {
        System.out.println("\n[Alta de Doctor]");
        System.out.print("ID Unico del Doctor: ");
        String id = teclado.nextLine().trim();

        if (doctores.stream().anyMatch(d -> d.getId().equals(id))) {
            System.out.println("Error: Ya existe un doctor con ese ID.");
            return;
        }

        System.out.print("Nombre Completo: ");
        String nombre = teclado.nextLine().trim();
        System.out.print("Especialidad: ");
        String especialidad = teclado.nextLine().trim();

        if (id.isEmpty() || nombre.isEmpty() || especialidad.isEmpty()) {
            System.out.println("Error: Todos los campos son de captura obligatoria.");
            return;
        }

        Doctor nuevoDoc = new Doctor(id, nombre, especialidad);
        doctores.add(nuevoDoc);
        dataService.guardarDoctor(nuevoDoc);
        System.out.println("Doctor registrado e indexado correctamente.");
    }

    private static void registrarPaciente() {
        System.out.println("\n[Alta de Paciente]");
        System.out.print("ID Unico del Paciente: ");
        String id = teclado.nextLine().trim();

        if (pacientes.stream().anyMatch(p -> p.getId().equals(id))) {
            System.out.println("Error: Ya existe un paciente registrado con ese ID.");
            return;
        }

        System.out.print("Nombre Completo: ");
        String nombre = teclado.nextLine().trim();

        if (id.isEmpty() || nombre.isEmpty()) {
            System.out.println("Error: Campos incompletos.");
            return;
        }

        Paciente nuevoPac = new Paciente(id, nombre);
        pacientes.add(nuevoPac);
        dataService.guardarPaciente(nuevoPac);
        System.out.println("Paciente dado de alta de manera exitosa.");
    }

    private static void crearCita() {
        System.out.println("\n[Agendar Cita]");
        System.out.print("ID Unico de la Cita: ");
        String id = teclado.nextLine().trim();

        if (citas.stream().anyMatch(c -> c.getId().equals(id))) {
            System.out.println("Error: Ese ID de cita ya esta reservado.");
            return;
        }

        System.out.print("Fecha y Hora (Ej: 2026-11-15 10:30): ");
        String fechaHora = teclado.nextLine().trim();
        System.out.print("Motivo de consulta: ");
        String motivo = teclado.nextLine().trim();

        System.out.print("ID del Doctor Asignado: ");
        String idDoc = teclado.nextLine().trim();
        Doctor doc = doctores.stream().filter(d -> d.getId().equals(idDoc)).findFirst().orElse(null);

        if (doc == null) {
            System.out.println("Error: El ID del medico no coincide con ningun registro activo.");
            return;
        }

        System.out.print("ID del Paciente: ");
        String idPac = teclado.nextLine().trim();
        Paciente pac = pacientes.stream().filter(p -> p.getId().equals(idPac)).findFirst().orElse(null);

        if (pac == null) {
            System.out.println("Error: El ID del paciente no existe.");
            return;
        }

        Cita nuevaCita = new Cita(id, fechaHora, motivo, doc, pac);
        citas.add(nuevaCita);
        dataService.guardarCita(nuevaCita);
        System.out.println("Cita vinculada y agendada correctamente.");
    }

    private static void listarCitas() {
        System.out.println("\n--- LISTADO GENERAL DE CITAS CLINICAS ---");
        if (citas.isEmpty()) {
            System.out.println("No hay ninguna cita en el historial activo.");
            return;
        }
        for (Cita c : citas) {
            System.out.println(c.obtenerDetalle());
            System.out.println("----------------------------------------");
        }
    }
}
