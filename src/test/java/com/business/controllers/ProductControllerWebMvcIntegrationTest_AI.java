package com.business.controllers;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.*;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.servlet.ViewResolver;
import com.business.Exceptions;
import com.business.entities.Product;
import com.business.services.ProductServices;

@WebMvcTest(ProductController.class)
@Import({Exceptions.class, ProductControllerWebMvcIntegrationTest_AI.TestViewResolverConfig.class})
class ProductControllerWebMvcIntegrationTest_AI {
 @Autowired MockMvc mockMvc; @MockBean ProductServices productServices;
 @Test void addingProductShouldBindProductDelegateToServiceAndRedirect() throws Exception {mockMvc.perform(post("/addingProduct").param("pname","Laptop").param("pprice","1200.50").param("pdescription","Business laptop")).andExpect(status().is3xxRedirection()).andExpect(redirectedUrl("/admin/services"));verify(productServices).addProduct(any(Product.class));}
 @Test void updatingProductShouldDelegateToServiceAndRedirect() throws Exception {mockMvc.perform(get("/updatingProduct/{productId}",5).param("pname","Updated").param("pprice","999.99")).andExpect(status().is3xxRedirection()).andExpect(redirectedUrl("/admin/services"));verify(productServices).updateproduct(any(Product.class),eq(5));}
 @Test void deleteProductShouldDelegateToServiceAndRedirect() throws Exception {mockMvc.perform(get("/deleteProduct/{productId}",7)).andExpect(status().is3xxRedirection()).andExpect(redirectedUrl("/admin/services"));verify(productServices).deleteProduct(7);}
 @TestConfiguration static class TestViewResolverConfig { @Bean ViewResolver viewResolver(){return (v,l)-> v!=null&&v.startsWith("redirect:")?null:(m,req,res)->{};} }
}
