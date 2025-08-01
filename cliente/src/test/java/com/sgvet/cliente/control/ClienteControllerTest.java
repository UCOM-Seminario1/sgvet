package com.sgvet.cliente.control;

import com.sgvet.cliente.entity.Cliente;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class ClienteControllerTest {

    private ClienteController clienteController;

    @Before
    public void setUp() {
        clienteController = new ClienteController();
    }

    // Happy Path Tests - Pruebas básicas sin base de datos
    @Test
    public void buscarClientePorId_NotFound() {
        // Act
        Cliente result = clienteController.buscarClientePorId(999);

        // Assert
        assertNull(result);
    }

    @Test
    public void buscarClientePorId_ZeroId() {
        // Act
        Cliente result = clienteController.buscarClientePorId(0);

        // Assert
        assertNull(result);
    }

    @Test
    public void buscarClientePorId_NegativeId() {
        // Act
        Cliente result = clienteController.buscarClientePorId(-1);

        // Assert
        assertNull(result);
    }

    @Test
    public void buscarClientesPorNombre_EmptyString() {
        // Act
        List<Cliente> result = clienteController.buscarClientesPorNombre("");

        // Assert
        assertNotNull(result);
        // Puede retornar lista vacía o null dependiendo de la implementación
    }

    @Test
    public void buscarClientesPorNombre_NullString() {
        // Act
        List<Cliente> result = clienteController.buscarClientesPorNombre(null);

        // Assert
        // Puede retornar null o lista vacía dependiendo de la implementación
        assertTrue(result == null || result.isEmpty());
    }

    @Test
    public void buscarClientesPorApellido_EmptyString() {
        // Act
        List<Cliente> result = clienteController.buscarClientesPorApellido("");

        // Assert
        assertNotNull(result);
        // Puede retornar lista vacía o null dependiendo de la implementación
    }

    @Test
    public void buscarClientesPorApellido_NullString() {
        // Act
        List<Cliente> result = clienteController.buscarClientesPorApellido(null);

        // Assert
        // Puede retornar null o lista vacía dependiendo de la implementación
        assertTrue(result == null || result.isEmpty());
    }

    @Test
    public void buscarClientesPorTelefono_EmptyString() {
        // Act
        List<Cliente> result = clienteController.buscarClientesPorTelefono("");

        // Assert
        assertNotNull(result);
        // Puede retornar lista vacía o null dependiendo de la implementación
    }

    @Test
    public void buscarClientesPorTelefono_NullString() {
        // Act
        List<Cliente> result = clienteController.buscarClientesPorTelefono(null);

        // Assert
        // Puede retornar null o lista vacía dependiendo de la implementación
        assertTrue(result == null || result.isEmpty());
    }

    // Pruebas de funcionalidad básica
    @Test
    public void testListarClientes_Success() {
        // Act
        List<Cliente> result = clienteController.listarClientes();

        // Assert
        assertNotNull(result);
        // Debe retornar una lista (puede estar vacía)
    }

    @Test
    public void testCrearCliente_WithValidData() {
        // Arrange
        Cliente cliente = new Cliente(1, "Test", "User", 25, "999999999");

        // Act
        Boolean result = clienteController.crearCliente(cliente);

        // Assert
        assertTrue("La creación de cliente debe ser exitosa", result);
    }

    @Test
    public void testCrearCliente_WithNullCliente() {
        // Act
        Boolean result = clienteController.crearCliente(null);

        // Assert
        assertFalse("La creación con cliente null debe fallar", result);
    }

    @Test
    public void testModificarCliente_WithValidData() {
        // Arrange
        Cliente cliente = new Cliente(2, "Original", "Name", 30, "888888888");
        clienteController.crearCliente(cliente);
        
        Cliente clienteModificado = new Cliente(2, "Modified", "Name", 31, "888888888");

        // Act
        Boolean result = clienteController.modificarCliente(clienteModificado);

        // Assert
        assertTrue("La modificación de cliente debe ser exitosa", result);
    }

    @Test
    public void testModificarCliente_WithNullCliente() {
        // Act
        Boolean result = clienteController.modificarCliente(null);

        // Assert
        assertFalse("La modificación con cliente null debe fallar", result);
    }

    @Test
    public void testBuscarClientesGeneral_WithValidString() {
        // Act
        List<Cliente> result = clienteController.buscarClientesGeneral("test");

        // Assert
        assertNotNull(result);
        // Debe retornar una lista (puede estar vacía)
    }

    @Test
    public void testBuscarClientesGeneral_WithEmptyString() {
        // Act
        List<Cliente> result = clienteController.buscarClientesGeneral("");

        // Assert
        assertNotNull(result);
        // Debe retornar una lista (puede estar vacía)
    }

    @Test
    public void testBuscarClientesGeneral_WithNullString() {
        // Act
        List<Cliente> result = clienteController.buscarClientesGeneral(null);

        // Assert
        assertTrue(result == null || result.isEmpty());
    }

    // Pruebas de validación de datos
    @Test
    public void testClienteEntity_ValidData() {
        // Arrange
        Cliente cliente = new Cliente(3, "Juan", "Pérez", 30, "123456789");

        // Assert
        assertEquals(Integer.valueOf(3), cliente.getId());
        assertEquals("Juan", cliente.getNombre());
        assertEquals("Pérez", cliente.getApellido());
        assertEquals(Integer.valueOf(30), cliente.getEdad());
        assertEquals("123456789", cliente.getTelefono());
    }

    @Test
    public void testClienteEntity_SettersAndGetters() {
        // Arrange
        Cliente cliente = new Cliente();

        // Act
        cliente.setId(4);
        cliente.setNombre("María");
        cliente.setApellido("García");
        cliente.setEdad(25);
        cliente.setTelefono("987654321");

        // Assert
        assertEquals(Integer.valueOf(4), cliente.getId());
        assertEquals("María", cliente.getNombre());
        assertEquals("García", cliente.getApellido());
        assertEquals(Integer.valueOf(25), cliente.getEdad());
        assertEquals("987654321", cliente.getTelefono());
    }

    @Test
    public void testClienteEntity_ToString() {
        // Arrange
        Cliente cliente = new Cliente(5, "Carlos", "López", 35, "555555555");

        // Act
        String result = cliente.toString();

        // Assert
        assertNotNull(result);
        assertTrue(result.contains("Carlos"));
        assertTrue(result.contains("López"));
    }
}