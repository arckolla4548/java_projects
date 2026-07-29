package com.business.repositories;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import com.business.entities.User;

@ExtendWith(MockitoExtension.class)
class UserRepositoryTest_AI {
 @Mock UserRepository repo;
 @Test void findUserByUemailShouldReturnConfiguredUser(){User u=new User();when(repo.findUserByUemail("user@example.com")).thenReturn(u);assertSame(u,repo.findUserByUemail("user@example.com"));verify(repo).findUserByUemail("user@example.com");}
 @Test void findUserByUemailShouldReturnNullWhenConfiguredMissing(){when(repo.findUserByUemail("missing@example.com")).thenReturn(null);assertNull(repo.findUserByUemail("missing@example.com"));}
}
