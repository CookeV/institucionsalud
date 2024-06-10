CREATE DATABASE IF NOT EXISTS InstitucionSalud;

USE InstitucionSalud;

CREATE TABLE IF NOT EXISTS Especialidades (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL
);

CREATE TABLE IF NOT EXISTS Medicos (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    especialidad_id INT,
    FOREIGN KEY (especialidad_id) REFERENCES Especialidades(id)
);

CREATE TABLE IF NOT EXISTS Pacientes (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    fecha_nacimiento DATE NOT NULL
);

CREATE TABLE IF NOT EXISTS Diagnosticos (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL
);

CREATE TABLE IF NOT EXISTS Ingresos (
    id INT AUTO_INCREMENT PRIMARY KEY,
    paciente_id INT,
    medico_id INT,
    especialidad_id INT,
    diagnostico_id INT,
    fecha_ingreso DATE,
    fecha_alta DATE,
    FOREIGN KEY (paciente_id) REFERENCES Pacientes(id),
    FOREIGN KEY (medico_id) REFERENCES Medicos(id),
    FOREIGN KEY (especialidad_id) REFERENCES Especialidades(id),
    FOREIGN KEY (diagnostico_id) REFERENCES Diagnosticos(id)
);
