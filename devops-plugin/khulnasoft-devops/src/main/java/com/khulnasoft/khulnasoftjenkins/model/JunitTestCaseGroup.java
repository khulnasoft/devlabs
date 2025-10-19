package com.khulnasoft.khulnasoftjenkins.model;

import hudson.tasks.test.TestResult;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a group of JUnit test cases for batch processing
 */
public class JunitTestCaseGroup {
    private final List<TestResult> testCases = new ArrayList<>();
    private int failures = 0;
    private int passes = 0;
    private int skips = 0;
    private long duration = 0;

    /**
     * Add a test case to this group
     * @param testCase the test case to add
     */
    public void add(TestResult testCase) {
        testCases.add(testCase);

        switch (testCase.getResult()) {
            case FAILURE:
                failures++;
                break;
            case SUCCESS:
                passes++;
                break;
            case SKIPPED:
                skips++;
                break;
        }

        duration += testCase.getDuration();
    }

    /**
     * Get all test cases in this group
     * @return list of test cases
     */
    public List<TestResult> getTestCases() {
        return new ArrayList<>(testCases);
    }

    /**
     * Get the total number of test cases
     * @return total count
     */
    public int getTotal() {
        return testCases.size();
    }

    /**
     * Get the number of failed tests
     * @return failure count
     */
    public int getFailures() {
        return failures;
    }

    /**
     * Get the number of passed tests
     * @return pass count
     */
    public int getPasses() {
        return passes;
    }

    /**
     * Get the number of skipped tests
     * @return skip count
     */
    public int getSkips() {
        return skips;
    }

    /**
     * Get the total duration of all tests in milliseconds
     * @return total duration
     */
    public long getDuration() {
        return duration;
    }

    /**
     * Check if this group is empty
     * @return true if no test cases
     */
    public boolean isEmpty() {
        return testCases.isEmpty();
    }

    @Override
    public String toString() {
        return "JunitTestCaseGroup{" +
                "total=" + getTotal() +
                ", failures=" + failures +
                ", passes=" + passes +
                ", skips=" + skips +
                ", duration=" + duration +
                '}';
    }
}
