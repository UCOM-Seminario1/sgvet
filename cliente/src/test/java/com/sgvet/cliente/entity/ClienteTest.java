package com.sgvet.cliente.entity;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class ClienteTest {

    private Cliente clienteValido;
    private Cliente clienteInvalido;

    @Before
    public void setUp() {
        clienteValido = new Cliente(1, "Juan", "Pérez", 30, "123456789");
        clienteInvalido = new Cliente(null, null, null, null, null);
    }

    // ========== HAPPY PATH TESTS ==========

    @Test
    public void testConstructor_HappyPath_ClienteValido() {
        // Arrange & Act
        Cliente cliente = new Cliente(1, "María", "García", 25, "987654321");

        // Assert
        assertNotNull("El cliente no debería ser null", cliente);
        assertEquals("El ID debería ser 1", Integer.valueOf(1), cliente.getId());
        assertEquals("El nombre debería ser María", "María", cliente.getNombre());
        assertEquals("El apellido debería ser García", "García", cliente.getApellido());
        assertEquals("La edad debería ser 25", Integer.valueOf(25), cliente.getEdad());
        assertEquals("El teléfono debería ser 987654321", "987654321", cliente.getTelefono());
    }

    @Test
    public void testConstructor_HappyPath_ClienteConEdadMinima() {
        // Arrange & Act
        Cliente cliente = new Cliente(2, "Ana", "López", 18, "555123456");

        // Assert
        assertEquals("La edad mínima debería ser 18", Integer.valueOf(18), cliente.getEdad());
    }

    @Test
    public void testConstructor_HappyPath_ClienteConEdadMaxima() {
        // Arrange & Act
        Cliente cliente = new Cliente(3, "Carlos", "Rodríguez", 100, "777888999");

        // Assert
        assertEquals("La edad máxima debería ser 100", Integer.valueOf(100), cliente.getEdad());
    }

    @Test
    public void testConstructor_HappyPath_ClienteConIdMaximo() {
        // Arrange & Act
        Cliente cliente = new Cliente(Integer.MAX_VALUE, "Test", "Test", 30, "123456789");

        // Assert
        assertEquals("El ID debería ser Integer.MAX_VALUE", Integer.valueOf(Integer.MAX_VALUE), cliente.getId());
    }

    @Test
    public void testConstructor_HappyPath_ClienteConNombreLargo() {
        // Arrange
        String nombreLargo = "A".repeat(1000);
        
        // Act
        Cliente cliente = new Cliente(4, nombreLargo, "Test", 30, "123456789");

        // Assert
        assertEquals("El nombre largo debería ser correcto", nombreLargo, cliente.getNombre());
    }

    @Test
    public void testConstructor_HappyPath_ClienteConTelefonoLargo() {
        // Arrange
        String telefonoLargo = "1".repeat(50);
        
        // Act
        Cliente cliente = new Cliente(5, "Test", "Test", 30, telefonoLargo);

        // Assert
        assertEquals("El teléfono largo debería ser correcto", telefonoLargo, cliente.getTelefono());
    }

    @Test
    public void testConstructor_HappyPath_ClienteConDatosNulos() {
        // Arrange & Act
        Cliente cliente = new Cliente(null, null, null, null, null);

        // Assert
        assertNull("El ID debería ser null", cliente.getId());
        assertNull("El nombre debería ser null", cliente.getNombre());
        assertNull("El apellido debería ser null", cliente.getApellido());
        assertNull("La edad debería ser null", cliente.getEdad());
        assertNull("El teléfono debería ser null", cliente.getTelefono());
    }

    @Test
    public void testConstructor_HappyPath_ClienteVacio() {
        // Arrange & Act
        Cliente cliente = new Cliente();

        // Assert
        assertNull("El ID debería ser null", cliente.getId());
        assertNull("El nombre debería ser null", cliente.getNombre());
        assertNull("El apellido debería ser null", cliente.getApellido());
        assertNull("La edad debería ser null", cliente.getEdad());
        assertNull("El teléfono debería ser null", cliente.getTelefono());
    }

    // ========== SETTER TESTS ==========

    @Test
    public void testSetters_HappyPath_ValoresValidos() {
        // Arrange
        Cliente cliente = new Cliente();

        // Act
        cliente.setId(10);
        cliente.setNombre("Pedro");
        cliente.setApellido("Gómez");
        cliente.setEdad(35);
        cliente.setTelefono("111222333");

        // Assert
        assertEquals("El ID debería ser 10", Integer.valueOf(10), cliente.getId());
        assertEquals("El nombre debería ser Pedro", "Pedro", cliente.getNombre());
        assertEquals("El apellido debería ser Gómez", "Gómez", cliente.getApellido());
        assertEquals("La edad debería ser 35", Integer.valueOf(35), cliente.getEdad());
        assertEquals("El teléfono debería ser 111222333", "111222333", cliente.getTelefono());
    }

    @Test
    public void testSetters_HappyPath_ValoresNulos() {
        // Arrange
        Cliente cliente = new Cliente(1, "Test", "Test", 30, "123456789");

        // Act
        cliente.setId(null);
        cliente.setNombre(null);
        cliente.setApellido(null);
        cliente.setEdad(null);
        cliente.setTelefono(null);

        // Assert
        assertNull("El ID debería ser null", cliente.getId());
        assertNull("El nombre debería ser null", cliente.getNombre());
        assertNull("El apellido debería ser null", cliente.getApellido());
        assertNull("La edad debería ser null", cliente.getEdad());
        assertNull("El teléfono debería ser null", cliente.getTelefono());
    }

    @Test
    public void testSetters_HappyPath_ValoresExtremos() {
        // Arrange
        Cliente cliente = new Cliente();

        // Act
        cliente.setId(Integer.MAX_VALUE);
        cliente.setId(Integer.MIN_VALUE);
        cliente.setEdad(0);
        cliente.setEdad(150);

        // Assert
        assertEquals("El ID debería ser Integer.MIN_VALUE", Integer.valueOf(Integer.MIN_VALUE), cliente.getId());
        assertEquals("La edad debería ser 150", Integer.valueOf(150), cliente.getEdad());
    }

    // ========== GETTER TESTS ==========

    @Test
    public void testGetters_HappyPath_ValoresCorrectos() {
        // Arrange
        Cliente cliente = new Cliente(1, "Juan", "Pérez", 30, "123456789");

        // Act & Assert
        assertEquals("El getter del ID debería retornar 1", Integer.valueOf(1), cliente.getId());
        assertEquals("El getter del nombre debería retornar Juan", "Juan", cliente.getNombre());
        assertEquals("El getter del apellido debería retornar Pérez", "Pérez", cliente.getApellido());
        assertEquals("El getter de la edad debería retornar 30", Integer.valueOf(30), cliente.getEdad());
        assertEquals("El getter del teléfono debería retornar 123456789", "123456789", cliente.getTelefono());
    }

    @Test
    public void testGetters_HappyPath_ValoresNulos() {
        // Arrange
        Cliente cliente = new Cliente(null, null, null, null, null);

        // Act & Assert
        assertNull("El getter del ID debería retornar null", cliente.getId());
        assertNull("El getter del nombre debería retornar null", cliente.getNombre());
        assertNull("El getter del apellido debería retornar null", cliente.getApellido());
        assertNull("El getter de la edad debería retornar null", cliente.getEdad());
        assertNull("El getter del teléfono debería retornar null", cliente.getTelefono());
    }

    // ========== TO STRING TESTS ==========

    @Test
    public void testToString_HappyPath_ClienteCompleto() {
        // Arrange
        Cliente cliente = new Cliente(1, "Juan", "Pérez", 30, "123456789");

        // Act
        String resultado = cliente.toString();

        // Assert
        assertNotNull("El toString no debería ser null", resultado);
        assertTrue("El toString debería contener el ID", resultado.contains("id=1"));
        assertTrue("El toString debería contener el nombre", resultado.contains("nombre='Juan'"));
        assertTrue("El toString debería contener el apellido", resultado.contains("apellido='Pérez'"));
        assertTrue("El toString debería contener la edad", resultado.contains("edad=30"));
        assertTrue("El toString debería contener el teléfono", resultado.contains("telefono='123456789'"));
    }

    @Test
    public void testToString_HappyPath_ClienteConDatosNulos() {
        // Arrange
        Cliente cliente = new Cliente(null, null, null, null, null);

        // Act
        String resultado = cliente.toString();

        // Assert
        assertNotNull("El toString no debería ser null", resultado);
        assertTrue("El toString debería contener null para el ID", resultado.contains("id=null"));
        assertTrue("El toString debería contener null para el nombre", resultado.contains("nombre='null'"));
        assertTrue("El toString debería contener null para el apellido", resultado.contains("apellido='null'"));
        assertTrue("El toString debería contener null para la edad", resultado.contains("edad=null"));
        assertTrue("El toString debería contener null para el teléfono", resultado.contains("telefono='null'"));
    }

    @Test
    public void testToString_HappyPath_ClienteVacio() {
        // Arrange
        Cliente cliente = new Cliente();

        // Act
        String resultado = cliente.toString();

        // Assert
        assertNotNull("El toString no debería ser null", resultado);
        assertTrue("El toString debería contener null para el ID", resultado.contains("id=null"));
        assertTrue("El toString debería contener null para el nombre", resultado.contains("nombre='null'"));
        assertTrue("El toString debería contener null para el apellido", resultado.contains("apellido='null'"));
        assertTrue("El toString debería contener null para la edad", resultado.contains("edad=null"));
        assertTrue("El toString debería contener null para el teléfono", resultado.contains("telefono='null'"));
    }

    // ========== EDGE CASES ==========

    @Test
    public void testConstructor_EdgeCase_ClienteConEdadCero() {
        // Arrange & Act
        Cliente cliente = new Cliente(1, "Test", "Test", 0, "123456789");

        // Assert
        assertEquals("La edad debería ser 0", Integer.valueOf(0), cliente.getEdad());
    }

    @Test
    public void testConstructor_EdgeCase_ClienteConIdCero() {
        // Arrange & Act
        Cliente cliente = new Cliente(0, "Test", "Test", 30, "123456789");

        // Assert
        assertEquals("El ID debería ser 0", Integer.valueOf(0), cliente.getId());
    }

    @Test
    public void testConstructor_EdgeCase_ClienteConNombreVacio() {
        // Arrange & Act
        Cliente cliente = new Cliente(1, "", "Test", 30, "123456789");

        // Assert
        assertEquals("El nombre debería ser vacío", "", cliente.getNombre());
    }

    @Test
    public void testConstructor_EdgeCase_ClienteConTelefonoVacio() {
        // Arrange & Act
        Cliente cliente = new Cliente(1, "Test", "Test", 30, "");

        // Assert
        assertEquals("El teléfono debería ser vacío", "", cliente.getTelefono());
    }

    @Test
    public void testConstructor_EdgeCase_ClienteConApellidoVacio() {
        // Arrange & Act
        Cliente cliente = new Cliente(1, "Test", "", 30, "123456789");

        // Assert
        assertEquals("El apellido debería ser vacío", "", cliente.getApellido());
    }

    @Test
    public void testSetters_EdgeCase_ValoresNegativos() {
        // Arrange
        Cliente cliente = new Cliente();

        // Act
        cliente.setId(-1);
        cliente.setEdad(-5);

        // Assert
        assertEquals("El ID debería ser -1", Integer.valueOf(-1), cliente.getId());
        assertEquals("La edad debería ser -5", Integer.valueOf(-5), cliente.getEdad());
    }

    @Test
    public void testSetters_EdgeCase_ValoresExtremos() {
        // Arrange
        Cliente cliente = new Cliente();

        // Act
        cliente.setId(Integer.MAX_VALUE);
        cliente.setId(Integer.MIN_VALUE);
        cliente.setEdad(Integer.MAX_VALUE);
        cliente.setEdad(Integer.MIN_VALUE);

        // Assert
        assertEquals("El ID debería ser Integer.MIN_VALUE", Integer.valueOf(Integer.MIN_VALUE), cliente.getId());
        assertEquals("La edad debería ser Integer.MIN_VALUE", Integer.valueOf(Integer.MIN_VALUE), cliente.getEdad());
    }

    @Test
    public void testToString_EdgeCase_ClienteConCaracteresEspeciales() {
        // Arrange
        Cliente cliente = new Cliente(1, "José", "O'Connor", 30, "123-456-789");

        // Act
        String resultado = cliente.toString();

        // Assert
        assertNotNull("El toString no debería ser null", resultado);
        assertTrue("El toString debería contener caracteres especiales", resultado.contains("José"));
        assertTrue("El toString debería contener apóstrofe", resultado.contains("O'Connor"));
        assertTrue("El toString debería contener guiones en teléfono", resultado.contains("123-456-789"));
    }
} 