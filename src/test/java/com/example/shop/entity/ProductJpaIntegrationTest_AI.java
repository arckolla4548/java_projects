package com.example.shop.entity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.util.ReflectionTestUtils;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
class ProductJpaIntegrationTest_AI {

    @Autowired
    private TestEntityManager entityManager;

    @Test
    void productEntityShouldBePersistedWithGeneratedId() {
        Product product = new Product();
        ReflectionTestUtils.setField(product, "name", "Persisted Product");
        ReflectionTestUtils.setField(product, "price", 49.95D);

        Product persistedProduct = entityManager.persistFlushFind(product);

        assertNotNull(ReflectionTestUtils.getField(persistedProduct, "id"));
        assertEquals("Persisted Product", ReflectionTestUtils.getField(persistedProduct, "name"));
        assertEquals(49.95D, (Double) ReflectionTestUtils.getField(persistedProduct, "price"));
    }
}
