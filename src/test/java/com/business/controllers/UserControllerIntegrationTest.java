package com.business.controllers;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import com.business.entities.User;
import com.business.services.UserServices;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(UserController.class)
class UserControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserServices services;

    @Test
    void addingUser_ShouldBindUserDelegateToServiceAndRedirect() throws Exception {
        mockMvc.perform(post("/addingUser")
                        .param("uname", "Alice")
                        .param("uemail", "alice@example.com")
                        .param("upassword", "secret")
                        .param("unumber", "1234567890"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/admin/services"));

        verify(services).addUser(any(User.class));
    }

    @Test
    void addingUser_WhenServiceThrowsException_ShouldReturnExceptionViewWithServerError() throws Exception {
        doThrow(new RuntimeException("save failed")).when(services).addUser(any(User.class));

        mockMvc.perform(post("/addingUser")
                        .param("uname", "Alice")
                        .param("uemail", "alice@example.com")
                        .param("upassword", "secret")
                        .param("unumber", "1234567890"))
                .andExpect(status().isInternalServerError())
                .andExpect(view().name("exception"));
    }

    @Test
    void updatingUser_ShouldBindUserDelegateToServiceAndRedirect() throws Exception {
        mockMvc.perform(get("/updatingUser/{id}", 3)
                        .param("uname", "Bob")
                        .param("uemail", "bob@example.com")
                        .param("upassword", "updated")
                        .param("unumber", "9876543210"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/admin/services"));

        verify(services).updateUser(any(User.class), org.mockito.Mockito.eq(3));
    }

    @Test
    void deleteUser_ShouldDelegateToServiceAndRedirect() throws Exception {
        mockMvc.perform(get("/deleteUser/{id}", 4))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/admin/services"));

        verify(services).deleteUser(4);
    }
}
