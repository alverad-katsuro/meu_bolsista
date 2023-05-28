#!/bin/bash

mvn install && docker-compose build && docker stack rm meubolsista-api && docker stack deploy -c docker-compose.api.yml meubolsista-api
