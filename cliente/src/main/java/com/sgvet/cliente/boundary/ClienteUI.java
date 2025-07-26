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
            System.out.println("0. Volver al menu principal");
            System.out.print("Seleccione una opcion (0-4): ");

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
        System.out.println("\n--- Crear Nuevo Cliente ---");
        
        try {
            System.out.print("Ingrese el ID del cliente: ");
            int id = scanner.nextInt();
            scanner.nextLine(); // Limpiar buffer
            
            System.out.print("Ingrese el nombre del cliente: ");
            String nombre = scanner.nextLine();
            
            System.out.print("Ingrese el apellido del cliente: ");
            String apellido = scanner.nextLine();
            
            System.out.print("Ingrese la edad del cliente: ");
            int edad = scanner.nextInt();
            scanner.nextLine(); // Limpiar buffer
            
            System.out.print("Ingrese el teléfono del cliente: ");
            String telefono = scanner.nextLine();
            
            // Crear el objeto Cliente
            Cliente nuevoCliente = new Cliente(id, nombre, apellido, edad, telefono);
            
            // Intentar crear el cliente
            boolean creado = clienteController.crearCliente(nuevoCliente);
            
            if (creado) {
                System.out.println("Cliente creado exitosamente.");
            } else {
                System.out.println("Error al crear el cliente.");
            }
            
        } catch (Exception e) {
            System.out.println("Error en la entrada de datos. Asegúrese de ingresar valores válidos.");
            scanner.nextLine(); // Limpiar buffer en caso de error
        }
    }

    private static void listarClientes() {
        System.out.println("\n--- Lista de Clientes ---");
        List<Cliente> listaClientes = clienteController.listarClientes();
        
        if (listaClientes.isEmpty()) {
            System.out.println("No hay clientes registrados.");
        } else {
            System.out.println("Total de clientes: " + listaClientes.size());
            System.out.println("----------------------------------------");
            for (Cliente cliente : listaClientes) {
                System.out.println("ID: " + cliente.getId());
                System.out.println("Nombre: " + cliente.getNombre());
                System.out.println("Apellido: " + cliente.getApellido());
                System.out.println("Edad: " + cliente.getEdad());
                System.out.println("Teléfono: " + cliente.getTelefono());
                System.out.println("----------------------------------------");
            }
        }
    }

    private static void eliminarCliente(Scanner scanner) {
        System.out.println("\n--- Eliminar Cliente ---");
        System.out.print("Ingrese el ID del cliente a eliminar: ");
        
        if (scanner.hasNextInt()) {
            int id = scanner.nextInt();
            scanner.nextLine(); // Limpiar buffer
            
            // Primero buscar el cliente para mostrar sus datos
            Cliente cliente = clienteController.buscarClientePorId(id);
            
            if (cliente != null) {
                System.out.println("\nCliente encontrado:");
                System.out.println("ID: " + cliente.getId());
                System.out.println("Nombre: " + cliente.getNombre());
                System.out.println("Apellido: " + cliente.getApellido());
                System.out.println("Edad: " + cliente.getEdad());
                System.out.println("Teléfono: " + cliente.getTelefono());
                
                System.out.print("\n¿Está seguro que desea eliminar este cliente? (s/n): ");
                String confirmacion = scanner.nextLine().toLowerCase();
                
                if (confirmacion.equals("s") || confirmacion.equals("si")) {
                    boolean eliminado = clienteController.eliminarCliente(id);
                    if (eliminado) {
                        System.out.println("Cliente eliminado exitosamente.");
                    } else {
                        System.out.println("Error al eliminar el cliente.");
                    }
                } else {
                    System.out.println("Operación cancelada.");
                }
            } else {
                System.out.println("No se encontró un cliente con el ID: " + id);
            }
        } else {
            System.out.println("Por favor, ingrese un ID válido (número entero).");
            scanner.next(); // Limpiar entrada inválida
        }
    }

    private static void buscarCliente(Scanner scanner) {
        System.out.println("\n--- Buscar Cliente ---");
        System.out.print("Ingrese el ID del cliente a buscar: ");
        
        if (scanner.hasNextInt()) {
            int id = scanner.nextInt();
            scanner.nextLine(); // Limpiar buffer
            
            Cliente cliente = clienteController.buscarClientePorId(id);
            
            if (cliente != null) {
                System.out.println("\nCliente encontrado:");
                System.out.println("ID: " + cliente.getId());
                System.out.println("Nombre: " + cliente.getNombre());
                System.out.println("Apellido: " + cliente.getApellido());
                System.out.println("Edad: " + cliente.getEdad());
                System.out.println("Teléfono: " + cliente.getTelefono());
            } else {
                System.out.println("No se encontró un cliente con el ID: " + id);
            }
        } else {
            System.out.println("Por favor, ingrese un ID válido (número entero).");
            scanner.next(); // Limpiar entrada inválida
        }
    }
}
