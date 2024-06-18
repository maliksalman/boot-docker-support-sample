#!/bin/bash

if [[ -d dockerdemo-1.0 ]]; then
  rm -rf dockerdemo-1.0
fi

java \
    -Djarmode=tools \
    -jar target/dockerdemo-1.0.jar \
    extract
