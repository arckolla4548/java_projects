package com.business.controllers;

import static org.hamcrest.Matchers.contains;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import com.business.entities.Product;
import com.business.loginCredentials.AdminLogin;
import com.business.services.ProductServices;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(HomeController.class)
class HomeControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductServices productServices;

    @Test
    void testEndpoint_ShouldReturnPlainTextHealthMessage() throws Exception {
        mockMvc.perform(get("/test"))
                .andExpect(status().isOk())
                .andExpect(content().string("Application is working!"));
    }

    @Test
    void home_ShouldReturnHomeView() throws Exception {
        mockMvc.perform(get("/home"))
                .andExpect(status().isOk())
                .andExpect(view().name("Home"));
    }

    @Test
    void products_ShouldLoadProductsIntoModelAndReturnProductsView() throws Exception {
        Product product = new Product();
        product.setPid(1);
        product.setPname("Laptop");
        product.setPprice(999.99);
        product.setPdescription("Business laptop");
        when(productServices.getAllProducts()).thenReturn(List.of(product));

        mockMvc.perform(get("/products"))
                .andExpect(status().isOk())
                .andExpect(view().name("Products"))
                .andExpect(model().attribute("products", contains(product)));
    }

    @Test
    void products_WhenServiceThrowsException_ShouldReturnExceptionViewWithServerError() throws Exception {
        when(productServices.getAllProducts()).thenThrow(new RuntimeException("products unavailable"));

        mockMvc.perform(get("/products"))
                .andExpect(status().isInternalServerError())
                .andExpect(view().name("exception"));
    }

    @Test
    void login_ShouldAddAdminLoginModelAttributeAndReturnLoginView() throws Exception {
        mockMvc.perform(get("/login"))
                .andExpect(status().isOk())
                .andExpect(view().name("Login"))
                .andExpect(model().attributeExists("adminLogin"))
                .andExpect(model().attribute("adminLogin", org.hamcrest.Matchers.instanceOf(AdminLogin.class)));
    }

    @Test
    void staticPages_ShouldReturnExpectedViews() throws Exception {
        mockMvc.perform(get("/location"))
                .andExpect(status().isOk())
                .andExpect(view().name("Locate_us"));

        mockMvc.perform(get("/about"))
                .andExpect(status().isOk())
                .andExpect(view().name("About"));
    }
}
