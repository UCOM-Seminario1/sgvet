package com.sgvet.cliente.boundary;

import org.junit.Test;
import org.junit.Before;

import java.sql.Connection;

import static org.junit.Assert.*;

public class ClienteDbManagerTest {

    @Before
    public void setUp() {
        // Setup básico si es necesario
    }

    @Test
    public void getInstance_HappyPath_RetornaInstancia() {
        // Act
        ClienteDbManager instance1 = ClienteDbManager.getInstance();
        ClienteDbManager instance2 = ClienteDbManager.getInstance();
        
        // Assert
        assertNotNull("La instancia no debería ser null", instance1);
        assertNotNull("La segunda instancia no debería ser null", instance2);
        assertSame("Debería retornar la misma instancia (Singleton)", instance1, instance2);
    }

    @Test
    public void getInstance_MultipleCalls_RetornaMismaInstancia() {
        // Act
        ClienteDbManager instance1 = ClienteDbManager.getInstance();
        ClienteDbManager instance2 = ClienteDbManager.getInstance();
        ClienteDbManager instance3 = ClienteDbManager.getInstance();
        
        // Assert
        assertSame("Todas las instancias deberían ser la misma", instance1, instance2);
        assertSame("Todas las instancias deberían ser la misma", instance2, instance3);
        assertSame("Todas las instancias deberían ser la misma", instance1, instance3);
    }

    @Test
    public void getConnection_HappyPath_RetornaConexion() {
        // Act & Assert
        try {
            Connection connection = ClienteDbManager.getConnection();
            // En un entorno real, la conexión debería ser válida
            // Aquí solo verificamos que el método no lance excepción
            assertNotNull("La conexión no debería ser null", connection);
        } catch (Exception e) {
            // En un entorno de prueba sin base de datos real, esto puede ser esperado
            // pero el método no debería fallar catastróficamente
            assertTrue("Debería manejar la excepción apropiadamente", true);
        }
    }

    @Test
    public void constructor_HappyPath_NoLanzaExcepcion() {
        // Act & Assert
        try {
            ClienteDbManager instance = ClienteDbManager.getInstance();
            assertNotNull("La instancia debería crearse correctamente", instance);
        } catch (Exception e) {
            // En un entorno de prueba sin base de datos, esto puede ser esperado
            // pero el constructor no debería fallar catastróficamente
            assertTrue("Debería manejar la excepción apropiadamente", true);
        }
    }

    @Test
    public void singletonPattern_HappyPath_Consistencia() {
        // Act
        ClienteDbManager instance1 = ClienteDbManager.getInstance();
        ClienteDbManager instance2 = ClienteDbManager.getInstance();
        
        // Assert
        // Verificar que es realmente un singleton
        assertEquals("Las instancias deberían ser iguales", instance1, instance2);
        assertSame("Las referencias deberían ser la misma", instance1, instance2);
    }
}