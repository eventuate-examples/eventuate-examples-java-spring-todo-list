#! /bin/bash

sh set-env-postgres.sh

./gradlew clean
./gradlew assemble -P eventuateDriver=local

docker-compose -f docker-compose-eventuate-local-postgres.yml down
docker-compose -f docker-compose-eventuate-local-postgres.yml up --build -d

./wait-for-services.sh $DOCKER_HOST_IP 8081 8082

./gradlew $* :e2etest:cleanTest
./gradlew $* :e2etest:test