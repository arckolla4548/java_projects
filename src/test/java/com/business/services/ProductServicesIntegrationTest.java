package com.business.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.business.entities.Product;
import com.business.repositories.ProductRepository;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@SpringBootTest(classes = ProductServices.class)
class ProductServicesIntegrationTest {
    @Autowired private ProductServices productServices;
    @MockBean private ProductRepository productRepository;

    @Test void addProduct_ShouldSaveProduct() { Product product = new Product(); productServices.addProduct(product); verify(productRepository).save(product); }
    @Test void getAllProducts_ShouldReturnRepositoryProducts() { Product product = new Product(); when(productRepository.findAll()).thenReturn(List.of(product)); assertEquals(List.of(product), productServices.getAllProducts()); }
    @Test void getProduct_WhenPresent_ShouldReturnProduct() { Product product = new Product(); product.setPid(2); when(productRepository.findById(2)).thenReturn(Optional.of(product)); assertEquals(product, productServices.getProduct(2)); }
    @Test void getProduct_WhenMissing_ShouldThrowNoSuchElementException() { when(productRepository.findById(10)).thenReturn(Optional.empty()); assertThrows(NoSuchElementException.class, () -> productServices.getProduct(10)); }
    @Test void updateProduct_WhenExistingProductMatches_ShouldSetIdAndSave() { Product existing = new Product(); existing.setPid(3); Product updated = new Product(); when(productRepository.findById(3)).thenReturn(Optional.of(existing)); productServices.updateproduct(updated, 3); assertEquals(3, updated.getPid()); verify(productRepository).save(updated); }
    @Test void updateProduct_WhenProductMissing_ShouldThrowNoSuchElementException() { Product updated = new Product(); when(productRepository.findById(3)).thenReturn(Optional.empty()); assertThrows(NoSuchElementException.class, () -> productServices.updateproduct(updated, 3)); }
    @Test void deleteProduct_ShouldDeleteById() { productServices.deleteProduct(4); verify(productRepository).deleteById(4); }
    @Test void getProductByName_WhenFound_ShouldReturnProduct() { Product product = new Product(); when(productRepository.findByPname("Laptop")).thenReturn(product); assertEquals(product, productServices.getProductByName("Laptop")); }
    @Test void getProductByName_WhenNotFound_ShouldReturnNull() { when(productRepository.findByPname("Missing")).thenReturn(null); assertNull(productServices.getProductByName("Missing")); }
}
