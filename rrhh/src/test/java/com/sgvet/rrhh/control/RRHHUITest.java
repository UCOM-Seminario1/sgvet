package com.sgvet.rrhh.control;

import com.sgvet.rrhh.boundary.RRHHUI;
import com.sgvet.rrhh.control.RRHHController;
import org.junit.jupiter.api.*;
import org.mockito.Mockito;
import java.io.*;
import java.lang.reflect.Field;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;




public class RRHHUITest {

    private final PrintStream originalOut = System.out;
    private final InputStream originalIn = System.in;
    private ByteArrayOutputStream outContent;
    private RRHHController mockController;

    @BeforeEach
    void setUp() throws Exception {
        outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent, true, StandardCharsets.UTF_8));

        // Mock RRHHController and inject into RRHHUI
        mockController = mock(RRHHController.class);
        Field controllerField = RRHHUI.class.getDeclaredField("rrhhController");
        controllerField.setAccessible(true);
        controllerField.set(null, mockController);
    }

    @AfterEach
    void tearDown() {
        System.setOut(originalOut);
        System.setIn(originalIn);
    }

    @Test
    void testSolicitarVacacionesExitoso() {
        // Simulate user input: id, fechaInicio, fechaFin
        String input = "5\n2024-07-01\n2024-07-10\n";
        System.setIn(new ByteArrayInputStream(input.getBytes(StandardCharsets.UTF_8)));

        when(mockController.solicitarVacaciones(5, "2024-07-01", "2024-07-10")).thenReturn(true);

        Scanner scanner = new Scanner(System.in);
        invokeSolicitarVacaciones(scanner);

        String output = outContent.toString();
        assertTrue(output.contains("Vacaciones solicitadas correctamente."));
        verify(mockController).solicitarVacaciones(5, "2024-07-01", "2024-07-10");
    }

    @Test
    void testSolicitarVacacionesFalla() {
        String input = "7\n2024-08-01\n2024-08-05\n";
        System.setIn(new ByteArrayInputStream(input.getBytes(StandardCharsets.UTF_8)));

        when(mockController.solicitarVacaciones(7, "2024-08-01", "2024-08-05")).thenReturn(false);

        Scanner scanner = new Scanner(System.in);
        invokeSolicitarVacaciones(scanner);

        String output = outContent.toString();
        assertTrue(output.contains("No se pudo solicitar las vacaciones. Verifique el ID."));
        verify(mockController).solicitarVacaciones(7, "2024-08-01", "2024-08-05");
    }

    @Test
    void testSolicitarPermisoExitoso() {
        String input = "3\nMotivo especial\n2024-09-01\n";
        System.setIn(new ByteArrayInputStream(input.getBytes(StandardCharsets.UTF_8)));

        when(mockController.solicitarPermiso(3, "Motivo especial", "2024-09-01")).thenReturn(true);

        Scanner scanner = new Scanner(System.in);
        invokeSolicitarPermiso(scanner);

        String output = outContent.toString();
        assertTrue(output.contains("Permiso solicitado correctamente."));
        verify(mockController).solicitarPermiso(3, "Motivo especial", "2024-09-01");
    }

    @Test
    void testSolicitarPermisoFalla() {
        String input = "4\nNo autorizado\n2024-10-01\n";
        System.setIn(new ByteArrayInputStream(input.getBytes(StandardCharsets.UTF_8)));

        when(mockController.solicitarPermiso(4, "No autorizado", "2024-10-01")).thenReturn(false);

        Scanner scanner = new Scanner(System.in);
        invokeSolicitarPermiso(scanner);

        String output = outContent.toString();
        assertTrue(output.contains("No se pudo solicitar el permiso. Verifique el ID."));
        verify(mockController).solicitarPermiso(4, "No autorizado", "2024-10-01");
    }

    @Test
    void testSolicitarVacacionesInputInvalido() {
        // Input: not an int for ID
        String input = "abc\n";
        System.setIn(new ByteArrayInputStream(input.getBytes(StandardCharsets.UTF_8)));

        Scanner scanner = new Scanner(System.in);
        // Try/catch to avoid NoSuchElementException
        try {
            invokeSolicitarVacaciones(scanner);
        } catch (Exception ignored) {}

        String output = outContent.toString();
        // Should throw InputMismatchException and not call controller
        verify(mockController, never()).solicitarVacaciones(anyInt(), anyString(), anyString());
    }

    // Helpers to invoke private static methods via reflection
    private void invokeSolicitarVacaciones(Scanner scanner) {
        try {
            var m = RRHHUI.class.getDeclaredMethod("solicitarVacaciones", Scanner.class);
            m.setAccessible(true);
            m.invoke(null, scanner);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void invokeSolicitarPermiso(Scanner scanner) {
        try {
            var m = RRHHUI.class.getDeclaredMethod("solicitarPermiso", Scanner.class);
            m.setAccessible(true);
            m.invoke(null, scanner);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}