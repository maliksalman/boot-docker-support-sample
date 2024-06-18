#!/bin/bash

java \
    -Dspring.profiles.active=externaldb \
    -XX:SharedArchiveFile=application.jsa \
    -jar dockerdemo-1.0/dockerdemo-1.0.jar
