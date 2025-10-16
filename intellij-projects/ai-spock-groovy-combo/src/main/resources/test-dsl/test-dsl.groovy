/**
 * KhulnaSoft Test DSL - Reusable testing utilities for Spock framework
 * Provides common testing patterns and AI-enhanced test capabilities
 */

// Import guard
if (!binding.variables.containsKey('testDslLoaded')) {
    testDslLoaded = true

    // Common assertion utilities
    static def assertSuccess(def result, def expected = null) {
        assert result != null
        if (expected != null) {
            assert result == expected
        }
        result
    }

    static def assertFailure(def closure) {
        def failed = false
        try {
            closure.call()
        } catch (Exception e) {
            failed = true
        }
        assert failed, "Expected operation to fail but it succeeded"
    }

    static def assertThrows(Class<? extends Exception> exceptionClass, def closure) {
        def thrownException = null
        try {
            closure.call()
        } catch (Exception e) {
            thrownException = e
        }
        assert thrownException != null, "Expected exception ${exceptionClass.simpleName} but none was thrown"
        assert exceptionClass.isInstance(thrownException), "Expected ${exceptionClass.simpleName} but got ${thrownException.class.simpleName}"
        thrownException
    }

    // Data generation utilities
    static def randomInt(int min = 1, int max = 1000) {
        new Random().nextInt(max - min + 1) + min
    }

    static def randomString(int length = 10) {
        def chars = 'abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789'
        (1..length).collect { chars[new Random().nextInt(chars.length())] }.join()
    }

    static def randomEmail() {
        "${randomString(8)}@${randomString(5)}.com"
    }

    static def randomPhone() {
        "+1${new Random().nextInt(900) + 100}${new Random().nextInt(9000) + 1000}"
    }

    // Test data builders
    static def userData(Map overrides = [:]) {
        [
            id: overrides.id ?: randomInt(),
            name: overrides.name ?: randomString(10),
            email: overrides.email ?: randomEmail(),
            phone: overrides.phone ?: randomPhone(),
            age: overrides.age ?: randomInt(18, 80),
            active: overrides.active ?: true
        ]
    }

    static def productData(Map overrides = [:]) {
        [
            id: overrides.id ?: randomInt(),
            name: overrides.name ?: "Product ${randomString(5)}",
            price: overrides.price ?: randomInt(10, 1000),
            category: overrides.category ?: ['Electronics', 'Books', 'Clothing'][new Random().nextInt(3)],
            inStock: overrides.inStock ?: true,
            description: overrides.description ?: "A great ${randomString(8)} product"
        ]
    }

    static def orderData(Map overrides = [:]) {
        [
            id: overrides.id ?: randomInt(),
            userId: overrides.userId ?: randomInt(),
            products: overrides.products ?: [productData()],
            totalAmount: overrides.totalAmount ?: randomInt(50, 2000),
            status: overrides.status ?: ['PENDING', 'CONFIRMED', 'SHIPPED', 'DELIVERED'][new Random().nextInt(4)],
            createdDate: overrides.createdDate ?: new Date()
        ]
    }

    // Test scenario helpers
    static def withUser(def closure) {
        def user = userData()
        closure.call(user)
        user
    }

    static def withProduct(def closure) {
        def product = productData()
        closure.call(product)
        product
    }

    static def withOrder(def closure) {
        def order = orderData()
        closure.call(order)
        order
    }

    // Validation helpers
    static def validateUser(Map user) {
        assert user.id > 0
        assert user.name?.length() > 0
        assert user.email?.contains('@')
        assert user.age >= 18
    }

    static def validateProduct(Map product) {
        assert product.id > 0
        assert product.name?.length() > 0
        assert product.price > 0
        assert product.category in ['Electronics', 'Books', 'Clothing']
    }

    static def validateOrder(Map order) {
        assert order.id > 0
        assert order.userId > 0
        assert order.products?.size() > 0
        assert order.totalAmount > 0
        assert order.status in ['PENDING', 'CONFIRMED', 'SHIPPED', 'DELIVERED']
    }

    // Performance testing utilities
    static def measureTime(def closure) {
        def start = System.currentTimeMillis()
        def result = closure.call()
        def duration = System.currentTimeMillis() - start
        [result: result, duration: duration]
    }

    static def assertPerformance(long maxTimeMs, def closure) {
        def measurement = measureTime(closure)
        assert measurement.duration <= maxTimeMs, "Operation took ${measurement.duration}ms, expected <= ${maxTimeMs}ms"
        measurement.result
    }

    // Mock helpers
    static def createMock(def classToMock) {
        def mock = Mock(classToMock)
        // Configure common mock behaviors
        mock.*(_) >> { args -> null }  // Default mock behavior
        mock
    }

    static def createStub(def classToStub) {
        def stub = Stub(classToStub)
        // Configure common stub behaviors
        stub.*(_) >> { args -> null }  // Default stub behavior
        stub
    }

    // Database testing utilities
    static def withCleanDatabase(def closure) {
        // In a real implementation, this would clean the database
        println "Setting up clean database state"
        def result = closure.call()
        println "Cleaning up database state"
        result
    }

    static def withTestData(Map data, def closure) {
        // In a real implementation, this would insert test data
        println "Inserting test data: $data"
        def result = closure.call()
        println "Cleaning up test data"
        result
    }

    // AI-enhanced test utilities
    static def generateTestCases(String description) {
        // Simple heuristic-based test case generation
        def cases = []

        if (description.contains('edge case') || description.contains('boundary')) {
            cases << [type: 'boundary', data: generateBoundaryCases(description)]
        }

        if (description.contains('negative') || description.contains('error')) {
            cases << [type: 'negative', data: generateNegativeCases(description)]
        }

        if (description.contains('positive') || description.contains('happy path')) {
            cases << [type: 'positive', data: generatePositiveCases(description)]
        }

        cases
    }

    private static def generateBoundaryCases(String description) {
        // Generate boundary test cases based on description
        [
            [input: 0, expected: 'zero_case'],
            [input: 1, expected: 'minimum_case'],
            [input: Integer.MAX_VALUE, expected: 'maximum_case']
        ]
    }

    private static def generateNegativeCases(String description) {
        // Generate negative test cases based on description
        [
            [input: null, expected: 'null_input'],
            [input: -1, expected: 'negative_input'],
            [input: '', expected: 'empty_input']
        ]
    }

    private static def generatePositiveCases(String description) {
        // Generate positive test cases based on description
        [
            [input: 'valid_input', expected: 'success_case'],
            [input: 'another_valid', expected: 'another_success']
        ]
    }

    // Test classification utilities
    static def classifyTest(String testName, String testContent) {
        def classification = [:]

        // Simple heuristic-based classification
        if (testContent.contains('database') || testContent.contains('repository')) {
            classification.type = 'integration'
        } else if (testContent.contains('http') || testContent.contains('rest') || testContent.contains('web')) {
            classification.type = 'functional'
        } else {
            classification.type = 'unit'
        }

        // Determine test category
        if (testContent.contains('performance') || testContent.contains('load')) {
            classification.category = 'performance'
        } else if (testContent.contains('security') || testContent.contains('auth')) {
            classification.category = 'security'
        } else {
            classification.category = 'functional'
        }

        classification
    }

    // Test reporting utilities
    static def generateTestReport(def testResults) {
        def report = [:]
        report.totalTests = testResults.size()
        report.passed = testResults.count { it.passed }
        report.failed = testResults.count { !it.passed }
        report.successRate = (report.passed / report.totalTests * 100).round(2)
        report.duration = testResults.sum { it.duration ?: 0 }

        // Group by test type
        report.byType = testResults.groupBy { classifyTest(it.name, it.content).type }

        report
    }

    // AI-powered test enhancement
    static def enhanceTestWithAI(String testCode, String requirements) {
        // This would integrate with actual AI services
        // For now, return enhanced version with comments
        def enhanced = testCode.replace('// TODO', '// ENHANCED: AI-generated improvement')

        // Add AI-generated assertions if missing
        if (!testCode.contains('assert') && !testCode.contains('then:')) {
            enhanced += '\n        then: "AI-generated assertion"\n        result != null'
        }

        enhanced
    }

    // Test data mutation for robustness testing
    static def mutateTestData(Map originalData) {
        def mutated = [:]
        mutated.putAll(originalData)

        // Randomly mutate some fields for robustness testing
        def keysToMutate = originalData.keySet().toList()
        Collections.shuffle(keysToMutate)

        def keyToMutate = keysToMutate.first()
        switch (originalData[keyToMutate].class) {
            case Integer:
                mutated[keyToMutate] = originalData[keyToMutate] + randomInt(-10, 10)
                break
            case String:
                mutated[keyToMutate] = randomString(originalData[keyToMutate].length())
                break
            case Boolean:
                mutated[keyToMutate] = !originalData[keyToMutate]
                break
        }

        mutated
    }

    // Contract testing utilities
    static def verifyContract(def consumer, def provider) {
        def contract = [
            consumer: consumer,
            provider: provider,
            verified: true,
            timestamp: new Date()
        ]

        // In a real implementation, this would verify API contracts
        println "Contract verification: ${contract.consumer} <-> ${contract.provider}"

        contract
    }
}

// DSL Extension methods for cleaner syntax
class TestDslExtensions {
    static def given(String description, def closure) {
        println "Given: $description"
        closure.call()
    }

    static def when(String description, def closure) {
        println "When: $description"
        def result = closure.call()
        println "Result: $result"
        result
    }

    static def then(String description, def closure) {
        println "Then: $description"
        def assertion = closure.call()
        println "Assertion: $assertion"
        assertion
    }

    static def and(String description, def closure) {
        println "And: $description"
        closure.call()
    }
}

// Make DSL available globally in test scripts
this.metaClass.mixin TestDslExtensions
