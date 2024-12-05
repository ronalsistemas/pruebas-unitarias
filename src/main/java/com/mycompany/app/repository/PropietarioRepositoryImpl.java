package com.mycompany.app.repository;

import com.mycompany.app.model.Propietario;

import java.sql.*;

public class PropietarioRepositoryImpl implements PropietarioRepository {

    private final Connection connection;

    public PropietarioRepositoryImpl() {
        this.connection = DatabaseConnection.getConnection();
    }

    @Override
    public Propietario guardar(Propietario propietario) {
        String sql = "INSERT INTO propietario (nombre, ciudad, telefono) VALUES (?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, propietario.getNombre());
            stmt.setString(2, propietario.getCiudad());
            stmt.setString(3, propietario.getTelefono());
            stmt.executeUpdate();
            ResultSet generatedKeys = stmt.getGeneratedKeys();
            if (generatedKeys.next()) {
                int generatedId = generatedKeys.getInt(1);
                propietario.setId(generatedId);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return propietario;
    }
}
