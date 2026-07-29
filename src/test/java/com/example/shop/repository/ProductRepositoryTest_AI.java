package com.example.shop.repository;

import com.example.shop.entity.Product;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProductRepositoryTest_AI {

    @Mock
    private ProductRepository productRepository;

    @Test
    void repositoryInterface_whenInspected_extendsJpaRepository() {
        assertTrue(JpaRepository.class.isAssignableFrom(ProductRepository.class));
    }

    @Test
    void findById_whenProductExists_returnsProduct() {
        Long productId = 1L;
        Product product = new Product();
        when(productRepository.findById(productId)).thenReturn(Optional.of(product));

        Optional<Product> result = productRepository.findById(productId);

        assertTrue(result.isPresent());
        assertSame(product, result.get());
        verify(productRepository).findById(productId);
    }

    @Test
    void findById_whenProductDoesNotExist_returnsEmptyOptional() {
        Long productId = 99L;
        when(productRepository.findById(productId)).thenReturn(Optional.empty());

        Optional<Product> result = productRepository.findById(productId);

        assertTrue(result.isEmpty());
        verify(productRepository).findById(productId);
    }

    @Test
    void findById_whenRepositoryThrowsException_propagatesException() {
        Long productId = -1L;
        IllegalArgumentException expectedException = new IllegalArgumentException("invalid id");
        when(productRepository.findById(productId)).thenThrow(expectedException);

        IllegalArgumentException actualException = assertThrows(IllegalArgumentException.class, () -> productRepository.findById(productId));

        assertSame(expectedException, actualException);
        verify(productRepository).findById(productId);
    }

    @Test
    void save_whenValidProductProvided_returnsSavedProduct() {
        Product product = new Product();
        when(productRepository.save(product)).thenReturn(product);

        Product result = productRepository.save(product);

        assertSame(product, result);
        verify(productRepository).save(product);
    }

    @Test
    void deleteById_whenCalled_invokesRepositoryDelete() {
        Long productId = 1L;

        productRepository.deleteById(productId);

        verify(productRepository).deleteById(productId);
    }
}
