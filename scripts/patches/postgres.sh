#!/usr/bin/env bash

set -euo pipefail

source scripts/property_utils.sh

SQ_HOME=$1

echo "configuring postgres"
set_property khulnasoft.jdbc.url jdbc:postgresql://localhost:5432/khulnasoft "$SQ_HOME/conf/khulnasoft.properties"
set_property khulnasoft.jdbc.username khulnasoft "$SQ_HOME/conf/khulnasoft.properties"
set_property khulnasoft.jdbc.password khulnasoft "$SQ_HOME/conf/khulnasoft.properties"
