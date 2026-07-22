package com.business.services;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import java.util.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import com.business.entities.User;
import com.business.repositories.UserRepository;

@SpringBootTest(classes = UserServices.class)
class UserServicesSpringBootIntegrationTest_AI {
 @Autowired UserServices userServices; @MockBean UserRepository userRepository;
 @Test void shouldLoadServiceBeanAndReturnUsersFromRepository(){List<User> users=Collections.singletonList(new User());when(userRepository.findAll()).thenReturn(users);assertSame(users,userServices.getAllUser());}
 @Test void getUserShouldReturnUserWhenPresent(){User user=new User();when(userRepository.findById(2)).thenReturn(Optional.of(user));assertSame(user,userServices.getUser(2));}
 @Test void getUserShouldThrowWhenMissing(){when(userRepository.findById(2)).thenReturn(Optional.empty());assertThrows(NoSuchElementException.class,()->userServices.getUser(2));}
 @Test void updateUserShouldSetIdAndSave(){User user=new User();userServices.updateUser(user,2);assertEquals(2,user.getU_id());verify(userRepository).save(user);}
 @Test void validateLoginCredentialsShouldReturnTrueForMatchingEmailAndPassword(){User user=new User();user.setUemail("user@example.com");user.setUpassword("secret");when(userRepository.findAll()).thenReturn(Collections.singletonList(user));assertTrue(userServices.validateLoginCredentials("user@example.com","secret"));}
 @Test void validateLoginCredentialsShouldReturnFalseForNonMatchingPassword(){User user=new User();user.setUemail("user@example.com");user.setUpassword("secret");when(userRepository.findAll()).thenReturn(Collections.singletonList(user));assertFalse(userServices.validateLoginCredentials("user@example.com","bad"));}
}
