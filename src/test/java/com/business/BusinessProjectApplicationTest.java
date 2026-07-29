package com.business;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.Mockito.mockStatic;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.SpringApplication;

@ExtendWith(MockitoExtension.class)
class BusinessProjectApplicationTest {

    @InjectMocks
    private BusinessProjectApplication application;

    @Test
    void mainRunsSpringApplication() {
        String[] args = {"--server.port=0"};
        try (MockedStatic<SpringApplication> springApplication = mockStatic(SpringApplication.class)) {
            springApplication.when(() -> SpringApplication.run(BusinessProjectApplication.class, args)).thenReturn(null);

            assertDoesNotThrow(() -> BusinessProjectApplication.main(args));

            springApplication.verify(() -> SpringApplication.run(BusinessProjectApplication.class, args));
        }
    }

    @Test
    void initDoesNotThrowException() {
        assertDoesNotThrow(() -> application.init());
    }
}
