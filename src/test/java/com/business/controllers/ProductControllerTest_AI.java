package com.business.controllers;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import com.business.entities.Product;
import com.business.services.ProductServices;

@ExtendWith(MockitoExtension.class)
class ProductControllerTest_AI {
 @Mock ProductServices productServices; @InjectMocks ProductController controller;
 @Test void addProductShouldDelegateToServiceAndRedirect(){Product p=new Product();assertEquals("redirect:/admin/services",controller.addProduct(p));verify(productServices).addProduct(p);}
 @Test void updateProductShouldDelegateToServiceAndRedirect(){Product p=new Product();assertEquals("redirect:/admin/services",controller.updateProduct(p,10));verify(productServices).updateproduct(p,10);}
 @Test void deleteShouldDelegateToServiceAndRedirect(){assertEquals("redirect:/admin/services",controller.delete(10));verify(productServices).deleteProduct(10);}
}
