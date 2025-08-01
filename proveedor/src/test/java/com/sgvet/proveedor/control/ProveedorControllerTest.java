package com.sgvet.proveedor.control;

import java.util.List;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import com.sgvet.proveedor.entity.Proveedor;

public class ProveedorControllerTest {

    private static ProveedorController proveedorController;

    @BeforeClass
    public static void setUp() {
        proveedorController = new ProveedorController();
    }

    @Test
    public void testCrearProveedorExitoso() {
        Proveedor nuevoProveedor = new Proveedor();
        nuevoProveedor.setId(200);
        nuevoProveedor.setNombre("Proveedor Controller Test");
        nuevoProveedor.setRazonSocial("Test Controller SRL");
        nuevoProveedor.setTelefono("0981-888888");
        nuevoProveedor.setCorreo("controller@test.com");

        Boolean resultado = proveedorController.crearProveedor(nuevoProveedor);
        Assert.assertNotNull("El resultado no debe ser null", resultado);
        Assert.assertTrue("La creación debe ser exitosa", resultado);
    }

    @Test
    public void testListarProveedores() {
        List<Proveedor> proveedores = proveedorController.listarProveedores();
        Assert.assertNotNull("La lista de proveedores no debe ser null", proveedores);
        Assert.assertTrue("Debe existir al menos un proveedor", proveedores.size() > 0);
    }

    @Test
    public void testListarProveedoresContenido() {
        List<Proveedor> proveedores = proveedorController.listarProveedores();

        for (Proveedor proveedor : proveedores) {
            Assert.assertNotNull("El proveedor no debe ser null", proveedor);
            Assert.assertNotNull("El ID no debe ser null", proveedor.getId());
            Assert.assertNotNull("El nombre no debe ser null", proveedor.getNombre());
        }
    }

    @Test
    public void testBuscarProveedorPorIdExistente() {
        Proveedor proveedor = proveedorController.buscarProveedorPorId(1);
        Assert.assertNotNull("Debe encontrar el proveedor", proveedor);
        Assert.assertEquals("El ID debe coincidir", Integer.valueOf(1), proveedor.getId());
    }

    @Test
    public void testBuscarProveedorPorIdNoExistente() {
        Proveedor proveedor = proveedorController.buscarProveedorPorId(999);
        Assert.assertNull("No debe encontrar proveedor inexistente", proveedor);
    }

    @Test
    public void testBuscarProveedorPorIdNull() {
        Proveedor proveedor = proveedorController.buscarProveedorPorId(null);
        Assert.assertNull("Debe manejar ID null correctamente", proveedor);
    }

    @Test
    public void testBuscarProveedoresPorNombreExistente() {
        List<Proveedor> proveedores = proveedorController.buscarProveedoresPorNombre("Animalia");
        Assert.assertNotNull("La lista no debe ser null", proveedores);

        if (proveedores.size() > 0) {
            boolean encontrado = false;
            for (Proveedor p : proveedores) {
                if (p.getNombre().toUpperCase().contains("ANIMALIA")) {
                    encontrado = true;
                    break;
                }
            }
            Assert.assertTrue("Debe encontrar proveedores con el nombre buscado", encontrado);
        }
    }

    @Test
    public void testBuscarProveedoresPorNombreVacio() {
        List<Proveedor> proveedores = proveedorController.buscarProveedoresPorNombre("");
        Assert.assertNotNull("La lista no debe ser null", proveedores);
    }

    @Test
    public void testBuscarProveedoresPorNombreNull() {
        List<Proveedor> proveedores = proveedorController.buscarProveedoresPorNombre(null);
        Assert.assertNotNull("La lista no debe ser null", proveedores);
    }

    @Test
    public void testEditarProveedorExistente() {
        // Buscar un proveedor existente
        Proveedor proveedorOriginal = proveedorController.buscarProveedorPorId(1);
        Assert.assertNotNull("Debe existir el proveedor", proveedorOriginal);

        // Crear versión modificada
        Proveedor proveedorModificado = new Proveedor(
                proveedorOriginal.getId(),
                "Nombre Editado Test",
                proveedorOriginal.getRazonSocial(),
                proveedorOriginal.getTelefono(),
                proveedorOriginal.getCorreo()
        );

        Boolean resultado = proveedorController.editarProveedor(proveedorModificado);
        Assert.assertNotNull("El resultado no debe ser null", resultado);
        Assert.assertTrue("La edición debe ser exitosa", resultado);
    }

    @Test
    public void testEditarProveedorNoExistente() {
        Proveedor proveedorInexistente = new Proveedor(
                999,
                "Proveedor Inexistente",
                "Inexistente SRL",
                "0000-000000",
                "inexistente@test.com"
        );

        Boolean resultado = proveedorController.editarProveedor(proveedorInexistente);
        Assert.assertNotNull("El resultado no debe ser null", resultado);
        Assert.assertFalse("La edición debe fallar para proveedor inexistente", resultado);
    }

    @Test
    public void testValidarDatosProveedorValido() {
        Proveedor proveedorValido = new Proveedor(
                1,
                "Proveedor Válido",
                "Válido SRL",
                "0981-123456",
                "valido@proveedor.com"
        );

        boolean resultado = proveedorController.validarDatosProveedor(proveedorValido);
        Assert.assertTrue("Los datos válidos deben pasar la validación", resultado);
    }

    @Test
    public void testValidarDatosProveedorInvalido() {
        Proveedor proveedorInvalido = new Proveedor(
                1,
                "", // Nombre vacío (inválido)
                "",  // Razón social vacía (inválido)
                "telefono-invalido",
                "correo-invalido"
        );

        boolean resultado = proveedorController.validarDatosProveedor(proveedorInvalido);
        Assert.assertFalse("Los datos inválidos deben fallar la validación", resultado);
    }

    @Test
    public void testEditarProveedorMejoradoExitoso() {
        // Buscar un proveedor existente
        Proveedor proveedorOriginal = proveedorController.buscarProveedorPorId(1);
        Assert.assertNotNull("Debe existir el proveedor", proveedorOriginal);

        // Crear versión con datos válidos
        Proveedor proveedorMejorado = new Proveedor(
                proveedorOriginal.getId(),
                "Proveedor Mejorado",
                "Mejorado SRL",
                "0981-111111",
                "mejorado@proveedor.com"
        );

        Boolean resultado = proveedorController.editarProveedorMejorado(proveedorMejorado);
        Assert.assertNotNull("El resultado no debe ser null", resultado);
    }

    @Test
    public void testRegistrarProveedor() {
        Proveedor nuevoProveedor = new Proveedor();
        nuevoProveedor.setId(300);
        nuevoProveedor.setNombre("Proveedor Registrado");
        nuevoProveedor.setRazonSocial("Registrado SRL");
        nuevoProveedor.setTelefono("0981-777777");
        nuevoProveedor.setCorreo("registrado@test.com");

        Proveedor resultado = proveedorController.registrarProveedor(nuevoProveedor);
        Assert.assertNotNull("El proveedor registrado no debe ser null", resultado);
        Assert.assertEquals("Los IDs deben coincidir", nuevoProveedor.getId(), resultado.getId());
        Assert.assertEquals("Los nombres deben coincidir", nuevoProveedor.getNombre(), resultado.getNombre());
    }

    @Test
    public void testEditarProveedor() {
        Proveedor proveedorMock = new Proveedor(1, "Proveedor Test", "Test SRL", "123456", "test@test.com");
        Boolean resultado = proveedorController.editarProveedor(proveedorMock);
        Assert.assertNotNull("El resultado no debe ser null", resultado);
        Assert.assertTrue("La edición debe ser exitosa", resultado);
    }

    @Test
    public void testEditarProveedorMejorado() {
        Proveedor proveedorMock = new Proveedor(1, "Proveedor Mejorado", "Mejorado SRL", "654321", "mejorado@test.com");
        Boolean resultado = proveedorController.editarProveedorMejorado(proveedorMock);
        Assert.assertNotNull("El resultado no debe ser null", resultado);
        Assert.assertTrue("La edición mejorada debe ser exitosa", resultado);
    }
}