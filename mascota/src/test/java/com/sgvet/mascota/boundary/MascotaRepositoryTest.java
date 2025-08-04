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
        Mascota mascota = new Mascota(null, "Test", "Apellido", 2, "999999999", 10, "Perro",
            "Boxer");
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

}
