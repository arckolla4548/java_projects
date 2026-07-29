package com.business.controllers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doThrow;
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
    void addUserSavesUserAndRedirectsToAdminServices() {
        User user = new User();

        String result = userController.addUser(user);

        assertEquals("redirect:/admin/services", result);
        verify(services).addUser(user);
    }

    @Test
    void addUserPropagatesServiceException() {
        User user = new User();
        RuntimeException exception = new RuntimeException("Unable to add user");
        doThrow(exception).when(services).addUser(user);

        RuntimeException thrown = assertThrows(RuntimeException.class, () -> userController.addUser(user));

        assertEquals(exception, thrown);
        verify(services).addUser(user);
    }

    @Test
    void updateUserUpdatesUserAndRedirectsToAdminServices() {
        User user = new User();
        int userId = 7;

        String result = userController.updateUser(user, userId);

        assertEquals("redirect:/admin/services", result);
        verify(services).updateUser(user, userId);
    }

    @Test
    void updateUserPropagatesServiceException() {
        User user = new User();
        int userId = 7;
        RuntimeException exception = new RuntimeException("Unable to update user");
        doThrow(exception).when(services).updateUser(user, userId);

        RuntimeException thrown = assertThrows(RuntimeException.class, () -> userController.updateUser(user, userId));

        assertEquals(exception, thrown);
        verify(services).updateUser(user, userId);
    }

    @Test
    void deleteUserDeletesUserAndRedirectsToAdminServices() {
        int userId = 7;

        String result = userController.deleteUser(userId);

        assertEquals("redirect:/admin/services", result);
        verify(services).deleteUser(userId);
    }

    @Test
    void deleteUserPropagatesServiceException() {
        int userId = 7;
        RuntimeException exception = new RuntimeException("Unable to delete user");
        doThrow(exception).when(services).deleteUser(userId);

        RuntimeException thrown = assertThrows(RuntimeException.class, () -> userController.deleteUser(userId));

        assertEquals(exception, thrown);
        verify(services).deleteUser(userId);
    }
}
