package com.sgvet.cliente.boundary;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import com.sgvet.cliente.boundary.ClienteDbManager;
import com.sgvet.cliente.entity.HistorialCliente;
import com.sgvet.mascota.boundary.MascotaRepository;
import com.sgvet.mascota.entity.Mascota;

public class HistorialClienteRepository {

    private ClienteDbManager clienteDbManager = ClienteDbManager.getInstance();
    private MascotaRepository mascotaRepository = new MascotaRepository();

    public HistorialClienteRepository() {
    }

    public List<HistorialCliente> listarPorClienteId(Integer clienteId) {
        List<HistorialCliente> historial = new ArrayList<>();
        String sql = "SELECT * FROM HISTORIAL_CLIENTE WHERE ID_CLIENTE = ? ORDER BY FECHA_CONSULTA DESC";

        try (PreparedStatement ps = ClienteDbManager.getConnection().prepareStatement(sql)) {
            ps.setInt(1, clienteId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                HistorialCliente h = new HistorialCliente(
                        rs.getInt("ID"),
                        rs.getInt("ID_CLIENTE"),
                        rs.getInt("ID_MASCOTA"),
                        rs.getString("TIPO_CONSULTA"),
                        rs.getString("DESCRIPCION"),
                        rs.getString("DIAGNOSTICO"),
                        rs.getString("TRATAMIENTO"),
                        rs.getTimestamp("FECHA_CONSULTA").toLocalDateTime(),
                        rs.getString("VETERINARIO"),
                        rs.getDouble("COSTO"),
                        rs.getString("OBSERVACION")
                );
                historial.add(h);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return historial;
    }

    public List<HistorialCliente> listarTodos() {
        List<HistorialCliente> historial = new ArrayList<>();
        String sql = "SELECT * FROM HISTORIAL_CLIENTE ORDER BY FECHA_CONSULTA DESC";

        try (Statement stmt = ClienteDbManager.getConnection().createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                HistorialCliente h = new HistorialCliente(
                        rs.getInt("ID"),
                        rs.getInt("ID_CLIENTE"),
                        rs.getInt("ID_MASCOTA"),
                        rs.getString("TIPO_CONSULTA"),
                        rs.getString("DESCRIPCION"),
                        rs.getString("DIAGNOSTICO"),
                        rs.getString("TRATAMIENTO"),
                        rs.getTimestamp("FECHA_CONSULTA").toLocalDateTime(),
                        rs.getString("VETERINARIO"),
                        rs.getDouble("COSTO"),
                        rs.getString("OBSERVACION")
                );
                historial.add(h);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return historial;
    }

    public void insertar(HistorialCliente historial) {
        String sql = "INSERT INTO HISTORIAL_CLIENTE (ID, ID_CLIENTE, ID_MASCOTA, TIPO_CONSULTA, " +
                "DESCRIPCION, DIAGNOSTICO, TRATAMIENTO, FECHA_CONSULTA, " +
                "VETERINARIO, COSTO, OBSERVACION) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement ps = ClienteDbManager.getConnection().prepareStatement(sql)) {
            ps.setInt(1, historial.getId());
            ps.setInt(2, historial.getIdCliente());
            ps.setInt(3, historial.getIdMascota());
            ps.setString(4, historial.getTipoConsulta());
            ps.setString(5, historial.getDescripcion());
            ps.setString(6, historial.getDiagnostico());
            ps.setString(7, historial.getTratamiento());
            ps.setTimestamp(8, Timestamp.valueOf(historial.getFechaConsulta()));
            ps.setString(9, historial.getVeterinario());
            ps.setDouble(10, historial.getCosto());
            ps.setString(11, historial.getObservacion());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public HistorialCliente buscarPorId(Integer id) {
        String sql = "SELECT * FROM HISTORIAL_CLIENTE WHERE ID = ?";

        try (PreparedStatement ps = ClienteDbManager.getConnection().prepareStatement(sql)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return new HistorialCliente(
                        rs.getInt("ID"),
                        rs.getInt("ID_CLIENTE"),
                        rs.getInt("ID_MASCOTA"),
                        rs.getString("TIPO_CONSULTA"),
                        rs.getString("DESCRIPCION"),
                        rs.getString("DIAGNOSTICO"),
                        rs.getString("TRATAMIENTO"),
                        rs.getTimestamp("FECHA_CONSULTA").toLocalDateTime(),
                        rs.getString("VETERINARIO"),
                        rs.getDouble("COSTO"),
                        rs.getString("OBSERVACION")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public boolean eliminar(Integer id) {
        String sql = "DELETE FROM HISTORIAL_CLIENTE WHERE ID = ?";

        try (PreparedStatement ps = ClienteDbManager.getConnection().prepareStatement(sql)) {
            ps.setInt(1, id);
            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}