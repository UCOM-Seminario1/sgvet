package com.sgvet.mascota.boundary;
import com.sgvet.mascota.control.MascotaController;
import com.sgvet.mascota.entity.Mascota;

import java.lang.ref.Cleaner;
import java.util.List;
import java.util.Scanner;

public class MascotaUI {

    static MascotaController mascotaController = new MascotaController();
    public static void main(String[] args) {
        menuMascotas();
    }
    
    public static void menuMascotas() {
        Scanner scanner = new Scanner(System.in);
        int opcion = -1;

        while (opcion != 0) {
            System.out.println("\n--- Menu de Mascotas ---");
            System.out.println("1. Crear mascota");
            System.out.println("2. Listar Mascotas");
            System.out.println("3. Eliminar mascota");
            System.out.println("4. Buscar mascota");
            System.out.println("0. Volver al menu principal");
            System.out.print("Seleccione una opcion (0-4): ");

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
                        System.out.println("Volviendo al menu principal...");
                        break;
                    default:
                        System.out.println("Opcion invalida. Intente de nuevo.");
                }
            } else {
                System.out.println("Por favor, ingrese un numero valido.");
                scanner.next();
            }
        }

    }

    private static void crearMascota(Scanner scanner) {
        System.out.println("Ingrese los datos de la mascota:");
        System.out.print("Nombre: ");
        String nombre = scanner.nextLine();
        System.out.print("Apellido: ");
        String apellido = scanner.nextLine();
        System.out.print("Edad: ");
        int edad = scanner.hasNextInt() ? scanner.nextInt() : 0;
        scanner.nextLine();
        System.out.print("Telefono: ");
        String telefono = scanner.nextLine();
        System.out.print("ID Cliente: ");
        int idCliente = scanner.hasNextInt() ? scanner.nextInt() : 0;
        scanner.nextLine();
        System.out.print("Tipo de Mascota: ");
        String tipoMascota = scanner.nextLine();
        System.out.print("Raza: ");
        String raza = scanner.nextLine();

        Mascota mascota = new Mascota(null, nombre, apellido, edad, telefono, idCliente, tipoMascota, raza);
        boolean creada = mascotaController.crearMascota(mascota);
        if (creada) {
            System.out.println("Mascota creada exitosamente. ID asignado: " + mascota.getId());
        } else {
            System.out.println("Error al crear la mascota.");
        }
    }

    private static void listarMascotas() {
        System.out.println("Funcionalidad para listar Mascotas (pendiente de implementar).");
        List<Mascota> listaMascotas=  mascotaController.listarMascotas();
        for (Mascota mascota: listaMascotas){
            System.out.println("Nombre:" + mascota.getNombre());
            System.out.println("Apellido:" + mascota.getApellido());
            System.out.println("Telefono:" + mascota.getTelefono());
            System.out.println("Edad:" + mascota.getEdad());
            System.out.println("Id:" + mascota.getId());

        }
    }

    private static void eliminarMascota(Scanner scanner) {
        System.out.print("Ingrese el ID de la mascota a eliminar: ");
        if (scanner.hasNextInt()) {
            int id = scanner.nextInt();
            scanner.nextLine(); // Clean buffer
            boolean eliminado = mascotaController.eliminarMascota(id);
            if (eliminado) {
                System.out.println("Mascota eliminada exitosamente.");
            } else {
                System.out.println("No se encontró una mascota con ese ID.");
            }
        } else {
            System.out.println("ID inválido.");
            scanner.next(); // Clean invalid input
        }
    }

    private static void buscarMascota(Scanner scanner) {
        System.out.println("Buscar mascota por:");
        System.out.println("1. ID");
        System.out.println("2. Nombre");
        System.out.println("3. ID Cliente");
        System.out.print("Seleccione una opción (1-3): ");
        int opcion = scanner.hasNextInt() ? scanner.nextInt() : 0;
        scanner.nextLine();
        switch (opcion) {
            case 1:
                System.out.print("Ingrese el ID de la mascota: ");
                int id = scanner.hasNextInt() ? scanner.nextInt() : 0;
                scanner.nextLine();
                Mascota mascota = mascotaController.buscarMascotaPorId(id);
                if (mascota != null) {
                    System.out.println("Mascota encontrada: " + mascota.getNombre() + " " + mascota.getApellido() + ", Tipo: " + mascota.getTipoMascota() + ", Raza: " + mascota.getRaza());
                } else {
                    System.out.println("No se encontró una mascota con ese ID.");
                }
                break;
            case 2:
                System.out.print("Ingrese el nombre (o parte) de la mascota: ");
                String nombre = scanner.nextLine();
                List<Mascota> mascotasPorNombre = mascotaController.buscarMascotasPorNombre(nombre);
                if (!mascotasPorNombre.isEmpty()) {
                    System.out.println("Mascotas encontradas:");
                    for (Mascota m : mascotasPorNombre) {
                        System.out.println("ID: " + m.getId() + ", Nombre: " + m.getNombre() + ", Apellido: " + m.getApellido() + ", Tipo: " + m.getTipoMascota() + ", Raza: " + m.getRaza());
                    }
                } else {
                    System.out.println("No se encontraron mascotas con ese nombre.");
                }
                break;
            case 3:
                System.out.print("Ingrese el ID del cliente: ");
                int idCliente = scanner.hasNextInt() ? scanner.nextInt() : 0;
                scanner.nextLine();
                List<Mascota> mascotasPorCliente = mascotaController.buscarMascotasPorCliente(idCliente);
                if (!mascotasPorCliente.isEmpty()) {
                    System.out.println("Mascotas encontradas para el cliente " + idCliente + ":");
                    for (Mascota m : mascotasPorCliente) {
                        System.out.println("ID: " + m.getId() + ", Nombre: " + m.getNombre() + ", Apellido: " + m.getApellido() + ", Tipo: " + m.getTipoMascota() + ", Raza: " + m.getRaza());
                    }
                } else {
                    System.out.println("No se encontraron mascotas para ese cliente.");
                }
                break;
            default:
                System.out.println("Opción inválida.");
        }
    }
}
