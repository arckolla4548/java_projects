package com.example.shop;

import static org.mockito.Mockito.mockStatic;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.SpringApplication;

@ExtendWith(MockitoExtension.class)
public class ShopApplicationTest_AI {

    @Test
    void mainShouldDelegateToSpringApplicationRun() {
        String[] args = {"--spring.profiles.active=test"};

        try (MockedStatic<SpringApplication> mockedSpringApplication = mockStatic(SpringApplication.class)) {
            ShopApplication.main(args);

            mockedSpringApplication.verify(() -> SpringApplication.run(ShopApplication.class, args));
        }
    }
}
