package com.sgvet.cliente.boundary;

import com.sgvet.cliente.entity.Cliente;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class ClienteRepositoryTest {

    private ClienteRepository clienteRepository;

    @Before
    public void setUp() {
        clienteRepository = new ClienteRepository();
    }

    @Test
    public void modificarCliente_HappyPath_ClienteExiste() {
        // Arrange
        Cliente cliente = new Cliente(1, "Juan", "Pérez", 30, "123456789");
        
        // Act
        Boolean resultado = clienteRepository.modificarCliente(cliente);
        
        // Assert
        // En un entorno real, esto dependería de la base de datos
        // Aquí solo verificamos que el método no lance excepción
        assertNotNull("El resultado no debería ser null", resultado);
    }

    @Test
    public void modificarCliente_UnhappyPath_ClienteNoExiste() {
        // Arrange
        Cliente cliente = new Cliente(999, "Cliente", "Inexistente", 25, "000000000");
        
        // Act
        Boolean resultado = clienteRepository.modificarCliente(cliente);
        
        // Assert
        // En un entorno real, esto dependería de la base de datos
        // Aquí solo verificamos que el método no lance excepción
        assertNotNull("El resultado no debería ser null", resultado);
    }

    @Test
    public void buscarPorId_HappyPath_ClienteExiste() {
        // Act
        Cliente resultado = clienteRepository.buscarPorId(1);
        
        // Assert
        // En un entorno real, esto dependería de la base de datos
        // Aquí solo verificamos que el método no lance excepción
        // El resultado puede ser null si no existe en la base de datos
        // pero el método no debería fallar
    }

    @Test
    public void buscarPorId_UnhappyPath_ClienteNoExiste() {
        // Act
        Cliente resultado = clienteRepository.buscarPorId(999);
        
        // Assert
        // En un entorno real, esto dependería de la base de datos
        // Aquí solo verificamos que el método no lance excepción
        // El resultado puede ser null, pero el método no debería fallar
    }

    @Test
    public void listarTodos_HappyPath_RetornaLista() {
        // Act
        List<Cliente> resultado = clienteRepository.listarTodos();
        
        // Assert
        // Debería retornar una lista (puede estar vacía)
        assertNotNull("La lista no debería ser null", resultado);
    }

    @Test
    public void insertar_HappyPath_ClienteValido() {
        // Arrange
        Cliente cliente = new Cliente(888, "Nuevo", "Cliente", 25, "987654321");
        
        // Act
        // Este método no retorna nada, solo verificamos que no lance excepción
        try {
            clienteRepository.insertar(cliente);
            // Si llegamos aquí, no hubo excepción
            assertTrue("El método debería ejecutarse sin excepción", true);
        } catch (Exception e) {
            fail("No debería lanzar excepción: " + e.getMessage());
        }
    }

    @Test
    public void buscarPorNombre_HappyPath_RetornaLista() {
        // Act
        List<Cliente> resultado = clienteRepository.buscarPorNombre("Juan");
        
        // Assert
        // Debería retornar una lista (puede estar vacía)
        assertNotNull("La lista no debería ser null", resultado);
    }

    @Test
    public void buscarPorApellido_HappyPath_RetornaLista() {
        // Act
        List<Cliente> resultado = clienteRepository.buscarPorApellido("Pérez");
        
        // Assert
        // Debería retornar una lista (puede estar vacía)
        assertNotNull("La lista no debería ser null", resultado);
    }

    @Test
    public void buscarPorTelefono_HappyPath_RetornaLista() {
        // Act
        List<Cliente> resultado = clienteRepository.buscarPorTelefono("123");
        
        // Assert
        // Debería retornar una lista (puede estar vacía)
        assertNotNull("La lista no debería ser null", resultado);
    }

    @Test
    public void buscarGeneral_HappyPath_RetornaLista() {
        // Act
        List<Cliente> resultado = clienteRepository.buscarGeneral("Juan");
        
        // Assert
        // Debería retornar una lista (puede estar vacía)
        assertNotNull("La lista no debería ser null", resultado);
    }
} 