package com.example.shop.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.lang.reflect.Field;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockitoExtension.class)
class CustomerOrderTest_AI {

    @Test
    void defaultConstructor_whenInvoked_createsCustomerOrderInstance() {
        CustomerOrder customerOrder = new CustomerOrder();

        assertNotNull(customerOrder);
    }

    @Test
    void class_whenInspected_hasEntityAnnotation() {
        assertTrue(CustomerOrder.class.isAnnotationPresent(Entity.class));
    }

    @Test
    void idField_whenInspected_hasIdAndGeneratedValueAnnotations() throws NoSuchFieldException {
        Field idField = CustomerOrder.class.getDeclaredField("id");

        assertTrue(idField.isAnnotationPresent(Id.class));
        assertTrue(idField.isAnnotationPresent(GeneratedValue.class));
    }

    @Test
    void declaredFields_whenInspected_haveExpectedTypes() throws NoSuchFieldException {
        assertEquals(Long.class, CustomerOrder.class.getDeclaredField("id").getType());
        assertEquals(Double.class, CustomerOrder.class.getDeclaredField("totalPrice").getType());
        assertEquals(LocalDateTime.class, CustomerOrder.class.getDeclaredField("createdAt").getType());
    }
}
