package com.business.repositories;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import com.business.entities.Admin;

@ExtendWith(MockitoExtension.class)
class AdminRepositoryTest_AI {
 @Mock AdminRepository repo;
 @Test void findByAdminEmailShouldReturnConfiguredAdmin(){Admin a=new Admin();when(repo.findByAdminEmail("admin@example.com")).thenReturn(a);assertSame(a,repo.findByAdminEmail("admin@example.com"));verify(repo).findByAdminEmail("admin@example.com");}
 @Test void findByAdminEmailShouldReturnNullWhenConfiguredMissing(){when(repo.findByAdminEmail("missing@example.com")).thenReturn(null);assertNull(repo.findByAdminEmail("missing@example.com"));}
}
