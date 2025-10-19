#!/usr/bin/env bash

set -euo pipefail

source scripts/property_utils.sh

if [ $# -ne 1 ]; then
  echo "Usage: $0 <SQ_HOME>" >&2
  exit 1
fi

SQ_HOME=$1

# Check if required environment variables are set
if [ -z "$DB_URL" ]; then
  echo "Warning: DB_URL not set, using default PostgreSQL connection" >&2
  DB_URL="jdbc:postgresql://localhost:5432/khulnasoft"
fi

if [ -z "$DB_USERNAME" ]; then
  echo "Warning: DB_USERNAME not set, using default" >&2
  DB_USERNAME="khulnasoft"
fi

if [ -z "$DB_PASSWORD" ]; then
  echo "Warning: DB_PASSWORD not set, using default" >&2
  DB_PASSWORD="khulnasoft"
fi

# Validate SQ_HOME
if [ ! -d "$SQ_HOME" ]; then
  echo "Error: SQ_HOME is not a valid directory: $SQ_HOME" >&2
  exit 1
fi

# Check if configuration file exists
CONF_FILE="$SQ_HOME/conf/khulnasoft.properties"
if [ ! -f "$CONF_FILE" ]; then
  echo "Error: Configuration file not found: $CONF_FILE" >&2
  exit 1
fi

echo "Configuring PostgreSQL with provided credentials"
set_property khulnasoft.jdbc.url "$DB_URL" "$CONF_FILE"
set_property khulnasoft.jdbc.username "$DB_USERNAME" "$CONF_FILE"
set_property khulnasoft.jdbc.password "$DB_PASSWORD" "$CONF_FILE"
