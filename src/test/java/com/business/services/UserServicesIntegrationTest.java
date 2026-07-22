package com.business.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.business.entities.User;
import com.business.repositories.UserRepository;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@SpringBootTest(classes = UserServices.class)
class UserServicesIntegrationTest {
    @Autowired private UserServices userServices;
    @MockBean private UserRepository userRepository;

    @Test void getAllUser_ShouldReturnAllUsers() { User user = new User(); when(userRepository.findAll()).thenReturn(List.of(user)); assertEquals(List.of(user), userServices.getAllUser()); }
    @Test void getUser_WhenPresent_ShouldReturnUser() { User user = new User(); user.setU_id(1); when(userRepository.findById(1)).thenReturn(Optional.of(user)); assertEquals(user, userServices.getUser(1)); }
    @Test void getUser_WhenMissing_ShouldThrowNoSuchElementException() { when(userRepository.findById(9)).thenReturn(Optional.empty()); assertThrows(NoSuchElementException.class, () -> userServices.getUser(9)); }
    @Test void getUserByEmail_ShouldDelegateToRepository() { User user = new User(); when(userRepository.findUserByUemail("alice@example.com")).thenReturn(user); assertEquals(user, userServices.getUserByEmail("alice@example.com")); }
    @Test void updateUser_ShouldSetIdAndSave() { User user = new User(); userServices.updateUser(user, 6); assertEquals(6, user.getU_id()); verify(userRepository).save(user); }
    @Test void deleteUser_ShouldDeleteById() { userServices.deleteUser(7); verify(userRepository).deleteById(7); }
    @Test void addUser_ShouldSaveUser() { User user = new User(); userServices.addUser(user); verify(userRepository).save(user); }
    @Test void validateLoginCredentials_WithMatchingUser_ShouldReturnTrue() { User user = new User(); user.setUemail("alice@example.com"); user.setUpassword("secret"); when(userRepository.findAll()).thenReturn(List.of(user)); assertTrue(userServices.validateLoginCredentials("alice@example.com", "secret")); }
    @Test void validateLoginCredentials_WithWrongEmailOrPassword_ShouldReturnFalse() { User user = new User(); user.setUemail("alice@example.com"); user.setUpassword("secret"); when(userRepository.findAll()).thenReturn(List.of(user)); assertFalse(userServices.validateLoginCredentials("alice@example.com", "bad")); assertFalse(userServices.validateLoginCredentials("missing@example.com", "secret")); }
}
