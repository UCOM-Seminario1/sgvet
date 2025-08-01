package com.sgvet.facturacion.boundary;

import com.sgvet.facturacion.control.FacturacionController;
import com.sgvet.facturacion.entity.Facturacion;

import main.java.com.sgvet.facturacion.entity.FiltroBusquedaFactura;

import org.junit.Test;

import static org.junit.Assert.*;

import java.util.List;

public class FacturacionRepositoryTest {

    @Test
    public void insertar() {
        FacturacionDbManager instance = FacturacionDbManager.getInstance();
        FacturacionRepository repository = new FacturacionRepository();
        FacturacionController controller = new FacturacionController();
        Facturacion factura = new Facturacion();
        factura.setId(100);
        factura.setCantidad(1);
        factura.setIva(10);
        factura.setImporte("10");
        factura.setTotal("10");
        factura.setDescripcion("Prueba");
        controller.crearFacturacion(factura);

    }
    @Test
public void testBuscarFacturaPorIdExistente() {
    // Insertamos una factura de prueba
    Facturacion factura = new Facturacion();
    factura.setId(998); // Asegurate de usar un ID único para test
    factura.setNombre("BUSCAR_TEST");
    factura.setRazonSocial("Empresa Test");
    factura.setCantidad(2);
    factura.setImporte("50000");
    factura.setIva(5);
    factura.setTotal("52500");
    factura.setDescripcion("Test de búsqueda");

    repository.insertar(factura);

    // Creamos un filtro de búsqueda por ID
    FiltroBusquedaFactura filtro = new FiltroBusquedaFactura(998, null, null);
    List<Facturacion> resultados = repository.buscar(filtro);

    // Validamos el resultado
    assertNotNull("El resultado no debe ser null", resultados);
    assertFalse("La lista no debe estar vacía", resultados.isEmpty());

    Facturacion resultado = resultados.get(0);
    assertEquals("El ID de la factura no coincide", 998, resultado.getId());
    assertEquals("El nombre no coincide", "BUSCAR_TEST", resultado.getNombre());
}

}