package com.business.repositories;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import com.business.entities.Admin;

@DataJpaTest(properties = "spring.jpa.properties.hibernate.globally_quoted_identifiers=true")
class AdminRepositoryDataJpaIntegrationTest_AI {
 @Autowired AdminRepository adminRepository;
 @Test void saveAndFindByIdShouldPersistAdmin(){Admin a=new Admin();a.setAdminName("Admin");a.setAdminEmail("admin@example.com");a.setAdminPassword("secret");a.setAdminNumber("1234567890");Admin saved=adminRepository.save(a);assertNotNull(saved);assertEquals("admin@example.com",adminRepository.findById(saved.getAdminId()).orElseThrow().getAdminEmail());}
 @Test void findByAdminEmailShouldReturnMatchingAdmin(){Admin a=new Admin();a.setAdminName("Admin");a.setAdminEmail("admin@example.com");a.setAdminPassword("secret");adminRepository.save(a);Admin result=adminRepository.findByAdminEmail("admin@example.com");assertNotNull(result);assertEquals("Admin",result.getAdminName());}
 @Test void findByAdminEmailShouldReturnNullWhenMissing(){assertNull(adminRepository.findByAdminEmail("missing@example.com"));}
}
