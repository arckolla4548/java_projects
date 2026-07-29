package com.example.shop.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockitoExtension.class)
class ProductTest_AI {

    @Test
    void noArgsConstructor_whenInvoked_createsProductInstance() {
        Product product = new Product();

        assertNotNull(product);
    }

    @Test
    void class_whenInspected_hasEntityAnnotation() {
        assertTrue(Product.class.isAnnotationPresent(Entity.class));
    }

    @Test
    void idField_whenInspected_hasIdAndGeneratedValueAnnotations() throws NoSuchFieldException {
        Field idField = Product.class.getDeclaredField("id");

        assertTrue(idField.isAnnotationPresent(Id.class));
        assertTrue(idField.isAnnotationPresent(GeneratedValue.class));
    }

    @Test
    void declaredFields_whenInspected_haveExpectedTypes() throws NoSuchFieldException {
        assertEquals(Long.class, Product.class.getDeclaredField("id").getType());
        assertEquals(String.class, Product.class.getDeclaredField("name").getType());
        assertEquals(Double.class, Product.class.getDeclaredField("price").getType());
    }
}
