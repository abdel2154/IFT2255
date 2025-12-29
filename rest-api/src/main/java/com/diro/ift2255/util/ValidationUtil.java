package com.diro.ift2255.util;

import java.util.regex.Pattern;
/**
 * Fournit des méthodes utilitaires pour valider
 * les données d’entrée.
 */

public class ValidationUtil {
    private static final Pattern EMAIL_PATTERN =
            Pattern.compile("^[\\w._%+-]+@[\\w.-]+\\.[A-Za-z]{2,}$");

    public static boolean isEmail(String email) {
        return email != null && EMAIL_PATTERN.matcher(email).matches();
    }
}
