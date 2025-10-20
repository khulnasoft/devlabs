package com.khulnasoft.buildrunner;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for Calculator functionality.
 * Demonstrates comprehensive testing of arithmetic operations.
 */
class CalculatorTest {

    private Calculator calculator;

    @BeforeEach
    void setUp() {
        calculator = new Calculator();
    }

    @Test
    @DisplayName("Should add two positive numbers correctly")
    void testAddPositiveNumbers() {
        int result = calculator.add(5, 3);
        assertEquals(8, result, "5 + 3 should equal 8");
    }

    @Test
    @DisplayName("Should add negative numbers correctly")
    void testAddNegativeNumbers() {
        int result = calculator.add(-5, -3);
        assertEquals(-8, result, "-5 + -3 should equal -8");
    }

    @Test
    @DisplayName("Should add zero correctly")
    void testAddWithZero() {
        assertEquals(5, calculator.add(5, 0), "5 + 0 should equal 5");
        assertEquals(0, calculator.add(0, 0), "0 + 0 should equal 0");
    }

    @ParameterizedTest
    @CsvSource({
        "1, 2, 3",
        "10, 15, 25",
        "-5, 3, -2",
        "0, 0, 0"
    })
    @DisplayName("Should add various number combinations correctly")
    void testAddVariousCombinations(int a, int b, int expected) {
        int result = calculator.add(a, b);
        assertEquals(expected, result, a + " + " + b + " should equal " + expected);
    }

    @Test
    @DisplayName("Should subtract positive numbers correctly")
    void testSubtractPositiveNumbers() {
        int result = calculator.subtract(10, 4);
        assertEquals(6, result, "10 - 4 should equal 6");
    }

    @Test
    @DisplayName("Should subtract negative numbers correctly")
    void testSubtractNegativeNumbers() {
        int result = calculator.subtract(-5, -3);
        assertEquals(-2, result, "-5 - -3 should equal -2");
    }

    @Test
    @DisplayName("Should multiply two positive numbers correctly")
    void testMultiplyPositiveNumbers() {
        int result = calculator.multiply(6, 7);
        assertEquals(42, result, "6 * 7 should equal 42");
    }

    @Test
    @DisplayName("Should multiply by zero correctly")
    void testMultiplyByZero() {
        assertEquals(0, calculator.multiply(5, 0), "5 * 0 should equal 0");
        assertEquals(0, calculator.multiply(0, 10), "0 * 10 should equal 0");
    }

    @Test
    @DisplayName("Should multiply negative numbers correctly")
    void testMultiplyNegativeNumbers() {
        int result = calculator.multiply(-4, 3);
        assertEquals(-12, result, "-4 * 3 should equal -12");
    }

    @Test
    @DisplayName("Should divide positive numbers correctly")
    void testDividePositiveNumbers() {
        double result = calculator.divide(15, 3);
        assertEquals(5.0, result, 0.001, "15 / 3 should equal 5.0");
    }

    @Test
    @DisplayName("Should throw exception when dividing by zero")
    void testDivideByZero() {
        ArithmeticException exception = assertThrows(
            ArithmeticException.class,
            () -> calculator.divide(5, 0),
            "Division by zero should throw ArithmeticException"
        );
        assertEquals("Division by zero", exception.getMessage());
    }

    @Test
    @DisplayName("Should handle negative division correctly")
    void testDivideNegativeNumbers() {
        double result = calculator.divide(-10, 2);
        assertEquals(-5.0, result, 0.001, "-10 / 2 should equal -5.0");
    }

    @ParameterizedTest
    @ValueSource(ints = {0, 2, 4, 6, 8, 10, 100})
    @DisplayName("Should correctly identify even numbers")
    void testIsEvenTrue(int number) {
        assertTrue(calculator.isEven(number), number + " should be even");
    }

    @ParameterizedTest
    @ValueSource(ints = {1, 3, 5, 7, 9, 11, 101})
    @DisplayName("Should correctly identify odd numbers")
    void testIsEvenFalse(int number) {
        assertFalse(calculator.isEven(number), number + " should be odd");
    }

    @Test
    @DisplayName("Should calculate factorial of zero")
    void testFactorialZero() {
        long result = calculator.factorial(0);
        assertEquals(1, result, "Factorial of 0 should be 1");
    }

    @Test
    @DisplayName("Should calculate factorial of one")
    void testFactorialOne() {
        long result = calculator.factorial(1);
        assertEquals(1, result, "Factorial of 1 should be 1");
    }

    @Test
    @DisplayName("Should calculate factorial of positive numbers")
    void testFactorialPositive() {
        assertEquals(2, calculator.factorial(2), "Factorial of 2 should be 2");
        assertEquals(6, calculator.factorial(3), "Factorial of 3 should be 6");
        assertEquals(24, calculator.factorial(4), "Factorial of 4 should be 24");
        assertEquals(120, calculator.factorial(5), "Factorial of 5 should be 120");
    }

    @Test
    @DisplayName("Should throw exception for negative factorial")
    void testFactorialNegative() {
        IllegalArgumentException exception = assertThrows(
            IllegalArgumentException.class,
            () -> calculator.factorial(-1),
            "Factorial of negative number should throw IllegalArgumentException"
        );
        assertEquals("Factorial is not defined for negative numbers", exception.getMessage());
    }

    @Test
    @DisplayName("Should handle large factorial")
    void testFactorialLarge() {
        long result = calculator.factorial(10);
        assertEquals(3628800, result, "Factorial of 10 should be 3628800");
    }
}
