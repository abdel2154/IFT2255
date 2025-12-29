package com.diro.ift2255.model;

import java.util.List;

/**
 * Représente un ensemble de cours choisi par un étudiant.
 */
public class CourseSet {

    private String id;                // Identifiant interne (UUID)
    private String name;              // Nom de l'ensemble (ex: "Session hiver 2026")
    private List<String> courseIds;   // Sigles des cours choisis (IFT2255, IFT2015, etc.)
    private List<Course> courses;     // Détails des cours (facultatif mais pratique à renvoyer)

    private int totalCredits;         // Somme des crédits de l'ensemble
    private int estimatedWorkload;    // Charge estimée (ex: totalCredits * 3 heures/semaine)

    public CourseSet() {}

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getCourseIds() {
        return courseIds;
    }

    public void setCourseIds(List<String> courseIds) {
        this.courseIds = courseIds;
    }

    public List<Course> getCourses() {
        return courses;
    }

    public void setCourses(List<Course> courses) {
        this.courses = courses;
    }

    public int getTotalCredits() {
        return totalCredits;
    }

    public void setTotalCredits(int totalCredits) {
        this.totalCredits = totalCredits;
    }

    public int getEstimatedWorkload() {
        return estimatedWorkload;
    }

    public void setEstimatedWorkload(int estimatedWorkload) {
        this.estimatedWorkload = estimatedWorkload;
    }
}
