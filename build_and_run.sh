#!/bin/bash
set -x
echo "Starting ./mvnw clean install -DskipTests "
if mvn clean install -DskipTests; then
  echo "Clean install - successful "
  echo "Starting docker compose up "
  docker compose up
else
  echo "Clean install - failed "
fi