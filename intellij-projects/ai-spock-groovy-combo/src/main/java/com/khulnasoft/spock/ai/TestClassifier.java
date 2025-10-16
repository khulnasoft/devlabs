package com.khulnasoft.spock.ai

/**
 * Automatic Test Classification System
 * Classifies tests as Unit, Integration, or Functional based on code analysis
 */
class TestClassifier {

    enum TestType {
        UNIT, INTEGRATION, FUNCTIONAL, PERFORMANCE, SECURITY
    }

    enum TestCategory {
        POSITIVE, NEGATIVE, EDGE_CASE, BOUNDARY, STRESS, REGRESSION
    }

    /**
     * Classifies a test method based on its content and context.
     */
    TestClassification classifyTest(String testName, String testContent, String sourceContext = null) {
        def classification = new TestClassification()

        // Primary classification based on content analysis
        classification.testType = determineTestType(testContent, sourceContext)
        classification.category = determineCategory(testName, testContent)
        classification.confidence = calculateConfidence(testContent, sourceContext)

        // Additional metadata
        classification.tags = extractTags(testContent)
        classification.dependencies = extractDependencies(testContent)
        classification.complexity = calculateComplexity(testContent)

        classification
    }

    /**
     * Batch classifies multiple test methods.
     */
    List<TestClassification> classifyTests(List testMethods, String sourceContext = null) {
        testMethods.collect { testMethod ->
            classifyTest(testMethod.name, testMethod.content, sourceContext)
        }
    }

    private TestType determineTestType(String testContent, String sourceContext) {
        def content = (testContent + (sourceContext ?: '')).toLowerCase()

        // Integration test indicators
        if (content.contains('database') || content.contains('repository') ||
            content.contains('dao') || content.contains('jdbc') ||
            content.contains('hibernate') || content.contains('jpa') ||
            content.contains('entitymanager') || content.contains('datasource')) {
            return TestType.INTEGRATION
        }

        // Functional test indicators
        if (content.contains('http') || content.contains('rest') ||
            content.contains('web') || content.contains('client') ||
            content.contains('endpoint') || content.contains('api') ||
            content.contains('selenium') || content.contains('webdriver') ||
            content.contains('browser') || content.contains('ui')) {
            return TestType.FUNCTIONAL
        }

        // Performance test indicators
        if (content.contains('performance') || content.contains('load') ||
            content.contains('stress') || content.contains('benchmark') ||
            content.contains('throughput') || content.contains('latency')) {
            return TestType.PERFORMANCE
        }

        // Security test indicators
        if (content.contains('security') || content.contains('auth') ||
            content.contains('permission') || content.contains('access') ||
            content.contains('token') || content.contains('jwt')) {
            return TestType.SECURITY
        }

        // Default to unit test
        return TestType.UNIT
    }

    private TestCategory determineCategory(String testName, String testContent) {
        def content = (testName + ' ' + testContent).toLowerCase()

        // Negative test indicators
        if (content.contains('not') || content.contains('fail') ||
            content.contains('error') || content.contains('exception') ||
            content.contains('invalid') || content.contains('null') ||
            content.contains('empty') || content.contains('negative')) {
            return TestCategory.NEGATIVE
        }

        // Edge case indicators
        if (content.contains('edge') || content.contains('boundary') ||
            content.contains('corner') || content.contains('extreme') ||
            content.contains('limit') || content.contains('maximum') ||
            content.contains('minimum') || content.contains('zero')) {
            return TestCategory.EDGE_CASE
        }

        // Boundary indicators
        if (content.contains('boundary') || content.contains('range') ||
            content.contains('threshold') || content.contains('constraint')) {
            return TestCategory.BOUNDARY
        }

        // Stress test indicators
        if (content.contains('stress') || content.contains('load') ||
            content.contains('volume') || content.contains('concurrent')) {
            return TestCategory.STRESS
        }

        // Default to positive
        return TestCategory.POSITIVE
    }

    private double calculateConfidence(String testContent, String sourceContext) {
        def indicators = 0
        def totalChecks = 0

        def content = (testContent + (sourceContext ?: '')).toLowerCase()

        // Check for clear indicators
        def typeIndicators = [
            'database': TestType.INTEGRATION,
            'http': TestType.FUNCTIONAL,
            'performance': TestType.PERFORMANCE,
            'security': TestType.SECURITY,
            'repository': TestType.INTEGRATION,
            'dao': TestType.INTEGRATION,
            'jdbc': TestType.INTEGRATION,
            'selenium': TestType.FUNCTIONAL,
            'webdriver': TestType.FUNCTIONAL
        ]

        typeIndicators.each { indicator, expectedType ->
            totalChecks++
            if (content.contains(indicator)) {
                indicators++
            }
        }

        // Check for category indicators
        def categoryIndicators = [
            'fail': TestCategory.NEGATIVE,
            'error': TestCategory.NEGATIVE,
            'exception': TestCategory.NEGATIVE,
            'edge': TestCategory.EDGE_CASE,
            'boundary': TestCategory.BOUNDARY,
            'stress': TestCategory.STRESS
        ]

        categoryIndicators.each { indicator, expectedCategory ->
            totalChecks++
            if (content.contains(indicator)) {
                indicators++
            }
        }

        return totalChecks > 0 ? (indicators / totalChecks) : 0.5
    }

    private List<String> extractTags(String testContent) {
        def tags = []

        def content = testContent.toLowerCase()

        if (content.contains('fast')) tags << 'fast'
        if (content.contains('slow')) tags << 'slow'
        if (content.contains('integration')) tags << 'integration'
        if (content.contains('unit')) tags << 'unit'
        if (content.contains('smoke')) tags << 'smoke'
        if (content.contains('regression')) tags << 'regression'
        if (content.contains('critical')) tags << 'critical'
        if (content.contains('happy.path')) tags << 'happy-path'

        tags
    }

    private List<String> extractDependencies(String testContent) {
        def dependencies = []

        def content = testContent.toLowerCase()

        if (content.contains('database') || content.contains('db')) dependencies << 'database'
        if (content.contains('http') || content.contains('rest')) dependencies << 'http-client'
        if (content.contains('selenium') || content.contains('webdriver')) dependencies << 'selenium'
        if (content.contains('docker')) dependencies << 'docker'
        if (content.contains('kafka')) dependencies << 'kafka'
        if (content.contains('redis')) dependencies << 'redis'

        dependencies
    }

    private String calculateComplexity(String testContent) {
        def complexity = 0

        // Count setup/teardown blocks
        def setupCount = testContent.findAll('given:').size()
        def teardownCount = testContent.findAll('cleanup:').size()

        // Count assertions
        def assertionCount = testContent.findAll(/\bassert\b|\bthen:/).size()

        // Count mocking/stubbing
        def mockCount = testContent.findAll(/\bMock\b|\bStub\b/).size()

        // Count external dependencies
        def externalDeps = extractDependencies(testContent).size()

        complexity = setupCount + teardownCount + (assertionCount * 0.5) + (mockCount * 0.3) + (externalDeps * 0.8)

        if (complexity <= 2) return 'LOW'
        if (complexity <= 5) return 'MEDIUM'
        return 'HIGH'
    }
}

/**
 * Test classification result container.
 */
class TestClassification {
    TestClassifier.TestType testType
    TestClassifier.TestCategory category
    double confidence
    List<String> tags
    List<String> dependencies
    String complexity

    String toString() {
        "TestClassification{type=$testType, category=$category, confidence=${String.format('%.2f', confidence)}, tags=$tags, complexity=$complexity}"
    }

    Map toMap() {
        [
            testType: testType?.toString(),
            category: category?.toString(),
            confidence: confidence,
            tags: tags,
            dependencies: dependencies,
            complexity: complexity
        ]
    }
}

/**
 * Gradle plugin for automatic test classification and reporting.
 */
class TestClassificationPlugin {

    void apply(org.gradle.api.Project project) {
        project.tasks.register('classifyTests') {
            group = 'verification'
            description = 'Classifies tests and generates classification report'

            doLast {
                def classifier = new TestClassifier()
                def classifications = []

                // Find all test files
                def testFiles = project.fileTree('src/test') {
                    include '**/*Spec.groovy'
                    include '**/*Test.groovy'
                    include '**/*Tests.groovy'
                }

                testFiles.each { testFile ->
                    def testContent = testFile.text

                    // Extract test methods (simplified parsing)
                    def testMethods = extractTestMethods(testContent)

                    testMethods.each { testMethod ->
                        def classification = classifier.classifyTest(testMethod.name, testMethod.content)
                        classifications << [
                            file: testFile.name,
                            method: testMethod.name,
                            classification: classification
                        ]
                    }
                }

                // Generate report
                generateClassificationReport(classifications)
            }
        }
    }

    private List extractTestMethods(String testContent) {
        def methods = []

        // Simple regex to find test methods
        def methodPattern = /def\s+"([^"]+)"/
        def matches = testContent =~ methodPattern

        matches.each { match ->
            methods << [
                name: match[1],
                content: extractMethodContent(testContent, match[1])
            ]
        }

        methods
    }

    private String extractMethodContent(String testContent, String methodName) {
        // Simple extraction - in a real implementation, use proper AST parsing
        def startIndex = testContent.indexOf('def "' + methodName + '"')
        def endIndex = testContent.indexOf('def "', startIndex + 1)
        if (endIndex == -1) endIndex = testContent.length()

        testContent.substring(startIndex, endIndex)
    }

    private void generateClassificationReport(List classifications) {
        def report = [:]

        // Group by test type
        report.byType = classifications.groupBy { it.classification.testType }

        // Group by category
        report.byCategory = classifications.groupBy { it.classification.category }

        // Group by complexity
        report.byComplexity = classifications.groupBy { it.classification.complexity }

        // Calculate statistics
        report.statistics = [
            totalTests: classifications.size(),
            byType: report.byType.collectEntries { type, tests -> [type.toString(), tests.size()] },
            byCategory: report.byCategory.collectEntries { category, tests -> [category.toString(), tests.size()] },
            byComplexity: report.byComplexity.collectEntries { complexity, tests -> [complexity, tests.size()] }
        ]

        // Print report
        println "Test Classification Report"
        println "=" * 40
        println "Total Tests: ${report.statistics.totalTests}"
        println "\nBy Type:"
        report.statistics.byType.each { type, count ->
            println "  ${type}: ${count}"
        }
        println "\nBy Category:"
        report.statistics.byCategory.each { category, count ->
            println "  ${category}: ${count}"
        }
        println "\nBy Complexity:"
        report.statistics.byComplexity.each { complexity, count ->
            println "  ${complexity}: ${count}"
        }

        // Save detailed report
        new File('build/reports/test-classification.json').withWriter { writer ->
            writer.write groovy.json.JsonOutput.prettyPrint(groovy.json.JsonOutput.toJson(report))
        }
    }
}
