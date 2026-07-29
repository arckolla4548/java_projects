package com.business.controllers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doThrow;
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
    void addProductSavesProductAndRedirectsToAdminServices() {
        Product product = new Product();

        String result = productController.addProduct(product);

        assertEquals("redirect:/admin/services", result);
        verify(productServices).addProduct(product);
    }

    @Test
    void addProductPropagatesServiceException() {
        Product product = new Product();
        RuntimeException exception = new RuntimeException("Unable to add product");
        doThrow(exception).when(productServices).addProduct(product);

        RuntimeException thrown = assertThrows(RuntimeException.class, () -> productController.addProduct(product));

        assertEquals(exception, thrown);
        verify(productServices).addProduct(product);
    }

    @Test
    void updateProductUpdatesProductAndRedirectsToAdminServices() {
        Product product = new Product();
        int productId = 10;

        String result = productController.updateProduct(product, productId);

        assertEquals("redirect:/admin/services", result);
        verify(productServices).updateproduct(product, productId);
    }

    @Test
    void updateProductPropagatesServiceException() {
        Product product = new Product();
        int productId = 10;
        RuntimeException exception = new RuntimeException("Unable to update product");
        doThrow(exception).when(productServices).updateproduct(product, productId);

        RuntimeException thrown = assertThrows(RuntimeException.class, () -> productController.updateProduct(product, productId));

        assertEquals(exception, thrown);
        verify(productServices).updateproduct(product, productId);
    }

    @Test
    void deleteDeletesProductAndRedirectsToAdminServices() {
        int productId = 10;

        String result = productController.delete(productId);

        assertEquals("redirect:/admin/services", result);
        verify(productServices).deleteProduct(productId);
    }

    @Test
    void deletePropagatesServiceException() {
        int productId = 10;
        RuntimeException exception = new RuntimeException("Unable to delete product");
        doThrow(exception).when(productServices).deleteProduct(productId);

        RuntimeException thrown = assertThrows(RuntimeException.class, () -> productController.delete(productId));

        assertEquals(exception, thrown);
        verify(productServices).deleteProduct(productId);
    }
}
