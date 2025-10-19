#!/usr/bin/env bash
# Property utilities for Khulnasoft configuration

set_property() {
  local property="$1"
  local value="$2"
  local file="$3"

  echo "Setting property $property=$value in $file"

  # Check if file exists
  if [ ! -f "$file" ]; then
    echo "Configuration file not found: $file" >&2
    exit 1
  fi

  # Use sed to set or update the property
  if grep -q "^$property=" "$file"; then
    # Update existing property
    sed -i.bak "s|^$property=.*|$property=$value|" "$file"
  else
    # Add new property
    echo "$property=$value" >> "$file"
  fi
}
