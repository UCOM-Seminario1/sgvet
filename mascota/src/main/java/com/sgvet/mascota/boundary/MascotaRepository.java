package com.sgvet.mascota.boundary;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import com.sgvet.mascota.boundary.MascotaDbManager;
import com.sgvet.mascota.entity.Mascota;    

public class MascotaRepository {
    MascotaDbManager mascotaDbManager = MascotaDbManager.getInstance();

    public MascotaRepository() {
    }

    public boolean eliminarPorId(int id) {
        String sql = "DELETE FROM Mascota WHERE ID = ?";
        try (PreparedStatement ps = MascotaDbManager.getConnection().prepareStatement(sql)) {
            ps.setInt(1, id);
            int affected = ps.executeUpdate();
            return affected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }


    public List<Mascota> listarTodos() {
        List<Mascota> Mascotas = new ArrayList<>();
        String sql = "SELECT * FROM Mascota";

        try (Statement stmt = MascotaDbManager.getConnection().createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Mascota c = new Mascota(
                        rs.getInt("ID"),
                        rs.getString("NOMBRE"),
                        rs.getString("APELLIDO"),
                        rs.getInt("EDAD"),
                        rs.getString("TELEFONO"),
                        rs.getInt("IDCLIENTE"),
                        rs.getString("TIPOMASCOTA"),
                        rs.getString("RAZA")
                );
                Mascotas.add(c);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return Mascotas;
    }

    public Mascota buscarPorId(int id) {
        String sql = "SELECT * FROM Mascota WHERE ID = ?";
        try (PreparedStatement ps = MascotaDbManager.getConnection().prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new Mascota(
                        rs.getInt("ID"),
                        rs.getString("NOMBRE"),
                        rs.getString("APELLIDO"),
                        rs.getInt("EDAD"),
                        rs.getString("TELEFONO"),
                        rs.getInt("IDCLIENTE"),
                        rs.getString("TIPOMASCOTA"),
                        rs.getString("RAZA")
                    );
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Mascota> buscarPorNombre(String nombre) {
        List<Mascota> mascotas = new ArrayList<>();
        String sql = "SELECT * FROM Mascota WHERE NOMBRE LIKE ?";
        try (PreparedStatement ps = MascotaDbManager.getConnection().prepareStatement(sql)) {
            ps.setString(1, "%" + nombre + "%");
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Mascota c = new Mascota(
                        rs.getInt("ID"),
                        rs.getString("NOMBRE"),
                        rs.getString("APELLIDO"),
                        rs.getInt("EDAD"),
                        rs.getString("TELEFONO"),
                        rs.getInt("IDCLIENTE"),
                        rs.getString("TIPOMASCOTA"),
                        rs.getString("RAZA")
                    );
                    mascotas.add(c);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return mascotas;
    }

    public List<Mascota> buscarPorCliente(int idCliente) {
        List<Mascota> mascotas = new ArrayList<>();
        String sql = "SELECT * FROM Mascota WHERE IDCLIENTE = ?";
        try (PreparedStatement ps = MascotaDbManager.getConnection().prepareStatement(sql)) {
            ps.setInt(1, idCliente);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Mascota c = new Mascota(
                        rs.getInt("ID"),
                        rs.getString("NOMBRE"),
                        rs.getString("APELLIDO"),
                        rs.getInt("EDAD"),
                        rs.getString("TELEFONO"),
                        rs.getInt("IDCLIENTE"),
                        rs.getString("TIPOMASCOTA"),
                        rs.getString("RAZA")
                    );
                    mascotas.add(c);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return mascotas;
    }

    public boolean actualizar(Mascota mascota) {
        String sql = "UPDATE Mascota SET NOMBRE=?, APELLIDO=?, EDAD=?, TELEFONO=?, IDCLIENTE=?, TIPOMASCOTA=?, RAZA=? WHERE ID=?";
        try (PreparedStatement ps = MascotaDbManager.getConnection().prepareStatement(sql)) {
            ps.setString(1, mascota.getNombre());
            ps.setString(2, mascota.getApellido());
            ps.setInt(3, mascota.getEdad());
            ps.setString(4, mascota.getTelefono());
            ps.setInt(5, mascota.getIdCliente());
            ps.setString(6, mascota.getTipoMascota());
            ps.setString(7, mascota.getRaza());
            ps.setInt(8, mascota.getId());
            int affected = ps.executeUpdate();
            return affected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public void insertar(Mascota mascota) {
        // Si el ID es null, asignar el siguiente ID disponible
        if (mascota.getId() == null) {
            mascota.setId(generarNuevoId());
        }
        String sql = "INSERT INTO Mascota (ID, NOMBRE, APELLIDO, EDAD, TELEFONO, IDCLIENTE, TIPOMASCOTA, RAZA) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement ps = MascotaDbManager.getConnection().prepareStatement(sql)) {
            ps.setInt(1, mascota.getId());
            ps.setString(2, mascota.getNombre());
            ps.setString(3, mascota.getApellido());
            ps.setInt(4, mascota.getEdad());
            ps.setString(5, mascota.getTelefono());
            ps.setInt(6, mascota.getIdCliente());
            ps.setString(7, mascota.getTipoMascota());
            ps.setString(8, mascota.getRaza());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private int generarNuevoId() {
        String sql = "SELECT MAX(ID) AS max_id FROM Mascota";
        try (Statement stmt = MascotaDbManager.getConnection().createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            if (rs.next()) {
                return rs.getInt("max_id") + 1;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 1; // Si no hay registros, empezar en 1
    }
}
