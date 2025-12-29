package com.diro.ift2255.util;

import java.util.Map;
/**
 * Fournit des méthodes utilitaires pour formater
 * les réponses de l’API REST.
 */

public class ResponseUtil {
    public static Map<String, String> formatError(String errorMessage) {
        return Map.of("error", errorMessage);
    }
}
