package com.business.repositories;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import com.business.entities.User;

@DataJpaTest(properties = "spring.jpa.properties.hibernate.globally_quoted_identifiers=true")
class UserRepositoryDataJpaIntegrationTest_AI {
 @Autowired UserRepository userRepository;
 @Test void saveAndFindByIdShouldPersistUser(){User u=new User();u.setUname("Test User");u.setUemail("user@example.com");u.setUpassword("secret");u.setUnumber(9876543210L);User saved=userRepository.save(u);assertNotNull(saved);assertEquals("user@example.com",userRepository.findById(saved.getU_id()).orElseThrow().getUemail());}
 @Test void findUserByUemailShouldReturnMatchingUser(){User u=new User();u.setUname("Test User");u.setUemail("user@example.com");u.setUpassword("secret");userRepository.save(u);User result=userRepository.findUserByUemail("user@example.com");assertNotNull(result);assertEquals("Test User",result.getUname());}
 @Test void findUserByUemailShouldReturnNullWhenMissing(){assertNull(userRepository.findUserByUemail("missing@example.com"));}
}
