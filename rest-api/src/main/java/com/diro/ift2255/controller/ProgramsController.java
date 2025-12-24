package com.diro.ift2255.controller;

import io.javalin.http.Context;
import com.diro.ift2255.service.CourseService;
import com.diro.ift2255.util.ResponseUtil;
import java.util.HashMap;
import java.util.Map;

public class ProgramsController {
    private final CourseService courseService;

    public ProgramsController(CourseService courseService) {
        this.courseService = courseService;
    }

    /**
     * Proxy vers Planifium : GET /api/v1/programs?programs_list=...&include_courses_detail=true
     */
    public void getPrograms(Context ctx) {
        Map<String, String> queryParams = new HashMap<>();
        ctx.queryParamMap().forEach((key, values) -> {
            if (!values.isEmpty()) {
                queryParams.put(key, values.get(0));
            }
        });

        if (!queryParams.containsKey("programs_list") || queryParams.get("programs_list").trim().isEmpty()) {
            ctx.status(400).json(ResponseUtil.formatError("Le paramètre 'programs_list' est requis."));
            return;
        }

        // Valider le format: liste d'IDs séparés par des virgules (ex: 117510,117511)
        String programsList = queryParams.get("programs_list").trim();
        if (!programsList.matches("^[0-9]+(,[0-9]+)*$")) {
            ctx.status(400).json(ResponseUtil.formatError("Le paramètre 'programs_list' doit être une liste d'IDs numériques séparés par des virgules."));
            return;
        }

        Map<String, Object> result = courseService.getPrograms(queryParams);
        ctx.json(result);
    }
}
