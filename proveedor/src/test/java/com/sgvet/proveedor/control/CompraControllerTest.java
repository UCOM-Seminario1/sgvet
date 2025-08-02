package com.sgvet.proveedor.control;

import com.sgvet.proveedor.entity.Compra;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.time.LocalDate;
import java.util.List;

public class CompraControllerTest {

    private static CompraController compraController;

    @BeforeClass
    public static void setUp() {
        compraController = new CompraController();
    }

    @Test
    public void testRegistrarCompra() {
        LocalDate fecha = LocalDate.now();
        Compra nuevaCompra = new Compra(0, 1, fecha, 1500.50, "Compra de prueba");

        int cantidadAntes = compraController.listarCompras().size();
        compraController.registrarCompra(nuevaCompra);
        int cantidadDespues = compraController.listarCompras().size();

        Assert.assertEquals("Debe incrementar la cantidad de compras", cantidadAntes + 1, cantidadDespues);
        Assert.assertTrue("La compra debe tener un ID asignado", nuevaCompra.getId() > 0);
    }

    @Test
    public void testRegistrarCompraConDatos() {
        LocalDate fecha = LocalDate.of(2025, 7, 31);
        Compra compra = new Compra(0, 2, fecha, 2500.75, "Medicamentos veterinarios");

        compraController.registrarCompra(compra);

        // Verificar que la compra fue registrada
        Compra compraEncontrada = compraController.buscarCompraPorId(compra.getId());
        Assert.assertNotNull("La compra debe ser encontrada", compraEncontrada);
        Assert.assertEquals("El proveedor ID debe coincidir", Integer.valueOf(2), Integer.valueOf(compraEncontrada.getProveedorId()));
        Assert.assertEquals("El monto debe coincidir", 2500.75, compraEncontrada.getMontoTotal(), 0.01);
        Assert.assertEquals("La descripción debe coincidir", "Medicamentos veterinarios", compraEncontrada.getDescripcion());
    }

    @Test
    public void testListarCompras() {
        List<Compra> compras = compraController.listarCompras();
        Assert.assertNotNull("La lista no debe ser null", compras);
    }

    @Test
    public void testListarComprasContenido() {
        // Registrar una compra primero
        Compra compra = new Compra(0, 1, LocalDate.now(), 1000.0, "Test lista");
        compraController.registrarCompra(compra);

        List<Compra> compras = compraController.listarCompras();
        Assert.assertTrue("Debe existir al menos una compra", compras.size() > 0);

        // Verificar que las compras tienen datos válidos
        for (Compra c : compras) {
            Assert.assertNotNull("La compra no debe ser null", c);
            Assert.assertTrue("El ID debe ser mayor a 0", c.getId() > 0);
            Assert.assertTrue("El proveedor ID debe ser mayor a 0", c.getProveedorId() > 0);
            Assert.assertNotNull("La fecha no debe ser null", c.getFecha());
            Assert.assertTrue("El monto debe ser mayor a 0", c.getMontoTotal() > 0);
        }
    }

    @Test
    public void testBuscarCompraPorIdExistente() {
        // Registrar una compra primero
        Compra compra = new Compra(0, 3, LocalDate.now(), 750.25, "Buscar por ID test");
        compraController.registrarCompra(compra);

        Compra compraEncontrada = compraController.buscarCompraPorId(compra.getId());
        Assert.assertNotNull("Debe encontrar la compra", compraEncontrada);
        Assert.assertEquals("Los IDs deben coincidir", compra.getId(), compraEncontrada.getId());
        Assert.assertEquals("Los proveedores deben coincidir", compra.getProveedorId(), compraEncontrada.getProveedorId());
    }

    @Test
    public void testBuscarCompraPorIdNoExistente() {
        Compra compraEncontrada = compraController.buscarCompraPorId(99999);
        Assert.assertNull("No debe encontrar compra con ID inexistente", compraEncontrada);
    }

    @Test
    public void testBuscarCompraPorIdNegativo() {
        Compra compraEncontrada = compraController.buscarCompraPorId(-1);
        Assert.assertNull("No debe encontrar compra con ID negativo", compraEncontrada);
    }

    @Test
    public void testEliminarCompraExistente() {
        // Registrar una compra primero
        Compra compra = new Compra(0, 1, LocalDate.now(), 500.0, "Para eliminar");
        compraController.registrarCompra(compra);

        boolean resultado = compraController.eliminarCompra(compra.getId());
        Assert.assertTrue("La eliminación debe ser exitosa", resultado);

        // Verificar que ya no existe
        Compra compraEliminada = compraController.buscarCompraPorId(compra.getId());
        Assert.assertNull("La compra eliminada no debe encontrarse", compraEliminada);
    }

    @Test
    public void testEliminarCompraNoExistente() {
        boolean resultado = compraController.eliminarCompra(88888);
        Assert.assertFalse("La eliminación debe fallar para ID inexistente", resultado);
    }

    @Test
    public void testEliminarCompraIdNegativo() {
        boolean resultado = compraController.eliminarCompra(-5);
        Assert.assertFalse("La eliminación debe fallar para ID negativo", resultado);
    }

    @Test
    public void testRegistrarMultiplesCompras() {
        int cantidadInicial = compraController.listarCompras().size();

        // Registrar varias compras
        for (int i = 1; i <= 3; i++) {
            Compra compra = new Compra(0, i, LocalDate.now(), i * 100.0, "Compra múltiple " + i);
            compraController.registrarCompra(compra);
        }

        int cantidadFinal = compraController.listarCompras().size();
        Assert.assertEquals("Debe incrementar en 3 compras", cantidadInicial + 3, cantidadFinal);
    }

    @Test
    public void testCompraConMontoDecimal() {
        Compra compra = new Compra(0, 1, LocalDate.now(), 1234.56, "Monto con decimales");
        compraController.registrarCompra(compra);

        Compra compraEncontrada = compraController.buscarCompraPorId(compra.getId());
        Assert.assertNotNull("La compra debe ser encontrada", compraEncontrada);
        Assert.assertEquals("El monto decimal debe conservarse", 1234.56, compraEncontrada.getMontoTotal(), 0.01);
    }

    @Test
    public void testCompraConDescripcionLarga() {
        String descripcionLarga = "Esta es una descripción muy larga para probar que el sistema maneja correctamente las descripciones extensas de las compras realizadas a los proveedores.";
        Compra compra = new Compra(0, 2, LocalDate.now(), 999.99, descripcionLarga);

        compraController.registrarCompra(compra);

        Compra compraEncontrada = compraController.buscarCompraPorId(compra.getId());
        Assert.assertNotNull("La compra debe ser encontrada", compraEncontrada);
        Assert.assertEquals("La descripción larga debe conservarse", descripcionLarga, compraEncontrada.getDescripcion());
    }
}