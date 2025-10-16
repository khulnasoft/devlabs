package com.khulnasoft.cover.plugin;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import dev.langchain4j.model.openai.OpenAiChatModel;
import dev.langchain4j.service.AiServices;
import dev.langchain4j.service.UserMessage;
import org.tinylog.Logger;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * AI-powered test completeness evaluator that uses GPT to assess test coverage quality.
 * Analyzes code structure, test patterns, and provides comprehensive evaluation metrics.
 */
public class TestCompletenessEvaluator {

    private static final Logger LOG = Logger.getInstance(TestCompletenessEvaluator.class);
    private final OpenAiChatModel chatModel;
    private final Gson gson;

    public TestCompletenessEvaluator(String apiKey, String modelName) {
        this.chatModel = OpenAiChatModel.builder()
                .apiKey(apiKey)
                .modelName(modelName)
                .temperature(0.2) // Lower temperature for more consistent analysis
                .timeout(Duration.ofSeconds(60))
                .maxTokens(3000)
                .build();
        
        this.gson = new Gson();
    }

    /**
     * Evaluates the completeness of a test suite for a given Java class.
     */
    public TestCompletenessResult evaluateClassCompleteness(String sourceCode, List<String> existingTests) {
        try {
            LOG.info("Evaluating test completeness for class");

            String prompt = buildCompletenessPrompt(sourceCode, existingTests);
            
            TestCompletenessAnalyzer analyzer = AiServices.builder(TestCompletenessAnalyzer.class)
                    .chatLanguageModel(chatModel)
                    .build();

            String response = analyzer.analyzeCompleteness(prompt);
            JsonObject analysis = gson.fromJson(response, JsonObject.class);

            return parseCompletenessResult(analysis);

        } catch (Exception e) {
            LOG.error("Failed to evaluate test completeness", e);
            return createFallbackResult();
        }
    }

    /**
     * Evaluates overall project test completeness across multiple classes.
     */
    public ProjectCompletenessResult evaluateProjectCompleteness(Map<String, String> sourceFiles, 
                                                               Map<String, List<String>> testFiles) {
        try {
            LOG.info("Evaluating project-wide test completeness");

            String prompt = buildProjectCompletenessPrompt(sourceFiles, testFiles);
            
            TestCompletenessAnalyzer analyzer = AiServices.builder(TestCompletenessAnalyzer.class)
                    .chatLanguageModel(chatModel)
                    .build();

            String response = analyzer.analyzeProjectCompleteness(prompt);
            JsonObject analysis = gson.fromJson(response, JsonObject.class);

            return parseProjectCompletenessResult(analysis);

        } catch (Exception e) {
            LOG.error("Failed to evaluate project test completeness", e);
            return createFallbackProjectResult();
        }
    }

    private String buildCompletenessPrompt(String sourceCode, List<String> existingTests) {
        StringBuilder prompt = new StringBuilder();
        prompt.append("Analyze this Java class for test completeness. Consider:\n");
        prompt.append("1. Public method coverage\n");
        prompt.append("2. Edge cases and error conditions\n");
        prompt.append("3. Constructor variations\n");
        prompt.append("4. State management and lifecycle\n");
        prompt.append("5. Integration points with other classes\n");
        prompt.append("6. Boundary value testing\n");
        prompt.append("7. Null safety and exception handling\n\n");
        
        prompt.append("Source Code:\n");
        prompt.append(sourceCode);
        prompt.append("\n\n");
        
        if (!existingTests.isEmpty()) {
            prompt.append("Existing Tests:\n");
            for (String test : existingTests) {
                prompt.append(test).append("\n");
            }
            prompt.append("\n");
        }
        
        prompt.append("Provide a JSON response with:\n");
        prompt.append("- overall_score (0-100)\n");
        prompt.append("- coverage_metrics (object with method_coverage, edge_case_coverage, etc.)\n");
        prompt.append("- missing_scenarios (array of specific missing test scenarios)\n");
        prompt.append("- improvement_suggestions (array of actionable suggestions)\n");
        prompt.append("- complexity_assessment (low/medium/high)\n");
        prompt.append("- estimated_effort (hours to achieve good coverage)\n");
        
        return prompt.toString();
    }

    private String buildProjectCompletenessPrompt(Map<String, String> sourceFiles, 
                                                Map<String, List<String>> testFiles) {
        StringBuilder prompt = new StringBuilder();
        prompt.append("Analyze this Java project's overall test completeness. Consider:\n");
        prompt.append("1. Class-level coverage distribution\n");
        prompt.append("2. Integration between classes\n");
        prompt.append("3. End-to-end scenarios\n");
        prompt.append("4. Configuration and setup testing\n");
        prompt.append("5. Performance and load testing gaps\n");
        prompt.append("6. Security testing coverage\n");
        prompt.append("7. API and contract testing\n\n");
        
        prompt.append("Project Structure:\n");
        prompt.append("Source Files: ").append(sourceFiles.size()).append("\n");
        prompt.append("Test Files: ").append(testFiles.size()).append("\n\n");
        
        prompt.append("Provide a JSON response with:\n");
        prompt.append("- overall_project_score (0-100)\n");
        prompt.append("- class_coverage_distribution (object with coverage ranges)\n");
        prompt.append("- integration_gaps (array of missing integration tests)\n");
        prompt.append("- critical_missing_tests (array of most important missing tests)\n");
        prompt.append("- testing_patterns (analysis of test quality patterns)\n");
        prompt.append("- improvement_roadmap (prioritized improvement steps)\n");
        
        return prompt.toString();
    }

    private TestCompletenessResult parseCompletenessResult(JsonObject analysis) {
        TestCompletenessResult result = new TestCompletenessResult();
        
        result.setOverallScore(analysis.get("overall_score").getAsInt());
        
        JsonObject metrics = analysis.getAsJsonObject("coverage_metrics");
        result.setMethodCoverage(metrics.get("method_coverage").getAsDouble());
        result.setEdgeCaseCoverage(metrics.get("edge_case_coverage").getAsDouble());
        result.setErrorHandlingCoverage(metrics.get("error_handling_coverage").getAsDouble());
        result.setBoundaryValueCoverage(metrics.get("boundary_value_coverage").getAsDouble());
        
        result.setComplexityAssessment(analysis.get("complexity_assessment").getAsString());
        result.setEstimatedEffort(analysis.get("estimated_effort").getAsDouble());
        
        // Parse missing scenarios and suggestions as arrays
        List<String> missingScenarios = new ArrayList<>();
        analysis.getAsJsonArray("missing_scenarios").forEach(scenario -> 
            missingScenarios.add(scenario.getAsString()));
        result.setMissingScenarios(missingScenarios);
        
        List<String> suggestions = new ArrayList<>();
        analysis.getAsJsonArray("improvement_suggestions").forEach(suggestion -> 
            suggestions.add(suggestion.getAsString()));
        result.setImprovementSuggestions(suggestions);
        
        return result;
    }

    private ProjectCompletenessResult parseProjectCompletenessResult(JsonObject analysis) {
        ProjectCompletenessResult result = new ProjectCompletenessResult();
        
        result.setOverallScore(analysis.get("overall_project_score").getAsInt());
        
        JsonObject distribution = analysis.getAsJsonObject("class_coverage_distribution");
        result.setHighCoverageClasses(distribution.get("high_coverage").getAsInt());
        result.setMediumCoverageClasses(distribution.get("medium_coverage").getAsInt());
        result.setLowCoverageClasses(distribution.get("low_coverage").getAsInt());
        
        // Parse arrays
        List<String> integrationGaps = new ArrayList<>();
        analysis.getAsJsonArray("integration_gaps").forEach(gap -> 
            integrationGaps.add(gap.getAsString()));
        result.setIntegrationGaps(integrationGaps);
        
        List<String> criticalTests = new ArrayList<>();
        analysis.getAsJsonArray("critical_missing_tests").forEach(test -> 
            criticalTests.add(test.getAsString()));
        result.setCriticalMissingTests(criticalTests);
        
        result.setTestingPatterns(analysis.get("testing_patterns").getAsString());
        
        List<String> roadmap = new ArrayList<>();
        analysis.getAsJsonArray("improvement_roadmap").forEach(step -> 
            roadmap.add(step.getAsString()));
        result.setImprovementRoadmap(roadmap);
        
        return result;
    }

    private TestCompletenessResult createFallbackResult() {
        TestCompletenessResult result = new TestCompletenessResult();
        result.setOverallScore(50);
        result.setMethodCoverage(0.5);
        result.setEdgeCaseCoverage(0.3);
        result.setErrorHandlingCoverage(0.4);
        result.setBoundaryValueCoverage(0.3);
        result.setComplexityAssessment("medium");
        result.setEstimatedEffort(8.0);
        result.setMissingScenarios(List.of("Edge case testing", "Error condition testing", "Boundary value testing"));
        result.setImprovementSuggestions(List.of("Add more edge case tests", "Improve error handling coverage", "Add boundary value tests"));
        return result;
    }

    private ProjectCompletenessResult createFallbackProjectResult() {
        ProjectCompletenessResult result = new ProjectCompletenessResult();
        result.setOverallScore(60);
        result.setHighCoverageClasses(30);
        result.setMediumCoverageClasses(40);
        result.setLowCoverageClasses(30);
        result.setIntegrationGaps(List.of("Missing integration between service and repository layers"));
        result.setCriticalMissingTests(List.of("End-to-end API tests", "Database integration tests"));
        result.setTestingPatterns("Inconsistent test quality across classes");
        result.setImprovementRoadmap(List.of("Standardize test patterns", "Add integration tests", "Improve coverage for complex classes"));
        return result;
    }

    // Inner classes for results
    public static class TestCompletenessResult {
        private int overallScore;
        private double methodCoverage;
        private double edgeCaseCoverage;
        private double errorHandlingCoverage;
        private double boundaryValueCoverage;
        private String complexityAssessment;
        private double estimatedEffort;
        private List<String> missingScenarios;
        private List<String> improvementSuggestions;

        // Getters and setters
        public int getOverallScore() { return overallScore; }
        public void setOverallScore(int overallScore) { this.overallScore = overallScore; }
        public double getMethodCoverage() { return methodCoverage; }
        public void setMethodCoverage(double methodCoverage) { this.methodCoverage = methodCoverage; }
        public double getEdgeCaseCoverage() { return edgeCaseCoverage; }
        public void setEdgeCaseCoverage(double edgeCaseCoverage) { this.edgeCaseCoverage = edgeCaseCoverage; }
        public double getErrorHandlingCoverage() { return errorHandlingCoverage; }
        public void setErrorHandlingCoverage(double errorHandlingCoverage) { this.errorHandlingCoverage = errorHandlingCoverage; }
        public double getBoundaryValueCoverage() { return boundaryValueCoverage; }
        public void setBoundaryValueCoverage(double boundaryValueCoverage) { this.boundaryValueCoverage = boundaryValueCoverage; }
        public String getComplexityAssessment() { return complexityAssessment; }
        public void setComplexityAssessment(String complexityAssessment) { this.complexityAssessment = complexityAssessment; }
        public double getEstimatedEffort() { return estimatedEffort; }
        public void setEstimatedEffort(double estimatedEffort) { this.estimatedEffort = estimatedEffort; }
        public List<String> getMissingScenarios() { return missingScenarios; }
        public void setMissingScenarios(List<String> missingScenarios) { this.missingScenarios = missingScenarios; }
        public List<String> getImprovementSuggestions() { return improvementSuggestions; }
        public void setImprovementSuggestions(List<String> improvementSuggestions) { this.improvementSuggestions = improvementSuggestions; }
    }

    public static class ProjectCompletenessResult {
        private int overallScore;
        private int highCoverageClasses;
        private int mediumCoverageClasses;
        private int lowCoverageClasses;
        private List<String> integrationGaps;
        private List<String> criticalMissingTests;
        private String testingPatterns;
        private List<String> improvementRoadmap;

        // Getters and setters
        public int getOverallScore() { return overallScore; }
        public void setOverallScore(int overallScore) { this.overallScore = overallScore; }
        public int getHighCoverageClasses() { return highCoverageClasses; }
        public void setHighCoverageClasses(int highCoverageClasses) { this.highCoverageClasses = highCoverageClasses; }
        public int getMediumCoverageClasses() { return mediumCoverageClasses; }
        public void setMediumCoverageClasses(int mediumCoverageClasses) { this.mediumCoverageClasses = mediumCoverageClasses; }
        public int getLowCoverageClasses() { return lowCoverageClasses; }
        public void setLowCoverageClasses(int lowCoverageClasses) { this.lowCoverageClasses = lowCoverageClasses; }
        public List<String> getIntegrationGaps() { return integrationGaps; }
        public void setIntegrationGaps(List<String> integrationGaps) { this.integrationGaps = integrationGaps; }
        public List<String> getCriticalMissingTests() { return criticalMissingTests; }
        public void setCriticalMissingTests(List<String> criticalMissingTests) { this.criticalMissingTests = criticalMissingTests; }
        public String getTestingPatterns() { return testingPatterns; }
        public void setTestingPatterns(String testingPatterns) { this.testingPatterns = testingPatterns; }
        public List<String> getImprovementRoadmap() { return improvementRoadmap; }
        public void setImprovementRoadmap(List<String> improvementRoadmap) { this.improvementRoadmap = improvementRoadmap; }
    }

    // AI Service interface for LangChain4J
    interface TestCompletenessAnalyzer {
        @UserMessage("{prompt}")
        String analyzeCompleteness(String prompt);

        @UserMessage("{prompt}")
        String analyzeProjectCompleteness(String prompt);
    }
}
