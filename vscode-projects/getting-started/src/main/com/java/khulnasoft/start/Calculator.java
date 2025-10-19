package com.khulnasoft.start;

/**
 * A simple calculator class that provides basic arithmetic operations.
 */
public class Calculator {

    /**
     * Adds two numbers.
     * @param a the first number
     * @param b the second number
     * @return the sum of a and b
     */
    public double add(double a, double b) {
        return a + b;
    }

    /**
     * Subtracts the second number from the first.
     * @param a the first number
     * @param b the second number to subtract
     * @return the difference (a - b)
     */
    public double subtract(double a, double b) {
        return a - b;
    }

    /**
     * Multiplies two numbers.
     * @param a the first number
     * @param b the second number
     * @return the product of a and b
     */
    public double multiply(double a, double b) {
        return a * b;
    }

    /**
     * Divides the first number by the second.
     * @param a the dividend
     * @param b the divisor
     * @return the quotient (a / b)
     * @throws ArithmeticException if b is zero
     */
    public double divide(double a, double b) {
        if (b == 0) {
            throw new ArithmeticException("Division by zero is not allowed");
        }
        return a / b;
    }

    /**
     * Main method for testing the calculator.
     * @param args command line arguments (not used)
     */
    public static void main(String[] args) {
        Calculator calc = new Calculator();
        
        // Test basic operations
        System.out.println("Addition: 5 + 3 = " + calc.add(5, 3));
        System.out.println("Subtraction: 10 - 4 = " + calc.subtract(10, 4));
        System.out.println("Multiplication: 6 * 7 = " + calc.multiply(6, 7));
        System.out.println("Division: 15 / 3 = " + calc.divide(15, 3));
        
        // Test division by zero
        try {
            System.out.println("Division by zero: " + calc.divide(5, 0));
        } catch (ArithmeticException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
