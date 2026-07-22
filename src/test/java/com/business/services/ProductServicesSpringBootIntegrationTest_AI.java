package com.business.services;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import java.util.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import com.business.entities.Product;
import com.business.repositories.ProductRepository;

@SpringBootTest(classes = ProductServices.class)
class ProductServicesSpringBootIntegrationTest_AI {
 @Autowired ProductServices productServices; @MockBean ProductRepository productRepository;
 @Test void shouldLoadServiceBeanAndReturnProductsFromRepository(){List<Product> products=Collections.singletonList(new Product());when(productRepository.findAll()).thenReturn(products);assertSame(products,productServices.getAllProducts());verify(productRepository).findAll();}
 @Test void getProductShouldReturnProductWhenPresent(){Product product=new Product();when(productRepository.findById(5)).thenReturn(Optional.of(product));assertSame(product,productServices.getProduct(5));}
 @Test void getProductShouldThrowWhenMissing(){when(productRepository.findById(5)).thenReturn(Optional.empty());assertThrows(NoSuchElementException.class,()->productServices.getProduct(5));}
 @Test void updateProductShouldSetIdAndSaveWhenExistingProductMatchesId(){Product existing=new Product();existing.setPid(5);Product update=new Product();when(productRepository.findById(5)).thenReturn(Optional.of(existing));productServices.updateproduct(update,5);assertEquals(5,update.getPid());verify(productRepository).save(update);}
 @Test void getProductByNameShouldReturnProductWhenFound(){Product product=new Product();when(productRepository.findByPname("Laptop")).thenReturn(product);assertSame(product,productServices.getProductByName("Laptop"));}
 @Test void getProductByNameShouldReturnNullWhenMissing(){when(productRepository.findByPname("Missing")).thenReturn(null);assertNull(productServices.getProductByName("Missing"));}
}
