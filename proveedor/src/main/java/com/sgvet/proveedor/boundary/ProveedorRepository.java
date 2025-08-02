package com.sgvet.proveedor.boundary;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import java.util.logging.Level;

import com.sgvet.proveedor.entity.Proveedor;
import com.sgvet.proveedor.util.Constants;
import com.sgvet.proveedor.util.SecurityUtils;

public class ProveedorRepository {
    
    private static final Logger LOGGER = Logger.getLogger(ProveedorRepository.class.getName());
    private final ProveedorDbManager proveedorDbManager;

    public ProveedorRepository() {
        this.proveedorDbManager = ProveedorDbManager.getInstance();
    }

    public List<Proveedor> listarTodos() {
        List<Proveedor> proveedores = new ArrayList<>();
        
        try (Statement stmt = ProveedorDbManager.getConnection().createStatement();
             ResultSet rs = stmt.executeQuery(Constants.SELECT_ALL_PROVEEDORES)) {

            while (rs.next()) {
                Proveedor proveedor = mapResultSetToProveedor(rs);
                proveedores.add(proveedor);
            }
            
            LOGGER.log(Level.INFO, "Listados {0} proveedores exitosamente", proveedores.size());
            
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error al listar proveedores", e);
            // No relanzar excepción, devolver lista vacía para mantener funcionalidad
        }

        return proveedores;
    }

    public void insertar(Proveedor proveedor) {
        if (proveedor == null) {
            LOGGER.log(Level.WARNING, "Intento de insertar proveedor null");
            return;
        }

        try (PreparedStatement ps = ProveedorDbManager.getConnection().prepareStatement(
                Constants.INSERT_PROVEEDOR, Statement.RETURN_GENERATED_KEYS)) {
            
            setProveedorParameters(ps, proveedor);
            ps.executeUpdate();

            try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    proveedor.setId(generatedKeys.getInt(1));
                }
            }
            
            SecurityUtils.secureLog("Proveedor insertado", proveedor.getNombre());
            
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error al insertar proveedor", e);
            throw new RuntimeException("Error al insertar proveedor", e);
        }
    }

    public Proveedor buscarPorId(Integer id) {
        if (id == null || id <= 0) {
            LOGGER.log(Level.WARNING, "ID inválido para búsqueda: {0}", id);
            return null;
        }

        try (PreparedStatement ps = ProveedorDbManager.getConnection().prepareStatement(Constants.SELECT_BY_ID)) {
            ps.setInt(1, id);
            
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Proveedor proveedor = mapResultSetToProveedor(rs);
                    LOGGER.log(Level.INFO, "Proveedor encontrado con ID: {0}", id);
                    return proveedor;
                }
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error al buscar proveedor por ID: " + id, e);
        }

        LOGGER.log(Level.INFO, "Proveedor no encontrado con ID: {0}", id);
        return null;
    }

    public boolean actualizar(Proveedor proveedor) {
        if (proveedor == null || proveedor.getId() == null) {
            LOGGER.log(Level.WARNING, "Proveedor o ID null en actualización");
            return false;
        }

        String sql = Constants.UPDATE_PROVEEDOR;
        
        try (PreparedStatement ps = ProveedorDbManager.getConnection().prepareStatement(sql)) {
            ps.setString(1, SecurityUtils.sanitizeInput(proveedor.getNombre()));
            ps.setString(2, SecurityUtils.sanitizeInput(proveedor.getRazonSocial()));
            ps.setString(3, SecurityUtils.sanitizeInput(proveedor.getTelefono()));
            ps.setString(4, SecurityUtils.sanitizeInput(proveedor.getCorreo()));
            ps.setInt(5, proveedor.getId());
            
            int filasAfectadas = ps.executeUpdate();
            boolean actualizado = filasAfectadas > 0;
            
            if (actualizado) {
                SecurityUtils.secureLog("Proveedor actualizado", proveedor.getNombre());
            } else {
                LOGGER.log(Level.WARNING, "No se actualizó ningún proveedor con ID: {0}", proveedor.getId());
            }
            
            return actualizado;
            
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error al actualizar proveedor", e);
            return false;
        }
    }

    public boolean existeCorreoEnOtroProveedor(String correo, Integer idProveedorActual) {
        if (correo == null || correo.trim().isEmpty() || idProveedorActual == null) {
            return false;
        }
        
        String correoSanitizado = SecurityUtils.sanitizeInput(correo);
        
        try (PreparedStatement ps = ProveedorDbManager.getConnection().prepareStatement(Constants.CHECK_EMAIL_EXISTS)) {
            ps.setString(1, correoSanitizado);
            ps.setInt(2, idProveedorActual);
            
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error al verificar existencia de correo", e);
        }
        
        return false;
    }

    public List<Proveedor> buscarPorNombre(String nombre) {
        List<Proveedor> proveedores = new ArrayList<>();
        
        if (nombre == null || nombre.trim().isEmpty()) {
            LOGGER.log(Level.WARNING, "Búsqueda con nombre vacío o null");
            return proveedores;
        }
        
        String nombreSanitizado = SecurityUtils.sanitizeInput(nombre);
        
        try (PreparedStatement ps = ProveedorDbManager.getConnection().prepareStatement(Constants.SELECT_BY_NOMBRE)) {
            ps.setString(1, "%" + nombreSanitizado + "%");
            
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Proveedor proveedor = mapResultSetToProveedor(rs);
                    proveedores.add(proveedor);
                }
            }
            
            LOGGER.log(Level.INFO, "Encontrados {0} proveedores con nombre: {1}", 
                new Object[]{proveedores.size(), nombreSanitizado});
            
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error al buscar proveedores por nombre", e);
        }

        return proveedores;
    }
    
    /**
     * Mapea un ResultSet a un objeto Proveedor
     * @param rs ResultSet de la consulta
     * @return Proveedor mapeado
     * @throws SQLException si hay error en el mapeo
     */
    private Proveedor mapResultSetToProveedor(ResultSet rs) throws SQLException {
        return new Proveedor(
                rs.getInt("ID"),
                rs.getString("NOMBRE"),
                rs.getString("RAZONSOCIAL"),
                rs.getString("TELEFONO"),
                rs.getString("CORREO")
        );
    }
    
    /**
     * Establece los parámetros del PreparedStatement para inserción
     * @param ps PreparedStatement
     * @param proveedor Proveedor con los datos
     * @throws SQLException si hay error al establecer parámetros
     */
    private void setProveedorParameters(PreparedStatement ps, Proveedor proveedor) throws SQLException {
        ps.setInt(1, proveedor.getId());
        ps.setString(2, SecurityUtils.sanitizeInput(proveedor.getNombre()));
        ps.setString(3, SecurityUtils.sanitizeInput(proveedor.getRazonSocial()));
        ps.setString(4, SecurityUtils.sanitizeInput(proveedor.getTelefono()));
        ps.setString(5, SecurityUtils.sanitizeInput(proveedor.getCorreo()));
    }
}