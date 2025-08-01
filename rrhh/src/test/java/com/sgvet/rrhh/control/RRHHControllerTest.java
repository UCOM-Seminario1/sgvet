package com.sgvet.rrhh.control;

import com.sgvet.rrhh.entity.RRHH;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Test unitario para la función actualizarEmpleado del RRHHController
 * @author Equipo de Desarrollo
 */
public class RRHHControllerTest {

    private RRHHController rrhhController;
    private RRHH empleadoExistente;
    private RRHH empleadoActualizado;

    @Before
    public void setUp() {
        rrhhController = new RRHHController();
        
        // Empleado existente para las pruebas
        empleadoExistente = new RRHH(
            1, 
            "Juan", 
            "Pérez", 
            "12345678", 
            "0987654321", 
            "juan.perez@veterinaria.com", 
            "Veterinario", 
            "Cirugía"
        );
        
        // Empleado con datos actualizados
        empleadoActualizado = new RRHH(
            1, 
            "Juan Carlos", 
            "Pérez González", 
            "12345678", 
            "0998765432", 
            "juan.carlos.perez@veterinaria.com", 
            "Veterinario Senior", 
            "Cirugía Cardíaca"
        );
    }

    /**
     * Test: Actualización exitosa de información de empleado
     */
    @Test
    public void testActualizarEmpleadoExitoso() {
        // Arrange: Preparar datos de prueba
        RRHH empleadoParaActualizar = new RRHH(
            1, 
            "María", 
            "García", 
            "87654321", 
            "0991234567", 
            "maria.garcia@veterinaria.com", 
            "Asistente Veterinario", 
            "Clínica General"
        );

        // Act: Ejecutar la función
        boolean resultado = rrhhController.actualizarEmpleado(empleadoParaActualizar);

        // Assert: Verificar el resultado
        assertTrue("La actualización debería ser exitosa", resultado);
    }

    /**
     * Test: Actualización fallida - empleado sin ID
     */
    @Test
    public void testActualizarEmpleadoSinId() {
        // Arrange: Empleado sin ID
        RRHH empleadoSinId = new RRHH(
            null, 
            "Ana", 
            "López", 
            "11223344", 
            "0991112222", 
            "ana.lopez@veterinaria.com", 
            "Veterinario", 
            "Dermatología"
        );

        // Act: Ejecutar la función
        boolean resultado = rrhhController.actualizarEmpleado(empleadoSinId);

        // Assert: Verificar que falla
        assertFalse("La actualización debería fallar sin ID", resultado);
    }

    /**
     * Test: Actualización fallida - empleado con datos inválidos
     */
    @Test
    public void testActualizarEmpleadoConDatosInvalidos() {
        // Arrange: Empleado con datos inválidos (cédula incorrecta)
        RRHH empleadoInvalido = new RRHH(
            1, 
            "Carlos", 
            "Rodríguez", 
            "123", // Cédula muy corta
            "0993334444", 
            "carlos.rodriguez@veterinaria.com", 
            "Veterinario", 
            "Radiología"
        );

        // Act: Ejecutar la función
        boolean resultado = rrhhController.actualizarEmpleado(empleadoInvalido);

        // Assert: Verificar que falla
        assertFalse("La actualización debería fallar con datos inválidos", resultado);
    }

    /**
     * Test: Actualización fallida - empleado inexistente
     */
    @Test
    public void testActualizarEmpleadoInexistente() {
        // Arrange: Empleado con ID que no existe
        RRHH empleadoInexistente = new RRHH(
            999, // ID que no existe
            "Pedro", 
            "Martínez", 
            "55667788", 
            "0995556666", 
            "pedro.martinez@veterinaria.com", 
            "Veterinario", 
            "Oftalmología"
        );

        // Act: Ejecutar la función
        boolean resultado = rrhhController.actualizarEmpleado(empleadoInexistente);

        // Assert: Verificar que falla
        assertFalse("La actualización debería fallar para empleado inexistente", resultado);
    }

    /**
     * Test: Actualización con correo inválido
     */
    @Test
    public void testActualizarEmpleadoConCorreoInvalido() {
        // Arrange: Empleado con correo inválido
        RRHH empleadoCorreoInvalido = new RRHH(
            1, 
            "Laura", 
            "Fernández", 
            "99887766", 
            "0997778888", 
            "correo.invalido", // Correo sin formato válido
            "Veterinario", 
            "Neurología"
        );

        // Act: Ejecutar la función
        boolean resultado = rrhhController.actualizarEmpleado(empleadoCorreoInvalido);

        // Assert: Verificar que falla
        assertFalse("La actualización debería fallar con correo inválido", resultado);
    }

    /**
     * Test: Actualización con teléfono inválido
     */
    @Test
    public void testActualizarEmpleadoConTelefonoInvalido() {
        // Arrange: Empleado con teléfono inválido
        RRHH empleadoTelefonoInvalido = new RRHH(
            1, 
            "Roberto", 
            "Silva", 
            "11223344", 
            "abc123", // Teléfono con letras
            "roberto.silva@veterinaria.com", 
            "Veterinario", 
            "Cardiología"
        );

        // Act: Ejecutar la función
        boolean resultado = rrhhController.actualizarEmpleado(empleadoTelefonoInvalido);

        // Assert: Verificar que falla
        assertFalse("La actualización debería fallar con teléfono inválido", resultado);
    }

    /**
     * Test: Actualización con campos obligatorios vacíos
     */
    @Test
    public void testActualizarEmpleadoConCamposVacios() {
        // Arrange: Empleado con campos obligatorios vacíos
        RRHH empleadoCamposVacios = new RRHH(
            1, 
            "", // Nombre vacío
            "Vargas", 
            "55443322", 
            "0999998888", 
            "carmen.vargas@veterinaria.com", 
            "Veterinario", 
            "Endocrinología"
        );

        // Act: Ejecutar la función
        boolean resultado = rrhhController.actualizarEmpleado(empleadoCamposVacios);

        // Assert: Verificar que falla
        assertFalse("La actualización debería fallar con campos vacíos", resultado);
    }

    /**
     * Test: Actualización con empleado null
     */
    @Test
    public void testActualizarEmpleadoNull() {
        // Act: Ejecutar la función con null
        boolean resultado = rrhhController.actualizarEmpleado(null);

        // Assert: Verificar que falla
        assertFalse("La actualización debería fallar con empleado null", resultado);
    }

    /**
     * Test: Verificar que se actualizan todos los campos correctamente
     */
    @Test
    public void testActualizarTodosLosCampos() {
        // Arrange: Empleado con todos los campos actualizados
        RRHH empleadoCompleto = new RRHH(
            1, 
            "Dr. Alejandro", 
            "Mendoza Torres", 
            "12345678", 
            "099-123-4567", 
            "alejandro.mendoza@veterinaria.com", 
            "Veterinario Especialista", 
            "Cirugía Ortopédica"
        );

        // Act: Ejecutar la función
        boolean resultado = rrhhController.actualizarEmpleado(empleadoCompleto);

        // Assert: Verificar que es exitoso
        assertTrue("La actualización completa debería ser exitosa", resultado);
    }
} 