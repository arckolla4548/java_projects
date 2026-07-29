package com.business.loginCredentials;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

class UserLoginTest_AI {
 @Test void gettersAndSettersShouldStoreValues(){UserLogin l=new UserLogin();l.setUserEmail("user@example.com");l.setUserPassword("secret");assertEquals("user@example.com",l.getUserEmail());assertEquals("secret",l.getUserPassword());}
 @Test void toStringShouldIncludeFieldValues(){UserLogin l=new UserLogin();l.setUserEmail("user@example.com");l.setUserPassword("secret");String s=l.toString();assertTrue(s.contains("userEmail=user@example.com"));assertTrue(s.contains("userPassword=secret"));}
}
