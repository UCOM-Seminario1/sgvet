package com.sgvet.mascota.boundary;

import com.sgvet.mascota.entity.Mascota;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class MascotaRepositoryTest {
    private MascotaRepository repository;

    @Before
    public void setUp() {
        repository = new MascotaRepository();
    }

    @Test
    public void testInsertarYBuscarPorId() {
        Mascota mascota = new Mascota(null, "Test", "Apellido", 2, "999999999", 10, "Perro", "Boxer");
        repository.insertar(mascota);
        assertNotNull(mascota.getId());
        Mascota encontrada = repository.buscarPorId(mascota.getId());
        assertNotNull(encontrada);
        assertEquals("Test", encontrada.getNombre());
    }

    @Test
    public void testListarTodos() {
        List<Mascota> lista = repository.listarTodos();
        assertNotNull(lista);
    }

    @Test
    public void testBuscarPorNombre() {
        Mascota mascota = new Mascota(null, "UnicoNombre", "Apellido", 1, "888888888", 11, "Gato", "Persa");
        repository.insertar(mascota);
        List<Mascota> resultado = repository.buscarPorNombre("UnicoNombre");
        assertFalse(resultado.isEmpty());
        assertEquals("UnicoNombre", resultado.get(0).getNombre());
    }

    @Test
    public void testBuscarPorCliente() {
        int idCliente = 12345;
        Mascota mascota1 = new Mascota(null, "Cliente1", "Apellido", 1, "777777777", idCliente, "Perro", "Pug");
        Mascota mascota2 = new Mascota(null, "Cliente2", "Apellido", 2, "666666666", idCliente, "Gato", "Sphynx");
        repository.insertar(mascota1);
        repository.insertar(mascota2);
        List<Mascota> resultado = repository.buscarPorCliente(idCliente);
        assertTrue(resultado.size() >= 2);
        for (Mascota m : resultado) {
            assertEquals(Integer.valueOf(idCliente), m.getIdCliente());
        }
    }

    @Test
    public void testActualizar() {
        Mascota mascota = new Mascota(null, "Actualizar", "Apellido", 3, "555555555", 12, "Perro", "Dogo");
        repository.insertar(mascota);
        mascota.setNombre("Actualizado");
        mascota.setEdad(4);
        boolean actualizado = repository.actualizar(mascota);
        assertTrue(actualizado);
        Mascota verificada = repository.buscarPorId(mascota.getId());
        assertEquals("Actualizado", verificada.getNombre());
        assertEquals(Integer.valueOf(4), verificada.getEdad());
    }

    @Test
    public void testEliminarPorId() {
        Mascota mascota = new Mascota(null, "Eliminar", "Apellido", 5, "444444444", 13, "Gato", "Bengal");
        repository.insertar(mascota);
        int id = mascota.getId();
        boolean eliminado = repository.eliminarPorId(id);
        assertTrue(eliminado);
        Mascota buscada = repository.buscarPorId(id);
        assertNull(buscada);
    }

    // UNHAPPY PATH TESTS
    @Test
    public void testBuscarPorIdNoExistente() {
        Mascota resultado = repository.buscarPorId(99999);
        assertNull(resultado);
    }

    @Test
    public void testEliminarPorIdNoExistente() {
        boolean resultado = repository.eliminarPorId(99999);
        assertFalse(resultado);
    }

    @Test
    public void testActualizarMascotaNoExistente() {
        Mascota mascotaInexistente = new Mascota(99999, "NoExiste", "Apellido", 1, "111111111", 1, "Perro", "Raza");
        boolean resultado = repository.actualizar(mascotaInexistente);
        assertFalse(resultado);
    }

    @Test
    public void testBuscarPorNombreNoExistente() {
        List<Mascota> resultado = repository.buscarPorNombre("NombreQueNoExiste");
        assertTrue(resultado.isEmpty());
    }

    @Test
    public void testBuscarPorClienteNoExistente() {
        List<Mascota> resultado = repository.buscarPorCliente(99999);
        assertTrue(resultado.isEmpty());
    }

    @Test
    public void testBuscarPorNombreParcial() {
        Mascota mascota = new Mascota(null, "TestParcial", "Apellido", 1, "999999999", 14, "Perro", "Pastor");
        repository.insertar(mascota);
        List<Mascota> resultado = repository.buscarPorNombre("Test");
        assertFalse(resultado.isEmpty());
        assertTrue(resultado.stream().anyMatch(m -> m.getNombre().contains("Test")));
    }

    @Test
    public void testGenerarNuevoIdCuandoTablaVacia() {
        // Crear una nueva instancia para asegurar tabla limpia en este test
        MascotaRepository nuevoRepo = new MascotaRepository();
        Mascota mascota = new Mascota(null, "PrimeroEnTabla", "Apellido", 1, "000000000", 1, "Perro", "Raza");
        nuevoRepo.insertar(mascota);
        assertNotNull(mascota.getId());
        assertTrue(mascota.getId() >= 1);
    }

    @Test
    public void testListarTodosConVariasMascotas() {
        int cantidadInicial = repository.listarTodos().size();

        Mascota mascota1 = new Mascota(null, "Lista1", "Apellido1", 1, "111111111", 17, "Perro", "Raza1");
        Mascota mascota2 = new Mascota(null, "Lista2", "Apellido2", 2, "222222222", 18, "Gato", "Raza2");

        repository.insertar(mascota1);
        repository.insertar(mascota2);

        List<Mascota> lista = repository.listarTodos();
        assertTrue(lista.size() >= cantidadInicial + 2);
    }

    @Test
    public void testCreateMascotaFromResultSetConTodosLosCampos() {
        Mascota mascota = new Mascota(null, "TestCompleto", "ApellidoCompleto", 5, "555555555", 19, "Perro", "RazaCompleta");
        repository.insertar(mascota);

        Mascota encontrada = repository.buscarPorId(mascota.getId());
        assertNotNull(encontrada);
        assertEquals("TestCompleto", encontrada.getNombre());
        assertEquals("ApellidoCompleto", encontrada.getApellido());
        assertEquals(Integer.valueOf(5), encontrada.getEdad());
        assertEquals("555555555", encontrada.getTelefono());
        assertEquals(Integer.valueOf(19), encontrada.getIdCliente());
        assertEquals("Perro", encontrada.getTipoMascota());
        assertEquals("RazaCompleta", encontrada.getRaza());
    }

    @Test
    public void testConstructorVacio() {
        MascotaRepository nuevoRepository = new MascotaRepository();
        assertNotNull(nuevoRepository);
        // Verificar que puede realizar operaciones básicas
        List<Mascota> lista = nuevoRepository.listarTodos();
        assertNotNull(lista);
    }
}