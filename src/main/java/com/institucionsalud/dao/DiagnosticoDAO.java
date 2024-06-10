package com.institucionsalud.dao;

import com.institucionsalud.models.Diagnostico;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DiagnosticoDAO {
    private Connection connection;

    public DiagnosticoDAO(Connection connection) {
        this.connection = connection;
    }

    public List<Diagnostico> getAll() throws SQLException {
        List<Diagnostico> diagnosticos = new ArrayList<>();
        String query = "SELECT * FROM Diagnosticos";
        Statement stmt = connection.createStatement();
        ResultSet rs = stmt.executeQuery(query);
        while (rs.next()) {
            Diagnostico diagnostico = new Diagnostico();
            diagnostico.setId(rs.getInt("id"));
            diagnostico.setNombre(rs.getString("nombre"));
            diagnosticos.add(diagnostico);
        }
        return diagnosticos;
    }

    public void insert(Diagnostico diagnostico) throws SQLException {
        String query = "INSERT INTO Diagnosticos (nombre) VALUES (?)";
        PreparedStatement pstmt = connection.prepareStatement(query);
        pstmt.setString(1, diagnostico.getNombre());
        pstmt.executeUpdate();
    }

    public void update(Diagnostico diagnostico) throws SQLException {
        String query = "UPDATE Diagnosticos SET nombre = ? WHERE id = ?";
        PreparedStatement pstmt = connection.prepareStatement(query);
        pstmt.setString(1, diagnostico.getNombre());
        pstmt.setInt(2, diagnostico.getId());
        pstmt.executeUpdate();
    }

    public void delete(int id) throws SQLException {
        String query = "DELETE FROM Diagnosticos WHERE id = ?";
        PreparedStatement pstmt = connection.prepareStatement(query);
        pstmt.setInt(1, id);
        pstmt.executeUpdate();
    }
}
