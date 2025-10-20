package com.khulnasoft.buildrunner;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.TimeUnit;

/**
 * Main class for the Build Runner application.
 * This application manages and executes various build tools.
 */
public class BuildRunner {

    private static final String GRADLE_WRAPPER = "./gradlew";
    private static final String GRADLE_CMD = "gradle";

    public static void main(String[] args) {
        System.out.println("Build Runner starting...");

        if (args.length > 0) {
            String command = args[0];
            switch (command) {
                case "gradle":
                    runGradleBuild(args.length > 1 ? args[1] : "build");
                    break;
                case "maven":
                    runMavenBuild(args.length > 1 ? args[1] : "compile");
                    break;
                case "webpack":
                    runWebpackBuild(args.length > 1 ? args[1] : "build");
                    break;
                case "all":
                    runAllBuilds(args.length > 1 ? args[1] : "build");
                    break;
                default:
                    System.out.println("Unknown command: " + command);
                    printUsage();
            }
        } else {
            printUsage();
        }
    }

    private static void runGradleBuild(String task) {
        System.out.println("Running Gradle build task: " + task);
        try {
            // Check if gradlew exists, otherwise use gradle command
            String gradleCommand = Files.exists(Paths.get(GRADLE_WRAPPER)) ? GRADLE_WRAPPER : GRADLE_CMD;

            ProcessBuilder pb = new ProcessBuilder(gradleCommand, task, "--info");
            pb.inheritIO();

            System.out.println("Executing: " + String.join(" ", pb.command()));
            Process process = pb.start();
            int exitCode = process.waitFor();

            if (exitCode == 0) {
                System.out.println("Gradle build completed successfully");
            } else {
                System.err.println("Gradle build failed with exit code: " + exitCode);
                System.exit(exitCode);
            }
        } catch (IOException | InterruptedException e) {
            System.err.println("Error running Gradle build: " + e.getMessage());
            System.exit(1);
        }
    }

    private static void runMavenBuild(String goal) {
        System.out.println("Running Maven goal: " + goal);
        try {
            ProcessBuilder pb = new ProcessBuilder("mvn", goal);
            pb.inheritIO();

            System.out.println("Executing: " + String.join(" ", pb.command()));
            Process process = pb.start();
            int exitCode = process.waitFor();

            if (exitCode == 0) {
                System.out.println("Maven build completed successfully");
            } else {
                System.err.println("Maven build failed with exit code: " + exitCode);
                System.exit(exitCode);
            }
        } catch (IOException | InterruptedException e) {
            System.err.println("Error running Maven build: " + e.getMessage());
            System.exit(1);
        }
    }

    private static void runWebpackBuild(String script) {
        System.out.println("Running Webpack script: " + script);
        try {
            ProcessBuilder pb = new ProcessBuilder("npm", "run", script);
            pb.inheritIO();

            System.out.println("Executing: " + String.join(" ", pb.command()));
            Process process = pb.start();
            int exitCode = process.waitFor();

            if (exitCode == 0) {
                System.out.println("Webpack build completed successfully");
            } else {
                System.err.println("Webpack build failed with exit code: " + exitCode);
                System.exit(exitCode);
            }
        } catch (IOException | InterruptedException e) {
            System.err.println("Error running Webpack build: " + e.getMessage());
            System.exit(1);
        }
    }

    private static void runAllBuilds(String task) {
        System.out.println("Running " + task + " on all build tools...");

        String[] tools = {"gradle", "maven", "webpack"};
        boolean allSuccessful = true;

        for (String tool : tools) {
            System.out.println("\n=== Running " + tool + " " + task + " ===");
            try {
                switch (tool) {
                    case "gradle":
                        runGradleBuild(task);
                        break;
                    case "maven":
                        runMavenBuild(task.equals("build") ? "compile" : task);
                        break;
                    case "webpack":
                        runWebpackBuild(task);
                        break;
                }
            } catch (Exception e) {
                System.err.println("Failed to run " + tool + " build: " + e.getMessage());
                allSuccessful = false;
            }
        }

        if (allSuccessful) {
            System.out.println("\n=== All builds completed successfully ===");
        } else {
            System.err.println("\n=== Some builds failed ===");
            System.exit(1);
        }
    }

    private static void printUsage() {
        System.out.println("Usage: java -jar build-runner.jar [gradle|maven|webpack|all] [task]");
        System.out.println("Commands:");
        System.out.println("  gradle   - Run Gradle build (tasks: build, test, clean, etc.)");
        System.out.println("  maven    - Run Maven build (goals: compile, test, package, etc.)");
        System.out.println("  webpack  - Run Webpack build (scripts: build, dev, test)");
        System.out.println("  all      - Run all build tools");
        System.out.println("Tasks:");
        System.out.println("  build    - Build the project (default)");
        System.out.println("  test     - Run tests");
        System.out.println("  clean    - Clean build artifacts");
        System.out.println("  compile  - Compile source code");
        System.out.println("  package  - Package the application");
    }
}
