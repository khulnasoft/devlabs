package com.khulnasoft.spock.ai

import groovy.json.JsonBuilder
import groovy.json.JsonSlurper
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

/**
 * Test Analytics Dashboard Generator
 * Creates comprehensive test analytics and reports from test execution results
 */
class TestAnalyticsDashboard {

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
    Map generateAnalytics(String testResultsDir, Map options = [:]) {
        // Parse test results
        def testResults = resultParser.parseTestResults(testResultsDir)

        // Calculate analytics
        def analytics = calculator.calculateAnalytics(testResults, options)

        // Generate reports
        def reports = reportGenerator.generateReports(analytics, options)

        [
            analytics: analytics,
            reports: reports,
            metadata: [
                generatedAt: LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME),
                version: '1.0.0',
                options: options
            ]
        ]
    }

    /**
     * Exports analytics data in various formats.
     */
    void exportAnalytics(Map analyticsData, String outputDir, List<String> formats = ['json', 'html']) {
        formats.each { format ->
            def fileName = "test-analytics-${LocalDateTime.now().format(DateTimeFormatter.ofPattern('yyyy-MM-dd-HH-mm-ss'))}.${format}"
            def outputFile = new File(outputDir, fileName)

            switch (format) {
                case 'json':
                    exportJson(analyticsData, outputFile)
                    break
                case 'html':
                    exportHtml(analyticsData, outputFile)
                    break
                case 'csv':
                    exportCsv(analyticsData, outputFile)
                    break
            }
        }
    }

    private void exportJson(Map data, File file) {
        file.withWriter { writer ->
            writer.write new JsonBuilder(data).toPrettyString()
        }
    }

    private void exportHtml(Map data, File file) {
        def html = generateHtmlReport(data)
        file.withWriter { writer ->
            writer.write html
        }
    }

    private void exportCsv(Map data, File file) {
        def csv = generateCsvReport(data)
        file.withWriter { writer ->
            writer.write csv
        }
    }

    private String generateHtmlReport(Map data) {
        def analytics = data.analytics

        """
<!DOCTYPE html>
<html>
<head>
    <title>Test Analytics Dashboard</title>
    <style>
        body { font-family: Arial, sans-serif; margin: 20px; }
        .metric { background: #f5f5f5; padding: 10px; margin: 10px 0; border-radius: 5px; }
        .chart { margin: 20px 0; }
        .summary { background: #e8f4fd; padding: 15px; border-radius: 5px; }
        table { border-collapse: collapse; width: 100%; }
        th, td { border: 1px solid #ddd; padding: 8px; text-align: left; }
        th { background-color: #f2f2f2; }
        .high { color: #d32f2f; }
        .medium { color: #f57c00; }
        .low { color: #388e3c; }
    </style>
</head>
<body>
    <h1>Test Analytics Dashboard</h1>
    <p>Generated: ${data.metadata.generatedAt}</p>

    <div class="summary">
        <h2>Summary</h2>
        <div class="metric">
            <strong>Total Tests:</strong> ${analytics.summary.totalTests}
        </div>
        <div class="metric">
            <strong>Success Rate:</strong> ${String.format('%.1f%%', analytics.summary.successRate)}
        </div>
        <div class="metric">
            <strong>Total Duration:</strong> ${String.format('%.2f', analytics.summary.totalDuration)}s
        </div>
    </div>

    <h2>Test Results by Type</h2>
    <table>
        <tr><th>Type</th><th>Count</th><th>Success Rate</th><th>Avg Duration</th></tr>
        ${generateTableRows(analytics.byType)}
    </table>

    <h2>Test Results by Category</h2>
    <table>
        <tr><th>Category</th><th>Count</th><th>Success Rate</th><th>Avg Duration</th></tr>
        ${generateTableRows(analytics.byCategory)}
    </table>

    <h2>Top Failing Tests</h2>
    <table>
        <tr><th>Test Name</th><th>Failure Rate</th><th>Last Failure</th></tr>
        ${generateFailingTestsRows(analytics.topFailing)}
    </table>

    <h2>Performance Analysis</h2>
    <div class="metric">
        <strong>Slowest Tests:</strong> ${analytics.performance.slowest.join(', ')}
    </div>
    <div class="metric">
        <strong>Fastest Tests:</strong> ${analytics.performance.fastest.join(', ')}
    </div>
</body>
</html>
"""
    }

    private String generateTableRows(Map data) {
        data.collect { key, value ->
            "<tr><td>${key}</td><td>${value.count}</td><td>${String.format('%.1f%%', value.successRate)}</td><td>${String.format('%.2f', value.avgDuration)}s</td></tr>"
        }.join('\n        ')
    }

    private String generateFailingTestsRows(List failingTests) {
        failingTests.collect { test ->
            "<tr><td>${test.name}</td><td>${String.format('%.1f%%', test.failureRate)}</td><td>${test.lastFailure}</td></tr>"
        }.join('\n        ')
    }

    private String generateCsvReport(Map data) {
        def analytics = data.analytics
        def csv = new StringBuilder()

        csv.append('Metric,Value\n')
        csv.append("Total Tests,${analytics.summary.totalTests}\n")
        csv.append("Success Rate,${String.format('%.1f%%', analytics.summary.successRate)}\n")
        csv.append("Total Duration,${String.format('%.2f', analytics.summary.totalDuration)}\n")

        csv.append('\nType,Count,Success Rate,Avg Duration\n')
        analytics.byType.each { type, data ->
            csv.append("${type},${data.count},${String.format('%.1f%%', data.successRate)},${String.format('%.2f', data.avgDuration)}\n")
        }

        csv.toString()
    }
}

/**
 * Parses test results from various formats.
 */
class TestResultParser {

    List<TestResult> parseTestResults(String resultsDir) {
        def results = []

        // Parse JUnit XML reports
        def junitFiles = findFiles(resultsDir, '**/*TEST-*.xml')
        junitFiles.each { file ->
            results.addAll(parseJUnitFile(file))
        }

        // Parse Spock HTML reports if available
        def htmlFiles = findFiles(resultsDir, '**/*test-results.html')
        htmlFiles.each { file ->
            results.addAll(parseSpockHtmlFile(file))
        }

        // Parse JSON reports if available
        def jsonFiles = findFiles(resultsDir, '**/*.json')
        jsonFiles.each { file ->
            results.addAll(parseJsonFile(file))
        }

        results
    }

    private List<TestResult> parseJUnitFile(File file) {
        def results = []

        def xml = new XmlSlurper().parse(file)
        xml.testsuite.each { suite ->
            suite.testcase.each { testcase ->
                def result = new TestResult(
                    name: testcase.@name.text(),
                    className: testcase.@classname.text(),
                    duration: testcase.@time.text().toDouble(),
                    status: testcase.failure.size() > 0 || testcase.error.size() > 0 ? 'FAILED' : 'PASSED',
                    failureMessage: testcase.failure.text() ?: testcase.error.text(),
                    timestamp: suite.@timestamp.text()
                )
                results << result
            }
        }

        results
    }

    private List<TestResult> parseSpockHtmlFile(File file) {
        // Simplified HTML parsing - in reality, would use proper HTML parser
        def results = []
        def content = file.text

        // Extract test information using regex (simplified)
        def testPattern = /<tr[^>]*>.*?<td[^>]*>(.*?)<\/td>.*?<td[^>]*>(.*?)<\/td>.*?<td[^>]*>(.*?)<\/td>/
        def matches = content =~ testPattern

        matches.each { match ->
            def status = match[3].toLowerCase().contains('pass') ? 'PASSED' : 'FAILED'
            results << new TestResult(
                name: match[1],
                className: 'SpockTest',
                duration: 0.0, // Would need more sophisticated parsing
                status: status
            )
        }

        results
    }

    private List<TestResult> parseJsonFile(File file) {
        def results = []

        try {
            def json = new JsonSlurper().parse(file)
            if (json.tests) {
                json.tests.each { test ->
                    results << new TestResult(
                        name: test.name,
                        className: test.className,
                        duration: test.duration ?: 0.0,
                        status: test.status,
                        failureMessage: test.failureMessage
                    )
                }
            }
        } catch (Exception e) {
            // Skip invalid JSON files
        }

        results
    }

    private List<File> findFiles(String baseDir, String pattern) {
        def files = []
        def dir = new File(baseDir)

        if (dir.exists()) {
            dir.eachFileRecurse { file ->
                if (file.isFile() && file.name =~ pattern) {
                    files << file
                }
            }
        }

        files
    }
}

/**
 * Calculates analytics from test results.
 */
class AnalyticsCalculator {

    Map calculateAnalytics(List<TestResult> results, Map options) {
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
        if (options.containsKey('historicalData')) {
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
                    lastFailure: failures.max { it.timestamp }.timestamp
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
            return 'INTEGRATION'
        }
        if (name.contains('selenium') || name.contains('web') || name.contains('ui')) {
            return 'FUNCTIONAL'
        }
        if (name.contains('performance') || name.contains('load')) {
            return 'PERFORMANCE'
        }
        return 'UNIT'
    }

    private String classifyTestCategory(String testName) {
        def name = testName.toLowerCase()

        if (name.contains('negative') || name.contains('fail') || name.contains('error')) {
            return 'NEGATIVE'
        }
        if (name.contains('edge') || name.contains('boundary')) {
            return 'EDGE_CASE'
        }
        if (name.contains('performance') || name.contains('load')) {
            return 'PERFORMANCE'
        }
        return 'POSITIVE'
    }
}

/**
 * Generates various reports from analytics data.
 */
class ReportGenerator {

    Map generateReports(Map analytics, Map options) {
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
        new JsonBuilder(analytics).toPrettyString()
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

        if (analytics.summary.successRate < 80) {
            recommendations << "⚠️  Low success rate detected. Consider reviewing test stability and environment setup."
        }

        if (analytics.summary.avgDuration > 30) {
            recommendations << "⚠️  Tests are running slowly. Consider optimizing test performance or parallel execution."
        }

        def failingTests = analytics.topFailing.findAll { it.failureRate > 50 }
        if (failingTests.size() > 0) {
            recommendations << "🔧 Flaky tests detected: ${failingTests.collect { it.name }.join(', ')}. Consider fixing or isolating these tests."
        }

        def integrationTests = analytics.byType.INTEGRATION
        if (integrationTests && integrationTests.successRate < 70) {
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
 */
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
 */
class TestAnalyticsCli {

    static void main(String[] args) {
        if (args.length < 1) {
            println "Usage: TestAnalyticsCli <results-dir> [output-dir] [formats...]"
            println "Example: TestAnalyticsCli build/test-results /tmp/reports json html"
            return
        }

        def resultsDir = args[0]
        def outputDir = args.length > 1 ? args[1] : 'build/reports'
        def formats = args.length > 2 ? args[2..-1].toList() : ['json', 'html']

        def dashboard = new TestAnalyticsDashboard()
        def analyticsData = dashboard.generateAnalytics(resultsDir)

        println "Generating test analytics reports..."
        dashboard.exportAnalytics(analyticsData, outputDir, formats)

        println "Reports generated in: ${outputDir}"
        println "Formats: ${formats.join(', ')}"
    }
}
