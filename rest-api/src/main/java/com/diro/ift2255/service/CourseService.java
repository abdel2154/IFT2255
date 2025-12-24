package com.diro.ift2255.service;

import com.diro.ift2255.model.Course;
import com.diro.ift2255.util.HttpClientApi;
import com.fasterxml.jackson.core.type.TypeReference;
import java.net.URI;
import java.util.*;

public class CourseService {
    private final HttpClientApi clientApi;
    private static final String BASE_URL = "https://planifium-api.onrender.com/api/v1/courses";
    private static final String BASE_PROGRAMS_URL = "https://planifium-api.onrender.com/api/v1/programs";

    public CourseService(HttpClientApi clientApi) {
        this.clientApi = clientApi;
    }

    /**
     * Fetch all courses with search criteria
     */
    public List<Course> getAllCourses(Map<String, String> queryParams) {
        Map<String, String> params = (queryParams == null) ? Collections.emptyMap() : queryParams;
        URI uri = HttpClientApi.buildUri(BASE_URL, params);

        try {
            List<Course> courses = clientApi.get(uri, new TypeReference<List<Course>>() {});
            return (courses != null) ? courses : new ArrayList<>();
        } catch (RuntimeException e) {
            // Cas d'erreur du diagramme de séquence: API indisponible
            System.err.println("Erreur API Planifium: " + e.getMessage());
            // Retourner une liste vide avec un message d'erreur
            return new ArrayList<>();
        }
    }

    /**
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
     * Appelle l'endpoint Planifium /api/v1/programs en renvoyant le JSON reçu
     * Ex : ?programs_list=117510[&include_courses_detail=true]
     */
    public Map<String, Object> getPrograms(Map<String, String> queryParams) {
        Map<String, String> params = (queryParams == null) ? Collections.emptyMap() : queryParams;
        URI uri = HttpClientApi.buildUri(BASE_PROGRAMS_URL, params);

        try {
            Map<String, Object> response = clientApi.get(uri, new TypeReference<Map<String, Object>>() {});
            return (response != null) ? response : new HashMap<>();
        } catch (RuntimeException e) {
            System.err.println("Erreur API Planifium (programs): " + e.getMessage());
            return new HashMap<>();
        }
    }
}