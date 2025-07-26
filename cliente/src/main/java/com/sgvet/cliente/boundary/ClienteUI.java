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
        int opcionBusqueda = -1;
        
        while (opcionBusqueda != 0) {
            System.out.println("\n--- Menu de Busqueda de Clientes ---");
            System.out.println("1. Buscar por ID");
            System.out.println("2. Buscar por nombre");
            System.out.println("3. Buscar por apellido");
            System.out.println("4. Buscar por teléfono");
            System.out.println("5. Búsqueda general");
            System.out.println("0. Volver al menu de clientes");
            System.out.print("Seleccione una opcion de busqueda (0-5): ");
            
            if (scanner.hasNextInt()) {
                opcionBusqueda = scanner.nextInt();
                scanner.nextLine(); // Limpiar buffer
                
                switch (opcionBusqueda) {
                    case 1:
                        buscarPorId(scanner);
                        break;
                    case 2:
                        buscarPorNombre(scanner);
                        break;
                    case 3:
                        buscarPorApellido(scanner);
                        break;
                    case 4:
                        buscarPorTelefono(scanner);
                        break;
                    case 5:
                        buscarGeneral(scanner);
                        break;
                    case 0:
                        System.out.println("Volviendo al menu de clientes...");
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

    private static void buscarPorId(Scanner scanner) {
        System.out.print("Ingrese el ID del cliente a buscar: ");
        if (scanner.hasNextInt()) {
            int id = scanner.nextInt();
            scanner.nextLine(); // Limpiar buffer
            
            Cliente cliente = clienteController.buscarClientePorId(id);
            if (cliente != null) {
                System.out.println("\n--- Cliente Encontrado ---");
                mostrarCliente(cliente);
            } else {
                System.out.println("No se encontró ningún cliente con el ID: " + id);
            }
        } else {
            System.out.println("Por favor, ingrese un ID válido (número entero).");
            scanner.next(); // Limpiar entrada inválida
        }
    }

    private static void buscarPorNombre(Scanner scanner) {
        System.out.print("Ingrese el nombre del cliente a buscar: ");
        String nombre = scanner.nextLine().trim();
        
        if (!nombre.isEmpty()) {
            List<Cliente> clientes = clienteController.buscarClientesPorNombre(nombre);
            mostrarResultadosBusqueda(clientes, "nombre", nombre);
        } else {
            System.out.println("Por favor, ingrese un nombre válido.");
        }
    }

    private static void buscarPorApellido(Scanner scanner) {
        System.out.print("Ingrese el apellido del cliente a buscar: ");
        String apellido = scanner.nextLine().trim();
        
        if (!apellido.isEmpty()) {
            List<Cliente> clientes = clienteController.buscarClientesPorApellido(apellido);
            mostrarResultadosBusqueda(clientes, "apellido", apellido);
        } else {
            System.out.println("Por favor, ingrese un apellido válido.");
        }
    }

    private static void buscarPorTelefono(Scanner scanner) {
        System.out.print("Ingrese el teléfono del cliente a buscar: ");
        String telefono = scanner.nextLine().trim();
        
        if (!telefono.isEmpty()) {
            List<Cliente> clientes = clienteController.buscarClientesPorTelefono(telefono);
            mostrarResultadosBusqueda(clientes, "teléfono", telefono);
        } else {
            System.out.println("Por favor, ingrese un teléfono válido.");
        }
    }

    private static void buscarGeneral(Scanner scanner) {
        System.out.print("Ingrese el criterio de búsqueda (nombre, apellido o teléfono): ");
        String criterio = scanner.nextLine().trim();
        
        if (!criterio.isEmpty()) {
            List<Cliente> clientes = clienteController.buscarClientesGeneral(criterio);
            mostrarResultadosBusqueda(clientes, "criterio general", criterio);
        } else {
            System.out.println("Por favor, ingrese un criterio de búsqueda válido.");
        }
    }

    private static void mostrarResultadosBusqueda(List<Cliente> clientes, String tipo, String criterio) {
        if (clientes != null && !clientes.isEmpty()) {
            System.out.println("\n--- Resultados de búsqueda por " + tipo + ": '" + criterio + "' ---");
            System.out.println("Se encontraron " + clientes.size() + " cliente(s):");
            System.out.println();
            
            for (int i = 0; i < clientes.size(); i++) {
                System.out.println("Cliente " + (i + 1) + ":");
                mostrarCliente(clientes.get(i));
                if (i < clientes.size() - 1) {
                    System.out.println("---");
                }
            }
        } else {
            System.out.println("No se encontraron clientes con el " + tipo + ": '" + criterio + "'");
        }
    }

    private static void mostrarCliente(Cliente cliente) {
        System.out.println("ID: " + cliente.getId());
        System.out.println("Nombre: " + cliente.getNombre());
        System.out.println("Apellido: " + cliente.getApellido());
        System.out.println("Edad: " + cliente.getEdad());
        System.out.println("Teléfono: " + cliente.getTelefono());
    }
}
