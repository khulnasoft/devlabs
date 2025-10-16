# AI-Enhanced Spock Testing Framework

This project demonstrates an AI-powered testing framework that enhances the Spock testing experience with intelligent automation, natural language test generation, and comprehensive test analytics.

## 🚀 Features

### 🤖 AI-Powered Test Generation
- **Natural Language → Test Spec Pipeline**: Convert plain English requirements into Spock test specifications
- **Intelligent Test Classification**: Automatically classify tests as Unit, Integration, Functional, Performance, or Security
- **AI-Assisted Failure Analysis**: Get intelligent suggestions when tests fail

### 📊 Test Analytics Dashboard
- **Comprehensive Reporting**: Generate detailed test analytics in JSON, HTML, and CSV formats
- **Performance Analysis**: Track test execution times and identify bottlenecks
- **Failure Pattern Analysis**: Identify common failure patterns and trends

### 🏭 Reusable Test DSL
- **Common Testing Patterns**: Pre-built utilities for data generation, validation, and assertions
- **Performance Testing Utilities**: Built-in performance measurement and validation tools
- **Mock and Stub Helpers**: Simplified mocking and stubbing for tests

### 🐳 Docker Integration
- **Containerized Selenium Tests**: Run Selenium WebDriver tests in isolated Docker environments
- **Multi-Browser Testing**: Support for Chrome, Firefox, and cross-browser test execution
- **CI/CD Ready**: Pre-configured for automated testing pipelines

## 📁 Project Structure

```
ai-spock-groovy-combo/
├── src/
│   ├── main/
│   │   ├── java/com/khulnasoft/spock/
│   │   │   ├── OddEvenCamp.java              # Interface for number classification
│   │   │   ├── OddEvenCampImpl.java          # Implementation
│   │   │   ├── validation/                   # Validation framework
│   │   │   │   ├── Validator.java
│   │   │   │   └── NegativeChecker.java
│   │   │   └── ai/                           # AI-enhanced testing framework
│   │   │       ├── AiTestGenerator.java      # AI test generation engine
│   │   │       ├── NaturalLanguageTestPipeline.java  # NL → Test conversion
│   │   │       ├── TestClassifier.java       # Automatic test classification
│   │   │       ├── TestFailureAnalyzer.java  # AI failure analysis
│   │   │       └── TestAnalyticsDashboard.java  # Test analytics generator
│   │   └── resources/
│   │       └── test-dsl/
│   │           └── test-dsl.groovy           # Reusable testing utilities
│   └── test/
│       └── groovy/com/khulnasoft/spock/
│           └── OddEvenCampAiSpec.groovy      # AI-enhanced test examples
├── requirements.txt                          # Natural language test requirements
├── SpockConfig.groovy                        # Spock configuration for AI features
├── Dockerfile.selenium-tests                 # Docker setup for Selenium testing
└── docker-compose.selenium.yml               # Docker Compose for test orchestration
```

## 🛠️ Getting Started

### Prerequisites
- Java 21 or higher
- Gradle 8.10.2 or higher
- Docker (for Selenium tests)
- Chrome/Chromium browser (for Selenium tests)

### Basic Setup
```bash
# Clone and navigate to the project
cd intellij-projects/ai-spock-groovy-combo

# Build the project
./gradlew build

# Run all tests
./gradlew test

# Run with AI enhancements
./gradlew test --info
```

## 🎯 AI-Enhanced Testing Features

### 1. Natural Language Test Generation

Create a `requirements.txt` file with your test requirements:

```txt
## Feature: User Authentication
As a user, I want to authenticate so that I can access protected resources.

### Scenario: Successful Login
Given that I have valid credentials
When I submit the login form
Then I should be authenticated and redirected to the dashboard
```

Generate test specifications:
```bash
./gradlew generateTestSpec
```

### 2. Automatic Test Classification

Classify your tests and get detailed analysis:
```bash
./gradlew classifyTests
```

### 3. AI-Assisted Failure Analysis

Analyze test failures with intelligent suggestions:
```bash
./gradlew analyzeTestFailures
```

### 4. Test Analytics Dashboard

Generate comprehensive test analytics:
```bash
./gradlew generateTestAnalytics
```

### 5. Dockerized Selenium Testing

Run Selenium tests in Docker containers:
```bash
# Start the test environment
docker-compose -f docker-compose.selenium.yml up -d

# Run Selenium tests
docker-compose -f docker-compose.selenium.yml up test-runner

# View test reports
open build/reports/test-analytics-*.html
```

## 📚 Usage Examples

### Writing AI-Enhanced Tests

```groovy
package com.khulnasoft.spock

import com.khulnasoft.spock.ai.TestClassifier
import spock.lang.Specification
import spock.lang.Subject

class MyServiceAiSpec extends Specification {

    @Subject
    MyService service = new MyService()

    def "AI-classified unit test"() {
        given: "Test data using DSL"
        def user = userData(id: 1, name: "John Doe", active: true)

        when: "Service method is called"
        def result = service.processUser(user)

        then: "Expected result"
        result.success == true
        result.user == user
    }

    def "Performance test with AI analysis"() {
        given: "Large dataset"
        def largeDataset = (1..10000).collect { it }

        when: "Processing with performance measurement"
        def (result, duration) = measureTime {
            service.processBatch(largeDataset)
        }

        then: "Performance requirements met"
        assertPerformance(5000) {  // Max 5 seconds
            service.processBatch(largeDataset)
        }
        duration < 5000
    }

    def "AI-powered failure analysis demo"() {
        given: "Test classifier"
        def classifier = new TestClassifier()

        when: "Analyzing current test"
        def classification = classifier.classifyTest(
            "AI-classified unit test",
            "Unit test for positive case verification"
        )

        then: "Correctly classified"
        classification.testType.toString() == "UNIT"
        classification.confidence > 0.7
    }
}
```

### Using the Test DSL

```groovy
// Generate test data
def user = userData(name: "Alice", email: "alice@example.com")

// Validate data
validateUser(user)

// Mock external services
def mockService = createMock(ExternalService)

// Performance testing
def (result, duration) = measureTime {
    service.processData(largeDataset)
}

// Assert performance
assertPerformance(1000) {
    service.processData(largeDataset)
}
```

## 🔧 Configuration

### Spock Configuration (`SpockConfig.groovy`)

```groovy
spock {
    report {
        issueNamePrefix 'Issue #'
        issueUrlPrefix 'https://github.com/your-org/your-repo/issues/'
    }
}

performance {
    slowTestThreshold = 1000
    maxParallelForks = Runtime.runtime.availableProcessors()
}
```

### Gradle Build Configuration

The project includes enhanced Gradle configuration with:
- **Parallel test execution** for faster builds
- **Comprehensive reporting** (JUnit XML, HTML, JaCoCo coverage)
- **Code quality checks** (SpotBugs, Checkstyle)
- **Custom AI testing tasks**

## 🐳 Docker Integration

### Running Selenium Tests

```bash
# Start the complete test environment
docker-compose -f docker-compose.selenium.yml up -d

# Run tests in container
docker-compose -f docker-compose.selenium.yml up test-runner

# Run with different browsers
docker-compose -f docker-compose.selenium.yml --profile firefox up test-runner

# Clean up
docker-compose -f docker-compose.selenium.yml down -v
```

### Custom Docker Images

- **`selenium-runner`**: Base image for running Selenium tests
- **`development`**: Development environment with live reloading
- **`production`**: Optimized for production test execution
- **`ci`**: CI/CD optimized with analytics generation
- **`parallel`**: Parallel test execution configuration

## 📊 Test Analytics

### Generated Reports

The framework generates multiple report formats:

- **JSON**: Machine-readable analytics data
- **HTML**: Interactive dashboard with charts and visualizations
- **CSV**: Tabular data for spreadsheet analysis

### Analytics Metrics

- **Test execution statistics** (pass/fail rates, duration)
- **Performance analysis** (slowest/fastest tests, trends)
- **Failure pattern analysis** (common failure types, root causes)
- **Test classification breakdown** (unit vs integration vs functional)
- **Coverage analysis** (code coverage trends and gaps)

## 🚀 CI/CD Integration

The project includes GitHub Actions workflows for:

- **Automated testing** across all project modules
- **Multi-browser Selenium testing** in Docker
- **AI-powered PR reviews** with intelligent feedback
- **Test analytics and reporting** in CI/CD pipelines
- **Performance regression detection**

## 🤝 Contributing

1. **Write tests first** using the AI-enhanced framework
2. **Use natural language requirements** in `requirements.txt`
3. **Leverage the test DSL** for common patterns
4. **Generate analytics** to ensure test quality
5. **Run full test suite** before submitting changes

## 📈 Roadmap

- **Phase 3**: Spring Boot microservices refactoring
- **Phase 4**: AI-powered tooling and plugins
- **Phase 5**: Developer experience improvements
- **Phase 6**: Documentation and tutorials
- **Phase 7**: Release and distribution

## 🔗 Related Projects

- **khulnasoft/pr-insight**: AI-powered PR review tool
- **Spring Boot Refactoring**: Live coding demonstrations
- **Selenium Testing Suite**: Browser automation framework
- **Coverage AI Plugin**: Intelligent test coverage analysis

## 📝 License

This project is part of the KhulnaSoft DevLabs monorepo and follows the same license terms.

---

**Happy Testing with AI! 🤖✨**
