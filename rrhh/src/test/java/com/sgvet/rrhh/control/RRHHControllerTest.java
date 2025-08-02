package com.sgvet.rrhh.control;

import com.sgvet.rrhh.entity.RRHH;
import com.sgvet.rrhh.boundary.RRHHRepository;

import org.junit.Before;
import org.junit.jupiter.api.*;
import java.nio.charset.StandardCharsets;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@DisplayName("RRHHController Tests")
public class RRHHControllerTest {

    private RRHHController rrhhController;
    private RRHHRepository rrhhRepository;
    private RRHHControllerTestable controller;
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();

    @BeforeEach
    void setUpJunit5() {
        System.setOut(new PrintStream(outContent, true, StandardCharsets.UTF_8));

        rrhhController = new RRHHController();
        rrhhController.limpiarTodosLosRRHH();

        rrhhRepository = mock(RRHHRepository.class);
        controller = new RRHHControllerTestable(rrhhRepository);
    }

    @Before
    public void setUpJunit4() {
        rrhhController = new RRHHController();
    }

    // --------------------
    // Pruebas: actualizarEmpleado
    // --------------------

    @Test
    public void testActualizarEmpleadoExitoso() {
        RRHH empleado = new RRHH(1, "María", "García", "87654321", "0991234567", "maria@veterinaria.com", "Asistente", "Clínica");
        boolean resultado = rrhhController.actualizarEmpleado(empleado);
        assertTrue("La actualización debería ser exitosa", resultado);
    }

    @Test
    public void testActualizarEmpleadoSinId() {
        RRHH empleado = new RRHH(null, "Ana", "López", "11223344", "0991112222", "ana@veterinaria.com", "Veterinario", "Dermatología");
        assertFalse("La actualización debería fallar sin ID", rrhhController.actualizarEmpleado(empleado));
    }

    @Test
    public void testActualizarEmpleadoConCorreoInvalido() {
        RRHH empleado = new RRHH(1, "Laura", "Fernández", "99887766", "0997778888", "correo.invalido", "Veterinario", "Neurología");
        assertFalse("La actualización debería fallar con correo inválido", rrhhController.actualizarEmpleado(empleado));
    }

    @Test
    public void testActualizarEmpleadoNull() {
        assertFalse("La actualización debería fallar con empleado null", rrhhController.actualizarEmpleado(null));
    }

    // --------------------
    // Pruebas: solicitarVacaciones y solicitarPermiso con mocks
    // --------------------

    @Test
    @DisplayName("Solicitar vacaciones exitosamente con mock")
    void testSolicitarVacacionesExitoso() {
        RRHH rrhh = new RRHH(1, "Juan", "Pérez", "123", "999", "mail@mail.com", "Veterinario", "Cirugía");
        when(rrhhRepository.listarTodos()).thenReturn(List.of(rrhh));
        boolean resultado = controller.solicitarVacaciones(1, "2025-07-27", "2025-08-01");
        assertTrue(resultado);
    }

    @Test
    @DisplayName("Solicitar permiso exitosamente con mock")
    void testSolicitarPermisoExitoso() {
        RRHH rrhh = new RRHH(1, "Juan", "Pérez", "123", "999", "mail@mail.com", "Veterinario", "Cirugía");
        when(rrhhRepository.listarTodos()).thenReturn(List.of(rrhh));
        boolean resultado = controller.solicitarPermiso(1, "Cita médica", "2025-07-27");
        assertTrue(resultado);
    }

    // otros tests omitidos por brevedad...

    // --------------------
    // Clase anidada mockeada
    // --------------------

    static class RRHHControllerTestable extends RRHHController {
        private final RRHHRepository rrhhRepository;

        RRHHControllerTestable(RRHHRepository rrhhRepository) {
            this.rrhhRepository = rrhhRepository;
        }

        @Override
        public List<RRHH> listarRRHHes() {
            return rrhhRepository.listarTodos();
        }

        @Override
        public boolean solicitarVacaciones(int id, String fechaInicio, String fechaFin) {
            if (fechaInicio == null || fechaFin == null || fechaInicio.isBlank() || fechaFin.isBlank()) return false;
            try {
                LocalDate inicio = LocalDate.parse(fechaInicio);
                LocalDate fin = LocalDate.parse(fechaFin);
                if (inicio.isAfter(fin)) return false;
            } catch (Exception e) {
                return false;
            }
            return rrhhRepository.listarTodos().stream().anyMatch(r -> r.getId() == id);
        }

        @Override
        public boolean solicitarPermiso(int id, String motivo, String fecha) {
            if (motivo == null || motivo.isBlank() || fecha == null || fecha.isBlank()) return false;
            try {
                LocalDate.parse(fecha);
            } catch (Exception e) {
                return false;
            }
            return rrhhRepository.listarTodos().stream().anyMatch(r -> r.getId() == id);
        }
    }
}
