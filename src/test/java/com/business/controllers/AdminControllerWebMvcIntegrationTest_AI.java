package com.business.controllers;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import java.util.Collections;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.*;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.servlet.ViewResolver;
import com.business.Exceptions;
import com.business.entities.*;
import com.business.services.*;

@WebMvcTest(AdminController.class)
@Import({Exceptions.class, AdminControllerWebMvcIntegrationTest_AI.TestViewResolverConfig.class})
class AdminControllerWebMvcIntegrationTest_AI {
 @Autowired MockMvc mockMvc; @MockBean UserServices services; @MockBean AdminServices adminServices; @MockBean ProductServices productServices; @MockBean OrderServices orderServices;
 @Test void adminLoginShouldRedirectForValidCredentials() throws Exception {when(adminServices.validateAdminCredentials("admin@example.com","secret")).thenReturn(true);mockMvc.perform(get("/adminLogin").param("email","admin@example.com").param("password","secret")).andExpect(status().is3xxRedirection()).andExpect(redirectedUrl("/admin/services"));}
 @Test void adminLoginShouldReturnLoginViewForInvalidCredentials() throws Exception {when(adminServices.validateAdminCredentials("admin@example.com","bad")).thenReturn(false);mockMvc.perform(get("/adminLogin").param("email","admin@example.com").param("password","bad")).andExpect(status().isOk()).andExpect(view().name("Login")).andExpect(model().attribute("error","Invalid email or password"));}
 @Test void userLoginShouldReturnBuyProductViewForValidCredentials() throws Exception {User u=new User();u.setUname("Test User");when(services.validateLoginCredentials("user@example.com","secret")).thenReturn(true);when(services.getUserByEmail("user@example.com")).thenReturn(u);when(orderServices.getOrdersForUser(u)).thenReturn(Collections.emptyList());mockMvc.perform(get("/userlogin").param("userEmail","user@example.com").param("userPassword","secret")).andExpect(status().isOk()).andExpect(view().name("BuyProduct")).andExpect(model().attribute("name","Test User"));}
 @Test void adminServicesShouldPopulateAdminPageModel() throws Exception {when(services.getAllUser()).thenReturn(Collections.singletonList(new User()));when(adminServices.getAll()).thenReturn(Collections.singletonList(new Admin()));when(productServices.getAllProducts()).thenReturn(Collections.singletonList(new Product()));when(orderServices.getOrders()).thenReturn(Collections.singletonList(new Orders()));mockMvc.perform(get("/admin/services")).andExpect(status().isOk()).andExpect(view().name("Admin_Page")).andExpect(model().attributeExists("users","admins","products","orders"));}
 @Test void simpleAdminPagesAndActions() throws Exception {mockMvc.perform(get("/addAdmin")).andExpect(status().isOk()).andExpect(view().name("Add_Admin"));mockMvc.perform(post("/addingAdmin").param("adminName","Admin")).andExpect(status().is3xxRedirection()).andExpect(redirectedUrl("/admin/services"));verify(adminServices).addAdmin(any(Admin.class));}
 @Test void productSearchAndOrderShouldReturnViews() throws Exception {when(productServices.getProductByName("Laptop")).thenReturn(new Product());when(orderServices.getOrdersForUser(nullable(User.class))).thenReturn(Collections.emptyList());mockMvc.perform(post("/product/search").param("productName","Laptop")).andExpect(status().isOk()).andExpect(view().name("BuyProduct"));mockMvc.perform(post("/product/order").param("oName","Laptop").param("oPrice","10.0").param("oQuantity","3")).andExpect(status().isOk()).andExpect(view().name("Order_success")).andExpect(model().attribute("amount",30.0));}
 @TestConfiguration static class TestViewResolverConfig { @Bean ViewResolver viewResolver(){return (v,l)-> v!=null&&v.startsWith("redirect:")?null:(m,req,res)->{};} }
}
