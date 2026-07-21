package com.example.junit.exercise1;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;


class CalculatorTest {

    // The class under test — instantiated fresh for each @Test method
    private final Calculator calculator = new Calculator();

    @Test
    void testAdd() {
        int result = calculator.add(3, 4);
        assertEquals(7, result, "3 + 4 should equal 7");
    }

    @Test
    void testSubtract() {
        int result = calculator.subtract(10, 4);
        assertEquals(6, result, "10 - 4 should equal 6");
    }

    @Test
    void testMultiply() {
        int result = calculator.multiply(3, 5);
        assertEquals(15, result, "3 × 5 should equal 15");
    }

    @Test
    void testDivide() {
        double result = calculator.divide(10, 4);
        assertEquals(2.5, result, "10 / 4 should equal 2.5");
    }

    @Test
    void testDivideByZeroThrowsException() {
        // assertThrows verifies that an ArithmeticException is thrown
        ArithmeticException ex = assertThrows(
            ArithmeticException.class,
            () -> calculator.divide(5, 0),
            "Dividing by zero should throw ArithmeticException"
        );
        assertEquals("Cannot divide by zero", ex.getMessage());
    }

    @Test
    void testAddNegativeNumbers() {
        assertEquals(-5, calculator.add(-3, -2), "-3 + -2 should equal -5");
    }
}
