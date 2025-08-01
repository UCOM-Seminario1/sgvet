package com.sgvet.cliente.control;

import com.sgvet.cliente.boundary.HistorialClienteRepository;
import com.sgvet.cliente.entity.HistorialCliente;
import java.util.List;

public class HistorialClienteController {

    private HistorialClienteRepository historialRepository;

    public HistorialClienteController() {
        this.historialRepository = new HistorialClienteRepository();
    }

    /**
     * Crear una nueva entrada en el historial del cliente
     * @param historial Objeto HistorialCliente a crear
     * @return true si se creó exitosamente, false en caso contrario
     */
    public boolean crearHistorial(HistorialCliente historial) {
        try {
            // Validaciones básicas
            if (historial == null) {
                System.err.println("Error: El historial no puede ser nulo");
                return false;
            }

            if (historial.getIdCliente() == null || historial.getIdMascota() == null) {
                System.err.println("Error: ID de cliente e ID de mascota son obligatorios");
                return false;
            }

            if (historial.getTipoConsulta() == null || historial.getTipoConsulta().trim().isEmpty()) {
                System.err.println("Error: El tipo de consulta es obligatorio");
                return false;
            }

            // Verificar si ya existe un historial con el mismo ID
            if (historial.getId() != null && historialRepository.buscarPorId(historial.getId()) != null) {
                System.err.println("Error: Ya existe un historial con el ID: " + historial.getId());
                return false;
            }

            historialRepository.insertar(historial);
            return true;

        } catch (Exception e) {
            System.err.println("Error al crear el historial: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Consultar el historial completo de un cliente específico
     * @param clienteId ID del cliente
     * @return Lista de entradas del historial del cliente
     */
    public List<HistorialCliente> consultarHistorialPorCliente(Integer clienteId) {
        try {
            if (clienteId == null) {
                System.err.println("Error: El ID del cliente no puede ser nulo");
                return List.of(); // Retorna lista vacía
            }

            return historialRepository.listarPorClienteId(clienteId);

        } catch (Exception e) {
            System.err.println("Error al consultar historial por cliente: " + e.getMessage());
            e.printStackTrace();
            return List.of(); // Retorna lista vacía en caso de error
        }
    }

    /**
     * Listar todo el historial de todos los clientes
     * @return Lista completa del historial
     */
    public List<HistorialCliente> listarTodoElHistorial() {
        try {
            return historialRepository.listarTodos();
        } catch (Exception e) {
            System.err.println("Error al listar todo el historial: " + e.getMessage());
            e.printStackTrace();
            return List.of(); // Retorna lista vacía en caso de error
        }
    }

    /**
     * Buscar una entrada específica del historial por su ID
     * @param id ID de la entrada del historial
     * @return Objeto HistorialCliente si se encuentra, null en caso contrario
     */
    public HistorialCliente buscarHistorialPorId(Integer id) {
        try {
            if (id == null) {
                System.err.println("Error: El ID no puede ser nulo");
                return null;
            }

            return historialRepository.buscarPorId(id);

        } catch (Exception e) {
            System.err.println("Error al buscar historial por ID: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Eliminar una entrada del historial
     * @param id ID de la entrada a eliminar
     * @return true si se eliminó exitosamente, false en caso contrario
     */
    public boolean eliminarHistorial(Integer id) {
        try {
            if (id == null) {
                System.err.println("Error: El ID no puede ser nulo");
                return false;
            }

            // Verificar si existe antes de eliminar
            HistorialCliente historialExistente = historialRepository.buscarPorId(id);
            if (historialExistente == null) {
                System.err.println("Error: No se encontró historial con ID: " + id);
                return false;
            }

            return historialRepository.eliminar(id);

        } catch (Exception e) {
            System.err.println("Error al eliminar historial: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Actualizar una entrada existente del historial
     * @param historial Objeto HistorialCliente con los datos actualizados
     * @return true si se actualizó exitosamente, false en caso contrario
     */
    public boolean actualizarHistorial(HistorialCliente historial) {
        try {
            if (historial == null || historial.getId() == null) {
                System.err.println("Error: El historial y su ID no pueden ser nulos");
                return false;
            }

            // Verificar si existe el historial a actualizar
            HistorialCliente historialExistente = historialRepository.buscarPorId(historial.getId());
            if (historialExistente == null) {
                System.err.println("Error: No se encontró historial con ID: " + historial.getId());
                return false;
            }

            // Para actualizar, primero eliminamos y luego insertamos
            // (Esto podría mejorarse con un método update específico en el repository)
            if (historialRepository.eliminar(historial.getId())) {
                historialRepository.insertar(historial);
                return true;
            }

            return false;

        } catch (Exception e) {
            System.err.println("Error al actualizar historial: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Obtener estadísticas básicas del historial
     * @param clienteId ID del cliente (opcional, null para todos los clientes)
     * @return String con información estadística
     */
    public String obtenerEstadisticas(Integer clienteId) {
        try {
            List<HistorialCliente> historial;

            if (clienteId != null) {
                historial = historialRepository.listarPorClienteId(clienteId);
            } else {
                historial = historialRepository.listarTodos();
            }

            if (historial.isEmpty()) {
                return "No hay entradas en el historial.";
            }

            int totalEntradas = historial.size();
            double costoTotal = historial.stream()
                    .mapToDouble(h -> h.getCosto() != null ? h.getCosto() : 0.0)
                    .sum();
            double costoPromedio = costoTotal / totalEntradas;

            String tipoClienteText = clienteId != null ? "del cliente ID " + clienteId : "general";

            return String.format(
                    "=== Estadísticas del Historial %s ===\n" +
                            "Total de entradas: %d\n" +
                            "Costo total: $%.2f\n" +
                            "Costo promedio por consulta: $%.2f",
                    tipoClienteText, totalEntradas, costoTotal, costoPromedio
            );

        } catch (Exception e) {
            System.err.println("Error al obtener estadísticas: " + e.getMessage());
            e.printStackTrace();
            return "Error al calcular estadísticas.";
        }
    }
}