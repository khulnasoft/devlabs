# Build Tool Projects

This directory contains example configurations and implementations for various build tools commonly used in software development projects.

## Structure

```
build-tool-projects/
├── build-runner/          # Build orchestration scripts
│   └── build-runner.sh    # Main script to run builds across tools
├── gradle/                # Gradle build tool example
│   ├── build.gradle       # Gradle build configuration
│   ├── settings.gradle    # Gradle settings
│   └── src/               # Source code
├── maven/                 # Maven build tool example
│   ├── pom.xml            # Maven project configuration
│   └── src/               # Source code
├── webpack/               # Webpack build tool example
│   ├── webpack.config.js  # Webpack configuration
│   ├── package.json       # NPM dependencies and scripts
│   └── src/               # Source code
└── README.md              # This file
```

## Build Tools Overview

### Gradle
- **Language**: Java/Groovy/Kotlin
- **Use Case**: Multi-language builds, dependency management
- **Example**: Java application with JUnit tests

### Maven
- **Language**: Java
- **Use Case**: Java project management, dependency resolution
- **Example**: Simple Java project with testing

### Webpack
- **Language**: JavaScript/Node.js
- **Use Case**: Frontend bundling, asset management
- **Example**: JavaScript application bundling

## Usage

### Using the Build Runner Script

The `build-runner.sh` script provides a unified interface to run builds across different tools:

```bash
# Run Gradle build
./build-runner.sh gradle build

# Run Maven tests
./build-runner.sh maven test

# Run Webpack build
./build-runner.sh webpack build

# Run all tools with clean command
./build-runner.sh all clean
```

### Individual Tool Usage

#### Gradle
```bash
cd gradle
gradle build
gradle test
gradle run
```

#### Maven
```bash
cd maven
mvn compile
mvn test
mvn package
```

#### Webpack
```bash
cd webpack
npm install
npm run build
npm run dev
```

## Prerequisites

Ensure you have the following tools installed:

- **Java 11+** (for Gradle and Maven)
- **Node.js** (for Webpack)
- **Gradle** (or use the Gradle Wrapper)
- **Maven**
- **npm** (comes with Node.js)

## Configuration

Each build tool has its own configuration:

- **Gradle**: `build.gradle` - Define dependencies, plugins, and tasks
- **Maven**: `pom.xml` - Define project structure, dependencies, and build lifecycle
- **Webpack**: `webpack.config.js` - Configure bundling, loaders, and plugins

## Features Added

- **Unified Build Runner**: Single script to orchestrate multiple build tools
- **Comprehensive .gitignore**: Tool-specific ignore patterns for clean repositories
- **Example Projects**: Working examples for each build tool
- **Documentation**: This README with usage instructions

## Contributing

To add a new build tool:

1. Create a new directory under `build-tool-projects/`
2. Add appropriate configuration files
3. Update `.gitignore` with tool-specific patterns
4. Update `build-runner.sh` to support the new tool
5. Update this README

## Troubleshooting

- **Build failures**: Check that all prerequisites are installed
- **Dependency issues**: Run `npm install` or `gradle build --refresh-dependencies`
- **Port conflicts**: Ensure no other services are using required ports for dev servers

For issues specific to individual tools, refer to their official documentation.
