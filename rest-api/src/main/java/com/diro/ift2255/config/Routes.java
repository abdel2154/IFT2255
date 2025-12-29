package com.diro.ift2255.config;

import io.javalin.Javalin;
import com.diro.ift2255.controller.CourseController;
import com.diro.ift2255.controller.ProgramsController;
import com.diro.ift2255.controller.AcademicController;
import com.diro.ift2255.controller.EligibilityController;
import com.diro.ift2255.controller.AvisController;
import com.diro.ift2255.controller.CourseSetController;
import com.diro.ift2255.service.AcademicService;
import com.diro.ift2255.service.CourseService;
import com.diro.ift2255.service.ComparisonService;
import com.diro.ift2255.service.EligibilityService;
import com.diro.ift2255.service.AvisService;
import com.diro.ift2255.service.CourseSetService;
import com.diro.ift2255.util.HttpClientApi;



public class Routes {
    public static void register(Javalin app) {
        HttpClientApi httpClient = new HttpClientApi();

        CourseService courseService = new CourseService(httpClient);
        ComparisonService comparisonService = new ComparisonService(courseService);
        CourseController courseController = new CourseController(courseService, comparisonService);

        ProgramsController programsController = new ProgramsController(courseService);

        AcademicService academicService = new AcademicService();
        AcademicController academicController = new AcademicController(academicService);

        EligibilityService eligibilityService = new EligibilityService(courseService);
        EligibilityController eligibilityController = new EligibilityController(eligibilityService);

        AvisService avisService = new AvisService();
        AvisController avisController = new AvisController(avisService);


        CourseSetService courseSetService = new CourseSetService(courseService);
        CourseSetController courseSetController = new CourseSetController(courseSetService);



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

        // Voir l'horaire d'un cours pour un trimestre donné
        app.get("/courses/{id}/schedule", courseController::getCourseSchedule);


        // CUxx - Voir les cours offerts d'un trimestre
        app.get("/semesters/{semester}/courses", courseController::getCoursesBySemester);

        // Résultats académiques CSV
        app.get("/courses/{sigle}/stats", academicController::getCourseStats);

        // Vérifier l'éligibilité à un cours
        app.post("/courses/{id}/check-eligibility", eligibilityController::checkCourseEligibility);

        // Proxy Planifium - liste de programmes (ex: ?programs_list=117510)
        app.get("/api/v1/programs", programsController::getPrograms);

        // Avis étudiants
        app.get("/courses/{id}/avis", avisController::getAvisForCourse);
        app.post("/courses/{id}/avis", avisController::createAvis);

        // Créer un ensemble de cours
        app.post("/course-sets", courseSetController::createCourseSet);

        // Lister tous les ensembles
        app.get("/course-sets", courseSetController::getAllCourseSets);

        // Voir un ensemble précis
        app.get("/course-sets/{id}", courseSetController::getCourseSetById);


    }
}