package com.sgvet.cliente;

import com.sgvet.cliente.control.ClienteController;
import com.sgvet.cliente.entity.Cliente;
import java.util.List;

/**
 * Clase de prueba para demostrar la funcionalidad de búsqueda de clientes
 * Esta clase no es un test unitario formal, sino una demostración de uso
 */
public class ClienteBusquedaTest {
    
    public static void main(String[] args) {
        System.out.println("=== DEMOSTRACIÓN DE BÚSQUEDA DE CLIENTES ===\n");
        
        ClienteController controller = new ClienteController();
        
        // 1. Listar todos los clientes
        System.out.println("1. LISTANDO TODOS LOS CLIENTES:");
        List<Cliente> todosLosClientes = controller.listarClientes();
        if (todosLosClientes != null && !todosLosClientes.isEmpty()) {
            for (Cliente cliente : todosLosClientes) {
                System.out.println("   - " + cliente.getNombre() + " " + cliente.getApellido() + " (ID: " + cliente.getId() + ")");
            }
        } else {
            System.out.println("   No hay clientes registrados en la base de datos.");
        }
        
        System.out.println("\n" + "=".repeat(50) + "\n");
        
        // 2. Demostrar búsqueda por ID (si hay clientes)
        if (todosLosClientes != null && !todosLosClientes.isEmpty()) {
            Cliente primerCliente = todosLosClientes.get(0);
            System.out.println("2. BÚSQUEDA POR ID (" + primerCliente.getId() + "):");
            Cliente clienteEncontrado = controller.buscarClientePorId(primerCliente.getId());
            if (clienteEncontrado != null) {
                System.out.println("   Cliente encontrado: " + clienteEncontrado.getNombre() + " " + clienteEncontrado.getApellido());
            } else {
                System.out.println("   No se encontró el cliente con ID: " + primerCliente.getId());
            }
        }
        
        System.out.println("\n" + "=".repeat(50) + "\n");
        
        // 3. Demostrar búsqueda por nombre
        System.out.println("3. BÚSQUEDA POR NOMBRE (ejemplo con 'Juan'):");
        List<Cliente> clientesPorNombre = controller.buscarClientesPorNombre("Juan");
        if (clientesPorNombre != null && !clientesPorNombre.isEmpty()) {
            System.out.println("   Se encontraron " + clientesPorNombre.size() + " cliente(s) con 'Juan' en el nombre:");
            for (Cliente cliente : clientesPorNombre) {
                System.out.println("   - " + cliente.getNombre() + " " + cliente.getApellido());
            }
        } else {
            System.out.println("   No se encontraron clientes con 'Juan' en el nombre.");
        }
        
        System.out.println("\n" + "=".repeat(50) + "\n");
        
        // 4. Demostrar búsqueda por apellido
        System.out.println("4. BÚSQUEDA POR APELLIDO (ejemplo con 'García'):");
        List<Cliente> clientesPorApellido = controller.buscarClientesPorApellido("García");
        if (clientesPorApellido != null && !clientesPorApellido.isEmpty()) {
            System.out.println("   Se encontraron " + clientesPorApellido.size() + " cliente(s) con 'García' en el apellido:");
            for (Cliente cliente : clientesPorApellido) {
                System.out.println("   - " + cliente.getNombre() + " " + cliente.getApellido());
            }
        } else {
            System.out.println("   No se encontraron clientes con 'García' en el apellido.");
        }
        
        System.out.println("\n" + "=".repeat(50) + "\n");
        
        // 5. Demostrar búsqueda general
        System.out.println("5. BÚSQUEDA GENERAL (ejemplo con 'a'):");
        List<Cliente> clientesGenerales = controller.buscarClientesGeneral("a");
        if (clientesGenerales != null && !clientesGenerales.isEmpty()) {
            System.out.println("   Se encontraron " + clientesGenerales.size() + " cliente(s) con 'a' en nombre, apellido o teléfono:");
            for (Cliente cliente : clientesGenerales) {
                System.out.println("   - " + cliente.getNombre() + " " + cliente.getApellido() + " (Tel: " + cliente.getTelefono() + ")");
            }
        } else {
            System.out.println("   No se encontraron clientes con 'a' en nombre, apellido o teléfono.");
        }
        
        System.out.println("\n" + "=".repeat(50));
        System.out.println("DEMOSTRACIÓN COMPLETADA");
        System.out.println("Para usar la interfaz interactiva, ejecute ClienteUI.java");
    }
} 