#! /bin/bash

. ./set-env-postgres.sh

docker-compose -f docker-compose-eventuate-local-postgres-${MODE}.yml down
docker-compose -f docker-compose-eventuate-local-postgres-${MODE}.yml up --build -d cdcservice

./gradlew -x e2eTest build -P eventuateDriver=local

docker-compose -f docker-compose-eventuate-local-postgres-${MODE}.yml up --build -d

./wait-for-services.sh $DOCKER_HOST_IP 8080

./gradlew cleanTest e2eTest -P ignoreE2EFailures=false

docker-compose -f docker-compose-eventuate-local-postgres-${MODE}.yml down
