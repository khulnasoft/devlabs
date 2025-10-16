package com.khulnasoft.spock

import com.khulnasoft.spock.ai.TestClassifier
import spock.lang.Specification
import spock.lang.Subject
import spock.lang.Unroll

/**
 * AI-Enhanced Spock Test demonstrating natural language test generation
 * and automatic test classification capabilities
 */
class OddEvenCampAiSpec extends Specification {

    @Subject
    OddEvenCamp oddEvenCamp = new OddEvenCampImpl(new NegativeValidator())

    def "Verify even number classification"() {
        given: "An even number for classification"
        def number = 42

        when: "The number is checked for parity"
        def result = oddEvenCamp.check(number)

        then: "It should return 1 for even numbers"
        result == 1
    }

    def "Verify odd number classification"() {
        given: "An odd number for classification"
        def number = 23

        when: "The number is checked for parity"
        def result = oddEvenCamp.check(number)

        then: "It should return 0 for odd numbers"
        result == 0
    }

    @Unroll
    def "Test boundary conditions - #scenario"() {
        given: "A boundary test scenario"
        def number = input

        when: "Testing boundary condition"
        def result = shouldThrow ? { oddEvenCamp.check(number) } : { oddEvenCamp.check(number) }

        then: "Verify expected behavior"
        if (shouldThrow) {
            assertThrows(NegativeNumberException) {
                result()
            }
        } else {
            result() == expected
        }

        where: "Test data for boundary conditions"
        scenario          | input | expected | shouldThrow
        "Zero input"      | 0     | null     | true
        "Negative input"  | -5    | null     | true
        "Positive even"   | 10    | 1        | false
        "Positive odd"    | 7     | 0        | false
        "Large even"      | 1000  | 1        | false
        "Large odd"       | 999   | 0        | false
    }

    def "Test performance under load"() {
        given: "Multiple numbers to process"
        def numbers = (1..1000).collect { it }

        when: "Processing all numbers"
        def startTime = System.currentTimeMillis()
        def results = numbers.collect { oddEvenCamp.check(it) }
        def duration = System.currentTimeMillis() - startTime

        then: "Should complete within reasonable time"
        duration < 1000 // Less than 1 second for 1000 operations
        results.every { it in [0, 1] } // All results should be valid
    }

    def "Verify error handling for invalid inputs"() {
        when: "Passing null input"
        oddEvenCamp.check(null)

        then: "Should handle gracefully"
        thrown(NullPointerException)
    }

    def "Demonstrate AI-powered test analysis"() {
        given: "Test classifier for automatic categorization"
        def classifier = new TestClassifier()

        when: "Classifying this test"
        def classification = classifier.classifyTest("Verify even number classification", getCurrentTestContent())

        then: "Should be classified correctly"
        classification.testType.toString() == "UNIT"
        classification.category.toString() == "POSITIVE"
        classification.confidence > 0.7
    }

    private String getCurrentTestContent() {
        // In a real implementation, this would extract the current test method content
        return "Unit test for positive case verification"
    }
}

/**
 * Integration test example showing database and external service testing
 */
class OddEvenCampIntegrationSpec extends Specification {

    @Subject
    OddEvenCamp oddEvenCamp = new OddEvenCampImpl(new NegativeValidator())

    def "Integration test with external validation service"() {
        given: "External validation service mock"
        def externalService = Mock(ExternalValidationService) {
            validateNumber(_) >> true
        }

        and: "Service wrapper"
        def serviceWrapper = new OddEvenCampServiceWrapper(oddEvenCamp, externalService)

        when: "Validating through external service"
        def result = serviceWrapper.validateAndCheck(42)

        then: "Should integrate properly"
        result.valid == true
        result.even == true
        1 * externalService.validateNumber(42)
    }
}

/**
 * Performance test example
 */
class OddEvenCampPerformanceSpec extends Specification {

    @Subject
    OddEvenCamp oddEvenCamp = new OddEvenCampImpl(new NegativeValidator())

    def "Performance test for large dataset"() {
        given: "Large dataset"
        def largeDataset = (1..100000).collect { it }

        when: "Processing large dataset"
        def startTime = System.currentTimeMillis()
        def results = largeDataset.collect { oddEvenCamp.check(it) }
        def duration = System.currentTimeMillis() - startTime

        then: "Should meet performance requirements"
        duration < 5000 // Less than 5 seconds for 100k operations
        results.size() == 100000
        results.every { it in [0, 1] }
    }
}

/**
 * Mock external service for integration testing
 */
interface ExternalValidationService {
    boolean validateNumber(int number)
}

/**
 * Service wrapper for integration testing
 */
class OddEvenCampServiceWrapper {
    private final OddEvenCamp camp
    private final ExternalValidationService validationService

    OddEvenCampServiceWrapper(OddEvenCamp camp, ExternalValidationService validationService) {
        this.camp = camp
        this.validationService = validationService
    }

    Map validateAndCheck(int number) {
        def valid = validationService.validateNumber(number)
        def even = camp.check(number) == 1
        [valid: valid, even: even]
    }
}

/**
 * Custom validator for testing validation logic
 */
class NegativeValidator implements Validator<Integer> {
    @Override
    void validate(Integer value) {
        if (value == null || value <= 0) {
            throw new NegativeNumberException("Number must be positive: " + value)
        }
    }
}
