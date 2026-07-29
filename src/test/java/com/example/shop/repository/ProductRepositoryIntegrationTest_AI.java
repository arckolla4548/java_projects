package com.example.shop.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Optional;

import com.example.shop.entity.Product;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.util.ReflectionTestUtils;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
class ProductRepositoryIntegrationTest_AI {

    @Autowired
    private ProductRepository productRepository;

    @Test
    void saveAndFindByIdShouldPersistProduct() {
        Product product = new Product();
        ReflectionTestUtils.setField(product, "name", "Integration Test Product");
        ReflectionTestUtils.setField(product, "price", 19.99D);

        Product savedProduct = productRepository.saveAndFlush(product);
        Long generatedId = (Long) ReflectionTestUtils.getField(savedProduct, "id");

        assertNotNull(generatedId);

        Optional<Product> foundProduct = productRepository.findById(generatedId);

        assertTrue(foundProduct.isPresent());
        assertEquals("Integration Test Product", ReflectionTestUtils.getField(foundProduct.get(), "name"));
        assertEquals(19.99D, (Double) ReflectionTestUtils.getField(foundProduct.get(), "price"));
    }

    @Test
    void findByIdShouldReturnEmptyWhenProductDoesNotExist() {
        Optional<Product> result = productRepository.findById(Long.MAX_VALUE);

        assertTrue(result.isEmpty());
    }

    @Test
    void deleteByIdShouldRemovePersistedProduct() {
        Product product = new Product();
        ReflectionTestUtils.setField(product, "name", "Product To Delete");
        ReflectionTestUtils.setField(product, "price", 5.50D);

        Product savedProduct = productRepository.saveAndFlush(product);
        Long generatedId = (Long) ReflectionTestUtils.getField(savedProduct, "id");

        assertNotNull(generatedId);

        productRepository.deleteById(generatedId);
        productRepository.flush();

        assertFalse(productRepository.findById(generatedId).isPresent());
    }
}
