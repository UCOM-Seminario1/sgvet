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
}

