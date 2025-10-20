package com.khulnasoft.buildrunner;

/**
 * Simple Calculator class for demonstration purposes.
 * Provides basic arithmetic operations.
 */
public class Calculator {

    /**
     * Adds two numbers.
     * @param a first number
     * @param b second number
     * @return sum of a and b
     */
    public int add(int a, int b) {
        return a + b;
    }

    /**
     * Subtracts two numbers.
     * @param a first number
     * @param b second number
     * @return difference of a and b
     */
    public int subtract(int a, int b) {
        return a - b;
    }

    /**
     * Multiplies two numbers.
     * @param a first number
     * @param b second number
     * @return product of a and b
     */
    public int multiply(int a, int b) {
        return a * b;
    }

    /**
     * Divides two numbers.
     * @param a dividend
     * @param b divisor
     * @return quotient of a and b
     * @throws ArithmeticException if divisor is zero
     */
    public double divide(int a, int b) {
        if (b == 0) {
            throw new ArithmeticException("Division by zero");
        }
        return (double) a / b;
    }

    /**
     * Checks if a number is even.
     * @param number the number to check
     * @return true if number is even, false otherwise
     */
    public boolean isEven(int number) {
        return number % 2 == 0;
    }

    /**
     * Calculates the factorial of a number.
     * @param n the number to calculate factorial for
     * @return factorial of n
     * @throws IllegalArgumentException if n is negative
     */
    public long factorial(int n) {
        if (n < 0) {
            throw new IllegalArgumentException("Factorial is not defined for negative numbers");
        }
        if (n == 0 || n == 1) {
            return 1;
        }
        long result = 1;
        for (int i = 2; i <= n; i++) {
            result *= i;
        }
        return result;
    }
}
