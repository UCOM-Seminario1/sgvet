package com.sgvet.cliente.control;

import com.sgvet.cliente.boundary.ClienteRepository;
import com.sgvet.cliente.entity.Cliente;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import java.util.List;

public class ClienteControllerTest {

    private ClienteController clienteController;
    private ClienteRepository clienteRepository;

    @Before
    public void setUp() {
        clienteController = new ClienteController();
        clienteRepository = new ClienteRepository();
    }

    @Test
    public void testEliminarClienteExitoso() {
        // Arrange - Crear un cliente de prueba
        Cliente cliente = new Cliente(999, "Test", "Eliminar", 30, "123456789");
        
        // Insertar el cliente primero
        clienteRepository.insertar(cliente);
        
        // Verificar que el cliente existe antes de eliminarlo
        Cliente clienteAntes = clienteRepository.buscarPorId(999);
        assertNotNull("El cliente debería existir antes de eliminarlo", clienteAntes);
        
        // Act - Eliminar el cliente
        Boolean resultado = clienteController.eliminarCliente(999);
        
        // Assert - Verificar que la eliminación fue exitosa
        assertTrue("La eliminación debería ser exitosa", resultado);
        
        // Verificar que el cliente ya no existe
        Cliente clienteDespues = clienteRepository.buscarPorId(999);
        assertNull("El cliente debería ser null después de eliminarlo", clienteDespues);
    }

    @Test
    public void testEliminarClienteInexistente() {
        // Arrange - ID de un cliente que no existe
        Integer idInexistente = 99999;
        
        // Verificar que el cliente no existe
        Cliente clienteAntes = clienteRepository.buscarPorId(idInexistente);
        assertNull("El cliente no debería existir", clienteAntes);
        
        // Act - Intentar eliminar el cliente inexistente
        Boolean resultado = clienteController.eliminarCliente(idInexistente);
        
        // Assert - Verificar que retorna false
        assertFalse("Debería retornar false cuando el cliente no existe", resultado);
    }

    @Test
    public void testEliminarClienteConIdNull() {
        // Act - Intentar eliminar con ID null
        Boolean resultado = clienteController.eliminarCliente(null);
        
        // Assert - Verificar que maneja el null correctamente
        assertFalse("Debería retornar false cuando el ID es null", resultado);
    }

    @Test
    public void testCrearCliente() {
        // Arrange
        Cliente cliente = new Cliente(888, "Test", "Crear", 25, "987654321");
        
        // Act
        Boolean resultado = clienteController.crearCliente(cliente);
        
        // Assert
        assertTrue("La creación del cliente debería ser exitosa", resultado);
        
        // Verificar que el cliente fue creado
        Cliente clienteCreado = clienteRepository.buscarPorId(888);
        assertNotNull("El cliente debería existir después de crearlo", clienteCreado);
        assertEquals("Test", clienteCreado.getNombre());
        assertEquals("Crear", clienteCreado.getApellido());
        
        // Limpiar - eliminar el cliente de prueba
        clienteRepository.eliminarCliente(888);
    }

    @Test
    public void testListarClientes() {
        // Act
        List<Cliente> clientes = clienteController.listarClientes();
        
        // Assert
        assertNotNull("La lista de clientes no debería ser null", clientes);
        assertTrue("Debería retornar una lista", clientes instanceof List);
    }

    @Test
    public void testBuscarClientePorId() {
        // Arrange - Crear un cliente de prueba
        Cliente cliente = new Cliente(777, "Test", "Buscar", 35, "555555555");
        clienteRepository.insertar(cliente);
        
        // Act
        Cliente clienteEncontrado = clienteController.buscarClientePorId(777);
        
        // Assert
        assertNotNull("Debería encontrar el cliente", clienteEncontrado);
        assertEquals("Test", clienteEncontrado.getNombre());
        assertEquals("Buscar", clienteEncontrado.getApellido());
        
        // Limpiar
        clienteRepository.eliminarCliente(777);
    }

    @Test
    public void testModificarCliente() {
        // Arrange - Crear un cliente de prueba
        Cliente clienteOriginal = new Cliente(666, "Original", "Nombre", 40, "111111111");
        clienteRepository.insertar(clienteOriginal);
        
        // Modificar el cliente
        Cliente clienteModificado = new Cliente(666, "Modificado", "Apellido", 45, "222222222");
        
        // Act
        Boolean resultado = clienteController.modificarCliente(clienteModificado);
        
        // Assert
        assertTrue("La modificación debería ser exitosa", resultado);
        
        // Verificar que los cambios se aplicaron
        Cliente clienteVerificado = clienteRepository.buscarPorId(666);
        assertNotNull("El cliente debería existir después de modificarlo", clienteVerificado);
        assertEquals("Modificado", clienteVerificado.getNombre());
        assertEquals("Apellido", clienteVerificado.getApellido());
        assertEquals(Integer.valueOf(45), clienteVerificado.getEdad());
        assertEquals("222222222", clienteVerificado.getTelefono());
        
        // Limpiar
        clienteRepository.eliminarCliente(666);
    }
}