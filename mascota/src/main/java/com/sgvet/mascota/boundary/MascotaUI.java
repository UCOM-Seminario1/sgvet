package com.sgvet.mascota.boundary;
import com.sgvet.mascota.control.MascotaController;
import com.sgvet.mascota.entity.Mascota;

import java.lang.ref.Cleaner;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Logger;

public class MascotaUI {

    private static final Logger logger = Logger.getLogger(MascotaUI.class.getName());
    static MascotaController mascotaController = new MascotaController();

    public static void main(String[] args) {
        menuMascotas();
    }
    
    public static void menuMascotas() {
        Scanner scanner = new Scanner(System.in);
        int opcion = -1;

        while (opcion != 0) {
            logger.info("\n--- Menu de Mascotas ---");
            logger.info("1. Crear mascota");
            logger.info("2. Listar Mascotas");
            logger.info("3. Eliminar mascota");
            logger.info("4. Buscar mascota");
            logger.info("0. Volver al menu principal");
            logger.info("Seleccione una opcion (0-4): ");

            if (scanner.hasNextInt()) {
                opcion = scanner.nextInt();
                scanner.nextLine(); // Limpiar buffer
                switch (opcion) {
                    case 1:
                        crearMascota(scanner);
                        break;
                    case 2:
                        listarMascotas();
                        break;
                    case 3:
                        eliminarMascota(scanner);
                        break;
                    case 4:
                        buscarMascota(scanner);
                        break;
                    case 0:
                        logger.info("Volviendo al menu principal...");
                        break;
                    default:
                        logger.warning("Opcion invalida. Intente de nuevo.");
                }
            } else {
                logger.warning("Por favor, ingrese un numero valido.");
                scanner.next();
            }
        }

    }

    private static void crearMascota(Scanner scanner) {
        logger.info("Ingrese los datos de la mascota:");
        logger.info("Nombre: ");
        String nombre = scanner.nextLine();
        logger.info("Apellido: ");
        String apellido = scanner.nextLine();
        logger.info("Edad: ");
        int edad = scanner.hasNextInt() ? scanner.nextInt() : 0;
        scanner.nextLine();
        logger.info("Telefono: ");
        String telefono = scanner.nextLine();
        logger.info("ID Cliente: ");
        int idCliente = scanner.hasNextInt() ? scanner.nextInt() : 0;
        scanner.nextLine();
        logger.info("Tipo de Mascota: ");
        String tipoMascota = scanner.nextLine();
        logger.info("Raza: ");
        String raza = scanner.nextLine();

        Mascota mascota = new Mascota(null, nombre, apellido, edad, telefono, idCliente, tipoMascota, raza);
        boolean creada = mascotaController.crearMascota(mascota);
        if (creada) {
            logger.info("Mascota creada exitosamente. ID asignado: " + mascota.getId());
        } else {
            logger.severe("Error al crear la mascota.");
        }
    }

    private static void listarMascotas() {
        logger.info("Funcionalidad para listar Mascotas (pendiente de implementar).");
        List<Mascota> listaMascotas=  mascotaController.listarMascotas();
        for (Mascota mascota: listaMascotas){
            logger.info("Nombre:" + mascota.getNombre());
            logger.info("Apellido:" + mascota.getApellido());
            logger.info("Telefono:" + mascota.getTelefono());
            logger.info("Edad:" + mascota.getEdad());
            logger.info("Id:" + mascota.getId());

        }
    }

    private static void eliminarMascota(Scanner scanner) {
        logger.info("Ingrese el ID de la mascota a eliminar: ");
        if (scanner.hasNextInt()) {
            int id = scanner.nextInt();
            scanner.nextLine(); // Clean buffer
            boolean eliminado = mascotaController.eliminarMascota(id);
            if (eliminado) {
                logger.info("Mascota eliminada exitosamente.");
            } else {
                logger.warning("No se encontró una mascota con ese ID.");
            }
        } else {
            logger.warning("ID inválido.");
            scanner.next(); // Clean invalid input
        }
    }

    private static void buscarMascota(Scanner scanner) {
        logger.info("Buscar mascota por:");
        logger.info("1. ID");
        logger.info("2. Nombre");
        logger.info("3. ID Cliente");
        logger.info("Seleccione una opción (1-3): ");
        int opcion = scanner.hasNextInt() ? scanner.nextInt() : 0;
        scanner.nextLine();

        switch (opcion) {
            case 1:
                buscarPorId(scanner);
                break;
            case 2:
                buscarPorNombre(scanner);
                break;
            case 3:
                buscarPorCliente(scanner);
                break;
            default:
                logger.warning("Opción inválida.");
        }
    }

    private static void buscarPorId(Scanner scanner) {
        logger.info("Ingrese el ID de la mascota: ");
        int id = scanner.hasNextInt() ? scanner.nextInt() : 0;
        scanner.nextLine();
        Mascota mascota = mascotaController.buscarMascotaPorId(id);
        if (mascota != null) {
            logger.info("Mascota encontrada: " + formatMascotaInfo(mascota));
        } else {
            logger.warning("No se encontró una mascota con ese ID.");
        }
    }

    private static void buscarPorNombre(Scanner scanner) {
        logger.info("Ingrese el nombre (o parte) de la mascota: ");
        String nombre = scanner.nextLine();
        List<Mascota> mascotasPorNombre = mascotaController.buscarMascotasPorNombre(nombre);
        if (!mascotasPorNombre.isEmpty()) {
            logger.info("Mascotas encontradas:");
            mascotasPorNombre.forEach(m -> logger.info(formatMascotaDetails(m)));
        } else {
            logger.warning("No se encontraron mascotas con ese nombre.");
        }
    }

    private static void buscarPorCliente(Scanner scanner) {
        logger.info("Ingrese el ID del cliente: ");
        int idCliente = scanner.hasNextInt() ? scanner.nextInt() : 0;
        scanner.nextLine();
        List<Mascota> mascotasPorCliente = mascotaController.buscarMascotasPorCliente(idCliente);
        if (!mascotasPorCliente.isEmpty()) {
            logger.info("Mascotas encontradas para el cliente " + idCliente + ":");
            mascotasPorCliente.forEach(m -> logger.info(formatMascotaDetails(m)));
        } else {
            logger.warning("No se encontraron mascotas para ese cliente.");
        }
    }

    private static String formatMascotaInfo(Mascota mascota) {
        return mascota.getNombre() + " " + mascota.getApellido() +
               ", Tipo: " + mascota.getTipoMascota() +
               ", Raza: " + mascota.getRaza();
    }

    private static String formatMascotaDetails(Mascota mascota) {
        return "ID: " + mascota.getId() +
               ", Nombre: " + mascota.getNombre() +
               ", Apellido: " + mascota.getApellido() +
               ", Tipo: " + mascota.getTipoMascota() +
               ", Raza: " + mascota.getRaza();
    }
}
