#!/usr/bin/env bash
# Log utilities for Khulnasoft scripts

DEFAULT_LOG="all"
LOGS=("all" "khulnasoft" "web" "ce" "es")

# Logging function for consistent error and info messages
log() {
  local level="$1"
  local message="$2"
  echo "[$level] $(date '+%Y-%m-%d %H:%M:%S'): $message" >&2
}

info() {
  log "INFO" "$1"
}

warn() {
  log "WARN" "$1"
}

error() {
  log "ERROR" "$1"
}

# Function to check if a log type is supported
checkLogArgument() {
  local log="$1"
  local lower_log=$(echo "$log" | tr '[:upper:]' '[:lower:]')

  for valid_log in "${LOGS[@]}"; do
    if [ "$lower_log" = "$valid_log" ]; then
      info "Using log type: $log"
      return 0
    fi
  done

  error "Unsupported log type: $log. Valid values are: ${LOGS[*]}"
  exit 1
}

# Function to tail logs based on type
doTail() {
  local log="$1"

  case "$log" in
    all)
      info "Tailing all logs..."
      # Add logic to tail multiple log files if needed
      ;;
    khulnasoft)
      info "Tailing Khulnasoft logs..."
      ;;
    web)
      info "Tailing web logs..."
      ;;
    ce)
      info "Tailing compute engine logs..."
      ;;
    es)
      info "Tailing Elasticsearch logs..."
      ;;
    *)
      error "Unknown log type: $log"
      exit 1
      ;;
  esac
}
