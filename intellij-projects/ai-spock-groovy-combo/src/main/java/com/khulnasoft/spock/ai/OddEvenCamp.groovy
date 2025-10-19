package com.khulnasoft.spock.ai

import spock.lang.Specification
import spock.lang.Subject

class OddEvenCamp {
    int check(int number) {
        if (number < 0) {
            throw new NegativeNumberException("Number must be non-negative")
        }
        return number % 2
    }
}

class NegativeNumberException extends RuntimeException {
    NegativeNumberException(String message) {
        super(message)
    }
}
