package com.diro.ift2255.config;

import io.javalin.Javalin;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import java.lang.reflect.Method;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class RoutesTest {
    
    @Test
    @DisplayName("La classe Routes doit exister et avoir des méthodes")
    void routesClass_ExistsAndHasMethods() {
        assertNotNull(Routes.class);
        assertTrue(Routes.class.getDeclaredMethods().length > 0);
    }
    
    @Test
    @DisplayName("La méthode register doit exister avec la bonne signature")
    void registerMethod_ExistsAndTakesJavalinParameter() throws Exception {
        Method registerMethod = Routes.class.getDeclaredMethod("register", Javalin.class);
        assertNotNull(registerMethod);
        assertEquals(1, registerMethod.getParameterCount());
        assertEquals(Javalin.class, registerMethod.getParameterTypes()[0]);
    }
    
    @Test
    @DisplayName("La méthode register doit ajouter des routes à l'application Javalin")
    void register_AddsRoutesToApp() {
        Javalin mockApp = mock(Javalin.class);
        Routes.register(mockApp);
        
        verify(mockApp, atLeast(1)).get(anyString(), any());
        verify(mockApp, atLeast(1)).post(anyString(), any());
    }
}