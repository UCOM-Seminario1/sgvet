package com.sgvet.cliente.control;

import com.sgvet.cliente.boundary.ClienteRepository;
import com.sgvet.cliente.entity.Cliente;

import java.util.List;

public class ClienteController {

    private ClienteRepository clienteRepository = new ClienteRepository();
    // Aquí puedes agregar métodos para manejar las solicitudes relacionadas con los clientes
    // Por ejemplo, crear, actualizar, eliminar y listar clientes

    public Boolean crearCliente(Cliente cliente) {
        // Lógica para crear un cliente
        try{

            clienteRepository.insertar(cliente);
            return true;
        } 
        catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<Cliente> listarClientes() {
        // Lógica para listar todos los clientes
        return clienteRepository.listarTodos();
    }

    // Métodos de búsqueda
    public Cliente buscarClientePorId(Integer id) {
        try {
            return clienteRepository.buscarPorId(id);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<Cliente> buscarClientesPorNombre(String nombre) {
        try {
            return clienteRepository.buscarPorNombre(nombre);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<Cliente> buscarClientesPorApellido(String apellido) {
        try {
            return clienteRepository.buscarPorApellido(apellido);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<Cliente> buscarClientesPorTelefono(String telefono) {
        try {
            return clienteRepository.buscarPorTelefono(telefono);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<Cliente> buscarClientesGeneral(String criterio) {
        try {
            return clienteRepository.buscarGeneral(criterio);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
}
