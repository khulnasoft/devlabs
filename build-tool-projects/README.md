# Build Tool Projects

This directory contains comprehensive example configurations and implementations for various build tools commonly used in software development projects. Each project demonstrates best practices, proper structure, and real-world usage patterns.

## Structure

```
build-tool-projects/
├── build-runner/          # Enhanced build orchestration scripts
│   ├── build-runner.sh    # Advanced multi-tool build manager
│   └── .gitignore         # Build runner specific ignores
├── gradle/                # Complete Gradle build tool example
│   ├── build.gradle       # Comprehensive Gradle configuration
│   ├── settings.gradle    # Gradle project settings
│   ├── gradlew            # Gradle wrapper
│   ├── src/               # Complete application source code
│   └── .gitignore         # Gradle specific ignores
├── maven/                 # Complete Maven build tool example
│   ├── pom.xml            # Comprehensive Maven configuration
│   ├── src/               # Complete application source code
│   └── .gitignore         # Maven specific ignores
├── webpack/               # Complete Webpack build tool example
│   ├── webpack.config.js  # Advanced Webpack configuration
│   ├── package.json       # NPM dependencies and scripts
│   ├── src/               # Complete frontend application
│   └── .gitignore         # Webpack specific ignores
└── README.md              # This comprehensive documentation
```

## Build Tools Overview

### Gradle
- **Language**: Java/Groovy/Kotlin
- **Use Case**: Multi-language builds, dependency management, enterprise applications
- **Example**: Calculator application with comprehensive test suite
- **Features**:
  - JUnit 5 testing framework
  - JaCoCo code coverage
  - Application plugin for runnable JARs
  - Gradle wrapper for consistent builds

### Maven
- **Language**: Java
- **Use Case**: Java project management, dependency resolution, enterprise applications
- **Example**: File processing utility with comprehensive test suite
- **Features**:
  - JUnit 5 testing framework
  - Maven Surefire plugin for testing
  - Standard directory layout
  - Dependency management

### Webpack
- **Language**: JavaScript/Node.js
- **Use Case**: Frontend bundling, asset management, modern web applications
- **Example**: Interactive web application with API integration
- **Features**:
  - Babel for ES6+ transpilation
  - CSS loading and processing
  - Development server with hot reload
  - HTML template generation
  - Bundle analysis and optimization
  - Code linting and formatting

## Enhanced Build Runner Script

The `build-runner.sh` script provides a powerful, unified interface to run builds across different tools with advanced features:

### Features
- **Colored Output**: Visual distinction between info, success, warnings, and errors
- **Comprehensive Logging**: All operations logged to `build-runner.log`
- **Error Handling**: Robust error checking and graceful failure handling
- **Command Validation**: Validates commands before execution
- **Directory Checking**: Ensures project directories exist
- **Tool Detection**: Verifies required tools are installed
- **Batch Operations**: Run commands across all tools simultaneously

### Usage

```bash
# Basic commands
./build-runner.sh gradle build
./build-runner.sh maven test
./build-runner.sh webpack build

# Run all tools with a single command
./build-runner.sh all build
./build-runner.sh all test
./build-runner.sh all clean

# Advanced commands
./build-runner.sh gradle analyze    # Generate build scan
./build-runner.sh maven install     # Install to local repository
./build-runner.sh webpack format    # Format code with Prettier
./build-runner.sh webpack lint      # Lint code with ESLint

# Utility options
./build-runner.sh --help           # Show help
./build-runner.sh --log            # Show log file location
./build-runner.sh --clean-log      # Clear log file
```

## Individual Tool Usage

### Gradle

#### Prerequisites
- Java 11 or higher
- Gradle (or use the included wrapper)

#### Commands
```bash
cd gradle

# Build the project
gradle build
./gradlew build

# Run tests
gradle test
./gradlew test

# Run the application
gradle run
./gradlew run

# Clean build artifacts
gradle clean

# Generate build scan (if enabled)
gradle build --scan
```

#### Project Structure
```
gradle/
├── src/main/java/com/khulnasoft/buildrunner/
│   ├── BuildRunner.java      # Main application class
│   └── Calculator.java       # Calculator utility class
└── src/test/java/com/khulnasoft/buildrunner/
    ├── BuildRunnerTest.java  # Basic application tests
    └── CalculatorTest.java   # Comprehensive calculator tests
```

### Maven

#### Prerequisites
- Java 11 or higher
- Maven 3.6 or higher

#### Commands
```bash
cd maven

# Compile the project
mvn compile

# Run tests
mvn test

# Package the application
mvn package

# Install to local repository
mvn install

# Clean build artifacts
mvn clean

# Generate site documentation
mvn site
```

#### Project Structure
```
maven/
├── src/main/java/com/khulnasoft/mavenbuild/
│   ├── MavenBuildTools.java  # Main application class
│   └── FileProcessor.java    # File processing utility
└── src/test/java/com/khulnasoft/mavenbuild/
    └── FileProcessorTest.java # Comprehensive file processing tests
```

### Webpack

#### Prerequisites
- Node.js 14 or higher
- npm or yarn

#### Commands
```bash
cd webpack

# Install dependencies
npm install

# Build for production
npm run build

# Start development server
npm run dev

# Run tests (if configured)
npm test

# Clean build artifacts
npm run clean

# Lint code
npm run lint

# Format code
npm run format

# Analyze bundle
npm run analyze
```

#### Project Structure
```
webpack/
├── src/
│   ├── app.js              # Main application logic
│   ├── utils.js            # Utility functions
│   ├── dataService.js      # API data service
│   ├── styles.css          # Application styles
│   └── index.html          # HTML template
├── dist/                   # Build output (generated)
├── webpack.config.js       # Webpack configuration
└── package.json           # Dependencies and scripts
```

## Advanced Features

### Gradle Project Features
- **Comprehensive Calculator Class**: Full arithmetic operations with error handling
- **Extensive Test Suite**: 100% test coverage with edge cases
- **Modern Java Features**: Uses Java 11+ features and best practices
- **Gradle Wrapper**: Ensures consistent builds across environments

### Maven Project Features
- **File Processing Utilities**: Real-world file manipulation capabilities
- **Comprehensive Testing**: Edge case testing and error scenarios
- **Standard Maven Structure**: Follows Maven conventions and best practices
- **JAR Packaging**: Produces executable JAR files

### Webpack Project Features
- **Modern JavaScript Application**: ES6+ features with Babel transpilation
- **Interactive UI**: Bootstrap-styled responsive interface
- **API Integration**: Fetches data from external APIs
- **Development Tools**: Hot reload, linting, formatting, bundle analysis
- **Production Optimization**: Code splitting, minification, and optimization

## Configuration Details

### Gradle Configuration (`build.gradle`)
```gradle
plugins {
    id 'java'
    id 'application'
    id 'jacoco'  // Code coverage
}

java {
    sourceCompatibility = JavaVersion.VERSION_11
    targetCompatibility = JavaVersion.VERSION_11
}

application {
    mainClass = 'com.khulnasoft.buildrunner.BuildRunner'
}

dependencies {
    testImplementation 'org.junit.jupiter:junit-jupiter-api:5.8.2'
    testRuntimeOnly 'org.junit.jupiter:junit-jupiter-engine:5.8.2'
}

tasks.named('test') {
    useJUnitPlatform()
    finalizedBy jacocoTestReport
}
```

### Maven Configuration (`pom.xml`)
```xml
<properties>
    <maven.compiler.source>11</maven.compiler.source>
    <maven.compiler.target>11</maven.compiler.target>
</properties>

<dependencies>
    <dependency>
        <groupId>org.junit.jupiter</groupId>
        <artifactId>junit-jupiter-api</artifactId>
        <version>5.8.2</version>
        <scope>test</scope>
    </dependency>
</dependencies>

<build>
    <plugins>
        <plugin>
            <groupId>org.apache.maven.plugins</groupId>
            <artifactId>maven-surefire-plugin</artifactId>
            <version>3.0.0-M7</version>
        </plugin>
    </plugins>
</build>
```

### Webpack Configuration (`webpack.config.js`)
```javascript
const path = require('path');
const HtmlWebpackPlugin = require('html-webpack-plugin');

module.exports = {
  entry: './src/app.js',
  output: {
    filename: 'bundle.js',
    path: path.resolve(__dirname, 'dist'),
    clean: true,
  },
  module: {
    rules: [
      {
        test: /\.js$/,
        exclude: /node_modules/,
        use: {
          loader: 'babel-loader',
          options: {
            presets: ['@babel/preset-env']
          }
        }
      },
      {
        test: /\.css$/,
        use: ['style-loader', 'css-loader']
      }
    ]
  },
  plugins: [
    new HtmlWebpackPlugin({
      template: './src/index.html',
      title: 'Webpack Build Tools Demo'
    })
  ],
  devServer: {
    contentBase: './dist',
    hot: true,
    port: 8080,
  },
  mode: 'development'
};
```

## Quality Assurance

### Testing Strategy
- **Unit Tests**: Comprehensive test coverage for all classes
- **Integration Tests**: End-to-end functionality testing
- **Edge Case Testing**: Error conditions and boundary values
- **Parameterized Tests**: Data-driven test scenarios

### Code Quality Tools
- **Gradle**: JaCoCo code coverage, Checkstyle, SpotBugs
- **Maven**: Maven Surefire for testing, optional quality plugins
- **Webpack**: ESLint for code quality, Prettier for formatting

### Build Verification
- **Gradle**: `./gradlew build` - Full build with tests and coverage
- **Maven**: `mvn verify` - Complete build lifecycle with testing
- **Webpack**: `npm run build` - Production build with optimization

## Contributing

To add a new build tool:

1. Create a new directory under `build-tool-projects/`
2. Add appropriate configuration files (build.gradle, pom.xml, webpack.config.js, etc.)
3. Implement sample application with tests
4. Update `.gitignore` with tool-specific patterns
5. Update `build-runner.sh` to support the new tool
6. Update this README with documentation

## Troubleshooting

### Common Issues

- **Build failures**: Check that all prerequisites are installed and up to date
- **Dependency issues**: Run dependency installation commands (`npm install`, `./gradlew build --refresh-dependencies`)
- **Port conflicts**: Ensure no other services are using required ports (e.g., webpack dev server on port 8080)
- **Permission issues**: Some operations may require elevated permissions

### Tool-Specific Issues

#### Gradle
- **Wrapper not found**: Run `gradle wrapper` to generate the wrapper
- **Daemon issues**: Run `./gradlew --stop` to stop the Gradle daemon

#### Maven
- **Local repository issues**: Run `mvn dependency:purge-local-repository` to clean local cache
- **Plugin issues**: Update Maven to latest version

#### Webpack
- **Node version issues**: Ensure Node.js version matches package.json engines requirement
- **Module resolution**: Check that all dependencies are installed with `npm install`

### Getting Help

- **Build Runner Logs**: Check `build-runner.log` for detailed execution logs
- **Tool Documentation**:
  - Gradle: https://docs.gradle.org
  - Maven: https://maven.apache.org/guides/
  - Webpack: https://webpack.js.org/concepts/

For issues specific to individual tools, refer to their official documentation and community resources.
