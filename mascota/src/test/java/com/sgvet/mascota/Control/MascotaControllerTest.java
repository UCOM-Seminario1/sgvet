package com.sgvet.mascota.control;

import com.sgvet.mascota.entity.Mascota;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class MascotaControllerTest {

    private MascotaController controller;

    @Before
    public void setUp() {
        controller = new MascotaController();
    }

    @Test
    public void testCrearMascota_valida() {
        Mascota mascota = new Mascota(null, "Luna", "Pérez", 2, "099112233", 1, "Perro", "Labrador");
        boolean resultado = controller.crearMascota(mascota);
        assertTrue("La mascota válida debería insertarse correctamente", resultado);
        assertNotNull("Después de la inserción, el ID no debería ser null", mascota.getId());
    }

    @Test
    public void testCrearMascota_null() {
        boolean resultado = controller.crearMascota(null);
        assertFalse("No debería permitir insertar una mascota null", resultado);
    }

    @Test
    public void testCrearMascota_datosInvalidos() {
        Mascota mascota = new Mascota(null, "", "", -1, "", -99, "", "");
        boolean resultado = controller.crearMascota(mascota);
        // Si no hay validaciones, puede que retorne true
        assertTrue("Debería manejar mascotas con datos inválidos sin lanzar excepción", resultado);
    }
}
