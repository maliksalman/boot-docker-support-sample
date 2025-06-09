#!/bin/bash

docker compose -f externaldb.yaml up -d
docker run --rm -it \
  -v ./config.properties:/config/config.properties \
  -e CONFIG_PATH=/config \
  -e SPRING_PROFILES_ACTIVE=externaldb \
  -e DB_HOSTNAME=host.docker.internal \
  -p 8080:8080 \
  maliksalman/dockerdemo:native-1.0