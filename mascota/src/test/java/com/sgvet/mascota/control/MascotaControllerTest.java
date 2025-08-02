package com.sgvet.mascota.control;

import com.sgvet.mascota.entity.Mascota;
import org.junit.Before;
import org.junit.Test;
import org.junit.After;

import java.util.List;

import static org.junit.Assert.*;

public class MascotaControllerTest {
    private MascotaController controller;

    @Before
    public void setUp() {
        controller = new MascotaController();
    }

    @After
    public void tearDown() {
        // Limpiar datos de prueba si es necesario
    }

    // HAPPY PATH TESTS
    @Test
    public void testCrearMascotaExitoso() {
        Mascota mascota = new Mascota(null, "TestController", "ApellidoController", 3, "123456789", 20, "Perro", "Labrador");
        Boolean resultado = controller.crearMascota(mascota);
        assertTrue(resultado);
        assertNotNull(mascota.getId());
    }

    @Test
    public void testCrearMascotaConIdExistente() {
        Mascota mascota = new Mascota(2000, "TestControllerConId", "Apellido", 2, "987654321", 21, "Gato", "Siames");
        Boolean resultado = controller.crearMascota(mascota);
        assertTrue(resultado);
        assertEquals(Integer.valueOf(2000), mascota.getId());
    }

    @Test
    public void testListarMascotas() {
        // Crear algunas mascotas para asegurar que la lista no esté vacía
        Mascota mascota1 = new Mascota(null, "Lista1", "Apellido1", 1, "111111111", 22, "Perro", "Beagle");
        Mascota mascota2 = new Mascota(null, "Lista2", "Apellido2", 2, "222222222", 23, "Gato", "Persa");

        controller.crearMascota(mascota1);
        controller.crearMascota(mascota2);

        List<Mascota> lista = controller.listarMascotas();
        assertNotNull(lista);
        assertTrue(lista.size() >= 2);
    }

    @Test
    public void testEliminarMascotaExitoso() {
        Mascota mascota = new Mascota(null, "ParaEliminar", "Apellido", 1, "555555555", 24, "Perro", "Pastor");
        controller.crearMascota(mascota);
        int id = mascota.getId();

        boolean eliminado = controller.eliminarMascota(id);
        assertTrue(eliminado);

        // Verificar que ya no existe
        Mascota buscada = controller.buscarMascotaPorId(id);
        assertNull(buscada);
    }

    @Test
    public void testBuscarMascotaPorIdExitoso() {
        Mascota mascota = new Mascota(null, "BuscarPorId", "Apellido", 4, "666666666", 25, "Gato", "Maine Coon");
        controller.crearMascota(mascota);

        Mascota encontrada = controller.buscarMascotaPorId(mascota.getId());
        assertNotNull(encontrada);
        assertEquals("BuscarPorId", encontrada.getNombre());
        assertEquals("Apellido", encontrada.getApellido());
        assertEquals(Integer.valueOf(4), encontrada.getEdad());
    }

    @Test
    public void testBuscarMascotasPorNombreExitoso() {
        Mascota mascota1 = new Mascota(null, "BuscarNombre1", "Apellido1", 1, "777777777", 26, "Perro", "Husky");
        Mascota mascota2 = new Mascota(null, "BuscarNombre2", "Apellido2", 2, "888888888", 27, "Perro", "Golden");

        controller.crearMascota(mascota1);
        controller.crearMascota(mascota2);

        List<Mascota> resultado = controller.buscarMascotasPorNombre("BuscarNombre");
        assertNotNull(resultado);
        assertTrue(resultado.size() >= 2);

        for (Mascota m : resultado) {
            assertTrue(m.getNombre().contains("BuscarNombre"));
        }
    }

    @Test
    public void testBuscarMascotasPorClienteExitoso() {
        int idCliente = 30000;
        Mascota mascota1 = new Mascota(null, "Cliente1", "Apellido1", 1, "111222333", idCliente, "Perro", "Bulldog");
        Mascota mascota2 = new Mascota(null, "Cliente2", "Apellido2", 3, "444555666", idCliente, "Gato", "Bengala");

        controller.crearMascota(mascota1);
        controller.crearMascota(mascota2);

        List<Mascota> resultado = controller.buscarMascotasPorCliente(idCliente);
        assertNotNull(resultado);
        assertTrue(resultado.size() >= 2);

        for (Mascota m : resultado) {
            assertEquals(Integer.valueOf(idCliente), m.getIdCliente());
        }
    }

    @Test
    public void testActualizarMascotaExitoso() {
        Mascota mascota = new Mascota(null, "ParaActualizar", "ApellidoOriginal", 2, "999888777", 28, "Perro", "Dalmata");
        controller.crearMascota(mascota);

        // Actualizar datos
        mascota.setNombre("Actualizada");
        mascota.setApellido("ApellidoNuevo");
        mascota.setEdad(5);
        mascota.setTelefono("111222333");
        mascota.setTipoMascota("Gato");
        mascota.setRaza("Sphynx");

        boolean actualizado = controller.actualizarMascota(mascota);
        assertTrue(actualizado);

        // Verificar cambios
        Mascota verificada = controller.buscarMascotaPorId(mascota.getId());
        assertEquals("Actualizada", verificada.getNombre());
        assertEquals("ApellidoNuevo", verificada.getApellido());
        assertEquals(Integer.valueOf(5), verificada.getEdad());
        assertEquals("111222333", verificada.getTelefono());
        assertEquals("Gato", verificada.getTipoMascota());
        assertEquals("Sphynx", verificada.getRaza());
    }

    // UNHAPPY PATH TESTS
    @Test
    public void testCrearMascotaConExcepcion() {
        // Crear una mascota con datos que puedan causar excepción (ej: nombre muy largo)
        Mascota mascotaProblematica = new Mascota(null, null, "Apellido", 1, "123456789", 29, "Perro", "Raza");
        Boolean resultado = controller.crearMascota(mascotaProblematica);
        // El resultado dependerá de cómo maneje la base de datos los valores null
        assertNotNull(resultado);
    }

    @Test
    public void testEliminarMascotaNoExistente() {
        boolean resultado = controller.eliminarMascota(99999);
        assertFalse(resultado);
    }

    @Test
    public void testBuscarMascotaPorIdNoExistente() {
        Mascota resultado = controller.buscarMascotaPorId(99999);
        assertNull(resultado);
    }

    @Test
    public void testBuscarMascotasPorNombreNoExistente() {
        List<Mascota> resultado = controller.buscarMascotasPorNombre("NombreQueNoExisteEnLaBase");
        assertNotNull(resultado);
        assertTrue(resultado.isEmpty());
    }

    @Test
    public void testBuscarMascotasPorClienteNoExistente() {
        List<Mascota> resultado = controller.buscarMascotasPorCliente(99999);
        assertNotNull(resultado);
        assertTrue(resultado.isEmpty());
    }

    @Test
    public void testActualizarMascotaNoExistente() {
        Mascota mascotaInexistente = new Mascota(99999, "NoExiste", "Apellido", 1, "123456789", 1, "Perro", "Raza");
        boolean resultado = controller.actualizarMascota(mascotaInexistente);
        assertFalse(resultado);
    }

    // @Test
    // public void testBuscarMascotasPorNombreVacio() {
    //     List<Mascota> resultado = controller.buscarMascotasPorNombre("");
    //     assertNotNull(resultado);
    //     // Debería devolver todas las mascotas debido al LIKE '%%'
    // }

    @Test
    public void testBuscarMascotasPorNombreConEspacios() {
        Mascota mascota = new Mascota(null, "Nombre Con Espacios", "Apellido", 1, "123456789", 31, "Perro", "Raza");
        controller.crearMascota(mascota);

        List<Mascota> resultado = controller.buscarMascotasPorNombre("Con Espacios");
        assertNotNull(resultado);
        assertFalse(resultado.isEmpty());
    }

    @Test
    public void testListarMascotasCuandoEstaVacia() {
        // Este test asume que podríamos tener una base de datos vacía
        List<Mascota> lista = controller.listarMascotas();
        assertNotNull(lista);
        // No hacer aserciones sobre el tamaño ya que otros tests pueden haber insertado datos
    }

    @Test
    public void testCrearMultiplesMascotasMismoCliente() {
        int idCliente = 32000;
        Mascota mascota1 = new Mascota(null, "Multi1", "Apellido1", 1, "111111111", idCliente, "Perro", "Raza1");
        Mascota mascota2 = new Mascota(null, "Multi2", "Apellido2", 2, "222222222", idCliente, "Gato", "Raza2");
        Mascota mascota3 = new Mascota(null, "Multi3", "Apellido3", 3, "333333333", idCliente, "Hamster", "Raza3");

        assertTrue(controller.crearMascota(mascota1));
        assertTrue(controller.crearMascota(mascota2));
        assertTrue(controller.crearMascota(mascota3));

        List<Mascota> mascotasDelCliente = controller.buscarMascotasPorCliente(idCliente);
        assertTrue(mascotasDelCliente.size() >= 3);
    }

    // UNHAPPY PATH TESTS PARA VALORES NULOS EN BÚSQUEDAS

    @Test(expected = IllegalArgumentException.class)
    public void testBuscarMascotaPorIdNulo() {
        controller.buscarMascotaPorId(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testBuscarMascotasPorNombreNulo() {
        controller.buscarMascotasPorNombre(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testBuscarMascotasPorClienteNulo() {
        controller.buscarMascotasPorCliente(null);
    }
}
