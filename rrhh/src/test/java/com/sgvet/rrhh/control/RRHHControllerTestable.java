package com.sgvet.rrhh.control;

import com.sgvet.rrhh.boundary.RRHHRepository;
import com.sgvet.rrhh.entity.RRHH;

import java.util.List;

/**
 * Clase de test que extiende RRHHController
 * para permitir inyectar un repositorio mockeado
 */
public class RRHHControllerTestable extends RRHHController {
    private final RRHHRepository rrhhRepository;

    public RRHHControllerTestable(RRHHRepository rrhhRepository) {
        this.rrhhRepository = rrhhRepository;
    }

    @Override
    public List<RRHH> listarRRHHes() {
        return rrhhRepository.listarTodos();
    }

    @Override
    public boolean solicitarVacaciones(int id, String fechaInicio, String fechaFin) {
        if (fechaInicio == null || fechaFin == null || !fechaInicio.matches("\\d{4}-\\d{2}-\\d{2}") || !fechaFin.matches("\\d{4}-\\d{2}-\\d{2}")) {
            return false;
        }

        for (RRHH rrhh : rrhhRepository.listarTodos()) {
            if (rrhh.getId() == id) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean solicitarPermiso(int id, String motivo, String fecha) {
        if (motivo == null || motivo.trim().isEmpty() || fecha == null || fecha.trim().isEmpty()) {
            return false;
        }

        for (RRHH rrhh : rrhhRepository.listarTodos()) {
            if (rrhh.getId() == id) {
                return true;
            }
        }
        return false;
    }
}
