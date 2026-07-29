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
public class AppUserTest_AI {

    @Test
    void constructorShouldCreateAppUserInstance() {
        AppUser appUser = new AppUser();

        assertNotNull(appUser);
    }

    @Test
    void classShouldBeAnnotatedAsJpaEntity() {
        assertTrue(AppUser.class.isAnnotationPresent(Entity.class));
    }

    @Test
    void idFieldShouldHaveJpaIdentifierAnnotations() throws NoSuchFieldException {
        Field idField = AppUser.class.getDeclaredField("id");

        assertTrue(idField.isAnnotationPresent(Id.class));
        assertTrue(idField.isAnnotationPresent(GeneratedValue.class));
    }
}
