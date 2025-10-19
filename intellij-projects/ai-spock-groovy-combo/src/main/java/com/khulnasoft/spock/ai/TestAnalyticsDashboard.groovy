package com.khulnasoft.spock.ai

import com.fasterxml.jackson.databind.ObjectMapper
import groovy.util.logging.Slf4j
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.io.File

/**
 * Test Analytics Dashboard Generator
 *
 * This class provides comprehensive test analytics and reporting capabilities for test execution results.
 * It supports multiple input formats (JUnit XML, Spock HTML, JSON) and generates various output formats
 * including JSON, HTML, and CSV reports.
 *
 * Key Features:
 * - Parses test results from multiple sources and formats
 * - Calculates comprehensive analytics including success rates, performance metrics, and failure analysis
 * - Generates detailed reports with recommendations
 * - Supports export in multiple formats for different use cases
 * - Provides both programmatic API and command-line interface
 *
 * Usage:
 *   def dashboard = new TestAnalyticsDashboard()
 *   def analytics = dashboard.generateAnalytics("/path/to/test-results")
 *   dashboard.exportAnalytics(analytics, "/path/to/output", ["json", "html"])
 *
 * Command Line Usage:
 *   groovy TestAnalyticsDashboard.groovy /path/to/test-results /path/to/output json html
 *
 * @author Test Analytics System
 * @version 1.0.0
 * @since 1.0.0
 */
@Slf4j
class TestAnalyticsDashboard {

    // Constants
    private static final String DEFAULT_OUTPUT_DIR = 'build/reports'
    private static final List<String> DEFAULT_FORMATS = ['json', 'html']
    private static final String JUNIT_FILE_PATTERN = '**/*TEST-*.xml'
    private static final String SPOCK_HTML_FILE_PATTERN = '**/*test-results.html'
    private static final String JSON_FILE_PATTERN = '**/*.json'
    private static final String TIMESTAMP_PATTERN = 'yyyy-MM-dd-HH-mm-ss'
    private static final String VERSION = '1.0.0'

    // Test type constants
    private static final String TEST_TYPE_INTEGRATION = 'INTEGRATION'
    private static final String TEST_TYPE_FUNCTIONAL = 'FUNCTIONAL'
    private static final String TEST_TYPE_PERFORMANCE = 'PERFORMANCE'
    private static final String TEST_TYPE_UNIT = 'UNIT'

    // Test category constants
    private static final String TEST_CATEGORY_NEGATIVE = 'NEGATIVE'
    private static final String TEST_CATEGORY_EDGE_CASE = 'EDGE_CASE'
    private static final String TEST_CATEGORY_PERFORMANCE = 'PERFORMANCE'
    private static final String TEST_CATEGORY_POSITIVE = 'POSITIVE'

    // Thresholds
    private static final double HIGH_SUCCESS_RATE_THRESHOLD = 80.0
    private static final double MEDIUM_SUCCESS_RATE_THRESHOLD = 60.0
    private static final double HIGH_FAILURE_RATE_THRESHOLD = 50.0
    private static final double SLOW_TEST_THRESHOLD_SECONDS = 30.0
    private static final double LOW_SUCCESS_RATE_THRESHOLD = 80.0
    private static final double INTEGRATION_TEST_SUCCESS_RATE_THRESHOLD = 70.0

    private final TestResultParser resultParser
    private final AnalyticsCalculator calculator
    private final ReportGenerator reportGenerator

    TestAnalyticsDashboard() {
        this.resultParser = new TestResultParser()
        this.calculator = new AnalyticsCalculator()
        this.reportGenerator = new ReportGenerator()
    }

    /**
     * Generates comprehensive test analytics from test results.
     */
    Map<String, Object> generateAnalytics(String testResultsDir, Map<String, Object> options = [:]) {
        log.info("Generating test analytics from: ${testResultsDir}")

        // Parse test results
        List<TestResult> testResults = resultParser.parseTestResults(testResultsDir)

        // Validate test results
        if (testResults == null) {
            testResults = []
            log.warn("No test results found in directory: ${testResultsDir}")
        }

        // Calculate analytics
        Map<String, Object> analytics = calculator.calculateAnalytics(testResults, options)

        // Generate reports
        Map<String, Object> reports = reportGenerator.generateReports(analytics, options)

        Map<String, Object> metadata = [
            generatedAt: LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME),
            version: VERSION,
            options: options
        ]

        Map<String, Object> result = [
            analytics: analytics,
            reports: reports,
            metadata: metadata
        ]

        log.info("Generated analytics for ${testResults.size()} test results")
        return result
    }

    /**
     * Exports analytics data in various formats.
     */
    void exportAnalytics(Map<String, Object> analyticsData, String outputDir, List<String> formats = DEFAULT_FORMATS) {
        formats.each { format ->
            String fileName = "test-analytics-${LocalDateTime.now().format(DateTimeFormatter.ofPattern(TIMESTAMP_PATTERN))}.${format}"
            File outputFile = new File(outputDir, fileName)

            switch (format) {
                case "json":
                    exportJson(analyticsData, outputFile)
                    break
                case "html":
                    exportHtml(analyticsData, outputFile)
                    break
                case "csv":
                    exportCsv(analyticsData, outputFile)
                    break
            }
        }

        log.info("Exported analytics to ${outputDir} in formats: ${formats.join(', ')}")
    }

    private void exportJson(Map<String, Object> data, File file) {
        if (data == null) {
            log.warn("Cannot export JSON: data is null")
            return
        }

        try {
            ObjectMapper mapper = new ObjectMapper()
            mapper.writeValue(file, data)
        } catch (Exception e) {
            log.error("Failed to export JSON analytics", e)
        }
    }

    private void exportHtml(Map<String, Object> data, File file) {
        if (data == null) {
            log.warn("Cannot export HTML: data is null")
            return
        }

        String html = generateHtmlReport(data)
        if (html == null) {
            log.warn("Cannot export HTML: generated report is null")
            return
        }

        try {
            file.withWriter { writer ->
                writer.write(html)
            }
        } catch (Exception e) {
            log.error("Failed to export HTML analytics", e)
        }
    }

    private void exportCsv(Map<String, Object> data, File file) {
        if (data == null) {
            log.warn("Cannot export CSV: data is null")
            return
        }

        String csv = generateCsvReport(data)
        if (csv == null) {
            log.warn("Cannot export CSV: generated report is null")
            return
        }

        try {
            file.withWriter { writer ->
                writer.write(csv)
            }
        } catch (Exception e) {
            log.error("Failed to export CSV analytics", e)
        }
    }

    private String generateHtmlReport(Map<String, Object> data) {
        if (data == null) {
            return generateErrorHtml("No data available for HTML report")
        }

        Map<String, Object> analytics = data.analytics
        if (analytics == null) {
            return generateErrorHtml("No analytics data available")
        }

        StringBuilder html = new StringBuilder()
        html.append(generateHtmlHeader())
        html.append(generateHtmlBody(data, analytics))
        html.append("</html>\n")

        return html.toString()
    }

    private String generateHtmlHeader() {
        StringBuilder html = new StringBuilder()
        html.append("<!DOCTYPE html>\n")
        html.append("<html>\n")
        html.append("<head>\n")
        html.append("    <title>Test Analytics Dashboard</title>\n")
        html.append("    <style>\n")
        html.append("        body { font-family: Arial, sans-serif; margin: 20px; }\n")
        html.append("        .metric { background: #f5f5f5; padding: 10px; margin: 10px 0; border-radius: 5px; }\n")
        html.append("        .chart { margin: 20px 0; }\n")
        html.append("        .summary { background: #e8f4fd; padding: 15px; border-radius: 5px; }\n")
        html.append("        table { border-collapse: collapse; width: 100%; }\n")
        html.append("        th, td { border: 1px solid #ddd; padding: 8px; text-align: left; }\n")
        html.append("        th { background-color: #f2f2f2; }\n")
        html.append("        .high { color: #d32f2f; }\n")
        html.append("        .medium { color: #f57c00; }\n")
        html.append("        .low { color: #388e3c; }\n")
        html.append("    </style>\n")
        html.append("</head>\n")
        html.append("<body>\n")
        html.append("    <h1>Test Analytics Dashboard</h1>\n")
        return html.toString()
    }

    private String generateHtmlBody(Map<String, Object> data, Map<String, Object> analytics) {
        StringBuilder html = new StringBuilder()
        html.append("    <p>Generated: ${data.metadata?.generatedAt ?: 'Unknown'}</p>\n")
        html.append(generateSummarySection(analytics))
        html.append(generateTypeSection(analytics))
        html.append(generateCategorySection(analytics))
        html.append(generateFailingTestsSection(analytics))
        html.append(generatePerformanceSection(analytics))
        html.append("</body>\n")
        return html.toString()
    }

    private String generateSummarySection(Map<String, Object> analytics) {
        StringBuilder html = new StringBuilder()
        html.append("    <div class=\"summary\">\n")
        html.append("        <h2>Summary</h2>\n")
        html.append("        <div class=\"metric\">\n")
        html.append("            <strong>Total Tests:</strong> ${analytics.summary?.totalTests ?: 0}\n")
        html.append("        </div>\n")
        html.append("        <div class=\"metric\">\n")
        html.append("            <strong>Success Rate:</strong> ${analytics.summary?.successRate ? String.format('%.1f%%', analytics.summary.successRate) : '0.0%'}\n")
        html.append("        </div>\n")
        html.append("        <div class=\"metric\">\n")
        html.append("            <strong>Total Duration:</strong> ${analytics.summary?.totalDuration ? String.format('%.2f', analytics.summary.totalDuration) : '0.00'}s\n")
        html.append("        </div>\n")
        html.append("    </div>\n")
        return html.toString()
    }

    private String generateTypeSection(Map<String, Object> analytics) {
        StringBuilder html = new StringBuilder()
        html.append("    <h2>Test Results by Type</h2>\n")
        html.append("    <table>\n")
        html.append("        <tr><th>Type</th><th>Count</th><th>Success Rate</th><th>Avg Duration</th></tr>\n")
        html.append("        ${generateTableRows(analytics.byType)}\n")
        html.append("    </table>\n")
        return html.toString()
    }

    private String generateCategorySection(Map<String, Object> analytics) {
        StringBuilder html = new StringBuilder()
        html.append("    <h2>Test Results by Category</h2>\n")
        html.append("    <table>\n")
        html.append("        <tr><th>Category</th><th>Count</th><th>Success Rate</th><th>Avg Duration</th></tr>\n")
        html.append("        ${generateTableRows(analytics.byCategory)}\n")
        html.append("    </table>\n")
        return html.toString()
    }

    private String generateFailingTestsSection(Map<String, Object> analytics) {
        StringBuilder html = new StringBuilder()
        html.append("    <h2>Top Failing Tests</h2>\n")
        html.append("    <table>\n")
        html.append("        <tr><th>Test Name</th><th>Failure Rate</th><th>Last Failure</th></tr>\n")
        html.append("        ${generateFailingTestsRows(analytics.topFailing)}\n")
        html.append("    </table>\n")
        return html.toString()
    }

    private String generatePerformanceSection(Map<String, Object> analytics) {
        StringBuilder html = new StringBuilder()
        html.append("    <h2>Performance Analysis</h2>\n")
        html.append("    <div class=\"metric\">\n")
        html.append("        <strong>Slowest Tests:</strong> ${analytics.performance?.slowestTests?.join(', ') ?: 'None'}\n")
        html.append("    </div>\n")
        html.append("    <div class=\"metric\">\n")
        html.append("        <strong>Fastest Tests:</strong> ${analytics.performance?.fastestTests?.join(', ') ?: 'None'}\n")
        html.append("    </div>\n")
        return html.toString()
    }

    private String generateErrorHtml(String errorMessage) {
        StringBuilder html = new StringBuilder()
        html.append("<!DOCTYPE html>\n")
        html.append("<html>\n")
        html.append("<head>\n")
        html.append("    <title>Test Analytics Dashboard - Error</title>\n")
        html.append("    <style>\n")
        html.append("        body { font-family: Arial, sans-serif; margin: 20px; }\n")
        html.append("        .error { background: #ffebee; padding: 15px; border-radius: 5px; color: #c62828; }\n")
        html.append("    </style>\n")
        html.append("</head>\n")
        html.append("<body>\n")
        html.append("    <h1>Test Analytics Dashboard</h1>\n")
        html.append("    <div class=\"error\">\n")
        html.append("        <h2>Error</h2>\n")
        html.append("        <p>${errorMessage}</p>\n")
        html.append("    </div>\n")
        html.append("</body>\n")
        html.append("</html>\n")
        return html.toString()
    }

    private String generateTableRows(Map<String, Object> data) {
        if (!data) return "<tr><td colspan=\"4\">No data available</td></tr>"

        StringBuilder rows = new StringBuilder()
        data.each { key, value ->
            def successRate = value.successRate ?: 0.0
            def avgDuration = value.avgDuration ?: 0.0
            def cssClass = successRate > HIGH_SUCCESS_RATE_THRESHOLD ? 'high' : successRate > MEDIUM_SUCCESS_RATE_THRESHOLD ? 'medium' : 'low'

            rows.append("<tr><td>${key}</td><td>${value.count}</td><td class=\"${cssClass}\">${String.format('%.1f%%', successRate)}</td><td>${String.format('%.2f', avgDuration)}s</td></tr>\n        ")
        }
        return rows.toString()
    }

    private String generateFailingTestsRows(List<Map<String, Object>> failingTests) {
        if (!failingTests) return "<tr><td colspan=\"3\">No failing tests</td></tr>"

        StringBuilder rows = new StringBuilder()
        failingTests.each { test ->
            rows.append("<tr><td>${test.name}</td><td>${String.format('%.1f%%', test.failureRate)}</td><td>${test.lastFailure}</td></tr>\n        ")
        }
        return rows.toString()
    }

    private String generateCsvReport(Map data) {
        if (data == null) {
            return 'Metric,Value\nError,No data available\n'
        }

        def analytics = data.analytics
        if (analytics == null) {
            return 'Metric,Value\nError,No analytics data available\n'
        }

        def csv = new StringBuilder()

        csv.append('Metric,Value\n')
        csv.append("Total Tests,${analytics.summary?.totalTests ?: 0}\n")
        csv.append("Success Rate,${analytics.summary?.successRate ? String.format('%.1f%%', analytics.summary.successRate) : '0.0%'}\n")
        csv.append("Total Duration,${analytics.summary?.totalDuration ? String.format('%.2f', analytics.summary.totalDuration) : '0.00'}\n")

        csv.append('\nType,Count,Success Rate,Avg Duration\n')
        if (analytics.byType) {
            analytics.byType.each { type, data ->
                csv.append("${type},${data.count ?: 0},${data.successRate ? String.format('%.1f%%', data.successRate) : '0.0%'},${data.avgDuration ? String.format('%.2f', data.avgDuration) : '0.00'}\n")
            }
        }

        csv.toString()
    }
}

/**
 * Parses test results from various formats.
 *
 * This class handles the parsing of test execution results from multiple sources:
 * - JUnit XML reports (standard format used by most Java testing frameworks)
 * - Spock HTML reports (generated by Spock framework)
 * - JSON reports (custom format for structured test data)
 *
 * The parser uses robust error handling and validation to ensure reliable processing
 * of test data from different sources and formats.
 *
 * @author Test Analytics System
 * @since 1.0.0
 */
@Slf4j
class TestResultParser {

    List<TestResult> parseTestResults(String resultsDir) {
        def results = []

        // Parse JUnit XML reports
        def junitFiles = findFiles(resultsDir, JUNIT_FILE_PATTERN)
        junitFiles.each { file ->
            results.addAll(parseJUnitFile(file))
        }

        // Parse Spock HTML reports if available
        def htmlFiles = findFiles(resultsDir, SPOCK_HTML_FILE_PATTERN)
        htmlFiles.each { file ->
            results.addAll(parseSpockHtmlFile(file))
        }

        // Parse JSON reports if available
        def jsonFiles = findFiles(resultsDir, JSON_FILE_PATTERN)
        jsonFiles.each { file ->
            results.addAll(parseJsonFile(file))
        }

        log.info("Parsed ${results.size()} test results from ${resultsDir}")
        results
    }

    private List<TestResult> parseJUnitFile(File file) {
        def results = []

        if (file == null || !file.exists() || !file.canRead()) {
            log.warn("Cannot parse JUnit file: file is null, doesn't exist, or cannot be read")
            return results
        }

        try {
            def xml = new XmlSlurper().parse(file)
            if (xml.testsuite.size() == 0) {
                log.warn("JUnit file ${file.name} contains no testsuites")
                return results
            }

            xml.testsuite.each { suite ->
                if (suite.testcase.size() == 0) {
                    log.warn("Testsuite in ${file.name} contains no testcases")
                    return
                }

                suite.testcase.each { testcase ->
                    def result = new TestResult(
                        name: testcase.@name.text() ?: 'Unknown',
                        className: testcase.@classname.text() ?: 'Unknown',
                        duration: testcase.@time.text()?.toDouble() ?: 0.0,
                        status: testcase.failure.size() > 0 || testcase.error.size() > 0 ? 'FAILED' : 'PASSED',
                        failureMessage: testcase.failure.text() ?: testcase.error.text() ?: '',
                        timestamp: suite.@timestamp.text() ?: ''
                    )
                    results << result
                }
            }
        } catch (Exception e) {
            log.warn("Failed to parse JUnit file ${file.name}: ${e.message}")
        }

        results
    }

    private List<TestResult> parseSpockHtmlFile(File file) {
        def results = []

        if (file == null || !file.exists() || !file.canRead()) {
            log.warn("Cannot parse Spock HTML file: file is null, doesn't exist, or cannot be read")
            return results
        }

        try {
            def content = file.text

            if (!content || content.trim().isEmpty()) {
                log.warn("Spock HTML file ${file.name} is empty or contains no content")
                return results
            }

            // Extract test information using improved regex pattern
            // More flexible pattern that can handle various HTML structures
            def testPattern = /<tr[^>]*>(?:.|\n)*?<td[^>]*>(.*?)<\/td>(?:.|\n)*?<td[^>]*>(.*?)<\/td>(?:.|\n)*?<td[^>]*>(.*?)<\/td>/
            def matches = content =~ testPattern

            if (matches.size() == 0) {
                log.warn("No test results found in Spock HTML file ${file.name}")
                return results
            }

            matches.each { match ->
                try {
                    // Validate that we have the expected number of capture groups
                    if (match.size() < 4) {
                        log.warn("Invalid regex match in ${file.name}: expected 4 groups, got ${match.size()}")
                        return
                    }

                    def testName = match[1]?.trim()
                    def className = match[2]?.trim()
                    def statusText = match[3]?.trim()

                    // Validate required fields
                    if (!testName || !statusText) {
                        log.warn("Missing required fields in ${file.name}: testName='${testName}', status='${statusText}'")
                        return
                    }

                    def status = statusText.toLowerCase().contains('pass') ? 'PASSED' : 'FAILED'
                    results << new TestResult(
                        name: testName,
                        className: className ?: 'SpockTest',
                        duration: 0.0, // Would need more sophisticated parsing
                        status: status
                    )
                } catch (Exception e) {
                    log.warn("Failed to parse test result from ${file.name}: ${e.message}")
                }
            }
        } catch (Exception e) {
            log.warn("Failed to parse Spock HTML file ${file.name}: ${e.message}")
        }

        results
    }

    private List<TestResult> parseJsonFile(File file) {
        def results = []

        if (file == null || !file.exists() || !file.canRead()) {
            log.warn("Cannot parse JSON file: file is null, doesn't exist, or cannot be read")
            return results
        }

        try {
            def json = new groovy.json.JsonSlurper().parse(file)

            if (!json) {
                log.warn("JSON file ${file.name} is empty or contains no data")
                return results
            }

            if (!json.tests) {
                log.warn("JSON file ${file.name} does not contain a 'tests' field")
                return results
            }

            if (!(json.tests instanceof List)) {
                log.warn("JSON file ${file.name} 'tests' field is not an array")
                return results
            }

            json.tests.each { test ->
                try {
                    if (!test.name) {
                        log.warn("Test in ${file.name} is missing required 'name' field")
                        return
                    }

                    results << new TestResult(
                        name: test.name,
                        className: test.className ?: 'Unknown',
                        duration: test.duration ?: 0.0,
                        status: test.status ?: 'UNKNOWN',
                        failureMessage: test.failureMessage ?: ''
                    )
                } catch (Exception e) {
                    log.warn("Failed to parse test result from ${file.name}: ${e.message}")
                }
            }
        } catch (Exception e) {
            log.warn("Failed to parse JSON file ${file.name}: ${e.message}")
        }

        results
    }

    private List<File> findFiles(String baseDir, String pattern) {
        def files = []
        def dir = new File(baseDir)

        if (!dir.exists() || !dir.isDirectory()) {
            log.warn("Directory does not exist or is not a directory: ${baseDir}")
            return files
        }

        // Constants for performance optimization
        final long MAX_FILE_SIZE = 50 * 1024 * 1024 // 50MB limit
        final int MAX_FILES = 10000 // Limit to prevent excessive processing

        try {
            dir.eachFileRecurse { file ->
                // Safety checks
                if (!file.isFile()) return
                if (files.size() >= MAX_FILES) {
                    log.warn("Reached maximum file limit (${MAX_FILES}) while searching in ${baseDir}")
                    return
                }
                if (file.length() > MAX_FILE_SIZE) {
                    log.warn("Skipping large file ${file.name} (size: ${file.length()} bytes)")
                    return
                }

                // Check if file matches pattern
                if (file.name =~ pattern) {
                    files << file
                }
            }
        } catch (Exception e) {
            log.error("Error during file traversal in ${baseDir}: ${e.message}")
        }

        log.debug("Found ${files.size()} files matching pattern '${pattern}' in ${baseDir}")
        files
    }
}

/**
 * Calculates analytics from test results.
 *
 * This class performs comprehensive analysis of test execution data including:
 * - Basic summary statistics (total tests, success rates, duration metrics)
 * - Test categorization by type (unit, integration, functional, performance)
 * - Test categorization by category (positive, negative, edge cases, performance)
 * - Performance analysis (slowest/fastest tests, duration statistics)
 * - Failure analysis (common failure patterns, flaky test identification)
 * - Trend analysis (comparing current results with historical data)
 *
 * The calculator uses efficient algorithms and provides detailed metrics for
 * understanding test suite health and performance characteristics.
 *
 * @author Test Analytics System
 * @since 1.0.0
 */
@Slf4j
class AnalyticsCalculator {

    Map calculateAnalytics(List<TestResult> results, Map options = [:]) {
        def analytics = [:]

        // Basic summary
        analytics.summary = calculateSummary(results)

        // By type analysis
        analytics.byType = calculateByType(results, options)

        // By category analysis
        analytics.byCategory = calculateByCategory(results, options)

        // Performance analysis
        analytics.performance = calculatePerformance(results)

        // Failure analysis
        analytics.failures = calculateFailures(results)

        // Top failing tests
        analytics.topFailing = findTopFailingTests(results)

        // Trends (if historical data is available)
        if (options.historicalData) {
            analytics.trends = calculateTrends(results, options.historicalData)
        }

        analytics
    }

    private Map calculateSummary(List<TestResult> results) {
        def total = results.size()
        def passed = results.count { it.status == 'PASSED' }
        def failed = total - passed
        def totalDuration = results.sum { it.duration }

        [
            totalTests: total,
            passedTests: passed,
            failedTests: failed,
            successRate: total > 0 ? (passed / total * 100) : 0.0,
            totalDuration: totalDuration,
            avgDuration: total > 0 ? (totalDuration / total) : 0.0
        ]
    }

    private Map calculateByType(List<TestResult> results, Map options) {
        def byType = [:]

        // Simple classification based on test names and content
        results.each { result ->
            def type = classifyTestType(result.name, result.className)
            if (!byType.containsKey(type)) {
                byType[type] = [count: 0, passed: 0, totalDuration: 0.0]
            }

            byType[type].count++
            if (result.status == 'PASSED') {
                byType[type].passed++
            }
            byType[type].totalDuration += result.duration
        }

        // Calculate derived metrics
        byType.each { type, data ->
            data.successRate = data.count > 0 ? (data.passed / data.count * 100) : 0.0
            data.avgDuration = data.count > 0 ? (data.totalDuration / data.count) : 0.0
        }

        byType
    }

    private Map calculateByCategory(List<TestResult> results, Map options) {
        def byCategory = [:]

        // Simple classification based on test names
        results.each { result ->
            def category = classifyTestCategory(result.name)
            if (!byCategory.containsKey(category)) {
                byCategory[category] = [count: 0, passed: 0, totalDuration: 0.0]
            }

            byCategory[category].count++
            if (result.status == 'PASSED') {
                byCategory[category].passed++
            }
            byCategory[category].totalDuration += result.duration
        }

        // Calculate derived metrics
        byCategory.each { category, data ->
            data.successRate = data.count > 0 ? (data.passed / data.count * 100) : 0.0
            data.avgDuration = data.count > 0 ? (data.totalDuration / data.count) : 0.0
        }

        byCategory
    }

    private Map calculatePerformance(List<TestResult> results) {
        def durations = results.findAll { it.duration > 0 }.collect { it.duration }.sort()

        def slowest = results.sort { -it.duration }.take(5).collect { [name: it.name, duration: it.duration] }
        def fastest = results.sort { it.duration }.take(5).collect { [name: it.name, duration: it.duration] }

        [
            avgDuration: durations.sum() / durations.size(),
            medianDuration: durations.size() > 0 ? durations[durations.size().intdiv(2)] : 0.0,
            slowest: slowest,
            fastest: fastest,
            slowestTests: slowest.collect { it.name },
            fastestTests: fastest.collect { it.name }
        ]
    }

    private Map calculateFailures(List<TestResult> results) {
        def failures = results.findAll { it.status == 'FAILED' }

        [
            totalFailures: failures.size(),
            byType: failures.groupBy { classifyTestType(it.name, it.className) },
            commonMessages: failures.groupBy { it.failureMessage }.sort { -it.value.size() }.take(5)
        ]
    }

    private List findTopFailingTests(List<TestResult> results) {
        def testFailures = results.findAll { it.status == 'FAILED' }
            .groupBy { it.name }
            .collect { name, failures ->
                [
                    name: name,
                    failureCount: failures.size(),
                    failureRate: (failures.size() / results.count { it.name == name } * 100),
                    lastFailure: failures.max { a, b ->
                        (a.timestamp ?: '') <=> (b.timestamp ?: '')
                    }?.timestamp ?: 'Unknown'
                ]
            }
            .sort { -it.failureRate }
            .take(10)

        testFailures
    }

    private Map calculateTrends(List<TestResult> currentResults, List historicalResults) {
        def currentSummary = calculateSummary(currentResults)
        def historicalSummary = calculateSummary(historicalResults)

        [
            successRateChange: currentSummary.successRate - historicalSummary.successRate,
            testCountChange: currentSummary.totalTests - historicalSummary.totalTests,
            durationChange: currentSummary.totalDuration - historicalSummary.totalDuration
        ]
    }

    private String classifyTestType(String testName, String className) {
        def name = (testName + ' ' + className).toLowerCase()

        if (name.contains('integration') || name.contains('repository') || name.contains('database')) {
            return TEST_TYPE_INTEGRATION
        }
        if (name.contains('selenium') || name.contains('web') || name.contains('ui')) {
            return TEST_TYPE_FUNCTIONAL
        }
        if (name.contains('performance') || name.contains('load')) {
            return TEST_TYPE_PERFORMANCE
        }
        return TEST_TYPE_UNIT
    }

    private String classifyTestCategory(String testName) {
        def name = testName.toLowerCase()

        if (name.contains('negative') || name.contains('fail') || name.contains('error')) {
            return TEST_CATEGORY_NEGATIVE
        }
        if (name.contains('edge') || name.contains('boundary')) {
            return TEST_CATEGORY_EDGE_CASE
        }
        if (name.contains('performance') || name.contains('load')) {
            return TEST_CATEGORY_PERFORMANCE
        }
        return TEST_CATEGORY_POSITIVE
    }
}

/**
 * Generates various reports from analytics data.
 *
 * This class creates multiple types of reports from processed test analytics:
 * - JSON reports: Structured data for programmatic consumption
 * - Summary reports: Human-readable text summaries
 * - Performance reports: Detailed performance analysis
 * - Failure reports: Analysis of test failures and patterns
 * - Recommendation reports: Actionable insights for test improvement
 * - HTML reports: Interactive web dashboards with styling and charts
 * - CSV reports: Tabular data for spreadsheet analysis
 *
 * The reports are designed to provide different views of the same data
 * for various stakeholders (developers, QA, management).
 *
 * @author Test Analytics System
 * @since 1.0.0
 */
@Slf4j
class ReportGenerator {

    // Test type constants (duplicated for access in this class)
    private static final String TEST_TYPE_INTEGRATION = 'INTEGRATION'

    Map generateReports(Map analytics, Map options = [:]) {
        def reports = [:]

        // JSON report
        reports.json = generateJsonReport(analytics)

        // Summary report
        reports.summary = generateSummaryReport(analytics)

        // Performance report
        reports.performance = generatePerformanceReport(analytics)

        // Failure analysis report
        reports.failures = generateFailureReport(analytics)

        // Recommendations
        reports.recommendations = generateRecommendations(analytics)

        reports
    }

    private String generateJsonReport(Map analytics) {
        groovy.json.JsonBuilder(analytics).toPrettyString()
    }

    private String generateSummaryReport(Map analytics) {
        def summary = analytics.summary
        """
Test Summary:
- Total Tests: ${summary.totalTests}
- Passed: ${summary.passedTests}
- Failed: ${summary.failedTests}
- Success Rate: ${String.format('%.1f%%', summary.successRate)}
- Total Duration: ${String.format('%.2f', summary.totalDuration)}s
- Average Duration: ${String.format('%.2f', summary.avgDuration)}s
"""
    }

    private String generatePerformanceReport(Map analytics) {
        def perf = analytics.performance
        """
Performance Analysis:
- Average Duration: ${String.format('%.2f', perf.avgDuration)}s
- Median Duration: ${String.format('%.2f', perf.medianDuration)}s

Slowest Tests:
${perf.slowest.collect { "  ${it.name}: ${String.format('%.2f', it.duration)}s" }.join('\n')}

Fastest Tests:
${perf.fastest.collect { "  ${it.name}: ${String.format('%.2f', it.duration)}s" }.join('\n')}
"""
    }

    private String generateFailureReport(Map analytics) {
        def failures = analytics.failures
        """
Failure Analysis:
- Total Failures: ${failures.totalFailures}

Common Failure Messages:
${failures.commonMessages.collect { message, tests ->
    "  '${message}': ${tests.size()} occurrences"
}.join('\n')}
"""
    }

    private String generateRecommendations(Map analytics) {
        def recommendations = []

        if (analytics.summary.successRate < LOW_SUCCESS_RATE_THRESHOLD) {
            recommendations << "⚠️  Low success rate detected. Consider reviewing test stability and environment setup."
        }

        if (analytics.summary.avgDuration > SLOW_TEST_THRESHOLD_SECONDS) {
            recommendations << "⚠️  Tests are running slowly. Consider optimizing test performance or parallel execution."
        }

        def failingTests = analytics.topFailing.findAll { it.failureRate > HIGH_FAILURE_RATE_THRESHOLD }
        if (failingTests.size() > 0) {
            recommendations << "🔧 Flaky tests detected: ${failingTests.collect { it.name }.join(', ')}. Consider fixing or isolating these tests."
        }

        def integrationTests = analytics.byType.TEST_TYPE_INTEGRATION
        if (integrationTests && integrationTests.successRate < INTEGRATION_TEST_SUCCESS_RATE_THRESHOLD) {
            recommendations << "🔧 Integration tests have low success rate. Check external dependencies and test isolation."
        }

        if (recommendations.isEmpty()) {
            recommendations << "✅ All tests are performing well. No immediate action required."
        }

        recommendations.join('\n')
    }
}

/**
 * Represents a test result.
 *
 * This class encapsulates all information about a single test execution,
 * including test identification, execution details, and outcome information.
 * It serves as the core data structure for test analytics processing.
 *
 * Key Properties:
 * - name: The test method name
 * - className: The fully qualified class name containing the test
 * - duration: Execution time in seconds (0.0 if not available)
 * - status: Test outcome (PASSED, FAILED, or UNKNOWN)
 * - failureMessage: Detailed failure description (empty if test passed)
 * - timestamp: When the test was executed (format may vary by source)
 *
 * @author Test Analytics System
 * @since 1.0.0
 */
@Slf4j
class TestResult {
    String name
    String className
    double duration
    String status
    String failureMessage
    String timestamp

    String toString() {
        "${status}: ${className}.${name} (${String.format('%.2f', duration)}s)"
    }
}

/**
 * Command-line interface for test analytics.
 *
 * This class provides a simple command-line interface for running test analytics
 * from the command line. It accepts arguments for input directory, output directory,
 * and output formats, then generates comprehensive test reports.
 *
 * Usage:
 *   groovy TestAnalyticsDashboard.groovy <results-dir> [output-dir] [formats...]
 *
 * Examples:
 *   groovy TestAnalyticsDashboard.groovy build/test-results
 *   groovy TestAnalyticsDashboard.groovy build/test-results /tmp/reports json html
 *   groovy TestAnalyticsDashboard.groovy build/test-results /tmp/reports json html csv
 *
 * Arguments:
 * - results-dir: Directory containing test result files (required)
 * - output-dir: Directory for generated reports (optional, defaults to 'build/reports')
 * - formats: Output formats to generate (optional, defaults to ['json', 'html'])
 *
 * @author Test Analytics System
 * @since 1.0.0
 */
@Slf4j
class TestAnalyticsCli {

    static void main(String[] args) {
        if (args.length < 1) {
            println "Usage: TestAnalyticsCli <results-dir> [output-dir] [formats...]"
            println "Example: TestAnalyticsCli build/test-results /tmp/reports json html"
            return
        }

        def resultsDir = args[0]
        def outputDir = args.length > 1 ? args[1] : DEFAULT_OUTPUT_DIR
        def formats = args.length > 2 ? args[2..-1].toList() : DEFAULT_FORMATS

        def dashboard = new TestAnalyticsDashboard()
        def analyticsData = dashboard.generateAnalytics(resultsDir)

        println "Generating test analytics reports..."
        dashboard.exportAnalytics(analyticsData, outputDir, formats)

        println "Reports generated in: ${outputDir}"
        println "Formats: ${formats.join(', ')}"
    }
}
