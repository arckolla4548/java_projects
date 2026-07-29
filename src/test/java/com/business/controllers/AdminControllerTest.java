package com.business.controllers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyDouble;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.clearInvocations;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
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
    void getAllDataWithValidCredentialsRedirectsToAdminServices() {
        AdminLogin login = new AdminLogin();
        when(adminServices.validateAdminCredentials(login.getEmail(), login.getPassword())).thenReturn(true);

        String result = adminController.getAllData(login, model);

        assertEquals("redirect:/admin/services", result);
        verify(adminServices).validateAdminCredentials(login.getEmail(), login.getPassword());
        verifyNoInteractions(services, productServices, orderServices, model);
    }

    @Test
    void getAllDataWithInvalidCredentialsReturnsLoginViewWithError() {
        AdminLogin login = new AdminLogin();
        when(adminServices.validateAdminCredentials(login.getEmail(), login.getPassword())).thenReturn(false);

        String result = adminController.getAllData(login, model);

        assertEquals("Login", result);
        verify(adminServices).validateAdminCredentials(login.getEmail(), login.getPassword());
        verify(model).addAttribute("error", "Invalid email or password");
        verifyNoInteractions(services, productServices, orderServices);
    }

    @Test
    void getAllDataPropagatesServiceException() {
        AdminLogin login = new AdminLogin();
        RuntimeException exception = new RuntimeException("Unable to validate admin");
        when(adminServices.validateAdminCredentials(login.getEmail(), login.getPassword())).thenThrow(exception);

        RuntimeException thrown = assertThrows(RuntimeException.class, () -> adminController.getAllData(login, model));

        assertEquals(exception, thrown);
        verify(adminServices).validateAdminCredentials(login.getEmail(), login.getPassword());
    }

    @Test
    void userLoginWithValidCredentialsAddsOrdersAndNameAndReturnsBuyProductView() {
        UserLogin login = new UserLogin();
        User user = new User();
        List<Orders> orders = Collections.singletonList(new Orders());
        when(services.validateLoginCredentials(login.getUserEmail(), login.getUserPassword())).thenReturn(true);
        when(services.getUserByEmail(login.getUserEmail())).thenReturn(user);
        when(orderServices.getOrdersForUser(user)).thenReturn(orders);

        String result = adminController.userLogin(login, model);

        assertEquals("BuyProduct", result);
        verify(services).validateLoginCredentials(login.getUserEmail(), login.getUserPassword());
        verify(services).getUserByEmail(login.getUserEmail());
        verify(orderServices).getOrdersForUser(user);
        verify(model).addAttribute("orders", orders);
        verify(model).addAttribute("name", user.getUname());
        verifyNoInteractions(adminServices, productServices);
    }

    @Test
    void userLoginWithInvalidCredentialsReturnsLoginViewWithError() {
        UserLogin login = new UserLogin();
        when(services.validateLoginCredentials(login.getUserEmail(), login.getUserPassword())).thenReturn(false);

        String result = adminController.userLogin(login, model);

        assertEquals("Login", result);
        verify(services).validateLoginCredentials(login.getUserEmail(), login.getUserPassword());
        verify(model).addAttribute("error2", "Invalid email or password");
        verifyNoInteractions(adminServices, productServices, orderServices);
    }

    @Test
    void userLoginPropagatesServiceException() {
        UserLogin login = new UserLogin();
        RuntimeException exception = new RuntimeException("Unable to validate user");
        when(services.validateLoginCredentials(login.getUserEmail(), login.getUserPassword())).thenThrow(exception);

        RuntimeException thrown = assertThrows(RuntimeException.class, () -> adminController.userLogin(login, model));

        assertEquals(exception, thrown);
        verify(services).validateLoginCredentials(login.getUserEmail(), login.getUserPassword());
    }

    @Test
    void seachHandlerWithUnavailableProductAddsMessageAndReturnsBuyProductView() {
        User user = authenticateUser();
        List<Orders> orders = Collections.singletonList(new Orders());
        String productName = "missing";
        clearInvocations(services, orderServices, model);
        when(productServices.getProductByName(productName)).thenReturn(null);
        when(orderServices.getOrdersForUser(user)).thenReturn(orders);

        String result = adminController.seachHandler(productName, model);

        assertEquals("BuyProduct", result);
        verify(productServices).getProductByName(productName);
        verify(orderServices).getOrdersForUser(user);
        verify(model).addAttribute("message", "SORRY...!  Product Unavailable");
        verify(model).addAttribute("product", null);
        verify(model).addAttribute("orders", orders);
    }

    @Test
    void seachHandlerWithAvailableProductAddsProductAndReturnsBuyProductView() {
        User user = authenticateUser();
        Product product = new Product();
        List<Orders> orders = Collections.singletonList(new Orders());
        String productName = "phone";
        clearInvocations(services, orderServices, model);
        when(productServices.getProductByName(productName)).thenReturn(product);
        when(orderServices.getOrdersForUser(user)).thenReturn(orders);

        String result = adminController.seachHandler(productName, model);

        assertEquals("BuyProduct", result);
        verify(productServices).getProductByName(productName);
        verify(orderServices).getOrdersForUser(user);
        verify(model).addAttribute("orders", orders);
        verify(model).addAttribute("product", product);
    }

    @Test
    void seachHandlerPropagatesServiceException() {
        String productName = "phone";
        RuntimeException exception = new RuntimeException("Unable to search product");
        when(productServices.getProductByName(productName)).thenThrow(exception);

        RuntimeException thrown = assertThrows(RuntimeException.class, () -> adminController.seachHandler(productName, model));

        assertEquals(exception, thrown);
        verify(productServices).getProductByName(productName);
    }

    @Test
    void returnBackAddsUsersAdminsProductsAndOrdersAndReturnsAdminPage() {
        List<User> users = Collections.singletonList(new User());
        List<Admin> admins = Collections.singletonList(new Admin());
        List<Product> products = Collections.singletonList(new Product());
        List<Orders> orders = Collections.singletonList(new Orders());
        when(services.getAllUser()).thenReturn(users);
        when(adminServices.getAll()).thenReturn(admins);
        when(productServices.getAllProducts()).thenReturn(products);
        when(orderServices.getOrders()).thenReturn(orders);

        String result = adminController.returnBack(model);

        assertEquals("Admin_Page", result);
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
    void returnBackPropagatesServiceException() {
        RuntimeException exception = new RuntimeException("Unable to load users");
        when(services.getAllUser()).thenThrow(exception);

        RuntimeException thrown = assertThrows(RuntimeException.class, () -> adminController.returnBack(model));

        assertEquals(exception, thrown);
        verify(services).getAllUser();
    }

    @Test
    void addAdminPageReturnsAddAdminView() {
        String result = adminController.addAdminPage();

        assertEquals("Add_Admin", result);
        verifyNoInteractions(services, adminServices, productServices, orderServices, model);
    }

    @Test
    void addAdminSavesAdminAndRedirectsToAdminServices() {
        Admin admin = new Admin();

        String result = adminController.addAdmin(admin);

        assertEquals("redirect:/admin/services", result);
        verify(adminServices).addAdmin(admin);
        verifyNoInteractions(services, productServices, orderServices, model);
    }

    @Test
    void addAdminPropagatesServiceException() {
        Admin admin = new Admin();
        RuntimeException exception = new RuntimeException("Unable to add admin");
        doThrow(exception).when(adminServices).addAdmin(admin);

        RuntimeException thrown = assertThrows(RuntimeException.class, () -> adminController.addAdmin(admin));

        assertEquals(exception, thrown);
        verify(adminServices).addAdmin(admin);
    }

    @Test
    void updateAddsAdminToModelAndReturnsUpdateAdminView() {
        int adminId = 1;
        Admin admin = new Admin();
        when(adminServices.getAdmin(adminId)).thenReturn(admin);

        String result = adminController.update(adminId, model);

        assertEquals("Update_Admin", result);
        verify(adminServices).getAdmin(adminId);
        verify(model).addAttribute("admin", admin);
    }

    @Test
    void updatePropagatesServiceException() {
        int adminId = 1;
        RuntimeException exception = new RuntimeException("Unable to load admin");
        when(adminServices.getAdmin(adminId)).thenThrow(exception);

        RuntimeException thrown = assertThrows(RuntimeException.class, () -> adminController.update(adminId, model));

        assertEquals(exception, thrown);
        verify(adminServices).getAdmin(adminId);
    }

    @Test
    void updateAdminUpdatesAdminAndRedirectsToAdminServices() {
        Admin admin = new Admin();
        int adminId = 1;

        String result = adminController.updateAdmin(admin, adminId);

        assertEquals("redirect:/admin/services", result);
        verify(adminServices).update(admin, adminId);
    }

    @Test
    void updateAdminPropagatesServiceException() {
        Admin admin = new Admin();
        int adminId = 1;
        RuntimeException exception = new RuntimeException("Unable to update admin");
        doThrow(exception).when(adminServices).update(admin, adminId);

        RuntimeException thrown = assertThrows(RuntimeException.class, () -> adminController.updateAdmin(admin, adminId));

        assertEquals(exception, thrown);
        verify(adminServices).update(admin, adminId);
    }

    @Test
    void deleteAdminDeletesAdminAndRedirectsToAdminServices() {
        int adminId = 1;

        String result = adminController.deleteAdmin(adminId);

        assertEquals("redirect:/admin/services", result);
        verify(adminServices).delete(adminId);
    }

    @Test
    void deleteAdminPropagatesServiceException() {
        int adminId = 1;
        RuntimeException exception = new RuntimeException("Unable to delete admin");
        doThrow(exception).when(adminServices).delete(adminId);

        RuntimeException thrown = assertThrows(RuntimeException.class, () -> adminController.deleteAdmin(adminId));

        assertEquals(exception, thrown);
        verify(adminServices).delete(adminId);
    }

    @Test
    void addProductReturnsAddProductView() {
        String result = adminController.addProduct();

        assertEquals("Add_Product", result);
        verifyNoInteractions(services, adminServices, productServices, orderServices, model);
    }

    @Test
    void updateProductAddsProductToModelAndReturnsUpdateProductView() {
        int productId = 2;
        Product product = new Product();
        when(productServices.getProduct(productId)).thenReturn(product);

        String result = adminController.updateProduct(productId, model);

        assertEquals("Update_Product", result);
        verify(productServices).getProduct(productId);
        verify(model).addAttribute("product", product);
    }

    @Test
    void updateProductPropagatesServiceException() {
        int productId = 2;
        RuntimeException exception = new RuntimeException("Unable to load product");
        when(productServices.getProduct(productId)).thenThrow(exception);

        RuntimeException thrown = assertThrows(RuntimeException.class, () -> adminController.updateProduct(productId, model));

        assertEquals(exception, thrown);
        verify(productServices).getProduct(productId);
    }

    @Test
    void addUserReturnsAddUserView() {
        String result = adminController.addUser();

        assertEquals("Add_User", result);
        verifyNoInteractions(services, adminServices, productServices, orderServices, model);
    }

    @Test
    void updateUserPageAddsUserToModelAndReturnsUpdateUserView() {
        int userId = 3;
        User user = new User();
        when(services.getUser(userId)).thenReturn(user);

        String result = adminController.updateUserPage(userId, model);

        assertEquals("Update_User", result);
        verify(services).getUser(userId);
        verify(model).addAttribute("user", user);
    }

    @Test
    void updateUserPagePropagatesServiceException() {
        int userId = 3;
        RuntimeException exception = new RuntimeException("Unable to load user");
        when(services.getUser(userId)).thenThrow(exception);

        RuntimeException thrown = assertThrows(RuntimeException.class, () -> adminController.updateUserPage(userId, model));

        assertEquals(exception, thrown);
        verify(services).getUser(userId);
    }

    @Test
    void orderHandlerSavesOrderAndReturnsOrderSuccessView() {
        authenticateUser();
        Orders order = new Orders();
        clearInvocations(services, orderServices, model);

        String result = adminController.orderHandler(order, model);

        assertEquals("Order_success", result);
        verify(orderServices).saveOrder(order);
        verify(model).addAttribute(eq("amount"), any());
    }

    @Test
    void orderHandlerPropagatesServiceException() {
        authenticateUser();
        Orders order = new Orders();
        RuntimeException exception = new RuntimeException("Unable to save order");
        clearInvocations(services, orderServices, model);
        doThrow(exception).when(orderServices).saveOrder(order);

        RuntimeException thrown = assertThrows(RuntimeException.class, () -> adminController.orderHandler(order, model));

        assertEquals(exception, thrown);
        verify(orderServices).saveOrder(order);
    }

    @Test
    void backAddsOrdersAndReturnsBuyProductView() {
        User user = authenticateUser();
        List<Orders> orders = Collections.singletonList(new Orders());
        clearInvocations(services, orderServices, model);
        when(orderServices.getOrdersForUser(user)).thenReturn(orders);

        String result = adminController.back(model);

        assertEquals("BuyProduct", result);
        verify(orderServices).getOrdersForUser(user);
        verify(model).addAttribute("orders", orders);
    }

    @Test
    void backPropagatesServiceException() {
        User user = authenticateUser();
        RuntimeException exception = new RuntimeException("Unable to load orders");
        clearInvocations(services, orderServices, model);
        when(orderServices.getOrdersForUser(user)).thenThrow(exception);

        RuntimeException thrown = assertThrows(RuntimeException.class, () -> adminController.back(model));

        assertEquals(exception, thrown);
        verify(orderServices).getOrdersForUser(user);
    }

    private User authenticateUser() {
        UserLogin login = new UserLogin();
        User user = new User();
        when(services.validateLoginCredentials(login.getUserEmail(), login.getUserPassword())).thenReturn(true);
        when(services.getUserByEmail(login.getUserEmail())).thenReturn(user);
        when(orderServices.getOrdersForUser(user)).thenReturn(Collections.emptyList());
        adminController.userLogin(login, model);
        return user;
    }
}
