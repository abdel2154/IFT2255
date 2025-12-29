package com.diro.ift2255.controller;

import com.diro.ift2255.model.CourseSet;
import com.diro.ift2255.service.CourseSetService;
import com.diro.ift2255.util.ResponseUtil;
import io.javalin.http.Context;

import java.util.List;
import java.util.Optional;

/**
 * Contrôleur pour les ensembles de cours (CourseSet).
 */
public class CourseSetController {

    private final CourseSetService courseSetService;

    public CourseSetController(CourseSetService courseSetService) {
        this.courseSetService = courseSetService;
    }

    /**
     * POST /course-sets
     * Body JSON:
     * {
     *   "name": "Session hiver optimisée",
     *   "courseIds": ["IFT2255", "IFT2015", "IFT1025"]
     * }
     */
    public void createCourseSet(Context ctx) {
        CreateCourseSetRequest req = ctx.bodyAsClass(CreateCourseSetRequest.class);

        if (req == null || req.name == null || req.name.isBlank()
                || req.courseIds == null || req.courseIds.isEmpty()) {
            ctx.status(400).json(
                    ResponseUtil.formatError("Le nom et la liste de cours sont obligatoires.")
            );
            return;
        }

        try {
            CourseSet set = courseSetService.createCourseSet(req.name, req.courseIds);
            ctx.status(201).json(set);
        } catch (IllegalArgumentException e) {
            ctx.status(400).json(ResponseUtil.formatError(e.getMessage()));
        }
    }

    /**
     * GET /course-sets/{id}
     */
    public void getCourseSetById(Context ctx) {
        String id = ctx.pathParam("id");

        Optional<CourseSet> setOpt = courseSetService.getById(id);

        if (setOpt.isPresent()) {
            ctx.json(setOpt.get());
        } else {
            ctx.status(404).json(
                    ResponseUtil.formatError("Aucun ensemble de cours trouvé pour l'id: " + id)
            );
        }
    }

    /**
     * (Optionnel) GET /course-sets
     * Pour lister tous les ensembles créés.
     */
    public void getAllCourseSets(Context ctx) {
        List<CourseSet> sets = courseSetService.getAll();
        ctx.json(sets);
    }

    public static class CreateCourseSetRequest {
        public String name;
        public List<String> courseIds;
    }
}
