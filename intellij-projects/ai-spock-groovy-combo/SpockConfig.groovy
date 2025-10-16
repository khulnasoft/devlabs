// Spock Configuration for AI-Enhanced Testing
// This file configures Spock behavior for the AI testing framework

import org.spockframework.runtime.extension.ExtensionAnnotation
import org.spockframework.runtime.extension.IAnnotationDrivenExtension
import org.spockframework.runtime.model.SpecInfo

// Enable AI-powered test features
spock {
    // Report configuration
    report {
        issueNamePrefix 'Issue #'
        issueUrlPrefix 'https://github.com/khulnasoft/devlabs/issues/'
    }

    // Filter configuration for AI test generation
    filter {
        // Include tests with AI annotations
        include *Spec, *Test
    }
}

// Custom extension for AI test classification
@ExtensionAnnotation(AiTestClassificationExtension)
@interface AiTestClassification {
    String value() default ""
}

// Extension implementation
class AiTestClassificationExtension implements IAnnotationDrivenExtension<AiTestClassification> {

    @Override
    void visitSpecAnnotation(AiTestClassification annotation, SpecInfo spec) {
        // Add AI classification behavior to the spec
        spec.addListener(new AiTestListener())
    }
}

// Listener for AI-powered test execution
class AiTestListener extends AbstractRunListener {

    @Override
    void beforeSpec(SpecInfo spec) {
        println "🤖 AI: Analyzing test specification: ${spec.name}"
    }

    @Override
    void beforeFeature(FeatureInfo feature) {
        println "🤖 AI: Processing test feature: ${feature.name}"
    }

    @Override
    void afterFeature(FeatureInfo feature, IterationInfo iteration) {
        if (iteration.getFeature().isFailed()) {
            println "🤖 AI: Test failed - ${feature.name}. Generating failure analysis..."
            // In a real implementation, this would trigger the failure analyzer
        }
    }
}

// Test data providers for parameterized tests
dataProviders {
    // Common test data sets
    positiveNumbers {
        [1, 2, 4, 8, 16, 32, 64, 100, 1000]
    }

    negativeNumbers {
        [-1, -2, -4, -8, -16, -32, -64, -100, -1000]
    }

    boundaryNumbers {
        [0, 1, -1, Integer.MAX_VALUE, Integer.MIN_VALUE]
    }

    oddNumbers {
        [1, 3, 5, 7, 9, 11, 13, 15, 17, 19]
    }

    evenNumbers {
        [0, 2, 4, 6, 8, 10, 12, 14, 16, 18, 20]
    }
}

// Performance testing configuration
performance {
    // Thresholds for performance tests
    slowTestThreshold = 1000 // milliseconds
    verySlowTestThreshold = 5000 // milliseconds

    // Parallel execution settings
    maxParallelForks = Runtime.runtime.availableProcessors()
    forkEvery = 100 // tests per fork
}

// Mock configuration for AI-generated tests
mocks {
    // Default mock behaviors for common interfaces
    defaultAnswer = 'EMPTY'
    verboseLogging = false
}

// Logging configuration for AI test analysis
logging {
    // Log AI processing steps
    aiProcessing = 'INFO'

    // Log test classification results
    testClassification = 'DEBUG'

    // Log performance metrics
    performanceMetrics = 'INFO'
}
