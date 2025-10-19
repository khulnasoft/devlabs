package com.khulnasoft.start;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the Calculator class.
 */
public class CalculatorTest {

    private Calculator calculator;

    @BeforeEach
    public void setUp() {
        calculator = new Calculator();
    }

    @Test
    public void testAddition() {
        assertEquals(5.0, calculator.add(2, 3), 0.001);
        assertEquals(0.0, calculator.add(-2, 2), 0.001);
        assertEquals(-5.0, calculator.add(-2, -3), 0.001);
        assertEquals(10.5, calculator.add(5.5, 5), 0.001);
    }

    @Test
    public void testSubtraction() {
        assertEquals(2.0, calculator.subtract(5, 3), 0.001);
        assertEquals(-4.0, calculator.subtract(-2, 2), 0.001);
        assertEquals(1.0, calculator.subtract(-2, -3), 0.001);
        assertEquals(0.5, calculator.subtract(5.5, 5), 0.001);
    }

    @Test
    public void testMultiplication() {
        assertEquals(15.0, calculator.multiply(5, 3), 0.001);
        assertEquals(-4.0, calculator.multiply(-2, 2), 0.001);
        assertEquals(6.0, calculator.multiply(-2, -3), 0.001);
        assertEquals(27.5, calculator.multiply(5.5, 5), 0.001);
        assertEquals(0.0, calculator.multiply(0, 5), 0.001);
    }

    @Test
    public void testDivision() {
        assertEquals(2.5, calculator.divide(5, 2), 0.001);
        assertEquals(-2.0, calculator.divide(-6, 3), 0.001);
        assertEquals(2.0, calculator.divide(-4, -2), 0.001);
        assertEquals(1.1, calculator.divide(5.5, 5), 0.001);
    }

    @Test
    public void testDivisionByZero() {
        assertThrows(ArithmeticException.class, () -> calculator.divide(5, 0));
        assertThrows(ArithmeticException.class, () -> calculator.divide(0, 0));
        assertThrows(ArithmeticException.class, () -> calculator.divide(-5, 0));
    }

    @Test
    public void testDivisionByZeroErrorMessage() {
        Exception exception = assertThrows(ArithmeticException.class, () -> {
            calculator.divide(5, 0);
        });
        assertEquals("Division by zero is not allowed", exception.getMessage());
    }
}
