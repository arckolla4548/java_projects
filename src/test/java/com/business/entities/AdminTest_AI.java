package com.business.entities;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

class AdminTest_AI {
 @Test void gettersAndSettersShouldStoreValues(){Admin a=new Admin();a.setAdminId(1);a.setAdminName("Admin Name");a.setAdminEmail("admin@example.com");a.setAdminPassword("secret");a.setAdminNumber("1234567890");assertEquals(1,a.getAdminId());assertEquals("Admin Name",a.getAdminName());assertEquals("admin@example.com",a.getAdminEmail());assertEquals("secret",a.getAdminPassword());assertEquals("1234567890",a.getAdminNumber());}
 @Test void toStringShouldIncludeFieldValues(){Admin a=new Admin();a.setAdminId(1);a.setAdminName("Admin Name");a.setAdminEmail("admin@example.com");a.setAdminPassword("secret");a.setAdminNumber("1234567890");String s=a.toString();assertTrue(s.contains("adminId=1"));assertTrue(s.contains("adminName=Admin Name"));assertTrue(s.contains("adminEmail=admin@example.com"));}
}
