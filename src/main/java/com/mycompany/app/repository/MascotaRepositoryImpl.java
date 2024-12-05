package com.mycompany.app.repository;

import com.mycompany.app.model.Mascota;
import com.mycompany.app.model.Propietario;

import java.sql.*;
import java.util.Optional;

public class MascotaRepositoryImpl implements MascotaRepository{

    private final Connection connection;

    public MascotaRepositoryImpl() {
        this.connection = DatabaseConnection.getConnection();
    }

    @Override
    public Optional<Mascota> findByName(String nombre) {
        try (PreparedStatement stmt = connection.prepareStatement("SELECT * FROM mascota WHERE nombre = ?")) {
            stmt.setString(1, nombre);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                Mascota mascota = new Mascota();
                mascota.setId(rs.getInt("id"));
                mascota.setNombre(rs.getString("nombre"));
                // Set other fields as needed
                return Optional.of(mascota);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public Mascota guardar(Mascota mascota) {
        try (PreparedStatement stmt = connection.prepareStatement("INSERT INTO mascota (nombre, propietario_id) VALUES (?, ?)", Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, mascota.getNombre());
            stmt.setInt(2, mascota.getPropietario().getId());
            stmt.executeUpdate();
            ResultSet generatedKeys = stmt.getGeneratedKeys();
            if (generatedKeys.next()) {
                mascota.setId(generatedKeys.getInt(1));

                String selectSql = "SELECT * FROM propietario WHERE id = ?";
                try (PreparedStatement selectStmt = connection.prepareStatement(selectSql)) {
                    selectStmt.setInt(1, mascota.getPropietario().getId());
                    ResultSet rs = selectStmt.executeQuery();
                    if (rs.next()) {
                        Propietario propietario = new Propietario();
                        propietario.setNombre(rs.getString("nombre"));
                        propietario.setCiudad(rs.getString("ciudad"));
                        propietario.setTelefono(rs.getString("telefono"));
                        mascota.setPropietario(propietario);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return mascota;
    }

    @Override
    public Optional<Mascota> findById(Integer id) {
        try (PreparedStatement stmt = connection.prepareStatement("SELECT * FROM mascota WHERE id = ?")) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                Mascota mascota = new Mascota();
                mascota.setId(rs.getInt("id"));
                mascota.setNombre(rs.getString("nombre"));
                // Set other fields as needed
                return Optional.of(mascota);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public void deleteById(Integer id) {
        try (PreparedStatement stmt = connection.prepareStatement("DELETE FROM mascota WHERE id = ?")) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
