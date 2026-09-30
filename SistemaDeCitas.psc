SubProceso RegistarDoctor(idsDoctores Por Referencia, totalDoctores Por Referencia)
	Escribir "Registro de doctor iniciado"
	Definir idDoctor, nombreDoctor, especialidad Como Cadena
	Definir indice Como Entero
	Definir idDuplicado Como Logico
	
	Escribir "Ingresa el ID del doctor:"
	Leer idDoctor
	Escribir "Ingresa el nombre completo del doctor:"
	Leer nombreDoctor
	Escribir "Ingresa la especialidad"
	Leer especialidad
	Si idDoctor = "" O nombreDoctor = "" O especialidad = "" Entonces
		Escribir "Error Favor de llenar todos los campos"
	SiNo
		idDuplicado <- Falso
		Si totalDoctores > 0 Entonces
			Para indice <- 1 Hasta totalDoctores Hacer
				Si idsDoctores[indice] = idDoctor Entonces
					idDuplicado <- Verdadero
				FinSi
			FinPara
		FinSi
        Si idDuplicado Entonces
            Escribir "Error: ya existe un doctor con ese ID"
        SiNo
            totalDoctores <- totalDoctores + 1
            idsDoctores[totalDoctores] <- idDoctor
            Escribir "Doctor registrado correctamente"
        FinSi
	FinSi
FinSubProceso
SubProceso RegistrarPaciente(idsPacientes Por Referencia, totalPacientes Por Referencia)
	Definir  idPaciente, nombrePaciente como cadena
	Definir indice Como Entero
	Definir idDuplicado Como Logico
	
	Escribir "Ingresa el ID del paciente:"
	Leer idPaciente
	Escribir "Ingresa el nombre completo del paciente:"
	Leer nombrePaciente
	Si idPaciente = "" O nombrePaciente = "" Entonces
		Escribir "Error Captura la información obligatoria"
	SiNo
		idDuplicado <- Falso
		Si totalPacientes > 0 Entonces
			Para indice <- 1 Hasta totalPacientes Hacer
				Si idsPacientes[indice] = idPaciente Entonces
					idDuplicado <- Verdadero
				FinSi
			FinPara
		FinSi
		Si idDuplicado Entonces
			Escribir "Error ya existe usuario con ese ID"
		SiNo
			totalPacientes <- totalPacientes + 1
			idsPacientes[totalPacientes] <- idPaciente
			Escribir "Paciente registrado exitosamente"
		FinSi
	FinSi
FinSubProceso
SubProceso CrearCita(idsCitas Por Referencia, totalCitas Por Referencia, idsDoctores, totalDoctores, idsPacientes, totalPacientes)
    Definir idCita, fechaCita, horaCita, motivoCita Como Cadena
    Definir idDoctorCita, idPacienteCita Como Cadena
	Definir indice Como Entero
    Definir doctorEncontrado, pacienteEncontrado Como Logico
    Definir idCitaDuplicado Como Logico
	
    Escribir "Ingresa el ID de la cita:"
    Leer idCita
    Escribir "Ingresa la fecha (AAAA-MM-DD):"
    Leer fechaCita
    Escribir "Ingresa la hora (HH:MM):"
    Leer horaCita
    Escribir "Ingresa el motivo:"
    Leer motivoCita
    Escribir "Ingresa el ID del doctor:"
    Leer idDoctorCita
    Escribir "Ingresa el ID del paciente:"
    Leer idPacienteCita
	Si idCita = "" O fechaCita = "" O horaCita = "" O motivoCita = "" O idDoctorCita = "" O idPacienteCita = "" Entonces
        Escribir "Error captura la información solicitada"
    SiNo
		doctorEncontrado <- Falso
        Si totalDoctores > 0 Entonces
            Para indice <- 1 Hasta totalDoctores Hacer
                Si idsDoctores[indice] = idDoctorCita Entonces
                    doctorEncontrado <- Verdadero
                FinSi
            FinPara
        FinSi
		
        Si doctorEncontrado = Falso Entonces
            Escribir "Error ID incorrecto"
        SiNo
			pacienteEncontrado <- Falso
            Si totalPacientes > 0 Entonces
                Para indice <- 1 Hasta totalPacientes Hacer
                    Si idsPacientes[indice] = idPacienteCita Entonces
                        pacienteEncontrado <- Verdadero
                    FinSi
                FinPara
            FinSi
			
            Si pacienteEncontrado = Falso Entonces
                Escribir "Error ningun ID coincide con el establecido"
            SiNo
				idCitaDuplicado <- Falso
                Si totalCitas > 0 Entonces
                    Para indice <- 1 Hasta totalCitas Hacer
                        Si idsCitas[indice] = idCita Entonces
                            idCitaDuplicado <- Verdadero
                        FinSi
                    FinPara
                FinSi
				
                Si idCitaDuplicado Entonces
                    Escribir "Error ya existe una cita con ese ID"
                SiNo
                    totalCitas <- totalCitas + 1
                    idsCitas[totalCitas] <- idCita
                    Escribir "Cita registrada correctamente"
                FinSi
            FinSi
        FinSi
       
    FinSi
FinSubProceso
// Dentro de PSeInt se simulan los registros mediente arrelos 
// En el sistema Java, administradores, doctores, pacientes y cita
Algoritmo SistemaDeCitas
	Escribir "Hola, bienvenido al sistema de citas médicas"
	Definir usuario, contrasena Como Cadena
	Definir opcion Como Entero
	Definir totalDoctores Como Entero
	Definir idsDoctores Como Cadena
	Dimension idsDoctores[100]
	totalDoctores <- 0
	Definir totalPacientes Como Entero
	Definir idsPacientes Como Cadena
	Dimension idsPacientes[100]
	totalPacientes <- 0
	Definir totalCitas Como Entero
	Definir idsCitas Como Cadena
	Dimension idsCitas[100]
	totalCitas <- 0
	Escribir  "Ingresa tu usuario:"
	Leer  usuario
	Escribir "Ingresa tu contraseña:"
	Leer contrasena
	Si usuario = "admin01" Y contrasena = "ClaveDemo2026" Entonces
		Escribir "Acceso autorizado"
		Repetir
			Escribir "MENÚ"
			Escribir "1. Registar doctor"
			Escribir "2. Registrar paciente"
			Escribir "3. Crear cita"
			Escribir "4. Salir"
			Leer opcion
			Segun opcion Hacer
				1:
					Escribir "Seleccionaste registrar doctor"
					RegistarDoctor(idsDoctores, totalDoctores)
				2: 
					Escribir "Seleccionaste registrar paciente"
					RegistrarPaciente(idsPacientes,totalPacientes)
				3:
					Escribir "Seleccionaste crear cita"
					CrearCita(idsCitas, totalCitas, idsDoctores, totalDoctores, idsPacientes, totalPacientes)
				4:
					Escribir "Saliste del sistema"
				De Otro Modo:
					Escribir "Opción no válida"
			FinSegun
		Hasta Que opcion = 4
	SiNo
		Escribir "Usuario o contraseña incorrectos"
	FinSi
FinAlgoritmo
