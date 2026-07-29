package com.example.shop.entity;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.lang.reflect.Field;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class ProductTest_AI {

    @Test
    void constructorShouldCreateProductInstance() {
        Product product = new Product();

        assertNotNull(product);
    }

    @Test
    void classShouldBeAnnotatedAsJpaEntity() {
        assertTrue(Product.class.isAnnotationPresent(Entity.class));
    }

    @Test
    void idFieldShouldHaveJpaIdentifierAnnotations() throws NoSuchFieldException {
        Field idField = Product.class.getDeclaredField("id");

        assertTrue(idField.isAnnotationPresent(Id.class));
        assertTrue(idField.isAnnotationPresent(GeneratedValue.class));
    }
}
