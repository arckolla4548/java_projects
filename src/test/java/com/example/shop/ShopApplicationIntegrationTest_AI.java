package com.example.shop;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.example.shop.controller.ProductController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;

@SpringBootTest(
        classes = ShopApplication.class,
        properties = {
                "spring.datasource.url=jdbc:h2:mem:shop-application-integration-test;DB_CLOSE_DELAY=-1;DB_CLOSE_ON_EXIT=FALSE",
                "spring.datasource.driver-class-name=org.h2.Driver",
                "spring.jpa.hibernate.ddl-auto=create-drop"
        }
)
class ShopApplicationIntegrationTest_AI {

    @Autowired
    private ApplicationContext applicationContext;

    @Test
    void contextShouldLoadSuccessfully() {
        assertNotNull(applicationContext);
    }

    @Test
    void productControllerBeanShouldBeAvailableInApplicationContext() {
        assertNotNull(applicationContext.getBean(ProductController.class));
    }
}
