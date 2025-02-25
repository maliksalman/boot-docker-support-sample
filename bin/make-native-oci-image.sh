#!/bin/bash

./mvnw \
    -Pnative \
    -Dspring-boot.build-image.imageName='maliksalman/dockerdemo:native-1.0' \
    clean spring-boot:build-image
