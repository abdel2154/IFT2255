package com.diro.ift2255.service;

import com.diro.ift2255.model.Course;
import com.diro.ift2255.model.EligibilityResult;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class EligibilityService {
    private final CourseService courseService;

    public EligibilityService(CourseService courseService) {
        this.courseService = courseService;
    }

    /**
     * Vérifie l'éligibilité d'un étudiant à un cours donné.
     * @param courseId ID du cours (ex: IFT2255)
     * @param completedCourses Liste des cours complétés par l'étudiant
     * @return EligibilityResult avec détails
     */
    public EligibilityResult checkEligibility(String courseId, List<String> completedCourses) {
        if (completedCourses == null) {
            completedCourses = new ArrayList<>();
        }

        // Récupérer les prérequis du cours
        List<Course> prerequisites = courseService.getPrerequisites(courseId);
        
        if (prerequisites == null || prerequisites.isEmpty()) {
            // Pas de prérequis = éligible
            return new EligibilityResult(courseId, courseId, true, new ArrayList<>(), new ArrayList<>());
        }

        // Extraire les IDs des prérequis
        List<String> requiredCourseIds = new ArrayList<>();
        for (Course prereq : prerequisites) {
            requiredCourseIds.add(prereq.getId().toUpperCase());
        }

        // Convertir completedCourses en majuscules pour comparaison
        Set<String> completedUppercase = new HashSet<>();
        for (String course : completedCourses) {
            completedUppercase.add(course.toUpperCase());
        }

        // Identifier les prérequis manquants et complétés
        List<String> missingPrerequisites = new ArrayList<>();
        List<String> completedPrerequisites = new ArrayList<>();

        for (String requiredId : requiredCourseIds) {
            if (completedUppercase.contains(requiredId)) {
                completedPrerequisites.add(requiredId);
            } else {
                missingPrerequisites.add(requiredId);
            }
        }

        boolean eligible = missingPrerequisites.isEmpty();
        String courseName = prerequisites.get(0).getName(); // Nom du premier prérequis (approximatif)

        EligibilityResult result = new EligibilityResult(courseId, courseName, eligible,
                missingPrerequisites, completedPrerequisites);

        if (!eligible) {
            result.setMessage("Prérequis manquants: " + String.join(", ", missingPrerequisites));
        }

        return result;
    }
}
