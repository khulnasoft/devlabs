package com.khulnasoft.spock.ai;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

package com.khulnasoft.spock.ai

import groovy.util.logging.Slf4j

/**
 * AI-powered test generation framework for Spock with AST-based analysis.
 * Generates Spock test specifications from natural language descriptions using advanced code understanding.
 */
@Slf4j
class AiTestGenerator {

    private final AstSourceAnalyzer analyzer
    private final TestTemplateEngine templateEngine

    AiTestGenerator() {
        this.analyzer = new AstSourceAnalyzer()
        this.templateEngine = new TestTemplateEngine()
    }

    /**
     * Generates Spock test specifications from natural language descriptions with AST analysis.
     *
     * @param sourceCode   The source code to generate tests for
     * @param requirements Natural language test requirements
     * @return Generated Spock test specification
     */
    String generateSpockTests(String sourceCode, String requirements) {
        log.info("Generating Spock tests with AST-based analysis")

        // Analyze the source code using AST for comprehensive understanding
        SourceCodeAnalysis analysis = analyzer.analyzeSourceCode(sourceCode)

        // Parse requirements into test scenarios with enhanced NLP
        List<Scenario> scenarios = parseRequirements(requirements, analysis)

        // Generate test specification with intelligent mapping
        return templateEngine.generateTestSpecification(analysis, scenarios)
    }

    /**
     * Generates test data for parameterized tests with intelligent type inference.
     *
     * @param methodSignature  Method signature to generate data for
     * @param dataRequirements Description of test data needed
     * @return Generated where-block data
     */
    String generateTestData(String methodSignature, String dataRequirements) {
        MethodInfo methodInfo = analyzer.analyzeMethod(methodSignature)
        List<DataScenario> dataScenarios = parseDataRequirements(dataRequirements)

        return templateEngine.generateWhereBlock(methodInfo, dataScenarios)
    }

    private List<Scenario> parseRequirements(String requirements, SourceCodeAnalysis analysis) {
        List<Scenario> scenarios = []

        // Enhanced requirement parsing with context awareness
        def requirementBlocks = requirements.split(/\n\s*(?=\d+\.|[-*]\s*(?:As a|I want|I need|Given that|When|Then|Feature:|Scenario:))/)

        requirementBlocks.each { block ->
            block = block.trim()
            if (block.length() > 10) {
                def scenario = createScenarioFromRequirement(block, analysis)
                if (scenario) {
                    scenarios << scenario
                }
            }
        }

        return scenarios
    }

    private Scenario createScenarioFromRequirement(String block, SourceCodeAnalysis analysis) {
        def scenario = new Scenario()

        // Enhanced name extraction
        def namePatterns = [
            /^(?:As a|I want|I need|Given that)\s+(.+?)(?=\n|$)/,
            /^(?:Feature|Scenario):\s+(.+?)(?=\n|$)/,
            /^(\d+\.)\s+(.+?)(?=\n|$)/,
            /^([A-Z][^.!?]+?)(?=\n|$)/
        ]

        for (def pattern : namePatterns) {
            def matcher = block =~ pattern
            if (matcher.find()) {
                scenario.name = matcher.group(1).trim()
                break
            }
        }

        // Enhanced clause extraction with context awareness
        scenario.given = extractClause(block, 'given', ['given that', 'given', 'setup', 'precondition'])
        scenario.when = extractClause(block, 'when', ['when', 'if', 'after', 'during'])
        scenario.then = extractClause(block, 'then', ['then', 'should', 'must', 'will', 'expect'])

        // Intelligent method mapping based on AST analysis
        if (scenario.when && analysis.methods) {
            scenario.targetMethod = findBestMatchingMethod(scenario.when, analysis.methods)
        }

        // Enhanced assertion generation based on method return types
        if (scenario.then && scenario.targetMethod) {
            scenario.expectedResult = inferExpectedResult(scenario.then, scenario.targetMethod)
        }

        return scenario
    }

    private String extractClause(String block, String clauseType, List<String> keywords) {
        def lowerBlock = block.toLowerCase()

        for (String keyword : keywords) {
            def pattern = "(?i)$keyword\\s+(?:that\\s+)?(.+?)(?=\\n|$)"
            def matcher = block =~ pattern

            if (matcher.find()) {
                def clause = matcher.group(1).trim()
                clause = clause.replaceAll(/^\s*[:\-]\s*/, '')
                return clause
            }
        }

        return null
    }

    private MethodInfo findBestMatchingMethod(String whenClause, List<MethodInfo> methods) {
        def lowerWhen = whenClause.toLowerCase()

        // Score methods based on name similarity and parameter relevance
        def scoredMethods = methods.collect { method ->
            int score = 0

            // Name similarity
            if (lowerWhen.contains(method.name.toLowerCase())) {
                score += 10
            }

            // Parameter relevance
            method.parameters.each { param ->
                if (lowerWhen.contains(param.name.toLowerCase()) ||
                    lowerWhen.contains(param.type.toLowerCase())) {
                    score += 5
                }
            }

            return [method: method, score: score]
        }

        // Return highest scoring method
        def bestMatch = scoredMethods.max { it.score }
        return bestMatch?.score > 0 ? bestMatch.method : methods.first()
    }

    private String inferExpectedResult(String thenClause, MethodInfo method) {
        def lowerThen = thenClause.toLowerCase()

        // Simple type-based inference
        switch (method.returnType.toLowerCase()) {
            case 'boolean':
                if (lowerThen.contains('true') || lowerThen.contains('success')) return 'true'
                if (lowerThen.contains('false') || lowerThen.contains('fail')) return 'false'
                break
            case ~/^(int|long|double|float)$/:
                if (lowerThen =~ /\d+/) {
                    def numberMatch = lowerThen =~ /(\d+)/
                    if (numberMatch.find()) return numberMatch.group(1)
                }
                break
            case 'string':
                if (lowerThen.contains('message') || lowerThen.contains('text')) {
                    return '"expected message"'
                }
                break
        }

        return 'expectedResult'
    }

    private List<DataScenario> parseDataRequirements(String dataRequirements) {
        List<DataScenario> dataScenarios = []

        for (String line : dataRequirements.split("\n")) {
            line = line.trim()
            if (line && !line.startsWith("//") && !line.startsWith("*")) {
                dataScenarios.add(parseDataLine(line))
            }
        }

        return dataScenarios
    }

    private DataScenario parseDataLine(String line) {
        String[] parts = line.split("\\|")
        return new DataScenario(Arrays.stream(parts).map(String::trim).toArray(String[]::new))
    }
}

/**
 * Enhanced scenario representation with intelligent method mapping.
 */
@Slf4j
class Scenario {
    String name
    String given
    String when
    String then
    MethodInfo targetMethod
    String expectedResult
    List<String> tags = []

    String toString() {
        "Scenario{name='$name', method=${targetMethod?.name ?: 'unknown'}}"
    }
}

/**
 * Enhanced data scenario with type inference.
 */
@Slf4j
class DataScenario {
    String[] inputs
    String expected

    DataScenario(String[] values) {
        if (values.length > 0) {
            this.inputs = Arrays.copyOf(values, values.length - 1)
            this.expected = values[values.length - 1]
        } else {
            this.inputs = []
            this.expected = 'null'
        }
    }

    String[] getInputs() {
        return inputs ?: []
    }

    String getExpected() {
        return expected ?: 'null'
    }
}

}

/**
 * Enhanced template engine for generating Spock test specifications with intelligent method mapping.
 */
@Slf4j
class TestTemplateEngine {

    String generateTestSpecification(SourceCodeAnalysis analysis, List<Scenario> scenarios) {
        StringBuilder spec = new StringBuilder()

        spec.append("import spock.lang.Specification\n")
        spec.append("import spock.lang.Subject\n")
        spec.append("import spock.lang.Unroll\n")

        // Add necessary imports based on analysis
        if (analysis.imports) {
            analysis.imports.each { importStmt ->
                if (!importStmt.isStatic && !importStmt.isStar) {
                    spec.append("import ${importStmt.className}\n")
                }
            }
        }

        spec.append("\n")
        spec.append("class ").append(analysis.className).append("Spec extends Specification {\n\n")

        // Generate setup method if class has dependencies
        if (analysis.fields.any { it.hasInitializer }) {
            spec.append(generateSetupMethod(analysis))
        }

        // Generate test methods based on scenarios
        scenarios.eachWithIndex { scenario, index ->
            String methodName = generateMethodName(scenario, index)
            spec.append(generateTestMethod(methodName, scenario, analysis))
        }

        spec.append("}\n")
        return spec.toString()
    }

    private String generateSetupMethod(SourceCodeAnalysis analysis) {
        def setup = """
    def setup() {
        // Initialize subject under test
        subject = new ${analysis.className}()

        // Initialize fields with dependencies
"""

        analysis.fields.findAll { it.hasInitializer }.each { field ->
            setup += "        subject.${field.name} = ${generateFieldInitialization(field)}\n"
        }

        setup += "    }\n\n"
        return setup
    }

    private String generateFieldInitialization(FieldInfo field) {
        switch (field.type.toLowerCase()) {
            case 'string':
                return '""'
            case ~/^(int|integer)$/:
                return '0'
            case ~/^(boolean|bool)$/:
                return 'false'
            case ~/^(list|arraylist)$/:
                return '[]'
            case ~/^(map|hashmap)$/:
                return '[:]'
            default:
                return "new ${field.type}()"
        }
    }

    private String generateMethodName(Scenario scenario, int index) {
        String baseName = scenario.name ?: scenario.when ?: "test_scenario_${index + 1}"
        baseName = baseName.replaceAll("[^a-zA-Z0-9]", "_")
        baseName = baseName.replaceAll("_+", "_")
        baseName = baseName.toLowerCase()
        baseName = baseName.replaceAll(/^_/, '')

        return baseName.take(50)
    }

    private String generateTestMethod(String methodName, Scenario scenario, SourceCodeAnalysis analysis) {
        def method = """
    def "${methodName}"() {
"""

        if (scenario.given) {
            method += """        given: "${scenario.given}"
        // Setup code based on given clause

"""
        }

        if (scenario.when) {
            method += """        when: "${scenario.when}"
"""
            if (scenario.targetMethod) {
                def methodCall = generateMethodCall(scenario.targetMethod)
                method += """        def result = subject.${methodCall}

"""
            } else {
                method += """        def result = subject.someMethod()  // Method mapping not determined

"""
            }
        }

        if (scenario.then) {
            method += """        then: "${scenario.then}"
"""
            if (scenario.expectedResult) {
                method += """        result == ${scenario.expectedResult}
"""
            } else {
                method += """        // Assertion would be generated based on expected result
        true  // Placeholder assertion

"""
            }
        }

        method += "    }\n\n"
        return method
    }

    private String generateMethodCall(MethodInfo method) {
        def paramList = method.parameters.collect { param ->
            // Generate appropriate parameter values based on type
            switch (param.type.toLowerCase()) {
                case 'string':
                    return '""'
                case ~/^(int|integer)$/:
                    return '0'
                case ~/^(boolean|bool)$/:
                    return 'false'
                default:
                    return "new ${param.type}()"
            }
        }

        return "${method.name}(${paramList.join(', ')})"
    }

    String generateWhereBlock(MethodInfo methodInfo, List<DataScenario> dataScenarios) {
        StringBuilder whereBlock = new StringBuilder()
        whereBlock.append("    where:\n\n")

        if (!dataScenarios.isEmpty()) {
            // Generate headers based on method parameters
            def headers = methodInfo.parameters.collect { it.name } + ['expected']
            whereBlock.append("        ").append(headers.join(' | ')).append("\n")
            whereBlock.append("        ").append(headers.collect { '-' * it.length() }.join('-|-')).append("\n")

            dataScenarios.each { scenario ->
                def values = scenario.inputs + [scenario.expected]
                whereBlock.append("        ").append(values.join(' | ')).append("\n")
            }
        } else {
            whereBlock.append("        // No data scenarios defined\n")
        }

        return whereBlock.toString()
    }
}
