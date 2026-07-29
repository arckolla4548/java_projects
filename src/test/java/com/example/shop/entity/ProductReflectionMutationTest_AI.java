package com.example.shop.entity;

import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class ProductReflectionMutationTest_AI {

    @Test
    void productFields_whenMutatedThroughReflection_storeExpectedValues() throws Exception {
        Product product = new Product();

        Field nameField = Product.class.getDeclaredField("name");
        nameField.setAccessible(true);
        nameField.set(product, "Laptop");

        Field priceField = Product.class.getDeclaredField("price");
        priceField.setAccessible(true);
        priceField.set(product, 999.99);

        assertEquals("Laptop", nameField.get(product));
        assertEquals(999.99, priceField.get(product));
    }

    @Test
    void productFields_whenNullAssigned_acceptNullValues() throws Exception {
        Product product = new Product();

        Field nameField = Product.class.getDeclaredField("name");
        nameField.setAccessible(true);
        nameField.set(product, null);

        assertNull(nameField.get(product));
    }
}
