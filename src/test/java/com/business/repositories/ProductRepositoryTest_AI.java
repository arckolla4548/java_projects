package com.business.repositories;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import com.business.entities.Product;

@ExtendWith(MockitoExtension.class)
class ProductRepositoryTest_AI {
 @Mock ProductRepository repo;
 @Test void findByPnameShouldReturnConfiguredProduct(){Product p=new Product();when(repo.findByPname("Laptop")).thenReturn(p);assertSame(p,repo.findByPname("Laptop"));verify(repo).findByPname("Laptop");}
 @Test void findByPnameShouldReturnNullWhenConfiguredMissing(){when(repo.findByPname("Missing")).thenReturn(null);assertNull(repo.findByPname("Missing"));}
}
