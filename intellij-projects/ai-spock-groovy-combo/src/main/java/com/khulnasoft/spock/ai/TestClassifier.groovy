package com.khulnasoft.spock.ai;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * Automatic Test Classification System
 * Classifies tests as Unit, Integration, or Functional based on code analysis
 */
public class TestClassifier {

    enum TestType {
        UNIT, INTEGRATION, FUNCTIONAL, PERFORMANCE, SECURITY
    }

    enum TestCategory {
        POSITIVE, NEGATIVE, EDGE_CASE, BOUNDARY, STRESS, REGRESSION
    }

    /**
     * Classifies a test method based on its content and context.
     */
    TestClassification classifyTest(String testName, String testContent, String sourceContext) {
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
    List<TestClassification> classifyTests(List testMethods, String sourceContext) {
        testMethods.stream().map(testMethod ->
            classifyTest(testMethod.name, testMethod.content, sourceContext)
        ).collect(Collectors.toList())
    }

    private TestType determineTestType(String testContent, String sourceContext) {
        String content = (testContent + (sourceContext ?: '')).toLowerCase()

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
        String content = (testName + ' ' + testContent).toLowerCase()

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
        int indicators = 0
        int totalChecks = 0

        String content = (testContent + (sourceContext ?: '')).toLowerCase()

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
        List<String> tags = new ArrayList<>();

        String content = testContent.toLowerCase()

        if (content.contains('fast')) tags.add('fast')
        if (content.contains('slow')) tags.add('slow')
        if (content.contains('integration')) tags.add('integration')
        if (content.contains('unit')) tags.add('unit')
        if (content.contains('smoke')) tags.add('smoke')
        if (content.contains('regression')) tags.add('regression')
        if (content.contains('critical')) tags.add('critical')
        if (content.contains('happy.path')) tags.add('happy-path')

        tags
    }

    private List<String> extractDependencies(String testContent) {
        List<String> dependencies = new ArrayList<>();

        String content = testContent.toLowerCase()

        if (content.contains('database') || content.contains('db')) dependencies.add('database')
        if (content.contains('http') || content.contains('rest')) dependencies.add('http-client')
        if (content.contains('selenium') || content.contains('webdriver')) dependencies.add('selenium')
        if (content.contains('docker')) dependencies.add('docker')
        if (content.contains('kafka')) dependencies.add('kafka')
        if (content.contains('redis')) dependencies.add('redis')

        dependencies
    }

    private String calculateComplexity(String testContent) {
        int complexity = 0

        // Count setup/teardown blocks
        int setupCount = testContent.findAll('given:').size()
        int teardownCount = testContent.findAll('cleanup:').size()

        // Count assertions
        int assertionCount = testContent.findAll(/\bassert\b|\bthen:/).size()

        // Count mocking/stubbing
        int mockCount = testContent.findAll(/\bMock\b|\bStub\b/).size()

        // Count external dependencies
        int externalDeps = extractDependencies(testContent).size()

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
        List methods = new ArrayList<>();

        // Simple regex to find test methods
        Pattern methodPattern = Pattern.compile("def\\s+\\"([^"]+)\\"");
        Matcher matches = methodPattern.matcher(testContent);

        while (matches.find()) {
            methods.add([
                name: matches.group(1),
                content: extractMethodContent(testContent, match[1])
            ]
        }

        methods
    }

    private String extractMethodContent(String testContent, String methodName) {
        // Simple extraction - in a real implementation, use proper AST parsing
        int startIndex = testContent.indexOf("def \"" + methodName + "\"");
        int endIndex = testContent.indexOf("def \"", startIndex + 1);
        if (endIndex == -1) endIndex = testContent.length();

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
        System.out.println("Test Classification Report");
        System.out.println("=" * 40);
        System.out.println("Total Tests: ${report.statistics.totalTests}");
        System.out.println("\nBy Type:");
        report.statistics.byType.each { type, count ->
            System.out.println("  ${type}: ${count}");
        }
        System.out.println("\nBy Category:");
        report.statistics.byCategory.each { category, count ->
            System.out.println("  ${category}: ${count}");
        }
        System.out.println("\nBy Complexity:");
        report.statistics.byComplexity.each { complexity, count ->
            System.out.println("  ${complexity}: ${count}");
        }

        // Save detailed report
        new File('build/reports/test-classification.json').withWriter { writer ->
            writer.write groovy.json.JsonOutput.prettyPrint(groovy.json.JsonOutput.toJson(report))
        }
    }
}
