package com.business.services;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import java.util.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import com.business.entities.Product;
import com.business.repositories.ProductRepository;

@ExtendWith(MockitoExtension.class)
class ProductServicesTest_AI {
 @Mock ProductRepository repo; @InjectMocks ProductServices service;
 @Test void addAndGetAll(){Product p=new Product();service.addProduct(p);verify(repo).save(p);List<Product> list=Collections.singletonList(p);when(repo.findAll()).thenReturn(list);assertSame(list,service.getAllProducts());}
 @Test void getProduct(){Product p=new Product();when(repo.findById(3)).thenReturn(Optional.of(p));assertSame(p,service.getProduct(3));when(repo.findById(4)).thenReturn(Optional.empty());assertThrows(NoSuchElementException.class,()->service.getProduct(4));}
 @Test void updateDeleteAndFindByName(){Product existing=new Product();existing.setPid(3);Product update=new Product();when(repo.findById(3)).thenReturn(Optional.of(existing));service.updateproduct(update,3);assertEquals(3,update.getPid());verify(repo).save(update);service.deleteProduct(3);verify(repo).deleteById(3);when(repo.findByPname("Laptop")).thenReturn(existing);assertSame(existing,service.getProductByName("Laptop"));when(repo.findByPname("Missing")).thenReturn(null);assertNull(service.getProductByName("Missing"));}
}
