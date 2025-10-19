#!/usr/bin/env bash
# Edition management utilities for Khulnasoft scripts
# Defines available editions and provides functions for validation and alias resolution

set -euo pipefail

# Default edition to use if none specified
DEFAULT_EDITION="community"

# List of supported editions
EDITIONS="community"

# Function to convert string to lowercase
# Usage: toLower "String"
toLower() {
  echo "$1" | tr '[:upper:]' '[:lower:]'
}

# Function to check if an edition is supported
# Usage: checkEdition "edition_name"
# Exits with error if edition is not supported
checkEdition() {
  local edition="$1"
  for supported_edition in $EDITIONS; do
    if [ "$edition" = "$supported_edition" ]; then
      return 0
    fi
  done

  echo "Error: Unsupported edition '$edition'. Supported editions: $EDITIONS" >&2
  exit 1
}

# Function to resolve edition aliases to canonical names
# Usage: resolveAliases "edition_alias"
# Currently maps 'oss' to 'community', others remain unchanged
resolveAliases() {
  local edition="$1"
  local lower_edition=$(toLower "$edition")

  case "$lower_edition" in
    oss)
      echo "community"
      ;;
    *)
      echo "$lower_edition"
      ;;
  esac
}

# Function to get the distribution directory path for an edition
# Usage: distributionDirOf "edition"
distributionDirOf() {
  echo "khulnasoft-application/build/distributions/"
}

# Function to get the base filename for an edition
# Usage: baseFileNameOf "edition"
baseFileNameOf() {
  echo "khulnasoft-application"
}
