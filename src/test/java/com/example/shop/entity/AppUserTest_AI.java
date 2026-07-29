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
class AppUserTest_AI {

    @Test
    void defaultConstructor_whenInvoked_createsAppUserInstance() {
        AppUser appUser = new AppUser();

        assertNotNull(appUser);
    }

    @Test
    void class_whenInspected_hasEntityAnnotation() {
        assertTrue(AppUser.class.isAnnotationPresent(Entity.class));
    }

    @Test
    void idField_whenInspected_hasIdAndGeneratedValueAnnotations() throws NoSuchFieldException {
        Field idField = AppUser.class.getDeclaredField("id");

        assertTrue(idField.isAnnotationPresent(Id.class));
        assertTrue(idField.isAnnotationPresent(GeneratedValue.class));
    }

    @Test
    void declaredFields_whenInspected_haveExpectedTypes() throws NoSuchFieldException {
        assertEquals(Long.class, AppUser.class.getDeclaredField("id").getType());
        assertEquals(String.class, AppUser.class.getDeclaredField("username").getType());
        assertEquals(String.class, AppUser.class.getDeclaredField("password").getType());
    }
}
