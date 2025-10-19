#!/usr/bin/env bash
###############################
# usage: start.sh [ -e ARG ] [ -p ARG ] [ -l ARG ]
#  -e ARG: edition to run
#          valid values are (case insensitive):
#             'community', 'oss',
#             'developer', 'dev',
#             'enterprise', 'ent',
#             'datacenter', 'dc', 'dce'.
#          default value is 'community'.
#  -p ARG: name(s) of patch separated by colon (name of patch is filename without extension)
#  -l ARG: name of log file to display.
#          valid values are 'all', 'khulnasoft', 'web', 'ce' and 'es' (case insensitive).
#          default value is 'all'.
###############################

# Check if required tools are available
command -v unzip >/dev/null 2>&1 || { echo >&2 "unzip is required but not installed. Aborting."; exit 1; }
command -v pgrep >/dev/null 2>&1 || { echo >&2 "pgrep is required but not installed. Aborting."; exit 1; }
command -v pkill >/dev/null 2>&1 || { echo >&2 "pkill is required but not installed. Aborting."; exit 1; }

# Validate ROOT directory exists and is readable
if [ ! -d "$ROOT" ]; then
  echo "Error: ROOT directory does not exist: $ROOT" >&2
  exit 1
fi

if [ ! -r "$ROOT/scripts/editions.sh" ]; then
  echo "Error: Cannot read editions.sh from $ROOT/scripts/" >&2
  exit 1
fi

# Validate build script exists
if [ ! -f "$ROOT/build.sh" ]; then
  echo "Error: build.sh not found in $ROOT. Please ensure sources are available." >&2
  exit 1
fi

PATCHES=""
EDITION="$DEFAULT_EDITION"
LOG="$DEFAULT_LOG"
while getopts ":e:p:l:" opt; do
  case "$opt" in
    e) EDITION=${OPTARG:=$DEFAULT_EDITION}
       ;;
    p) PATCHES="$OPTARG"
       ;;
    l) LOG=${OPTARG:=$DEFAULT_LOG}
       ;;
    ")
      echo "Error: Unsupported option -$OPTARG" >&2
      echo "Usage: $0 [ -e ARG ] [ -p ARG ] [ -l ARG ]" >&2
      echo "  -e ARG: edition (community, oss, developer, dev, enterprise, ent, datacenter, dc, dce)" >&2
      echo "  -p ARG: patches (colon-separated list of patch names)" >&2
      echo "  -l ARG: log type (all, khulnasoft, web, ce, es)" >&2
      exit 1
      ;;
  esac
done

# Validate patches argument format
if [ -n "$PATCHES" ]; then
  # Check for valid patch names (basic validation)
  for patch in $(echo "$PATCHES" | tr ':' ' '); do
    if [ -z "$patch" ]; then
      echo "Error: Empty patch name provided" >&2
      exit 1
    fi
    # Add more specific validation if needed
  done
fi

# Check if distribution files exist or can be built
OSS_ZIP="$(distributionDirOf "community")/$(baseFileNameOf "community")-*.zip"
if ! ls ${OSS_ZIP} &> /dev/null; then
  echo 'Sources are not built. Attempting to build...'
  if ! "$ROOT"/build.sh; then
    echo 'Error: Failed to build sources. Please check build requirements.' >&2
    exit 1
  fi
fi

# stop SQ running in any instance
stopAny

# Validate distribution directory
DIST_DIR="$(distributionDirOf "$EDITION")"
if [ ! -d "$DIST_DIR" ]; then
  echo "Error: Distribution directory does not exist: $DIST_DIR" >&2
  exit 1
fi

cd "$DIST_DIR"

# Check for zip file
BASE_FILE_NAME="$(baseFileNameOf "$EDITION")"
ZIP_FILE="${BASE_FILE_NAME}-*.zip"
if ! ls $ZIP_FILE &> /dev/null; then
  echo "Error: Distribution zip file not found: $ZIP_FILE" >&2
  exit 1
fi

SH_FILE_DIR="khulnasoft-*/bin/$OS_DIR/"
if ! ls $SH_FILE_DIR &> /dev/null; then
  echo "Unpacking ${BASE_FILE_NAME}..."
  if ! unzip -qq ${ZIP_FILE}; then
    echo "Error: Failed to unpack $ZIP_FILE" >&2
    exit 1
  fi
fi

# Find the unpacked directory
UNPACKED_DIR="$(find . -maxdepth 1 -type d -name 'khulnasoft-*' | head -1)"
if [ -z "$UNPACKED_DIR" ]; then
  echo "Error: Unpacked Khulnasoft directory not found" >&2
  exit 1
fi
cd "$UNPACKED_DIR"

SQ_HOME=$(pwd)
if [ ! -d "$SQ_HOME" ]; then
  echo "Error: SQ_HOME is not a valid directory: $SQ_HOME" >&2
  exit 1
fi
source "$ROOT"/scripts/patches_utils.sh

SQ_EXEC="$SQ_HOME/bin/$OS_DIR/$SH_FILE"

# Validate SQ_EXEC exists
if [ ! -f "$SQ_EXEC" ]; then
  echo "Error: Khulnasoft executable not found: $SQ_EXEC" >&2
  exit 1
fi

# Apply patches if specified
if [ "$PATCHES" ]; then
  echo "Applying patches: $PATCHES"
  if ! call_patches "$PATCHES" "$SQ_HOME"; then
    echo "Error: Failed to apply patches" >&2
    exit 1
  fi
fi

# Run Khulnasoft
echo "Starting Khulnasoft..."
if ! runSQ "$SQ_EXEC"; then
  echo "Error: Failed to start Khulnasoft" >&2
  exit 1
fi

sleep 2  # Increased sleep time for better startup detection
echo "Khulnasoft started successfully. Tailing logs..."
doTail "$LOG"
