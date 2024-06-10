USE InstitucionSalud;

INSERT INTO Especialidades (nombre) VALUES
('Pediatría'),
('Cardiología'),
('Dermatología'),
('Oftalmología'),
('Neurología');

INSERT INTO Medicos (nombre, especialidad_id) VALUES
('Dr. Juan Pérez', 1),
('Dra. María García', 2),
('Dr. Carlos López', 3),
('Dra. Ana Martínez', 4),
('Dr. David Rodríguez', 5);

INSERT INTO Pacientes (nombre, fecha_nacimiento) VALUES
('Luisa González', '1990-05-15'),
('Roberto Sánchez', '1985-09-23'),
('Laura Ramírez', '2000-03-10'),
('Manuel Gómez', '1978-11-28'),
('María Torres', '1965-07-07');

INSERT INTO Diagnosticos (nombre) VALUES
('Gripe'),
('Hipertensión'),
('Dermatitis'),
('Cataratas'),
('Migraña');

INSERT INTO Ingresos (paciente_id, medico_id, especialidad_id, diagnostico_id, fecha_ingreso, fecha_alta) VALUES
(1, 1, 1, 1, '2024-05-01', '2024-05-05'),
(2, 2, 2, 2, '2024-05-02', '2024-05-07'),
(3, 3, 3, 3, '2024-05-03', '2024-05-09'),
(4, 4, 4, 4, '2024-05-04', '2024-05-10'),
(5, 5, 5, 5, '2024-05-05', '2024-05-11');
