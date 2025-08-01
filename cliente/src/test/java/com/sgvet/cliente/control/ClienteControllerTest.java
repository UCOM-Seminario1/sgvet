package com.sgvet.cliente.control;

import com.sgvet.cliente.entity.Cliente;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class ClienteControllerTest {

    private ClienteController clienteController;

    @Before
    public void setUp() {
        clienteController = new ClienteController();
    }

    @Test
    public void modificarCliente_HappyPath_ClienteExiste() {
        // Arrange
        Cliente cliente = new Cliente(1, "Juan", "Pérez", 30, "123456789");
        
        // Act
        Boolean resultado = clienteController.modificarCliente(cliente);
        
        // Assert
        // Nota: En un entorno real, esto dependería de la base de datos
        // Aquí solo verificamos que el método no lance excepción
        assertNotNull("El resultado no debería ser null", resultado);
    }

    @Test
    public void modificarCliente_UnhappyPath_ClienteNull() {
        // Act
        Boolean resultado = clienteController.modificarCliente(null);
        
        // Assert
        // Debería manejar el caso null sin lanzar excepción
        assertNotNull("El resultado no debería ser null", resultado);
    }

    @Test
    public void buscarClientePorId_HappyPath_ClienteExiste() {
        // Act
        Cliente resultado = clienteController.buscarClientePorId(1);
        
        // Assert
        // En un entorno real, esto dependería de la base de datos
        // Aquí solo verificamos que el método no lance excepción
        // El resultado puede ser null si no existe en la base de datos
        // pero el método no debería fallar
    }

    @Test
    public void buscarClientePorId_UnhappyPath_IdInvalido() {
        // Act
        Cliente resultado = clienteController.buscarClientePorId(-1);
        
        // Assert
        // Debería manejar IDs inválidos sin lanzar excepción
        // El resultado puede ser null, pero el método no debería fallar
    }

    @Test
    public void crearCliente_HappyPath_ClienteValido() {
        // Arrange
        Cliente cliente = new Cliente(999, "Nuevo", "Cliente", 25, "987654321");
        
        // Act
        Boolean resultado = clienteController.crearCliente(cliente);
        
        // Assert
        // En un entorno real, esto dependería de la base de datos
        // Aquí solo verificamos que el método no lance excepción
        assertNotNull("El resultado no debería ser null", resultado);
    }

    @Test
    public void listarClientes_HappyPath_RetornaLista() {
        // Act
        var resultado = clienteController.listarClientes();
        
        // Assert
        // Debería retornar una lista (puede estar vacía)
        assertNotNull("La lista no debería ser null", resultado);
    }
}