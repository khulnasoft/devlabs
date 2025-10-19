#!/usr/bin/env bash
# Stop utilities for Khulnasoft scripts

stopAny() {
  echo "Stopping any running Khulnasoft instances..."

  # Find and kill Khulnasoft processes
  if pgrep -f "khulnasoft" > /dev/null; then
    pkill -f "khulnasoft" || true
    echo "Stopped Khulnasoft processes."
  else
    echo "No running Khulnasoft processes found."
  fi

  # Add more sophisticated stopping logic if needed
  # e.g., find processes by port, etc.
}
