package com.business.services;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import java.util.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import com.business.entities.Admin;
import com.business.repositories.AdminRepository;

@ExtendWith(MockitoExtension.class)
class AdminServicesTest_AI {
 @Mock AdminRepository repo; @InjectMocks AdminServices service;
 @Test void getAllShouldReturnAdmins(){List<Admin> list=Collections.singletonList(new Admin());when(repo.findAll()).thenReturn(list);assertSame(list,service.getAll());verify(repo).findAll();}
 @Test void getAdminShouldReturnFound(){Admin a=new Admin();when(repo.findById(1)).thenReturn(Optional.of(a));assertSame(a,service.getAdmin(1));}
 @Test void getAdminShouldThrowWhenMissing(){when(repo.findById(1)).thenReturn(Optional.empty());assertThrows(NoSuchElementException.class,()->service.getAdmin(1));}
 @Test void addDeleteUpdateShouldDelegate(){Admin e=new Admin();e.setAdminId(5);Admin a=new Admin();when(repo.findAll()).thenReturn(Collections.singletonList(e));service.update(a,5);verify(repo).save(a);service.addAdmin(a);verify(repo,times(2)).save(a);service.delete(5);verify(repo).deleteById(5);}
 @Test void validateCredentials(){Admin a=new Admin();a.setAdminPassword("secret");when(repo.findByAdminEmail("a@b.com")).thenReturn(a);assertTrue(service.validateAdminCredentials("a@b.com","secret"));assertFalse(service.validateAdminCredentials("a@b.com","bad"));when(repo.findByAdminEmail("x@y.com")).thenReturn(null);assertFalse(service.validateAdminCredentials("x@y.com","secret"));}
}
