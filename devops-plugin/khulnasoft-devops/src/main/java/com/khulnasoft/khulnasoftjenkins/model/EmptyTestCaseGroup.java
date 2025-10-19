package com.khulnasoft.khulnasoftjenkins.model;

/**
 * Represents an empty test case group when no test results are found
 */
public class EmptyTestCaseGroup extends JunitTestCaseGroup {
    private boolean warning = false;

    /**
     * Create an empty test case group
     */
    public EmptyTestCaseGroup() {
        super();
    }

    /**
     * Create an empty test case group with warning flag
     * @param warning true if this represents a warning condition
     */
    public EmptyTestCaseGroup(boolean warning) {
        super();
        this.warning = warning;
    }

    /**
     * Check if this represents a warning condition
     * @return true if warning
     */
    public boolean isWarning() {
        return warning;
    }

    /**
     * Set the warning flag
     * @param warning the warning flag
     */
    public void setWarning(boolean warning) {
        this.warning = warning;
    }

    @Override
    public String toString() {
        return "EmptyTestCaseGroup{" +
                "warning=" + warning +
                "} " + super.toString();
    }
}
