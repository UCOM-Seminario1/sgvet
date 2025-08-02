package com.sgvet.proveedor.entity;

import org.junit.Assert;
import org.junit.Test;

public class ProveedorTest {

    @Test
    public void testConstructorVacio() {
        Proveedor proveedor = new Proveedor();
        Assert.assertNotNull("El constructor vacío debe crear una instancia", proveedor);
        Assert.assertNull("El ID debe ser null inicialmente", proveedor.getId());
        Assert.assertNull("El nombre debe ser null inicialmente", proveedor.getNombre());
        Assert.assertNull("La razón social debe ser null inicialmente", proveedor.getRazonSocial());
        Assert.assertNull("El teléfono debe ser null inicialmente", proveedor.getTelefono());
        Assert.assertNull("El correo debe ser null inicialmente", proveedor.getCorreo());
    }

    @Test
    public void testConstructorCompleto() {
        Integer id = 1;
        String nombre = "Proveedor Test";
        String razonSocial = "Test SRL";
        String telefono = "0981-123456";
        String correo = "test@proveedor.com";

        Proveedor proveedor = new Proveedor(id, nombre, razonSocial, telefono, correo);

        Assert.assertEquals("El ID debe coincidir", id, proveedor.getId());
        Assert.assertEquals("El nombre debe coincidir", nombre, proveedor.getNombre());
        Assert.assertEquals("La razón social debe coincidir", razonSocial, proveedor.getRazonSocial());
        Assert.assertEquals("El teléfono debe coincidir", telefono, proveedor.getTelefono());
        Assert.assertEquals("El correo debe coincidir", correo, proveedor.getCorreo());
    }

    @Test
    public void testSettersYGetters() {
        Proveedor proveedor = new Proveedor();

        // Test ID
        Integer id = 123;
        proveedor.setId(id);
        Assert.assertEquals("El getter de ID debe devolver el valor seteado", id, proveedor.getId());

        // Test nombre
        String nombre = "Nuevo Proveedor";
        proveedor.setNombre(nombre);
        Assert.assertEquals("El getter de nombre debe devolver el valor seteado", nombre, proveedor.getNombre());

        // Test razón social
        String razonSocial = "Nueva Empresa SRL";
        proveedor.setRazonSocial(razonSocial);
        Assert.assertEquals("El getter de razón social debe devolver el valor seteado", razonSocial, proveedor.getRazonSocial());

        // Test teléfono
        String telefono = "0984-567890";
        proveedor.setTelefono(telefono);
        Assert.assertEquals("El getter de teléfono debe devolver el valor seteado", telefono, proveedor.getTelefono());

        // Test correo
        String correo = "nuevo@proveedor.com";
        proveedor.setCorreo(correo);
        Assert.assertEquals("El getter de correo debe devolver el valor seteado", correo, proveedor.getCorreo());
    }

    @Test
    public void testSettersConNull() {
        Proveedor proveedor = new Proveedor(1, "Test", "Test SRL", "123456", "test@test.com");

        // Verificar que se pueden setear valores null
        proveedor.setId(null);
        Assert.assertNull("Se debe poder setear ID como null", proveedor.getId());

        proveedor.setNombre(null);
        Assert.assertNull("Se debe poder setear nombre como null", proveedor.getNombre());

        proveedor.setRazonSocial(null);
        Assert.assertNull("Se debe poder setear razón social como null", proveedor.getRazonSocial());

        proveedor.setTelefono(null);
        Assert.assertNull("Se debe poder setear teléfono como null", proveedor.getTelefono());

        proveedor.setCorreo(null);
        Assert.assertNull("Se debe poder setear correo como null", proveedor.getCorreo());
    }

    @Test
    public void testSettersConStringsVacios() {
        Proveedor proveedor = new Proveedor();

        proveedor.setNombre("");
        Assert.assertEquals("Se debe poder setear nombre vacío", "", proveedor.getNombre());

        proveedor.setRazonSocial("");
        Assert.assertEquals("Se debe poder setear razón social vacía", "", proveedor.getRazonSocial());

        proveedor.setTelefono("");
        Assert.assertEquals("Se debe poder setear teléfono vacío", "", proveedor.getTelefono());

        proveedor.setCorreo("");
        Assert.assertEquals("Se debe poder setear correo vacío", "", proveedor.getCorreo());
    }

    @Test
    public void testToString() {
        Proveedor proveedor = new Proveedor(1, "Test Proveedor", "Test SRL", "0981-123456", "test@proveedor.com");
        String resultado = proveedor.toString();

        Assert.assertNotNull("toString no debe devolver null", resultado);
        Assert.assertTrue("toString debe contener el ID", resultado.contains("1"));
        Assert.assertTrue("toString debe contener el nombre", resultado.contains("Test Proveedor"));
        Assert.assertTrue("toString debe contener la razón social", resultado.contains("Test SRL"));
        Assert.assertTrue("toString debe contener el teléfono", resultado.contains("0981-123456"));
        Assert.assertTrue("toString debe contener el correo", resultado.contains("test@proveedor.com"));
    }

    @Test
    public void testToStringConValoresNull() {
        Proveedor proveedor = new Proveedor(null, null, null, null, null);
        String resultado = proveedor.toString();

        Assert.assertNotNull("toString no debe devolver null aunque los campos sean null", resultado);
        // Verificar que no lance excepción con valores null
    }

    @Test
    public void testConstructorConParametrosNull() {
        Proveedor proveedor = new Proveedor(null, null, null, null, null);

        Assert.assertNull("ID null debe mantenerse", proveedor.getId());
        Assert.assertNull("Nombre null debe mantenerse", proveedor.getNombre());
        Assert.assertNull("Razón social null debe mantenerse", proveedor.getRazonSocial());
        Assert.assertNull("Teléfono null debe mantenerse", proveedor.getTelefono());
        Assert.assertNull("Correo null debe mantenerse", proveedor.getCorreo());
    }

    @Test
    public void testConstructorConStringsVacios() {
        Proveedor proveedor = new Proveedor(0, "", "", "", "");

        Assert.assertEquals("ID 0 debe mantenerse", Integer.valueOf(0), proveedor.getId());
        Assert.assertEquals("Nombre vacío debe mantenerse", "", proveedor.getNombre());
        Assert.assertEquals("Razón social vacía debe mantenerse", "", proveedor.getRazonSocial());
        Assert.assertEquals("Teléfono vacío debe mantenerse", "", proveedor.getTelefono());
        Assert.assertEquals("Correo vacío debe mantenerse", "", proveedor.getCorreo());
    }

    @Test
    public void testIdConValoresNegativos() {
        Proveedor proveedor = new Proveedor();
        proveedor.setId(-1);
        Assert.assertEquals("Debe aceptar IDs negativos", Integer.valueOf(-1), proveedor.getId());
    }

    @Test
    public void testCamposConEspacios() {
        Proveedor proveedor = new Proveedor();

        String nombreConEspacios = "  Nombre con espacios  ";
        proveedor.setNombre(nombreConEspacios);
        Assert.assertEquals("Debe mantener espacios en nombre", nombreConEspacios, proveedor.getNombre());

        String correoConEspacios = "  correo@test.com  ";
        proveedor.setCorreo(correoConEspacios);
        Assert.assertEquals("Debe mantener espacios en correo", correoConEspacios, proveedor.getCorreo());
    }

    @Test
    public void testCamposConCaracteresEspeciales() {
        Proveedor proveedor = new Proveedor();

        String nombreEspecial = "Proveedor & Cía.";
        proveedor.setNombre(nombreEspecial);
        Assert.assertEquals("Debe aceptar caracteres especiales en nombre", nombreEspecial, proveedor.getNombre());

        String razonEspecial = "Empresa S.A. & Partners";
        proveedor.setRazonSocial(razonEspecial);
        Assert.assertEquals("Debe aceptar caracteres especiales en razón social", razonEspecial, proveedor.getRazonSocial());
    }
}