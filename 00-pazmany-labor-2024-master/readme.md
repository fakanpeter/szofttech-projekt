# Example application

## Table of Contents

- [backend](#backend)
- [frontend](#frontend)
- [nginx](#nginx)

## network

if you want to use docker, you must first define a network:

`docker network create example_network`


## backend
- provides an example springboot (v3) application
You can build and run this project as:
- import an IDE as maven project (IntelIj, SprintToolSuite) and run as springboot application
- in console ./mvnw spring-boot:run 
- if you want to run in docker (in `backend` folder): `docker run --rm -v $(pwd):/app -v $(pwd)/.m2:/.m2 -w /app -p 8080:8080 -u $(id -u):$(id -g) --name example-be --network example_network eclipse-temurin:21-jdk ./mvnw spring-boot:run`

OK URL: http://localhost:8080/api/ok
H2 database console URL: http://localhost:8080/h2-console
(all settings can be found in the application.properties file)


## frontend

You can start in docker in `frontend` folder:

`docker run --rm -v $(pwd):/app -v $(pwd)/.npm:/.npm -u $(id -u):$(id -g) -p 3000:3000 -w /app --name example-fe --network example_network node:latest ./start.sh`

Or you can start without docker: [frontend](frontend/readme.md)

## nginx

You can start in docker in project root folder:

`docker run --rm -v $(pwd)/nginx/nginx.conf:/etc/nginx/nginx.conf -p 80:80 --name example-proxy --network example_network nginx:latest`

Or you can start without docker: [nginx](nginx/readme.md)
