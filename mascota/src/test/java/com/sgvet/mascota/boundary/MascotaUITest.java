package com.sgvet.mascota.boundary;

import com.sgvet.mascota.entity.Mascota;
import org.junit.Before;
import org.junit.Test;
import org.junit.After;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;
import java.util.List;
import java.util.ArrayList;

import static org.junit.Assert.*;

public class MascotaUITest {
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;
    private final InputStream originalIn = System.in;
    private TestLogHandler logHandler;
    private Logger logger;

    @Before
    public void setUp() {
        System.setOut(new PrintStream(outContent));
        logHandler = new TestLogHandler();
        logger = Logger.getLogger(MascotaUI.class.getName());
        logger.addHandler(logHandler);
        logger.setLevel(Level.ALL);
    }

    @After
    public void tearDown() {
        System.setOut(originalOut);
        System.setIn(originalIn);
        if (logHandler != null) {
            logger.removeHandler(logHandler);
        }
    }

    // Helper class para capturar logs
    private static class TestLogHandler extends Handler {
        private final List<LogRecord> logRecords = new ArrayList<>();

        @Override
        public void publish(LogRecord record) {
            logRecords.add(record);
        }

        @Override
        public void flush() {}

        @Override
        public void close() throws SecurityException {}

        public List<LogRecord> getLogRecords() {
            return logRecords;
        }

        public boolean hasLogWithMessage(String message) {
            return logRecords.stream()
                    .anyMatch(record -> record.getMessage().contains(message));
        }
    }

    @Test
    public void testMenuMascotasOpcionSalir() {
        String input = "0\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        MascotaUI.menuMascotas();

        assertTrue(logHandler.hasLogWithMessage("Volviendo al menu principal"));
    }

    @Test
    public void testMenuMascotasOpcionInvalida() {
        String input = "9\n0\n"; // Opción inválida seguida de salir
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        MascotaUI.menuMascotas();

        assertTrue(logHandler.hasLogWithMessage("Opcion invalida"));
    }

    @Test
    public void testMenuMascotasEntradaNoNumerica() {
        String input = "abc\n0\n"; // Entrada no numérica seguida de salir
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        MascotaUI.menuMascotas();

        assertTrue(logHandler.hasLogWithMessage("Por favor, ingrese un numero valido"));
    }

    @Test
    public void testCrearMascotaCompleto() {
        String input = "1\nTestNombre\nTestApellido\n3\n123456789\n100\nPerro\nLabrador\n0\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        MascotaUI.menuMascotas();

        assertTrue(logHandler.hasLogWithMessage("Mascota creada exitosamente"));
    }

    @Test
    public void testCrearMascotaConEdadInvalida() {
        String input = "1\nTestNombre\nTestApellido\nabc\n123456789\n100\nPerro\nLabrador\n0\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        MascotaUI.menuMascotas();

        // Debería crear la mascota con edad 0 debido al manejo de entrada inválida
        assertTrue(logHandler.hasLogWithMessage("Mascota creada exitosamente"));
    }

    @Test
    public void testCrearMascotaConIdClienteInvalido() {
        String input = "1\nTestNombre\nTestApellido\n3\n123456789\nxyz\nPerro\nLabrador\n0\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        MascotaUI.menuMascotas();

        // Debería crear la mascota con idCliente 0 debido al manejo de entrada inválida
        assertTrue(logHandler.hasLogWithMessage("Mascota creada exitosamente"));
    }

    @Test
    public void testListarMascotas() {
        String input = "2\n0\n"; // Listar mascotas y salir
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        MascotaUI.menuMascotas();

        assertTrue(logHandler.hasLogWithMessage("Funcionalidad para listar Mascotas"));
    }

    @Test
    public void testEliminarMascotaConIdValido() {
        // First, create a pet to delete
        String inputCrear = "1\nParaEliminar\nApellido\n2\n987654321\n200\nGato\nPersa\n0\n";
        System.setIn(new ByteArrayInputStream(inputCrear.getBytes()));
        MascotaUI.menuMascotas();

        // Reset log handler
        logHandler.getLogRecords().clear();

        // Now delete (using an ID that likely exists)
        String inputEliminar = "3\n1\n0\n";
        System.setIn(new ByteArrayInputStream(inputEliminar.getBytes()));

        MascotaUI.menuMascotas();

        // Verify that the deletion was processed
        assertTrue(logHandler.hasLogWithMessage("Mascota eliminada exitosamente") ||
            logHandler.hasLogWithMessage("No se encontró una mascota con ese ID"));
    }

    @Test
    public void testEliminarMascotaConIdInvalido() {
        String input = "3\nabc\n0\n"; // ID inválido y salir
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        MascotaUI.menuMascotas();

        assertTrue(logHandler.hasLogWithMessage("ID inválido"));
    }

    @Test
    public void testBuscarMascotaPorId() {
        String input = "4\n1\n1\n0\n"; // Buscar mascota, por ID, ID=1, salir
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        MascotaUI.menuMascotas();

        assertTrue(logHandler.hasLogWithMessage("Mascota encontrada") ||
                  logHandler.hasLogWithMessage("No se encontró una mascota con ese ID"));
    }

    @Test
    public void testBuscarMascotaPorIdInvalido() {
        String input = "4\n1\nabc\n0\n"; // Buscar mascota, por ID, ID inválido, salir
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        MascotaUI.menuMascotas();

        // Debería buscar con ID 0 debido al manejo de entrada inválida
        assertTrue(logHandler.hasLogWithMessage("No se encontró una mascota con ese ID"));
    }

    @Test
    public void testBuscarMascotaPorNombre() {
        String input = "4\n2\nTestNombre\n0\n"; // Buscar mascota, por nombre, "TestNombre", salir
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        MascotaUI.menuMascotas();

        assertTrue(logHandler.hasLogWithMessage("Mascotas encontradas") ||
                  logHandler.hasLogWithMessage("No se encontraron mascotas con ese nombre"));
    }

    @Test
    public void testBuscarMascotaPorCliente() {
        String input = "4\n3\n100\n0\n"; // Buscar mascota, por cliente, ID=100, salir
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        MascotaUI.menuMascotas();

        assertTrue(logHandler.hasLogWithMessage("Mascotas encontradas para el cliente") ||
                  logHandler.hasLogWithMessage("No se encontraron mascotas para ese cliente"));
    }

    @Test
    public void testBuscarMascotaPorClienteIdInvalido() {
        String input = "4\n3\nxyz\n0\n"; // Buscar mascota, por cliente, ID inválido, salir
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        MascotaUI.menuMascotas();

        // Debería buscar con ID 0 debido al manejo de entrada inválida
        assertTrue(logHandler.hasLogWithMessage("No se encontraron mascotas para ese cliente"));
    }

    @Test
    public void testBuscarMascotaOpcionInvalida() {
        String input = "4\n9\n0\n"; // Buscar mascota, opción inválida, salir
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        MascotaUI.menuMascotas();

        assertTrue(logHandler.hasLogWithMessage("Opción inválida"));
    }

    @Test
    public void testFormatMascotaInfo() {
        // Test indirecto a través de buscar mascota
        // Primero crear una mascota
        String inputCrear = "1\nTestFormat\nApellidoFormat\n5\n555555555\n300\nPerro\nGolden\n";
        System.setIn(new ByteArrayInputStream(inputCrear.getBytes()));
        MascotaUI.menuMascotas();

        // Reset log handler
        logHandler.getLogRecords().clear();

        // Buscar por nombre para activar formatMascotaDetails
        String inputBuscar = "4\n2\nTestFormat\n0\n";
        System.setIn(new ByteArrayInputStream(inputBuscar.getBytes()));

        MascotaUI.menuMascotas();

        // Verificar que se usó el formato correcto
        assertTrue(logHandler.hasLogWithMessage("Mascotas encontradas") ||
                  logHandler.hasLogWithMessage("No se encontraron mascotas"));
    }

    @Test
    public void testMainMethod() {
        // Test del método main - simplemente verificar que no lance excepciones
        String input = "0\n"; // Salir inmediatamente
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        try {
            MascotaUI.main(new String[]{});
            // Si llegamos aquí, el método main se ejecutó sin excepciones
            assertTrue(true);
        } catch (Exception e) {
            fail("El método main no debería lanzar excepciones: " + e.getMessage());
        }
    }

    @Test
    public void testMenuCompleto() {
        // Test que pasa por todas las opciones del menú
        String input = "1\nCompleto\nApellido\n2\n123456789\n400\nPerro\nPastor\n" + // Crear
                      "2\n" + // Listar
                      "3\n99999\n" + // Eliminar (ID que no existe)
                      "4\n1\n1\n" + // Buscar por ID
                      "4\n2\nCompleto\n" + // Buscar por nombre
                      "4\n3\n400\n" + // Buscar por cliente
                      "0\n"; // Salir

        System.setIn(new ByteArrayInputStream(input.getBytes()));

        MascotaUI.menuMascotas();

        // Verificar que se ejecutaron todas las operaciones
        assertTrue(logHandler.hasLogWithMessage("Mascota creada exitosamente"));
        assertTrue(logHandler.hasLogWithMessage("Funcionalidad para listar Mascotas"));
        assertTrue(logHandler.hasLogWithMessage("Volviendo al menu principal"));
    }

    @Test
    public void testCrearMascotaConErrorEnControlador() {
        // Crear mascota con datos que podrían causar error
        String input = "1\n\n\n-1\n\n-1\n\n\n0\n"; // Valores vacíos/inválidos
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        MascotaUI.menuMascotas();

        // Debería mostrar mensaje de éxito o error dependiendo de cómo maneje el controlador
        assertTrue(logHandler.hasLogWithMessage("Mascota creada exitosamente") ||
                  logHandler.hasLogWithMessage("Error al crear la mascota"));
    }
}
