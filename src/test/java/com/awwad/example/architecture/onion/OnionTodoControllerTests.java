package com.awwad.example.architecture.onion;

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
class OnionTodoControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void createAndReadTodos() throws Exception {
        mockMvc.perform(post("/onion/todos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"title\":\"Learn Onion Architecture\"}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.title").value("Learn Onion Architecture"))
                .andExpect(jsonPath("$.done").value(false));

        mockMvc.perform(get("/onion/todos"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].title").value("Learn Onion Architecture"));
    }

    @Test
    void rejectsBlankTitle() throws Exception {
        mockMvc.perform(post("/onion/todos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"title\":\"\"}"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error").value("title is required"));
    }
}

