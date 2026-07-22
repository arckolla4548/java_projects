package com.business.controllers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;

import com.business.entities.User;
import com.business.services.UserServices;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class UserControllerTest_AI {

    @Mock
    private UserServices services;

    @InjectMocks
    private UserController userController;

    @Test
    void addUser_ShouldDelegateToServiceAndRedirect() {
        User user = new User();
        String result = userController.addUser(user);
        assertEquals("redirect:/admin/services", result);
        verify(services).addUser(user);
    }

    @Test
    void addUser_WhenServiceThrowsException_ShouldPropagateException() {
        User user = new User();
        RuntimeException exception = new RuntimeException("add failed");
        doThrow(exception).when(services).addUser(user);
        RuntimeException actual = assertThrows(RuntimeException.class, () -> userController.addUser(user));
        assertSame(exception, actual);
        verify(services).addUser(user);
    }

    @Test
    void updateUser_ShouldDelegateToServiceAndRedirect() {
        User user = new User();
        int userId = 10;
        String result = userController.updateUser(user, userId);
        assertEquals("redirect:/admin/services", result);
        verify(services).updateUser(user, userId);
    }

    @Test
    void updateUser_WhenServiceThrowsException_ShouldPropagateException() {
        User user = new User();
        int userId = 10;
        RuntimeException exception = new RuntimeException("update failed");
        doThrow(exception).when(services).updateUser(user, userId);
        RuntimeException actual = assertThrows(RuntimeException.class, () -> userController.updateUser(user, userId));
        assertSame(exception, actual);
        verify(services).updateUser(user, userId);
    }

    @Test
    void deleteUser_ShouldDelegateToServiceAndRedirect() {
        int userId = 7;
        String result = userController.deleteUser(userId);
        assertEquals("redirect:/admin/services", result);
        verify(services).deleteUser(userId);
    }

    @Test
    void deleteUser_WhenServiceThrowsException_ShouldPropagateException() {
        int userId = 7;
        RuntimeException exception = new RuntimeException("delete failed");
        doThrow(exception).when(services).deleteUser(userId);
        RuntimeException actual = assertThrows(RuntimeException.class, () -> userController.deleteUser(userId));
        assertSame(exception, actual);
        verify(services).deleteUser(userId);
    }
}
