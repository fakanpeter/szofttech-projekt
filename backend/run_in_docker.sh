#!/bin/sh

cd "`dirname "$0"`"

sudo docker run \
	--rm \
	-v "$(pwd)":/app \
	-v "$(pwd)"/.m2:/.m2 \
	-w /app \
	-p 8080:8080 \
	-u $(id -u):$(id -g) \
	--name szofttech_webapp_backend \
	--network szofttech_webapp_network \
	eclipse-temurin:21-jdk \
	./mvnw spring-boot:run
