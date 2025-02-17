#!/usr/bin/env bash

./gradlew assemble
java -jar ./build/libs/category-0.0.1-SNAPSHOT.jar --spring.profiles.active=local