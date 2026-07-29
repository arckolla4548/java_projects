package com.business.controllers;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import com.business.entities.Product;
import com.business.services.ProductServices;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(ProductController.class)
class ProductControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductServices productServices;

    @Test
    void addingProduct_ShouldBindProductDelegateToServiceAndRedirect() throws Exception {
        mockMvc.perform(post("/addingProduct")
                        .param("pname", "Keyboard")
                        .param("pprice", "49.99")
                        .param("pdescription", "Mechanical keyboard"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/admin/services"));

        verify(productServices).addProduct(any(Product.class));
    }

    @Test
    void addingProduct_WhenServiceThrowsException_ShouldReturnExceptionViewWithServerError() throws Exception {
        doThrow(new RuntimeException("save failed")).when(productServices).addProduct(any(Product.class));

        mockMvc.perform(post("/addingProduct")
                        .param("pname", "Keyboard")
                        .param("pprice", "49.99")
                        .param("pdescription", "Mechanical keyboard"))
                .andExpect(status().isInternalServerError())
                .andExpect(view().name("exception"));
    }

    @Test
    void updatingProduct_ShouldBindProductDelegateToServiceAndRedirect() throws Exception {
        mockMvc.perform(get("/updatingProduct/{productId}", 5)
                        .param("pname", "Mouse")
                        .param("pprice", "19.99")
                        .param("pdescription", "Wireless mouse"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/admin/services"));

        verify(productServices).updateproduct(any(Product.class), org.mockito.Mockito.eq(5));
    }

    @Test
    void deleteProduct_ShouldDelegateToServiceAndRedirect() throws Exception {
        mockMvc.perform(get("/deleteProduct/{productId}", 8))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/admin/services"));

        verify(productServices).deleteProduct(8);
    }
}
