package com.business.repositories;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import java.util.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import com.business.entities.Orders;
import com.business.entities.User;

@ExtendWith(MockitoExtension.class)
class OrderRepositoryTest_AI {
 @Mock OrderRepository repo;
 @Test void findOrdersByUserShouldReturnConfiguredOrders(){User u=new User();List<Orders> orders=Collections.singletonList(new Orders());when(repo.findOrdersByUser(u)).thenReturn(orders);assertSame(orders,repo.findOrdersByUser(u));verify(repo).findOrdersByUser(u);}
 @Test void findOrdersByUserShouldReturnEmptyListWhenConfiguredEmpty(){User u=new User();List<Orders> orders=Collections.emptyList();when(repo.findOrdersByUser(u)).thenReturn(orders);assertSame(orders,repo.findOrdersByUser(u));}
}
