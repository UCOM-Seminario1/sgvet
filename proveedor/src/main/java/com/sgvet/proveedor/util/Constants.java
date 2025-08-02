package com.sgvet.proveedor.util;

/**
 * Constantes para el módulo proveedor
 * Mejora la maintainability eliminando magic numbers y strings
 */
public final class Constants {
    
    // Constructor privado para clase de constantes
    private Constants() {
        throw new UnsupportedOperationException("Clase de constantes - no debe ser instanciada");
    }
    
    // Límites de validación
    public static final int MAX_NOMBRE_LENGTH = 50;
    public static final int MAX_RAZON_SOCIAL_LENGTH = 100;
    public static final int MAX_TELEFONO_LENGTH = 20;
    public static final int MAX_CORREO_LENGTH = 100;
    public static final int MIN_TELEFONO_DIGITS = 7;
    public static final int MAX_TELEFONO_DIGITS = 15;
    
    // Patrones de validación
    public static final String EMAIL_PATTERN = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
    public static final String TELEFONO_PATTERN = "^[\\d\\s\\-\\(\\)\\+]+$";
    
    // Mensajes de error
    public static final String ERROR_CAMPO_OBLIGATORIO = "❌ Error: %s es obligatorio.";
    public static final String ERROR_LONGITUD_MAXIMA = "❌ Error: %s no puede exceder %d caracteres.";
    public static final String ERROR_FORMATO_EMAIL = "❌ Error: El formato del correo electrónico no es válido.";
    public static final String ERROR_FORMATO_TELEFONO = "❌ Error: El formato del teléfono no es válido.";
    public static final String ERROR_CORREO_DUPLICADO = "❌ Error: Ya existe otro proveedor con este correo electrónico.";
    
    // Estados de respuesta
    public static final String OPERACION_EXITOSA = "✅ Operación realizada exitosamente.";
    public static final String OPERACION_CANCELADA = "📋 Operación cancelada.";
    public static final String PROVEEDOR_NO_ENCONTRADO = "❌ Proveedor no encontrado.";
    
    // Configuraciones por defecto
    public static final int DEFAULT_PAGE_SIZE = 10;
    public static final int MAX_SEARCH_RESULTS = 100;
    
    // SQL Queries (para eliminar strings mágicos)
    public static final String SELECT_ALL_PROVEEDORES = "SELECT * FROM PROVEEDOR";
    public static final String SELECT_BY_ID = "SELECT * FROM PROVEEDOR WHERE ID = ?";
    public static final String SELECT_BY_NOMBRE = "SELECT * FROM PROVEEDOR WHERE UPPER(NOMBRE) LIKE UPPER(?)";
    public static final String INSERT_PROVEEDOR = "INSERT INTO PROVEEDOR (ID, NOMBRE, RAZONSOCIAL, TELEFONO, CORREO) VALUES (?, ?, ?, ?, ?)";
    public static final String UPDATE_PROVEEDOR = "UPDATE PROVEEDOR SET NOMBRE = ?, RAZONSOCIAL = ?, TELEFONO = ?, CORREO = ? WHERE ID = ?";
    public static final String CHECK_EMAIL_EXISTS = "SELECT COUNT(*) FROM PROVEEDOR WHERE CORREO = ? AND ID != ?";
}