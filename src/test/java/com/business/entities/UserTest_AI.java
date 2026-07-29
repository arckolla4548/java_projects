package com.business.entities;

import static org.junit.jupiter.api.Assertions.*;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.Test;

class UserTest_AI {
 @Test void gettersAndSettersShouldStoreValues(){User u=new User();List<Orders> orders=Collections.singletonList(new Orders());u.setU_id(1);u.setUname("User Name");u.setUemail("user@example.com");u.setUpassword("secret");u.setUnumber(9876543210L);u.setOrders(orders);assertEquals(1,u.getU_id());assertEquals("User Name",u.getUname());assertEquals("user@example.com",u.getUemail());assertEquals("secret",u.getUpassword());assertEquals(9876543210L,u.getUnumber());assertSame(orders,u.getOrders());}
 @Test void toStringShouldIncludeFieldValues(){User u=new User();u.setU_id(1);u.setUname("User Name");u.setUemail("user@example.com");u.setUpassword("secret");u.setUnumber(9876543210L);String s=u.toString();assertTrue(s.contains("u_id=1"));assertTrue(s.contains("uname=User Name"));assertTrue(s.contains("uemail=user@example.com"));}
}
