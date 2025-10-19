package com.khulnasoft.buildrunner;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class BuildRunnerTest {

    @Test
    void testMainMethod() {
        // This is a basic test to ensure the class can be instantiated
        assertDoesNotThrow(() -> {
            String[] args = {};
            BuildRunner.main(args);
        });
    }
}
