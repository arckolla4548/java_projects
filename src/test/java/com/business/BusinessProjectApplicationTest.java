package com.business;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mockStatic;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.SpringApplication;

@ExtendWith(MockitoExtension.class)
class BusinessProjectApplicationTest {

    @InjectMocks
    private BusinessProjectApplication businessProjectApplication;

    @Test
    void mainInvokesSpringApplicationRun() {
        String[] args = {"--spring.profiles.active=test"};

        try (MockedStatic<SpringApplication> springApplication = mockStatic(SpringApplication.class)) {
            BusinessProjectApplication.main(args);

            springApplication.verify(() -> SpringApplication.run(BusinessProjectApplication.class, args));
        }
    }

    @Test
    void initPrintsHomeControllerLoadedMessage() {
        PrintStream originalOut = System.out;
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        try {
            System.setOut(new PrintStream(outputStream));

            businessProjectApplication.init();

            assertTrue(outputStream.toString().contains("HomeController loaded"));
        } finally {
            System.setOut(originalOut);
        }
    }
}
