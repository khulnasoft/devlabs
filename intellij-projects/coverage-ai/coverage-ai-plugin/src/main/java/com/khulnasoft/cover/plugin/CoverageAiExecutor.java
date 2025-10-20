package com.khulnasoft.cover.plugin;

import org.gradle.api.Action;
import org.gradle.api.Project;
import org.gradle.process.ExecResult;
import org.gradle.process.ExecSpec;

import java.io.ByteArrayOutputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class CoverageAiExecutor {
    public static final String WANDB_API_KEY = "WANDB_API_KEY";
    public static final String OPENAI_API_KEY = "OPENAI_API_KEY";
    private final String coverageAiBinaryPath;
    private final String wanDBApiKey;
    private final String apiKey;
    private final int coverage;
    private final int iterations;

    // New comprehensive configuration fields
    private final Boolean enableSuggestions;
    private final Integer maxSuggestionsPerFile;
    private final List<String> reportFormats;
    private final Boolean includeTrendAnalysis;
    private final Integer retryAttempts;
    private final Integer retryDelayMs;

    private CoverageAiExecutor(Builder builder) {
        this.coverageAiBinaryPath = builder.coverageAiBinaryPath;
        this.wanDBApiKey = builder.wanDBApiKey;
        this.apiKey = builder.apiKey;
        this.coverage = builder.coverage;
        this.iterations = builder.iterations;
        this.enableSuggestions = builder.enableSuggestions;
        this.maxSuggestionsPerFile = builder.maxSuggestionsPerFile;
        this.reportFormats = builder.reportFormats;
        this.includeTrendAnalysis = builder.includeTrendAnalysis;
        this.retryAttempts = builder.retryAttempts;
        this.retryDelayMs = builder.retryDelayMs;
    }

    public String execute(Project project, String sourceFile, String testFile, String jacocoReportPath,
                          String commandString, String projectPath) throws CoverError {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ByteArrayOutputStream errorStream = new ByteArrayOutputStream();
        ExecResult result = project.exec(getExecSpecAction(sourceFile, testFile, jacocoReportPath,
                commandString, projectPath));

        if (result.getExitValue() != 0) {
            String errorOutput = errorStream.toString(StandardCharsets.UTF_8);
            throw new CoverError("An error occurred while executing coverage agent " + result + "\n" + errorOutput);
        }
        String output = "Invalid encoding";
        output = outputStream.toString(StandardCharsets.UTF_8);
        return output;
    }

    private Action<ExecSpec> getExecSpecAction(String sourceFile, String testFile, String jacocoReportPath,
                                               String commandString, String projectPath) {
        return (ExecSpec execSpec) -> {
            if (wanDBApiKey != null && !wanDBApiKey.isEmpty()) {
                execSpec.environment(WANDB_API_KEY, wanDBApiKey);
            } else {
                execSpec.environment(OPENAI_API_KEY, apiKey);
            }

            // Build command line arguments
            List<String> commandLineArgs = new ArrayList<>();
            commandLineArgs.add(coverageAiBinaryPath);
            commandLineArgs.add("--source-file-path=" + sourceFile);
            commandLineArgs.add("--test-file-path=" + testFile);
            commandLineArgs.add("--code-coverage-report-path=" + jacocoReportPath);
            commandLineArgs.add("--test-command=" + commandString);
            commandLineArgs.add("--test-command-dir=" + projectPath);
            commandLineArgs.add("--coverage-type=jacoco");
            commandLineArgs.add("--desired-coverage=" + coverage);
            commandLineArgs.add("--max-iterations=" + iterations);

            // Add new configuration options
            if (enableSuggestions != null) {
                commandLineArgs.add("--enable-suggestions=" + enableSuggestions);
            }

            if (maxSuggestionsPerFile != null) {
                commandLineArgs.add("--max-suggestions-per-file=" + maxSuggestionsPerFile);
            }

            if (reportFormats != null && !reportFormats.isEmpty()) {
                commandLineArgs.add("--report-formats=" + String.join(",", reportFormats));
            }

            if (includeTrendAnalysis != null) {
                commandLineArgs.add("--include-trend-analysis=" + includeTrendAnalysis);
            }

            if (retryAttempts != null) {
                commandLineArgs.add("--retry-attempts=" + retryAttempts);
            }

            if (retryDelayMs != null) {
                commandLineArgs.add("--retry-delay-ms=" + retryDelayMs);
            }

            execSpec.commandLine(commandLineArgs);
            execSpec.setWorkingDir(projectPath);
        };
    }

    public static class Builder {
        private String coverageAiBinaryPath;
        private String wanDBApiKey;
        private String apiKey;
        private int coverage;
        private int iterations;

        // New comprehensive configuration fields
        private Boolean enableSuggestions;
        private Integer maxSuggestionsPerFile;
        private List<String> reportFormats;
        private Boolean includeTrendAnalysis;
        private Integer retryAttempts;
        private Integer retryDelayMs;

        public Builder coverageAiBinaryPath(String coverageAiBinaryPath) {
            this.coverageAiBinaryPath = coverageAiBinaryPath;
            return this;
        }

        public Builder coverage(int coverage) {
            this.coverage = coverage;
            return this;
        }

        public Builder iterations(int iterations) {
            this.iterations = iterations;
            return this;
        }

        public Builder wanDBApiKey(String wanDBApiKey) {
            this.wanDBApiKey = wanDBApiKey;
            return this;
        }

        public Builder apiKey(String apiKey) {
            this.apiKey = apiKey;
            return this;
        }

        // New comprehensive configuration methods
        public Builder enableSuggestions(Boolean enableSuggestions) {
            this.enableSuggestions = enableSuggestions;
            return this;
        }

        public Builder maxSuggestionsPerFile(Integer maxSuggestionsPerFile) {
            this.maxSuggestionsPerFile = maxSuggestionsPerFile;
            return this;
        }

        public Builder reportFormats(List<String> reportFormats) {
            this.reportFormats = reportFormats;
            return this;
        }

        public Builder includeTrendAnalysis(Boolean includeTrendAnalysis) {
            this.includeTrendAnalysis = includeTrendAnalysis;
            return this;
        }

        public Builder retryAttempts(Integer retryAttempts) {
            this.retryAttempts = retryAttempts;
            return this;
        }

        public Builder retryDelayMs(Integer retryDelayMs) {
            this.retryDelayMs = retryDelayMs;
            return this;
        }

        public CoverageAiExecutor build() {
            return new CoverageAiExecutor(this);
        }
    }
}
