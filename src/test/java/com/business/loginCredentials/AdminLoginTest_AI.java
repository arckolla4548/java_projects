package com.business.loginCredentials;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

class AdminLoginTest_AI {
    @Test
    void gettersAndSettersShouldStoreValues() {
        AdminLogin login = new AdminLogin(); login.setEmail("admin@example.com"); login.setPassword("secret");
        assertEquals("admin@example.com", login.getEmail()); assertEquals("secret", login.getPassword());
    }
    @Test
    void toStringShouldIncludeFieldValues() {
        AdminLogin login = new AdminLogin(); login.setEmail("admin@example.com"); login.setPassword("secret");
        String result = login.toString();
        assertTrue(result.contains("name=admin@example.com")); assertTrue(result.contains("password=secret"));
    }
}
