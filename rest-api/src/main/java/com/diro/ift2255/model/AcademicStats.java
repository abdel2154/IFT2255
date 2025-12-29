package com.diro.ift2255.model;

public class AcademicStats {
    private String sigle;
    private String nom;
    private String moyenne; // lettre
    private int score;
    private int participants;
    private int trimestres;

    public AcademicStats() {}

/**
 * Représente les statistiques académiques
 * agrégées associées à un cours.
 */

    public AcademicStats(String sigle, String nom, String moyenne, int score, int participants, int trimestres) {
        this.sigle = sigle;
        this.nom = nom;
        this.moyenne = moyenne;
        this.score = score;
        this.participants = participants;
        this.trimestres = trimestres;
    }

    public String getSigle() { return sigle; }
    public void setSigle(String sigle) { this.sigle = sigle; }

    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }

    public String getMoyenne() { return moyenne; }
    public void setMoyenne(String moyenne) { this.moyenne = moyenne; }

    public int getScore() { return score; }
    public void setScore(int score) { this.score = score; }

    public int getParticipants() { return participants; }
    public void setParticipants(int participants) { this.participants = participants; }

    public int getTrimestres() { return trimestres; }
    public void setTrimestres(int trimestres) { this.trimestres = trimestres; }
}
