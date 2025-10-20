#!/bin/bash

# Copyright KhulnaSoft, Ltd.

set -e

echo "Generating protobuf code..."

# Generate protobuf code using buf
buf generate proto

echo "Protobuf code generation completed successfully."
