package com.business.controllers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.ui.Model;

import com.business.entities.Admin;
import com.business.entities.Orders;
import com.business.entities.Product;
import com.business.entities.User;
import com.business.loginCredentials.AdminLogin;
import com.business.loginCredentials.UserLogin;
import com.business.services.AdminServices;
import com.business.services.OrderServices;
import com.business.services.ProductServices;
import com.business.services.UserServices;

@ExtendWith(MockitoExtension.class)
class AdminControllerTest {

    @Mock
    private UserServices services;

    @Mock
    private AdminServices adminServices;

    @Mock
    private ProductServices productServices;

    @Mock
    private OrderServices orderServices;

    @Mock
    private Model model;

    @InjectMocks
    private AdminController adminController;

    @Test
    void getAllData_WhenAdminCredentialsAreValid_ReturnsRedirectToAdminServices() {
        AdminLogin login = new AdminLogin();
        login.setEmail("admin@example.com");
        login.setPassword("password");
        when(adminServices.validateAdminCredentials("admin@example.com", "password")).thenReturn(true);

        String viewName = adminController.getAllData(login, model);

        assertEquals("redirect:/admin/services", viewName);
        verify(adminServices).validateAdminCredentials("admin@example.com", "password");
        verify(model, never()).addAttribute(eq("error"), any());
    }

    @Test
    void getAllData_WhenAdminCredentialsAreInvalid_ReturnsLoginWithError() {
        AdminLogin login = new AdminLogin();
        login.setEmail("admin@example.com");
        login.setPassword("wrong-password");
        when(adminServices.validateAdminCredentials("admin@example.com", "wrong-password")).thenReturn(false);

        String viewName = adminController.getAllData(login, model);

        assertEquals("Login", viewName);
        verify(adminServices).validateAdminCredentials("admin@example.com", "wrong-password");
        verify(model).addAttribute("error", "Invalid email or password");
    }

    @Test
    void getAllData_WhenAdminServiceThrowsException_PropagatesException() {
        AdminLogin login = new AdminLogin();
        login.setEmail("admin@example.com");
        login.setPassword("password");
        when(adminServices.validateAdminCredentials("admin@example.com", "password"))
                .thenThrow(new RuntimeException("service failure"));

        assertThrows(RuntimeException.class, () -> adminController.getAllData(login, model));
        verify(adminServices).validateAdminCredentials("admin@example.com", "password");
    }

    @Test
    void userLogin_WhenCredentialsAreValid_ReturnsBuyProductWithOrdersAndName() {
        UserLogin login = new UserLogin();
        login.setUserEmail("user@example.com");
        login.setUserPassword("password");
        User user = org.mockito.Mockito.mock(User.class);
        List<Orders> orders = Collections.singletonList(org.mockito.Mockito.mock(Orders.class));
        when(user.getUname()).thenReturn("Test User");
        when(services.validateLoginCredentials("user@example.com", "password")).thenReturn(true);
        when(services.getUserByEmail("user@example.com")).thenReturn(user);
        when(orderServices.getOrdersForUser(user)).thenReturn(orders);

        String viewName = adminController.userLogin(login, model);

        assertEquals("BuyProduct", viewName);
        verify(services).validateLoginCredentials("user@example.com", "password");
        verify(services).getUserByEmail("user@example.com");
        verify(orderServices).getOrdersForUser(user);
        verify(model).addAttribute("orders", orders);
        verify(model).addAttribute("name", "Test User");
    }

    @Test
    void userLogin_WhenCredentialsAreInvalid_ReturnsLoginWithError() {
        UserLogin login = new UserLogin();
        login.setUserEmail("user@example.com");
        login.setUserPassword("wrong-password");
        when(services.validateLoginCredentials("user@example.com", "wrong-password")).thenReturn(false);

        String viewName = adminController.userLogin(login, model);

        assertEquals("Login", viewName);
        verify(services).validateLoginCredentials("user@example.com", "wrong-password");
        verify(model).addAttribute("error2", "Invalid email or password");
        verify(services, never()).getUserByEmail(any());
    }

    @Test
    void seachHandler_WhenProductExists_ReturnsBuyProductWithProductAndOrders() {
        User currentUser = org.mockito.Mockito.mock(User.class);
        Product product = org.mockito.Mockito.mock(Product.class);
        List<Orders> orders = Collections.singletonList(org.mockito.Mockito.mock(Orders.class));
        ReflectionTestUtils.setField(adminController, "user", currentUser);
        when(productServices.getProductByName("Laptop")).thenReturn(product);
        when(orderServices.getOrdersForUser(currentUser)).thenReturn(orders);

        String viewName = adminController.seachHandler("Laptop", model);

        assertEquals("BuyProduct", viewName);
        verify(productServices).getProductByName("Laptop");
        verify(orderServices).getOrdersForUser(currentUser);
        verify(model).addAttribute("orders", orders);
        verify(model).addAttribute("product", product);
        verify(model, never()).addAttribute(eq("message"), any());
    }

    @Test
    void seachHandler_WhenProductDoesNotExist_ReturnsBuyProductWithUnavailableMessage() {
        User currentUser = org.mockito.Mockito.mock(User.class);
        List<Orders> orders = Collections.emptyList();
        ReflectionTestUtils.setField(adminController, "user", currentUser);
        when(productServices.getProductByName("Missing Product")).thenReturn(null);
        when(orderServices.getOrdersForUser(currentUser)).thenReturn(orders);

        String viewName = adminController.seachHandler("Missing Product", model);

        assertEquals("BuyProduct", viewName);
        verify(productServices).getProductByName("Missing Product");
        verify(model).addAttribute("message", "SORRY...!  Product Unavailable");
        verify(model).addAttribute("product", null);
        verify(orderServices).getOrdersForUser(currentUser);
        verify(model).addAttribute("orders", orders);
    }

    @Test
    void returnBack_AddsAdminPageDataAndReturnsAdminPage() {
        List<User> users = Collections.singletonList(org.mockito.Mockito.mock(User.class));
        List<Admin> admins = Collections.singletonList(org.mockito.Mockito.mock(Admin.class));
        List<Product> products = Collections.singletonList(org.mockito.Mockito.mock(Product.class));
        List<Orders> orders = Collections.singletonList(org.mockito.Mockito.mock(Orders.class));
        when(services.getAllUser()).thenReturn(users);
        when(adminServices.getAll()).thenReturn(admins);
        when(productServices.getAllProducts()).thenReturn(products);
        when(orderServices.getOrders()).thenReturn(orders);

        String viewName = adminController.returnBack(model);

        assertEquals("Admin_Page", viewName);
        verify(services).getAllUser();
        verify(adminServices).getAll();
        verify(productServices).getAllProducts();
        verify(orderServices).getOrders();
        verify(model).addAttribute("users", users);
        verify(model).addAttribute("admins", admins);
        verify(model).addAttribute("products", products);
        verify(model).addAttribute("orders", orders);
    }

    @Test
    void addAdminPage_ReturnsAddAdminView() {
        String viewName = adminController.addAdminPage();

        assertEquals("Add_Admin", viewName);
    }

    @Test
    void addAdmin_AddsAdminAndReturnsRedirect() {
        Admin admin = org.mockito.Mockito.mock(Admin.class);

        String viewName = adminController.addAdmin(admin);

        assertEquals("redirect:/admin/services", viewName);
        verify(adminServices).addAdmin(admin);
    }

    @Test
    void addAdmin_WhenServiceThrowsException_PropagatesException() {
        Admin admin = org.mockito.Mockito.mock(Admin.class);
        org.mockito.Mockito.doThrow(new RuntimeException("service failure")).when(adminServices).addAdmin(admin);

        assertThrows(RuntimeException.class, () -> adminController.addAdmin(admin));
        verify(adminServices).addAdmin(admin);
    }

    @Test
    void update_AddsAdminToModelAndReturnsUpdateAdminView() {
        Admin admin = org.mockito.Mockito.mock(Admin.class);
        when(adminServices.getAdmin(10)).thenReturn(admin);

        String viewName = adminController.update(10, model);

        assertEquals("Update_Admin", viewName);
        verify(adminServices).getAdmin(10);
        verify(model).addAttribute("admin", admin);
    }

    @Test
    void updateAdmin_UpdatesAdminAndReturnsRedirect() {
        Admin admin = org.mockito.Mockito.mock(Admin.class);

        String viewName = adminController.updateAdmin(admin, 10);

        assertEquals("redirect:/admin/services", viewName);
        verify(adminServices).update(admin, 10);
    }

    @Test
    void deleteAdmin_DeletesAdminAndReturnsRedirect() {
        String viewName = adminController.deleteAdmin(10);

        assertEquals("redirect:/admin/services", viewName);
        verify(adminServices).delete(10);
    }

    @Test
    void addProduct_ReturnsAddProductView() {
        String viewName = adminController.addProduct();

        assertEquals("Add_Product", viewName);
    }

    @Test
    void updateProduct_AddsProductToModelAndReturnsUpdateProductView() {
        Product product = org.mockito.Mockito.mock(Product.class);
        when(productServices.getProduct(20)).thenReturn(product);

        String viewName = adminController.updateProduct(20, model);

        assertEquals("Update_Product", viewName);
        verify(productServices).getProduct(20);
        verify(model).addAttribute("product", product);
    }

    @Test
    void addUser_ReturnsAddUserView() {
        String viewName = adminController.addUser();

        assertEquals("Add_User", viewName);
    }

    @Test
    void updateUserPage_AddsUserToModelAndReturnsUpdateUserView() {
        User user = org.mockito.Mockito.mock(User.class);
        when(services.getUser(30)).thenReturn(user);

        String viewName = adminController.updateUserPage(30, model);

        assertEquals("Update_User", viewName);
        verify(services).getUser(30);
        verify(model).addAttribute("user", user);
    }

    @Test
    void orderHandler_SavesOrderAndReturnsOrderSuccess() {
        User currentUser = org.mockito.Mockito.mock(User.class);
        Orders order = org.mockito.Mockito.mock(Orders.class);
        when(order.getoPrice()).thenReturn(25.0);
        when(order.getoQuantity()).thenReturn(2);
        ReflectionTestUtils.setField(adminController, "user", currentUser);

        String viewName = adminController.orderHandler(order, model);

        assertEquals("Order_success", viewName);
        verify(order).getoPrice();
        verify(order).getoQuantity();
        verify(order).setTotalAmmout(any(Double.class));
        verify(order).setUser(currentUser);
        verify(order).setOrderDate(any(java.util.Date.class));
        verify(orderServices).saveOrder(order);
        verify(model).addAttribute(eq("amount"), any(Double.class));
    }

    @Test
    void back_AddsCurrentUserOrdersAndReturnsBuyProduct() {
        User currentUser = org.mockito.Mockito.mock(User.class);
        List<Orders> orders = Collections.singletonList(org.mockito.Mockito.mock(Orders.class));
        ReflectionTestUtils.setField(adminController, "user", currentUser);
        when(orderServices.getOrdersForUser(currentUser)).thenReturn(orders);

        String viewName = adminController.back(model);

        assertEquals("BuyProduct", viewName);
        verify(orderServices).getOrdersForUser(currentUser);
        verify(model).addAttribute("orders", orders);
    }
}
