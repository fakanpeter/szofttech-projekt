#!/bin/sh

cd "`dirname "$0"`"

sudo docker run \
	--rm \
	-v "$(pwd)":/app \
	-v "$(pwd)/.npm":/.npm \
	-u $(id -u):$(id -g) \
	-p 3000:3000 \
	-w /app \
	--name szofttech-webapp-frontend \
	--network szofttech_webapp_network \
	node:latest \
	./start.sh
