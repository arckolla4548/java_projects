package com.business.controllers;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import java.util.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.ui.Model;
import com.business.entities.*;
import com.business.loginCredentials.*;
import com.business.services.*;

@ExtendWith(MockitoExtension.class)
class AdminControllerTest_AI {
 @Mock UserServices services; @Mock AdminServices adminServices; @Mock ProductServices productServices; @Mock OrderServices orderServices; @Mock Model model; @InjectMocks AdminController controller;
 @Test void adminLoginValidRedirects(){AdminLogin l=new AdminLogin();l.setEmail("admin@example.com");l.setPassword("secret");when(adminServices.validateAdminCredentials("admin@example.com","secret")).thenReturn(true);assertEquals("redirect:/admin/services",controller.getAllData(l,model));}
 @Test void adminLoginInvalidReturnsLogin(){AdminLogin l=new AdminLogin();l.setEmail("admin@example.com");l.setPassword("bad");when(adminServices.validateAdminCredentials("admin@example.com","bad")).thenReturn(false);assertEquals("Login",controller.getAllData(l,model));verify(model).addAttribute("error","Invalid email or password");}
 @Test void returnBackPopulatesModel(){when(services.getAllUser()).thenReturn(Collections.emptyList());when(adminServices.getAll()).thenReturn(Collections.emptyList());when(productServices.getAllProducts()).thenReturn(Collections.emptyList());when(orderServices.getOrders()).thenReturn(Collections.emptyList());assertEquals("Admin_Page",controller.returnBack(model));}
 @Test void simpleViews(){assertEquals("Add_Admin",controller.addAdminPage());assertEquals("Add_Product",controller.addProduct());assertEquals("Add_User",controller.addUser());}
 @Test void orderHandlerCalculatesAndSaves(){Orders o=new Orders();o.setoPrice(10);o.setoQuantity(3);assertEquals("Order_success",controller.orderHandler(o,model));assertEquals(30,o.getTotalAmmout());verify(orderServices).saveOrder(o);}
}
