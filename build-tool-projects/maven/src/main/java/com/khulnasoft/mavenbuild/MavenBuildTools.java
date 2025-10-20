package com.khulnasoft.mavenbuild;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Maven Build Tools main class.
 * Provides build management capabilities using Maven.
 */
public class MavenBuildTools {

    private static final String MAVEN_WRAPPER = "./mvnw";
    private static final String MAVEN_CMD = "mvn";

    public static void main(String[] args) {
        System.out.println("Maven Build Tools starting...");

        if (args.length > 0) {
            String command = args[0];
            switch (command) {
                case "compile":
                    compileProject();
                    break;
                case "test":
                    runTests();
                    break;
                case "package":
                    packageProject();
                    break;
                case "install":
                    installProject();
                    break;
                case "clean":
                    cleanProject();
                    break;
                case "verify":
                    verifyProject();
                    break;
                case "deploy":
                    deployProject();
                    break;
                default:
                    System.out.println("Unknown command: " + command);
                    printUsage();
            }
        } else {
            printUsage();
        }
    }

    private static void compileProject() {
        System.out.println("Compiling Maven project...");
        executeMavenCommand("compile");
    }

    private static void runTests() {
        System.out.println("Running Maven tests...");
        executeMavenCommand("test");
    }

    private static void packageProject() {
        System.out.println("Packaging Maven project...");
        executeMavenCommand("package");
    }

    private static void installProject() {
        System.out.println("Installing Maven project to local repository...");
        executeMavenCommand("install");
    }

    private static void cleanProject() {
        System.out.println("Cleaning Maven project...");
        executeMavenCommand("clean");
    }

    private static void verifyProject() {
        System.out.println("Verifying Maven project...");
        executeMavenCommand("verify");
    }

    private static void deployProject() {
        System.out.println("Deploying Maven project...");
        executeMavenCommand("deploy");
    }

    private static void executeMavenCommand(String goal) {
        try {
            // Check if mvnw exists, otherwise use mvn command
            String mavenCommand = Files.exists(Paths.get(MAVEN_WRAPPER)) ? MAVEN_WRAPPER : MAVEN_CMD;

            ProcessBuilder pb = new ProcessBuilder(mavenCommand, goal, "-q");
            pb.inheritIO();

            System.out.println("Executing: " + String.join(" ", pb.command()));
            Process process = pb.start();
            int exitCode = process.waitFor();

            if (exitCode == 0) {
                System.out.println("Maven " + goal + " completed successfully");
            } else {
                System.err.println("Maven " + goal + " failed with exit code: " + exitCode);
                System.exit(exitCode);
            }
        } catch (IOException | InterruptedException e) {
            System.err.println("Error running Maven " + goal + ": " + e.getMessage());
            System.exit(1);
        }
    }

    private static void printUsage() {
        System.out.println("Usage: mvn exec:java -Dexec.mainClass=\"com.khulnasoft.mavenbuild.MavenBuildTools\" -Dexec.args=\"[command]\"");
        System.out.println("Commands:");
        System.out.println("  compile  - Compile the source code");
        System.out.println("  test     - Run the tests");
        System.out.println("  package  - Package the compiled code");
        System.out.println("  install  - Install the package to local repository");
        System.out.println("  clean    - Clean previous build artifacts");
        System.out.println("  verify   - Run integration tests and verify package");
        System.out.println("  deploy   - Deploy the package to remote repository");
    }
}
