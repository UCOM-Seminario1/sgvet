package com.sgvet.proveedor.boundary;

import com.sgvet.proveedor.entity.Proveedor;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThrows;

public class ProveedorRepositoryTest {

    private static ProveedorRepository proveedorRepository;

    @BeforeClass
    public static void setUp() {
        // Inicializar el repositorio
        proveedorRepository = new ProveedorRepository();
    }

    @Test
    public void testListarTodos() {
        List<Proveedor> proveedores = proveedorRepository.listarTodos();
        Assert.assertNotNull("La lista de proveedores no debe ser null", proveedores);
        Assert.assertTrue("Debe existir al menos un proveedor", proveedores.size() > 0);
    }

    @Test
    public void testListarTodosNoVacio() {
        List<Proveedor> proveedores = proveedorRepository.listarTodos();
        for (Proveedor proveedor : proveedores) {
            Assert.assertNotNull("El proveedor no debe ser null", proveedor);
            Assert.assertNotNull("El ID del proveedor no debe ser null", proveedor.getId());
            Assert.assertNotNull("El nombre del proveedor no debe ser null", proveedor.getNombre());
        }
    }

    @Test
    public void testBuscarPorIdExistente() {
        Proveedor proveedor = proveedorRepository.buscarPorId(1);
        Assert.assertNotNull("Debe encontrar el proveedor con ID 1", proveedor);
        Assert.assertEquals("El ID debe ser 1", Integer.valueOf(1), proveedor.getId());
    }

    @Test
    public void testBuscarPorIdNoExistente() {
        Proveedor proveedor = proveedorRepository.buscarPorId(999);
        assertNull("No debe encontrar proveedor con ID inexistente", proveedor);
    }

    @Test
    public void testInsertarProveedor() {
        Proveedor nuevoProveedor = new Proveedor();
        nuevoProveedor.setId(100);
        nuevoProveedor.setNombre("Proveedor Test");
        nuevoProveedor.setRazonSocial("Test SRL");
        nuevoProveedor.setTelefono("0981-999999");
        nuevoProveedor.setCorreo("test@proveedor.com");

        try {
            proveedorRepository.insertar(nuevoProveedor);
            // Si llega aquí, la inserción fue exitosa
            Assert.assertTrue("La inserción debe ser exitosa", true);
        } catch (Exception e) {
            Assert.fail("La inserción no debe fallar: " + e.getMessage());
        }
    }

    @Test
    public void testActualizarProveedorExistente() {
        // Buscar un proveedor existente
        Proveedor proveedorOriginal = proveedorRepository.buscarPorId(1);
        Assert.assertNotNull("Debe existir el proveedor con ID 1", proveedorOriginal);

        // Crear una versión modificada
        Proveedor proveedorModificado = new Proveedor(
                proveedorOriginal.getId(),
                "Nombre Actualizado",
                proveedorOriginal.getRazonSocial(),
                proveedorOriginal.getTelefono(),
                proveedorOriginal.getCorreo()
        );

        boolean resultado = proveedorRepository.actualizar(proveedorModificado);
        Assert.assertTrue("La actualización debe ser exitosa", resultado);
    }

    @Test
    public void testBuscarPorNombre() {
        List<Proveedor> proveedores = proveedorRepository.buscarPorNombre("Animalia");
        Assert.assertNotNull("La lista no debe ser null", proveedores);

        if (proveedores.size() > 0) {
            boolean encontrado = false;
            for (Proveedor p : proveedores) {
                if (p.getNombre().toUpperCase().contains("ANIMALIA")) {
                    encontrado = true;
                    break;
                }
            }
            Assert.assertTrue("Debe encontrar proveedores que contengan 'Animalia'", encontrado);
        }
    }

    @Test
    public void testBuscarPorNombreNoExistente() {
        List<Proveedor> proveedores = proveedorRepository.buscarPorNombre("ProveedorInexistente123");
        Assert.assertNotNull("La lista no debe ser null", proveedores);
        Assert.assertEquals("No debe encontrar proveedores", 0, proveedores.size());
    }

    @Test
    public void testExisteCorreoEnOtroProveedor() {
        // Probar con un correo que existe en otro proveedor
        boolean existe = proveedorRepository.existeCorreoEnOtroProveedor("contacto@animalia.com", 2);
        Assert.assertTrue("Debe detectar que el correo existe en otro proveedor", existe);
    }

    @Test
    public void testNoExisteCorreoEnOtroProveedor() {
        // Probar con un correo que no existe
        boolean existe = proveedorRepository.existeCorreoEnOtroProveedor("correo_inexistente@test.com", 1);
        Assert.assertFalse("No debe detectar el correo como existente", existe);
    }
    @Test
    public void testInsertarProveedorConCamposNulos() {
        Proveedor proveedor = new Proveedor(null, null, null, null, null);
        assertThrows(IllegalArgumentException.class, () -> proveedorRepository.insertar(proveedor));
    }
    @Test
    public void testInsertarProveedorDuplicadoCorreo() {
        Proveedor p1 = new Proveedor(null, "A", "RS", "123", "a@mail.com");
        Proveedor p2 = new Proveedor(null, "B", "RS", "456", "a@mail.com"); // mismo correo

        proveedorRepository.insertar(p1);
        assertThrows(IllegalArgumentException.class, () -> proveedorRepository.insertar(p2));
    }

}