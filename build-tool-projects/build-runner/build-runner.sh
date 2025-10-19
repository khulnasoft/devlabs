#!/usr/bin/env bash
# Build Runner Script
# Manages and executes multiple build tools (Gradle, Maven, Webpack)

set -euo pipefail

BUILD_TOOLS_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")/.." && pwd)"
GRADLE_DIR="$BUILD_TOOLS_DIR/gradle"
MAVEN_DIR="$BUILD_TOOLS_DIR/maven"
WEBPACK_DIR="$BUILD_TOOLS_DIR/webpack"

usage() {
    echo "Usage: $0 [gradle|maven|webpack|all] [command]"
    echo "Commands:"
    echo "  gradle: Run Gradle build"
    echo "  maven:  Run Maven build"
    echo "  webpack: Run Webpack build"
    echo "  all:    Run all build tools"
    echo "Arguments:"
    echo "  build:   Build the project (default)"
    echo "  test:    Run tests"
    echo "  clean:   Clean build artifacts"
    echo "  install: Install dependencies"
}

check_tool() {
    local tool="$1"
    if ! command -v "$tool" &> /dev/null; then
        echo "Error: $tool is not installed." >&2
        exit 1
    fi
}

run_gradle() {
    local cmd="$1"
    check_tool "gradle"
    cd "$GRADLE_DIR"
    case "$cmd" in
        "build") gradle build ;;
        "test") gradle test ;;
        "clean") gradle clean ;;
        *) echo "Unknown Gradle command: $cmd" ;;
    esac
}

run_maven() {
    local cmd="$1"
    check_tool "mvn"
    cd "$MAVEN_DIR"
    case "$cmd" in
        "build") mvn compile ;;
        "test") mvn test ;;
        "clean") mvn clean ;;
        "install") mvn install ;;
        *) echo "Unknown Maven command: $cmd" ;;
    esac
}

run_webpack() {
    local cmd="$1"
    check_tool "npm"
    cd "$WEBPACK_DIR"
    case "$cmd" in
        "build") npm run build ;;
        "test") npm test ;;
        "clean") rm -rf dist ;;
        "install") npm install ;;
        *) echo "Unknown Webpack command: $cmd" ;;
    esac
}

run_all() {
    local cmd="$1"
    echo "Running $cmd on all build tools..."
    run_gradle "$cmd"
    run_maven "$cmd"
    run_webpack "$cmd"
}

main() {
    if [ $# -lt 1 ]; then
        usage
        exit 1
    fi

    local tool="$1"
    local cmd="${2:-build}"

    case "$tool" in
        "gradle") run_gradle "$cmd" ;;
        "maven") run_maven "$cmd" ;;
        "webpack") run_webpack "$cmd" ;;
        "all") run_all "$cmd" ;;
        *) usage ;;
    esac
}

main "$@"
