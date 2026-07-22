package com.business.services;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import java.util.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import com.business.entities.Admin;
import com.business.repositories.AdminRepository;

@SpringBootTest(classes = AdminServices.class)
class AdminServicesSpringBootIntegrationTest_AI {
 @Autowired AdminServices adminServices; @MockBean AdminRepository adminRepository;
 @Test void shouldLoadServiceBeanAndReturnAllAdminsFromRepository(){List<Admin> admins=Collections.singletonList(new Admin());when(adminRepository.findAll()).thenReturn(admins);assertSame(admins,adminServices.getAll());verify(adminRepository).findAll();}
 @Test void getAdminShouldReturnAdminWhenRepositoryFindsId(){Admin admin=new Admin();when(adminRepository.findById(1)).thenReturn(Optional.of(admin));assertSame(admin,adminServices.getAdmin(1));}
 @Test void getAdminShouldThrowWhenRepositoryReturnsEmptyOptional(){when(adminRepository.findById(1)).thenReturn(Optional.empty());assertThrows(NoSuchElementException.class,()->adminServices.getAdmin(1));}
 @Test void validateAdminCredentialsShouldReturnTrueForMatchingStoredPassword(){Admin admin=new Admin();admin.setAdminPassword("secret");when(adminRepository.findByAdminEmail("admin@example.com")).thenReturn(admin);assertTrue(adminServices.validateAdminCredentials("admin@example.com","secret"));}
 @Test void validateAdminCredentialsShouldReturnFalseWhenAdminNotFound(){when(adminRepository.findByAdminEmail("missing@example.com")).thenReturn(null);assertFalse(adminServices.validateAdminCredentials("missing@example.com","secret"));}
}
