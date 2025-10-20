package com.khulnasoft.cover.plugin;

import org.gradle.api.Project;
import org.gradle.api.model.ObjectFactory;
import org.gradle.api.provider.ListProperty;
import org.gradle.api.provider.MapProperty;
import org.gradle.api.provider.Property;

public abstract class CoverageAiExtension {

    // AI Model Configuration
    private final Property<String> aiProvider;
    private final Property<String> modelName;
    private final Property<Double> temperature;
    private final Property<Integer> maxTokens;
    private final Property<Integer> timeoutSeconds;
    private final Property<Integer> retryAttempts;
    private final Property<Integer> retryDelayMs;

    // Analysis Configuration
    private final Property<Double> coverageThreshold;
    private final Property<Double> branchCoverageThreshold;
    private final Property<Double> methodCoverageThreshold;
    private final Property<Double> classCoverageThreshold;
    private final Property<Boolean> enableSuggestions;
    private final Property<Integer> maxSuggestionsPerFile;
    private final Property<Double> minCoverageForSuggestions;
    private final ListProperty<String> suggestionTypes;
    private final Property<String> analysisDepth;
    private final Property<Boolean> includeCyclomaticComplexity;
    private final Property<Boolean> includeCognitiveComplexity;

    // Reporting Configuration
    private final ListProperty<String> reportFormats;
    private final Property<String> outputDir;
    private final Property<Boolean> includeSourceCode;
    private final Property<Boolean> includeRecommendations;
    private final Property<Boolean> includeMetrics;
    private final Property<Boolean> includeTrendAnalysis;
    private final Property<Boolean> generateIndividualFileReports;

    // Integration Configuration
    private final Property<Boolean> jacocoEnabled;
    private final Property<String> jacocoReportPath;
    private final Property<String> jacocoXmlReportPath;
    private final Property<String> jacocoHtmlReportPath;
    private final ListProperty<String> jacocoSourceDirs;
    private final ListProperty<String> jacocoClassDirs;
    private final ListProperty<String> jacocoAdditionalSourceDirs;
    private final ListProperty<String> jacocoAdditionalClassDirs;

    private final Property<Boolean> junitEnabled;
    private final Property<String> junitReportPath;
    private final Property<Boolean> junitIncludeParameterizedTests;
    private final Property<Boolean> junitIncludeDynamicTests;

    private final Property<Boolean> testngEnabled;
    private final Property<String> testngReportPath;

    private final Property<Boolean> spockEnabled;
    private final Property<String> spockReportPath;
    private final Property<Boolean> spockIncludeDataDrivenTests;

    // Exclusions
    private final ListProperty<String> excludedPackages;
    private final ListProperty<String> excludedClasses;
    private final ListProperty<String> excludedMethods;
    private final ListProperty<String> excludedAnnotations;

    // Development Configuration
    private final Property<Boolean> debugMode;
    private final Property<Boolean> verboseLogging;
    private final Property<Boolean> cacheEnabled;
    private final Property<String> cacheDir;
    private final Property<Boolean> offlineMode;

    public CoverageAiExtension(Project project) {
        ObjectFactory factory = project.getObjects();

        // AI Model Configuration
        this.aiProvider = factory.property(String.class).convention("openai");
        this.modelName = factory.property(String.class).convention("gpt-4");
        this.temperature = factory.property(Double.class).convention(0.3);
        this.maxTokens = factory.property(Integer.class).convention(2000);
        this.timeoutSeconds = factory.property(Integer.class).convention(30);
        this.retryAttempts = factory.property(Integer.class).convention(3);
        this.retryDelayMs = factory.property(Integer.class).convention(1000);

        // Analysis Configuration
        this.coverageThreshold = factory.property(Double.class).convention(0.75);
        this.branchCoverageThreshold = factory.property(Double.class).convention(0.75);
        this.methodCoverageThreshold = factory.property(Double.class).convention(0.80);
        this.classCoverageThreshold = factory.property(Double.class).convention(0.85);
        this.enableSuggestions = factory.property(Boolean.class).convention(true);
        this.maxSuggestionsPerFile = factory.property(Integer.class).convention(5);
        this.minCoverageForSuggestions = factory.property(Double.class).convention(0.50);
        this.suggestionTypes = factory.listProperty(String.class).convention(
            java.util.Arrays.asList(
                "missing-test-cases",
                "edge-case-coverage",
                "error-handling-tests",
                "boundary-value-tests",
                "null-safety-tests",
                "exception-paths",
                "integration-points"
            )
        );
        this.analysisDepth = factory.property(String.class).convention("method");
        this.includeCyclomaticComplexity = factory.property(Boolean.class).convention(true);
        this.includeCognitiveComplexity = factory.property(Boolean.class).convention(true);

        // Reporting Configuration
        this.reportFormats = factory.listProperty(String.class).convention(
            java.util.Arrays.asList("html", "json", "xml", "markdown")
        );
        this.outputDir = factory.property(String.class).convention("build/coverage-ai-reports");
        this.includeSourceCode = factory.property(Boolean.class).convention(true);
        this.includeRecommendations = factory.property(Boolean.class).convention(true);
        this.includeMetrics = factory.property(Boolean.class).convention(true);
        this.includeTrendAnalysis = factory.property(Boolean.class).convention(true);
        this.generateIndividualFileReports = factory.property(Boolean.class).convention(true);

        // Integration Configuration
        this.jacocoEnabled = factory.property(Boolean.class).convention(true);
        this.jacocoReportPath = factory.property(String.class).convention("build/jacoco/test.exec");
        this.jacocoXmlReportPath = factory.property(String.class).convention("build/reports/jacoco/test/jacocoTestReport.xml");
        this.jacocoHtmlReportPath = factory.property(String.class).convention("build/reports/jacoco/test/html");
        this.jacocoSourceDirs = factory.listProperty(String.class).convention(
            java.util.Arrays.asList("src/main/java", "src/main/kotlin", "src/main/groovy")
        );
        this.jacocoClassDirs = factory.listProperty(String.class).convention(
            java.util.Arrays.asList("build/classes/java/main", "build/classes/kotlin/main", "build/classes/groovy/main")
        );
        this.jacocoAdditionalSourceDirs = factory.listProperty(String.class).empty();
        this.jacocoAdditionalClassDirs = factory.listProperty(String.class).empty();

        this.junitEnabled = factory.property(Boolean.class).convention(true);
        this.junitReportPath = factory.property(String.class).convention("build/test-results");
        this.junitIncludeParameterizedTests = factory.property(Boolean.class).convention(true);
        this.junitIncludeDynamicTests = factory.property(Boolean.class).convention(true);

        this.testngEnabled = factory.property(Boolean.class).convention(false);
        this.testngReportPath = factory.property(String.class).convention("build/test-results/testng");

        this.spockEnabled = factory.property(Boolean.class).convention(true);
        this.spockReportPath = factory.property(String.class).convention("build/test-results/spock");
        this.spockIncludeDataDrivenTests = factory.property(Boolean.class).convention(true);

        // Exclusions
        this.excludedPackages = factory.listProperty(String.class).convention(
            java.util.Arrays.asList(
                ".*\\.generated\\..*",
                ".*\\.config\\..*",
                ".*\\..*Config.*",
                ".*\\..*Configuration.*"
            )
        );
        this.excludedClasses = factory.listProperty(String.class).convention(
            java.util.Arrays.asList(
                ".*Test.*",
                ".*Application",
                ".*Main",
                ".*Bootstrap",
                ".*Config",
                ".*Configuration",
                ".*DTO",
                ".*Dto"
            )
        );
        this.excludedMethods = factory.listProperty(String.class).convention(
            java.util.Arrays.asList(
                "toString",
                "equals",
                "hashCode",
                "getters",
                "setters",
                "finalize",
                "clone"
            )
        );
        this.excludedAnnotations = factory.listProperty(String.class).convention(
            java.util.Arrays.asList(
                "@Generated",
                "@Configuration",
                "@Controller",
                "@RestController",
                "@Service",
                "@Repository",
                "@Component"
            )
        );

        // Development Configuration
        this.debugMode = factory.property(Boolean.class).convention(false);
        this.verboseLogging = factory.property(Boolean.class).convention(false);
        this.cacheEnabled = factory.property(Boolean.class).convention(true);
        this.cacheDir = factory.property(String.class).convention(".coverage-ai/cache");
        this.offlineMode = factory.property(Boolean.class).convention(false);

        // Legacy properties for backward compatibility
        this.apiKey = factory.property(String.class);
        this.wanDBApiKey = factory.property(String.class);
        this.iterations = factory.property(Integer.class);
        this.coverage = factory.property(Integer.class);
        this.coverageAiBinaryPath = factory.property(String.class);
    }

    // Legacy properties for backward compatibility
    private final Property<String> apiKey;
    private final Property<String> wanDBApiKey;
    private final Property<Integer> iterations;
    private final Property<Integer> coverage;
    private final Property<String> coverageAiBinaryPath;

    // Legacy getters for backward compatibility
    public Property<Integer> getIterations() {
        return iterations;
    }

    public Property<String> getApiKey() {
        return apiKey;
    }

    public Property<String> getWanDBApiKey() {
        return wanDBApiKey;
    }

    public Property<Integer> getCoverage() {
        return coverage;
    }

    public Property<String> getCoverageAiBinaryPath() {
        return coverageAiBinaryPath;
    }

    // AI Model Configuration getters
    public Property<String> getAiProvider() {
        return aiProvider;
    }

    public Property<String> getModelName() {
        return modelName;
    }

    public Property<Double> getTemperature() {
        return temperature;
    }

    public Property<Integer> getMaxTokens() {
        return maxTokens;
    }

    public Property<Integer> getTimeoutSeconds() {
        return timeoutSeconds;
    }

    public Property<Integer> getRetryAttempts() {
        return retryAttempts;
    }

    public Property<Integer> getRetryDelayMs() {
        return retryDelayMs;
    }

    // Analysis Configuration getters
    public Property<Double> getCoverageThreshold() {
        return coverageThreshold;
    }

    public Property<Double> getBranchCoverageThreshold() {
        return branchCoverageThreshold;
    }

    public Property<Double> getMethodCoverageThreshold() {
        return methodCoverageThreshold;
    }

    public Property<Double> getClassCoverageThreshold() {
        return classCoverageThreshold;
    }

    public Property<Boolean> getEnableSuggestions() {
        return enableSuggestions;
    }

    public Property<Integer> getMaxSuggestionsPerFile() {
        return maxSuggestionsPerFile;
    }

    public Property<Double> getMinCoverageForSuggestions() {
        return minCoverageForSuggestions;
    }

    public ListProperty<String> getSuggestionTypes() {
        return suggestionTypes;
    }

    public Property<String> getAnalysisDepth() {
        return analysisDepth;
    }

    public Property<Boolean> getIncludeCyclomaticComplexity() {
        return includeCyclomaticComplexity;
    }

    public Property<Boolean> getIncludeCognitiveComplexity() {
        return includeCognitiveComplexity;
    }

    // Reporting Configuration getters
    public ListProperty<String> getReportFormats() {
        return reportFormats;
    }

    public Property<String> getOutputDir() {
        return outputDir;
    }

    public Property<Boolean> getIncludeSourceCode() {
        return includeSourceCode;
    }

    public Property<Boolean> getIncludeRecommendations() {
        return includeRecommendations;
    }

    public Property<Boolean> getIncludeMetrics() {
        return includeMetrics;
    }

    public Property<Boolean> getIncludeTrendAnalysis() {
        return includeTrendAnalysis;
    }

    public Property<Boolean> getGenerateIndividualFileReports() {
        return generateIndividualFileReports;
    }

    // Integration Configuration getters
    public Property<Boolean> getJacocoEnabled() {
        return jacocoEnabled;
    }

    public Property<String> getJacocoReportPath() {
        return jacocoReportPath;
    }

    public Property<String> getJacocoXmlReportPath() {
        return jacocoXmlReportPath;
    }

    public Property<String> getJacocoHtmlReportPath() {
        return jacocoHtmlReportPath;
    }

    public ListProperty<String> getJacocoSourceDirs() {
        return jacocoSourceDirs;
    }

    public ListProperty<String> getJacocoClassDirs() {
        return jacocoClassDirs;
    }

    public ListProperty<String> getJacocoAdditionalSourceDirs() {
        return jacocoAdditionalSourceDirs;
    }

    public ListProperty<String> getJacocoAdditionalClassDirs() {
        return jacocoAdditionalClassDirs;
    }

    public Property<Boolean> getJunitEnabled() {
        return junitEnabled;
    }

    public Property<String> getJunitReportPath() {
        return junitReportPath;
    }

    public Property<Boolean> getJunitIncludeParameterizedTests() {
        return junitIncludeParameterizedTests;
    }

    public Property<Boolean> getJunitIncludeDynamicTests() {
        return junitIncludeDynamicTests;
    }

    public Property<Boolean> getTestngEnabled() {
        return testngEnabled;
    }

    public Property<String> getTestngReportPath() {
        return testngReportPath;
    }

    public Property<Boolean> getSpockEnabled() {
        return spockEnabled;
    }

    public Property<String> getSpockReportPath() {
        return spockReportPath;
    }

    public Property<Boolean> getSpockIncludeDataDrivenTests() {
        return spockIncludeDataDrivenTests;
    }

    // Exclusions getters
    public ListProperty<String> getExcludedPackages() {
        return excludedPackages;
    }

    public ListProperty<String> getExcludedClasses() {
        return excludedClasses;
    }

    public ListProperty<String> getExcludedMethods() {
        return excludedMethods;
    }

    public ListProperty<String> getExcludedAnnotations() {
        return excludedAnnotations;
    }

    // Development Configuration getters
    public Property<Boolean> getDebugMode() {
        return debugMode;
    }

    public Property<Boolean> getVerboseLogging() {
        return verboseLogging;
    }

    public Property<Boolean> getCacheEnabled() {
        return cacheEnabled;
    }

    public Property<String> getCacheDir() {
        return cacheDir;
    }

    public Property<Boolean> getOfflineMode() {
        return offlineMode;
    }
}
