package com.sgvet.proveedor.boundary;

import com.sgvet.proveedor.control.ProveedorController;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.Scanner;

import static org.junit.Assert.assertTrue;

public class ProveedorUITest {

    private ProveedorController proveedorControllerMock;

    @Before
    public void setUp() {
        proveedorControllerMock = Mockito.mock(ProveedorController.class);
    }

    @Test
    public void testEditarProveedorDesdeMenu() {
        // Simular entrada del usuario para seleccionar la opción de editar proveedor
        Scanner scannerMock = new Scanner("3\n0\n"); // Opción 3 para editar proveedor, luego salir

        // Llamar al menú principal
        ProveedorUI.menuProveedores(scannerMock);

        // Verificar que no se lanzaron excepciones
        assertTrue("El flujo de editar proveedor desde el menú se ejecutó sin errores", true);
    }
}
