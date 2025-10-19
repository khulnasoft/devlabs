#!/usr/bin/env groovy

// Simple test script to verify AI pipeline functionality

def pipeline = new com.khulnasoft.spock.ai.NaturalLanguageTestPipeline()
def requirements = """
## Feature: Number Classification
As a developer, I want to classify numbers as even or odd.

### Scenario: Positive Even Number Classification
Given that I have a positive even number
When I call the check method on the OddEvenCamp
Then I should get 1 as the result

### Scenario: Positive Odd Number Classification
Given that I have a positive odd number
When I call the check method on the OddEvenCamp
Then I should get 0 as the result

### Scenario: Zero Input
Given that I have zero as input
When I call the check method on the OddEvenCamp
Then I should get a NegativeNumberException
"""

def sourceCode = """
package com.khulnasoft.spock.ai

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
"""

println "Testing AI Pipeline..."
def testSpec = pipeline.generateTestSpec(requirements, sourceCode)

println "Generated Test Specification:"
println "=" * 50
println testSpec

// Save to file
def outputDir = new File('build/generated-tests')
outputDir.mkdirs()
def outputFile = new File(outputDir, 'TestSpecDemo.groovy')
outputFile.text = testSpec

println "Test specification saved to ${outputFile.absolutePath}"
