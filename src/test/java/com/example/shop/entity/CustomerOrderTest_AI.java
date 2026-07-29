package com.example.shop.entity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.lang.reflect.Field;
import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class CustomerOrderTest_AI {

    @Test
    void constructorShouldCreateCustomerOrderInstance() {
        CustomerOrder customerOrder = new CustomerOrder();

        assertNotNull(customerOrder);
    }

    @Test
    void classShouldBeAnnotatedAsJpaEntity() {
        assertTrue(CustomerOrder.class.isAnnotationPresent(Entity.class));
    }

    @Test
    void idFieldShouldHaveJpaIdentifierAnnotations() throws NoSuchFieldException {
        Field idField = CustomerOrder.class.getDeclaredField("id");

        assertTrue(idField.isAnnotationPresent(Id.class));
        assertTrue(idField.isAnnotationPresent(GeneratedValue.class));
    }

    @Test
    void createdAtFieldShouldBeLocalDateTimeType() throws NoSuchFieldException {
        Field createdAtField = CustomerOrder.class.getDeclaredField("createdAt");

        assertEquals(LocalDateTime.class, createdAtField.getType());
    }
}
