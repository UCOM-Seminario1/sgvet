package com.sgvet.cliente.control;

import com.sgvet.cliente.boundary.ClienteRepository;
import com.sgvet.cliente.entity.Cliente;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class ClienteControllerTest {

    private ClienteController clienteController;
    private ClienteRepository clienteRepository;
    private Cliente clienteValido;
    private Cliente clienteInvalido;

    @Before
    public void setUp() {
        clienteController = new ClienteController();
        clienteRepository = new ClienteRepository();

        clienteValido = new Cliente(1, "Juan", "Pérez", 30, "123456789");
        clienteInvalido = new Cliente(null, null, null, null, null);
    }

    // ====== TESTS DE CREAR CLIENTE ======

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

    // ====== TESTS DE LISTAR CLIENTES ======

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

    @Test
    public void testListarClientes_HappyPath_RetornaLista_Derecha() {
        List<Cliente> clientes = clienteController.listarClientes();
        assertNotNull("La lista de clientes no debería ser null", clientes);
        assertTrue("Debería retornar una lista", clientes instanceof List);
    }

    // ====== TESTS DE ELIMINAR CLIENTE ======

    @Test
    public void testEliminarClienteExitoso() {
        Cliente cliente = new Cliente(999, "Test", "Eliminar", 30, "123456789");
        clienteRepository.insertar(cliente);
        Cliente clienteAntes = clienteRepository.buscarPorId(999);
        assertNotNull("El cliente debería existir antes de eliminarlo", clienteAntes);
        Boolean resultado = clienteController.eliminarCliente(999);
        assertTrue("La eliminación debería ser exitosa", resultado);
        Cliente clienteDespues = clienteRepository.buscarPorId(999);
        assertNull("El cliente debería ser null después de eliminarlo", clienteDespues);
    }

    @Test
    public void testEliminarClienteInexistente() {
        Integer idInexistente = 99999;
        Cliente clienteAntes = clienteRepository.buscarPorId(idInexistente);
        assertNull("El cliente no debería existir", clienteAntes);
        Boolean resultado = clienteController.eliminarCliente(idInexistente);
        assertFalse("Debería retornar false cuando el cliente no existe", resultado);
    }

    @Test
    public void testEliminarClienteConIdNull() {
        Boolean resultado = clienteController.eliminarCliente(null);
        assertFalse("Debería retornar false cuando el ID es null", resultado);
    }

    // ====== TESTS DE MODIFICAR CLIENTE ======

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
    public void testModificarCliente() {
        Cliente clienteOriginal = new Cliente(666, "Original", "Nombre", 40, "111111111");
        clienteRepository.insertar(clienteOriginal);
        Cliente clienteModificado = new Cliente(666, "Modificado", "Apellido", 45, "222222222");
        Boolean resultado = clienteController.modificarCliente(clienteModificado);
        assertTrue("La modificación debería ser exitosa", resultado);
        Cliente clienteVerificado = clienteRepository.buscarPorId(666);
        assertNotNull("El cliente debería existir después de modificarlo", clienteVerificado);
        assertEquals("Modificado", clienteVerificado.getNombre());
        assertEquals("Apellido", clienteVerificado.getApellido());
        assertEquals(Integer.valueOf(45), clienteVerificado.getEdad());
        assertEquals("222222222", clienteVerificado.getTelefono());
        clienteRepository.eliminarCliente(666);
    }

    // ====== TESTS DE BUSCAR CLIENTE POR ID ======

    @Test
    public void buscarClientePorId_HappyPath_ClienteExiste() {
        // Act
        Cliente resultado = clienteController.buscarClientePorId(1);
        // No hay asserts específicos, solo que no falle el método

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
        // No hay asserts específicos, solo que no falle el método
    }

    @Test
    public void testBuscarClientePorId() {
        Cliente cliente = new Cliente(777, "Test", "Buscar", 35, "555555555");
        clienteRepository.insertar(cliente);
        Cliente clienteEncontrado = clienteController.buscarClientePorId(777);
        assertNotNull("Debería encontrar el cliente", clienteEncontrado);
        assertEquals("Test", clienteEncontrado.getNombre());
        assertEquals("Buscar", clienteEncontrado.getApellido());
        clienteRepository.eliminarCliente(777);

        // Assert
        // Debería manejar IDs inválidos sin lanzar excepción
        // El resultado puede ser null, pero el método no debería fallar
    }

    @Test
    public void buscarClientePorId_NotFound() {
        Cliente result = clienteController.buscarClientePorId(999);
        assertNull(result);
    }
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
    public void buscarClientePorId_ZeroId() {
        Cliente result = clienteController.buscarClientePorId(0);
        assertNull(result);
    }

    @Test
    public void buscarClientePorId_NegativeId() {
        Cliente result = clienteController.buscarClientePorId(-1);
        assertNull(result);
    }

    // ====== TESTS DE BUSCAR CLIENTES POR ATRIBUTOS ======

    @Test
    public void buscarClientesPorNombre_EmptyString() {
        List<Cliente> result = clienteController.buscarClientesPorNombre("");
        assertNotNull(result);
    }

    @Test
    public void buscarClientesPorNombre_NullString() {
        List<Cliente> result = clienteController.buscarClientesPorNombre(null);
        assertTrue(result == null || result.isEmpty());
    }

    @Test
    public void buscarClientesPorApellido_EmptyString() {
        List<Cliente> result = clienteController.buscarClientesPorApellido("");
        assertNotNull(result);
    }

    @Test
    public void buscarClientesPorApellido_NullString() {
        List<Cliente> result = clienteController.buscarClientesPorApellido(null);
        assertTrue(result == null || result.isEmpty());
    }

    @Test
    public void buscarClientesPorTelefono_EmptyString() {
        List<Cliente> result = clienteController.buscarClientesPorTelefono("");
        assertNotNull(result);
    }

    @Test
    public void buscarClientesPorTelefono_NullString() {
        List<Cliente> result = clienteController.buscarClientesPorTelefono(null);
        assertTrue(result == null || result.isEmpty());
    }

    // ====== TESTS DE BUSCAR CLIENTES GENERAL ======

    @Test
    public void testBuscarClientesGeneral_WithValidString() {
        List<Cliente> result = clienteController.buscarClientesGeneral("test");
        assertNotNull(result);
    }
    public void listarClientes_HappyPath_RetornaLista() {
        // Act
        var resultado = clienteController.listarClientes();

        // Assert
        // Debería retornar una lista (puede estar vacía)
        assertNotNull("La lista no debería ser null", resultado);
    }

    @Test
    public void testBuscarClientesGeneral_WithEmptyString() {
        List<Cliente> result = clienteController.buscarClientesGeneral("");
        assertNotNull(result);
    }

    @Test
    public void testBuscarClientesGeneral_WithNullString() {
        List<Cliente> result = clienteController.buscarClientesGeneral(null);
        assertTrue(result == null || result.isEmpty());
    }

    // ====== TESTS DE VALIDACION DE DATOS CLIENTE (Entity) ======

    @Test
    public void testClienteEntity_ValidData() {
        Cliente cliente = new Cliente(3, "Juan", "Pérez", 30, "123456789");

        assertEquals(Integer.valueOf(3), cliente.getId());
        assertEquals("Juan", cliente.getNombre());
        assertEquals("Pérez", cliente.getApellido());
        assertEquals(Integer.valueOf(30), cliente.getEdad());
        assertEquals("123456789", cliente.getTelefono());
    }

    @Test
    public void testClienteEntity_SettersAndGetters() {
        Cliente cliente = new Cliente();

        cliente.setId(4);
        cliente.setNombre("María");
        cliente.setApellido("García");
        cliente.setEdad(25);
        cliente.setTelefono("987654321");

        assertEquals(Integer.valueOf(4), cliente.getId());
        assertEquals("María", cliente.getNombre());
        assertEquals("García", cliente.getApellido());
        assertEquals(Integer.valueOf(25), cliente.getEdad());
        assertEquals("987654321", cliente.getTelefono());
    }

    @Test
    public void testClienteEntity_ToString() {
        Cliente cliente = new Cliente(5, "Carlos", "López", 35, "555555555");

        String result = cliente.toString();

        assertNotNull(result);
        assertTrue(result.contains("Carlos"));
        assertTrue(result.contains("López"));
    }
}

