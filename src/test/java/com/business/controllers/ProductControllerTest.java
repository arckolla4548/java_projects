package com.business.controllers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.business.entities.Product;
import com.business.services.ProductServices;

@ExtendWith(MockitoExtension.class)
class ProductControllerTest {

    @Mock
    private ProductServices productServices;

    @InjectMocks
    private ProductController productController;

    @Test
    void addProduct_AddsProductAndReturnsRedirect() {
        Product product = org.mockito.Mockito.mock(Product.class);

        String viewName = productController.addProduct(product);

        assertEquals("redirect:/admin/services", viewName);
        verify(productServices).addProduct(product);
    }

    @Test
    void addProduct_WhenProductServiceThrowsException_PropagatesException() {
        Product product = org.mockito.Mockito.mock(Product.class);
        org.mockito.Mockito.doThrow(new RuntimeException("service failure")).when(productServices).addProduct(product);

        assertThrows(RuntimeException.class, () -> productController.addProduct(product));
        verify(productServices).addProduct(product);
    }

    @Test
    void updateProduct_UpdatesProductAndReturnsRedirect() {
        Product product = org.mockito.Mockito.mock(Product.class);

        String viewName = productController.updateProduct(product, 10);

        assertEquals("redirect:/admin/services", viewName);
        verify(productServices).updateproduct(product, 10);
    }

    @Test
    void updateProduct_WhenProductServiceThrowsException_PropagatesException() {
        Product product = org.mockito.Mockito.mock(Product.class);
        org.mockito.Mockito.doThrow(new RuntimeException("service failure")).when(productServices).updateproduct(product, 10);

        assertThrows(RuntimeException.class, () -> productController.updateProduct(product, 10));
        verify(productServices).updateproduct(product, 10);
    }

    @Test
    void delete_DeletesProductAndReturnsRedirect() {
        String viewName = productController.delete(10);

        assertEquals("redirect:/admin/services", viewName);
        verify(productServices).deleteProduct(10);
    }

    @Test
    void delete_WhenProductServiceThrowsException_PropagatesException() {
        org.mockito.Mockito.doThrow(new RuntimeException("service failure")).when(productServices).deleteProduct(10);

        assertThrows(RuntimeException.class, () -> productController.delete(10));
        verify(productServices).deleteProduct(10);
    }
}
