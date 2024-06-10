package com.institucionsalud.dao;

import com.institucionsalud.models.Especialidad;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EspecialidadDAO {
    private Connection connection;

    public EspecialidadDAO(Connection connection) {
        this.connection = connection;
    }

    public List<Especialidad> getAll() throws SQLException {
        List<Especialidad> especialidades = new ArrayList<>();
        String query = "SELECT * FROM Especialidades";
        Statement stmt = connection.createStatement();
        ResultSet rs = stmt.executeQuery(query);
        while (rs.next()) {
            Especialidad especialidad = new Especialidad();
            especialidad.setId(rs.getInt("id"));
            especialidad.setNombre(rs.getString("nombre"));
            especialidades.add(especialidad);
        }
        return especialidades;
    }

    public void insert(Especialidad especialidad) throws SQLException {
        String query = "INSERT INTO Especialidades (nombre) VALUES (?)";
        PreparedStatement pstmt = connection.prepareStatement(query);
        pstmt.setString(1, especialidad.getNombre());
        pstmt.executeUpdate();
    }

    public void update(Especialidad especialidad) throws SQLException {
        String query = "UPDATE Especialidades SET nombre = ? WHERE id = ?";
        PreparedStatement pstmt = connection.prepareStatement(query);
        pstmt.setString(1, especialidad.getNombre());
        pstmt.setInt(2, especialidad.getId());
        pstmt.executeUpdate();
    }

    public void delete(int id) throws SQLException {
        String query = "DELETE FROM Especialidades WHERE id = ?";
        PreparedStatement pstmt = connection.prepareStatement(query);
        pstmt.setInt(1, id);
        pstmt.executeUpdate();
    }
}