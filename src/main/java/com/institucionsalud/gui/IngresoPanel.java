package com.institucionsalud.gui;

import com.institucionsalud.dao.IngresoDAO;
import com.institucionsalud.models.Ingreso;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.List;

public class IngresoPanel extends JPanel {
    private IngresoDAO ingresoDAO;
    private DefaultTableModel tableModel;
    private JTable table;
    private JTextField pacienteIdField;
    private JTextField medicoIdField;
    private JTextField especialidadIdField;
    private JTextField diagnosticoIdField;
    private JTextField fechaIngresoField;
    private JTextField fechaAltaField;

    public IngresoPanel(IngresoDAO ingresoDAO) {
        this.ingresoDAO = ingresoDAO;
        setLayout(new BorderLayout());

        // Panel superior para formularios
        JPanel formPanel = new JPanel(new GridLayout(7, 2));
        formPanel.add(new JLabel("ID Paciente:"));
        pacienteIdField = new JTextField();
        formPanel.add(pacienteIdField);
        formPanel.add(new JLabel("ID Medico:"));
        medicoIdField = new JTextField();
        formPanel.add(medicoIdField);
        formPanel.add(new JLabel("ID Especialidad:"));
        especialidadIdField = new JTextField();
        formPanel.add(especialidadIdField);
        formPanel.add(new JLabel("ID Diagnóstico:"));
        diagnosticoIdField = new JTextField();
        formPanel.add(diagnosticoIdField);
        formPanel.add(new JLabel("Fecha Ingreso (YYYY-MM-DD):"));
        fechaIngresoField = new JTextField();
        formPanel.add(fechaIngresoField);
        formPanel.add(new JLabel("Fecha Alta (YYYY-MM-DD):"));
        fechaAltaField = new JTextField();
        formPanel.add(fechaAltaField);

        JButton addButton = new JButton("Agregar");
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addIngreso();
            }
        });
        formPanel.add(addButton);

        JButton updateButton = new JButton("Actualizar");
        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateIngreso();
            }
        });
        formPanel.add(updateButton);

        add(formPanel, BorderLayout.NORTH);

        // Tabla para mostrar los datos
        tableModel = new DefaultTableModel(new Object[]{"ID", "ID Paciente", "ID Medico", "ID Especialidad", "ID Diagnóstico", "Fecha Ingreso", "Fecha Alta"}, 0);
        table = new JTable(tableModel);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                int selectedRow = table.getSelectedRow();
                if (selectedRow != -1) {
                    pacienteIdField.setText(tableModel.getValueAt(selectedRow, 1).toString());
                    medicoIdField.setText(tableModel.getValueAt(selectedRow, 2).toString());
                    especialidadIdField.setText(tableModel.getValueAt(selectedRow, 3).toString());
                    diagnosticoIdField.setText(tableModel.getValueAt(selectedRow, 4).toString());
                    fechaIngresoField.setText(tableModel.getValueAt(selectedRow, 5).toString());
                    fechaAltaField.setText(tableModel.getValueAt(selectedRow, 6).toString());
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
                deleteIngreso();
            }
        });
        buttonPanel.add(deleteButton);

        add(buttonPanel, BorderLayout.SOUTH);

        loadIngresos();
    }

    private void loadIngresos() {
        try {
            List<Ingreso> ingresos = ingresoDAO.getAll();
            tableModel.setRowCount(0);
            for (Ingreso ingreso : ingresos) {
                tableModel.addRow(new Object[]{ingreso.getId(), ingreso.getPacienteId(), ingreso.getMedicoId(), ingreso.getEspecialidadId(), ingreso.getDiagnosticoId(), ingreso.getFechaIngreso(), ingreso.getFechaAlta()});
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error al cargar ingresos", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void addIngreso() {
        int pacienteId = Integer.parseInt(pacienteIdField.getText());
        int medicoId = Integer.parseInt(medicoIdField.getText());
        int especialidadId = Integer.parseInt(especialidadIdField.getText());
        int diagnosticoId = Integer.parseInt(diagnosticoIdField.getText());
        String fechaIngreso = fechaIngresoField.getText();
        String fechaAlta = fechaAltaField.getText();
        if (pacienteId > 0 && medicoId > 0 && especialidadId > 0 && diagnosticoId > 0 && !fechaIngreso.isEmpty() && !fechaAlta.isEmpty()) {
            Ingreso ingreso = new Ingreso();
            ingreso.setPacienteId(pacienteId);
            ingreso.setMedicoId(medicoId);
            ingreso.setEspecialidadId(especialidadId);
            ingreso.setDiagnosticoId(diagnosticoId);
            ingreso.setFechaIngreso(java.sql.Date.valueOf(fechaIngreso));
            ingreso.setFechaAlta(java.sql.Date.valueOf(fechaAlta));
            try {
                ingresoDAO.insert(ingreso);
                loadIngresos();
                pacienteIdField.setText("");
                medicoIdField.setText("");
                especialidadIdField.setText("");
                diagnosticoIdField.setText("");
                fechaIngresoField.setText("");
                fechaAltaField.setText("");
            } catch (SQLException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(this, "Error al agregar ingreso", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void updateIngreso() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow != -1) {
            int id = (int) tableModel.getValueAt(selectedRow, 0);
            int pacienteId = Integer.parseInt(pacienteIdField.getText());
            int medicoId = Integer.parseInt(medicoIdField.getText());
            int especialidadId = Integer.parseInt(especialidadIdField.getText());
            int diagnosticoId = Integer.parseInt(diagnosticoIdField.getText());
            String fechaIngreso = fechaIngresoField.getText();
            String fechaAlta = fechaAltaField.getText();
            if (pacienteId > 0 && medicoId > 0 && especialidadId > 0 && diagnosticoId > 0 && !fechaIngreso.isEmpty() && !fechaAlta.isEmpty()) {
                Ingreso ingreso = new Ingreso();
                ingreso.setId(id);
                ingreso.setPacienteId(pacienteId);
                ingreso.setMedicoId(medicoId);
                ingreso.setEspecialidadId(especialidadId);
                ingreso.setDiagnosticoId(diagnosticoId);
                ingreso.setFechaIngreso(java.sql.Date.valueOf(fechaIngreso));
                ingreso.setFechaAlta(java.sql.Date.valueOf(fechaAlta));
                try {
                    ingresoDAO.update(ingreso);
                    loadIngresos();
                } catch (SQLException e) {
                    e.printStackTrace();
                    JOptionPane.showMessageDialog(this, "Error al actualizar ingreso", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }

    private void deleteIngreso() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow != -1) {
            int id = (int) tableModel.getValueAt(selectedRow, 0);
            try {
                ingresoDAO.delete(id);
                loadIngresos();
            } catch (SQLException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(this, "Error al eliminar ingreso", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}