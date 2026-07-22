package com.business.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.business.entities.Orders;
import com.business.entities.User;
import com.business.repositories.OrderRepository;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@SpringBootTest(classes = OrderServices.class)
class OrderServicesIntegrationTest {
    @Autowired private OrderServices orderServices;
    @MockBean private OrderRepository orderRepository;

    @Test void getOrders_ShouldReturnAllOrders() { Orders order = new Orders(); when(orderRepository.findAll()).thenReturn(List.of(order)); assertEquals(List.of(order), orderServices.getOrders()); }
    @Test void saveOrder_ShouldSaveOrder() { Orders order = new Orders(); orderServices.saveOrder(order); verify(orderRepository).save(order); }
    @Test void updateOrder_ShouldSetIdAndSave() { Orders order = new Orders(); orderServices.updateOrder(12, order); assertEquals(12, order.getoId()); verify(orderRepository).save(order); }
    @Test void deleteOrder_ShouldDeleteById() { orderServices.deleteOrder(13); verify(orderRepository).deleteById(13); }
    @Test void getOrdersForUser_ShouldDelegateToRepositoryQueryMethod() { User user = new User(); Orders order = new Orders(); when(orderRepository.findOrdersByUser(user)).thenReturn(List.of(order)); assertEquals(List.of(order), orderServices.getOrdersForUser(user)); }
}
