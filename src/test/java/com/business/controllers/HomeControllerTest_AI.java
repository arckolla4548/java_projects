package com.business.controllers;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import java.util.Collections;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.ui.Model;
import com.business.loginCredentials.AdminLogin;
import com.business.services.ProductServices;

@ExtendWith(MockitoExtension.class)
class HomeControllerTest_AI {
 @Mock ProductServices productServices; @Mock Model model; @InjectMocks HomeController controller;
 @Test void testShouldReturnApplicationWorkingMessage(){assertEquals("Application is working!",controller.test());}
 @Test void viewMethodsShouldReturnNames(){assertEquals("Home",controller.home());assertEquals("Locate_us",controller.location());assertEquals("About",controller.about());}
 @Test void productsShouldAddProducts(){when(productServices.getAllProducts()).thenReturn(Collections.emptyList());assertEquals("Products",controller.products(model));verify(model).addAttribute("products",Collections.emptyList());}
 @Test void loginShouldAddAdminLogin(){assertEquals("Login",controller.login(model));verify(model).addAttribute(eq("adminLogin"),any(AdminLogin.class));}
}
