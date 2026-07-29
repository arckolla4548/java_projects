package com.example.shop.controller;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.lang.reflect.Method;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.bind.annotation.GetMapping;

@ExtendWith(MockitoExtension.class)
public class ProductControllerTest_AI {

    @InjectMocks
    private ProductController productController;

    @Test
    void homeShouldReturnProductsViewName() {
        String viewName = productController.home();

        assertEquals("products", viewName);
    }

    @Test
    void homeShouldReturnConsistentViewNameAcrossMultipleInvocations() {
        assertEquals("products", productController.home());
        assertEquals("products", productController.home());
    }

    @Test
    void homeShouldBeMappedToRootPath() throws NoSuchMethodException {
        Method homeMethod = ProductController.class.getMethod("home");
        GetMapping getMapping = homeMethod.getAnnotation(GetMapping.class);

        assertNotNull(getMapping);
        assertArrayEquals(new String[] {"/"}, getMapping.value());
    }
}
