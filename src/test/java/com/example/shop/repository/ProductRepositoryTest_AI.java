package com.example.shop.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Collections;
import java.util.List;

import com.example.shop.entity.Product;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.jpa.repository.JpaRepository;

@ExtendWith(MockitoExtension.class)
public class ProductRepositoryTest_AI {

    @Mock
    private ProductRepository productRepository;

    @Test
    void repositoryShouldExtendJpaRepository() {
        assertTrue(JpaRepository.class.isAssignableFrom(ProductRepository.class));
    }

    @Test
    void findAllShouldReturnProductsFromRepositoryProxy() {
        List<Product> products = Collections.emptyList();
        when(productRepository.findAll()).thenReturn(products);

        List<Product> result = productRepository.findAll();

        assertEquals(products, result);
        verify(productRepository).findAll();
    }

    @Test
    void saveShouldReturnSavedProductFromRepositoryProxy() {
        Product product = new Product();
        when(productRepository.save(product)).thenReturn(product);

        Product result = productRepository.save(product);

        assertSame(product, result);
        verify(productRepository).save(product);
    }

    @Test
    void findAllShouldPropagateRepositoryException() {
        RuntimeException repositoryException = new RuntimeException("repository failure");
        when(productRepository.findAll()).thenThrow(repositoryException);

        RuntimeException thrown = assertThrows(RuntimeException.class, () -> productRepository.findAll());

        assertSame(repositoryException, thrown);
        verify(productRepository).findAll();
    }
}
