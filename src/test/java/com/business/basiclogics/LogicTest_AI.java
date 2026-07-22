package com.business.basiclogics;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class LogicTest_AI {

    @Test
    void countTotalShouldReturnPriceMultipliedByQuantity() {
        double result = Logic.countTotal(12.5, 4);

        assertEquals(50.0, result);
    }

    @Test
    void countTotalShouldReturnZeroWhenQuantityIsZero() {
        double result = Logic.countTotal(12.5, 0);

        assertEquals(0.0, result);
    }

    @Test
    void countTotalShouldSupportNegativeQuantity() {
        double result = Logic.countTotal(12.5, -2);

        assertEquals(-25.0, result);
    }
}
