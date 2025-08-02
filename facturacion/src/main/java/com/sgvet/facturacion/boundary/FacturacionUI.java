package com.sgvet.facturacion.boundary;
import com.sgvet.facturacion.control.FacturacionController;
import com.sgvet.facturacion.entity.Facturacion;

import java.util.List;
import java.util.Scanner;

public class FacturacionUI {

    static FacturacionController facturacionController = new FacturacionController();

    public static void main(String[] args) {
        menuFacturacion();
    }

    public static void menuFacturacion() {
        Scanner scanner = new Scanner(System.in);
        int opcion = -1;

        while (opcion != 0) {
            System.out.println("\n--- Menu de Facturaciones ---");
            System.out.println("1. Crear facturacion");
            System.out.println("2. Listar facturaciones");
            System.out.println("3. Eliminar facturacion");
            System.out.println("4. Buscar facturacion");
            System.out.println("0. Volver al menu principal");
            System.out.print("Seleccione una opcion (0-4): ");

            if (scanner.hasNextInt()) {
                opcion = scanner.nextInt();
                scanner.nextLine(); // Limpiar buffer
                switch (opcion) {
                    case 1:
                        crearFacturacion(scanner);
                        break;
                    case 2:
                        listarFacturaciones();
                        break;
                    case 3:
                        eliminarFacturacion(scanner);
                        break;
                    case 4:
                        facturacionController.buscarFacturasInteractivo();
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
        // scanner.close(); // No cerrar aquí si se comparte el scanner globalmente
    }

    private static void crearFacturacion(Scanner scanner) {
        System.out.println("Ingrese los datos de la facturación:");
        System.out.print("ID: ");
        int id = scanner.nextInt();
        scanner.nextLine();
        System.out.print("Nombre: ");
        String nombre = scanner.nextLine();
        System.out.print("Razón Social: ");
        String razonSocial = scanner.nextLine();
        System.out.print("Cantidad: ");
        int cantidad = scanner.nextInt();
        scanner.nextLine();
        System.out.print("Importe: ");
        String importe = scanner.nextLine();
        System.out.print("IVA: ");
        int iva = scanner.nextInt();
        scanner.nextLine();
        System.out.print("Total: ");
        String total = scanner.nextLine();
        System.out.print("Descripción: ");
        String descripcion = scanner.nextLine();

        Facturacion facturacion = new Facturacion(id, nombre, razonSocial, cantidad, importe, iva, total, descripcion);
        facturacionController.crearFacturacion(facturacion);
        System.out.println("Facturación creada correctamente.");
    }

    private static void listarFacturaciones() {
        List<Facturacion> listaFacturaciones = facturacionController.listarFacturacions();
        for (Facturacion facturacion : listaFacturaciones) {
            System.out.println("ID: " + facturacion.getId());
            System.out.println("Nombre: " + facturacion.getNombre());
            System.out.println("Razón Social: " + facturacion.getRazonSocial());
            System.out.println("Cantidad: " + facturacion.getCantidad());
            System.out.println("Importe: " + facturacion.getImporte());
            System.out.println("IVA: " + facturacion.getIva());
            System.out.println("Total: " + facturacion.getTotal());
            System.out.println("Descripción: " + facturacion.getDescripcion());
            System.out.println("-----------------------------");
        }
    }

    private static void eliminarFacturacion(Scanner scanner) {
        System.out.print("Ingrese el ID de la facturación a eliminar: ");
        if (scanner.hasNextInt()) {
            int id = scanner.nextInt();
            scanner.nextLine(); // Clean buffer
            boolean eliminado = facturacionController.eliminarFacturacion(id);
            if (eliminado) {
                System.out.println("Facturación eliminada exitosamente.");
            } else {
                System.out.println("No se encontró una facturación con ese ID.");
            }
        } else {
            System.out.println("ID inválido.");
            scanner.next(); // Clean invalid input
        }
    }

    // private static void buscarFacturacion(Scanner scanner) {
    //     System.out.print("Ingrese el ID de la facturación a buscar: ");
    //     if (scanner.hasNextInt()) {
    //         int id = scanner.nextInt();
    //         scanner.nextLine();
    //         Facturacion facturacion = facturacionController.buscarFactura(id);
    //         if (facturacion != null) {
    //             System.out.println("ID: " + facturacion.getId());
    //             System.out.println("Nombre: " + facturacion.getNombre());
    //             System.out.println("Razón Social: " + facturacion.getRazonSocial());
    //             System.out.println("Cantidad: " + facturacion.getCantidad());
    //             System.out.println("Importe: " + facturacion.getImporte());
    //             System.out.println("IVA: " + facturacion.getIva());
    //             System.out.println("Total: " + facturacion.getTotal());
    //             System.out.println("Descripción: " + facturacion.getDescripcion());
    //         } else {
    //             System.out.println("No se encontró una facturación con ese ID.");
    //         }
    //     } else {
    //         System.out.println("ID inválido.");
    //         scanner.next(); // Clean invalid input
    //     }
    // }
}
