#!/usr/bin/env bash
# Patch utilities for Khulnasoft scripts

call_patches() {
  local patches="$1"
  local sq_home="$2"

  echo "Applying patches: $patches"

  # Split patches by colon
  IFS=':' read -ra PATCH_LIST <<< "$patches"

  for patch in "${PATCH_LIST[@]}"; do
    echo "Applying patch: $patch"

    # Check if patch script exists
    local patch_script="$ROOT/scripts/patches/${patch}.sh"
    if [ -f "$patch_script" ]; then
      # Run the patch script with SQ_HOME as argument
      bash "$patch_script" "$sq_home"
    else
      echo "Patch script not found: $patch_script" >&2
      exit 1
    fi
  done
}
