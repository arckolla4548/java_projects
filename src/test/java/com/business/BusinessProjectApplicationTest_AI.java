package com.business;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mockStatic;

import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.springframework.boot.SpringApplication;

class BusinessProjectApplicationTest_AI {
    @Test
    void mainShouldDelegateToSpringApplicationRun() {
        String[] args = {"--server.port=0"};
        try (MockedStatic<SpringApplication> springApplication = mockStatic(SpringApplication.class)) {
            springApplication.when(() -> SpringApplication.run(eq(BusinessProjectApplication.class), any(String[].class))).thenReturn(null);
            BusinessProjectApplication.main(args);
            springApplication.verify(() -> SpringApplication.run(BusinessProjectApplication.class, args));
        }
    }
    @Test
    void initShouldCompleteWithoutException() {
        assertDoesNotThrow(new BusinessProjectApplication()::init);
    }
}
