package com.institucionsalud.gui;

import com.institucionsalud.dao.EspecialidadDAO;
import com.institucionsalud.models.Especialidad;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.List;

public class EspecialidadPanel extends JPanel {
    private EspecialidadDAO especialidadDAO;
    private DefaultTableModel tableModel;
    private JTable table;
    private JTextField nombreField;

    public EspecialidadPanel(EspecialidadDAO especialidadDAO) {
        this.especialidadDAO = especialidadDAO;
        setLayout(new BorderLayout());

        // Panel superior para formularios
        JPanel formPanel = new JPanel(new GridLayout(2, 2));
        formPanel.add(new JLabel("Nombre:"));
        nombreField = new JTextField();
        formPanel.add(nombreField);

        JButton addButton = new JButton("Agregar");
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addEspecialidad();
            }
        });
        formPanel.add(addButton);

        JButton updateButton = new JButton("Actualizar");
        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateEspecialidad();
            }
        });
        formPanel.add(updateButton);

        add(formPanel, BorderLayout.NORTH);

        // Tabla para mostrar los datos
        tableModel = new DefaultTableModel(new Object[]{"ID", "Nombre"}, 0);
        table = new JTable(tableModel);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                int selectedRow = table.getSelectedRow();
                if (selectedRow != -1) {
                    nombreField.setText(tableModel.getValueAt(selectedRow, 1).toString());
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
                deleteEspecialidad();
            }
        });
        buttonPanel.add(deleteButton);

        add(buttonPanel, BorderLayout.SOUTH);

        loadEspecialidades();
    }

    private void loadEspecialidades() {
        try {
            List<Especialidad> especialidades = especialidadDAO.getAll();
            tableModel.setRowCount(0);
            for (Especialidad especialidad : especialidades) {
                tableModel.addRow(new Object[]{especialidad.getId(), especialidad.getNombre()});
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error al cargar especialidades", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void addEspecialidad() {
        String nombre = nombreField.getText();
        if (!nombre.isEmpty()) {
            Especialidad especialidad = new Especialidad();
            especialidad.setNombre(nombre);
            try {
                especialidadDAO.insert(especialidad);
                loadEspecialidades();
                nombreField.setText("");
            } catch (SQLException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(this, "Error al agregar especialidad", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void updateEspecialidad() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow != -1) {
            int id = (int) tableModel.getValueAt(selectedRow, 0);
            String nombre = nombreField.getText();
            if (!nombre.isEmpty()) {
                Especialidad especialidad = new Especialidad();
                especialidad.setId(id);
                especialidad.setNombre(nombre);
                try {
                    especialidadDAO.update(especialidad);
                    loadEspecialidades();
                } catch (SQLException e) {
                    e.printStackTrace();
                    JOptionPane.showMessageDialog(this, "Error al actualizar especialidad", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }

    private void deleteEspecialidad() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow != -1) {
            int id = (int) tableModel.getValueAt(selectedRow, 0);
            try {
                especialidadDAO.delete(id);
                loadEspecialidades();
            } catch (SQLException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(this, "Error al eliminar especialidad", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}