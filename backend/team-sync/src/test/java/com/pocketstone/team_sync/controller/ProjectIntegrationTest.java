package com.pocketstone.team_sync.controller;

import java.time.LocalDate;

import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.jayway.jsonpath.JsonPath;
import com.pocketstone.team_sync.dto.projectdto.ProjectDto;
import com.pocketstone.team_sync.entity.User;
import com.pocketstone.team_sync.repository.ProjectRepository;
import com.pocketstone.team_sync.repository.UserRepository;

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(SpringExtension.class)
public class ProjectIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProjectRepository projectRepository;


    @Test // 프로젝트 생성
    public void testPostUpcomingProject() throws Exception {
        User user = userRepository.findByLoginId("test3id").orElseThrow();

        ProjectDto project = new ProjectDto("testUpcomingProject",
                LocalDate.of(2024, 12, 25),
                LocalDate.of(2025,12,25));

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());

        MockHttpServletResponse response = mockMvc.perform(post("/api/projects/project")
                        .with(SecurityMockMvcRequestPostProcessors.user(user))
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(project)))
                .andDo(print())
                .andExpect(jsonPath("$.*", hasSize(4)))
                .andExpect(jsonPath("$.projectName").value("testUpcomingProject"))
                .andExpect(jsonPath("$.startDate").value("2024-12-25"))
                .andExpect(jsonPath("$.mvpDate").value("2025-12-25"))
                .andExpect(status().isCreated())
                .andReturn().getResponse();

        Integer idInt = JsonPath.parse(response.getContentAsString()).read("$.id");
        Long id = idInt.longValue();
        assertNotNull(projectRepository.findById(id));

    }

    @Test // 프로젝트 생성
    public void testPostOngoingProject() throws Exception {
        User user = userRepository.findByLoginId("test3id").orElseThrow();

        ProjectDto project = new ProjectDto("testOngoingProject",
                LocalDate.of(2024, 10, 1),
                LocalDate.of(2024,12,31));

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());

        MockHttpServletResponse response = mockMvc.perform(post("/api/projects/project")
                        .with(SecurityMockMvcRequestPostProcessors.user(user))
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(project)))
                .andDo(print())
                .andExpect(jsonPath("$.*", hasSize(4)))
                .andExpect(jsonPath("$.projectName").value("testOngoingProject"))
                .andExpect(jsonPath("$.startDate").value("2024-10-01"))
                .andExpect(jsonPath("$.mvpDate").value("2024-12-31"))
                .andExpect(status().isCreated())
                .andReturn().getResponse();

        Integer idInt = JsonPath.parse(response.getContentAsString()).read("$.id");
        Long id = idInt.longValue();
        assertNotNull(projectRepository.findById(id));

    }

    @Test // 프로젝트 생성
    public void testPostOngoingProject2() throws Exception {
        User user = userRepository.findByLoginId("test3id").orElseThrow();

        ProjectDto project = new ProjectDto("testOngoingProject2",
                LocalDate.of(2024, 10, 1),
                LocalDate.of(2025,3,1));

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());

        MockHttpServletResponse response = mockMvc.perform(post("/api/projects/project")
                        .with(SecurityMockMvcRequestPostProcessors.user(user))
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(project)))
                .andDo(print())
                .andExpect(jsonPath("$.*", hasSize(4)))
                .andExpect(jsonPath("$.projectName").value("testOngoingProject2"))
                .andExpect(jsonPath("$.startDate").value("2024-10-01"))
                .andExpect(jsonPath("$.mvpDate").value("2025-03-01"))
                .andExpect(status().isCreated())
                .andReturn().getResponse();

        Integer idInt = JsonPath.parse(response.getContentAsString()).read("$.id");
        Long id = idInt.longValue();
        assertNotNull(projectRepository.findById(id));

    }

    @Test // 마감일을 과거로 프로젝트 생성
    public void testPastProject() throws Exception {
        User user = userRepository.findByLoginId("test3id").orElseThrow();

        ProjectDto project = new ProjectDto("testCompleteProject",
                LocalDate.of(2023, 10, 1),
                LocalDate.of(2023,12,31));

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());

        MockHttpServletResponse response = mockMvc.perform(post("/api/projects/project")
                        .with(SecurityMockMvcRequestPostProcessors.user(user))
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(project)))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andReturn().getResponse();

    }

    @Test //프로젝트 이름으로 찾기
    public void testGetProjectByName() throws Exception {
        User user = userRepository.findByLoginId("test3id").orElseThrow();
        String projectName = "testUpcomingProject";

        ObjectMapper objectMapper = new ObjectMapper();
        MockHttpServletResponse response = mockMvc.perform(get ("/api/projects/{projectName}", projectName)
                        .with(SecurityMockMvcRequestPostProcessors.user(user))
                        .contentType("application/json"))
                .andDo(print())
                .andExpect(jsonPath("$.*", hasSize(4)))
                .andExpect(jsonPath("$.projectName").value("testUpcomingProject"))
                .andExpect(jsonPath("$.startDate").value("2024-12-25"))
                .andExpect(jsonPath("$.mvpDate").value("2025-12-25"))
                .andExpect(status().isOk())
                .andReturn().getResponse();


    }

    @Test
    public void testFailedGetProjectByName() throws Exception {
        User user = userRepository.findByLoginId("test3id").orElseThrow();
        String projectName = "NonExistProject"; //존재하지 않는 프로젝트 이름

        ObjectMapper objectMapper = new ObjectMapper();
        MockHttpServletResponse response = mockMvc.perform(get ("/api/projects/{projectName}", projectName)
                        .with(SecurityMockMvcRequestPostProcessors.user(user))
                        .contentType("application/json"))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andReturn().getResponse();


    }

    @Test
    public void testGetOngoingProjects() throws Exception { //현재 진행중인 프로젝트 조회
        User user = userRepository.findByLoginId("test3id").orElseThrow();

        ObjectMapper objectMapper = new ObjectMapper();
        MockHttpServletResponse response = mockMvc.perform(get("/api/projects?status=ONGOING")
                        .with(SecurityMockMvcRequestPostProcessors.user(user))
                        .contentType("application/json"))
                .andDo(print())
                .andExpect(jsonPath("$.*", hasSize(2)))
                .andExpect(jsonPath("$[0].projectName").value("testOngoingProject"))
                .andExpect(jsonPath("$[0].startDate").value("2024-10-01"))
                .andExpect(jsonPath("$[0].mvpDate").value("2024-12-31"))
                .andExpect(jsonPath("$[1].projectName").value("testOngoingProject2"))
                .andExpect(jsonPath("$[1].startDate").value("2024-10-01"))
                .andExpect(jsonPath("$[1].mvpDate").value("2025-03-01"))
                .andExpect(status().isOk())
                .andReturn().getResponse();

    }

    @Test
    public void testGetUpcomingProjects() throws Exception {
        User user = userRepository.findByLoginId("test3id").orElseThrow();

        ObjectMapper objectMapper = new ObjectMapper();
        MockHttpServletResponse response = mockMvc.perform(get("/api/projects?status=UPCOMING")
                        .with(SecurityMockMvcRequestPostProcessors.user(user))
                        .contentType("application/json"))
                .andDo(print())
                .andExpect(jsonPath("$.*", hasSize(1)))
                .andExpect(jsonPath("$[0].projectName").value("testUpcomingProject"))
                .andExpect(jsonPath("$[0].startDate").value("2024-12-25"))
                .andExpect(jsonPath("$[0].mvpDate").value("2025-12-25"))
                .andExpect(status().isOk())
                .andReturn().getResponse();

    }

    @Test
    public void testCompleteProject() throws Exception {

    }

}
