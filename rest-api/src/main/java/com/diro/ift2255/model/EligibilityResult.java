package com.diro.ift2255.model;

import java.util.List;

public class EligibilityResult {
    private String courseId;
    private String courseName;
    private boolean eligible;
    private List<String> missingPrerequisites;
    private List<String> completedPrerequisites;
    private String message;

    public EligibilityResult() {}

    public EligibilityResult(String courseId, String courseName, boolean eligible,
                             List<String> missingPrerequisites, List<String> completedPrerequisites) {
        this.courseId = courseId;
        this.courseName = courseName;
        this.eligible = eligible;
        this.missingPrerequisites = missingPrerequisites;
        this.completedPrerequisites = completedPrerequisites;
        this.message = eligible ? "Vous êtes éligible!" : "Vous avez des prérequis manquants.";
    }

    public String getCourseId() { return courseId; }
    public void setCourseId(String courseId) { this.courseId = courseId; }

    public String getCourseName() { return courseName; }
    public void setCourseName(String courseName) { this.courseName = courseName; }

    public boolean isEligible() { return eligible; }
    public void setEligible(boolean eligible) { this.eligible = eligible; }

    public List<String> getMissingPrerequisites() { return missingPrerequisites; }
    public void setMissingPrerequisites(List<String> missingPrerequisites) { this.missingPrerequisites = missingPrerequisites; }

    public List<String> getCompletedPrerequisites() { return completedPrerequisites; }
    public void setCompletedPrerequisites(List<String> completedPrerequisites) { this.completedPrerequisites = completedPrerequisites; }

    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }
}
