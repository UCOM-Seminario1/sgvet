package com.sgvet.mascota.boundary;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import com.sgvet.mascota.entity.Mascota;    

public class MascotaRepository {
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

    // Display constants
    private static final String TIPO_LABEL = ", Tipo: ";
    private static final String RAZA_LABEL = ", Raza: ";

    public MascotaRepository() {
    }

    public boolean eliminarPorId(int id) {
        String sql = "DELETE FROM Mascota WHERE " + COL_ID + " = ?";
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
        String sql = SELECT_CLAUSE + ALL_COLUMNS + " FROM Mascota";

        try (Statement stmt = MascotaDbManager.getConnection().createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Mascota c = createMascotaFromResultSet(rs);
                Mascotas.add(c);
            }
        } catch (SQLException e) {
            e.printStackTrace();
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
            e.printStackTrace();
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
            e.printStackTrace();
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
            e.printStackTrace();
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
            e.printStackTrace();
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
            e.printStackTrace();
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
            e.printStackTrace();
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
