package com.business.controllers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.ui.Model;

import com.business.entities.Product;
import com.business.loginCredentials.AdminLogin;
import com.business.services.ProductServices;

@ExtendWith(MockitoExtension.class)
class HomeControllerTest {

    @Mock
    private ProductServices productServices;

    @Mock
    private Model model;

    @InjectMocks
    private HomeController homeController;

    @Test
    void test_ReturnsApplicationWorkingMessage() {
        String response = homeController.test();

        assertEquals("Application is working!", response);
    }

    @Test
    void home_ReturnsHomeView() {
        String viewName = homeController.home();

        assertEquals("Home", viewName);
    }

    @Test
    void products_WhenProductsExist_AddsProductsToModelAndReturnsProductsView() {
        List<Product> products = Collections.singletonList(org.mockito.Mockito.mock(Product.class));
        when(productServices.getAllProducts()).thenReturn(products);

        String viewName = homeController.products(model);

        assertEquals("Products", viewName);
        verify(productServices).getAllProducts();
        verify(model).addAttribute("products", products);
    }

    @Test
    void products_WhenNoProductsExist_AddsEmptyListToModelAndReturnsProductsView() {
        List<Product> products = Collections.emptyList();
        when(productServices.getAllProducts()).thenReturn(products);

        String viewName = homeController.products(model);

        assertEquals("Products", viewName);
        verify(productServices).getAllProducts();
        verify(model).addAttribute("products", products);
    }

    @Test
    void products_WhenProductServiceThrowsException_PropagatesException() {
        when(productServices.getAllProducts()).thenThrow(new RuntimeException("service failure"));

        assertThrows(RuntimeException.class, () -> homeController.products(model));
        verify(productServices).getAllProducts();
    }

    @Test
    void location_ReturnsLocateUsView() {
        String viewName = homeController.location();

        assertEquals("Locate_us", viewName);
    }

    @Test
    void about_ReturnsAboutView() {
        String viewName = homeController.about();

        assertEquals("About", viewName);
    }

    @Test
    void login_AddsAdminLoginToModelAndReturnsLoginView() {
        String viewName = homeController.login(model);

        assertEquals("Login", viewName);
        verify(model).addAttribute(org.mockito.Mockito.eq("adminLogin"), org.mockito.Mockito.any(AdminLogin.class));
    }
}
