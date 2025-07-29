package com.sgvet.cliente.boundary;

import com.sgvet.cliente.control.HistorialClienteController;
import com.sgvet.cliente.control.ClienteController;
import com.sgvet.cliente.entity.HistorialCliente;
import com.sgvet.cliente.entity.Cliente;
import com.sgvet.mascota.boundary.MascotaRepository;
import com.sgvet.mascota.entity.Mascota;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;

public class HistorialClienteUI {

    private static HistorialClienteController historialController = new HistorialClienteController();
    private static ClienteController clienteController = new ClienteController();
    private static MascotaRepository mascotaRepository = new MascotaRepository();

    public static void main(String[] args) {
        menuHistorial();
    }

    public static void menuHistorial() {
        Scanner scanner = new Scanner(System.in);
        int opcion = -1;

        while (opcion != 0) {
            System.out.println("\n--- Menu de Historial de Clientes ---");
            System.out.println("1. Agregar entrada al historial");
            System.out.println("2. Consultar historial por cliente");
            System.out.println("3. Ver todo el historial");
            System.out.println("4. Buscar entrada por ID");
            System.out.println("5. Eliminar entrada del historial");
            System.out.println("0. Volver al menu principal");
            System.out.print("Seleccione una opcion (0-5): ");

            if (scanner.hasNextInt()) {
                opcion = scanner.nextInt();
                scanner.nextLine(); // Limpiar buffer
                switch (opcion) {
                    case 1:
                        agregarHistorial(scanner);
                        break;
                    case 2:
                        consultarHistorialPorCliente(scanner);
                        break;
                    case 3:
                        verTodoElHistorial();
                        break;
                    case 4:
                        buscarHistorialPorId(scanner);
                        break;
                    case 5:
                        eliminarHistorial(scanner);
                        break;
                    case 0:
                        System.out.println("Volviendo al menu principal...");
                        break;
                    default:
                        System.out.println("Opcion invalida. Intente de nuevo.");
                }
            } else {
                System.out.println("Por favor, ingrese un numero valido.");
                scanner.next(); // Limpiar entrada inválida
            }
        }
    }

    private static void agregarHistorial(Scanner scanner) {
        System.out.println("\n--- Agregar Entrada al Historial ---");

        // Mostrar clientes disponibles
        System.out.println("Clientes disponibles:");
        List<Cliente> clientes = clienteController.listarClientes();
        if (clientes.isEmpty()) {
            System.out.println("No hay clientes registrados.");
            return;
        }

        for (Cliente cliente : clientes) {
            System.out.printf("ID: %d - %s %s\n", cliente.getId(), cliente.getNombre(), cliente.getApellido());
        }

        System.out.print("Ingrese ID del historial: ");
        Integer id = scanner.nextInt();
        scanner.nextLine();

        System.out.print("Ingrese ID del cliente: ");
        Integer clienteId = scanner.nextInt();
        scanner.nextLine();

        // Mostrar mascotas del cliente
        List<Mascota> mascotasCliente = mascotaRepository.listarTodos().stream()
                .filter(m -> m.getIdCliente().equals(clienteId))
                .collect(java.util.stream.Collectors.toList());

        if (mascotasCliente.isEmpty()) {
            System.out.println("El cliente no tiene mascotas registradas.");
            return;
        }

        System.out.println("Mascotas del cliente:");
        for (Mascota mascota : mascotasCliente) {
            System.out.printf("ID: %d - %s (%s)\n", mascota.getId(), mascota.getNombre(), mascota.getTipoMascota());
        }

        System.out.print("Ingrese ID de la mascota: ");
        Integer idMascota = scanner.nextInt();
        scanner.nextLine();

        System.out.print("Tipo de consulta: ");
        String tipoConsulta = scanner.nextLine();

        System.out.print("Descripcion detallada: ");
        String descripcion = scanner.nextLine();

        System.out.print("Diagnostico: ");
        String diagnostico = scanner.nextLine();

        System.out.print("Tratamiento: ");
        String tratamiento = scanner.nextLine();

        System.out.print("Veterinario: ");
        String veterinario = scanner.nextLine();

        System.out.print("Costo: ");
        Double costo = scanner.nextDouble();
        scanner.nextLine();

        System.out.print("Observacion final: ");
        String observacion = scanner.nextLine();

        HistorialCliente historial = new HistorialCliente(
                id, clienteId, idMascota, tipoConsulta, descripcion, diagnostico,
                tratamiento, LocalDateTime.now(), veterinario, costo, observacion
        );

        if (historialController.crearHistorial(historial)) {
            System.out.println("Entrada del historial agregada exitosamente.");
        } else {
            System.out.println("Error al agregar la entrada del historial.");
        }
    }

    private static void consultarHistorialPorCliente(Scanner scanner) {
        System.out.println("\n--- Consultar Historial por Cliente ---");

        // Mostrar clientes disponibles
        System.out.println("Clientes disponibles:");
        List<Cliente> clientes = clienteController.listarClientes();
        if (clientes.isEmpty()) {
            System.out.println("No hay clientes registrados.");
            return;
        }

        for (Cliente cliente : clientes) {
            System.out.printf("ID: %d - %s %s\n", cliente.getId(), cliente.getNombre(), cliente.getApellido());
        }

        System.out.print("Ingrese ID del cliente: ");
        Integer clienteId = scanner.nextInt();
        scanner.nextLine();

        List<HistorialCliente> historial = historialController.consultarHistorialPorCliente(clienteId);

        if (historial.isEmpty()) {
            System.out.println("No se encontro historial para el cliente especificado.");
            return;
        }

        System.out.println("\n--- Historial del Cliente ---");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

        for (HistorialCliente entrada : historial) {
            System.out.println("=============================================");
            System.out.println("ID: " + entrada.getId());
            System.out.println("Cliente ID: " + entrada.getIdCliente());
            System.out.println("Fecha: " + entrada.getFechaConsulta().format(formatter));

            // Obtener información de la mascota
            Mascota mascota = mascotaRepository.listarTodos().stream()
                    .filter(m -> m.getId().equals(entrada.getIdMascota()))
                    .findFirst()
                    .orElse(null);

            if (mascota != null) {
                System.out.println("Mascota: " + mascota.getNombre() + " (" + mascota.getTipoMascota() + " - " + mascota.getRaza() + ")");
            } else {
                System.out.println("Mascota ID: " + entrada.getIdMascota() + " (No encontrada)");
            }

            System.out.println("Tipo de Consulta: " + entrada.getTipoConsulta());
            System.out.println("Descripcion: " + entrada.getDescripcion());
            System.out.println("Diagnostico: " + entrada.getDiagnostico());
            System.out.println("Tratamiento: " + entrada.getTratamiento());
            System.out.println("Veterinario: " + entrada.getVeterinario());
            System.out.println("Costo: $" + entrada.getCosto());
            System.out.println("Observacion: " + entrada.getObservacion());
            System.out.println("=============================================\n");
        }
    }

    private static void verTodoElHistorial() {
        System.out.println("\n--- Todo el Historial ---");
        List<HistorialCliente> historial = historialController.listarTodoElHistorial();

        if (historial.isEmpty()) {
            System.out.println("No hay entradas en el historial.");
            return;
        }

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

        for (HistorialCliente entrada : historial) {
            System.out.println("=============================================");
            System.out.println("ID: " + entrada.getId());
            System.out.println("Cliente ID: " + entrada.getIdCliente());
            System.out.println("Fecha: " + entrada.getFechaConsulta().format(formatter));

            // Obtener información de la mascota
            Mascota mascota = mascotaRepository.listarTodos().stream()
                    .filter(m -> m.getId().equals(entrada.getIdMascota()))
                    .findFirst()
                    .orElse(null);

            if (mascota != null) {
                System.out.println("Mascota: " + mascota.getNombre() + " (" + mascota.getTipoMascota() + " - " + mascota.getRaza() + ")");
            } else {
                System.out.println("Mascota ID: " + entrada.getIdMascota() + " (No encontrada)");
            }

            System.out.println("Tipo de Consulta: " + entrada.getTipoConsulta());
            System.out.println("Descripcion: " + entrada.getDescripcion());
            System.out.println("Diagnostico: " + entrada.getDiagnostico());
            System.out.println("Tratamiento: " + entrada.getTratamiento());
            System.out.println("Veterinario: " + entrada.getVeterinario());
            System.out.println("Costo: $" + entrada.getCosto());
            System.out.println("Observacion: " + entrada.getObservacion());
            System.out.println("=============================================\n");
        }
    }

    private static void buscarHistorialPorId(Scanner scanner) {
        System.out.println("\n--- Buscar Entrada por ID ---");
        System.out.print("Ingrese ID de la entrada: ");
        Integer id = scanner.nextInt();
        scanner.nextLine();

        HistorialCliente historial = historialController.buscarHistorialPorId(id);

        if (historial == null) {
            System.out.println("No se encontro entrada con el ID especificado.");
            return;
        }

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        System.out.println("\n--- Entrada Encontrada ---");
        System.out.println("=============================================");
        System.out.println("ID: " + historial.getId());
        System.out.println("Cliente ID: " + historial.getIdCliente());
        System.out.println("Fecha: " + historial.getFechaConsulta().format(formatter));

        // Obtener información de la mascota
        Mascota mascota = mascotaRepository.listarTodos().stream()
                .filter(m -> m.getId().equals(historial.getIdMascota()))
                .findFirst()
                .orElse(null);

        if (mascota != null) {
            System.out.println("Mascota: " + mascota.getNombre() + " (" + mascota.getTipoMascota() + " - " + mascota.getRaza() + ")");
        } else {
            System.out.println("Mascota ID: " + historial.getIdMascota() + " (No encontrada)");
        }

        System.out.println("Tipo de Consulta: " + historial.getTipoConsulta());
        System.out.println("Descripcion: " + historial.getDescripcion());
        System.out.println("Diagnostico: " + historial.getDiagnostico());
        System.out.println("Tratamiento: " + historial.getTratamiento());
        System.out.println("Veterinario: " + historial.getVeterinario());
        System.out.println("Costo: $" + historial.getCosto());
        System.out.println("Observacion: " + historial.getObservacion());
        System.out.println("=============================================");
    }

    private static void eliminarHistorial(Scanner scanner) {
        System.out.println("\n--- Eliminar Entrada del Historial ---");
        System.out.print("Ingrese ID de la entrada a eliminar: ");
        Integer id = scanner.nextInt();
        scanner.nextLine();

        if (historialController.eliminarHistorial(id)) {
            System.out.println("Entrada del historial eliminada exitosamente.");
        } else {
            System.out.println("Error al eliminar la entrada del historial o ID no encontrado.");
        }
    }
}