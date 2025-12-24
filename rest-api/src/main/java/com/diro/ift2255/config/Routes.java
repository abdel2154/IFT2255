package com.diro.ift2255.config;

import io.javalin.Javalin;
import com.diro.ift2255.controller.CourseController;
import com.diro.ift2255.controller.ProgramsController;
import com.diro.ift2255.controller.AcademicController;
import com.diro.ift2255.controller.EligibilityController;
import com.diro.ift2255.service.AcademicService;
import com.diro.ift2255.service.CourseService;
import com.diro.ift2255.service.ComparisonService;
import com.diro.ift2255.service.EligibilityService;
import com.diro.ift2255.util.HttpClientApi;

public class Routes {
    public static void register(Javalin app) {
        // Initialisation des services
        HttpClientApi httpClient = new HttpClientApi();
        CourseService courseService = new CourseService(httpClient);
        ComparisonService comparisonService = new ComparisonService(courseService);
        CourseController courseController = new CourseController(courseService, comparisonService);
        ProgramsController programsController = new ProgramsController(courseService);
        AcademicService academicService = new AcademicService();
        AcademicController academicController = new AcademicController(academicService);
        EligibilityService eligibilityService = new EligibilityService(courseService);
        EligibilityController eligibilityController = new EligibilityController(eligibilityService);

        // Routes
        app.get("/", ctx -> ctx.result("API de choix de cours - UdeM"));

        // CU09 - Recherche de cours
        app.get("/courses", courseController::getAllCourses);

        // CU10 - Voir les détails d'un cours
        app.get("/courses/{id}", courseController::getCourseById);

        // CUxx - Voir les cours offerts d'un programme
        app.get("/programs/{code}/courses", courseController::getCoursesByProgram);

        // CU11 - Comparer des cours
        app.post("/courses/compare", courseController::compareCourses);

        // CUxx - Voir les cours offerts d'un trimestre
        app.get("/semesters/{semester}/courses", courseController::getCoursesBySemester);

        // Résultats académiques CSV
        app.get("/courses/{sigle}/stats", academicController::getCourseStats);

        // Vérifier l'éligibilité à un cours
        app.post("/courses/{id}/check-eligibility", eligibilityController::checkCourseEligibility);

        // Proxy Planifium - liste de programmes (ex: ?programs_list=117510)
        app.get("/api/v1/programs", programsController::getPrograms);
    }
}