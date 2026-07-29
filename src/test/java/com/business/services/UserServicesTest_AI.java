package com.business.services;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import java.util.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import com.business.entities.User;
import com.business.repositories.UserRepository;

@ExtendWith(MockitoExtension.class)
class UserServicesTest_AI {
 @Mock UserRepository repo; @InjectMocks UserServices service;
 @Test void getAllAndGetUser(){List<User> list=Collections.singletonList(new User());when(repo.findAll()).thenReturn(list);assertSame(list,service.getAllUser());User u=new User();when(repo.findById(2)).thenReturn(Optional.of(u));assertSame(u,service.getUser(2));when(repo.findById(3)).thenReturn(Optional.empty());assertThrows(NoSuchElementException.class,()->service.getUser(3));}
 @Test void addUpdateDeleteAndEmail(){User u=new User();service.addUser(u);verify(repo).save(u);service.updateUser(u,2);assertEquals(2,u.getU_id());verify(repo,times(2)).save(u);service.deleteUser(2);verify(repo).deleteById(2);when(repo.findUserByUemail("u@e.com")).thenReturn(u);assertSame(u,service.getUserByEmail("u@e.com"));}
 @Test void validateLoginCredentials(){User u=new User();u.setUemail("user@example.com");u.setUpassword("secret");when(repo.findAll()).thenReturn(Collections.singletonList(u));assertTrue(service.validateLoginCredentials("user@example.com","secret"));assertFalse(service.validateLoginCredentials("user@example.com","bad"));assertFalse(service.validateLoginCredentials("other@example.com","secret"));}
}
