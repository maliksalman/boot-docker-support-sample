#!/bin/bash

java \
    -Dspring.profiles.active=externaldb \
    -jar dockerdemo-1.0/dockerdemo-1.0.jar
