#!/usr/bin/env bash

set -euo pipefail

source scripts/property_utils.sh

# Check if debug port is provided via environment variable\nport=${DEBUG_PORT:-5006}\necho "Enabling debug on compute engine, listening on port $port"\n\n# Validate SQ_HOME\nif [ ! -d \"$1\" ]; then\n  echo \"Error: SQ_HOME is not a valid directory: $1\" >&2\n  exit 1\nfi\n\nset_property khulnasoft.ce.javaAdditionalOpts \"-agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=$port\" \"$1/conf/khulnasoft.properties\"
