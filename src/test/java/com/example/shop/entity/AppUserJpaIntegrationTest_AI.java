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
class AppUserJpaIntegrationTest_AI {

    @Autowired
    private TestEntityManager entityManager;

    @Test
    void appUserEntityShouldBePersistedWithGeneratedId() {
        AppUser appUser = new AppUser();
        ReflectionTestUtils.setField(appUser, "username", "persisted-user");
        ReflectionTestUtils.setField(appUser, "password", "persisted-password");

        AppUser persistedUser = entityManager.persistFlushFind(appUser);

        assertNotNull(ReflectionTestUtils.getField(persistedUser, "id"));
        assertEquals("persisted-user", ReflectionTestUtils.getField(persistedUser, "username"));
        assertEquals("persisted-password", ReflectionTestUtils.getField(persistedUser, "password"));
    }
}
