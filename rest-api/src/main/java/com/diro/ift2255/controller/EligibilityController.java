package com.diro.ift2255.controller;

import io.javalin.http.Context;
import com.diro.ift2255.service.EligibilityService;
import com.diro.ift2255.util.ResponseUtil;
import java.util.List;
import java.util.Map;

public class EligibilityController {
    private final EligibilityService eligibilityService;

    public EligibilityController(EligibilityService eligibilityService) {
        this.eligibilityService = eligibilityService;
    }

    /**
     * POST /courses/{id}/check-eligibility
     * Body: { "completedCourses": ["IFT1015", "IFT1025", ...] }
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

        ctx.json(eligibilityService.checkEligibility(courseId, req.completedCourses));
    }

    public static class EligibilityRequest {
        public List<String> completedCourses;
    }
}
