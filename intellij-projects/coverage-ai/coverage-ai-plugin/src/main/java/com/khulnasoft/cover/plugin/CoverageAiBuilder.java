package com.khulnasoft.cover.plugin;

import dev.langchain4j.model.openai.OpenAiChatModel;
import org.gradle.api.Project;

import java.util.List;
import java.util.Optional;

public class CoverageAiBuilder {
    private String apiKey;
    private String wanDBApiKey;
    private int iterations;
    private int coverage;
    private String coverageAiBinaryPath;
    private ModelPrompter modelPrompter;
    private Optional<String> javaClassPath = Optional.empty();
    private Optional<String> javaTestClassPath = Optional.empty();
    private String projectPath;
    private Optional<String> javaClassDir = Optional.empty();
    private String buildDirectory;
    private CoverageAiExecutor coverageAiExecutor;
    private Project project;
    private OpenAiChatModel.OpenAiChatModelBuilder openAiChatModelBuilder;

    // New comprehensive configuration properties
    private String aiProvider;
    private String modelName;
    private Double temperature;
    private Integer maxTokens;
    private Integer timeoutSeconds;
    private Integer retryAttempts;
    private Integer retryDelayMs;
    private Boolean enableSuggestions;
    private Integer maxSuggestionsPerFile;
    private String outputDir;
    private List<String> reportFormats;
    private Boolean includeTrendAnalysis;

    public static CoverageAiBuilder builder() {
        return new CoverageAiBuilder();
    }

    public CoverageAiBuilder apiKey(String apiKey) {
        this.apiKey = apiKey;
        return this;
    }

    public CoverageAiBuilder wanDBApiKey(String wanDBApiKey) {
        this.wanDBApiKey = wanDBApiKey;
        return this;
    }

    public CoverageAiBuilder iterations(int iterations) {
        this.iterations = iterations;
        return this;
    }

    public CoverageAiBuilder coverage(int coverage) {
        this.coverage = coverage;
        return this;
    }

    public CoverageAiBuilder coverageAiBinaryPath(String coverageAiBinaryPath) {
        this.coverageAiBinaryPath = coverageAiBinaryPath;
        return this;
    }

    public CoverageAiBuilder modelPrompter(ModelPrompter modelPrompter) {
        this.modelPrompter = modelPrompter;
        return this;
    }

    public CoverageAiBuilder javaClassPath(Optional<String> javaClassPath) {
        this.javaClassPath = javaClassPath;
        return this;
    }

    public CoverageAiBuilder javaTestClassPath(Optional<String> javaTestClassPath) {
        this.javaTestClassPath = javaTestClassPath;
        return this;
    }

    public CoverageAiBuilder projectPath(String projectPath) {
        this.projectPath = projectPath;
        return this;
    }

    public CoverageAiBuilder javaClassDir(Optional<String> javaClassDir) {
        this.javaClassDir = javaClassDir;
        return this;
    }

    public CoverageAiBuilder buildDirectory(String buildDirectory) {
        this.buildDirectory = buildDirectory;
        return this;
    }

    public CoverageAiBuilder coverageAiExecutor(CoverageAiExecutor coverageAiExecutor) {
        this.coverageAiExecutor = coverageAiExecutor;
        return this;
    }

    public CoverageAiBuilder project(Project project) {
        this.project = project;
        return this;
    }

    public CoverageAiBuilder openAiChatModelBuilder(OpenAiChatModel.OpenAiChatModelBuilder openAiChatModelBuilder) {
        this.openAiChatModelBuilder = openAiChatModelBuilder;
        return this;
    }

    // New comprehensive configuration methods
    public CoverageAiBuilder aiProvider(String aiProvider) {
        this.aiProvider = aiProvider;
        return this;
    }

    public CoverageAiBuilder modelName(String modelName) {
        this.modelName = modelName;
        return this;
    }

    public CoverageAiBuilder temperature(Double temperature) {
        this.temperature = temperature;
        return this;
    }

    public CoverageAiBuilder maxTokens(Integer maxTokens) {
        this.maxTokens = maxTokens;
        return this;
    }

    public CoverageAiBuilder timeoutSeconds(Integer timeoutSeconds) {
        this.timeoutSeconds = timeoutSeconds;
        return this;
    }

    public CoverageAiBuilder retryAttempts(Integer retryAttempts) {
        this.retryAttempts = retryAttempts;
        return this;
    }

    public CoverageAiBuilder retryDelayMs(Integer retryDelayMs) {
        this.retryDelayMs = retryDelayMs;
        return this;
    }

    public CoverageAiBuilder enableSuggestions(Boolean enableSuggestions) {
        this.enableSuggestions = enableSuggestions;
        return this;
    }

    public CoverageAiBuilder maxSuggestionsPerFile(Integer maxSuggestionsPerFile) {
        this.maxSuggestionsPerFile = maxSuggestionsPerFile;
        return this;
    }

    public CoverageAiBuilder outputDir(String outputDir) {
        this.outputDir = outputDir;
        return this;
    }

    public CoverageAiBuilder reportFormats(List<String> reportFormats) {
        this.reportFormats = reportFormats;
        return this;
    }

    public CoverageAiBuilder includeTrendAnalysis(Boolean includeTrendAnalysis) {
        this.includeTrendAnalysis = includeTrendAnalysis;
        return this;
    }

    public CoverageAi build() {
        return new CoverageAi(this);
    }

    public OpenAiChatModel.OpenAiChatModelBuilder openAiChatModelBuilder() {
        return openAiChatModelBuilder;
    }

    // Legacy getters
    public String getApiKey() {
        return apiKey;
    }

    public String getWanDBApiKey() {
        return wanDBApiKey;
    }

    public int getIterations() {
        return iterations;
    }

    public int getCoverage() {
        return coverage;
    }

    public String getCoverageAiBinaryPath() {
        return coverageAiBinaryPath;
    }

    public ModelPrompter getModelPrompter() {
        return modelPrompter;
    }

    public Optional<String> getJavaClassPath() {
        return javaClassPath;
    }

    public Optional<String> getJavaTestClassPath() {
        return javaTestClassPath;
    }

    public String getProjectPath() {
        return projectPath;
    }

    public Optional<String> getJavaClassDir() {
        return javaClassDir;
    }

    public String getBuildDirectory() {
        return buildDirectory;
    }

    public CoverageAiExecutor getCoverageAiExecutor() {
        return coverageAiExecutor;
    }

    public Project getProject() {
        return project;
    }

    // New comprehensive configuration getters
    public String getAiProvider() {
        return aiProvider;
    }

    public String getModelName() {
        return modelName;
    }

    public Double getTemperature() {
        return temperature;
    }

    public Integer getMaxTokens() {
        return maxTokens;
    }

    public Integer getTimeoutSeconds() {
        return timeoutSeconds;
    }

    public Integer getRetryAttempts() {
        return retryAttempts;
    }

    public Integer getRetryDelayMs() {
        return retryDelayMs;
    }

    public Boolean getEnableSuggestions() {
        return enableSuggestions;
    }

    public Integer getMaxSuggestionsPerFile() {
        return maxSuggestionsPerFile;
    }

    public String getOutputDir() {
        return outputDir;
    }

    public List<String> getReportFormats() {
        return reportFormats;
    }

    public Boolean getIncludeTrendAnalysis() {
        return includeTrendAnalysis;
    }
}
