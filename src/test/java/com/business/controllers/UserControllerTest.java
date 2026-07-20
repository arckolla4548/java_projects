package com.business.controllers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.business.entities.User;
import com.business.services.UserServices;

@ExtendWith(MockitoExtension.class)
class UserControllerTest {

    @Mock
    private UserServices services;

    @InjectMocks
    private UserController userController;

    @Test
    void addUser_AddsUserAndReturnsRedirect() {
        User user = org.mockito.Mockito.mock(User.class);

        String viewName = userController.addUser(user);

        assertEquals("redirect:/admin/services", viewName);
        verify(services).addUser(user);
    }

    @Test
    void addUser_WhenUserServiceThrowsException_PropagatesException() {
        User user = org.mockito.Mockito.mock(User.class);
        org.mockito.Mockito.doThrow(new RuntimeException("service failure")).when(services).addUser(user);

        assertThrows(RuntimeException.class, () -> userController.addUser(user));
        verify(services).addUser(user);
    }

    @Test
    void updateUser_UpdatesUserAndReturnsRedirect() {
        User user = org.mockito.Mockito.mock(User.class);

        String viewName = userController.updateUser(user, 10);

        assertEquals("redirect:/admin/services", viewName);
        verify(services).updateUser(user, 10);
    }

    @Test
    void updateUser_WhenUserServiceThrowsException_PropagatesException() {
        User user = org.mockito.Mockito.mock(User.class);
        org.mockito.Mockito.doThrow(new RuntimeException("service failure")).when(services).updateUser(user, 10);

        assertThrows(RuntimeException.class, () -> userController.updateUser(user, 10));
        verify(services).updateUser(user, 10);
    }

    @Test
    void deleteUser_DeletesUserAndReturnsRedirect() {
        String viewName = userController.deleteUser(10);

        assertEquals("redirect:/admin/services", viewName);
        verify(services).deleteUser(10);
    }

    @Test
    void deleteUser_WhenUserServiceThrowsException_PropagatesException() {
        org.mockito.Mockito.doThrow(new RuntimeException("service failure")).when(services).deleteUser(10);

        assertThrows(RuntimeException.class, () -> userController.deleteUser(10));
        verify(services).deleteUser(10);
    }
}
