package com.institucionsalud.gui;

import com.institucionsalud.dao.MedicoDAO;
import com.institucionsalud.models.Medico;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.List;

public class MedicoPanel extends JPanel {
    private MedicoDAO medicoDAO;
    private DefaultTableModel tableModel;
    private JTable table;
    private JTextField nombreField;
    private JTextField especialidadIdField;

    public MedicoPanel(MedicoDAO medicoDAO) {
        this.medicoDAO = medicoDAO;
        setLayout(new BorderLayout());

        // Panel superior para formularios
        JPanel formPanel = new JPanel(new GridLayout(3, 2));
        formPanel.add(new JLabel("Nombre:"));
        nombreField = new JTextField();
        formPanel.add(nombreField);
        formPanel.add(new JLabel("ID Especialidad:"));
        especialidadIdField = new JTextField();
        formPanel.add(especialidadIdField);

        JButton addButton = new JButton("Agregar");
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addMedico();
            }
        });
        formPanel.add(addButton);

        JButton updateButton = new JButton("Actualizar");
        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateMedico();
            }
        });
        formPanel.add(updateButton);

        add(formPanel, BorderLayout.NORTH);

        // Tabla para mostrar los datos
        tableModel = new DefaultTableModel(new Object[]{"ID", "Nombre", "ID Especialidad"}, 0);
        table = new JTable(tableModel);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                int selectedRow = table.getSelectedRow();
                if (selectedRow != -1) {
                    nombreField.setText(tableModel.getValueAt(selectedRow, 1).toString());
                    especialidadIdField.setText(tableModel.getValueAt(selectedRow, 2).toString());
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
                deleteMedico();
            }
        });
        buttonPanel.add(deleteButton);

        add(buttonPanel, BorderLayout.SOUTH);

        loadMedicos();
    }

    private void loadMedicos() {
        try {
            List<Medico> medicos = medicoDAO.getAll();
            tableModel.setRowCount(0);
            for (Medico medico : medicos) {
                tableModel.addRow(new Object[]{medico.getId(), medico.getNombre(), medico.getEspecialidadId()});
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error al cargar médicos", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void addMedico() {
        String nombre = nombreField.getText();
        int especialidadId = Integer.parseInt(especialidadIdField.getText());
        if (!nombre.isEmpty() && especialidadId > 0) {
            Medico medico = new Medico();
            medico.setNombre(nombre);
            medico.setEspecialidadId(especialidadId);
            try {
                medicoDAO.insert(medico);
                loadMedicos();
                nombreField.setText("");
                especialidadIdField.setText("");
            } catch (SQLException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(this, "Error al agregar médico", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void updateMedico() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow != -1) {
            int id = (int) tableModel.getValueAt(selectedRow, 0);
            String nombre = nombreField.getText();
            int especialidadId = Integer.parseInt(especialidadIdField.getText());
            if (!nombre.isEmpty() && especialidadId > 0) {
                Medico medico = new Medico();
                medico.setId(id);
                medico.setNombre(nombre);
                medico.setEspecialidadId(especialidadId);
                try {
                    medicoDAO.update(medico);
                    loadMedicos();
                } catch (SQLException e) {
                    e.printStackTrace();
                    JOptionPane.showMessageDialog(this, "Error al actualizar médico", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }

    private void deleteMedico() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow != -1) {
            int id = (int) tableModel.getValueAt(selectedRow, 0);
            try {
                medicoDAO.delete(id);
                loadMedicos();
            } catch (SQLException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(this, "Error al eliminar médico", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}