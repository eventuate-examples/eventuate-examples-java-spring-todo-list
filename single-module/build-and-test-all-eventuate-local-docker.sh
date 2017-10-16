#! /bin/bash

sh set-env-postgres.sh

./gradlew clean
./gradlew assemble -P eventuateDriver=local

docker-compose -f docker-compose-eventuate-local-postgres.yml down
docker-compose -f docker-compose-eventuate-local-postgres.yml up --build -d

./wait-for-services.sh $DOCKER_HOST_IP 8080

./gradlew $* cleanTest
./gradlew $* e2eTest