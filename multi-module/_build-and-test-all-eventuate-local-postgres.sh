#! /bin/bash

. ./set-env-postgres.sh

docker-compose -f docker-compose-eventuate-local-postgres-${MODE}.yml down
docker-compose -f docker-compose-eventuate-local-postgres-${MODE}.yml up --build -d cdcservice

./gradlew -x :e2etest:test build  -P eventuateDriver=local

docker-compose -f docker-compose-eventuate-local-postgres-${MODE}.yml up --build -d

./wait-for-services.sh $DOCKER_HOST_IP 8081 8082

./gradlew :e2etest:cleanTest :e2etest:test -P ignoreE2EFailures=false

docker-compose -f docker-compose-eventuate-local-postgres-${MODE}.yml down