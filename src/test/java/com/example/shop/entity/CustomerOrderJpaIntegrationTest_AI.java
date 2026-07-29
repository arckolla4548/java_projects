package com.example.shop.entity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.util.ReflectionTestUtils;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
class CustomerOrderJpaIntegrationTest_AI {

    @Autowired
    private TestEntityManager entityManager;

    @Test
    void customerOrderEntityShouldBePersistedWithGeneratedIdAndCreatedAt() {
        CustomerOrder customerOrder = new CustomerOrder();
        ReflectionTestUtils.setField(customerOrder, "totalPrice", 125.75D);

        CustomerOrder persistedOrder = entityManager.persistFlushFind(customerOrder);

        assertNotNull(ReflectionTestUtils.getField(persistedOrder, "id"));
        assertNotNull(ReflectionTestUtils.getField(persistedOrder, "createdAt"));
        assertEquals(LocalDateTime.class, ReflectionTestUtils.getField(persistedOrder, "createdAt").getClass());
        assertEquals(125.75D, (Double) ReflectionTestUtils.getField(persistedOrder, "totalPrice"));
    }
}
