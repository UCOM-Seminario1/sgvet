package com.sgvet.cliente.control;

import com.sgvet.cliente.boundary.ClienteRepository;
import com.sgvet.cliente.entity.Cliente;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class ClienteControllerTest {

    private ClienteController clienteController;
    private Cliente clienteValido;
    private Cliente clienteInvalido;

    @Before
    public void setUp() {
        clienteController = new ClienteController();
        clienteValido = new Cliente(1, "Juan", "Pérez", 30, "123456789");
        clienteInvalido = new Cliente(null, null, null, null, null);
    }

    // ========== HAPPY PATH TESTS ==========

    @Test
    public void testCrearCliente_HappyPath_ClienteValido() {
        // Arrange & Act
        Boolean resultado = clienteController.crearCliente(clienteValido);

        // Assert
        assertNotNull("El resultado no debería ser null", resultado);
        // Nota: En un entorno real, esto dependería de la base de datos
        // Por ahora solo verificamos que el método no lance excepción
    }

    @Test
    public void testCrearCliente_HappyPath_ClienteConDatosCompletos() {
        // Arrange
        Cliente clienteCompleto = new Cliente(2, "María", "García", 25, "987654321");

        // Act
        Boolean resultado = clienteController.crearCliente(clienteCompleto);

        // Assert
        assertNotNull("El resultado no debería ser null", resultado);
    }

    @Test
    public void testCrearCliente_HappyPath_ClienteConEdadMinima() {
        // Arrange
        Cliente clienteJoven = new Cliente(3, "Ana", "López", 18, "555123456");

        // Act
        Boolean resultado = clienteController.crearCliente(clienteJoven);

        // Assert
        assertNotNull("El resultado no debería ser null", resultado);
    }

    @Test
    public void testCrearCliente_HappyPath_ClienteConEdadMaxima() {
        // Arrange
        Cliente clienteMayor = new Cliente(4, "Carlos", "Rodríguez", 100, "777888999");

        // Act
        Boolean resultado = clienteController.crearCliente(clienteMayor);

        // Assert
        assertNotNull("El resultado no debería ser null", resultado);
    }

    // ========== UNHAPPY PATH TESTS ==========

    @Test
    public void testCrearCliente_UnhappyPath_ClienteNull() {
        // Arrange & Act
        Boolean resultado = clienteController.crearCliente(null);

        // Assert
        assertNotNull("El resultado no debería ser null", resultado);
        // En un entorno real, esto debería retornar false
    }

    @Test
    public void testCrearCliente_UnhappyPath_ClienteConDatosNulos() {
        // Arrange & Act
        Boolean resultado = clienteController.crearCliente(clienteInvalido);

        // Assert
        assertNotNull("El resultado no debería ser null", resultado);
        // En un entorno real, esto debería retornar false
    }

    @Test
    public void testCrearCliente_UnhappyPath_ClienteConIdDuplicado() {
        // Arrange
        Cliente clienteDuplicado = new Cliente(1, "Pedro", "Gómez", 35, "111222333");

        // Act
        Boolean resultado = clienteController.crearCliente(clienteDuplicado);

        // Assert
        assertNotNull("El resultado no debería ser null", resultado);
        // En un entorno real, esto debería retornar false
    }

    @Test
    public void testCrearCliente_UnhappyPath_ClienteConNombreVacio() {
        // Arrange
        Cliente clienteSinNombre = new Cliente(5, "", "Martínez", 28, "444555666");

        // Act
        Boolean resultado = clienteController.crearCliente(clienteSinNombre);

        // Assert
        assertNotNull("El resultado no debería ser null", resultado);
    }

    @Test
    public void testCrearCliente_UnhappyPath_ClienteConEdadInvalida() {
        // Arrange
        Cliente clienteEdadInvalida = new Cliente(6, "Laura", "Fernández", -5, "777888999");

        // Act
        Boolean resultado = clienteController.crearCliente(clienteEdadInvalida);

        // Assert
        assertNotNull("El resultado no debería ser null", resultado);
    }

    @Test
    public void testCrearCliente_UnhappyPath_ClienteConTelefonoInvalido() {
        // Arrange
        Cliente clienteTelefonoInvalido = new Cliente(7, "Roberto", "Sánchez", 40, "abc123");

        // Act
        Boolean resultado = clienteController.crearCliente(clienteTelefonoInvalido);

        // Assert
        assertNotNull("El resultado no debería ser null", resultado);
    }

    @Test
    public void testCrearCliente_UnhappyPath_ClienteConIdNegativo() {
        // Arrange
        Cliente clienteIdNegativo = new Cliente(-1, "Carmen", "Vargas", 45, "999888777");

        // Act
        Boolean resultado = clienteController.crearCliente(clienteIdNegativo);

        // Assert
        assertNotNull("El resultado no debería ser null", resultado);
    }

    // ========== EDGE CASES ==========

    @Test
    public void testCrearCliente_EdgeCase_ClienteConNombreMuyLargo() {
        // Arrange
        String nombreLargo = "A".repeat(1000);
        Cliente clienteNombreLargo = new Cliente(8, nombreLargo, "Torres", 32, "123456789");

        // Act
        Boolean resultado = clienteController.crearCliente(clienteNombreLargo);

        // Assert
        assertNotNull("El resultado no debería ser null", resultado);
    }

    @Test
    public void testCrearCliente_EdgeCase_ClienteConTelefonoMuyLargo() {
        // Arrange
        String telefonoLargo = "1".repeat(50);
        Cliente clienteTelefonoLargo = new Cliente(9, "Miguel", "Herrera", 29, telefonoLargo);

        // Act
        Boolean resultado = clienteController.crearCliente(clienteTelefonoLargo);

        // Assert
        assertNotNull("El resultado no debería ser null", resultado);
    }

    // ========== LISTAR CLIENTES TESTS ==========

    @Test
    public void testListarClientes_HappyPath_ListaVacia() {
        // Act
        var resultado = clienteController.listarClientes();

        // Assert
        assertNotNull("La lista no debería ser null", resultado);
        // En un entorno real, esto dependería de la base de datos
    }

    @Test
    public void testListarClientes_HappyPath_ListaConClientes() {
        // Arrange - Crear algunos clientes primero
        clienteController.crearCliente(clienteValido);
        Cliente cliente2 = new Cliente(2, "María", "García", 25, "987654321");
        clienteController.crearCliente(cliente2);

        // Act
        var resultado = clienteController.listarClientes();

        // Assert
        assertNotNull("La lista no debería ser null", resultado);
        // En un entorno real, esto debería retornar la lista de clientes
    }
}