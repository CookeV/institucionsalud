package com.institucionsalud.gui;

import com.institucionsalud.dao.*;
import com.institucionsalud.models.Especialidad;

import com.institucionsalud.dao.*;
import com.institucionsalud.gui.*;

import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MainFrame extends JFrame {
    private Connection connection;

    public MainFrame() {
        super("Institucion de Salud");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Conectar a la base de datos
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/InstitucionSalud", "root", "0970");
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error al conectar a la base de datos", "Error", JOptionPane.ERROR_MESSAGE);
            System.exit(1);
        }

        // Crear instancias de los DAO
        EspecialidadDAO especialidadDAO = new EspecialidadDAO(connection);
        MedicoDAO medicoDAO = new MedicoDAO(connection);
        PacienteDAO pacienteDAO = new PacienteDAO(connection);
        DiagnosticoDAO diagnosticoDAO = new DiagnosticoDAO(connection);
        IngresoDAO ingresoDAO = new IngresoDAO(connection);

        // Crear paneles
        EspecialidadPanel especialidadPanel = new EspecialidadPanel(especialidadDAO);
        MedicoPanel medicoPanel = new MedicoPanel(medicoDAO);
        PacientePanel pacientePanel = new PacientePanel(pacienteDAO);
        DiagnosticoPanel diagnosticoPanel = new DiagnosticoPanel(diagnosticoDAO);
        IngresoPanel ingresoPanel = new IngresoPanel(ingresoDAO);

        // Configurar pestañas
        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.addTab("Especialidades", especialidadPanel);
        tabbedPane.addTab("Medicos", medicoPanel);
        tabbedPane.addTab("Pacientes", pacientePanel);
        tabbedPane.addTab("Diagnosticos", diagnosticoPanel);
        tabbedPane.addTab("Ingresos", ingresoPanel);

        getContentPane().add(tabbedPane, BorderLayout.CENTER);
    }


}
