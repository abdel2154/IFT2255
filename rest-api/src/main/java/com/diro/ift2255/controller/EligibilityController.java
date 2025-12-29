package com.diro.ift2255.controller;

import io.javalin.http.Context;
import com.diro.ift2255.service.EligibilityService;
import com.diro.ift2255.util.ResponseUtil;
import java.util.List;
import java.util.Map;

/**
 * Expose des endpoints REST permettant de vérifier
 * l’éligibilité d’un étudiant à un cours.
 */

public class EligibilityController {
    private final EligibilityService eligibilityService;

    public EligibilityController(EligibilityService eligibilityService) {
        this.eligibilityService = eligibilityService;
    }

    /**
     * POST /courses/{id}/check-eligibility
     * Body: { "completedCourses": ["IFT1015", "IFT1025", ...] }
     */
    /**
     * Vérifie l’éligibilité d’un étudiant à un cours donné.
     *
     * Analyse la liste des cours complétés et compare aux prérequis du cours via Planifium.
     *
     * @param ctx Contexte Javalin contenant l’id du cours et le body JSON {"completedCourses": [...]}
     * @return JSON indiquant si l’étudiant est éligible, les prérequis manquants et un message.
     */
    public void checkCourseEligibility(Context ctx) {
        String courseId = ctx.pathParam("id");

        if (courseId == null || courseId.trim().isEmpty()) {
            ctx.status(400).json(ResponseUtil.formatError("Le paramètre courseId est requis."));
            return;
        }

        EligibilityRequest req = ctx.bodyAsClass(EligibilityRequest.class);
        if (req == null || req.completedCourses == null) {
            ctx.status(400).json(ResponseUtil.formatError("Le paramètre completedCourses est requis."));
            return;
        }

        // Validation des sigles fournis
        if (req.completedCourses.size() > 100) {
            ctx.status(400).json(ResponseUtil.formatError("La liste 'completedCourses' est trop longue."));
            return;
        }
        for (String s : req.completedCourses) {
            if (s == null || s.trim().isEmpty()) {
                ctx.status(400).json(ResponseUtil.formatError("Chaque sigle dans 'completedCourses' doit être non vide."));
                return;
            }
            if (!s.trim().matches("(?i)^[A-Z]{2,}\\d{3,4}$")) {
                ctx.status(400).json(ResponseUtil.formatError("Sigle invalide dans 'completedCourses': " + s));
                return;
            }
        }

        ctx.json(eligibilityService.checkEligibility(courseId, req.completedCourses));
    }

    public static class EligibilityRequest {
        public List<String> completedCourses;
    }
}
