package com.diro.ift2255.controller;

import com.diro.ift2255.model.Avis;
import com.diro.ift2255.service.AvisService;
import com.diro.ift2255.service.AvisService.AvisSummary;
import com.diro.ift2255.util.ResponseUtil;
import io.javalin.http.Context;
/**
 * Gère les requêtes REST liées aux avis étudiants,
 * incluant la consultation et la soumission d’avis.
 */

public class AvisController {

    private final AvisService avisService;

    public AvisController(AvisService avisService) {
        this.avisService = avisService;
    }

    // GET /courses/{id}/avis
    public void getAvisForCourse(Context ctx) {
        String id = ctx.pathParam("id");

        if (id == null || id.isBlank()) {
            ctx.status(400).json(ResponseUtil.formatError("Le paramètre id n'est pas valide."));
            return;
        }

        AvisSummary summary = avisService.getAvisForCourse(id);
        ctx.json(summary);
    }

    // POST /courses/{id}/avis
    public void createAvis(Context ctx) {
        String id = ctx.pathParam("id");

        if (id == null || id.isBlank()) {
            ctx.status(400).json(ResponseUtil.formatError("Le paramètre id n'est pas valide."));
            return;
        }

        Avis avis;
        try {
            avis = ctx.bodyAsClass(Avis.class);
        } catch (Exception e) {
            ctx.status(400).json(ResponseUtil.formatError("Corps de requête invalide : " + e.getMessage()));
            return;
        }

        try {
            Avis created = avisService.addAvis(id, avis);
            ctx.status(201).json(created);
        } catch (IllegalArgumentException e) {
            // Erreur de validation → 400 au lieu de 500
            ctx.status(400).json(ResponseUtil.formatError(e.getMessage()));
        }
    }
}
