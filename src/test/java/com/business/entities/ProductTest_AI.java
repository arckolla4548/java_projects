package com.business.entities;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

class ProductTest_AI {
 @Test void gettersAndSettersShouldStoreValues(){Product p=new Product();p.setPid(1);p.setPname("Laptop");p.setPprice(1250.75);p.setPdescription("Business laptop");assertEquals(1,p.getPid());assertEquals("Laptop",p.getPname());assertEquals(1250.75,p.getPprice());assertEquals("Business laptop",p.getPdescription());}
 @Test void toStringShouldIncludeFieldValues(){Product p=new Product();p.setPid(1);p.setPname("Laptop");p.setPprice(1250.75);p.setPdescription("Business laptop");String s=p.toString();assertTrue(s.contains("pid=1"));assertTrue(s.contains("pname=Laptop"));assertTrue(s.contains("pprice=1250.75"));}
}
