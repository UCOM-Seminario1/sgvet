package com.sgvet.facturacion.boundary;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import com.sgvet.facturacion.boundary.FacturacionDbManager;
import com.sgvet.facturacion.entity.Facturacion;    

public class FacturacionRepository {
    FacturacionDbManager facturacionDbManager = FacturacionDbManager.getInstance();

    public FacturacionRepository() {
    }

    public boolean eliminarPorId(int id) {
        String sql = "DELETE FROM FACTURA WHERE ID = ?";
        try (PreparedStatement ps = FacturacionDbManager.getConnection().prepareStatement(sql)) {
            ps.setInt(1, id);
            int affected = ps.executeUpdate();
            return affected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<Facturacion> listarTodos() {
        List<Facturacion> facturas = new ArrayList<>();
        String sql = "SELECT * FROM FACTURA";

        try (Statement stmt = FacturacionDbManager.getConnection().createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Facturacion f = new Facturacion(
                        rs.getInt("ID"),
                        rs.getString("NOMBRE"),
                        rs.getString("RAZONSOCIAL"),
                        rs.getInt("CANTIDAD"),
                        rs.getString("IMPORTE"),
                        rs.getInt("IVA"),
                        rs.getString("TOTAL"),
                        rs.getString("DESCRIPCION")
                );
                facturas.add(f);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return facturas;
    }

    public void insertar(Facturacion facturacion) {
        String sql = "INSERT INTO FACTURA (ID, NOMBRE, RAZONSOCIAL, CANTIDAD, IMPORTE, IVA, TOTAL, DESCRIPCION) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement ps = FacturacionDbManager.getConnection().prepareStatement(sql)) {
            ps.setInt(1, facturacion.getId());
            ps.setString(2, facturacion.getNombre());
            ps.setString(3, facturacion.getRazonSocial());
            ps.setInt(4, facturacion.getCantidad());
            ps.setString(5, facturacion.getImporte());
            ps.setInt(6, facturacion.getIva());
            ps.setString(7, facturacion.getTotal());
            ps.setString(8, facturacion.getDescripcion());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
