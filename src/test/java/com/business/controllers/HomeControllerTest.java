package com.business.controllers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.verifyNoMoreInteractions;
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
    void testShouldReturnApplicationWorkingMessage() {
        String result = homeController.test();

        assertEquals("Application is working!", result);
        verifyNoInteractions(productServices, model);
    }

    @Test
    void homeShouldReturnHomeViewName() {
        String result = homeController.home();

        assertEquals("Home", result);
        verifyNoInteractions(productServices, model);
    }

    @Test
    void productsShouldAddAllProductsToModelAndReturnProductsViewName() {
        List<Product> products = Collections.emptyList();
        when(productServices.getAllProducts()).thenReturn(products);

        String result = homeController.products(model);

        assertEquals("Products", result);
        verify(productServices).getAllProducts();
        verify(model).addAttribute("products", products);
        verifyNoMoreInteractions(productServices, model);
    }

    @Test
    void locationShouldReturnLocateUsViewName() {
        String result = homeController.location();

        assertEquals("Locate_us", result);
        verifyNoInteractions(productServices, model);
    }

    @Test
    void aboutShouldReturnAboutViewName() {
        String result = homeController.about();

        assertEquals("About", result);
        verifyNoInteractions(productServices, model);
    }

    @Test
    void loginShouldAddAdminLoginToModelAndReturnLoginViewName() {
        String result = homeController.login(model);

        assertEquals("Login", result);
        verify(model).addAttribute(org.mockito.ArgumentMatchers.eq("adminLogin"), org.mockito.ArgumentMatchers.any(AdminLogin.class));
        verifyNoMoreInteractions(model);
        verifyNoInteractions(productServices);
    }
}
