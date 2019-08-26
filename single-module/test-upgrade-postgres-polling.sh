#! /bin/bash -e

./build-and-test-all-eventuate-local-postgres-polling.sh --no-rm

cat update-postgres.sql | ./postgres-cli.sh -i

DOCKER_COMPOSE="docker-compose -f docker-compose-eventuate-local-postgres-polling.yml -f docker-compose-eventuate-local-postgres-polling-unified-cdc-update.yml "

$DOCKER_COMPOSE up -d --no-deps cdcservice

./wait-for-services.sh $DOCKER_HOST_IP 8099

./gradlew e2eTest

$DOCKER_COMPOSE down