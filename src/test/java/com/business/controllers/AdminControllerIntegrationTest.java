package com.business.controllers;

import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.isNull;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import com.business.entities.Admin;
import com.business.entities.Orders;
import com.business.entities.Product;
import com.business.entities.User;
import com.business.services.AdminServices;
import com.business.services.OrderServices;
import com.business.services.ProductServices;
import com.business.services.UserServices;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(AdminController.class)
class AdminControllerIntegrationTest {
    @Autowired private MockMvc mockMvc;
    @MockBean private UserServices services;
    @MockBean private AdminServices adminServices;
    @MockBean private ProductServices productServices;
    @MockBean private OrderServices orderServices;

    @Test void adminLogin_WithValidCredentials_ShouldRedirectToAdminServices() throws Exception {
        when(adminServices.validateAdminCredentials("admin@example.com", "secret")).thenReturn(true);
        mockMvc.perform(get("/adminLogin").param("email", "admin@example.com").param("password", "secret"))
                .andExpect(status().is3xxRedirection()).andExpect(redirectedUrl("/admin/services"));
    }

    @Test void adminLogin_WithInvalidCredentials_ShouldReturnLoginViewWithError() throws Exception {
        when(adminServices.validateAdminCredentials("admin@example.com", "bad")).thenReturn(false);
        mockMvc.perform(get("/adminLogin").param("email", "admin@example.com").param("password", "bad"))
                .andExpect(status().isOk()).andExpect(view().name("Login"))
                .andExpect(model().attribute("error", "Invalid email or password"));
    }

    @Test void userLogin_WithValidCredentials_ShouldReturnBuyProductViewWithOrdersAndName() throws Exception {
        User user = new User(); user.setUname("Alice"); user.setUemail("alice@example.com");
        Orders order = new Orders(); order.setoName("Laptop");
        when(services.validateLoginCredentials("alice@example.com", "secret")).thenReturn(true);
        when(services.getUserByEmail("alice@example.com")).thenReturn(user);
        when(orderServices.getOrdersForUser(user)).thenReturn(List.of(order));
        mockMvc.perform(get("/userlogin").param("userEmail", "alice@example.com").param("userPassword", "secret"))
                .andExpect(status().isOk()).andExpect(view().name("BuyProduct"))
                .andExpect(model().attribute("orders", contains(order))).andExpect(model().attribute("name", "Alice"));
    }

    @Test void userLogin_WithInvalidCredentials_ShouldReturnLoginViewWithError() throws Exception {
        when(services.validateLoginCredentials("alice@example.com", "bad")).thenReturn(false);
        mockMvc.perform(get("/userlogin").param("userEmail", "alice@example.com").param("userPassword", "bad"))
                .andExpect(status().isOk()).andExpect(view().name("Login"))
                .andExpect(model().attribute("error2", "Invalid email or password"));
    }

    @Test void adminServices_ShouldLoadAllModelCollectionsAndReturnAdminPage() throws Exception {
        User user = new User(); Admin admin = new Admin(); Product product = new Product(); Orders order = new Orders();
        when(services.getAllUser()).thenReturn(List.of(user)); when(adminServices.getAll()).thenReturn(List.of(admin));
        when(productServices.getAllProducts()).thenReturn(List.of(product)); when(orderServices.getOrders()).thenReturn(List.of(order));
        mockMvc.perform(get("/admin/services")).andExpect(status().isOk()).andExpect(view().name("Admin_Page"))
                .andExpect(model().attribute("users", contains(user))).andExpect(model().attribute("admins", contains(admin)))
                .andExpect(model().attribute("products", contains(product))).andExpect(model().attribute("orders", contains(order)));
    }

    @Test void adminServices_WhenServiceThrowsException_ShouldReturnExceptionViewWithServerError() throws Exception {
        when(services.getAllUser()).thenThrow(new RuntimeException("load failed"));
        mockMvc.perform(get("/admin/services")).andExpect(status().isInternalServerError()).andExpect(view().name("exception"));
    }

    @Test void productSearch_WhenProductExists_ShouldReturnBuyProductWithProduct() throws Exception {
        Product product = new Product(); product.setPname("Laptop");
        when(productServices.getProductByName("Laptop")).thenReturn(product);
        when(orderServices.getOrdersForUser(isNull(User.class))).thenReturn(List.of());
        mockMvc.perform(post("/product/search").param("productName", "Laptop"))
                .andExpect(status().isOk()).andExpect(view().name("BuyProduct")).andExpect(model().attribute("product", product));
    }

    @Test void productSearch_WhenProductMissing_ShouldReturnBuyProductWithUnavailableMessage() throws Exception {
        when(productServices.getProductByName("Missing")).thenReturn(null);
        when(orderServices.getOrdersForUser(isNull(User.class))).thenReturn(List.of());
        mockMvc.perform(post("/product/search").param("productName", "Missing"))
                .andExpect(status().isOk()).andExpect(view().name("BuyProduct"))
                .andExpect(model().attribute("message", "SORRY...!  Product Unavailable"));
    }

    @Test void productOrder_ShouldCalculateTotalPersistOrderAndReturnSuccessView() throws Exception {
        mockMvc.perform(post("/product/order").param("oName", "Monitor").param("oPrice", "150.50").param("oQuantity", "2"))
                .andExpect(status().isOk()).andExpect(view().name("Order_success")).andExpect(model().attribute("amount", is(301.0)));
        ArgumentCaptor<Orders> orderCaptor = ArgumentCaptor.forClass(Orders.class);
        verify(orderServices).saveOrder(orderCaptor.capture());
        org.junit.jupiter.api.Assertions.assertEquals(301.0, orderCaptor.getValue().getTotalAmmout());
        org.junit.jupiter.api.Assertions.assertNotNull(orderCaptor.getValue().getOrderDate());
    }

    @Test void addAdmin_ShouldDelegateAndRedirect() throws Exception {
        mockMvc.perform(post("/addingAdmin").param("adminName", "Root").param("adminEmail", "root@example.com").param("adminPassword", "1234").param("adminNumber", "1111111111"))
                .andExpect(status().is3xxRedirection()).andExpect(redirectedUrl("/admin/services"));
        verify(adminServices).addAdmin(any(Admin.class));
    }

    @Test void addAdmin_WhenServiceThrowsException_ShouldReturnExceptionViewWithServerError() throws Exception {
        doThrow(new RuntimeException("admin save failed")).when(adminServices).addAdmin(any(Admin.class));
        mockMvc.perform(post("/addingAdmin").param("adminName", "Root").param("adminEmail", "root@example.com").param("adminPassword", "1234"))
                .andExpect(status().isInternalServerError()).andExpect(view().name("exception"));
    }
}
