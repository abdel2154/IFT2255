package com.diro.ift2255.service;

import com.diro.ift2255.model.Course;
import com.diro.ift2255.util.HttpClientApi;
import com.fasterxml.jackson.core.type.TypeReference;

import java.net.URI;
import java.util.*;
/**
 * Contient la logique métier liée à la recherche
 * et à la consultation des cours.
 */

public class CourseService {

    private final HttpClientApi clientApi;
    private static final String BASE_URL = "https://planifium-api.onrender.com/api/v1/courses";
    private static final String BASE_PROGRAMS_URL = "https://planifium-api.onrender.com/api/v1/programs";

    public CourseService(HttpClientApi clientApi) {
        this.clientApi = clientApi;
    }

    /**
     * CU09 - Recherche de cours
     * Fetch all courses with search criteria
     */
    public List<Course> getAllCourses(Map<String, String> queryParams) {
        Map<String, String> params = (queryParams == null) ? Collections.emptyMap() : queryParams;
        URI uri = HttpClientApi.buildUri(BASE_URL, params);

        try {
            List<Course> courses = clientApi.get(uri, new TypeReference<List<Course>>() {});
            return (courses != null) ? courses : new ArrayList<>();
        } catch (RuntimeException e) {
            System.err.println("Erreur API Planifium (getAllCourses): " + e.getMessage());
            return new ArrayList<>();
        }
    }

    /**
     * CU10 - Voir les détails d'un cours
     * Fetch a course by ID
     */
    public Optional<Course> getCourseById(String courseId) {
        return getCourseById(courseId, null);
    }

    /** Fetch a course by ID with optional query params */
    public Optional<Course> getCourseById(String courseId, Map<String, String> queryParams) {
        Map<String, String> params = (queryParams == null) ? Collections.emptyMap() : queryParams;
        URI uri = HttpClientApi.buildUri(BASE_URL + "/" + courseId, params);

        try {
            Course course = clientApi.get(uri, Course.class);
            return Optional.of(course);
        } catch (RuntimeException e) {
            System.err.println("Cours non trouvé: " + courseId);
            return Optional.empty();
        }
    }

    /**
     * Appelle l'endpoint Planifium /api/v1/programs
     * Ex : ?programs_list=117510[&include_courses_detail=true]
     */
    public Map<String, Object> getPrograms(Map<String, String> queryParams) {
        Map<String, String> params = (queryParams == null) ? Collections.emptyMap() : queryParams;
        URI uri = HttpClientApi.buildUri(BASE_PROGRAMS_URL, params);

        try {
            Map<String, Object> response =
                    clientApi.get(uri, new TypeReference<Map<String, Object>>() {});
            return (response != null) ? response : new HashMap<>();
        } catch (RuntimeException e) {
            System.err.println("Erreur API Planifium (programs): " + e.getMessage());
            return new HashMap<>();
        }
    }

    /**
     * Voir les cours offerts pour un trimestre donné
     */
    public List<Course> getCoursesBySemester(String semester, Map<String, String> optionalParams) {
        Map<String, String> params = new HashMap<>();
        params.put("schedule_semester", semester);
        params.put("include_schedule", "true");

        if (optionalParams != null && optionalParams.containsKey("courses_sigle")) {
            params.put("courses_sigle", optionalParams.get("courses_sigle"));
        }

        URI uri = HttpClientApi.buildUri(BASE_URL, params);

        try {
            List<Course> courses =
                    clientApi.get(uri, new TypeReference<List<Course>>() {});
            return (courses != null) ? courses : new ArrayList<>();
        } catch (RuntimeException e) {
            System.err.println("Erreur API Planifium (courses by semester): " + e.getMessage());
            return new ArrayList<>();
        }
    }

    /**
     * Récupère la chaîne complète des prérequis d'un cours.
     * Appelle: GET /api/v1/courses/{courseId}/prerequisites
     */
    public List<Course> getPrerequisites(String courseId) {
        String prereqUrl = BASE_URL + "/" + courseId + "/prerequisites";
        URI uri = URI.create(prereqUrl);

        try {
            List<Course> prerequisites =
                    clientApi.get(uri, new TypeReference<List<Course>>() {});
            return (prerequisites != null) ? prerequisites : new ArrayList<>();
        } catch (RuntimeException e) {
            System.err.println("Erreur API Planifium (prerequisites): " + e.getMessage());
            return new ArrayList<>();
        }
    }

    /**
     * Voir l'horaire d'un cours pour un trimestre donné.
     */
    public Optional<Course> getCourseSchedule(String courseId, String semester) {
        Map<String, String> params = new HashMap<>();
        params.put("include_schedule", "true");   // on demande les horaires

        if (semester != null && !semester.isBlank()) {
            params.put("schedule_semester", semester.toLowerCase()); // ex: a25
        }

        String pathId = courseId.toLowerCase();

        URI uri = HttpClientApi.buildUri(BASE_URL + "/" + pathId, params);

        try {
            Course course = clientApi.get(uri, Course.class);
            return Optional.of(course);
        } catch (RuntimeException e) {
            System.err.println("Erreur Planifium (getCourseSchedule): " + e.getMessage());
            return Optional.empty();
        }

    }
}

