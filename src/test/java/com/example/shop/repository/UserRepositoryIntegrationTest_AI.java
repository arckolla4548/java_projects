package com.example.shop.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Optional;

import com.example.shop.entity.AppUser;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.util.ReflectionTestUtils;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
class UserRepositoryIntegrationTest_AI {

    @Autowired
    private UserRepository userRepository;

    @Test
    void saveAndFindByIdShouldPersistAppUser() {
        AppUser appUser = new AppUser();
        ReflectionTestUtils.setField(appUser, "username", "integration-user");
        ReflectionTestUtils.setField(appUser, "password", "integration-password");

        AppUser savedUser = userRepository.saveAndFlush(appUser);
        Long generatedId = (Long) ReflectionTestUtils.getField(savedUser, "id");

        assertNotNull(generatedId);

        Optional<AppUser> foundUser = userRepository.findById(generatedId);

        assertTrue(foundUser.isPresent());
        assertEquals("integration-user", ReflectionTestUtils.getField(foundUser.get(), "username"));
        assertEquals("integration-password", ReflectionTestUtils.getField(foundUser.get(), "password"));
    }

    @Test
    void findByIdShouldReturnEmptyWhenAppUserDoesNotExist() {
        Optional<AppUser> result = userRepository.findById(Long.MAX_VALUE);

        assertTrue(result.isEmpty());
    }

    @Test
    void deleteByIdShouldRemovePersistedAppUser() {
        AppUser appUser = new AppUser();
        ReflectionTestUtils.setField(appUser, "username", "delete-user");
        ReflectionTestUtils.setField(appUser, "password", "delete-password");

        AppUser savedUser = userRepository.saveAndFlush(appUser);
        Long generatedId = (Long) ReflectionTestUtils.getField(savedUser, "id");

        assertNotNull(generatedId);

        userRepository.deleteById(generatedId);
        userRepository.flush();

        assertFalse(userRepository.findById(generatedId).isPresent());
    }
}
