package com.business.controllers;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import com.business.entities.User;
import com.business.services.UserServices;

@ExtendWith(MockitoExtension.class)
class UserControllerTest_AI {
 @Mock UserServices services; @InjectMocks UserController controller;
 @Test void addUserShouldDelegateToServiceAndRedirect(){User u=new User();assertEquals("redirect:/admin/services",controller.addUser(u));verify(services).addUser(u);}
 @Test void updateUserShouldDelegateToServiceAndRedirect(){User u=new User();assertEquals("redirect:/admin/services",controller.updateUser(u,5));verify(services).updateUser(u,5);}
 @Test void deleteUserShouldDelegateToServiceAndRedirect(){assertEquals("redirect:/admin/services",controller.deleteUser(5));verify(services).deleteUser(5);}
}
