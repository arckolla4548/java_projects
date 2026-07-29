package com.business.controllers;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.*;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.servlet.ViewResolver;
import com.business.Exceptions;
import com.business.entities.User;
import com.business.services.UserServices;

@WebMvcTest(UserController.class)
@Import({Exceptions.class, UserControllerWebMvcIntegrationTest_AI.TestViewResolverConfig.class})
class UserControllerWebMvcIntegrationTest_AI {
 @Autowired MockMvc mockMvc; @MockBean UserServices services;
 @Test void addingUserShouldBindUserDelegateToServiceAndRedirect() throws Exception {mockMvc.perform(post("/addingUser").param("uname","Test User").param("uemail","user@example.com").param("upassword","secret").param("unumber","9876543210")).andExpect(status().is3xxRedirection()).andExpect(redirectedUrl("/admin/services"));verify(services).addUser(any(User.class));}
 @Test void updatingUserShouldBindUserDelegateToServiceAndRedirect() throws Exception {mockMvc.perform(get("/updatingUser/{id}",11).param("uname","Updated User").param("uemail","updated@example.com")).andExpect(status().is3xxRedirection()).andExpect(redirectedUrl("/admin/services"));verify(services).updateUser(any(User.class),eq(11));}
 @Test void deleteUserShouldDelegateToServiceAndRedirect() throws Exception {mockMvc.perform(get("/deleteUser/{id}",11)).andExpect(status().is3xxRedirection()).andExpect(redirectedUrl("/admin/services"));verify(services).deleteUser(11);}
 @TestConfiguration static class TestViewResolverConfig { @Bean ViewResolver viewResolver(){return (v,l)-> v!=null&&v.startsWith("redirect:")?null:(m,req,res)->{};} }
}
