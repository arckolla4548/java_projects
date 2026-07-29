package com.example.shop.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import com.example.shop.entity.Product;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.util.ReflectionTestUtils;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
class RepositoryPersistenceEdgeCaseTest_AI {

    @Autowired
    private ProductRepository productRepository;

    @Test
    void repositoryShouldInitiallyReturnEmptyCollection() {
        List<Product> products = productRepository.findAll();

        assertTrue(products.isEmpty());
    }

    @Test
    void multipleProductsShouldBePersistedSuccessfully() {
        Product first = new Product();
        ReflectionTestUtils.setField(first, "name", "Keyboard");
        ReflectionTestUtils.setField(first, "price", 25.00D);

        Product second = new Product();
        ReflectionTestUtils.setField(second, "name", "Mouse");
        ReflectionTestUtils.setField(second, "price", 15.00D);

        productRepository.save(first);
        productRepository.save(second);

        List<Product> products = productRepository.findAll();

        assertEquals(2, products.size());
    }
}
