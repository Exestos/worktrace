package com.exestos.worktrace.controller;

import com.exestos.worktrace.domain.User;
import com.exestos.worktrace.service.user.RegistrationService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class AuthControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    private RegistrationService registrationService;

    @Test
    public void testGetToken() throws Exception {
        String username = "username";
        String password = "password";
        String fName = "Steve";
        String lName = "Smith";
        User user = registrationService.register(username, password, fName, lName);
        this.mvc.perform(post("/token")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.createObjectNode()
                        .put("username", username)
                        .put("password", password)
                        .toString())
        ).andExpect(status().isOk());
    }
}
