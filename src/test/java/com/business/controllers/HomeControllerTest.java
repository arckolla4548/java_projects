package com.business.controllers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
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
    void testReturnsApplicationWorkingMessage() {
        String result = homeController.test();

        assertEquals("Application is working!", result);
        verifyNoInteractions(productServices, model);
    }

    @Test
    void homeReturnsHomeView() {
        String result = homeController.home();

        assertEquals("Home", result);
        verifyNoInteractions(productServices, model);
    }

    @Test
    void productsAddsProductsToModelAndReturnsProductsView() {
        List<Product> products = Collections.singletonList(new Product());
        when(productServices.getAllProducts()).thenReturn(products);

        String result = homeController.products(model);

        assertEquals("Products", result);
        verify(productServices).getAllProducts();
        verify(model).addAttribute("products", products);
    }

    @Test
    void productsPropagatesServiceException() {
        RuntimeException exception = new RuntimeException("Unable to load products");
        when(productServices.getAllProducts()).thenThrow(exception);

        RuntimeException thrown = assertThrows(RuntimeException.class, () -> homeController.products(model));

        assertEquals(exception, thrown);
        verify(productServices).getAllProducts();
    }

    @Test
    void locationReturnsLocateUsView() {
        String result = homeController.location();

        assertEquals("Locate_us", result);
        verifyNoInteractions(productServices, model);
    }

    @Test
    void aboutReturnsAboutView() {
        String result = homeController.about();

        assertEquals("About", result);
        verifyNoInteractions(productServices, model);
    }

    @Test
    void loginAddsAdminLoginToModelAndReturnsLoginView() {
        String result = homeController.login(model);

        assertEquals("Login", result);
        verify(model).addAttribute(eq("adminLogin"), any(AdminLogin.class));
        verifyNoInteractions(productServices);
    }
}
