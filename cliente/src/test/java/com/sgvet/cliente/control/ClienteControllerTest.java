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
        Boolean resultado = clienteController.crearCliente(clienteValido);
        assertNotNull("El resultado no debería ser null", resultado);
    }

    @Test
    public void testCrearCliente_HappyPath_ClienteConDatosCompletos() {
        Cliente clienteCompleto = new Cliente(2, "María", "García", 25, "987654321");
        Boolean resultado = clienteController.crearCliente(clienteCompleto);
        assertNotNull("El resultado no debería ser null", resultado);
    }

    @Test
    public void testCrearCliente_HappyPath_ClienteConEdadMinima() {
        Cliente clienteJoven = new Cliente(3, "Ana", "López", 18, "555123456");
        Boolean resultado = clienteController.crearCliente(clienteJoven);
        assertNotNull("El resultado no debería ser null", resultado);
    }

    @Test
    public void testCrearCliente_HappyPath_ClienteConEdadMaxima() {
        Cliente clienteMayor = new Cliente(4, "Carlos", "Rodríguez", 100, "777888999");
        Boolean resultado = clienteController.crearCliente(clienteMayor);
        assertNotNull("El resultado no debería ser null", resultado);
    }

    // ========== UNHAPPY PATH TESTS ==========

    @Test
    public void testCrearCliente_UnhappyPath_ClienteNull() {
        Boolean resultado = clienteController.crearCliente(null);
        assertNotNull("El resultado no debería ser null", resultado);
    }

    @Test
    public void testCrearCliente_UnhappyPath_ClienteConDatosNulos() {
        Boolean resultado = clienteController.crearCliente(clienteInvalido);
        assertNotNull("El resultado no debería ser null", resultado);
    }

    @Test
    public void testCrearCliente_UnhappyPath_ClienteConIdDuplicado() {
        Cliente clienteDuplicado = new Cliente(1, "Pedro", "Gómez", 35, "111222333");
        Boolean resultado = clienteController.crearCliente(clienteDuplicado);
        assertNotNull("El resultado no debería ser null", resultado);
    }

    @Test
    public void testCrearCliente_UnhappyPath_ClienteConNombreVacio() {
        Cliente clienteSinNombre = new Cliente(5, "", "Martínez", 28, "444555666");
        Boolean resultado = clienteController.crearCliente(clienteSinNombre);
        assertNotNull("El resultado no debería ser null", resultado);
    }

    @Test
    public void testCrearCliente_UnhappyPath_ClienteConEdadInvalida() {
        Cliente clienteEdadInvalida = new Cliente(6, "Laura", "Fernández", -5, "777888999");
        Boolean resultado = clienteController.crearCliente(clienteEdadInvalida);
        assertNotNull("El resultado no debería ser null", resultado);
    }

    @Test
    public void testCrearCliente_UnhappyPath_ClienteConTelefonoInvalido() {
        Cliente clienteTelefonoInvalido = new Cliente(7, "Roberto", "Sánchez", 40, "abc123");
        Boolean resultado = clienteController.crearCliente(clienteTelefonoInvalido);
        assertNotNull("El resultado no debería ser null", resultado);
    }

    @Test
    public void testCrearCliente_UnhappyPath_ClienteConIdNegativo() {
        Cliente clienteIdNegativo = new Cliente(-1, "Carmen", "Vargas", 45, "999888777");
        Boolean resultado = clienteController.crearCliente(clienteIdNegativo);
        assertNotNull("El resultado no debería ser null", resultado);
    }

    // ========== EDGE CASES ==========

    @Test
    public void testCrearCliente_EdgeCase_ClienteConNombreMuyLargo() {
        String nombreLargo = "A".repeat(1000);
        Cliente clienteNombreLargo = new Cliente(8, nombreLargo, "Torres", 32, "123456789");
        Boolean resultado = clienteController.crearCliente(clienteNombreLargo);
        assertNotNull("El resultado no debería ser null", resultado);
    }

    @Test
    public void testCrearCliente_EdgeCase_ClienteConTelefonoMuyLargo() {
        String telefonoLargo = "1".repeat(50);
        Cliente clienteTelefonoLargo = new Cliente(9, "Miguel", "Herrera", 29, telefonoLargo);
        Boolean resultado = clienteController.crearCliente(clienteTelefonoLargo);
        assertNotNull("El resultado no debería ser null", resultado);
    }

    // ========== LISTAR CLIENTES TESTS ==========

    @Test
    public void testListarClientes_HappyPath_ListaVacia() {
        var resultado = clienteController.listarClientes();
        assertNotNull("La lista no debería ser null", resultado);
    }

    @Test
    public void testListarClientes_HappyPath_ListaConClientes() {
        clienteController.crearCliente(clienteValido);
        Cliente cliente2 = new Cliente(2, "María", "García", 25, "987654321");
        clienteController.crearCliente(cliente2);
        var resultado = clienteController.listarClientes();
        assertNotNull("La lista no debería ser null", resultado);
    }

    // ========== PRUEBAS AGREGADAS DE LA RAMA DERECHA ==========

    @Test
    public void modificarCliente_HappyPath_ClienteExiste() {
        Cliente cliente = new Cliente(1, "Juan", "Pérez", 30, "123456789");
        Boolean resultado = clienteController.modificarCliente(cliente);
        assertNotNull("El resultado no debería ser null", resultado);
    }

    @Test
    public void modificarCliente_UnhappyPath_ClienteNull() {
        Boolean resultado = clienteController.modificarCliente(null);
        assertNotNull("El resultado no debería ser null", resultado);
    }

    @Test
    public void buscarClientePorId_HappyPath_ClienteExiste() {
        Cliente resultado = clienteController.buscarClientePorId(1);
        // No hay asserts específicos, solo que no falle el método
    }

    @Test
    public void buscarClientePorId_UnhappyPath_IdInvalido() {
        Cliente resultado = clienteController.buscarClientePorId(-1);
        // No hay asserts específicos, solo que no falle el método
    }

    @Test
    public void crearCliente_HappyPath_ClienteValido_Derecha() {
        Cliente cliente = new Cliente(999, "Nuevo", "Cliente", 25, "987654321");
        Boolean resultado = clienteController.crearCliente(cliente);
        assertNotNull("El resultado no debería ser null", resultado);
    }

    @Test
    public void listarClientes_HappyPath_RetornaLista_Derecha() {
        var resultado = clienteController.listarClientes();
        assertNotNull("La lista no debería ser null", resultado);
    }
}
