package com.diro.ift2255.controller;

import static org.mockito.Mockito.*;
import static org.mockito.ArgumentMatchers.*;

import io.javalin.http.Context;
import com.diro.ift2255.model.Course;
import com.diro.ift2255.service.CourseService;

import org.junit.jupiter.api.*;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.*;

@ExtendWith(MockitoExtension.class)
public class CourseControllerExtraTest {

    @Mock
    private CourseService mockService;

    @Mock
    private Context mockContext;

    private CourseController controller;

    @BeforeEach
    void setup() {
        controller = new CourseController(mockService, null);
    }

    @Test
    @DisplayName("Get all courses with no query params and empty result returns empty list")
    void testGetAllCoursesNoQueryParamsEmptyList() {
        when(mockContext.queryParamMap()).thenReturn(new HashMap<>());
        when(mockService.getAllCourses(any())).thenReturn(new ArrayList<>());

        controller.getAllCourses(mockContext);

        verify(mockContext).json(argThat(obj -> obj instanceof List && ((List<?>)obj).isEmpty()));
    }

    @Test
    @DisplayName("Get all courses with query params and no results returns message")
    void testGetAllCoursesWithQueryParamsNoResults() {
        Map<String, List<String>> queryParamMap = new HashMap<>();
        queryParamMap.put("session", Arrays.asList("A2025"));

        when(mockContext.queryParamMap()).thenReturn(queryParamMap);
        when(mockService.getAllCourses(any())).thenReturn(new ArrayList<>());

        controller.getAllCourses(mockContext);

        verify(mockContext).json(argThat(obj -> obj instanceof Map && ((Map<?,?>)obj).containsKey("message") && ((Map<?,?>)obj).containsKey("courses")));
    }

    @Test
    @DisplayName("Get all courses uses first value of multi-valued query params")
    void testGetAllCoursesUsesFirstValue() {
        Map<String, List<String>> queryParamMap = new HashMap<>();
        queryParamMap.put("session", Arrays.asList("A2025", "B2025"));

        when(mockContext.queryParamMap()).thenReturn(queryParamMap);
        when(mockService.getAllCourses(any())).thenReturn(Arrays.asList(new Course("IFT1015", "Prog I")));

        controller.getAllCourses(mockContext);

        verify(mockService).getAllCourses(argThat(params -> params.get("session").equals("A2025")));
        verify(mockContext).json(any());
    }
}
