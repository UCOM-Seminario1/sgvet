package com.sgvet.proveedor.util;

import org.junit.jupiter.Assert;
import org.junit.jupiter.api.Test;

public class ProveedorValidadorTest {

    // Tests para validarEmail
    @Test
    public void testValidarEmailValido() {
        Assert.assertTrue("Email válido debe pasar", ProveedorValidador.validarEmail("test@ejemplo.com"));
        Assert.assertTrue("Email con subddominio debe pasar", ProveedorValidador.validarEmail("usuario@mail.empresa.com"));
        Assert.assertTrue("Email con números debe pasar", ProveedorValidador.validarEmail("user123@test.com"));
        Assert.assertTrue("Email con guión debe pasar", ProveedorValidador.validarEmail("test-user@ejemplo.com"));
    }

    @Test
    public void testValidarEmailInvalido() {
        Assert.assertFalse("Email sin @ debe fallar", ProveedorValidador.validarEmail("testejemplo.com"));
        Assert.assertFalse("Email sin dominio debe fallar", ProveedorValidador.validarEmail("test@"));
        Assert.assertFalse("Email sin usuario debe fallar", ProveedorValidador.validarEmail("@ejemplo.com"));
        Assert.assertFalse("Email vacío debe fallar", ProveedorValidador.validarEmail(""));
        Assert.assertFalse("Email null debe fallar", ProveedorValidador.validarEmail(null));
        Assert.assertFalse("Email con espacios debe fallar", ProveedorValidador.validarEmail("test @ejemplo.com"));
    }

    // Tests para validarTelefono
    @Test
    public void testValidarTelefonoValido() {
        Assert.assertTrue("Teléfono válido debe pasar", ProveedorValidador.validarTelefono("0981-123456"));
        Assert.assertTrue("Teléfono con paréntesis debe pasar", ProveedorValidador.validarTelefono("(0981) 123456"));
        Assert.assertTrue("Teléfono solo números debe pasar", ProveedorValidador.validarTelefono("0981123456"));
        Assert.assertTrue("Teléfono con + debe pasar", ProveedorValidador.validarTelefono("+595981123456"));
        Assert.assertTrue("Teléfono con espacios debe pasar", ProveedorValidador.validarTelefono("0981 123 456"));
    }

    @Test
    public void testValidarTelefonoInvalido() {
        Assert.assertFalse("Teléfono con letras debe fallar", ProveedorValidador.validarTelefono("0981abc123"));
        Assert.assertFalse("Teléfono muy corto debe fallar", ProveedorValidador.validarTelefono("123"));
        Assert.assertFalse("Teléfono muy largo debe fallar", ProveedorValidador.validarTelefono("12345678901234567890"));
    }

    @Test
    public void testValidarTelefonoOpcional() {
        Assert.assertTrue("Teléfono vacío debe pasar (opcional)", ProveedorValidador.validarTelefono(""));
        Assert.assertTrue("Teléfono null debe pasar (opcional)", ProveedorValidador.validarTelefono(null));
        Assert.assertTrue("Teléfono solo espacios debe pasar", ProveedorValidador.validarTelefono("   "));
    }

    // Tests para validarCampoObligatorio
    @Test
    public void testValidarCampoObligatorioValido() {
        Assert.assertTrue("Campo con contenido debe pasar", ProveedorValidador.validarCampoObligatorio("Contenido válido", "Campo"));
        Assert.assertTrue("Campo con espacios y contenido debe pasar", ProveedorValidador.validarCampoObligatorio("  Contenido  ", "Campo"));
    }

    @Test
    public void testValidarCampoObligatorioInvalido() {
        Assert.assertFalse("Campo vacío debe fallar", ProveedorValidador.validarCampoObligatorio("", "Campo"));
        Assert.assertFalse("Campo null debe fallar", ProveedorValidador.validarCampoObligatorio(null, "Campo"));
        Assert.assertFalse("Campo solo espacios debe fallar", ProveedorValidador.validarCampoObligatorio("   ", "Campo"));
    }

    // Tests para validarLongitudMaxima
    @Test
    public void testValidarLongitudMaximaValida() {
        Assert.assertTrue("Texto dentro del límite debe pasar", ProveedorValidador.validarLongitudMaxima("Texto", 10, "Campo"));
        Assert.assertTrue("Texto en el límite exacto debe pasar", ProveedorValidador.validarLongitudMaxima("1234567890", 10, "Campo"));
        Assert.assertTrue("Texto null debe pasar", ProveedorValidador.validarLongitudMaxima(null, 10, "Campo"));
    }

    @Test
    public void testValidarLongitudMaximaInvalida() {
        Assert.assertFalse("Texto que excede límite debe fallar", ProveedorValidador.validarLongitudMaxima("Texto muy largo", 5, "Campo"));
    }

    // Tests para limpiarString
    @Test
    public void testLimpiarString() {
        Assert.assertEquals("Debe limpiar espacios extra", "Texto limpio", ProveedorValidador.limpiarString("  Texto   limpio  "));
        Assert.assertEquals("Debe convertir múltiples espacios en uno", "Texto con espacios", ProveedorValidador.limpiarString("Texto    con   espacios"));
        Assert.assertEquals("Debe manejar string vacío", "", ProveedorValidador.limpiarString(""));
        Assert.assertEquals("Debe manejar null", "", ProveedorValidador.limpiarString(null));
        Assert.assertEquals("Debe manejar solo espacios", "", ProveedorValidador.limpiarString("   "));
    }

    // Tests para validarTodosCampos
    @Test
    public void testValidarTodosCamposValidos() {
        boolean resultado = ProveedorValidador.validarTodosCampos(
                "Nombre Válido",
                "Razón Social Válida",
                "0981-123456",
                "test@ejemplo.com"
        );
        Assert.assertTrue("Datos válidos deben pasar", resultado);
    }

    @Test
    public void testValidarTodosCamposNombreInvalido() {
        boolean resultado = ProveedorValidador.validarTodosCampos(
                "", // Nombre vacío
                "Razón Social Válida",
                "0981-123456",
                "test@ejemplo.com"
        );
        Assert.assertFalse("Nombre vacío debe fallar", resultado);
    }

    @Test
    public void testValidarTodosCamposRazonSocialInvalida() {
        boolean resultado = ProveedorValidador.validarTodosCampos(
                "Nombre Válido",
                null, // Razón social null
                "0981-123456",
                "test@ejemplo.com"
        );
        Assert.assertFalse("Razón social null debe fallar", resultado);
    }

    @Test
    public void testValidarTodosCamposTelefonoInvalido() {
        boolean resultado = ProveedorValidador.validarTodosCampos(
                "Nombre Válido",
                "Razón Social Válida",
                "teléfono-inválido",
                "test@ejemplo.com"
        );
        Assert.assertFalse("Teléfono inválido debe fallar", resultado);
    }

    @Test
    public void testValidarTodosCamposCorreoInvalido() {
        boolean resultado = ProveedorValidador.validarTodosCampos(
                "Nombre Válido",
                "Razón Social Válida",
                "0981-123456",
                "correo-inválido"
        );
        Assert.assertFalse("Correo inválido debe fallar", resultado);
    }

    @Test
    public void testValidarTodosCamposConCamposOpcionales() {
        // Teléfono y correo opcionales (vacíos)
        boolean resultado = ProveedorValidador.validarTodosCampos(
                "Nombre Válido",
                "Razón Social Válida",
                "",  // Teléfono vacío (opcional)
                ""   // Correo vacío (opcional)
        );
        Assert.assertTrue("Campos opcionales vacíos deben pasar", resultado);
    }

    @Test
    public void testValidarTodosCamposLongitudExcesiva() {
        String nombreMuyLargo = "N".repeat(60); // Excede los 50 caracteres

        boolean resultado = ProveedorValidador.validarTodosCampos(
                nombreMuyLargo,
                "Razón Social Válida",
                "0981-123456",
                "test@ejemplo.com"
        );
        Assert.assertFalse("Nombre muy largo debe fallar", resultado);
    }

    @Test
    public void testValidarTodosCamposRazonSocialMuyLarga() {
        String razonSocialMuyLarga = "R".repeat(150); // Excede los 100 caracteres

        boolean resultado = ProveedorValidador.validarTodosCampos(
                "Nombre Válido",
                razonSocialMuyLarga,
                "0981-123456",
                "test@ejemplo.com"
        );
        Assert.assertFalse("Razón social muy larga debe fallar", resultado);
    }

    @Test
    public void testValidarEmailesEspeciales() {
        Assert.assertTrue("Email con punto en nombre debe pasar", ProveedorValidador.validarEmail("first.last@ejemplo.com"));
        Assert.assertTrue("Email con + debe pasar", ProveedorValidador.validarEmail("user+tag@ejemplo.com"));
        Assert.assertTrue("Email con _ debe pasar", ProveedorValidador.validarEmail("user_name@ejemplo.com"));
    }

    @Test
    public void testValidarTelefonosInternacionales() {
        Assert.assertTrue("Teléfono con código país debe pasar", ProveedorValidador.validarTelefono("+595 981 123456"));
        Assert.assertTrue("Teléfono formato internacional debe pasar", ProveedorValidador.validarTelefono("+1 (555) 123-4567"));
    }
}