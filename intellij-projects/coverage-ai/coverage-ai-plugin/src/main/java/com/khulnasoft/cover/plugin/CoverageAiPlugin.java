package com.khulnasoft.cover.plugin;


import org.gradle.api.Plugin;
import org.gradle.api.Project;

public class CoverageAiPlugin implements Plugin<Project> {
    static final Integer DEFAULT_ITERATIONS = Integer.valueOf(1);
    static final Integer DEFAULT_PERCENTAGE = Integer.valueOf(75);

    @Override
    public void apply(Project project) {
        project.getLogger().info("Running plugin version {}", "0.0.2");
        project.getExtensions().create("coverageAi", CoverageAiExtension.class, project);

        CoverageAiExtension extension = project.getExtensions().findByType(CoverageAiExtension.class);
        if (extension == null) {
            extension = project.getExtensions().create("coverageAi", CoverageAiExtension.class, project);
        }

        // Main coverage AI task
        project.getTasks().register("coverageAiTask", CoverageAiTask.class, task -> {
            task.setGroup("verification");
            task.setDescription("Runs the coverage ai task attempting to increase code coverage");
            if (extension != null) {
                task.getApiKey().set(extension.getApiKey());
                task.getWanDBApiKey().set(extension.getWanDBApiKey());
                task.getCoverageAiBinaryPath().set(extension.getCoverageAiBinaryPath());
                if (extension.getIterations().isPresent()) {
                    task.getIterations().set(extension.getIterations());
                } else {
                    task.getIterations().set(DEFAULT_ITERATIONS);
                }
                if (extension.getCoverage().isPresent()) {
                    task.getCoverage().set(extension.getCoverage());
                } else {
                    task.getCoverage().set(DEFAULT_PERCENTAGE);
                }
            }
            task.getOutputs().upToDateWhen(t -> false);
        });

        // Analyze coverage task
        project.getTasks().register("analyzeCoverage", CoverageAiTask.class, task -> {
            task.setGroup("verification");
            task.setDescription("Analyze test coverage and generate AI suggestions");
            task.getDependsOn().add("test");
            task.getDependsOn().add("jacocoTestReport");
            if (extension != null) {
                task.getApiKey().set(extension.getApiKey());
                task.getIterations().set(extension.getMaxSuggestionsPerFile());
                task.getCoverage().set((int) (extension.getCoverageThreshold().get() * 100));
            }
            task.getOutputs().dir(project.file(extension != null ? extension.getOutputDir().get() : "build/coverage-ai-reports"));
        });

        // Generate suggestions task
        project.getTasks().register("generateSuggestions", CoverageAiTask.class, task -> {
            task.setGroup("verification");
            task.setDescription("Generate AI-powered test suggestions based on coverage analysis");
            task.getDependsOn().add("analyzeCoverage");
            if (extension != null) {
                task.getApiKey().set(extension.getApiKey());
                task.getIterations().set(extension.getMaxSuggestionsPerFile());
                task.getEnableSuggestions().set(extension.getEnableSuggestions());
            }
        });

        // Export report task
        project.getTasks().register("exportReport", CoverageAiTask.class, task -> {
            task.setGroup("reporting");
            task.setDescription("Export coverage analysis report in specified format");
            if (extension != null) {
                task.getReportFormats().set(extension.getReportFormats());
                task.getOutputDir().set(extension.getOutputDir());
                task.getIncludeSourceCode().set(extension.getIncludeSourceCode());
                task.getIncludeRecommendations().set(extension.getIncludeRecommendations());
                task.getIncludeMetrics().set(extension.getIncludeMetrics());
                task.getIncludeTrendAnalysis().set(extension.getIncludeTrendAnalysis());
                task.getGenerateIndividualFileReports().set(extension.getGenerateIndividualFileReports());
            }
            task.getOutputs().file(project.file(extension != null ?
                extension.getOutputDir().get() + "/coverage-ai-report.zip" : "build/distributions/coverage-ai-report.zip"));
        });

        // Check coverage task
        project.getTasks().register("checkCoverage", CoverageAiTask.class, task -> {
            task.setGroup("verification");
            task.setDescription("Verify that coverage meets configured thresholds");
            task.getDependsOn().add("analyzeCoverage");
            if (extension != null) {
                task.getCoverageThreshold().set(extension.getCoverageThreshold());
                task.getBranchCoverageThreshold().set(extension.getBranchCoverageThreshold());
                task.getMethodCoverageThreshold().set(extension.getMethodCoverageThreshold());
                task.getClassCoverageThreshold().set(extension.getClassCoverageThreshold());
            }
        });

        // Baseline coverage task
        project.getTasks().register("baselineCoverage", CoverageAiTask.class, task -> {
            task.setGroup("verification");
            task.setDescription("Establish baseline coverage for trend analysis");
            task.getDependsOn().add("analyzeCoverage");
            if (extension != null) {
                task.getIncludeTrendAnalysis().set(true);
                task.getOutputDir().set(extension.getOutputDir());
            }
        });

        // Make analyzeCoverage the default task for check
        project.getTasks().named("check", task -> {
            task.dependsOn("analyzeCoverage");
        });
    }
}
