package com.example.shop.repository;

import com.example.shop.entity.Product;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.lang.reflect.Field;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
class ProductRepositoryDataJpaTest_AI {

    @Autowired
    private ProductRepository productRepository;

    @Test
    void saveAndFindProduct_whenPersisted_returnsStoredEntity() throws Exception {
        Product product = new Product();

        Field nameField = Product.class.getDeclaredField("name");
        nameField.setAccessible(true);
        nameField.set(product, "Phone");

        Product savedProduct = productRepository.save(product);

        Optional<Product> fetchedProduct = productRepository.findById(savedProduct.getId());

        assertTrue(fetchedProduct.isPresent());
    }
}
