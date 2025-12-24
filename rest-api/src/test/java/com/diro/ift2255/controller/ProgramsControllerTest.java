package com.diro.ift2255.controller;

import static org.mockito.Mockito.*;
import static org.mockito.ArgumentMatchers.*;

import io.javalin.http.Context;
import com.diro.ift2255.service.CourseService;
import org.junit.jupiter.api.*;
import org.mockito.Mock;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

@ExtendWith(MockitoExtension.class)
public class ProgramsControllerTest {

    @Mock
    private CourseService mockService;

    @Mock
    private Context mockContext;

    private ProgramsController controller;

    @BeforeEach
    void setup() {
        controller = new ProgramsController(mockService);
    }

    @Test
    @DisplayName("GET /api/v1/programs returns 400 when missing programs_list")
    void testGetProgramsMissingParam() {
        when(mockContext.queryParamMap()).thenReturn(new HashMap<>());
        when(mockContext.status(400)).thenReturn(mockContext);

        controller.getPrograms(mockContext);

        verify(mockContext).status(400);
        verify(mockContext).json(argThat(obj -> obj instanceof Map && ((Map<?,?>)obj).containsKey("error")));
    }

    @Test
    @DisplayName("GET /api/v1/programs returns 400 when programs_list invalid format")
    void testGetProgramsInvalidFormat() {
        Map<String, List<String>> queryParamMap = new HashMap<>();
        queryParamMap.put("programs_list", Arrays.asList("abc"));

        when(mockContext.queryParamMap()).thenReturn(queryParamMap);
        when(mockContext.status(400)).thenReturn(mockContext);

        controller.getPrograms(mockContext);

        verify(mockContext).status(400);
        verify(mockContext).json(argThat(obj -> obj instanceof Map && ((Map<?,?>)obj).containsKey("error")));
    }

    @Test
    @DisplayName("GET /api/v1/programs calls service and returns result")
    void testGetProgramsSuccess() {
        Map<String, List<String>> queryParamMap = new HashMap<>();
        queryParamMap.put("programs_list", Arrays.asList("117510"));

        Map<String,Object> fakeResponse = Map.of("programs", Arrays.asList("p1"));

        when(mockContext.queryParamMap()).thenReturn(queryParamMap);
        when(mockService.getPrograms(any())).thenReturn(fakeResponse);

        controller.getPrograms(mockContext);

        verify(mockService).getPrograms(argThat(m -> m.containsKey("programs_list") && m.get("programs_list").equals("117510")));
        verify(mockContext).json(fakeResponse);
    }

    @Test
    @DisplayName("GET /api/v1/programs accepts include_courses_detail param")
    void testGetProgramsIncludeDetail() {
        Map<String, List<String>> queryParamMap = new HashMap<>();
        queryParamMap.put("programs_list", Arrays.asList("117510"));
        queryParamMap.put("include_courses_detail", Arrays.asList("true"));

        when(mockContext.queryParamMap()).thenReturn(queryParamMap);
        when(mockService.getPrograms(any())).thenReturn(new HashMap<>());

        controller.getPrograms(mockContext);

        verify(mockService).getPrograms(argThat(m -> m.containsKey("include_courses_detail") && m.get("include_courses_detail").equals("true")));
        verify(mockContext).json(any());
    }

    @Test
    @DisplayName("GET /api/v1/programs supports multiple program ids")
    void testGetProgramsMultipleIds() {
        Map<String, List<String>> queryParamMap = new HashMap<>();
        queryParamMap.put("programs_list", Arrays.asList("117510,117511"));

        when(mockContext.queryParamMap()).thenReturn(queryParamMap);
        when(mockService.getPrograms(any())).thenReturn(new HashMap<>());

        controller.getPrograms(mockContext);

        verify(mockService).getPrograms(argThat(m -> m.get("programs_list").equals("117510,117511")));
        verify(mockContext).json(any());
    }
}
