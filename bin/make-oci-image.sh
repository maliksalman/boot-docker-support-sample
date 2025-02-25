#!/bin/bash

./mvnw \
    -Dspring-boot.build-image.imageName='maliksalman/dockerdemo:1.0' \
    clean spring-boot:build-image
