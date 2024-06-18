#!/bin/bash

if [[ -f application.jsa ]]; then
  rm -f application.jsa
fi

java \
    -Dspring.profiles.active=externaldb \
    -Dspring.context.exit=onRefresh \
    -XX:ArchiveClassesAtExit=application.jsa \
    -jar dockerdemo-1.0/dockerdemo-1.0.jar
