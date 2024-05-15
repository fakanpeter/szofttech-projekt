# Vau-Vau Alapítvány weboldal - szofttech webapp project

**Author:** Team Ducktape *(Laczkó Csongor, Fakan Péter, Kovács Balázs, Pesti Tamás)*

## Table of Contents

- [How to run in docker](#how-to-run-in-docker)
	- [0. network](#0-network)
	- [1. backend](#1-backend)
	- [2. frontend](#2-frontend)
	- [3. nginx](#3-nginx)

## How to run in docker

We wrote three scripts for making the running of the webapp easier.

You have to start the 3 containers in the right order to avoid possible misfunctionalities.

### 0. network

First you need to create the appropriate docker network interface:

`./create_docker_network.sh`

It will create an interface with name `szofttech_webapp_network`.


### 1. backend

To run the Java Springboot backend run the following:

`backend/run_in_docker.sh`

If it boots up, you will reach the backend directly at:
- `http://127.0.0.1:8080/`
- or `http://localhost:8080/`.


### 2. frontend

To run the Vue.js frontend run the following:

`frontend/run_in_docker.sh`

If it boots up, you will reach the frontend directly at:
- `http://127.0.0.1:3000/`
- or `http://localhost:3000/`.


### 3. nginx

To run the nginx proxy run the following script:

`nginx/run_in_docker.sh`

Then you can access the website at:
- frontend: `http://127.0.0.1/` or `http://localhost/`
- backend (only /api url mapping): `http://127.0.0.1/api/` or `http://localhost/api/`


## For developers

Additional infos can be found at the project readme files:
- [frontend](frontend/readme.md)
- [backend](backend/readme.md)
- [nginx](nginx/readme.md)

Warning: These files may be incomplete or completely misleading.
