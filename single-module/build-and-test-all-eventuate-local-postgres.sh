#! /bin/bash

. set-env-postgres.sh

docker-compose -f docker-compose-eventuate-local-postgres.yml down
docker-compose -f docker-compose-eventuate-local-postgres.yml up --build -d cdcservice

./gradlew assemble -P eventuateDriver=local
#./gradlew -x e2eTest build -P eventuateDriver=local

docker-compose -f docker-compose-eventuate-local-postgres.yml up --build -d

./wait-for-services.sh $DOCKER_HOST_IP 8080

./gradlew e2eTest cleanTest e2eTest clean -P ignoreE2EFailures=false

docker-compose -f docker-compose-eventuate-local-postgres.yml down
