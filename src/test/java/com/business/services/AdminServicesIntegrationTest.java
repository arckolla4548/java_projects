package com.business.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.business.entities.Admin;
import com.business.repositories.AdminRepository;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@SpringBootTest(classes = AdminServices.class)
class AdminServicesIntegrationTest {
    @Autowired private AdminServices adminServices;
    @MockBean private AdminRepository adminRepository;

    @Test void getAll_ShouldReturnAllAdminsFromRepository() {
        Admin admin = new Admin();
        when(adminRepository.findAll()).thenReturn(List.of(admin));
        assertEquals(List.of(admin), adminServices.getAll());
    }

    @Test void getAdmin_WhenPresent_ShouldReturnAdmin() {
        Admin admin = new Admin(); admin.setAdminId(1);
        when(adminRepository.findById(1)).thenReturn(Optional.of(admin));
        assertEquals(admin, adminServices.getAdmin(1));
    }

    @Test void getAdmin_WhenMissing_ShouldThrowNoSuchElementException() {
        when(adminRepository.findById(99)).thenReturn(Optional.empty());
        assertThrows(NoSuchElementException.class, () -> adminServices.getAdmin(99));
    }

    @Test void addAdmin_ShouldSaveAdmin() {
        Admin admin = new Admin();
        adminServices.addAdmin(admin);
        verify(adminRepository).save(admin);
    }

    @Test void update_WhenExistingAdminIdMatches_ShouldSaveProvidedAdmin() {
        Admin existing = new Admin(); existing.setAdminId(5);
        Admin updated = new Admin();
        when(adminRepository.findAll()).thenReturn(List.of(existing));
        adminServices.update(updated, 5);
        verify(adminRepository).save(updated);
    }

    @Test void update_WhenNoAdminIdMatches_ShouldNotSave() {
        Admin existing = new Admin(); existing.setAdminId(5);
        Admin updated = new Admin();
        when(adminRepository.findAll()).thenReturn(List.of(existing));
        adminServices.update(updated, 9);
        verify(adminRepository, never()).save(updated);
    }

    @Test void delete_ShouldDeleteAdminById() {
        adminServices.delete(7);
        verify(adminRepository).deleteById(7);
    }

    @Test void validateAdminCredentials_WithMatchingEmailAndPassword_ShouldReturnTrue() {
        Admin admin = new Admin(); admin.setAdminEmail("admin@example.com"); admin.setAdminPassword("1234");
        when(adminRepository.findByAdminEmail("admin@example.com")).thenReturn(admin);
        assertTrue(adminServices.validateAdminCredentials("admin@example.com", "1234"));
    }

    @Test void validateAdminCredentials_WithMissingAdminOrWrongPassword_ShouldReturnFalse() {
        Admin admin = new Admin(); admin.setAdminEmail("admin@example.com"); admin.setAdminPassword("1234");
        when(adminRepository.findByAdminEmail("missing@example.com")).thenReturn(null);
        when(adminRepository.findByAdminEmail("admin@example.com")).thenReturn(admin);
        assertFalse(adminServices.validateAdminCredentials("missing@example.com", "1234"));
        assertFalse(adminServices.validateAdminCredentials("admin@example.com", "bad"));
    }
}
