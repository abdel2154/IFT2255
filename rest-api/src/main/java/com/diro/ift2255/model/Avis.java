package com.diro.ift2255.model;
/**
 * Représente un avis étudiant sur un cours.
 */

public class Avis {
    private String courseId;   // rempli côté serveur
    private int difficulty;    // 1..5
    private int workload;      // 1..5
    private String comment;    // optionnel
    private String author;     // optionnel
    private long createdAt;    // timestamp

    public Avis() {}

    public String getCourseId() { return courseId; }
    public void setCourseId(String courseId) { this.courseId = courseId; }

    public int getDifficulty() { return difficulty; }
    public void setDifficulty(int difficulty) { this.difficulty = difficulty; }

    public int getWorkload() { return workload; }
    public void setWorkload(int workload) { this.workload = workload; }

    public String getComment() { return comment; }
    public void setComment(String comment) { this.comment = comment; }

    public String getAuthor() { return author; }
    public void setAuthor(String author) { this.author = author; }

    public long getCreatedAt() { return createdAt; }
    public void setCreatedAt(long createdAt) { this.createdAt = createdAt; }
}
