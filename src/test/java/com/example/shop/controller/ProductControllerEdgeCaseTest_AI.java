package com.example.shop.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.lang.reflect.Method;

import org.junit.jupiter.api.Test;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

class ProductControllerEdgeCaseTest_AI {

    @Test
    void controllerShouldBeAnnotatedWithSpringController() {
        assertTrue(ProductController.class.isAnnotationPresent(Controller.class));
    }

    @Test
    void homeMethodShouldExistAndReturnStringType() throws Exception {
        Method method = ProductController.class.getDeclaredMethod("home");

        assertNotNull(method);
        assertEquals(String.class, method.getReturnType());
    }

    @Test
    void homeMethodShouldContainGetMappingAnnotation() throws Exception {
        Method method = ProductController.class.getDeclaredMethod("home");
        GetMapping annotation = method.getAnnotation(GetMapping.class);

        assertNotNull(annotation);
        assertEquals("/", annotation.value()[0]);
    }

    @Test
    void homeShouldNeverReturnNullViewName() {
        ProductController controller = new ProductController();

        assertNotNull(controller.home());
        assertEquals("products", controller.home());
    }
}
