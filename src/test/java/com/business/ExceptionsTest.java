package com.business;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ExceptionsTest {

    @InjectMocks
    private Exceptions exceptions;

    @Test
    void handlerReturnsExceptionView() {
        String result = exceptions.handler();

        assertEquals("exception", result);
    }
}
