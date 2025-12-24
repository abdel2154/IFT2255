package com.diro.ift2255.config;

import io.javalin.Javalin;
import com.diro.ift2255.controller.CourseController;
import com.diro.ift2255.controller.ProgramsController;
import com.diro.ift2255.service.CourseService;
import com.diro.ift2255.service.ComparisonService;
import com.diro.ift2255.util.HttpClientApi;

public class Routes {
    public static void register(Javalin app) {
        // Initialisation des services
        HttpClientApi httpClient = new HttpClientApi();
        CourseService courseService = new CourseService(httpClient);
        ComparisonService comparisonService = new ComparisonService(courseService);
        CourseController courseController = new CourseController(courseService, comparisonService);
        ProgramsController programsController = new ProgramsController(courseService);

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

        // Proxy Planifium - liste de programmes (ex: ?programs_list=117510)
        app.get("/api/v1/programs", programsController::getPrograms);
    }
}