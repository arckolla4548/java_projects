package com.business.services;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import java.util.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import com.business.entities.*;
import com.business.repositories.OrderRepository;

@SpringBootTest(classes = OrderServices.class)
class OrderServicesSpringBootIntegrationTest_AI {
 @Autowired OrderServices orderServices; @MockBean OrderRepository orderRepository;
 @Test void shouldLoadServiceBeanAndReturnOrdersFromRepository(){List<Orders> orders=Collections.singletonList(new Orders());when(orderRepository.findAll()).thenReturn(orders);assertSame(orders,orderServices.getOrders());verify(orderRepository).findAll();}
 @Test void saveOrderShouldDelegateToRepository(){Orders order=new Orders();orderServices.saveOrder(order);verify(orderRepository).save(order);}
 @Test void updateOrderShouldSetIdAndSave(){Orders order=new Orders();orderServices.updateOrder(9,order);assertEquals(9,order.getoId());verify(orderRepository).save(order);}
 @Test void getOrdersForUserShouldReturnRepositoryResult(){User user=new User();List<Orders> orders=Collections.singletonList(new Orders());when(orderRepository.findOrdersByUser(user)).thenReturn(orders);assertSame(orders,orderServices.getOrdersForUser(user));}
}
