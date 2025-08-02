package com.sgvet.facturacion.control;

import com.sgvet.facturacion.boundary.FacturacionRepository;
import com.sgvet.facturacion.entity.Facturacion;
import com.sgvet.facturacion.entity.FiltroBusquedaFactura;

import java.util.List;
import java.util.Scanner;

public class FacturacionController {

    private FacturacionRepository repository = new FacturacionRepository();

    public Boolean crearFacturacion(Facturacion facturacion) {
        try {
            repository.insertar(facturacion);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<Facturacion> listarFacturacions() {
        return repository.listarTodos();
    }

    public boolean eliminarFacturacion(int id) {
        return repository.eliminarPorId(id);
    }

    public void buscarFacturasInteractivo() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("🔎 Buscar factura");
        System.out.print("Ingrese ID (opcional): ");
        String idInput = scanner.nextLine();
        Integer id = idInput.isEmpty() ? null : Integer.parseInt(idInput);

        System.out.print("Ingrese nombre (opcional): ");
        String nombre = scanner.nextLine();
        nombre = nombre.isEmpty() ? null : nombre;

        System.out.print("Ingrese razón social (opcional): ");
        String razonSocial = scanner.nextLine();
        razonSocial = razonSocial.isEmpty() ? null : razonSocial;

        FiltroBusquedaFactura filtro = new FiltroBusquedaFactura(id, nombre, razonSocial);
        List<Facturacion> resultados = repository.buscar(filtro);

        if (resultados.isEmpty()) {
            System.out.println("❌ No se encontraron facturas.");
        } else {
            System.out.println("✅ Facturas encontradas:");
            for (Facturacion f : resultados) {
                System.out.println(f);
            }
        }
    }
}
