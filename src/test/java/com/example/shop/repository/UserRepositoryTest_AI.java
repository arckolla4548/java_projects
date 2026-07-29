package com.example.shop.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Collections;
import java.util.List;

import com.example.shop.entity.AppUser;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.jpa.repository.JpaRepository;

@ExtendWith(MockitoExtension.class)
public class UserRepositoryTest_AI {

    @Mock
    private UserRepository userRepository;

    @Test
    void repositoryShouldExtendJpaRepository() {
        assertTrue(JpaRepository.class.isAssignableFrom(UserRepository.class));
    }

    @Test
    void findAllShouldReturnUsersFromRepositoryProxy() {
        List<AppUser> users = Collections.emptyList();
        when(userRepository.findAll()).thenReturn(users);

        List<AppUser> result = userRepository.findAll();

        assertEquals(users, result);
        verify(userRepository).findAll();
    }

    @Test
    void saveShouldReturnSavedUserFromRepositoryProxy() {
        AppUser appUser = new AppUser();
        when(userRepository.save(appUser)).thenReturn(appUser);

        AppUser result = userRepository.save(appUser);

        assertSame(appUser, result);
        verify(userRepository).save(appUser);
    }

    @Test
    void findAllShouldPropagateRepositoryException() {
        RuntimeException repositoryException = new RuntimeException("repository failure");
        when(userRepository.findAll()).thenThrow(repositoryException);

        RuntimeException thrown = assertThrows(RuntimeException.class, () -> userRepository.findAll());

        assertSame(repositoryException, thrown);
        verify(userRepository).findAll();
    }
}
