package com.institucionsalud.gui;

import com.institucionsalud.dao.PacienteDAO;
import com.institucionsalud.models.Paciente;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.List;

public class PacientePanel extends JPanel {
    private PacienteDAO pacienteDAO;
    private DefaultTableModel tableModel;
    private JTable table;
    private JTextField nombreField;
    private JTextField fechaNacimientoField;

    public PacientePanel(PacienteDAO pacienteDAO) {
        this.pacienteDAO = pacienteDAO;
        setLayout(new BorderLayout());

        // Panel superior para formularios
        JPanel formPanel = new JPanel(new GridLayout(3, 2));
        formPanel.add(new JLabel("Nombre:"));
        nombreField = new JTextField();
        formPanel.add(nombreField);
        formPanel.add(new JLabel("Fecha de Nacimiento (YYYY-MM-DD):"));
        fechaNacimientoField = new JTextField();
        formPanel.add(fechaNacimientoField);

        JButton addButton = new JButton("Agregar");
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addPaciente();
            }
        });
        formPanel.add(addButton);

        JButton updateButton = new JButton("Actualizar");
        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updatePaciente();
            }
        });
        formPanel.add(updateButton);

        add(formPanel, BorderLayout.NORTH);

        // Tabla para mostrar los datos
        tableModel = new DefaultTableModel(new Object[]{"ID", "Nombre", "Fecha de Nacimiento"}, 0);
        table = new JTable(tableModel);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                int selectedRow = table.getSelectedRow();
                if (selectedRow != -1) {
                    nombreField.setText(tableModel.getValueAt(selectedRow, 1).toString());
                    fechaNacimientoField.setText(tableModel.getValueAt(selectedRow, 2).toString());
                }
            }
        });
        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);

        // Panel inferior para botones
        JPanel buttonPanel = new JPanel();
        JButton deleteButton = new JButton("Eliminar");
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deletePaciente();
            }
        });
        buttonPanel.add(deleteButton);

        add(buttonPanel, BorderLayout.SOUTH);

        loadPacientes();
    }

    private void loadPacientes() {
        try {
            List<Paciente> pacientes = pacienteDAO.getAll();
            tableModel.setRowCount(0);
            for (Paciente paciente : pacientes) {
                tableModel.addRow(new Object[]{paciente.getId(), paciente.getNombre(), paciente.getFechaNacimiento()});
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error al cargar pacientes", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void addPaciente() {
        String nombre = nombreField.getText();
        String fechaNacimiento = fechaNacimientoField.getText();
        if (!nombre.isEmpty() && !fechaNacimiento.isEmpty()) {
            Paciente paciente = new Paciente();
            paciente.setNombre(nombre);
            paciente.setFechaNacimiento(java.sql.Date.valueOf(fechaNacimiento));
            try {
                pacienteDAO.insert(paciente);
                loadPacientes();
                nombreField.setText("");
                fechaNacimientoField.setText("");
            } catch (SQLException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(this, "Error al agregar paciente", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void updatePaciente() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow != -1) {
            int id = (int) tableModel.getValueAt(selectedRow, 0);
            String nombre = nombreField.getText();
            String fechaNacimiento = fechaNacimientoField.getText();
            if (!nombre.isEmpty() && !fechaNacimiento.isEmpty()) {
                Paciente paciente = new Paciente();
                paciente.setId(id);
                paciente.setNombre(nombre);
                paciente.setFechaNacimiento(java.sql.Date.valueOf(fechaNacimiento));
                try {
                    pacienteDAO.update(paciente);
                    loadPacientes();
                } catch (SQLException e) {
                    e.printStackTrace();
                    JOptionPane.showMessageDialog(this, "Error al actualizar paciente", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }

    private void deletePaciente() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow != -1) {
            int id = (int) tableModel.getValueAt(selectedRow, 0);
            try {
                pacienteDAO.delete(id);
                loadPacientes();
            } catch (SQLException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(this, "Error al eliminar paciente", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}