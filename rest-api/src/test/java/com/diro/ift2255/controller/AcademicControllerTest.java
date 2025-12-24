package com.diro.ift2255.controller;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.util.*;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.diro.ift2255.model.AcademicStats;
import com.diro.ift2255.service.AcademicService;

import io.javalin.http.Context;

@ExtendWith(MockitoExtension.class)
class AcademicControllerTest {

    @Mock
    private AcademicService mockAcademicService;

    @Mock
    private Context mockContext;

    private AcademicController controller;

    private long testStartTime;

    @BeforeAll
    static void printHeader() {
        System.out.println("\n" + "=".repeat(80));
        System.out.println("AcademicController Tests");
        System.out.println("=".repeat(80));
    }

    @BeforeEach
    void setup(TestInfo testInfo) {
        controller = new AcademicController(mockAcademicService);
        testStartTime = System.currentTimeMillis();

        System.out.println("\nTEST: " + testInfo.getDisplayName());
        System.out.println("    ├─ Method: " + testInfo.getTestMethod().get().getName());
        System.out.println("    ├─ Assertions:");
    }

    @AfterEach
    void tearDown() {
        long duration = System.currentTimeMillis() - testStartTime;
        System.out.println("    └─ Duration: " + duration + " ms");
    }

    /**************************************************************************
     * Tests for getCourseStats method
     *************************************************************************/

    @Test
    @DisplayName("Get course stats should return stats when sigle exists")
    void testGetCourseStatsValidSigle() {
        // ARRANGE
        String sigle = "IFT2255";
        AcademicStats mockStats = new AcademicStats();
        mockStats.setSigle(sigle);
        mockStats.setNom("Génie logiciel");
        mockStats.setMoyenne("A");
        mockStats.setScore(4);
        mockStats.setParticipants(85);

        when(mockContext.pathParam("sigle")).thenReturn(sigle);
        when(mockAcademicService.getStatsBySigle(sigle)).thenReturn(Optional.of(mockStats));

        // ACT
        controller.getCourseStats(mockContext);

        // ASSERT
        try {
            verify(mockAcademicService).getStatsBySigle(sigle);
            OK("Service appelé avec sigle", false);

            verify(mockContext).json(mockStats);
            OK("Retourne stats academiques");
        } catch (AssertionError e) {
            Err(e.getMessage());
            throw e;
        }
    }

    @Test
    @DisplayName("Get course stats should return 404 when sigle not found")
    void testGetCourseStatsNotFound() {
        // ARRANGE
        String sigle = "XXX9999";

        when(mockContext.pathParam("sigle")).thenReturn(sigle);
        when(mockAcademicService.getStatsBySigle(sigle)).thenReturn(Optional.empty());
        when(mockContext.status(404)).thenReturn(mockContext);

        // ACT
        controller.getCourseStats(mockContext);

        // ASSERT
        try {
            verify(mockAcademicService).getStatsBySigle(sigle);
            OK("Service appelé avec sigle", false);

            verify(mockContext).status(404);
            OK("Statut 404 défini", false);

            verify(mockContext).json(argThat(map -> map instanceof Map &&
                    ((Map<?, ?>) map).containsKey("message")));
            OK("Message d'erreur retourné");
        } catch (AssertionError e) {
            Err(e.getMessage());
            throw e;
        }
    }

    @Test
    @DisplayName("Get course stats should return 400 when sigle is empty")
    void testGetCourseStatsEmptySigle() {
        // ARRANGE
        when(mockContext.pathParam("sigle")).thenReturn("");
        when(mockContext.status(400)).thenReturn(mockContext);

        // ACT
        controller.getCourseStats(mockContext);

        // ASSERT
        try {
            verify(mockContext).status(400);
            OK("Statut 400 pour sigle vide", false);

            verify(mockContext).json(argThat(map -> map instanceof Map &&
                    ((Map<?, ?>) map).containsKey("error")));
            OK("Message d'erreur retourné", false);

            verify(mockAcademicService, never()).getStatsBySigle(any());
            OK("Service non appelé");
        } catch (AssertionError e) {
            Err(e.getMessage());
            throw e;
        }
    }

    @Test
    @DisplayName("Get course stats should accept case-insensitive sigle")
    void testGetCourseStatsCaseInsensitive() {
        // ARRANGE
        String sigleLowercase = "ift2255";
        String sigleUppercase = "IFT2255";
        AcademicStats mockStats = new AcademicStats();
        mockStats.setSigle(sigleUppercase);
        mockStats.setNom("Génie logiciel");

        when(mockContext.pathParam("sigle")).thenReturn(sigleLowercase);
        when(mockAcademicService.getStatsBySigle(sigleLowercase)).thenReturn(Optional.of(mockStats));

        // ACT
        controller.getCourseStats(mockContext);

        // ASSERT
        try {
            verify(mockAcademicService).getStatsBySigle(sigleLowercase);
            OK("Service appelé avec sigle lowercase", false);

            verify(mockContext).json(argThat(stats -> 
                stats instanceof AcademicStats && ((AcademicStats)stats).getSigle().equalsIgnoreCase(sigleLowercase)));
            OK("Retourne stats avec sigle case-insensitive");
        } catch (AssertionError e) {
            Err(e.getMessage());
            throw e;
        }
    }

    @Test
    @DisplayName("Get course stats should return 400 when sigle is null")
    void testGetCourseStatsNullSigle() {
        // ARRANGE
        when(mockContext.pathParam("sigle")).thenReturn(null);
        when(mockContext.status(400)).thenReturn(mockContext);

        // ACT
        controller.getCourseStats(mockContext);

        // ASSERT
        try {
            verify(mockContext).status(400);
            OK("Statut 400 pour sigle null", false);

            verify(mockContext).json(argThat(map -> map instanceof Map &&
                    ((Map<?, ?>) map).containsKey("error")));
            OK("Message d'erreur retourné", false);

            verify(mockAcademicService, never()).getStatsBySigle(any());
            OK("Service non appelé");
        } catch (AssertionError e) {
            Err(e.getMessage());
            throw e;
        }
    }

    @AfterAll
    static void printFooter() {
        System.out.println("\n" + "=".repeat(80));
        System.out.println("COMPLETED: AcademicController Tests");
        System.out.println("=".repeat(80) + "\n");
    }

    private void printMessage(String message, boolean isOk, boolean isLast) {
        String symbol = isLast ? "└─" : "├─";
        String status = isOk ? "[PASS]" : "[FAIL]";
        System.out.println("    │   " + symbol + " " + status + " " + message);
    }

    private void OK(String message) {
        printMessage(message, true, true);
    }

    private void OK(String message, boolean isLast) {
        printMessage(message, true, isLast);
    }

    private void Err(String message) {
        printMessage(message, false, true);
    }
}
