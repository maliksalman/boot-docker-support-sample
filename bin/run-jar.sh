#!/bin/bash

java \
    -Dspring.profiles.active=externaldb \
    -jar target/dockerdemo-1.0.jar
