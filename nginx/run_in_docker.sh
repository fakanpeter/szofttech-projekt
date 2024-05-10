#!/bin/sh

sudo docker run \
	--rm \
	-v "$(pwd)/nginx/nginx.conf":/etc/nginx/nginx.conf \
	-p 80:80 \
	--name szofttech_webapp_nginx \
	--network szofttech_webapp_network \
	nginx:latest
