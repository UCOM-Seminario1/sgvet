package com.sgvet.facturacion.boundary;

import com.sgvet.facturacion.control.FacturacionController;
import com.sgvet.facturacion.entity.Facturacion;
import org.junit.Test;

import static org.junit.Assert.*;

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
}