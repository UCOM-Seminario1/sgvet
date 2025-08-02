package com.sgvet.proveedor.entity;

import org.junit.Before;
import org.junit.Test;
import java.time.LocalDate;

import static org.junit.Assert.*;


public class CompraTestVM {

    private Compra compra;
    private final int ID = 1;
    private final int PROVEEDOR_ID = 100;
    private final LocalDate FECHA = LocalDate.of(2023, 6, 15);
    private final double MONTO_TOTAL = 1250.75;
    private final String DESCRIPCION = "Compra de materiales de oficina";

    @Before
    public void setUp() {
        compra = new Compra(ID, PROVEEDOR_ID, FECHA, MONTO_TOTAL, DESCRIPCION);
    }

    @Test
    public void testConstructorInitializesFieldsCorrectly() {
        assertEquals("El ID no coincide", ID, compra.getId());
        assertEquals("El proveedorId no coincide", PROVEEDOR_ID, compra.getProveedorId());
        assertEquals("La fecha no coincide", FECHA, compra.getFecha());
        assertEquals("El montoTotal no coincide", MONTO_TOTAL, compra.getMontoTotal(), 0.001);
        assertEquals("La descripción no coincide", DESCRIPCION, compra.getDescripcion());
    }

    @Test
    public void testGettersReturnCorrectValues() {
        assertEquals("getId() no retorna el valor esperado", ID, compra.getId());
        assertEquals("getProveedorId() no retorna el valor esperado", PROVEEDOR_ID, compra.getProveedorId());
        assertEquals("getFecha() no retorna el valor esperado", FECHA, compra.getFecha());
        assertEquals("getMontoTotal() no retorna el valor esperado", MONTO_TOTAL, compra.getMontoTotal(), 0.001);
        assertEquals("getDescripcion() no retorna el valor esperado", DESCRIPCION, compra.getDescripcion());
    }

    @Test
    public void testSettersModifyValuesCorrectly() {
        // Nuevos valores para prueba
        int nuevoId = 2;
        int nuevoProveedorId = 200;
        LocalDate nuevaFecha = LocalDate.now();
        double nuevoMonto = 500.50;
        String nuevaDescripcion = "Nueva descripción";

        // Aplicar setters
        compra.setId(nuevoId);
        compra.setProveedorId(nuevoProveedorId);
        compra.setFecha(nuevaFecha);
        compra.setMontoTotal(nuevoMonto);
        compra.setDescripcion(nuevaDescripcion);

        // Verificar cambios
        assertEquals("setId() no modificó correctamente el valor", nuevoId, compra.getId());
        assertEquals("setProveedorId() no modificó correctamente el valor", nuevoProveedorId, compra.getProveedorId());
        assertEquals("setFecha() no modificó correctamente el valor", nuevaFecha, compra.getFecha());
        assertEquals("setMontoTotal() no modificó correctamente el valor", nuevoMonto, compra.getMontoTotal(), 0.001);
        assertEquals("setDescripcion() no modificó correctamente el valor", nuevaDescripcion, compra.getDescripcion());
    }

    @Test
    public void testConstructorWithNegativeAmount() {
        double montoNegativo = -100.50;
        Compra compraNegativa = new Compra(3, 300, LocalDate.now(), montoNegativo, "Devolución");

        assertEquals("El constructor debería aceptar montos negativos",
                montoNegativo, compraNegativa.getMontoTotal(), 0.001);
    }

    @Test
    public void testConstructorWithNullDescription() {
        Compra compraSinDescripcion = new Compra(4, 400, LocalDate.now(), 200.0, null);

        assertNull("El constructor debería aceptar descripción nula",
                compraSinDescripcion.getDescripcion());
    }

    @Test
    public void testSetMontoTotalWithZero() {
        compra.setMontoTotal(0.0);

        assertEquals("setMontoTotal() debería aceptar cero como valor válido",
                0.0, compra.getMontoTotal(), 0.001);
    }

    @Test(expected = NullPointerException.class)
    public void testSetFechaWithNull() {
        compra.setFecha(null);
    }
}