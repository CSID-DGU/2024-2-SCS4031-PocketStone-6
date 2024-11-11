package com.pocketstone.team_sync.controller;


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.pocketstone.team_sync.auth.AddUserRequestDto;
import com.pocketstone.team_sync.auth.LoginRequestDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(SpringExtension.class)
class AuthIntegrationTest {


    @Autowired
    private MockMvc mockMvc;


    @Test // 회원가입 테스트
    public void testPostUser() throws Exception {
        AddUserRequestDto user = new AddUserRequestDto();
        user.setEmail("test3email@test.com");
        user.setLoginId("test3id");
        user.setPassword("1234qwer");
        user.setCompanyName("tester2");


        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());

        MockHttpServletResponse response = mockMvc.perform(post("/api/auth/signup")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(user)))
                .andDo(print())
                .andExpect(status().isOk()).andReturn().getResponse();


    }

    @Test
        // 로그인 테스트
    void testLogin() throws Exception {
        LoginRequestDto user = new LoginRequestDto();
        user.setLoginId("test3id");
        user.setPassword("1234qwer");

        ObjectMapper objectMapper = new ObjectMapper();

        MockHttpServletResponse response = mockMvc.perform(post("/api/auth/login")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(user)))
                .andDo(print())
                .andExpect(jsonPath("$.grantType").value("Bearer"))
                .andExpect(jsonPath("$.accessToken").exists())
                .andExpect(jsonPath("$.refreshToken").exists())
                .andExpect(status().isOk()).andReturn().getResponse();

    }

    @Test
        // 잘못된 비밀번호로 로그인
    void testFailedLogin() throws Exception {
        LoginRequestDto user = new LoginRequestDto();
        user.setLoginId("test3id");
        user.setPassword("wrongpassword"); //틀린 비밀번호

        ObjectMapper objectMapper = new ObjectMapper();

        MockHttpServletResponse response = mockMvc.perform(post("/api/auth/login")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(user)))
                .andDo(print())
                .andExpect(status().isUnauthorized()).andReturn().getResponse();
    }

    @Test
        // 존재하지 않는 id로 로그인
    void testNonExistUserLogin() throws Exception {
        LoginRequestDto user = new LoginRequestDto();
        user.setLoginId("ididididididididididididididididididididididi"); //존재하지 않는 id
        user.setPassword("123");

        ObjectMapper objectMapper = new ObjectMapper();

        MockHttpServletResponse response = mockMvc.perform(post("/api/auth/login")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(user)))
                .andDo(print())
                .andExpect(status().isNotFound()).andReturn().getResponse();
    }
}