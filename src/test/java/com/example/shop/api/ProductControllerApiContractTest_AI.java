package com.example.shop.api;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import com.example.shop.controller.ProductController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(ProductController.class)
class ProductControllerApiContractTest_AI {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void getRootShouldRenderProductsView() throws Exception {
        mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(view().name("products"));
    }

    @Test
    void deleteRootShouldReturnMethodNotAllowed() throws Exception {
        mockMvc.perform(delete("/"))
                .andExpect(status().isMethodNotAllowed());
    }
}
