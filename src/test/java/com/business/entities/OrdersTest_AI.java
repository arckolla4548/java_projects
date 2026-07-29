package com.business.entities;

import static org.junit.jupiter.api.Assertions.*;
import java.util.Date;
import org.junit.jupiter.api.Test;

class OrdersTest_AI {
 @Test void gettersAndSettersShouldStoreValues(){Orders o=new Orders();Date d=new Date();User u=new User();o.setoId(10);o.setoName("Product");o.setoPrice(99.5);o.setoQuantity(2);o.setOrderDate(d);o.setTotalAmmout(199.0);o.setUser(u);assertEquals(10,o.getoId());assertEquals("Product",o.getoName());assertEquals(99.5,o.getoPrice());assertEquals(2,o.getoQuantity());assertSame(d,o.getOrderDate());assertEquals(199.0,o.getTotalAmmout());assertSame(u,o.getUser());}
 @Test void toStringShouldIncludeFieldValues(){Orders o=new Orders();o.setoId(10);o.setoName("Product");o.setoPrice(99.5);o.setoQuantity(2);o.setTotalAmmout(199.0);String s=o.toString();assertTrue(s.contains("oId=10"));assertTrue(s.contains("oName=Product"));assertTrue(s.contains("oPrice=99.5"));}
}
