package com.business.services;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import java.util.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import com.business.entities.*;
import com.business.repositories.OrderRepository;

@ExtendWith(MockitoExtension.class)
class OrderServicesTest_AI {
 @Mock OrderRepository repo; @InjectMocks OrderServices service;
 @Test void getOrdersAndSave(){List<Orders> list=Collections.singletonList(new Orders());when(repo.findAll()).thenReturn(list);assertSame(list,service.getOrders());Orders o=new Orders();service.saveOrder(o);verify(repo).save(o);}
 @Test void updateDeleteAndGetForUser(){Orders o=new Orders();service.updateOrder(8,o);assertEquals(8,o.getoId());verify(repo).save(o);service.deleteOrder(8);verify(repo).deleteById(8);User u=new User();List<Orders> list=Collections.singletonList(o);when(repo.findOrdersByUser(u)).thenReturn(list);assertSame(list,service.getOrdersForUser(u));}
}
