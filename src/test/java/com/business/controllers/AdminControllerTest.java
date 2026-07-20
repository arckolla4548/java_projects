package com.business.controllers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.ui.Model;

import com.business.entities.Admin;
import com.business.entities.Orders;
import com.business.entities.Product;
import com.business.entities.User;
import com.business.loginCredentials.AdminLogin;
import com.business.loginCredentials.UserLogin;
import com.business.services.AdminServices;
import com.business.services.OrderServices;
import com.business.services.ProductServices;
import com.business.services.UserServices;

@ExtendWith(MockitoExtension.class)
class AdminControllerTest {

    @Mock
    private UserServices services;

    @Mock
    private AdminServices adminServices;

    @Mock
    private ProductServices productServices;

    @Mock
    private OrderServices orderServices;

    @Mock
    private Model model;

    @InjectMocks
    private AdminController adminController;

    @Test
    void getAllDataWhenAdminCredentialsAreValidShouldRedirectToAdminServices() {
        AdminLogin login = mock(AdminLogin.class);
        when(login.getEmail()).thenReturn("admin@example.com");
        when(login.getPassword()).thenReturn("secret");
        when(adminServices.validateAdminCredentials("admin@example.com", "secret"))
                .thenReturn(true);

        String viewName = adminController.getAllData(login, model);

        assertEquals("redirect:/admin/services", viewName);
        verify(adminServices).validateAdminCredentials("admin@example.com", "secret");
        verify(model, never()).addAttribute("error", "Invalid email or password");
    }

    @Test
    void getAllDataWhenAdminCredentialsAreInvalidShouldReturnLoginWithError() {
        AdminLogin login = mock(AdminLogin.class);
        when(login.getEmail()).thenReturn("admin@example.com");
        when(login.getPassword()).thenReturn("wrong");
        when(adminServices.validateAdminCredentials("admin@example.com", "wrong"))
                .thenReturn(false);

        String viewName = adminController.getAllData(login, model);

        assertEquals("Login", viewName);
        verify(model).addAttribute("error", "Invalid email or password");
    }

    @Test
    void getAllDataWhenAdminServiceThrowsExceptionShouldPropagateException() {
        AdminLogin login = mock(AdminLogin.class);
        when(login.getEmail()).thenReturn("admin@example.com");
        when(login.getPassword()).thenReturn("secret");
        when(adminServices.validateAdminCredentials("admin@example.com", "secret"))
                .thenThrow(new RuntimeException("database unavailable"));

        assertThrows(RuntimeException.class,
                () -> adminController.getAllData(login, model));
    }

    @Test
    void userLoginWhenCredentialsAreValidShouldLoadUserOrdersAndReturnBuyProduct() {
        UserLogin login = mock(UserLogin.class);
        User user = mock(User.class);
        Orders order = mock(Orders.class);
        List<Orders> orders = Collections.singletonList(order);

        when(login.getUserEmail()).thenReturn("user@example.com");
        when(login.getUserPassword()).thenReturn("password");
        when(services.validateLoginCredentials("user@example.com", "password"))
                .thenReturn(true);
        when(services.getUserByEmail("user@example.com")).thenReturn(user);
        when(orderServices.getOrdersForUser(user)).thenReturn(orders);
        when(user.getUname()).thenReturn("John");

        String viewName = adminController.userLogin(login, model);

        assertEquals("BuyProduct", viewName);
        verify(model).addAttribute("orders", orders);
        verify(model).addAttribute("name", "John");
    }

    @Test
    void userLoginWhenCredentialsAreInvalidShouldReturnLoginWithError() {
        UserLogin login = mock(UserLogin.class);
        when(login.getUserEmail()).thenReturn("user@example.com");
        when(login.getUserPassword()).thenReturn("bad-password");
        when(services.validateLoginCredentials("user@example.com", "bad-password"))
                .thenReturn(false);

        String viewName = adminController.userLogin(login, model);

        assertEquals("Login", viewName);
        verify(model).addAttribute("error2", "Invalid email or password");
        verify(services, never()).getUserByEmail(any());
        verify(orderServices, never()).getOrdersForUser(any());
    }

    @Test
    void userLoginWhenUserServiceThrowsExceptionShouldPropagateException() {
        UserLogin login = mock(UserLogin.class);

        when(login.getUserEmail()).thenReturn("user@example.com");
        when(login.getUserPassword()).thenReturn("password");
        when(services.validateLoginCredentials("user@example.com", "password"))
                .thenThrow(new IllegalStateException("login check failed"));

        assertThrows(IllegalStateException.class,
                () -> adminController.userLogin(login, model));
    }

    // Additional tests omitted for brevity...
}