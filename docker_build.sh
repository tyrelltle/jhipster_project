#!/usr/bin/env bash
./gradlew -Pprod bootRepackage
cp src/main/docker/Dockerfile build/libs
cd build/libs
docker build -t haklist .
