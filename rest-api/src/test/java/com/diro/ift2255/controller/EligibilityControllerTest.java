package com.diro.ift2255.controller;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.util.*;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.diro.ift2255.model.EligibilityResult;
import com.diro.ift2255.service.EligibilityService;

import io.javalin.http.Context;

@ExtendWith(MockitoExtension.class)
class EligibilityControllerTest {

    @Mock
    private EligibilityService mockEligibilityService;

    @Mock
    private Context mockContext;

    private EligibilityController controller;

    private long testStartTime;

    @BeforeAll
    static void printHeader() {
        System.out.println("\n" + "=".repeat(80));
        System.out.println("EligibilityController Tests");
        System.out.println("=".repeat(80));
    }

    @BeforeEach
    void setup(TestInfo testInfo) {
        controller = new EligibilityController(mockEligibilityService);
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
     * Tests for checkCourseEligibility method
     *************************************************************************/

    @Test
    @DisplayName("Check eligibility should return eligible true when prerequisites are met")
    void testCheckEligibilityEligibleStudent() {
        // ARRANGE
        String courseId = "IFT2255";
        EligibilityController.EligibilityRequest req = new EligibilityController.EligibilityRequest();
        req.completedCourses = Arrays.asList("IFT1015", "IFT1025");

        EligibilityResult mockResult = new EligibilityResult();
        mockResult.setCourseId(courseId);
        mockResult.setEligible(true);
        mockResult.setMissingPrerequisites(new ArrayList<>());
        mockResult.setCompletedPrerequisites(Arrays.asList("IFT1015", "IFT1025"));

        when(mockContext.pathParam("id")).thenReturn(courseId);
        when(mockContext.bodyAsClass(EligibilityController.EligibilityRequest.class)).thenReturn(req);
        when(mockEligibilityService.checkEligibility(eq(courseId), any())).thenReturn(mockResult);

        // ACT
        controller.checkCourseEligibility(mockContext);

        // ASSERT
        try {
            verify(mockEligibilityService).checkEligibility(courseId, req.completedCourses);
            OK("Service appelé avec courseId et completed courses", false);

            verify(mockContext).json(mockResult);
            OK("Retourne resultat avec eligible=true");
        } catch (AssertionError e) {
            Err(e.getMessage());
            throw e;
        }
    }

    @Test
    @DisplayName("Check eligibility should return eligible false when prerequisites are missing")
    void testCheckEligibilityNotEligibleStudent() {
        // ARRANGE
        String courseId = "IFT2255";
        EligibilityController.EligibilityRequest req = new EligibilityController.EligibilityRequest();
        req.completedCourses = Arrays.asList("IFT1015");

        EligibilityResult mockResult = new EligibilityResult();
        mockResult.setCourseId(courseId);
        mockResult.setEligible(false);
        mockResult.setMissingPrerequisites(Arrays.asList("IFT1025"));
        mockResult.setCompletedPrerequisites(Arrays.asList("IFT1015"));

        when(mockContext.pathParam("id")).thenReturn(courseId);
        when(mockContext.bodyAsClass(EligibilityController.EligibilityRequest.class)).thenReturn(req);
        when(mockEligibilityService.checkEligibility(eq(courseId), any())).thenReturn(mockResult);

        // ACT
        controller.checkCourseEligibility(mockContext);

        // ASSERT
        try {
            verify(mockEligibilityService).checkEligibility(courseId, req.completedCourses);
            OK("Service appelé avec courseId et completed courses", false);

            verify(mockContext).json(argThat(result -> 
                result instanceof EligibilityResult && !((EligibilityResult)result).isEligible()));
            OK("Retourne resultat avec eligible=false et missing prerequisites");
        } catch (AssertionError e) {
            Err(e.getMessage());
            throw e;
        }
    }

    @Test
    @DisplayName("Check eligibility should return 400 when completedCourses list is empty")
    void testCheckEligibilityEmptyList() {
        // ARRANGE
        String courseId = "IFT2255";
        EligibilityController.EligibilityRequest req = new EligibilityController.EligibilityRequest();
        req.completedCourses = new ArrayList<>();

        when(mockContext.pathParam("id")).thenReturn(courseId);
        when(mockContext.bodyAsClass(EligibilityController.EligibilityRequest.class)).thenReturn(req);
        when(mockContext.status(400)).thenReturn(mockContext);

        // ACT
        controller.checkCourseEligibility(mockContext);

        // ASSERT
        try {
            verify(mockContext).status(400);
            OK("Statut 400 pour liste vide", false);

            verify(mockContext).json(argThat(map -> map instanceof Map &&
                    ((Map<?, ?>) map).containsKey("error")));
            OK("Message d'erreur retourné", false);

            verify(mockEligibilityService, never()).checkEligibility(any(), any());
            OK("Service non appelé");
        } catch (AssertionError e) {
            Err(e.getMessage());
            throw e;
        }
    }

    @Test
    @DisplayName("Check eligibility should return 400 when sigle format is invalid")
    void testCheckEligibilityInvalidSigleFormat() {
        // ARRANGE
        String courseId = "IFT2255";
        EligibilityController.EligibilityRequest req = new EligibilityController.EligibilityRequest();
        req.completedCourses = Arrays.asList("INVALID", "IFT1015");

        when(mockContext.pathParam("id")).thenReturn(courseId);
        when(mockContext.bodyAsClass(EligibilityController.EligibilityRequest.class)).thenReturn(req);
        when(mockContext.status(400)).thenReturn(mockContext);

        // ACT
        controller.checkCourseEligibility(mockContext);

        // ASSERT
        try {
            verify(mockContext).status(400);
            OK("Statut 400 pour sigle invalide", false);

            verify(mockContext).json(argThat(map -> map instanceof Map &&
                    ((Map<?, ?>) map).containsKey("error")));
            OK("Message d'erreur retourné", false);

            verify(mockEligibilityService, never()).checkEligibility(any(), any());
            OK("Service non appelé");
        } catch (AssertionError e) {
            Err(e.getMessage());
            throw e;
        }
    }

    @Test
    @DisplayName("Check eligibility should return 400 when completedCourses exceeds 100 items")
    void testCheckEligibilityListTooLarge() {
        // ARRANGE
        String courseId = "IFT2255";
        EligibilityController.EligibilityRequest req = new EligibilityController.EligibilityRequest();
        
        // Crée une liste avec 101 éléments valides
        List<String> largeCourseList = new ArrayList<>();
        for (int i = 0; i < 101; i++) {
            largeCourseList.add("IFT" + (1000 + i));
        }
        req.completedCourses = largeCourseList;

        when(mockContext.pathParam("id")).thenReturn(courseId);
        when(mockContext.bodyAsClass(EligibilityController.EligibilityRequest.class)).thenReturn(req);
        when(mockContext.status(400)).thenReturn(mockContext);

        // ACT
        controller.checkCourseEligibility(mockContext);

        // ASSERT
        try {
            verify(mockContext).status(400);
            OK("Statut 400 pour liste > 100 elements", false);

            verify(mockContext).json(argThat(map -> map instanceof Map &&
                    ((Map<?, ?>) map).containsKey("error")));
            OK("Message d'erreur retourné", false);

            verify(mockEligibilityService, never()).checkEligibility(any(), any());
            OK("Service non appelé");
        } catch (AssertionError e) {
            Err(e.getMessage());
            throw e;
        }
    }

    @AfterAll
    static void printFooter() {
        System.out.println("\n" + "=".repeat(80));
        System.out.println("COMPLETED: EligibilityController Tests");
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
