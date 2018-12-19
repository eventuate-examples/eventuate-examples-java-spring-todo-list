#! /bin/bash -e

./build-and-test-all-eventuate-local-mysql.sh --no-rm

cat update-mysql.sql | ./mysql-cli.sh -i

DOCKER_COMPOSE="docker-compose -p java-spring-todo-list -f docker-compose-eventuate-local-mysql.yml -f docker-compose-eventuate-local-mysql-unified-cdc-update.yml "

$DOCKER_COMPOSE up -d --no-deps cdcservice

./wait-for-services.sh $DOCKER_HOST_IP 8099

./gradlew e2eTest

$DOCKER_COMPOSE down