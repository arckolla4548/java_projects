package com.business.controllers;

import com.business.entities.User;
import com.business.services.UserServices;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

@ExtendWith(MockitoExtension.class)
class UserControllerTest {

    @Mock
    private UserServices services;

    @Mock
    private User user;

    @InjectMocks
    private UserController userController;

    @Test
    void addUser_ShouldAddUserAndRedirectToAdminServices() {
        String result = userController.addUser(user);

        assertEquals("redirect:/admin/services", result);
        verify(services).addUser(user);
        verifyNoMoreInteractions(services);
    }

    @Test
    void updateUser_ShouldUpdateUserAndRedirectToAdminServices() {
        int id = 1;

        String result = userController.updateUser(user, id);

        assertEquals("redirect:/admin/services", result);
        verify(services).updateUser(user, id);
        verifyNoMoreInteractions(services);
    }

    @Test
    void deleteUser_ShouldDeleteUserAndRedirectToAdminServices() {
        int id = 1;

        String result = userController.deleteUser(id);

        assertEquals("redirect:/admin/services", result);
        verify(services).deleteUser(id);
        verifyNoMoreInteractions(services);
    }
}
