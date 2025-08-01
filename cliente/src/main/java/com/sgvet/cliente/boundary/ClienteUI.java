package com.sgvet.cliente.boundary;
import com.sgvet.cliente.control.ClienteController;
import com.sgvet.cliente.entity.Cliente;

import java.lang.ref.Cleaner;
import java.util.List;
import java.util.Scanner;

public class ClienteUI {

    static ClienteController clienteController = new ClienteController();
    public static void main(String[] args) {
        menuClientes();
    }
    
    public static void menuClientes() {
        Scanner scanner = new Scanner(System.in);
        int opcion = -1;

        while (opcion != 0) {
            System.out.println("\n--- Menu de Clientes ---");
            System.out.println("1. Crear cliente");
            System.out.println("2. Listar clientes");
            System.out.println("3. Eliminar cliente");
            System.out.println("4. Buscar cliente");
            System.out.println("5. Modificar cliente");
            System.out.println("0. Volver al menu principal");
            System.out.print("Seleccione una opcion (0-5): ");

            if (scanner.hasNextInt()) {
                opcion = scanner.nextInt();
                scanner.nextLine(); // Limpiar buffer
                switch (opcion) {
                    case 1:
                        crearCliente(scanner);
                        break;
                    case 2:
                        listarClientes();
                        break;
                    case 3:
                        eliminarCliente(scanner);
                        break;
                    case 4:
                        buscarCliente(scanner);
                        break;
                    case 5:
                        modificarCliente(scanner);
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

    private static void crearCliente(Scanner scanner) {
        System.out.println("Funcionalidad para crear cliente (pendiente de implementar).");
        // Aquí iría la lógica para crear un cliente
    }

    private static void listarClientes() {
        System.out.println("Funcionalidad para listar clientes (pendiente de implementar).");
        List<Cliente> listaClientes=  clienteController.listarClientes();
        for (Cliente cliente: listaClientes){
            System.out.println("Nombre:" + cliente.getNombre());
            System.out.println("Apellido:" + cliente.getApellido());
            System.out.println("Telefono:" + cliente.getTelefono());
            System.out.println("Edad:" + cliente.getEdad());
            System.out.println("Id:" + cliente.getId());

        }
        // Aquí iría la lógica para listar clientes
    }

    private static void eliminarCliente(Scanner scanner) {
        System.out.println("Funcionalidad para eliminar cliente (pendiente de implementar).");
        // Aquí iría la lógica para eliminar un cliente
    }

    private static void buscarCliente(Scanner scanner) {
        System.out.println("Funcionalidad para buscar cliente (pendiente de implementar).");
        // Aquí iría la lógica para buscar un cliente
    }

    private static void modificarCliente(Scanner scanner) {
        System.out.println("\n--- Modificar Cliente ---");
        
        // Solicitar ID del cliente a modificar
        System.out.print("Ingrese el ID del cliente a modificar: ");
        Integer idCliente = null;
        
        try {
            idCliente = Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Error: El ID debe ser un número válido.");
            return;
        }
        
        // Buscar el cliente existente
        Cliente clienteExistente = clienteController.buscarClientePorId(idCliente);
        
        if (clienteExistente == null) {
            System.out.println("Error: No se encontró un cliente con el ID " + idCliente);
            return;
        }
        
        // Mostrar datos actuales
        System.out.println("\nDatos actuales del cliente:");
        System.out.println("ID: " + clienteExistente.getId());
        System.out.println("Nombre: " + clienteExistente.getNombre());
        System.out.println("Apellido: " + clienteExistente.getApellido());
        System.out.println("Edad: " + clienteExistente.getEdad());
        System.out.println("Teléfono: " + clienteExistente.getTelefono());
        
        // Solicitar nuevos datos
        System.out.println("\nIngrese los nuevos datos (presione Enter para mantener el valor actual):");
        
        System.out.print("Nuevo nombre [" + clienteExistente.getNombre() + "]: ");
        String nuevoNombre = scanner.nextLine().trim();
        if (nuevoNombre.isEmpty()) {
            nuevoNombre = clienteExistente.getNombre();
        }
        
        System.out.print("Nuevo apellido [" + clienteExistente.getApellido() + "]: ");
        String nuevoApellido = scanner.nextLine().trim();
        if (nuevoApellido.isEmpty()) {
            nuevoApellido = clienteExistente.getApellido();
        }
        
        System.out.print("Nueva edad [" + clienteExistente.getEdad() + "]: ");
        String nuevaEdadStr = scanner.nextLine().trim();
        Integer nuevaEdad = clienteExistente.getEdad();
        if (!nuevaEdadStr.isEmpty()) {
            try {
                nuevaEdad = Integer.parseInt(nuevaEdadStr);
            } catch (NumberFormatException e) {
                System.out.println("Error: La edad debe ser un número válido. Se mantendrá la edad actual.");
            }
        }
        
        System.out.print("Nuevo teléfono [" + clienteExistente.getTelefono() + "]: ");
        String nuevoTelefono = scanner.nextLine().trim();
        if (nuevoTelefono.isEmpty()) {
            nuevoTelefono = clienteExistente.getTelefono();
        }
        
        // Crear objeto cliente con los datos actualizados
        Cliente clienteActualizado = new Cliente(
            clienteExistente.getId(),
            nuevoNombre,
            nuevoApellido,
            nuevaEdad,
            nuevoTelefono
        );
        
        // Confirmar cambios
        System.out.println("\n¿Está seguro de que desea aplicar estos cambios? (s/n): ");
        String confirmacion = scanner.nextLine().trim().toLowerCase();
        
        if (confirmacion.equals("s") || confirmacion.equals("si") || confirmacion.equals("sí")) {
            // Aplicar cambios
            Boolean resultado = clienteController.modificarCliente(clienteActualizado);
            
            if (resultado) {
                System.out.println("Cliente modificado exitosamente.");
            } else {
                System.out.println("Error: No se pudo modificar el cliente.");
            }
        } else {
            System.out.println("Operación cancelada.");
        }
    }
}