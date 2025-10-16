package com.khulnasoft.spock.ai

import groovy.json.JsonBuilder
import groovy.json.JsonSlurper

/**
 * AI-powered test generation framework for Spock.
 * Generates Spock test specifications from natural language descriptions.
 */
class AiTestGenerator {

    private final AiTestAnalyzer analyzer
    private final TestTemplateEngine templateEngine

    AiTestGenerator() {
        this.analyzer = new AiTestAnalyzer()
        this.templateEngine = new TestTemplateEngine()
    }

    /**
     * Generates Spock test specifications from natural language descriptions.
     *
     * @param sourceCode The source code to generate tests for
     * @param requirements Natural language test requirements
     * @return Generated Spock test specification
     */
    String generateSpockTests(String sourceCode, String requirements) {
        // Analyze the source code to understand the class structure
        def analysis = analyzer.analyzeSourceCode(sourceCode)

        // Parse requirements into test scenarios
        def scenarios = analyzer.parseRequirements(requirements)

        // Generate test specification
        def testSpec = templateEngine.generateTestSpecification(analysis, scenarios)

        return testSpec
    }

    /**
     * Generates test data for parameterized tests.
     *
     * @param methodSignature Method signature to generate data for
     * @param dataRequirements Description of test data needed
     * @return Generated where-block data
     */
    String generateTestData(String methodSignature, String dataRequirements) {
        def methodInfo = analyzer.analyzeMethod(methodSignature)
        def dataScenarios = analyzer.parseDataRequirements(dataRequirements)

        return templateEngine.generateWhereBlock(methodInfo, dataScenarios)
    }
}

/**
 * Analyzes source code to understand class and method structures.
 */
class AiTestAnalyzer {

    def analyzeSourceCode(String sourceCode) {
        def analysis = [:]

        // Extract class information
        def classMatch = sourceCode =~ /class\s+(\w+)/
        analysis.className = classMatch.find() ? classMatch.group(1) : 'UnknownClass'

        // Extract method information
        def methodMatches = sourceCode =~ /def\s+(\w+)\s*\([^)]*\)\s*{/
        analysis.methods = methodMatches.collect { it[1] }

        // Extract field information (simplified)
        def fieldMatches = sourceCode =~ /def\s+(\w+)\s*=/
        analysis.fields = fieldMatches.collect { it[1] }

        return analysis
    }

    def analyzeMethod(String methodSignature) {
        def match = methodSignature =~ /(\w+)\s*\(([^)]*)\)/
        if (match.find()) {
            return [
                name: match.group(1),
                parameters: match.group(2).split(',').collect { it.trim() }
            ]
        }
        return [name: 'unknown', parameters: []]
    }

    def parseRequirements(String requirements) {
        // Simple parsing - in a real implementation, this would use NLP
        def scenarios = []

        // Split by common test scenario indicators
        def scenarioTexts = requirements.split(/\b(when|given|then|and|but)\b/)

        scenarioTexts.eachWithIndex { text, index ->
            if (index % 2 == 1 && text.trim()) {
                def scenario = [
                    type: text.toLowerCase().trim(),
                    description: scenarioTexts[index + 1]?.trim() ?: ''
                ]
                scenarios << scenario
            }
        }

        return scenarios
    }

    def parseDataRequirements(String dataRequirements) {
        // Simple parsing for data requirements
        def dataScenarios = []

        def lines = dataRequirements.split('\n')
        lines.each { line ->
            if (line.trim() && !line.startsWith('//') && !line.startsWith('*')) {
                dataScenarios << parseDataLine(line)
            }
        }

        return dataScenarios
    }

    private def parseDataLine(String line) {
        // Simple parsing for data lines like "input | expected"
        def parts = line.split('\\|').collect { it.trim() }
        return [
            inputs: parts.take(parts.size() - 1),
            expected: parts.last()
        ]
    }
}

/**
 * Template engine for generating Spock test specifications.
 */
class TestTemplateEngine {

    String generateTestSpecification(Map analysis, List scenarios) {
        def spec = new StringBuilder()

        spec.append("""
import spock.lang.Specification
import spock.lang.Subject

class ${analysis.className}Spec extends Specification {

    @Subject
    ${analysis.className} subject = new ${analysis.className}()

""")

        // Generate test methods based on scenarios
        scenarios.eachWithIndex { scenario, index ->
            def methodName = generateMethodName(scenario, index)
            spec.append(generateTestMethod(methodName, scenario, analysis))
        }

        spec.append("}\n")
        return spec.toString()
    }

    private String generateMethodName(Map scenario, int index) {
        def baseName = scenario.description
            .replaceAll(/[^a-zA-Z0-9]/, '_')
            .replaceAll(/_+/, '_')
            .toLowerCase()

        return baseName ?: "test_scenario_${index + 1}"
    }

    private String generateTestMethod(String methodName, Map scenario, Map analysis) {
        return """
    def "${methodName}"() {
        given: "Setup for ${scenario.description}"
        // Setup code would be generated here based on scenario

        when: "Execute the scenario"
        def result = subject.${analysis.methods.first()}()

        then: "Verify the expected outcome"
        // Assertions would be generated here based on scenario
        true // Placeholder assertion
    }

"""
    }

    String generateWhereBlock(Map methodInfo, List dataScenarios) {
        def whereBlock = new StringBuilder()
        whereBlock.append("    where:\n")

        if (dataScenarios) {
            def maxInputs = dataScenarios.collect { it.inputs.size() }.max()
            def headers = ['input'] * maxInputs + ['expected']

            whereBlock.append("        ${headers.join(' | ')}\n")
            whereBlock.append("        ${'-' * (headers.join(' | ').length())}\n")

            dataScenarios.each { scenario ->
                def row = scenario.inputs + [scenario.expected]
                whereBlock.append("        ${row.join(' | ')}\n")
            }
        } else {
            whereBlock.append("        // No data scenarios defined\n")
        }

        return whereBlock.toString()
    }
}
