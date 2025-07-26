package com.sgvet.cliente.boundary;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import com.sgvet.cliente.boundary.ClienteDbManager;
import com.sgvet.cliente.entity.Cliente;    

public class ClienteRepository {
    ClienteDbManager clienteDbManager = ClienteDbManager.getInstance();

    public ClienteRepository() {
    }


    public List<Cliente> listarTodos() {
        List<Cliente> clientes = new ArrayList<>();
        String sql = "SELECT * FROM CLIENTE";

        try (Statement stmt = ClienteDbManager.getConnection().createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Cliente c = new Cliente(
                        rs.getInt("ID"),
                        rs.getString("NOMBRE"),
                        rs.getString("APELLIDO"),
                        rs.getInt("EDAD"),
                        rs.getString("TELEFONO")
                );
                clientes.add(c);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return clientes;
    }

    public void insertar(Cliente cliente) {
        String sql = "INSERT INTO CLIENTE (ID, NOMBRE, APELLIDO, EDAD, TELEFONO) VALUES (?, ?, ?, ?, ?)";

        try (PreparedStatement ps = ClienteDbManager.getConnection().prepareStatement(sql)) {
            ps.setInt(1, cliente.getId());
            ps.setString(2, cliente.getNombre());
            ps.setString(3, cliente.getApellido());
            ps.setInt(4, cliente.getEdad());
            ps.setString(5, cliente.getTelefono());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Métodos de búsqueda
    public Cliente buscarPorId(Integer id) {
        String sql = "SELECT * FROM CLIENTE WHERE ID = ?";
        
        try (PreparedStatement ps = ClienteDbManager.getConnection().prepareStatement(sql)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            
            if (rs.next()) {
                return new Cliente(
                    rs.getInt("ID"),
                    rs.getString("NOMBRE"),
                    rs.getString("APELLIDO"),
                    rs.getInt("EDAD"),
                    rs.getString("TELEFONO")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Cliente> buscarPorNombre(String nombre) {
        List<Cliente> clientes = new ArrayList<>();
        String sql = "SELECT * FROM CLIENTE WHERE UPPER(NOMBRE) LIKE UPPER(?)";
        
        try (PreparedStatement ps = ClienteDbManager.getConnection().prepareStatement(sql)) {
            ps.setString(1, "%" + nombre + "%");
            ResultSet rs = ps.executeQuery();
            
            while (rs.next()) {
                Cliente c = new Cliente(
                    rs.getInt("ID"),
                    rs.getString("NOMBRE"),
                    rs.getString("APELLIDO"),
                    rs.getInt("EDAD"),
                    rs.getString("TELEFONO")
                );
                clientes.add(c);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return clientes;
    }

    public List<Cliente> buscarPorApellido(String apellido) {
        List<Cliente> clientes = new ArrayList<>();
        String sql = "SELECT * FROM CLIENTE WHERE UPPER(APELLIDO) LIKE UPPER(?)";
        
        try (PreparedStatement ps = ClienteDbManager.getConnection().prepareStatement(sql)) {
            ps.setString(1, "%" + apellido + "%");
            ResultSet rs = ps.executeQuery();
            
            while (rs.next()) {
                Cliente c = new Cliente(
                    rs.getInt("ID"),
                    rs.getString("NOMBRE"),
                    rs.getString("APELLIDO"),
                    rs.getInt("EDAD"),
                    rs.getString("TELEFONO")
                );
                clientes.add(c);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return clientes;
    }

    public List<Cliente> buscarPorTelefono(String telefono) {
        List<Cliente> clientes = new ArrayList<>();
        String sql = "SELECT * FROM CLIENTE WHERE TELEFONO LIKE ?";
        
        try (PreparedStatement ps = ClienteDbManager.getConnection().prepareStatement(sql)) {
            ps.setString(1, "%" + telefono + "%");
            ResultSet rs = ps.executeQuery();
            
            while (rs.next()) {
                Cliente c = new Cliente(
                    rs.getInt("ID"),
                    rs.getString("NOMBRE"),
                    rs.getString("APELLIDO"),
                    rs.getInt("EDAD"),
                    rs.getString("TELEFONO")
                );
                clientes.add(c);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return clientes;
    }

    public List<Cliente> buscarGeneral(String criterio) {
        List<Cliente> clientes = new ArrayList<>();
        String sql = "SELECT * FROM CLIENTE WHERE UPPER(NOMBRE) LIKE UPPER(?) OR UPPER(APELLIDO) LIKE UPPER(?) OR TELEFONO LIKE ?";
        
        try (PreparedStatement ps = ClienteDbManager.getConnection().prepareStatement(sql)) {
            String busqueda = "%" + criterio + "%";
            ps.setString(1, busqueda);
            ps.setString(2, busqueda);
            ps.setString(3, busqueda);
            ResultSet rs = ps.executeQuery();
            
            while (rs.next()) {
                Cliente c = new Cliente(
                    rs.getInt("ID"),
                    rs.getString("NOMBRE"),
                    rs.getString("APELLIDO"),
                    rs.getInt("EDAD"),
                    rs.getString("TELEFONO")
                );
                clientes.add(c);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return clientes;
    }
}
