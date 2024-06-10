package com.institucionsalud.dao;

import com.institucionsalud.models.Medico;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MedicoDAO {
    private Connection connection;

    public MedicoDAO(Connection connection) {
        this.connection = connection;
    }

    public List<Medico> getAll() throws SQLException {
        List<Medico> medicos = new ArrayList<>();
        String query = "SELECT * FROM Medicos";
        Statement stmt = connection.createStatement();
        ResultSet rs = stmt.executeQuery(query);
        while (rs.next()) {
            Medico medico = new Medico();
            medico.setId(rs.getInt("id"));
            medico.setNombre(rs.getString("nombre"));
            medico.setEspecialidadId(rs.getInt("especialidad_id"));
            medicos.add(medico);
        }
        return medicos;
    }

    public void insert(Medico medico) throws SQLException {
        String query = "INSERT INTO Medicos (nombre, especialidad_id) VALUES (?, ?)";
        PreparedStatement pstmt = connection.prepareStatement(query);
        pstmt.setString(1, medico.getNombre());
        pstmt.setInt(2, medico.getEspecialidadId());
        pstmt.executeUpdate();
    }

    public void update(Medico medico) throws SQLException {
        String query = "UPDATE Medicos SET nombre = ?, especialidad_id = ? WHERE id = ?";
        PreparedStatement pstmt = connection.prepareStatement(query);
        pstmt.setString(1, medico.getNombre());
        pstmt.setInt(2, medico.getEspecialidadId());
        pstmt.setInt(3, medico.getId());
        pstmt.executeUpdate();
    }

    public void delete(int id) throws SQLException {
        String query = "DELETE FROM Medicos WHERE id = ?";
        PreparedStatement pstmt = connection.prepareStatement(query);
        pstmt.setInt(1, id);
        pstmt.executeUpdate();
    }
}