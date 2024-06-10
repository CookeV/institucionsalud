package com.institucionsalud.dao;

import com.institucionsalud.models.Paciente;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PacienteDAO {
    private Connection connection;

    public PacienteDAO(Connection connection) {
        this.connection = connection;
    }

    public List<Paciente> getAll() throws SQLException {
        List<Paciente> pacientes = new ArrayList<>();
        String query = "SELECT * FROM Pacientes";
        Statement stmt = connection.createStatement();
        ResultSet rs = stmt.executeQuery(query);
        while (rs.next()) {
            Paciente paciente = new Paciente();
            paciente.setId(rs.getInt("id"));
            paciente.setNombre(rs.getString("nombre"));
            paciente.setFechaNacimiento(rs.getDate("fecha_nacimiento"));
            pacientes.add(paciente);
        }
        return pacientes;
    }

    public void insert(Paciente paciente) throws SQLException {
        String query = "INSERT INTO Pacientes (nombre, fecha_nacimiento) VALUES (?, ?)";
        PreparedStatement pstmt = connection.prepareStatement(query);
        pstmt.setString(1, paciente.getNombre());
        pstmt.setDate(2, new java.sql.Date(paciente.getFechaNacimiento().getTime()));
        pstmt.executeUpdate();
    }

    public void update(Paciente paciente) throws SQLException {
        String query = "UPDATE Pacientes SET nombre = ?, fecha_nacimiento = ? WHERE id = ?";
        PreparedStatement pstmt = connection.prepareStatement(query);
        pstmt.setString(1, paciente.getNombre());
        pstmt.setDate(2, new java.sql.Date(paciente.getFechaNacimiento().getTime()));
        pstmt.setInt(3, paciente.getId());
        pstmt.executeUpdate();
    }

    public void delete(int id) throws SQLException {
        String query = "DELETE FROM Pacientes WHERE id = ?";
        PreparedStatement pstmt = connection.prepareStatement(query);
        pstmt.setInt(1, id);
        pstmt.executeUpdate();
    }
}