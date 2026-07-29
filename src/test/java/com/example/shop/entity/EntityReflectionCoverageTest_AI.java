package com.example.shop.entity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.lang.reflect.Field;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import org.junit.jupiter.api.Test;

class EntityReflectionCoverageTest_AI {

    @Test
    void productFieldsShouldExist() throws Exception {
        Field nameField = Product.class.getDeclaredField("name");
        Field priceField = Product.class.getDeclaredField("price");

        assertEquals(String.class, nameField.getType());
        assertEquals(Double.class, priceField.getType());
    }

    @Test
    void appUserFieldsShouldExist() throws Exception {
        Field usernameField = AppUser.class.getDeclaredField("username");
        Field passwordField = AppUser.class.getDeclaredField("password");

        assertEquals(String.class, usernameField.getType());
        assertEquals(String.class, passwordField.getType());
    }

    @Test
    void customerOrderFieldsShouldExist() throws Exception {
        Field totalPriceField = CustomerOrder.class.getDeclaredField("totalPrice");
        Field createdAtField = CustomerOrder.class.getDeclaredField("createdAt");

        assertNotNull(totalPriceField);
        assertNotNull(createdAtField);
    }

    @Test
    void allEntitiesShouldContainJpaIdentifierAnnotations() throws Exception {
        verifyIdAnnotations(Product.class);
        verifyIdAnnotations(AppUser.class);
        verifyIdAnnotations(CustomerOrder.class);
    }

    private void verifyIdAnnotations(Class<?> clazz) throws Exception {
        Field idField = clazz.getDeclaredField("id");

        assertTrue(idField.isAnnotationPresent(Id.class));
        assertTrue(idField.isAnnotationPresent(GeneratedValue.class));
    }
}
