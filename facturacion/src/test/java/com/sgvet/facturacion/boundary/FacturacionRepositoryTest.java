package com.sgvet.facturacion.boundary;

import com.sgvet.facturacion.control.FacturacionController;
import com.sgvet.facturacion.entity.Facturacion;
import com.sgvet.facturacion.entity.FiltroBusquedaFactura;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

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
public void testBuscarFacturaPorId() {
    FacturacionRepository repository = new FacturacionRepository();

    FiltroBusquedaFactura filtro = new FiltroBusquedaFactura();
    filtro.setId(2);

    List<Facturacion> resultados = repository.buscar(filtro);
    assertNotNull("La búsqueda no debe retornar null", resultados);
    assertEquals(1, resultados.size()); // ✅ Correcto si size() ya devuelve int
}
}