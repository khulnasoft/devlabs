package com.khulnasoft.spock.ai

import groovy.util.logging.Slf4j

/**
 * Natural Language to Test Specification Pipeline
 * Converts natural language requirements into Spock test specifications with enhanced NLP
 */
@Slf4j
class NaturalLanguageTestPipeline {

    private final AiTestGenerator testGenerator
    private final EnhancedTestRequirementParser requirementParser
    private final TestSpecificationFormatter formatter

    NaturalLanguageTestPipeline() {
        this.testGenerator = new AiTestGenerator()
        this.requirementParser = new EnhancedTestRequirementParser()
        this.formatter = new TestSpecificationFormatter()
    }

    /**
     * Converts natural language requirements into Spock test specifications.
     *
     * @param requirements Natural language test requirements
     * @param sourceCode Optional source code context
     * @return Generated Spock test specification
     */
    String generateTestSpec(String requirements, String sourceCode) {
        log.info("Generating test specification from natural language requirements")

        // Parse requirements into structured format with enhanced NLP
        List<Map<String, Object>> parsedRequirements = requirementParser.parseRequirements(requirements)

        // Enhance with source code context if provided
        if (sourceCode) {
            Map<String, Object> context = requirementParser.extractContext(sourceCode)
            parsedRequirements = requirementParser.enhanceWithContext(parsedRequirements, context)
        }

        // Generate comprehensive test scenarios
        List<Map<String, Object>> testScenarios = generateTestScenarios(parsedRequirements)

        // Format as Spock specification
        String spockSpec = formatter.formatAsSpockSpec(testScenarios)

        log.info("Generated Spock specification with ${testScenarios.size()} test scenarios")
        return spockSpec
    }

    private List<Map<String, Object>> generateTestScenarios(List<Map<String, Object>> parsedRequirements) {
        List<Map<String, Object>> scenarios = []

        for (Map<String, Object> requirement : parsedRequirements) {
            Map<String, Object> scenario = [:]

            // Enhanced scenario generation
            scenario.name = requirement.name ?: generateTestName(requirement)
            scenario.description = requirement.description ?: requirement.name
            scenario.given = requirement.given ?: extractGivenClause(requirement)
            scenario.when = requirement.when ?: extractWhenClause(requirement)
            scenario.then = requirement.then ?: extractThenClause(requirement)
            scenario.data = requirement.data ?: extractTestData(requirement)
            scenario.tags = requirement.tags ?: extractTags(requirement)
            scenario.priority = requirement.priority ?: 'medium'

            scenarios.add(scenario)
        }

        return scenarios
    }

    private String generateTestName(Map requirement) {
        // Generate meaningful test names from requirements
        def name = requirement.description ?: requirement.name ?: 'test_scenario'
        name = name.replaceAll(/[^a-zA-Z0-9\s]/, ' ').trim()
        name = name.split(/\s+/).collect { it.capitalize() }.join()
        name = name.replaceAll(/([A-Z])/, '_$1').toLowerCase()
        name = name.replaceAll(/^_/, '')

        return name.take(50) // Limit length
    }

    private extractGivenClause(Map requirement) {
        // Extract setup information from requirement
        def givenText = requirement.given ?: requirement.description ?: ''
        return givenText.contains('given') ? givenText : "Setup for ${requirement.name ?: 'test'}"
    }

    private extractWhenClause(Map requirement) {
        // Extract action information from requirement
        def whenText = requirement.when ?: requirement.description ?: ''
        return whenText.contains('when') ? whenText : "Execute ${requirement.name ?: 'test action'}"
    }

    private extractThenClause(Map requirement) {
        // Extract assertion information from requirement
        def thenText = requirement.then ?: requirement.description ?: ''
        return thenText.contains('then') ? thenText : "Verify ${requirement.name ?: 'test result'}"
    }

    private extractTestData(Map requirement) {
        // Extract test data from requirement
        def dataSection = requirement.find { k, v -> k == 'data' || k == 'examples' }
        return dataSection?.value ?: []
    }

    private extractTags(Map requirement) {
        def tags = []

        def content = (requirement.description ?: '') + ' ' + (requirement.name ?: '')
        content = content.toLowerCase()

        if (content.contains('performance') || content.contains('speed') || content.contains('fast')) {
            tags << 'performance'
        }
        if (content.contains('security') || content.contains('auth') || content.contains('permission')) {
            tags << 'security'
        }
        if (content.contains('integration') || content.contains('external') || content.contains('api')) {
            tags << 'integration'
        }
        if (content.contains('negative') || content.contains('fail') || content.contains('error')) {
            tags << 'negative'
        }
        if (content.contains('boundary') || content.contains('edge') || content.contains('limit')) {
            tags << 'boundary'
        }

        return tags
    }
}

/**
 * Enhanced natural language requirement parser with better NLP capabilities.
 */
@Slf4j
class EnhancedTestRequirementParser {

    List<Map<String, Object>> parseRequirements(String requirementsText) {
        log.info("Parsing natural language requirements")

        List<Map<String, Object>> requirements = []

        // Split by common requirement patterns with enhanced regex
        String[] requirementBlocks = requirementsText.split(/\n\s*(?=\d+\.|[-*]\s*(?:As a|I want|I need|Given that|When|Then|Feature:|Scenario:))/)

        for (String block : requirementBlocks) {
            block = block.trim()
            if (block.length() > 10) { // Filter out very short blocks
                Map<String, Object> requirement = parseRequirementBlock(block)
                if (requirement) {
                    requirements.add(requirement)
                }
            }
        }

        log.info("Parsed ${requirements.size()} requirements")
        return requirements
    }

    private Map parseRequirementBlock(String block) {
        def requirement = [:]

        // Enhanced requirement name extraction
        def namePatterns = [
            /^(?:As a|I want|I need|Given that)\s+(.+?)(?=\n|$)/,
            /^(?:Feature|Scenario):\s+(.+?)(?=\n|$)/,
            /^(\d+\.)\s+(.+?)(?=\n|$)/,
            /^([A-Z][^.!?]+?)(?=\n|$)/
        ]

        for (def pattern : namePatterns) {
            def matcher = block =~ pattern
            if (matcher.find()) {
                requirement.name = matcher.group(1).trim()
                break
            }
        }

        // Enhanced Given-When-Then extraction with better pattern matching
        requirement.given = extractClause(block, 'given', ['given that', 'given', 'setup', 'precondition'])
        requirement.when = extractClause(block, 'when', ['when', 'if', 'after', 'during'])
        requirement.then = extractClause(block, 'then', ['then', 'should', 'must', 'will', 'expect'])

        // Extract additional metadata
        requirement.description = extractDescription(block)
        requirement.priority = extractPriority(block)
        requirement.tags = extractRequirementTags(block)

        // Extract test data if present
        requirement.data = parseInlineData(block)

        return requirement
    }

    private String extractClause(String block, String clauseType, List<String> keywords) {
        def lowerBlock = block.toLowerCase()

        for (String keyword : keywords) {
            def pattern = "(?i)$keyword\\s+(?:that\\s+)?(.+?)(?=\\n|$)"
            def matcher = block =~ pattern

            if (matcher.find()) {
                def clause = matcher.group(1).trim()
                // Clean up the clause
                clause = clause.replaceAll(/^\s*[:\-]\s*/, '')
                return clause
            }
        }

        return null
    }

    private String extractDescription(String block) {
        // Extract descriptive text that isn't part of Given-When-Then
        def description = block

        // Remove structured elements
        description = description.replaceAll(/(?i)(?:given|when|then|as a|i want|i need|feature|scenario):\s*.+?(?=\n|$)/, '')

        // Clean up and return meaningful description
        description = description.replaceAll(/\s+/, ' ').trim()
        return description.length() > 10 ? description : null
    }

    private String extractPriority(String block) {
        def lowerBlock = block.toLowerCase()

        if (lowerBlock.contains('critical') || lowerBlock.contains('urgent') || lowerBlock.contains('high priority')) {
            return 'high'
        }
        if (lowerBlock.contains('medium') || lowerBlock.contains('normal')) {
            return 'medium'
        }
        if (lowerBlock.contains('low') || lowerBlock.contains('nice to have')) {
            return 'low'
        }

        return 'medium'
    }

    private List<String> extractRequirementTags(String block) {
        def tags = []
        def lowerBlock = block.toLowerCase()

        def tagPatterns = [
            'performance': ['performance', 'speed', 'fast', 'slow', 'timing'],
            'security': ['security', 'auth', 'permission', 'access', 'login'],
            'integration': ['integration', 'external', 'api', 'service', 'third party'],
            'negative': ['negative', 'fail', 'error', 'exception', 'invalid'],
            'boundary': ['boundary', 'edge', 'limit', 'maximum', 'minimum'],
            'smoke': ['smoke', 'basic', 'fundamental'],
            'regression': ['regression', 'existing', 'bug fix']
        ]

        tagPatterns.each { tag, keywords ->
            if (keywords.any { keyword -> lowerBlock.contains(keyword) }) {
                tags << tag
            }
        }

        return tags
    }

    private List parseInlineData(String block) {
        def data = []

        // Enhanced data table parsing
        def tablePattern = /(?s)(?:with|using)\s+(?:data|values?|parameters?|examples?)\s*:?\s*(.+?)(?=\n\s*\n|\n\s*(?:Feature|Scenario|Given|When|Then)|\Z)/
        def tableMatcher = block =~ tablePattern

        if (tableMatcher.find()) {
            def tableText = tableMatcher.group(1)

            // Parse markdown-style tables
            if (tableText.contains('|')) {
                data = parseMarkdownTable(tableText)
            }
            // Parse simple key-value pairs
            else if (tableText.contains('=')) {
                data = parseKeyValueData(tableText)
            }
        }

        return data
    }

    private List parseMarkdownTable(String tableText) {
        def data = []
        def lines = tableText.split('\n').findAll { it.trim() }

        if (lines.size() > 1) {
            def headers = lines[0].split('\\|').collect { it.trim() }

            lines[1..-1].each { line ->
                if (line.trim() && !line.contains('---')) {
                    def values = line.split('\\|').collect { it.trim() }
                    def dataRow = [:]
                    headers.eachWithIndex { header, index ->
                        dataRow[header] = values[index] ?: ''
                    }
                    data << dataRow
                }
            }
        }

        return data
    }

    private List parseKeyValueData(String dataText) {
        def data = []

        dataText.split('\n').each { line ->
            line = line.trim()
            if (line && !line.startsWith('//') && !line.startsWith('*')) {
                def parts = line.split('=', 2)
                if (parts.length == 2) {
                    data << [parameter: parts[0].trim(), value: parts[1].trim()]
                }
            }
        }

        return data
    }

    Map extractContext(String sourceCode) {
        log.info("Extracting context from source code")

        def context = [:]

        // Enhanced class information extraction using AST if available
        def classMatch = sourceCode =~ /class\s+(\w+)/
        if (classMatch.find()) {
            context.className = classMatch.group(1)
        }

        // Enhanced method signature extraction
        def methodMatches = sourceCode =~ /def\s+(\w+)\s*\(([^)]*)\)/
        context.methods = methodMatches.collect { [name: it[1], parameters: it[2]] }

        // Enhanced field information
        def fieldMatches = sourceCode =~ /(?:def|@.*)\s+(\w+)\s*(?:=|$)/
        context.fields = fieldMatches.collect { it[1] }

        // Extract annotations and modifiers
        def annotationMatches = sourceCode =~ /@(\w+)/
        context.annotations = annotationMatches.collect { it[1] }

        // Extract imports for dependency analysis
        def importMatches = sourceCode =~ /import\s+([^;]+)/
        context.imports = importMatches.collect { it[1] }

        log.info("Extracted context: ${context.keySet().join(', ')}")
        return context
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
                    def whenText = requirement.when.toString().toLowerCase()
                    whenText.contains(method.name.toLowerCase()) ||
                    method.parameters.toString().toLowerCase().split(',').any { param ->
                        whenText.contains(param.trim().toLowerCase())
                    }
                }
            }

            // Add dependency information
            if (context.imports) {
                requirement.dependencies = context.imports.findAll { importStmt ->
                    def reqText = (requirement.description ?: '') + ' ' + (requirement.name ?: '')
                    reqText.toLowerCase().contains(importStmt.toLowerCase().split('\\.').last())
                }
            }
        }

        return requirements
    }
}

/**
 * Enhanced Spock specification formatter with better test generation.
 */
@Slf4j
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

        if (scenario.data && !scenario.data.isEmpty()) {
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
@Slf4j
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
