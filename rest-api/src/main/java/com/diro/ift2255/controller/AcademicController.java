


package com.diro.ift2255.controller;

import io.javalin.http.Context;
import com.diro.ift2255.service.AcademicService;
import com.diro.ift2255.util.ResponseUtil;
/**
 * Expose des endpoints REST permettant de consulter
 * les résultats académiques agrégés d’un cours.
 */

public class AcademicController {
    private final AcademicService academicService;

    public AcademicController(AcademicService academicService) {
        this.academicService = academicService;
    }

    /**
     * GET /courses/{sigle}/stats
     */
    /**
     * Récupère les résultats académiques historiques pour un cours donné.
     *
     * Lit les statistiques du fichier CSV local pour le sigle demandé.
     *
     * @param ctx Contexte Javalin contenant le sigle du cours en paramètre de chemin.
     * @return JSON des statistiques académiques ou message d’erreur si le cours n’est pas trouvé.
     */
    public void getCourseStats(Context ctx) {
        String sigle = ctx.pathParam("sigle");
        if (sigle == null || sigle.trim().isEmpty()) {
            ctx.status(400).json(ResponseUtil.formatError("Le paramètre sigle est requis."));
            return;
        }

        academicService.getStatsBySigle(sigle).ifPresentOrElse(stats -> ctx.json(stats),
            () -> ctx.status(404).json(ResponseUtil.formatError("Desole aucune info pour le cour saisi")));
    }
}
