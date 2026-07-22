package com.business.repositories;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import com.business.entities.Product;

@DataJpaTest(properties = "spring.jpa.properties.hibernate.globally_quoted_identifiers=true")
class ProductRepositoryDataJpaIntegrationTest_AI {
 @Autowired ProductRepository productRepository;
 @Test void saveAndFindByIdShouldPersistProduct(){Product p=new Product();p.setPname("Laptop");p.setPprice(1200.0);p.setPdescription("Business laptop");Product saved=productRepository.save(p);assertNotNull(saved);assertEquals("Laptop",productRepository.findById(saved.getPid()).orElseThrow().getPname());}
 @Test void findByPnameShouldReturnMatchingProduct(){Product p=new Product();p.setPname("Laptop");p.setPprice(1200.0);p.setPdescription("Business laptop");productRepository.save(p);Product result=productRepository.findByPname("Laptop");assertNotNull(result);assertEquals(1200.0,result.getPprice());}
 @Test void findByPnameShouldReturnNullWhenMissing(){assertNull(productRepository.findByPname("Missing"));}
}
