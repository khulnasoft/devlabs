package com.khulnasoft.cover.plugin;

import dev.langchain4j.model.openai.OpenAiChatModel;
import org.gradle.api.DefaultTask;
import org.gradle.api.Project;
import org.gradle.api.model.ObjectFactory;
import org.gradle.api.provider.ListProperty;
import org.gradle.api.provider.Property;
import org.gradle.api.tasks.Input;
import org.gradle.api.tasks.Optional;
import org.gradle.api.tasks.OutputDirectory;
import org.gradle.api.tasks.OutputFile;
import org.gradle.api.tasks.TaskAction;

public abstract class CoverageAiTask extends DefaultTask {
    // Legacy properties for backward compatibility
    private Property<String> apiKey;
    private Property<String> wanDBApiKey;
    private Property<Integer> iterations;
    private Property<Integer> coverage;
    private Property<String> coverageAiBinaryPath;

    // Comprehensive configuration properties
    private Property<String> aiProvider;
    private Property<String> modelName;
    private Property<Double> temperature;
    private Property<Integer> maxTokens;
    private Property<Integer> timeoutSeconds;
    private Property<Integer> retryAttempts;
    private Property<Integer> retryDelayMs;

    // Analysis configuration
    private Property<Double> coverageThreshold;
    private Property<Double> branchCoverageThreshold;
    private Property<Double> methodCoverageThreshold;
    private Property<Double> classCoverageThreshold;
    private Property<Boolean> enableSuggestions;
    private Property<Integer> maxSuggestionsPerFile;
    private Property<Double> minCoverageForSuggestions;
    private ListProperty<String> suggestionTypes;
    private Property<String> analysisDepth;
    private Property<Boolean> includeCyclomaticComplexity;
    private Property<Boolean> includeCognitiveComplexity;

    // Reporting configuration
    private ListProperty<String> reportFormats;
    private Property<String> outputDir;
    private Property<Boolean> includeSourceCode;
    private Property<Boolean> includeRecommendations;
    private Property<Boolean> includeMetrics;
    private Property<Boolean> includeTrendAnalysis;
    private Property<Boolean> generateIndividualFileReports;

    // Integration configuration
    private Property<Boolean> jacocoEnabled;
    private Property<String> jacocoReportPath;
    private Property<String> jacocoXmlReportPath;
    private Property<String> jacocoHtmlReportPath;
    private ListProperty<String> jacocoSourceDirs;
    private ListProperty<String> jacocoClassDirs;
    private ListProperty<String> jacocoAdditionalSourceDirs;
    private ListProperty<String> jacocoAdditionalClassDirs;

    private Property<Boolean> junitEnabled;
    private Property<String> junitReportPath;
    private Property<Boolean> junitIncludeParameterizedTests;
    private Property<Boolean> junitIncludeDynamicTests;

    private Property<Boolean> testngEnabled;
    private Property<String> testngReportPath;

    private Property<Boolean> spockEnabled;
    private Property<String> spockReportPath;
    private Property<Boolean> spockIncludeDataDrivenTests;

    // Exclusions
    private ListProperty<String> excludedPackages;
    private ListProperty<String> excludedClasses;
    private ListProperty<String> excludedMethods;
    private ListProperty<String> excludedAnnotations;

    // Development configuration
    private Property<Boolean> debugMode;
    private Property<Boolean> verboseLogging;
    private Property<Boolean> cacheEnabled;
    private Property<String> cacheDir;
    private Property<Boolean> offlineMode;

    private CoverageAi coverageAi;
    private Project project;

    public CoverageAiTask() {
        init(getProject());
    }

    private void init(Project project) {
        this.project = project;
        ObjectFactory objectFactory = this.project.getObjects();

        // Initialize legacy properties
        this.apiKey = objectFactory.property(String.class);
        this.wanDBApiKey = objectFactory.property(String.class);
        this.iterations = objectFactory.property(Integer.class);
        this.coverageAiBinaryPath = objectFactory.property(String.class);
        this.coverage = objectFactory.property(Integer.class);

        // Initialize comprehensive properties
        this.aiProvider = objectFactory.property(String.class);
        this.modelName = objectFactory.property(String.class);
        this.temperature = objectFactory.property(Double.class);
        this.maxTokens = objectFactory.property(Integer.class);
        this.timeoutSeconds = objectFactory.property(Integer.class);
        this.retryAttempts = objectFactory.property(Integer.class);
        this.retryDelayMs = objectFactory.property(Integer.class);

        this.coverageThreshold = objectFactory.property(Double.class);
        this.branchCoverageThreshold = objectFactory.property(Double.class);
        this.methodCoverageThreshold = objectFactory.property(Double.class);
        this.classCoverageThreshold = objectFactory.property(Double.class);
        this.enableSuggestions = objectFactory.property(Boolean.class);
        this.maxSuggestionsPerFile = objectFactory.property(Integer.class);
        this.minCoverageForSuggestions = objectFactory.property(Double.class);
        this.suggestionTypes = objectFactory.listProperty(String.class);
        this.analysisDepth = objectFactory.property(String.class);
        this.includeCyclomaticComplexity = objectFactory.property(Boolean.class);
        this.includeCognitiveComplexity = objectFactory.property(Boolean.class);

        this.reportFormats = objectFactory.listProperty(String.class);
        this.outputDir = objectFactory.property(String.class);
        this.includeSourceCode = objectFactory.property(Boolean.class);
        this.includeRecommendations = objectFactory.property(Boolean.class);
        this.includeMetrics = objectFactory.property(Boolean.class);
        this.includeTrendAnalysis = objectFactory.property(Boolean.class);
        this.generateIndividualFileReports = objectFactory.property(Boolean.class);

        this.jacocoEnabled = objectFactory.property(Boolean.class);
        this.jacocoReportPath = objectFactory.property(String.class);
        this.jacocoXmlReportPath = objectFactory.property(String.class);
        this.jacocoHtmlReportPath = objectFactory.property(String.class);
        this.jacocoSourceDirs = objectFactory.listProperty(String.class);
        this.jacocoClassDirs = objectFactory.listProperty(String.class);
        this.jacocoAdditionalSourceDirs = objectFactory.listProperty(String.class);
        this.jacocoAdditionalClassDirs = objectFactory.listProperty(String.class);

        this.junitEnabled = objectFactory.property(Boolean.class);
        this.junitReportPath = objectFactory.property(String.class);
        this.junitIncludeParameterizedTests = objectFactory.property(Boolean.class);
        this.junitIncludeDynamicTests = objectFactory.property(Boolean.class);

        this.testngEnabled = objectFactory.property(Boolean.class);
        this.testngReportPath = objectFactory.property(String.class);

        this.spockEnabled = objectFactory.property(Boolean.class);
        this.spockReportPath = objectFactory.property(String.class);
        this.spockIncludeDataDrivenTests = objectFactory.property(Boolean.class);

        this.excludedPackages = objectFactory.listProperty(String.class);
        this.excludedClasses = objectFactory.listProperty(String.class);
        this.excludedMethods = objectFactory.listProperty(String.class);
        this.excludedAnnotations = objectFactory.listProperty(String.class);

        this.debugMode = objectFactory.property(Boolean.class);
        this.verboseLogging = objectFactory.property(Boolean.class);
        this.cacheEnabled = objectFactory.property(Boolean.class);
        this.cacheDir = objectFactory.property(String.class);
        this.offlineMode = objectFactory.property(Boolean.class);
    }

    // Legacy getters for backward compatibility
    @Optional
    @Input
    public Property<String> getApiKey() {
        return apiKey;
    }

    @Optional
    @Input
    public Property<String> getWanDBApiKey() {
        return wanDBApiKey;
    }

    @Optional
    @Input
    public Property<String> getCoverageAiBinaryPath() {
        return coverageAiBinaryPath;
    }

    @Optional
    @Input
    public Property<Integer> getIterations() {
        return iterations;
    }

    @Optional
    @Input
    public Property<Integer> getCoverage() {
        return coverage;
    }

    // Comprehensive configuration getters
    @Optional
    @Input
    public Property<String> getAiProvider() {
        return aiProvider;
    }

    @Optional
    @Input
    public Property<String> getModelName() {
        return modelName;
    }

    @Optional
    @Input
    public Property<Double> getTemperature() {
        return temperature;
    }

    @Optional
    @Input
    public Property<Integer> getMaxTokens() {
        return maxTokens;
    }

    @Optional
    @Input
    public Property<Integer> getTimeoutSeconds() {
        return timeoutSeconds;
    }

    @Optional
    @Input
    public Property<Integer> getRetryAttempts() {
        return retryAttempts;
    }

    @Optional
    @Input
    public Property<Integer> getRetryDelayMs() {
        return retryDelayMs;
    }

    @Optional
    @Input
    public Property<Double> getCoverageThreshold() {
        return coverageThreshold;
    }

    @Optional
    @Input
    public Property<Double> getBranchCoverageThreshold() {
        return branchCoverageThreshold;
    }

    @Optional
    @Input
    public Property<Double> getMethodCoverageThreshold() {
        return methodCoverageThreshold;
    }

    @Optional
    @Input
    public Property<Double> getClassCoverageThreshold() {
        return classCoverageThreshold;
    }

    @Optional
    @Input
    public Property<Boolean> getEnableSuggestions() {
        return enableSuggestions;
    }

    @Optional
    @Input
    public Property<Integer> getMaxSuggestionsPerFile() {
        return maxSuggestionsPerFile;
    }

    @Optional
    @Input
    public Property<Double> getMinCoverageForSuggestions() {
        return minCoverageForSuggestions;
    }

    @Optional
    @Input
    public ListProperty<String> getSuggestionTypes() {
        return suggestionTypes;
    }

    @Optional
    @Input
    public Property<String> getAnalysisDepth() {
        return analysisDepth;
    }

    @Optional
    @Input
    public Property<Boolean> getIncludeCyclomaticComplexity() {
        return includeCyclomaticComplexity;
    }

    @Optional
    @Input
    public Property<Boolean> getIncludeCognitiveComplexity() {
        return includeCognitiveComplexity;
    }

    @Optional
    @Input
    public ListProperty<String> getReportFormats() {
        return reportFormats;
    }

    @Optional
    @Input
    public Property<String> getOutputDir() {
        return outputDir;
    }

    @Optional
    @Input
    public Property<Boolean> getIncludeSourceCode() {
        return includeSourceCode;
    }

    @Optional
    @Input
    public Property<Boolean> getIncludeRecommendations() {
        return includeRecommendations;
    }

    @Optional
    @Input
    public Property<Boolean> getIncludeMetrics() {
        return includeMetrics;
    }

    @Optional
    @Input
    public Property<Boolean> getIncludeTrendAnalysis() {
        return includeTrendAnalysis;
    }

    @Optional
    @Input
    public Property<Boolean> getGenerateIndividualFileReports() {
        return generateIndividualFileReports;
    }

    // Output annotations
    @OutputDirectory
    public Property<String> getOutputDirectory() {
        return outputDir;
    }

    @TaskAction
    public void performTask() {
        CoverageAiBuilder builder = CoverageAiBuilder.builder();
        builder.project(this.project);

        // Configure AI model if API key is provided
        if (apiKey.isPresent() && !apiKey.get().isEmpty()) {
            builder.apiKey(apiKey.get());
        }

        if (wanDBApiKey.isPresent()) {
            builder.wanDBApiKey(wanDBApiKey.get());
        }

        if (iterations.isPresent()) {
            builder.iterations(iterations.get());
        }

        if (coverageAiBinaryPath.isPresent()) {
            builder.coverageAiBinaryPath(coverageAiBinaryPath.get());
        }

        if (coverage.isPresent()) {
            builder.coverage(coverage.get());
        }

        // Use comprehensive configuration if available
        if (aiProvider.isPresent() && modelName.isPresent()) {
            builder.aiProvider(aiProvider.get());
            builder.modelName(modelName.get());
        }

        if (temperature.isPresent()) {
            builder.temperature(temperature.get());
        }

        if (maxTokens.isPresent()) {
            builder.maxTokens(maxTokens.get());
        }

        if (enableSuggestions.isPresent()) {
            builder.enableSuggestions(enableSuggestions.get());
        }

        if (maxSuggestionsPerFile.isPresent()) {
            builder.maxSuggestionsPerFile(maxSuggestionsPerFile.get());
        }

        if (outputDir.isPresent()) {
            builder.outputDir(outputDir.get());
        }

        if (reportFormats.isPresent() && !reportFormats.get().isEmpty()) {
            builder.reportFormats(reportFormats.get());
        }

        if (includeTrendAnalysis.isPresent()) {
            builder.includeTrendAnalysis(includeTrendAnalysis.get());
        }

        coverageAi = builder.build();
        coverageAi.init();
        coverageAi.invoke();
    }
}
