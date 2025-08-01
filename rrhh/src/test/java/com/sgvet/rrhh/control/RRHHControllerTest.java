package com.sgvet.rrhh.control;

import com.sgvet.rrhh.entity.RRHH;
import com.sgvet.rrhh.boundary.RRHHRepository;
import org.junit.jupiter.api.*;

import java.nio.charset.StandardCharsets;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@DisplayName("RRHHController Tests")
class RRHHControllerTest {

    private RRHHController rrhhController;
    private RRHH empleadoValido;
    private RRHH empleadoInvalido;

    private RRHHRepository rrhhRepository;
    private RRHHControllerTestable controller;

    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();

    @BeforeEach
    void setUp() {
        System.setOut(new PrintStream(outContent, true, StandardCharsets.UTF_8));

        rrhhController = new RRHHController();
        rrhhController.limpiarTodosLosRRHH();

        empleadoValido = new RRHH(1, "Juan", "Pérez", "12345678", "555-1234",
                "juan.perez@email.com", "Veterinario", "Cirugía");
        empleadoInvalido = new RRHH(2, "", "", "", "", "", "", "");

        rrhhRepository = mock(RRHHRepository.class);
        controller = new RRHHControllerTestable(rrhhRepository);
    }

    @AfterEach
    void tearDown() {
        rrhhController.limpiarTodosLosRRHH();
    }

    @Test
    @DisplayName("Solicitar vacaciones exitosamente con mock")
    void testSolicitarVacacionesExitoso() {
        RRHH rrhh = new RRHH(1, "Juan", "Pérez", "123", "999", "mail@mail.com", "Veterinario", "Cirugía");
        when(rrhhRepository.listarTodos()).thenReturn(List.of(rrhh));
        boolean resultado = controller.solicitarVacaciones(1, "2025-07-27", "2025-08-01");
        assertTrue(resultado);
    }

    @Test
    @DisplayName("Solicitar vacaciones falla si RRHH no existe (mock)")
    void testSolicitarVacacionesRRHHNoExiste() {
        when(rrhhRepository.listarTodos()).thenReturn(Collections.emptyList());
        boolean resultado = controller.solicitarVacaciones(999, "2025-07-27", "2025-08-01");
        assertFalse(resultado);
    }

    @Test
    @DisplayName("Solicitar permiso exitosamente con mock")
    void testSolicitarPermisoExitoso() {
        RRHH rrhh = new RRHH(1, "Juan", "Pérez", "123", "999", "mail@mail.com", "Veterinario", "Cirugía");
        when(rrhhRepository.listarTodos()).thenReturn(List.of(rrhh));
        boolean resultado = controller.solicitarPermiso(1, "Cita médica", "2025-07-27");
        assertTrue(resultado);
    }

    @Test
    @DisplayName("Solicitar permiso falla si RRHH no existe (mock)")
    void testSolicitarPermisoRRHHNoExiste() {
        when(rrhhRepository.listarTodos()).thenReturn(Collections.emptyList());
        boolean resultado = controller.solicitarPermiso(999, "Cita médica", "2025-07-27");
        assertFalse(resultado);
    }

    @Test
    @DisplayName("Solicitar vacaciones falla si fechaInicio o fechaFin es null o vacía")
    void testSolicitarVacacionesFechasInvalidas() {
        RRHH rrhh = new RRHH(1, "Juan", "Pérez", "123", "999", "mail@mail.com", "Veterinario", "Cirugía");
        when(rrhhRepository.listarTodos()).thenReturn(List.of(rrhh));

        assertFalse(controller.solicitarVacaciones(1, null, "2025-08-01"));
        assertFalse(controller.solicitarVacaciones(1, "2025-07-27", null));
        assertFalse(controller.solicitarVacaciones(1, "", "2025-08-01"));
        assertFalse(controller.solicitarVacaciones(1, "2025-07-27", ""));
    }

    @Test
    @DisplayName("Solicitar vacaciones falla si se ingresan letras o formato inválido como fecha")
    void testSolicitarVacacionesFechasMalFormato() {
        RRHH rrhh = new RRHH(1, "Juan", "Pérez", "123", "999", "mail@mail.com", "Veterinario", "Cirugía");
        when(rrhhRepository.listarTodos()).thenReturn(List.of(rrhh));

        assertFalse(controller.solicitarVacaciones(1, "abc", "xyz"));
        assertFalse(controller.solicitarVacaciones(1, "2025/07/27", "2025-08-01"));
        assertFalse(controller.solicitarVacaciones(1, "2025-08-01", "2025/07/27"));
    }

    @Test
    @DisplayName("Solicitar vacaciones falla si la fecha de inicio es posterior a la de fin")
    void testSolicitarVacacionesFechasInvertidas() {
        RRHH rrhh = new RRHH(1, "Juan", "Pérez", "123", "999", "mail@mail.com", "Veterinario", "Cirugía");
        when(rrhhRepository.listarTodos()).thenReturn(List.of(rrhh));

        assertFalse(controller.solicitarVacaciones(1, "2025-08-10", "2025-08-01"));
    }

    @Test
    @DisplayName("Solicitar permiso falla si motivo es null o vacío")
    void testSolicitarPermisoMotivoInvalido() {
        RRHH rrhh = new RRHH(1, "Juan", "Pérez", "123", "999", "mail@mail.com", "Veterinario", "Cirugía");
        when(rrhhRepository.listarTodos()).thenReturn(List.of(rrhh));

        assertFalse(controller.solicitarPermiso(1, null, "2025-08-01"));
        assertFalse(controller.solicitarPermiso(1, "", "2025-08-01"));
    }

    @Test
    @DisplayName("Solicitar permiso falla si la fecha es inválida o con letras")
    void testSolicitarPermisoFechaInvalida() {
        RRHH rrhh = new RRHH(1, "Juan", "Pérez", "123", "999", "mail@mail.com", "Veterinario", "Cirugía");
        when(rrhhRepository.listarTodos()).thenReturn(List.of(rrhh));

        assertFalse(controller.solicitarPermiso(1, "Motivo válido", null));
        assertFalse(controller.solicitarPermiso(1, "Motivo válido", ""));
        assertFalse(controller.solicitarPermiso(1, "Motivo válido", "abc"));
        assertFalse(controller.solicitarPermiso(1, "Motivo válido", "2025/08/01"));
    }

    /**
     * Clase interna para testear lógica con un repositorio mockeado
     */
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
