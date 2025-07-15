/*
package com.tab.enote_app.integration;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tab.enote_app.dto.CategoryDto;
import com.tab.enote_app.dto.LoginRequest;
import com.tab.enote_app.entity.Category;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.health.StatusAggregator;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.io.UnsupportedEncodingException;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@ActiveProfiles("dev")
@AutoConfigureMockMvc
public class TestCategoryIntegration {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private CategoryDto categoryDto = null;
    private Category category = null;
    private String token=null;
    @Autowired
    private StatusAggregator statusAggregator;

    @BeforeEach
    public void initialize() {

        categoryDto  = CategoryDto.builder()
                .id(null)
                .name("Think Java")
                    .description("It is written By Eckel")
                .isActive(true)
                .build();

        category=Category.builder()
                .id(null)
                .name("Think Java")
                .description("It is written By Eckel")
                .isActive(true)
                .isDeleted(false)
                .build();

    }


    @Test
    public void testSaveCategory() throws JsonProcessingException, Exception
    {
        String token=generateToken("tab@gmail.com", "Tab@1234");

        mockMvc.perform(post("/api/v1/category/save")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(categoryDto))
                        .header("Authorization", token)
                )
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.message").value("saved success"))
                .andExpect(jsonPath("$.status").value("success"));
    }


    public String generateToken(String email,String password) throws JsonProcessingException, UnsupportedEncodingException, Exception
    {

        LoginRequest login = LoginRequest.builder()
                .password(password)
                .email(email)
                .build();

        String response = mockMvc.perform(post("/api/v1/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(login))
                ).andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        JsonNode root = objectMapper.readTree(response);
        String token = root.path("data").path("token").asText();
        return "Bearer "+token;
    }

}






*/
