package com.khulnasoft.spock.ai

/**
 * AI-Assisted Test Failure Reason Analyzer
 * Analyzes test failures and provides intelligent suggestions for fixes
 */
class TestFailureAnalyzer {

    private final FailurePatternMatcher patternMatcher
    private final FixSuggestionEngine suggestionEngine
    private final ContextAnalyzer contextAnalyzer

    TestFailureAnalyzer() {
        this.patternMatcher = new FailurePatternMatcher()
        this.suggestionEngine = new FixSuggestionEngine()
        this.contextAnalyzer = new ContextAnalyzer()
    }

    /**
     * Analyzes a test failure and provides detailed analysis and suggestions.
     */
    FailureAnalysis analyzeFailure(String testName, String failureMessage, String stackTrace, String testCode = null) {
        def analysis = new FailureAnalysis()

        analysis.testName = testName
        analysis.failureMessage = failureMessage
        analysis.stackTrace = stackTrace
        analysis.testCode = testCode

        // Identify failure patterns
        analysis.failurePatterns = patternMatcher.identifyPatterns(failureMessage, stackTrace)

        // Analyze context
        analysis.context = contextAnalyzer.analyzeContext(testCode, stackTrace)

        // Generate suggestions
        analysis.suggestions = suggestionEngine.generateSuggestions(analysis)

        // Calculate confidence
        analysis.confidence = calculateOverallConfidence(analysis)

        analysis
    }

    /**
     * Batch analyzes multiple test failures.
     */
    List<FailureAnalysis> analyzeFailures(List<FailureInfo> failures) {
        failures.collect { failure ->
            analyzeFailure(failure.testName, failure.message, failure.stackTrace, failure.testCode)
        }
    }

    private double calculateOverallConfidence(FailureAnalysis analysis) {
        def confidence = 0.0

        // Pattern match confidence
        confidence += analysis.failurePatterns.sum { it.confidence } * 0.4

        // Context analysis confidence
        confidence += analysis.context.confidence * 0.3

        // Suggestion quality confidence
        confidence += analysis.suggestions.sum { it.confidence } * 0.3

        Math.min(confidence, 1.0)
    }
}

/**
 * Identifies common failure patterns in test failures.
 */
class FailurePatternMatcher {

    enum FailurePattern {
        ASSERTION_ERROR("Assertion failed"),
        NULL_POINTER("NullPointerException"),
        TIMEOUT("Timeout exceeded"),
        RESOURCE_LEAK("Resource not cleaned up"),
        MOCK_SETUP("Mock setup issue"),
        TEST_ISOLATION("Test isolation problem"),
        DEPENDENCY_MISSING("Missing dependency"),
        CONFIGURATION_ERROR("Configuration issue"),
        NETWORK_ERROR("Network connectivity issue"),
        DATABASE_ERROR("Database connection issue")

        final String description

        FailurePattern(String description) {
            this.description = description
        }
    }

    List<PatternMatch> identifyPatterns(String failureMessage, String stackTrace) {
        def patterns = []
        def combinedText = (failureMessage + " " + stackTrace).toLowerCase()

        // Check for each pattern
        FailurePattern.values().each { pattern ->
            def confidence = calculatePatternConfidence(pattern, combinedText)
            if (confidence > 0.3) {
                patterns << new PatternMatch(
                    pattern: pattern,
                    confidence: confidence,
                    indicators: findPatternIndicators(pattern, combinedText)
                )
            }
        }

        patterns.sort { -it.confidence }
    }

    private double calculatePatternConfidence(FailurePattern pattern, String text) {
        def indicators = getPatternIndicators(pattern)
        def matches = 0

        indicators.each { indicator ->
            if (text.contains(indicator.toLowerCase())) {
                matches++
            }
        }

        return indicators.isEmpty() ? 0.0 : (matches / indicators.size())
    }

    private List<String> getPatternIndicators(FailurePattern pattern) {
        switch (pattern) {
            case FailurePattern.ASSERTION_ERROR:
                return ['assertionerror', 'assert', 'expected', 'but was', 'did not expect']
            case FailurePattern.NULL_POINTER:
                return ['nullpointerexception', 'null pointer', 'cannot invoke', 'because "this']
            case FailurePattern.TIMEOUT:
                return ['timeout', 'timed out', 'interrupted', 'wait timeout']
            case FailurePattern.RESOURCE_LEAK:
                return ['resource leak', 'connection leak', 'memory leak', 'file handle leak']
            case FailurePattern.MOCK_SETUP:
                return ['mock', 'stub', 'when', 'verify', 'argument mismatch']
            case FailurePattern.TEST_ISOLATION:
                return ['test isolation', 'cleanup', 'teardown', 'setup', 'state']
            case FailurePattern.DEPENDENCY_MISSING:
                return ['classnotfound', 'nosuchmethod', 'nosuchfield', 'missing dependency']
            case FailurePattern.CONFIGURATION_ERROR:
                return ['configuration', 'property', 'setting', 'environment', 'port']
            case FailurePattern.NETWORK_ERROR:
                return ['connection refused', 'network unreachable', 'socket', 'http host']
            case FailurePattern.DATABASE_ERROR:
                return ['sql', 'database', 'connection', 'jdbc', 'hibernate']
            default:
                return []
        }
    }

    private List<String> findPatternIndicators(FailurePattern pattern, String text) {
        getPatternIndicators(pattern).findAll { text.contains(it.toLowerCase()) }
    }
}

/**
 * Analyzes the context of test failures.
 */
class ContextAnalyzer {

    ContextInfo analyzeContext(String testCode, String stackTrace) {
        def context = new ContextInfo()

        // Analyze test structure
        context.testStructure = analyzeTestStructure(testCode)
        context.stackTraceLocation = analyzeStackTraceLocation(stackTrace)
        context.dependencies = extractDependencies(testCode)
        context.confidence = calculateContextConfidence(testCode, stackTrace)

        context
    }

    private Map analyzeTestStructure(String testCode) {
        def structure = [:]

        if (testCode) {
            structure.hasSetup = testCode.contains('setup:') || testCode.contains('given:')
            structure.hasCleanup = testCode.contains('cleanup:') || testCode.contains('and:')
            structure.hasWhen = testCode.contains('when:')
            structure.hasThen = testCode.contains('then:')
            structure.hasWhere = testCode.contains('where:')
            structure.assertionCount = testCode.findAll(/\bassert\b|\bthen:/).size()
        }

        structure
    }

    private Map analyzeStackTraceLocation(String stackTrace) {
        def location = [:]

        def lines = stackTrace.split('\n')
        def testLine = lines.find { it.contains('Spec') || it.contains('Test') }

        if (testLine) {
            def match = testLine =~ /at\s+(\w+)\.(\w+)\(/
            if (match.find()) {
                location.className = match.group(1)
                location.methodName = match.group(2)
            }
        }

        location
    }

    private List<String> extractDependencies(String testCode) {
        def dependencies = []

        if (testCode) {
            if (testCode.contains('Mock(') || testCode.contains('Stub(')) dependencies << 'mocking'
            if (testCode.contains('database') || testCode.contains('jdbc')) dependencies << 'database'
            if (testCode.contains('http') || testCode.contains('rest')) dependencies << 'http'
            if (testCode.contains('file') || testCode.contains('File')) dependencies << 'filesystem'
        }

        dependencies
    }

    private double calculateContextConfidence(String testCode, String stackTrace) {
        def confidence = 0.0

        if (testCode) confidence += 0.4
        if (stackTrace) confidence += 0.3

        // Check for detailed stack trace
        if (stackTrace && stackTrace.contains('at ')) confidence += 0.3

        Math.min(confidence, 1.0)
    }
}

/**
 * Generates fix suggestions for test failures.
 */
class FixSuggestionEngine {

    List<FixSuggestion> generateSuggestions(FailureAnalysis analysis) {
        def suggestions = []

        // Generate suggestions based on failure patterns
        analysis.failurePatterns.each { pattern ->
            def patternSuggestions = generatePatternSuggestions(pattern, analysis)
            suggestions.addAll(patternSuggestions)
        }

        // Generate context-based suggestions
        def contextSuggestions = generateContextSuggestions(analysis)
        suggestions.addAll(contextSuggestions)

        // Deduplicate and prioritize suggestions
        suggestions.unique { it.description }
        suggestions.sort { -it.confidence }

        suggestions.take(5) // Return top 5 suggestions
    }

    private List<FixSuggestion> generatePatternSuggestions(PatternMatch pattern, FailureAnalysis analysis) {
        def suggestions = []

        switch (pattern.pattern) {
            case FailurePatternMatcher.FailurePattern.ASSERTION_ERROR:
                suggestions << new FixSuggestion(
                    type: 'assertion_fix',
                    description: 'Review assertion logic and expected vs actual values',
                    confidence: 0.8,
                    actions: [
                        'Check if expected value is correct',
                        'Verify assertion method is appropriate',
                        'Review test data setup'
                    ]
                )
                break

            case FailurePatternMatcher.FailurePattern.NULL_POINTER:
                suggestions << new FixSuggestion(
                    type: 'null_check',
                    description: 'Add null checks or initialize required objects',
                    confidence: 0.9,
                    actions: [
                        'Initialize object before use',
                        'Add null checks in test setup',
                        'Review mock configuration'
                    ]
                )
                break

            case FailurePatternMatcher.FailurePattern.TIMEOUT:
                suggestions << new FixSuggestion(
                    type: 'timeout_fix',
                    description: 'Increase timeout or fix async operation',
                    confidence: 0.7,
                    actions: [
                        'Increase timeout value',
                        'Check for blocking operations',
                        'Review async code implementation'
                    ]
                )
                break

            case FailurePatternMatcher.FailurePattern.MOCK_SETUP:
                suggestions << new FixSuggestion(
                    type: 'mock_fix',
                    description: 'Fix mock or stub configuration',
                    confidence: 0.8,
                    actions: [
                        'Review mock setup in given block',
                        'Check method call expectations',
                        'Verify stub return values'
                    ]
                )
                break
        }

        suggestions
    }

    private List<FixSuggestion> generateContextSuggestions(FailureAnalysis analysis) {
        def suggestions = []

        // Missing setup suggestion
        if (!analysis.context.testStructure.hasSetup && analysis.context.dependencies.contains('mocking')) {
            suggestions << new FixSuggestion(
                type: 'missing_setup',
                description: 'Add proper test setup for mocking',
                confidence: 0.7,
                actions: [
                    'Add setup block for mock initialization',
                    'Configure mock behaviors before test execution'
                ]
            )
        }

        // Missing cleanup suggestion
        if (!analysis.context.testStructure.hasCleanup && analysis.context.dependencies.contains('resource')) {
            suggestions << new FixSuggestion(
                type: 'missing_cleanup',
                description: 'Add cleanup block for resource management',
                confidence: 0.6,
                actions: [
                    'Add cleanup block to release resources',
                    'Close connections and streams'
                ]
            )
        }

        suggestions
    }
}

/**
 * Represents a pattern match result.
 */
class PatternMatch {
    FailurePatternMatcher.FailurePattern pattern
    double confidence
    List<String> indicators
}

/**
 * Represents a fix suggestion.
 */
class FixSuggestion {
    String type
    String description
    double confidence
    List<String> actions

    String toString() {
        "FixSuggestion{type='$type', description='$description', confidence=${String.format('%.2f', confidence)}}"
    }
}

/**
 * Represents the context information of a test failure.
 */
class ContextInfo {
    Map testStructure
    Map stackTraceLocation
    List<String> dependencies
    double confidence
}

/**
 * Represents a complete failure analysis.
 */
class FailureAnalysis {
    String testName
    String failureMessage
    String stackTrace
    String testCode
    List<PatternMatch> failurePatterns
    ContextInfo context
    List<FixSuggestion> suggestions
    double confidence

    String getSummary() {
        def summary = new StringBuilder()
        summary.append("Test: $testName\n")
        summary.append("Failure: $failureMessage\n")
        summary.append("Patterns: ${failurePatterns.collect { it.pattern }.join(', ')}\n")
        summary.append("Top Suggestion: ${suggestions.first()?.description ?: 'No suggestions available'}\n")
        summary.append("Confidence: ${String.format('%.1f%%', confidence * 100)}\n")
        summary
    }
}

/**
 * Information about a test failure.
 */
class FailureInfo {
    String testName
    String message
    String stackTrace
    String testCode
}

/**
 * Command-line interface for test failure analysis.
 */
class FailureAnalyzerCli {

    static void main(String[] args) {
        if (args.length < 3) {
            println "Usage: FailureAnalyzerCli <test-name> <failure-message> <stack-trace> [test-code-file]"
            return
        }

        def testName = args[0]
        def failureMessage = args[1]
        def stackTrace = args[2]
        def testCodeFile = args.length > 3 ? new File(args[3]) : null
        def testCode = testCodeFile?.exists() ? testCodeFile.text : null

        def analyzer = new TestFailureAnalyzer()
        def analysis = analyzer.analyzeFailure(testName, failureMessage, stackTrace, testCode)

        println "Test Failure Analysis Report"
        println "=" * 50
        println analysis.summary

        println "\nDetailed Analysis:"
        println "-" * 30
        println "Failure Patterns:"
        analysis.failurePatterns.each { pattern ->
            println "  ${pattern.pattern.description} (${String.format('%.1f%%', pattern.confidence * 100)})"
        }

        println "\nSuggestions:"
        analysis.suggestions.eachWithIndex { suggestion, index ->
            println "${index + 1}. ${suggestion.description} (${String.format('%.1f%%', suggestion.confidence * 100)})"
            suggestion.actions.each { action ->
                println "   - $action"
            }
        }

        if (analysis.confidence < 0.5) {
            println "\n⚠️  Low confidence analysis - manual review recommended"
        }
    }
}
