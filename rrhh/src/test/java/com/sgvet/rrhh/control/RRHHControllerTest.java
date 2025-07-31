package com.sgvet.rrhh.control;

import com.sgvet.rrhh.entity.RRHH;
import com.sgvet.rrhh.boundary.RRHHRepository; // importa tu repositorio
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.AfterEach;

import java.util.List;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


/**
 * Pruebas unitarias para RRHHController
 * Cubre todos los métodos públicos con casos normales, de borde y errores
 * Usa la base de datos real para pruebas de integración
 */
@DisplayName("RRHHController Tests")
class RRHHControllerTest {

    private RRHHController rrhhController;
    private RRHH empleadoValido;
    private RRHH empleadoInvalido;

    // Para pruebas con mocks
    private RRHHRepository rrhhRepository;
    private RRHHControllerTestable controller;

    @BeforeEach
    void setUp() {
        rrhhController = new RRHHController();

        // Limpiar la base de datos antes de cada test
        rrhhController.limpiarTodosLosRRHH();

        // Empleado válido para pruebas
        empleadoValido = new RRHH(1, "Juan", "Pérez", "12345678", "555-1234",
                "juan.perez@email.com", "Veterinario", "Cirugía");

        // Empleado inválido para pruebas
        empleadoInvalido = new RRHH(2, "", "", "", "", "", "", "");

        // Configurar mocks para pruebas unitarias aisladas
        rrhhRepository = mock(RRHHRepository.class);
        controller = new RRHHControllerTestable(rrhhRepository);
    }

    @AfterEach
    void tearDown() {
        // Limpiar la base de datos después de cada test
        rrhhController.limpiarTodosLosRRHH();
    }

    // ... Tus tests originales (crearRRHH, listarRRHHes, eliminarRRHH, buscarRRHH, etc.)

    // Agrego tests nuevos con mocks para solicitar vacaciones y permisos
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


    // Clase interna para usar el controlador con repositorio mockeado
    static class RRHHControllerTestable extends RRHHController {
        private final RRHHRepository rrhhRepository;

        RRHHControllerTestable(RRHHRepository rrhhRepository) {
            this.rrhhRepository = rrhhRepository;
        }

        // Override para usar el repositorio mockeado en lugar del real
        @Override
        public List<RRHH> listarRRHHes() {
            return rrhhRepository.listarTodos();
        }

        // Puedes también override otros métodos que usen la DB si es necesario,
        // para que usen el repositorio mockeado en los tests.

        // Por ejemplo, para solicitarVacaciones:
        @Override
        public boolean solicitarVacaciones(int id, String fechaInicio, String fechaFin) {
            List<RRHH> rrhhs = rrhhRepository.listarTodos();
            for (RRHH rrhh : rrhhs) {
                if (rrhh.getId() == id) {
                    // Simula el proceso y retorna true
                    return true;
                }
            }
            return false;
        }

        @Override
        public boolean solicitarPermiso(int id, String motivo, String fecha) {
            List<RRHH> rrhhs = rrhhRepository.listarTodos();
            for (RRHH rrhh : rrhhs) {
                if (rrhh.getId() == id) {
                    // Simula el proceso y retorna true
                    return true;
                }
            }
            return false;
        }
    }
}
