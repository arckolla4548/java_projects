package com.example.shop;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.mockStatic;

@ExtendWith(MockitoExtension.class)
class ShopApplicationTest_AI {

    @Test
    void main_whenInvoked_startsSpringApplication() {
        String[] args = new String[] {"--server.port=0"};
        ConfigurableApplicationContext applicationContext = mock(ConfigurableApplicationContext.class);

        try (MockedStatic<SpringApplication> springApplicationMock = mockStatic(SpringApplication.class)) {
            springApplicationMock.when(() -> SpringApplication.run(ShopApplication.class, args)).thenReturn(applicationContext);

            ShopApplication.main(args);

            springApplicationMock.verify(() -> SpringApplication.run(ShopApplication.class, args));
        }
    }

    @Test
    void main_whenSpringApplicationThrowsException_propagatesException() {
        String[] args = new String[] {"--invalid=true"};
        IllegalStateException expectedException = new IllegalStateException("startup failed");

        try (MockedStatic<SpringApplication> springApplicationMock = mockStatic(SpringApplication.class)) {
            springApplicationMock.when(() -> SpringApplication.run(ShopApplication.class, args)).thenThrow(expectedException);

            IllegalStateException actualException = assertThrows(IllegalStateException.class, () -> ShopApplication.main(args));

            assertSame(expectedException, actualException);
            springApplicationMock.verify(() -> SpringApplication.run(ShopApplication.class, args));
        }
    }
}
