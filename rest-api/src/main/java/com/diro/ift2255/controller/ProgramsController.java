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

        Map<String, Object> result = courseService.getPrograms(queryParams);
        ctx.json(result);
    }
}
