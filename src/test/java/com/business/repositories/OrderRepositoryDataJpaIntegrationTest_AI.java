package com.business.repositories;

import static org.junit.jupiter.api.Assertions.*;
import java.util.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.*;
import com.business.entities.*;

@DataJpaTest(properties = "spring.jpa.properties.hibernate.globally_quoted_identifiers=true")
class OrderRepositoryDataJpaIntegrationTest_AI {
 @Autowired OrderRepository orderRepository; @Autowired TestEntityManager entityManager;
 @Test void saveAndFindByIdShouldPersistOrder(){User user=createPersistedUser("user@example.com");Orders order=createOrder("Laptop",user);Orders saved=orderRepository.save(order);assertNotNull(saved);assertEquals("Laptop",orderRepository.findById(saved.getoId()).orElseThrow().getoName());}
 @Test void findOrdersByUserShouldReturnOrdersForMatchingUser(){User user=createPersistedUser("user2@example.com");orderRepository.save(createOrder("Laptop",user));orderRepository.save(createOrder("Mouse",user));List<Orders> result=orderRepository.findOrdersByUser(user);assertEquals(2,result.size());}
 @Test void findOrdersByUserShouldReturnEmptyListForUserWithoutOrders(){User user=createPersistedUser("empty@example.com");assertTrue(orderRepository.findOrdersByUser(user).isEmpty());}
 private User createPersistedUser(String email){User u=new User();u.setUname("Test User");u.setUemail(email);u.setUpassword("secret");u.setUnumber(9876543210L);return entityManager.persistAndFlush(u);} 
 private Orders createOrder(String name,User user){Orders o=new Orders();o.setoName(name);o.setoPrice(10.0);o.setoQuantity(2);o.setTotalAmmout(20.0);o.setOrderDate(new Date());o.setUser(user);return o;}
}
