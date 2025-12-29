package com.diro.ift2255.service;

import com.diro.ift2255.model.Course;
import com.diro.ift2255.model.CourseSet;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Logique métier pour les ensembles de cours.
 * Stockage en mémoire (Map) pour ce projet.
 */
public class CourseSetService {

    private final CourseService courseService;

    // Stockage en mémoire: id → CourseSet
    private final Map<String, CourseSet> sets = new ConcurrentHashMap<>();

    public CourseSetService(CourseService courseService) {
        this.courseService = courseService;
    }

    /**
     * Crée un nouvel ensemble de cours.
     *
     * @param name      Nom de l'ensemble
     * @param courseIds Sigles des cours (IFT2255, IFT2015, ...)
     * @return l'ensemble créé
     */
    public CourseSet createCourseSet(String name, List<String> courseIds) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Le nom de l'ensemble est obligatoire.");
        }
        if (courseIds == null || courseIds.isEmpty()) {
            throw new IllegalArgumentException("La liste de cours ne peut pas être vide.");
        }

        List<Course> foundCourses = new ArrayList<>();
        int totalCredits = 0;

        for (String id : courseIds) {
            if (id == null || id.isBlank()) {
                continue;
            }
            Optional<Course> courseOpt = courseService.getCourseById(id.trim());
            if (courseOpt.isPresent()) {
                Course c = courseOpt.get();
                foundCourses.add(c);
                totalCredits += c.getCredits();
            }
        }

        if (foundCourses.isEmpty()) {
            throw new IllegalArgumentException("Aucun des cours fournis n'est valide ou trouvable.");
        }

        CourseSet set = new CourseSet();
        set.setId(UUID.randomUUID().toString());
        set.setName(name);
        set.setCourseIds(courseIds);
        set.setCourses(foundCourses);
        set.setTotalCredits(totalCredits);
        set.setEstimatedWorkload(totalCredits * 3); // même logique que ComparisonService

        sets.put(set.getId(), set);
        return set;
    }

    /**
     * Récupère un ensemble par son ID.
     */
    public Optional<CourseSet> getById(String id) {
        if (id == null) {
            return Optional.empty();
        }
        return Optional.ofNullable(sets.get(id));
    }

    /**
     * Optionnel: récupérer tous les ensembles
     */
    public List<CourseSet> getAll() {
        return new ArrayList<>(sets.values());
    }
}
