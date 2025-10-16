package com.khulnasoft.spock.ai

/**
 * Natural Language to Test Specification Pipeline
 * Converts natural language requirements into Spock test specifications
 */
class NaturalLanguageTestPipeline {

    private final AiTestGenerator testGenerator
    private final TestRequirementParser requirementParser
    private final TestSpecificationFormatter formatter

    NaturalLanguageTestPipeline() {
        this.testGenerator = new AiTestGenerator()
        this.requirementParser = new TestRequirementParser()
        this.formatter = new TestSpecificationFormatter()
    }

    /**
     * Converts natural language requirements into Spock test specifications.
     *
     * @param requirements Natural language test requirements
     * @param sourceCode Optional source code context
     * @return Generated Spock test specification
     */
    String generateTestSpec(String requirements, String sourceCode = null) {
        // Parse requirements into structured format
        def parsedRequirements = requirementParser.parseRequirements(requirements)

        // Enhance with source code context if provided
        if (sourceCode) {
            def context = requirementParser.extractContext(sourceCode)
            parsedRequirements = requirementParser.enhanceWithContext(parsedRequirements, context)
        }

        // Generate test scenarios
        def testScenarios = generateTestScenarios(parsedRequirements)

        // Format as Spock specification
        def testSpec = formatter.formatAsSpockSpec(testScenarios)

        return testSpec
    }

    private List generateTestScenarios(List parsedRequirements) {
        def scenarios = []

        parsedRequirements.each { requirement ->
            def scenario = [
                name: requirement.name,
                description: requirement.description,
                given: requirement.given,
                when: requirement.when,
                then: requirement.then,
                data: requirement.data
            ]

            scenarios << scenario
        }

        scenarios
    }
}

/**
 * Parses natural language test requirements into structured format.
 */
class TestRequirementParser {

    List parseRequirements(String requirementsText) {
        def requirements = []

        // Split by common requirement patterns
        def requirementBlocks = requirementsText.split(/\n\s*(?=\d+\.|\-|\*|Given|When|Then)/)

        requirementBlocks.each { block ->
            def requirement = parseRequirementBlock(block.trim())
            if (requirement) {
                requirements << requirement
            }
        }

        requirements
    }

    private Map parseRequirementBlock(String block) {
        def requirement = [:]

        // Extract requirement name
        def nameMatch = block =~ /^(?:As a|I want|I need|Given that|When|Then)\s+(.+?)(?=\n|$)/
        if (nameMatch.find()) {
            requirement.name = nameMatch.group(1).trim()
        }

        // Extract Given-When-Then structure
        def givenMatch = block =~ /(?i)given\s+(?:that\s+)?(.+?)(?=\n|$)/
        if (givenMatch.find()) {
            requirement.given = givenMatch.group(1).trim()
        }

        def whenMatch = block =~ /(?i)when\s+(.+?)(?=\n|$)/
        if (whenMatch.find()) {
            requirement.when = whenMatch.group(1).trim()
        }

        def thenMatch = block =~ /(?i)then\s+(.+?)(?=\n|$)/
        if (thenMatch.find()) {
            requirement.then = thenMatch.group(1).trim()
        }

        // Extract test data if present
        def dataMatch = block =~ /(?i)with\s+(?:data|values?|parameters?)\s*:?\s*(.+)/
        if (dataMatch.find()) {
            requirement.data = parseInlineData(dataMatch.group(1))
        }

        return requirement
    }

    private List parseInlineData(String dataText) {
        def data = []

        // Handle simple inline data format
        if (dataText.contains('|')) {
            def lines = dataText.split('\n')
            def headers = lines[0].split('\\|').collect { it.trim() }

            lines[1..-1].each { line ->
                if (line.trim()) {
                    def values = line.split('\\|').collect { it.trim() }
                    def dataRow = [:]
                    headers.eachWithIndex { header, index ->
                        dataRow[header] = values[index] ?: ''
                    }
                    data << dataRow
                }
            }
        }

        data
    }

    Map extractContext(String sourceCode) {
        def context = [:]

        // Extract class information
        def classMatch = sourceCode =~ /class\s+(\w+)/
        if (classMatch.find()) {
            context.className = classMatch.group(1)
        }

        // Extract method signatures
        def methodMatches = sourceCode =~ /def\s+(\w+)\s*\(([^)]*)\)/
        context.methods = methodMatches.collect { [name: it[1], parameters: it[2]] }

        // Extract field information
        def fieldMatches = sourceCode =~ /(?:def|@.*)\s+(\w+)\s*(?:=|$)/
        context.fields = fieldMatches.collect { it[1] }

        context
    }

    List enhanceWithContext(List requirements, Map context) {
        requirements.each { requirement ->
            // Enhance with class name if not specified
            if (!requirement.className && context.className) {
                requirement.className = context.className
            }

            // Enhance with method information if relevant
            if (requirement.when && context.methods) {
                requirement.relevantMethods = context.methods.findAll { method ->
                    requirement.when.toLowerCase().contains(method.name.toLowerCase())
                }
            }
        }

        requirements
    }
}

/**
 * Formats test scenarios as Spock specifications.
 */
class TestSpecificationFormatter {

    String formatAsSpockSpec(List scenarios) {
        if (scenarios.isEmpty()) {
            return "// No test scenarios generated\n"
        }

        def spec = new StringBuilder()
        def className = scenarios.first().className ?: 'GeneratedTest'

        spec.append("""
import spock.lang.Specification
import spock.lang.Subject
import spock.lang.Unroll

class ${className}Spec extends Specification {

    @Subject
    ${className} subject

    def setup() {
        subject = new ${className}()
    }

""")

        scenarios.eachWithIndex { scenario, index ->
            spec.append(formatTestMethod(scenario, index))
        }

        spec.append("}\n")
        return spec.toString()
    }

    private String formatTestMethod(Map scenario, int index) {
        def methodName = generateMethodName(scenario, index)

        def method = """
    def "${methodName}"() {
"""

        if (scenario.given) {
            method += """        given: "${scenario.given}"
        // Setup code would be generated based on given clause

"""
        }

        if (scenario.when) {
            method += """        when: "${scenario.when}"
        def result = subject.someMethod()  // This would be determined by AI analysis

"""
        }

        if (scenario.then) {
            method += """        then: "${scenario.then}"
        // Assertions would be generated based on then clause
        true  // Placeholder assertion

"""
        }

        if (scenario.data) {
            method += formatDataTable(scenario.data)
        }

        method += "    }\n\n"
        return method
    }

    private String formatDataTable(List data) {
        if (data.isEmpty()) return ""

        def dataTable = "        where:\n"
        def headers = data.first().keySet() as List

        dataTable += "        ${headers.join(' | ')}\n"
        dataTable += "        ${'-' * (headers.join(' | ').length())}\n"

        data.each { row ->
            def values = headers.collect { row[it] ?: '' }
            dataTable += "        ${values.join(' | ')}\n"
        }

        dataTable
    }

    private String generateMethodName(Map scenario, int index) {
        def name = scenario.name ?: scenario.description ?: "test_scenario_${index + 1}"
        name = name.replaceAll(/[^a-zA-Z0-9_]/, '_')
        name = name.replaceAll(/_+/, '_')
        name = name.toLowerCase()

        return name
    }
}

/**
 * Command-line interface for the natural language test pipeline.
 */
class TestSpecCli {

    static void main(String[] args) {
        if (args.length < 1) {
            println "Usage: TestSpecCli <requirements-file> [source-code-file]"
            println "Example: TestSpecCli requirements.txt MyClass.groovy"
            return
        }

        def requirementsFile = new File(args[0])
        def sourceCodeFile = args.length > 1 ? new File(args[1]) : null

        if (!requirementsFile.exists()) {
            println "Requirements file not found: ${args[0]}"
            return
        }

        def requirements = requirementsFile.text
        def sourceCode = sourceCodeFile?.exists() ? sourceCodeFile.text : null

        def pipeline = new NaturalLanguageTestPipeline()
        def testSpec = pipeline.generateTestSpec(requirements, sourceCode)

        println "Generated Spock Test Specification:"
        println "=" * 50
        println testSpec
    }
}
