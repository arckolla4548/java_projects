package com.business.controllers;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import java.util.Collections;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.*;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.servlet.ViewResolver;
import com.business.Exceptions;
import com.business.services.ProductServices;

@WebMvcTest(HomeController.class)
@Import({Exceptions.class, HomeControllerWebMvcIntegrationTest_AI.TestViewResolverConfig.class})
class HomeControllerWebMvcIntegrationTest_AI {
 @Autowired MockMvc mockMvc; @MockBean ProductServices productServices;
 @Test void testEndpointShouldReturnApplicationWorkingMessage() throws Exception {mockMvc.perform(get("/test")).andExpect(status().isOk()).andExpect(content().string("Application is working!"));}
 @Test void homeShouldReturnHomeView() throws Exception {mockMvc.perform(get("/home")).andExpect(status().isOk()).andExpect(view().name("Home"));}
 @Test void productsShouldPopulateProductsModelAndReturnProductsView() throws Exception {when(productServices.getAllProducts()).thenReturn(Collections.emptyList());mockMvc.perform(get("/products")).andExpect(status().isOk()).andExpect(view().name("Products")).andExpect(model().attributeExists("products"));}
 @Test void locationAboutAndLoginShouldReturnViews() throws Exception {mockMvc.perform(get("/location")).andExpect(status().isOk()).andExpect(view().name("Locate_us"));mockMvc.perform(get("/about")).andExpect(status().isOk()).andExpect(view().name("About"));mockMvc.perform(get("/login")).andExpect(status().isOk()).andExpect(view().name("Login")).andExpect(model().attributeExists("adminLogin"));}
 @TestConfiguration static class TestViewResolverConfig { @Bean ViewResolver viewResolver(){return (v,l)->(m,req,res)->{};} }
}
