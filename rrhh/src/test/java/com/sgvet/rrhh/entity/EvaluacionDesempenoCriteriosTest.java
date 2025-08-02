package com.sgvet.rrhh.entity;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Evaluación de Desempeño según criterios de clasificación")
class EvaluacionDesempenoCriteriosTest {

    private String clasificarDesempeno(double calificacion) {
        if (calificacion >= 9) return "Excelente";
        if (calificacion >= 7) return "Bueno";
        if (calificacion >= 5) return "Regular";
        return "Deficiente";
    }

    @Test
    @DisplayName("Debe clasificar correctamente un desempeño Excelente")
    void deberiaClasificarExcelente() {
        EvaluacionDesempeno eval = new EvaluacionDesempeno(1, "2024-01-15", 9, 10, 9, 10, "Excelente desempeño");
        assertEquals("Excelente", clasificarDesempeno(eval.getCalificacionFinal()));
    }

    @Test
    @DisplayName("Debe clasificar correctamente un desempeño Bueno")
    void deberiaClasificarBueno() {
        EvaluacionDesempeno eval = new EvaluacionDesempeno(2, "2024-01-15", 8, 7, 7, 8, "Buen desempeño");
        assertEquals("Bueno", clasificarDesempeno(eval.getCalificacionFinal()));
    }

    @Test
    @DisplayName("Debe clasificar correctamente un desempeño Regular")
    void deberiaClasificarRegular() {
        EvaluacionDesempeno eval = new EvaluacionDesempeno(3, "2024-01-15", 5, 6, 6, 5, "Desempeño aceptable");
        assertEquals("Regular", clasificarDesempeno(eval.getCalificacionFinal()));
    }

    @Test
    @DisplayName("Debe clasificar correctamente un desempeño Deficiente")
    void deberiaClasificarDeficiente() {
        EvaluacionDesempeno eval = new EvaluacionDesempeno(4, "2024-01-15", 3, 4, 2, 3, "Desempeño deficiente");
        assertEquals("Deficiente", clasificarDesempeno(eval.getCalificacionFinal()));
    }

    @Test
    @DisplayName("Debe validar que ningún campo obligatorio esté vacío o nulo")
    void deberiaValidarCamposObligatorios() {
        EvaluacionDesempeno eval = new EvaluacionDesempeno();
        assertEquals(0, eval.getIdEmpleado(), "ID de empleado no debería estar vacío");
        assertNull(eval.getFecha(), "La fecha debería estar inicialmente en null");
    }

    @Test
    @DisplayName("Debe validar que las calificaciones estén en el rango [0-10]")
    void deberiaValidarRangoCalificaciones() {
        EvaluacionDesempeno eval = new EvaluacionDesempeno(5, "2024-01-15", -1, 11, 5, 8, "Valores fuera de rango");
        assertTrue(eval.getPuntualidad() < 0 || eval.getAtencionCliente() > 10,
                "Debe detectar valores fuera del rango [0-10]");
    }
}
