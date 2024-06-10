package com.institucionsalud.gui;

import com.institucionsalud.dao.DiagnosticoDAO;
import com.institucionsalud.models.Diagnostico;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.List;

public class DiagnosticoPanel extends JPanel {
    private DiagnosticoDAO diagnosticoDAO;
    private DefaultTableModel tableModel;
    private JTable table;
    private JTextField nombreField;

    public DiagnosticoPanel(DiagnosticoDAO diagnosticoDAO) {
        this.diagnosticoDAO = diagnosticoDAO;
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
                addDiagnostico();
            }
        });
        formPanel.add(addButton);

        JButton updateButton = new JButton("Actualizar");
        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateDiagnostico();
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
                deleteDiagnostico();
            }
        });
        buttonPanel.add(deleteButton);

        add(buttonPanel, BorderLayout.SOUTH);

        loadDiagnosticos();
    }

    private void loadDiagnosticos() {
        try {
            List<Diagnostico> diagnosticos = diagnosticoDAO.getAll();
            tableModel.setRowCount(0);
            for (Diagnostico diagnostico : diagnosticos) {
                tableModel.addRow(new Object[]{diagnostico.getId(), diagnostico.getNombre()});
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error al cargar diagnósticos", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void addDiagnostico() {
        String nombre = nombreField.getText();
        if (!nombre.isEmpty()) {
            Diagnostico diagnostico = new Diagnostico();
            diagnostico.setNombre(nombre);
            try {
                diagnosticoDAO.insert(diagnostico);
                loadDiagnosticos();
                nombreField.setText("");
            } catch (SQLException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(this, "Error al agregar diagnóstico", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void updateDiagnostico() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow != -1) {
            int id = (int) tableModel.getValueAt(selectedRow, 0);
            String nombre = nombreField.getText();
            if (!nombre.isEmpty()) {
                Diagnostico diagnostico = new Diagnostico();
                diagnostico.setId(id);
                diagnostico.setNombre(nombre);
                try {
                    diagnosticoDAO.update(diagnostico);
                    loadDiagnosticos();
                } catch (SQLException e) {
                    e.printStackTrace();
                    JOptionPane.showMessageDialog(this, "Error al actualizar diagnóstico", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }

    private void deleteDiagnostico() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow != -1) {
            int id = (int) tableModel.getValueAt(selectedRow, 0);
            try {
                diagnosticoDAO.delete(id);
                loadDiagnosticos();
            } catch (SQLException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(this, "Error al eliminar diagnóstico", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}