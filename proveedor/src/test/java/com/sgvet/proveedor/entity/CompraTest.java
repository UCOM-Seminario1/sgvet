package com.sgvet.proveedor.entity;

import org.junit.Assert;
import org.junit.Test;

import java.time.LocalDate;

public class CompraTest {

    @Test
    public void testConstructorCompleto() {
        int id = 1;
        int proveedorId = 100;
        LocalDate fecha = LocalDate.of(2025, 7, 31);
        double montoTotal = 1500.75;
        String descripcion = "Compra de medicamentos";

        Compra compra = new Compra(id, proveedorId, fecha, montoTotal, descripcion);

        Assert.assertEquals("El ID debe coincidir", id, compra.getId());
        Assert.assertEquals("El proveedor ID debe coincidir", proveedorId, compra.getProveedorId());
        Assert.assertEquals("La fecha debe coincidir", fecha, compra.getFecha());
        Assert.assertEquals("El monto total debe coincidir", montoTotal, compra.getMontoTotal(), 0.01);
        Assert.assertEquals("La descripción debe coincidir", descripcion, compra.getDescripcion());
    }

    @Test
    public void testGettersBasicos() {
        int id = 2;
        int proveedorId = 200;
        LocalDate fecha = LocalDate.now();
        double montoTotal = 2500.50;
        String descripcion = "Compra de alimentos";

        Compra compra = new Compra(id, proveedorId, fecha, montoTotal, descripcion);

        Assert.assertEquals("getId debe devolver el valor correcto", id, compra.getId());
        Assert.assertEquals("getProveedorId debe devolver el valor correcto", proveedorId, compra.getProveedorId());
        Assert.assertEquals("getFecha debe devolver el valor correcto", fecha, compra.getFecha());
        Assert.assertEquals("getMontoTotal debe devolver el valor correcto", montoTotal, compra.getMontoTotal(), 0.01);
        Assert.assertEquals("getDescripcion debe devolver el valor correcto", descripcion, compra.getDescripcion());
    }

    @Test
    public void testSetters() {
        Compra compra = new Compra(0, 0, LocalDate.now(), 0.0, "");

        // Test setId
        int nuevoId = 999;
        compra.setId(nuevoId);
        Assert.assertEquals("setId debe actualizar el valor", nuevoId, compra.getId());

        // Test setProveedorId
        int nuevoProveedorId = 888;
        compra.setProveedorId(nuevoProveedorId);
        Assert.assertEquals("setProveedorId debe actualizar el valor", nuevoProveedorId, compra.getProveedorId());

        // Test setFecha
        LocalDate nuevaFecha = LocalDate.of(2025, 12, 25);
        compra.setFecha(nuevaFecha);
        Assert.assertEquals("setFecha debe actualizar el valor", nuevaFecha, compra.getFecha());

        // Test setMontoTotal
        double nuevoMonto = 3750.25;
        compra.setMontoTotal(nuevoMonto);
        Assert.assertEquals("setMontoTotal debe actualizar el valor", nuevoMonto, compra.getMontoTotal(), 0.01);

        // Test setDescripcion
        String nuevaDescripcion = "Nueva descripción";
        compra.setDescripcion(nuevaDescripcion);
        Assert.assertEquals("setDescripcion debe actualizar el valor", nuevaDescripcion, compra.getDescripcion());
    }

    @Test
    public void testConstructorConFechaActual() {
        LocalDate hoy = LocalDate.now();
        Compra compra = new Compra(1, 1, hoy, 1000.0, "Compra de hoy");

        Assert.assertEquals("La fecha debe ser la de hoy", hoy, compra.getFecha());
    }

    @Test
    public void testMontoConDecimales() {
        double montoConDecimales = 1234.56789;
        Compra compra = new Compra(1, 1, LocalDate.now(), montoConDecimales, "Test decimales");

        Assert.assertEquals("Debe mantener los decimales", montoConDecimales, compra.getMontoTotal(), 0.00001);
    }

    @Test
    public void testMontosCero() {
        Compra compra = new Compra(1, 1, LocalDate.now(), 0.0, "Compra sin costo");
        Assert.assertEquals("Debe aceptar monto cero", 0.0, compra.getMontoTotal(), 0.01);
    }

    @Test
    public void testMontosNegativos() {
        double montoNegativo = -500.0;
        Compra compra = new Compra(1, 1, LocalDate.now(), montoNegativo, "Devolución");
        Assert.assertEquals("Debe aceptar montos negativos", montoNegativo, compra.getMontoTotal(), 0.01);
    }

    @Test
    public void testDescripcionVacia() {
        Compra compra = new Compra(1, 1, LocalDate.now(), 100.0, "");
        Assert.assertEquals("Debe aceptar descripción vacía", "", compra.getDescripcion());
    }

    @Test
    public void testDescripcionNull() {
        Compra compra = new Compra(1, 1, LocalDate.now(), 100.0, null);
        Assert.assertNull("Debe aceptar descripción null", compra.getDescripcion());
    }

    @Test
    public void testDescripcionLarga() {
        String descripcionLarga = "Esta es una descripción muy larga que contiene muchos caracteres para probar que el sistema puede manejar descripciones extensas sin problemas y que todos los getters y setters funcionan correctamente con textos largos.";
        Compra compra = new Compra(1, 1, LocalDate.now(), 100.0, descripcionLarga);
        Assert.assertEquals("Debe manejar descripciones largas", descripcionLarga, compra.getDescripcion());
    }

    @Test
    public void testFechasPasadas() {
        LocalDate fechaPasada = LocalDate.of(2020, 1, 1);
        Compra compra = new Compra(1, 1, fechaPasada, 100.0, "Compra antigua");
        Assert.assertEquals("Debe aceptar fechas pasadas", fechaPasada, compra.getFecha());
    }

    @Test
    public void testFechasFuturas() {
        LocalDate fechaFutura = LocalDate.of(2030, 12, 31);
        Compra compra = new Compra(1, 1, fechaFutura, 100.0, "Compra programada");
        Assert.assertEquals("Debe aceptar fechas futuras", fechaFutura, compra.getFecha());
    }

    @Test
    public void testIdsNegativos() {
        Compra compra = new Compra(-1, -1, LocalDate.now(), 100.0, "Test IDs negativos");
        Assert.assertEquals("Debe aceptar ID negativo", -1, compra.getId());
        Assert.assertEquals("Debe aceptar proveedor ID negativo", -1, compra.getProveedorId());
    }

    @Test
    public void testSetterConFechaNull() {
        Compra compra = new Compra(1, 1, LocalDate.now(), 100.0, "Test");
        compra.setFecha(null);
        Assert.assertNull("Debe aceptar fecha null", compra.getFecha());
    }

    @Test
    public void testMontosGrandes() {
        double montoGrande = 999999.99;
        Compra compra = new Compra(1, 1, LocalDate.now(), montoGrande, "Compra grande");
        Assert.assertEquals("Debe manejar montos grandes", montoGrande, compra.getMontoTotal(), 0.01);
    }

    @Test
    public void testDescripcionConCaracteresEspeciales() {
        String descripcionEspecial = "Compra de medicamentos @ $500 & productos químicos (50% descuento)";
        Compra compra = new Compra(1, 1, LocalDate.now(), 500.0, descripcionEspecial);
        Assert.assertEquals("Debe manejar caracteres especiales", descripcionEspecial, compra.getDescripcion());
    }

    @Test
    public void testActualizacionCompleta() {
        // Crear compra inicial
        Compra compra = new Compra(1, 1, LocalDate.of(2025, 1, 1), 100.0, "Compra inicial");

        // Actualizar todos los campos
        compra.setId(99);
        compra.setProveedorId(99);
        compra.setFecha(LocalDate.of(2025, 12, 31));
        compra.setMontoTotal(9999.99);
        compra.setDescripcion("Compra actualizada");

        // Verificar cambios
        Assert.assertEquals("ID debe estar actualizado", 99, compra.getId());
        Assert.assertEquals("Proveedor ID debe estar actualizado", 99, compra.getProveedorId());
        Assert.assertEquals("Fecha debe estar actualizada", LocalDate.of(2025, 12, 31), compra.getFecha());
        Assert.assertEquals("Monto debe estar actualizado", 9999.99, compra.getMontoTotal(), 0.01);
        Assert.assertEquals("Descripción debe estar actualizada", "Compra actualizada", compra.getDescripcion());
    }
}