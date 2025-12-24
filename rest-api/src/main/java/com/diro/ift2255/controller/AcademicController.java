package com.diro.ift2255.controller;

import io.javalin.http.Context;
import com.diro.ift2255.service.AcademicService;
import com.diro.ift2255.util.ResponseUtil;

public class AcademicController {
    private final AcademicService academicService;

    public AcademicController(AcademicService academicService) {
        this.academicService = academicService;
    }

    /**
     * GET /courses/{sigle}/stats
     */
    public void getCourseStats(Context ctx) {
        String sigle = ctx.pathParam("sigle");
        if (sigle == null || sigle.trim().isEmpty()) {
            ctx.status(400).json(ResponseUtil.formatError("Le paramètre sigle est requis."));
            return;
        }

        academicService.getStatsBySigle(sigle).ifPresentOrElse(stats -> ctx.json(stats),
            () -> ctx.status(404).json(ResponseUtil.formatError("Desole aucune info pour le coirs saisi")));
    }
}
