package com.example.shop.controller;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class ProductControllerTest_AI {

    @InjectMocks
    private ProductController productController;

    @Test
    void home_whenRequested_returnsProductsViewName() {
        String viewName = productController.home();

        assertEquals("products", viewName);
    }
}
