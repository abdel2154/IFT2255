package com.diro.ift2255.service;

import com.diro.ift2255.model.Avis;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
/**
 * Gère la logique métier associée aux avis étudiants.
 */

public class AvisService {

    // Map sigle de cours -> liste d'avis
    private final Map<String, List<Avis>> avisParCours = new ConcurrentHashMap<>();

    public AvisSummary getAvisForCourse(String courseId) {
        String key = normalizeCourseId(courseId);

        List<Avis> list = avisParCours.getOrDefault(key, Collections.emptyList());

        AvisSummary summary = new AvisSummary();
        summary.setCourseId(key);
        summary.setCount(list.size());
        summary.setAvis(new ArrayList<>(list));

        if (list.size() < 5) {
            summary.setMessage("Pas assez d'avis pour afficher des statistiques fiables (minimum 5).");
            return summary;
        }

        double avgDiff = list.stream().mapToInt(Avis::getDifficulty).average().orElse(0);
        double avgWorkload = list.stream().mapToInt(Avis::getWorkload).average().orElse(0);

        summary.setAvgDifficulty(avgDiff);
        summary.setAvgWorkload(avgWorkload);
        summary.setMessage("Statistiques calculées à partir de " + list.size() + " avis.");

        return summary;
    }

    public Avis addAvis(String courseId, Avis avis) {
        String key = normalizeCourseId(courseId);
        validateAvis(avis);

        avis.setCourseId(key);
        avis.setCreatedAt(System.currentTimeMillis());

        avisParCours
                .computeIfAbsent(key, k -> new ArrayList<>())
                .add(avis);

        return avis;
    }

    // -------- validation interne --------

    private void validateAvis(Avis avis) {
        if (avis.getDifficulty() < 1 || avis.getDifficulty() > 5) {
            throw new IllegalArgumentException("La difficulté doit être entre 1 et 5.");
        }
        if (avis.getWorkload() < 1 || avis.getWorkload() > 5) {
            throw new IllegalArgumentException("La charge de travail doit être entre 1 et 5.");
        }
        if (avis.getComment() != null && avis.getComment().length() > 500) {
            throw new IllegalArgumentException("Le commentaire ne doit pas dépasser 500 caractères.");
        }
    }

    private String normalizeCourseId(String courseId) {
        return courseId == null ? null : courseId.trim().toUpperCase(Locale.ROOT);
    }

    // DTO pour le retour
    public static class AvisSummary {
        private String courseId;
        private int count;
        private Double avgDifficulty;
        private Double avgWorkload;
        private String message;
        private List<Avis> avis;

        public AvisSummary() {}

        public String getCourseId() { return courseId; }
        public void setCourseId(String courseId) { this.courseId = courseId; }

        public int getCount() { return count; }
        public void setCount(int count) { this.count = count; }

        public Double getAvgDifficulty() { return avgDifficulty; }
        public void setAvgDifficulty(Double avgDifficulty) { this.avgDifficulty = avgDifficulty; }

        public Double getAvgWorkload() { return avgWorkload; }
        public void setAvgWorkload(Double avgWorkload) { this.avgWorkload = avgWorkload; }

        public String getMessage() { return message; }
        public void setMessage(String message) { this.message = message; }

        public List<Avis> getAvis() { return avis; }
        public void setAvis(List<Avis> avis) { this.avis = avis; }
    }
}
