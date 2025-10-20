#!/usr/bin/env bash
# Build Runner Script
# Manages and executes multiple build tools (Gradle, Maven, Webpack)

set -euo pipefail

# Colors for output
RED='\033[0;31m'
GREEN='\033[0;32m'
YELLOW='\033[1;33m'
BLUE='\033[0;34m'
NC='\033[0m' # No Color

BUILD_TOOLS_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")/.." && pwd)"
GRADLE_DIR="$BUILD_TOOLS_DIR/gradle"
MAVEN_DIR="$BUILD_TOOLS_DIR/maven"
WEBPACK_DIR="$BUILD_TOOLS_DIR/webpack"

LOG_FILE="$BUILD_TOOLS_DIR/build-runner.log"

# Logging functions
log_info() {
    echo -e "${BLUE}[INFO]${NC} $1" | tee -a "$LOG_FILE"
}

log_success() {
    echo -e "${GREEN}[SUCCESS]${NC} $1" | tee -a "$LOG_FILE"
}

log_warning() {
    echo -e "${YELLOW}[WARNING]${NC} $1" | tee -a "$LOG_FILE"
}

log_error() {
    echo -e "${RED}[ERROR]${NC} $1" | tee -a "$LOG_FILE"
}

# Initialize log file
init_log() {
    echo "=== Build Runner Log - $(date) ===" > "$LOG_FILE"
}

usage() {
    echo "Usage: $0 [gradle|maven|webpack|all] [command]"
    echo "Commands:"
    echo "  gradle:  Run Gradle build"
    echo "  maven:   Run Maven build"
    echo "  webpack: Run Webpack build"
    echo "  all:     Run all build tools"
    echo "Arguments:"
    echo "  build:   Build the project (default)"
    echo "  test:    Run tests"
    echo "  clean:   Clean build artifacts"
    echo "  install: Install dependencies"
    echo "  analyze: Analyze bundle/code"
    echo "  format:  Format code"
    echo "  lint:    Lint code"
    echo "Options:"
    echo "  --help:  Show this help message"
    echo "  --log:   Show log file location"
    echo "  --clean-log: Clean log file"
}

check_tool() {
    local tool="$1"
    if ! command -v "$tool" &> /dev/null; then
        log_error "$tool is not installed."
        return 1
    fi
    return 0
}

check_directory() {
    local dir="$1"
    local tool="$2"
    if [[ ! -d "$dir" ]]; then
        log_error "$tool directory not found: $dir"
        return 1
    fi
    return 0
}

run_gradle() {
    local cmd="$1"
    log_info "Running Gradle $cmd"

    check_tool "gradle" || return 1
    check_directory "$GRADLE_DIR" "Gradle" || return 1

    cd "$GRADLE_DIR"
    case "$cmd" in
        "build") gradle build --info ;;
        "test") gradle test ;;
        "clean") gradle clean ;;
        "install") gradle publishToMavenLocal ;;
        "analyze") gradle build --scan ;;
        "format") log_info "Gradle formatting not available" ;;
        "lint") log_info "Gradle linting not available" ;;
        *) log_error "Unknown Gradle command: $cmd" ;;
    esac
}

run_maven() {
    local cmd="$1"
    log_info "Running Maven $cmd"

    check_tool "mvn" || return 1
    check_directory "$MAVEN_DIR" "Maven" || return 1

    cd "$MAVEN_DIR"
    case "$cmd" in
        "build") mvn compile ;;
        "test") mvn test ;;
        "clean") mvn clean ;;
        "install") mvn install ;;
        "analyze") mvn site ;;
        "format") log_info "Maven formatting not available" ;;
        "lint") log_info "Maven linting not available" ;;
        *) log_error "Unknown Maven command: $cmd" ;;
    esac
}

run_webpack() {
    local cmd="$1"
    log_info "Running Webpack $cmd"

    check_tool "node" || return 1
    check_tool "npm" || return 1
    check_directory "$WEBPACK_DIR" "Webpack" || return 1

    cd "$WEBPACK_DIR"
    case "$cmd" in
        "build") npm run build ;;
        "test") npm test ;;
        "clean") npm run clean ;;
        "install") npm install ;;
        "analyze") npm run analyze ;;
        "format") npm run format ;;
        "lint") npm run lint ;;
        *) log_error "Unknown Webpack command: $cmd" ;;
    esac
}

run_all() {
    local cmd="$1"
    log_info "Running $cmd on all build tools..."

    local tools=("gradle" "maven" "webpack")
    local failed_tools=()

    for tool in "${tools[@]}"; do
        log_info "Running $tool $cmd"
        if ! run_"$tool" "$cmd" 2>> "$LOG_FILE"; then
            failed_tools+=("$tool")
            log_error "$tool $cmd failed"
        else
            log_success "$tool $cmd completed"
        fi
    done

    if [[ ${#failed_tools[@]} -eq 0 ]]; then
        log_success "All builds completed successfully"
        return 0
    else
        log_error "Failed tools: ${failed_tools[*]}"
        return 1
    fi
}

validate_command() {
    local tool="$1"
    local cmd="$2"

    case "$tool" in
        "gradle")
            case "$cmd" in
                "build"|"test"|"clean"|"install"|"analyze") return 0 ;;
                *) return 1 ;;
            esac
            ;;
        "maven")
            case "$cmd" in
                "build"|"test"|"clean"|"install"|"analyze") return 0 ;;
                *) return 1 ;;
            esac
            ;;
        "webpack")
            case "$cmd" in
                "build"|"test"|"clean"|"install"|"analyze"|"format"|"lint") return 0 ;;
                *) return 1 ;;
            esac
            ;;
        "all")
            case "$cmd" in
                "build"|"test"|"clean"|"install") return 0 ;;
                *) return 1 ;;
            esac
            ;;
        *)
            return 1
            ;;
    esac
}

main() {
    init_log

    if [[ $# -lt 1 ]]; then
        usage
        exit 1
    fi

    # Handle special options
    case "$1" in
        "--help"|"-h")
            usage
            exit 0
            ;;
        "--log")
            echo "Log file: $LOG_FILE"
            exit 0
            ;;
        "--clean-log")
            echo "" > "$LOG_FILE"
            log_info "Log file cleaned"
            exit 0
            ;;
    esac

    local tool="$1"
    local cmd="${2:-build}"

    # Validate command
    if ! validate_command "$tool" "$cmd"; then
        log_error "Invalid command: $tool $cmd"
        usage
        exit 1
    fi

    # Check if tool directory exists
    case "$tool" in
        "gradle") check_directory "$GRADLE_DIR" "Gradle" ;;
        "maven") check_directory "$MAVEN_DIR" "Maven" ;;
        "webpack") check_directory "$WEBPACK_DIR" "Webpack" ;;
        "all") ;; # All directories will be checked in run_all
        *) log_error "Unknown tool: $tool"; exit 1 ;;
    esac

    log_info "Starting build process: $tool $cmd"

    case "$tool" in
        "gradle") run_gradle "$cmd" ;;
        "maven") run_maven "$cmd" ;;
        "webpack") run_webpack "$cmd" ;;
        "all") run_all "$cmd" ;;
        *) usage; exit 1 ;;
    esac

    log_success "Build process completed successfully"
}

main "$@"
