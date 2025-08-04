package com.sgvet.mascota.boundary;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import java.util.logging.Level;
import com.sgvet.mascota.entity.Mascota;

public class MascotaRepository {
    private static final Logger logger = Logger.getLogger(MascotaRepository.class.getName());
    MascotaDbManager mascotaDbManager = MascotaDbManager.getInstance();

    private static final String COL_ID = "ID";
    private static final String COL_NOMBRE = "NOMBRE";
    private static final String COL_APELLIDO = "APELLIDO";
    private static final String COL_EDAD = "EDAD";
    private static final String COL_TELEFONO = "TELEFONO";
    private static final String COL_IDCLIENTE = "IDCLIENTE";
    private static final String COL_TIPOMASCOTA = "TIPOMASCOTA";
    private static final String COL_RAZA = "RAZA";

    // SQL constants
    private static final String SELECT_CLAUSE = "SELECT ";
    private static final String FROM_MASCOTA_WHERE = " FROM Mascota WHERE ";
    private static final String ALL_COLUMNS = COL_ID + ", " + COL_NOMBRE + ", " + COL_APELLIDO + ", " +
                                             COL_EDAD + ", " + COL_TELEFONO + ", " + COL_IDCLIENTE + ", " +
                                             COL_TIPOMASCOTA + ", " + COL_RAZA;

    public MascotaRepository() {
        // Constructor vacío - La inicialización se realiza a través del MascotaDbManager singleton
        // No se requiere lógica adicional de inicialización en el constructor
    }

    public boolean eliminarPorId(int id) {
        String sql = "DELETE FROM Mascota WHERE " + COL_ID + " = ?";
        try (PreparedStatement ps = MascotaDbManager.getConnection().prepareStatement(sql)) {
            ps.setInt(1, id);
            int affected = ps.executeUpdate();
            return affected > 0;
        } catch (SQLException e) {
            if (logger.isLoggable(Level.SEVERE)) {
                logger.log(Level.SEVERE, "Error al eliminar mascota con ID: " + id, e);
            }
            return false;
        }
    }

    public List<Mascota> listarTodos() {
        List<Mascota> Mascotas = new ArrayList<>();
        String sql = SELECT_CLAUSE + ALL_COLUMNS + " FROM Mascota";

        try (Statement stmt = MascotaDbManager.getConnection().createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Mascota c = createMascotaFromResultSet(rs);
                Mascotas.add(c);
            }
        } catch (SQLException e) {
            if (logger.isLoggable(Level.SEVERE)) {
                logger.log(Level.SEVERE, "Error al listar todas las mascotas", e);
            }
        }

        return Mascotas;
    }

    public Mascota buscarPorId(int id) {
        String sql = SELECT_CLAUSE + ALL_COLUMNS + FROM_MASCOTA_WHERE + COL_ID + " = ?";
        try (PreparedStatement ps = MascotaDbManager.getConnection().prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return createMascotaFromResultSet(rs);
                }
            }
        } catch (SQLException e) {
            if (logger.isLoggable(Level.SEVERE)) {
                logger.log(Level.SEVERE, "Error al buscar mascota con ID: " + id, e);
            }
        }
        return null;
    }

    public List<Mascota> buscarPorNombre(String nombre) {
        List<Mascota> mascotas = new ArrayList<>();
        String sql = SELECT_CLAUSE + ALL_COLUMNS + FROM_MASCOTA_WHERE + COL_NOMBRE + " LIKE ?";
        try (PreparedStatement ps = MascotaDbManager.getConnection().prepareStatement(sql)) {
            ps.setString(1, "%" + nombre + "%");
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Mascota c = createMascotaFromResultSet(rs);
                    mascotas.add(c);
                }
            }
        } catch (SQLException e) {
            if (logger.isLoggable(Level.SEVERE)) {
                logger.log(Level.SEVERE, "Error al buscar mascotas por nombre: " + nombre, e);
            }
        }
        return mascotas;
    }

    public List<Mascota> buscarPorCliente(int idCliente) {
        List<Mascota> mascotas = new ArrayList<>();
        String sql = SELECT_CLAUSE + ALL_COLUMNS + FROM_MASCOTA_WHERE + COL_IDCLIENTE + " = ?";
        try (PreparedStatement ps = MascotaDbManager.getConnection().prepareStatement(sql)) {
            ps.setInt(1, idCliente);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Mascota c = createMascotaFromResultSet(rs);
                    mascotas.add(c);
                }
            }
        } catch (SQLException e) {
            if (logger.isLoggable(Level.SEVERE)) {
                logger.log(Level.SEVERE, "Error al buscar mascotas por cliente ID: " + idCliente, e);
            }
        }
        return mascotas;
    }

    public boolean actualizar(Mascota mascota) {
        String sql = "UPDATE Mascota SET " + COL_NOMBRE + "=?, " + COL_APELLIDO + "=?, " + COL_EDAD + "=?, " + COL_TELEFONO + "=?, " + COL_IDCLIENTE + "=?, " + COL_TIPOMASCOTA + "=?, " + COL_RAZA + "=? WHERE " + COL_ID + "=?";
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
            if (logger.isLoggable(Level.SEVERE)) {
                logger.log(Level.SEVERE, "Error al actualizar mascota ID: " + mascota.getId(), e);
            }
            return false;
        }
    }

    public void insertar(Mascota mascota) {
        // Si el ID es null, asignar el siguiente ID disponible
        if (mascota.getId() == null) {
            mascota.setId(generarNuevoId());
        }
        String sql = "INSERT INTO Mascota (" + COL_ID + ", " + COL_NOMBRE + ", " + COL_APELLIDO + ", " + COL_EDAD + ", " + COL_TELEFONO + ", " + COL_IDCLIENTE + ", " + COL_TIPOMASCOTA + ", " + COL_RAZA + ") VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
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
            if (logger.isLoggable(Level.SEVERE)) {
                logger.log(Level.SEVERE, "Error al insertar mascota: " + mascota.getNombre(), e);
            }
        }
    }

    private int generarNuevoId() {
        String sql = SELECT_CLAUSE + "MAX(" + COL_ID + ") AS max_id FROM Mascota";
        try (Statement stmt = MascotaDbManager.getConnection().createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            if (rs.next()) {
                return rs.getInt("max_id") + 1;
            }
        } catch (SQLException e) {
            if (logger.isLoggable(Level.SEVERE)) {
                logger.log(Level.SEVERE, "Error al generar nuevo ID", e);
            }
        }
        return 1; // Si no hay registros, empezar en 1
    }

    // Helper method to create Mascota from ResultSet
    private Mascota createMascotaFromResultSet(ResultSet rs) throws SQLException {
        return new Mascota(
            rs.getInt(COL_ID),
            rs.getString(COL_NOMBRE),
            rs.getString(COL_APELLIDO),
            rs.getInt(COL_EDAD),
            rs.getString(COL_TELEFONO),
            rs.getInt(COL_IDCLIENTE),
            rs.getString(COL_TIPOMASCOTA),
            rs.getString(COL_RAZA)
        );
    }
}
