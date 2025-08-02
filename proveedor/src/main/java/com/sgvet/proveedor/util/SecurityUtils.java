package com.sgvet.proveedor.util;

import java.util.logging.Logger;
import java.util.logging.Level;

/**
 * Utilidades de seguridad para el módulo proveedor
 * Mejora la puntuación de Security y Reliability en SonarQube
 */
public final class SecurityUtils {
    
    private static final Logger LOGGER = Logger.getLogger(SecurityUtils.class.getName());
    
    // Constructor privado para clase utilitaria
    private SecurityUtils() {
        throw new UnsupportedOperationException("Clase utilitaria - no debe ser instanciada");
    }
    
    /**
     * Sanitiza input del usuario para prevenir inyección
     * @param input String a sanitizar
     * @return String sanitizado
     */
    public static String sanitizeInput(String input) {
        if (input == null) {
            return "";
        }
        
        // Remover caracteres peligrosos
        String sanitized = input.replaceAll("[<>\"'%;()&+]", "");
        
        // Limitar longitud
        if (sanitized.length() > 255) {
            sanitized = sanitized.substring(0, 255);
            LOGGER.log(Level.WARNING, "Input truncado por exceder longitud máxima");
        }
        
        return sanitized.trim();
    }
    
    /**
     * Valida que una cadena no contenga SQL peligroso
     * @param input String a validar
     * @return true si es seguro
     */
    public static boolean isSqlSafe(String input) {
        if (input == null || input.isEmpty()) {
            return true;
        }
        
        String upperInput = input.toUpperCase();
        String[] dangerousKeywords = {"DROP", "DELETE", "INSERT", "UPDATE", "SELECT", "UNION", "EXEC"};
        
        for (String keyword : dangerousKeywords) {
            if (upperInput.contains(keyword)) {
                LOGGER.log(Level.SEVERE, "Intento de inyección SQL detectado: {0}", input);
                return false;
            }
        }
        
        return true;
    }
    
    /**
     * Log seguro que no expone información sensible
     * @param message Mensaje a loguear
     * @param sensitiveData Datos sensibles (se enmascaran)
     */
    public static void secureLog(String message, String sensitiveData) {
        String maskedData = maskSensitiveData(sensitiveData);
        LOGGER.log(Level.INFO, "{0}: {1}", new Object[]{message, maskedData});
    }
    
    /**
     * Enmascara datos sensibles
     * @param data Datos a enmascarar
     * @return Datos enmascarados
     */
    private static String maskSensitiveData(String data) {
        if (data == null || data.isEmpty()) {
            return "***";
        }
        
        if (data.length() <= 3) {
            return "***";
        }
        
        return data.substring(0, 2) + "***" + data.substring(data.length() - 1);
    }
}