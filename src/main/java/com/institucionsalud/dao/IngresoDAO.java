package com.institucionsalud.dao;

import com.institucionsalud.models.Ingreso;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class IngresoDAO {
    private Connection connection;

    public IngresoDAO(Connection connection) {
        this.connection = connection;
    }

    public List<Ingreso> getAll() throws SQLException {
        List<Ingreso> ingresos = new ArrayList<>();
        String query = "SELECT * FROM Ingresos";
        Statement stmt = connection.createStatement();
        ResultSet rs = stmt.executeQuery(query);
        while (rs.next()) {
            Ingreso ingreso = new Ingreso();
            ingreso.setId(rs.getInt("id"));
            ingreso.setPacienteId(rs.getInt("paciente_id"));
            ingreso.setMedicoId(rs.getInt("medico_id"));
            ingreso.setEspecialidadId(rs.getInt("especialidad_id"));
            ingreso.setDiagnosticoId(rs.getInt("diagnostico_id"));
            ingreso.setFechaIngreso(rs.getDate("fecha_ingreso"));
            ingreso.setFechaAlta(rs.getDate("fecha_alta"));
            ingresos.add(ingreso);
        }
        return ingresos;
    }

    public void insert(Ingreso ingreso) throws SQLException {
        String query = "INSERT INTO Ingresos (paciente_id, medico_id, especialidad_id, diagnostico_id, fecha_ingreso, fecha_alta) VALUES (?, ?, ?, ?, ?, ?)";
        PreparedStatement pstmt = connection.prepareStatement(query);
        pstmt.setInt(1, ingreso.getPacienteId());
        pstmt.setInt(2, ingreso.getMedicoId());
        pstmt.setInt(3, ingreso.getEspecialidadId());
        pstmt.setInt(4, ingreso.getDiagnosticoId());
        pstmt.setDate(5, new java.sql.Date(ingreso.getFechaIngreso().getTime()));
        pstmt.setDate(6, new java.sql.Date(ingreso.getFechaAlta().getTime()));
        pstmt.executeUpdate();
    }

    public void update(Ingreso ingreso) throws SQLException {
        String query = "UPDATE Ingresos SET paciente_id = ?, medico_id = ?, especialidad_id = ?, diagnostico_id = ?, fecha_ingreso = ?, fecha_alta = ? WHERE id = ?";
        PreparedStatement pstmt = connection.prepareStatement(query);
        pstmt.setInt(1, ingreso.getPacienteId());
        pstmt.setInt(2, ingreso.getMedicoId());
        pstmt.setInt(3, ingreso.getEspecialidadId());
        pstmt.setInt(4, ingreso.getDiagnosticoId());
        pstmt.setDate(5, new java.sql.Date(ingreso.getFechaIngreso().getTime()));
        pstmt.setDate(6, new java.sql.Date(ingreso.getFechaAlta().getTime()));
        pstmt.setInt(7, ingreso.getId());
        pstmt.executeUpdate();
    }

    public void delete(int id) throws SQLException {
        String query = "DELETE FROM Ingresos WHERE id = ?";
        PreparedStatement pstmt = connection.prepareStatement(query);
        pstmt.setInt(1, id);
        pstmt.executeUpdate();
    }
}