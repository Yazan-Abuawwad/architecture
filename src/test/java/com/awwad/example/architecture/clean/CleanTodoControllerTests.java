package com.awwad.example.architecture.clean;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class CleanTodoControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void createAndReadTodos() throws Exception {
        mockMvc.perform(post("/clean/todos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"title\":\"Learn Clean Architecture\"}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.title").value("Learn Clean Architecture"))
                .andExpect(jsonPath("$.done").value(false));

        mockMvc.perform(get("/clean/todos"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].title").value("Learn Clean Architecture"));
    }

    @Test
    void rejectsBlankTitle() throws Exception {
        mockMvc.perform(post("/clean/todos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"title\":\"\"}"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error").value("title is required"));
    }
}

