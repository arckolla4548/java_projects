package com.example.shop.repository;

import com.example.shop.entity.AppUser;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserRepositoryTest_AI {

    @Mock
    private UserRepository userRepository;

    @Test
    void repositoryInterface_whenInspected_extendsJpaRepository() {
        assertTrue(JpaRepository.class.isAssignableFrom(UserRepository.class));
    }

    @Test
    void findById_whenUserExists_returnsUser() {
        Long userId = 1L;
        AppUser appUser = new AppUser();
        when(userRepository.findById(userId)).thenReturn(Optional.of(appUser));

        Optional<AppUser> result = userRepository.findById(userId);

        assertTrue(result.isPresent());
        assertSame(appUser, result.get());
        verify(userRepository).findById(userId);
    }

    @Test
    void findById_whenUserDoesNotExist_returnsEmptyOptional() {
        Long userId = 99L;
        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        Optional<AppUser> result = userRepository.findById(userId);

        assertTrue(result.isEmpty());
        verify(userRepository).findById(userId);
    }

    @Test
    void findById_whenRepositoryThrowsException_propagatesException() {
        Long userId = -1L;
        IllegalArgumentException expectedException = new IllegalArgumentException("invalid id");
        when(userRepository.findById(userId)).thenThrow(expectedException);

        IllegalArgumentException actualException = assertThrows(IllegalArgumentException.class, () -> userRepository.findById(userId));

        assertSame(expectedException, actualException);
        verify(userRepository).findById(userId);
    }

    @Test
    void save_whenValidUserProvided_returnsSavedUser() {
        AppUser appUser = new AppUser();
        when(userRepository.save(appUser)).thenReturn(appUser);

        AppUser result = userRepository.save(appUser);

        assertSame(appUser, result);
        verify(userRepository).save(appUser);
    }

    @Test
    void deleteById_whenCalled_invokesRepositoryDelete() {
        Long userId = 1L;

        userRepository.deleteById(userId);

        verify(userRepository).deleteById(userId);
    }
}
