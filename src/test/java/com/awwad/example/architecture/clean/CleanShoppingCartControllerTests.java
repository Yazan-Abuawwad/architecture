package com.awwad.example.architecture.clean;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

@SpringBootTest
@WebAppConfiguration
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class CleanShoppingCartControllerTests {

    @Autowired
    private WebApplicationContext context;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = webAppContextSetup(context).build();
    }

    @Test
    void addItemValidatesStockAndAppliesDiscount() throws Exception {
        mockMvc.perform(post("/clean/cart/items")
                        .contentType(APPLICATION_JSON)
                        .content("{\"productId\":1,\"quantity\":2}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.items[0].productId").value(1))
                .andExpect(jsonPath("$.items[0].quantity").value(2))
                .andExpect(jsonPath("$.items[0].unitPrice").value(1000.00))
                .andExpect(jsonPath("$.items[0].discountedUnitPrice").value(900.00))
                .andExpect(jsonPath("$.items[0].lineTotal").value(1800.00))
                .andExpect(jsonPath("$.total").value(1800.00));

        mockMvc.perform(get("/clean/cart"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.items[0].quantity").value(2))
                .andExpect(jsonPath("$.total").value(1800.00));
    }

    @Test
    void rejectsUnknownProduct() throws Exception {
        mockMvc.perform(post("/clean/cart/items")
                        .contentType(APPLICATION_JSON)
                        .content("{\"productId\":999,\"quantity\":1}"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error").value("product not found"));
    }

    @Test
    void rejectsMissingProductId() throws Exception {
        mockMvc.perform(post("/clean/cart/items")
                        .contentType(APPLICATION_JSON)
                        .content("{\"quantity\":1}"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error").value("productId is required"));
    }

    @Test
    void rejectsNonPositiveQuantity() throws Exception {
        mockMvc.perform(post("/clean/cart/items")
                        .contentType(APPLICATION_JSON)
                        .content("{\"productId\":1,\"quantity\":0}"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error").value("quantity must be greater than zero"));
    }

    @Test
    void rejectsInsufficientStock() throws Exception {
        mockMvc.perform(post("/clean/cart/items")
                        .contentType(APPLICATION_JSON)
                        .content("{\"productId\":1,\"quantity\":6}"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error").value("insufficient stock"));
    }
}

