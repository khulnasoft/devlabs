package com.khulnasoft.khulnasoftjenkins.model;

import hudson.model.Run;
import hudson.tasks.test.AbstractTestResultAction;
import hudson.tasks.test.TestResult;

import java.util.ArrayList;
import java.util.List;

/**
 * Abstract adapter for test result actions to provide a unified interface
 */
public abstract class AbstractTestResultAdapter<T extends TestResult> {

    /**
     * Get all test results from a build
     * @param build the Jenkins build
     * @param ignoredActions list of action class names to ignore
     * @return list of test results
     */
    public static List<TestResult> getTestResult(Run build, List<String> ignoredActions) {
        List<TestResult> results = new ArrayList<>();

        if (build == null) {
            return results;
        }

        // Check for TestNG results first
        if (ignoredActions == null || !ignoredActions.contains("hudson.plugins.testng.Publisher")) {
            addTestNGResults(build, results);
        }

        // Check for JUnit results
        if (ignoredActions == null || !ignoredActions.contains("hudson.tasks.junit.JUnitResultArchiver")) {
            addJUnitResults(build, results);
        }

        // Check for other test result actions
        addOtherTestResults(build, results, ignoredActions);

        return results;
    }

    private static void addTestNGResults(Run build, List<TestResult> results) {
        try {
            // Try to get TestNG results
            Object testNGAction = build.getAction("hudson.plugins.testng.TestNGTestResultAction");
            if (testNGAction != null) {
                // Use reflection to call getResult() method
                java.lang.reflect.Method getResult = testNGAction.getClass().getMethod("getResult");
                Object result = getResult.invoke(testNGAction);
                if (result instanceof TestResult) {
                    results.add((TestResult) result);
                }
            }
        } catch (Exception e) {
            // TestNG plugin not available or method not found, skip
        }
    }

    private static void addJUnitResults(Run build, List<TestResult> results) {
        AbstractTestResultAction<?> junitAction = build.getAction(AbstractTestResultAction.class);
        if (junitAction != null) {
            results.addAll(junitAction.getFailedTests());
            results.addAll(junitAction.getSkippedTests());
            results.addAll(junitAction.getPassedTests());
        }
    }

    private static void addOtherTestResults(Run build, List<TestResult> results, List<String> ignoredActions) {
        for (Object action : build.getAllActions()) {
            if (action instanceof AbstractTestResultAction) {
                AbstractTestResultAction<?> testAction = (AbstractTestResultAction<?>) action;
                String actionClassName = action.getClass().getName();

                if (ignoredActions != null && ignoredActions.contains(actionClassName)) {
                    continue;
                }

                if (!"hudson.tasks.junit.JUnitResultArchiver".equals(actionClassName) &&
                    !"hudson.plugins.testng.TestNGTestResultAction".equals(actionClassName)) {
                    results.addAll(testAction.getFailedTests());
                    results.addAll(testAction.getSkippedTests());
                    results.addAll(testAction.getPassedTests());
                }
            }
        }
    }
}
