package com.business;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

class ExceptionsTest_AI {
    @Test void handlerShouldReturnExceptionViewName() { assertEquals("exception", new Exceptions().handler()); }
}
